package com.manuel.ApiProyectoFinal.controllers;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;

import com.manuel.ApiProyectoFinal.exceptions.ExistingObjectException;
import com.manuel.ApiProyectoFinal.exceptions.RecordNotFoundException;
import com.manuel.ApiProyectoFinal.models.Condition;
import com.manuel.ApiProyectoFinal.services.ConditionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/condition")
@Api(tags = "CONDICION")
@CrossOrigin(origins = "*",maxAge = 3600)
public class ConditionController {
	
	@Autowired
	ConditionService conditionService;
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@GetMapping("/getAll")
	@ApiOperation(value = "Muestra todas las Condiciones",notes = "")
	public ResponseEntity<List<Condition>> getAll(){
		return new ResponseEntity<List<Condition>>(this.conditionService.getAll(),new HttpHeaders(),HttpStatus.OK);
				
	}
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@PostMapping()
	@ApiOperation(value = "Crear una Condición",notes = "Crear una condición devolviendo la Condición creada con su id. Lanza excepción si existe una Condición similar")
	public ResponseEntity<Condition> createCondition(@Valid @RequestBody Condition newCondition) throws ExistingObjectException{
		return new ResponseEntity<Condition>(this.conditionService.createCondition(newCondition),new HttpHeaders(),HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@PutMapping()
	@ApiOperation(value = "Actualiza una Condición",notes = "Actualiza una condición devolviendo la Condición actualizada. Lanza excepción si existe una Condición similar o si no se ha encontrado")
	public ResponseEntity<Condition> updateCondition(@Valid @RequestBody Condition updateCondition) throws ExistingObjectException,RecordNotFoundException{
		return new ResponseEntity<Condition>(this.conditionService.updateCondition(updateCondition),new HttpHeaders(),HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Elimina una Condición",notes = "Elimina una condición devolviendo TRUE si lo ha eliminado y FALSE si no lo ha encontrado")
	public ResponseEntity<Boolean> deleteCondition(@PathVariable("id") Long id){
		return new ResponseEntity<Boolean>(this.conditionService.deleteCondition(id),new HttpHeaders(),HttpStatus.OK);
	}
	
	
}
