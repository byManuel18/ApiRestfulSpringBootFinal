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
import com.manuel.ApiProyectoFinal.models.Appliance;
import com.manuel.ApiProyectoFinal.services.ApplianceService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/appliance")
@Api(tags = "APARATO")
@CrossOrigin(origins = "*",maxAge = 3600)
public class ApplianceController {
	
	@Autowired
	ApplianceService applianceService;
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@GetMapping("/getAll/{uid}")
	@ApiOperation(value = "Muestra todos los Aparatos",notes = "Muestra los Aparatos según el Usuario introducido")
	public ResponseEntity<List<Appliance>> getAll(@PathVariable("uid") String uid){
		return new ResponseEntity<List<Appliance>>(this.applianceService.getAllAppliance(uid),new HttpHeaders(),HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@PostMapping()
	@ApiOperation(value = "Crea un Aparato",notes = "Crea un Aparato para un Usuario devolviendo el Aparato con su id creada. Lanza excepción si ya existe un aparato similar")
	public ResponseEntity<Appliance> createAppliance(@Valid @RequestBody Appliance newAppliance) throws ExistingObjectException{
		return new ResponseEntity<Appliance>(this.applianceService.createAppliance(newAppliance),new HttpHeaders(),HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@PutMapping()
	@ApiOperation(value = "Actualiza un Aparato",notes = "Actualiza un Aparato para un Usuario devolviendo el Aparato actualizado. Lanza excepción si ya existe un aparato similar o si no se ha encontrado")
	public ResponseEntity<Appliance> updateAppliance(@Valid @RequestBody Appliance updateAppliance) throws ExistingObjectException,RecordNotFoundException{
		return new ResponseEntity<Appliance>(this.applianceService.updateAppliance(updateAppliance),new HttpHeaders(),HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Elimina un Aparato",notes = "Elimina un aparato devolviendo TRUE si lo ha eliminado y FALSE si no lo ha encontrado")
	public ResponseEntity<Boolean> deleteAppliance(@PathVariable("id") Long id){
		return new ResponseEntity<Boolean>(this.applianceService.deleteAppliance(id),new HttpHeaders(),HttpStatus.OK);
	}
}
