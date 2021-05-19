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
import com.manuel.ApiProyectoFinal.enums.SearchByTemperatureRecord;
import com.manuel.ApiProyectoFinal.exceptions.ExistingObjectException;
import com.manuel.ApiProyectoFinal.exceptions.RecordNotFoundException;
import com.manuel.ApiProyectoFinal.models.TemperatureRecord;
import com.manuel.ApiProyectoFinal.services.TemperatureRecordService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/temperaturerecord")
@Api(tags = "REGISTROS_TEMPERATURA")
@CrossOrigin(origins = "*",maxAge = 3600)
public class TemperatureRecordController {
	
	@Autowired
	TemperatureRecordService temperatureRecordService;
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@PostMapping()
	@ApiOperation(value = "Crear un Registro de Temperatura",notes = "Crear un registro de temperatura devolviendo el registro creado con su id. Lanza excepción si existe un registro similar")
	public ResponseEntity<TemperatureRecord> createTemperatureRecord(@Valid @RequestBody TemperatureRecord newTemperatureRecord)throws ExistingObjectException{
		
		return new ResponseEntity<TemperatureRecord>(this.temperatureRecordService.createTemperatureRecord(newTemperatureRecord),new HttpHeaders(),HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@PutMapping()
	@ApiOperation(value = "Actualizar un Registro de Temperatura",notes = "Aztualiza un registro de temperatura devolviendo el registro actualizado. Lanza excepción si existe un registro similar o si no se ha encontrado")
	public ResponseEntity<TemperatureRecord> updateTemperatureRecord(@Valid @RequestBody TemperatureRecord updateTemperatureRecord)throws ExistingObjectException,RecordNotFoundException{
		
		return new ResponseEntity<TemperatureRecord>(this.temperatureRecordService.updateTemperatureRecord(updateTemperatureRecord),new HttpHeaders(),HttpStatus.OK);
		
	}
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Eliminar un Registro de Temperatura",notes = "Elimina un registro de temperatura mediante una id devolviendo TRUE si lo ha eliminado o FALSE si no lo ha encontrado")
	public ResponseEntity<Boolean> deleteTemperatureRecord(@PathVariable("id") Long id){
		return new ResponseEntity<Boolean>(this.temperatureRecordService.deleteTemperatureRecord(id),new HttpHeaders(),HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@GetMapping("/getPages/{uid}/{size}")
	@ApiOperation(value = "Obtiene las páginas del registro",notes = "Obtiene el número de páginas del registro según el usuario, el tamaño de la página y el filtro deseado")
	public ResponseEntity<Integer> getPages(@PathVariable("uid") String uid,@PathVariable("size") int size,
			@RequestParam(name = "date", defaultValue ="") String date,@RequestParam(name = "appliance", defaultValue ="") String appliance){
		
		String cadena="";
		SearchByTemperatureRecord searchCase=SearchByTemperatureRecord.NO;
		if(!date.equals("")){
			cadena=date;
			searchCase=SearchByTemperatureRecord.DATE;
		}else if(!appliance.equals("")) {
			searchCase=SearchByTemperatureRecord.APPLIANCE;
			cadena=appliance;
		}
		Integer totalpages=this.temperatureRecordService.getPages(uid, cadena, size, searchCase);
		
		return new ResponseEntity<Integer>(totalpages,new HttpHeaders(),HttpStatus.OK);
		
	}
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@GetMapping("/getBy/{uid}/{page}/{size}")
	@ApiOperation(value = "Muestra una Lista de Registros de Temperatura",notes = "Muestra una lista por usuario, con paginación y filtro deseado")
	public ResponseEntity<List<TemperatureRecord>> getAllBy(@PathVariable("uid") String uid,@PathVariable("size") int size,@PathVariable("page") int page,
			@RequestParam(name = "date", defaultValue ="") String date,@RequestParam(name = "appliance", defaultValue ="") String appliance,
			@RequestParam(name = "order",defaultValue = "ASCENDING") AscDesc order) {
		
		String cadena="";
		Page<TemperatureRecord> pageList=null;
		List<TemperatureRecord> listatemp=new ArrayList<TemperatureRecord>();
		Pageable pageable=null;
		SearchByTemperatureRecord caseSearch=SearchByTemperatureRecord.NO;
		
		if(!date.equals("")) {
			cadena=date;
			caseSearch=SearchByTemperatureRecord.DATE;
		
		}else if(!appliance.equals("")) {
			cadena=appliance;
			caseSearch=SearchByTemperatureRecord.APPLIANCE;

		}
		if(order==AscDesc.ASCENDING) {
			pageable=PageRequest.of(page, size,Sort.by("date").ascending());
		}else {
			pageable=PageRequest.of(page, size,Sort.by("date").descending());
		}
		
		pageList=this.temperatureRecordService.getAllBy(uid, cadena, caseSearch, pageable);
		if(pageList!=null) {
			listatemp.addAll(pageList.getContent());
		}
		return new ResponseEntity<List<TemperatureRecord>>(listatemp,new HttpHeaders(),HttpStatus.OK);
	}
	
	
	
	
}
