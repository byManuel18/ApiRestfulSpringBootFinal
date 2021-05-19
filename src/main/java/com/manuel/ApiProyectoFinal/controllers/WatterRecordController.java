package com.manuel.ApiProyectoFinal.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.manuel.ApiProyectoFinal.enums.AscDesc;
import com.manuel.ApiProyectoFinal.enums.WatterRecordSearch;
import com.manuel.ApiProyectoFinal.exceptions.ExistingObjectException;
import com.manuel.ApiProyectoFinal.exceptions.RecordNotFoundException;
import com.manuel.ApiProyectoFinal.models.WatterRecord;
import com.manuel.ApiProyectoFinal.services.WatterRecordService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/watterrecord")
@Api(tags = "REGISTRO_AGUA")
@CrossOrigin(origins = "*",maxAge = 3600)
public class WatterRecordController {
	
	@Autowired
	WatterRecordService watterRecordService;
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@PostMapping()
	@ApiOperation(value = "Crear Registro Agua",notes = "Crea un registro de agua devolviendo el registro creado con su id. Lanza excepción si exiate un registro similar")
	public ResponseEntity<WatterRecord> createWatterRecord(@Valid @RequestBody WatterRecord newWatterRecord)throws ExistingObjectException{
		return new ResponseEntity<WatterRecord>(this.watterRecordService.createWatterRecord(newWatterRecord),new HttpHeaders(),HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@PutMapping()
	@ApiOperation(value = "Actualizar Registro Agua",notes = "Actualiza un registro de agua devolviendo el registro actualizado. Lanza excepción si existe un registro similar o no lo encuentra")
	public ResponseEntity<WatterRecord> updateWatterRecord(@Valid @RequestBody WatterRecord updateWatterRecord)throws ExistingObjectException,RecordNotFoundException{
		return new ResponseEntity<WatterRecord>(this.watterRecordService.updateWatterRecord(updateWatterRecord),new HttpHeaders(),HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Eliminar Registro Agua",notes = "Elimina un registro mediante su id devolviendo TRUE si lo ha eliminado o FALSE si no lo ha encontrado")
	public ResponseEntity<Boolean> deleteWatterRecord(@PathVariable("id") Long id){
		return new ResponseEntity<Boolean>(this.watterRecordService.deleteWatterRecord(id),new HttpHeaders(),HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@GetMapping("/getPages/{uid}/{size}")
	@ApiOperation(value = "Obtiene las páginas del Registro de Agua",notes = "Obtiene las páginas del registro mediante un usuario, paginación y filtro deseado")
	public ResponseEntity<Integer> getPages(@PathVariable("uid") String uid,@PathVariable("size") int size, @RequestParam(name = "date", defaultValue ="") String date,
			@RequestParam(name = "samplingpoint", defaultValue ="") String samplingpoint){
		String cadena="";
		WatterRecordSearch caseSearch=WatterRecordSearch.NO;
		
		if(!date.equals("")) {
			cadena =date;
			caseSearch=WatterRecordSearch.DATE;
		}else if(!samplingpoint.equals("")) {
			cadena=samplingpoint;
			caseSearch=WatterRecordSearch.SAMPLING_POINT;
		}
		
		Integer totalpages=this.watterRecordService.getPages(uid, cadena, caseSearch, size);
		
		return new ResponseEntity<Integer>(totalpages,new  HttpHeaders(),HttpStatus.OK);
		
	}
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@GetMapping("/getBy/{uid}/{page}/{size}")
	@ApiOperation(value = "Obtiene una Lista del Registro de Agua",notes = "Muestra una lista del registro según un usuario, paginación y filtro deseado")
	public ResponseEntity<List<WatterRecord>> getAllBy(@PathVariable("page") int page,@PathVariable("uid") String uid,@PathVariable("size") int size, @RequestParam(name = "date", defaultValue ="") String date,
			@RequestParam(name = "order",defaultValue = "ASCENDING") AscDesc order,@RequestParam(name = "samplingpoint", defaultValue ="") String samplingpoint){
		String cadena="";
		WatterRecordSearch caseSearch=WatterRecordSearch.NO;
		Pageable pageable=null;
		Page<WatterRecord> pageList=null;
		List<WatterRecord> listWatterRecord=new ArrayList<WatterRecord>();
		
		if(!date.equals("")) {
			cadena=date;
			caseSearch=WatterRecordSearch.DATE;
			if(order==AscDesc.ASCENDING) {
				pageable=PageRequest.of(page, size,Sort.by("date").ascending());
			}else {
				pageable=PageRequest.of(page, size,Sort.by("date").descending());
			}
			
		}else if(!samplingpoint.equals("")){
			cadena=samplingpoint;
			caseSearch=WatterRecordSearch.SAMPLING_POINT;
			if(order==AscDesc.ASCENDING) {
				pageable=PageRequest.of(page, size,Sort.by("samplingpoint").ascending());
			}else {
				pageable=PageRequest.of(page, size,Sort.by("samplingpoint").descending());
			}
		}else {
			if(order==AscDesc.ASCENDING) {
				pageable=PageRequest.of(page, size,Sort.by("date").ascending());
			}else {
				pageable=PageRequest.of(page, size,Sort.by("date").descending());
			}
		}
		
		
		pageList=this.watterRecordService.getAllBy(cadena, uid, caseSearch, pageable);
		
		if(pageList!=null){
			listWatterRecord.addAll(pageList.getContent());
		}
		return new ResponseEntity<List<WatterRecord>>(listWatterRecord,new HttpHeaders(),HttpStatus.OK);
	}
}
