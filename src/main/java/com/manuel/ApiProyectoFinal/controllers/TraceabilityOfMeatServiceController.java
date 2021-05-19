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
import com.manuel.ApiProyectoFinal.enums.SearchByTraceabilityOfMeat;
import com.manuel.ApiProyectoFinal.exceptions.ExistingObjectException;
import com.manuel.ApiProyectoFinal.exceptions.RecordNotFoundException;
import com.manuel.ApiProyectoFinal.models.TraceabilityOfMeat;
import com.manuel.ApiProyectoFinal.services.TraceabilityOfMeatService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("/traceabilityofmeat")
@Api(tags = "TRAZABILIDAD_CARNE")
@CrossOrigin(origins = "*",maxAge = 3600)
public class TraceabilityOfMeatServiceController {
	
	@Autowired
	TraceabilityOfMeatService traceabilityOfMeatService;
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@PostMapping()
	@ApiOperation(value = "Crear Registro Trazabilidad Carne",notes = "Crea un registro de trazabilidad de carne devolviendo el registro creado con su id. Lanza excepción si ya existe uno similar")
	public ResponseEntity<TraceabilityOfMeat> createTraceabilityOfMeat(@Valid @RequestBody TraceabilityOfMeat newTraceabilityOfMeat)throws ExistingObjectException{
		return new ResponseEntity<TraceabilityOfMeat>(this.traceabilityOfMeatService.createTraceabilityOfMeat(newTraceabilityOfMeat),new HttpHeaders(),HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@PutMapping()
	@ApiOperation(value = "Actualiza Registro Trazabilidad Carne",notes = "Actualiza un registro de trazabilidad de carne devolviendo el registro actualizado. Lanza excepción si ya existe uno similar o no existe")
	public ResponseEntity<TraceabilityOfMeat> updateTraceabilityOfMeat(@Valid @RequestBody TraceabilityOfMeat updateTraceabilityOfMeat)throws ExistingObjectException,RecordNotFoundException{
		return new ResponseEntity<TraceabilityOfMeat>(this.traceabilityOfMeatService.updateTraceabilityOfMeat(updateTraceabilityOfMeat),new HttpHeaders(),HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Eliminar Registro Trazabilidad Carne",notes = "Elimina un registro mediante su id")
	public ResponseEntity<Boolean> deleteTraceabilityOfMeat(@PathVariable("id") Long id){
		return new ResponseEntity<Boolean>(this.traceabilityOfMeatService.deleteTraceabilityOfMeatRepository(id),new HttpHeaders(),HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@GetMapping("/getPages/{uid}/{size}")
	@ApiOperation(value = "Obtiene las páginas del Registro Trazabilidad de Carne",notes = "Obtiene el número de páginas que tiene el registro mediante un usuario, paginación y filtro deseado")
	public ResponseEntity<Integer> getPages(@PathVariable("uid") String uid,@PathVariable("size") int size,@RequestParam(name = "arrivaldate", defaultValue ="") String arrivaldate,
			@RequestParam(name = "startdate", defaultValue ="") String startdate,@RequestParam(name = "enddate", defaultValue ="") String enddate){
		
		String cadena="";
		SearchByTraceabilityOfMeat caseSearch=SearchByTraceabilityOfMeat.NO;
		
		if(!arrivaldate.equals("")) {
			cadena=arrivaldate;
			caseSearch=SearchByTraceabilityOfMeat.ARRIVAL_DATE;
		}else if(!startdate.equals("")) {
			cadena=startdate;
			caseSearch=SearchByTraceabilityOfMeat.START_DATE;
		}else if(!enddate.equals("")) {
			cadena=enddate;
			caseSearch=SearchByTraceabilityOfMeat.START_DATE;
		}
		
		Integer totalpages=this.traceabilityOfMeatService.getPages(uid, cadena, size, caseSearch);
		
		return new ResponseEntity<Integer>(totalpages,new HttpHeaders(),HttpStatus.OK);
		
	}
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@GetMapping("/getBy/{uid}/{page}/{size}")
	@ApiOperation(value = "Muestra una lista del Registro de la Trazabilidad de la Carne",notes = "Muestra una lista del registro de un usuario mediante paaginacióny filtro deseado")
	public ResponseEntity<List<TraceabilityOfMeat>> getAllBy(@PathVariable("uid") String uid,@PathVariable("page") int page,@PathVariable("size") int size,@RequestParam(name = "arrivaldate", defaultValue ="") String arrivaldate,
			@RequestParam(name = "startdate", defaultValue ="") String startdate,@RequestParam(name = "enddate", defaultValue ="") String enddate,@RequestParam(name = "order",defaultValue = "ASCENDING") AscDesc order){
		String cadena="";
		SearchByTraceabilityOfMeat caseSearch=SearchByTraceabilityOfMeat.NO;
		Pageable pageable=null;
		List<TraceabilityOfMeat> listaTOM=new ArrayList<TraceabilityOfMeat>();
		Page<TraceabilityOfMeat> pageTOM=null;
		
		if(!arrivaldate.equals("")) {
			cadena=arrivaldate;
			caseSearch=SearchByTraceabilityOfMeat.ARRIVAL_DATE;
			if(order==AscDesc.ASCENDING) {
				pageable=PageRequest.of(page, size,Sort.by("arrivaldate").ascending());
			}else {
				pageable=PageRequest.of(page, size,Sort.by("arrivaldate").descending());
			}
		}else if(!startdate.equals("")) {
			cadena=startdate;
			caseSearch=SearchByTraceabilityOfMeat.START_DATE;
			if(order==AscDesc.ASCENDING) {
				pageable=PageRequest.of(page, size,Sort.by("startdate").ascending());
			}else {
				pageable=PageRequest.of(page, size,Sort.by("startdate").descending());
			}
		}else if(!enddate.equals("")) {
			cadena=enddate;
			caseSearch=SearchByTraceabilityOfMeat.START_DATE;
			if(order==AscDesc.ASCENDING) {
				pageable=PageRequest.of(page, size,Sort.by("enddate").ascending());
			}else {
				pageable=PageRequest.of(page, size,Sort.by("enddate").descending());
			}
		}else {
			if(order==AscDesc.ASCENDING) {
				pageable=PageRequest.of(page, size,Sort.by("arrivaldate").ascending());
			}else {
				pageable=PageRequest.of(page, size,Sort.by("arrivaldate").descending());
			}
		}
		
		pageTOM=this.traceabilityOfMeatService.getAllBy(uid, cadena, caseSearch, pageable);
		if(pageTOM!=null) {
			listaTOM.addAll(pageTOM.getContent());
		}
		return new ResponseEntity<List<TraceabilityOfMeat>>(listaTOM,new HttpHeaders(),HttpStatus.OK);
	}

}
