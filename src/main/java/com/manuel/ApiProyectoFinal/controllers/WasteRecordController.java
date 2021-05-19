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
import com.manuel.ApiProyectoFinal.enums.SearchByWasteRecord;
import com.manuel.ApiProyectoFinal.exceptions.ExistingObjectException;
import com.manuel.ApiProyectoFinal.exceptions.RecordNotFoundException;
import com.manuel.ApiProyectoFinal.models.WasteRecord;
import com.manuel.ApiProyectoFinal.services.WasteRecordService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/wasterecord")
@Api(tags = "REGISTRO_DESECHOS")
@CrossOrigin(origins = "*",maxAge = 3600)
public class WasteRecordController {
	
	@Autowired
	WasteRecordService wasteRecordService;
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@PostMapping()
	@ApiOperation(value = "Crear Registro Residuos",notes = "Crea un registro de residuos devolviendo el registro creado con su id. Lanza excepción si existe un registro parecido")
	public ResponseEntity<WasteRecord> createWasteRecord(@Valid @RequestBody WasteRecord newWasteRecord)throws ExistingObjectException{
		return new ResponseEntity<WasteRecord>(this.wasteRecordService.createWasteRecord(newWasteRecord),new HttpHeaders(),HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@PutMapping()
	@ApiOperation(value = "Actualizar Registro Residuos",notes = "Actualiza un registro de residuos devolviendo el registro actualizado. Lanza excepción si existe un registro parecido o no se ha encontrado")
	public ResponseEntity<WasteRecord> updateWasteRecord(@Valid @RequestBody WasteRecord updateWasteRecord)throws ExistingObjectException,RecordNotFoundException{
		return new ResponseEntity<WasteRecord>(this.wasteRecordService.updateWasteRecord(updateWasteRecord),new HttpHeaders(),HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Eliminar Registro Residuos",notes = "Eliminar registro mediante su id devolviendo TRUE si lo ha eliminado y FALSE si no lo ha encontrado")
	public ResponseEntity<Boolean> deleteWasteRecord(@PathVariable("id") Long id ){
		return new ResponseEntity<Boolean>(this.wasteRecordService.deleteWasteRecord(id),new HttpHeaders(),HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@GetMapping("/getPages/{uid}/{size}")
	@ApiOperation(value = "Obtener páginas del Registro Residuos",notes = "Obtiene el número de páginas del registro dependiendo del usuario, la paginación y el filtro deseado")
	public ResponseEntity<Integer> getPages(@PathVariable("uid") String uid, @PathVariable("size") int size,
			@RequestParam(name = "date", defaultValue ="") String date,@RequestParam(name = "person", defaultValue ="") String person){
		SearchByWasteRecord caseSearch=SearchByWasteRecord.NO;
		String cadena="";
		if(!person.equals("")) {
			cadena=person;
			caseSearch=SearchByWasteRecord.PERSON;
		}else if(!date.equals("")) {
			cadena=date;
			caseSearch=SearchByWasteRecord.DATE;
		}
		
		Integer totalpages=this.wasteRecordService.getPages(uid, cadena, size, caseSearch);
		
		return new ResponseEntity<Integer>(totalpages,new HttpHeaders(),HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@GetMapping("/getBy/{uid}/{page}/{size}")
	@ApiOperation(value = "Obtener lista del Registro Residuos",notes = "Muestra una lista del registro dependiendo del usuario, la paginación y el filtro deseado")
	public ResponseEntity<List<WasteRecord>> getAllBy(@PathVariable("uid") String uid, @PathVariable("size") int size,
			@RequestParam(name = "date", defaultValue ="") String date,@RequestParam(name = "person", defaultValue ="") String person,
			@PathVariable("page")int page,@RequestParam(name = "order",defaultValue = "ASCENDING") AscDesc order){
		String cadena="";
		Pageable pageable=null;
		SearchByWasteRecord caseSearch=SearchByWasteRecord.NO;
		Page<WasteRecord> pageList=null;
		List<WasteRecord> listWasteRecord=new ArrayList<WasteRecord>();
		
		if(!person.equals("")) {
			cadena=person;
			caseSearch=SearchByWasteRecord.PERSON;
			if(order==AscDesc.ASCENDING) {
				pageable=PageRequest.of(page, size,Sort.by("person").ascending());
			}else {
				pageable=PageRequest.of(page, size,Sort.by("person").descending());
			}
		}else if(!date.equals("")) {
			cadena=date;
			caseSearch=SearchByWasteRecord.DATE;
			if(order==AscDesc.ASCENDING) {
				pageable=PageRequest.of(page, size,Sort.by("date").ascending());
			}else {
				pageable=PageRequest.of(page, size,Sort.by("date").descending());
			}
		}else {
			if(order==AscDesc.ASCENDING) {
				pageable=PageRequest.of(page, size,Sort.by("date").ascending());
			}else {
				pageable=PageRequest.of(page, size,Sort.by("date").descending());
			}
		}
		
		pageList=this.wasteRecordService.getAllBy(cadena, uid, caseSearch, pageable);
		if(pageList!=null) {
			listWasteRecord.addAll(pageList.getContent());
		}
		
		return new ResponseEntity<List<WasteRecord>>(listWasteRecord,new HttpHeaders(),HttpStatus.OK);
	}
	
}
