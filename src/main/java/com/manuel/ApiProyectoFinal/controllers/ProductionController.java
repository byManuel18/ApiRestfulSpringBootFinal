package com.manuel.ApiProyectoFinal.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.manuel.ApiProyectoFinal.models.Production;
import com.manuel.ApiProyectoFinal.services.ProductionService;

@RestController
@RequestMapping("/production")
@CrossOrigin(origins = "*",maxAge = 3600)
public class ProductionController {
	
	@Autowired
	ProductionService productionService;
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@PostMapping()
	public ResponseEntity<Production> createProduction(@Valid @RequestBody Production newProduction){
		return new ResponseEntity<Production>(this.productionService.createProduction(newProduction),new HttpHeaders(),HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@PutMapping()
	public ResponseEntity<Production> updateProduction(@Valid @RequestBody Production updateProduction){
		return new ResponseEntity<Production>(this.productionService.updateProduction(updateProduction),new HttpHeaders(),HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> deleteProduction(@PathVariable("id") Long id){
		return new ResponseEntity<Boolean>(this.productionService.deleteProduction(id),new HttpHeaders(),HttpStatus.OK);
	}
}
