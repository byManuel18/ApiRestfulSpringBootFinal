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

import com.manuel.ApiProyectoFinal.models.Appliance;
import com.manuel.ApiProyectoFinal.services.ApplianceService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/appliance")
@Api(tags = "APARATO")
@CrossOrigin(origins = "*",maxAge = 3600)
public class ApplianceController {
	
	@Autowired
	ApplianceService applianceService;
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@GetMapping("/getAll/{uid}")
	public ResponseEntity<List<Appliance>> getAll(@PathVariable("uid") String uid){
		return new ResponseEntity<List<Appliance>>(this.applianceService.getAllAppliance(uid),new HttpHeaders(),HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@PostMapping()
	public ResponseEntity<Appliance> createAppliance(@Valid @RequestBody Appliance newAppliance){
		return new ResponseEntity<Appliance>(this.applianceService.createAppliance(newAppliance),new HttpHeaders(),HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@PutMapping()
	public ResponseEntity<Appliance> updateAppliance(@Valid @RequestBody Appliance updateAppliance){
		return new ResponseEntity<Appliance>(this.applianceService.updateAppliance(updateAppliance),new HttpHeaders(),HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> deleteAppliance(@PathVariable("id") Long id){
		return new ResponseEntity<Boolean>(this.applianceService.deleteAppliance(id),new HttpHeaders(),HttpStatus.OK);
	}
}
