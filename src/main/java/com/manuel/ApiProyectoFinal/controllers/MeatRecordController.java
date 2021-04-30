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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manuel.ApiProyectoFinal.models.MeatRecord;
import com.manuel.ApiProyectoFinal.services.MeatRecordService;

@RestController
@RequestMapping("/meatrecord")
@CrossOrigin(origins = "*",maxAge = 3600)
public class MeatRecordController {
	
	@Autowired
	MeatRecordService MeatRecordservice;
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@GetMapping()
	public ResponseEntity<List<MeatRecord>> getAll(){
		return new ResponseEntity<List<MeatRecord>>(this.MeatRecordservice.getAllMeatRecord(),new HttpHeaders(),HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@PostMapping()
	public ResponseEntity<MeatRecord> createMeatRecord(@Valid @RequestBody MeatRecord newMeatRecord){
		
		return new ResponseEntity<MeatRecord>(this.MeatRecordservice.createMeatRecord(newMeatRecord),new HttpHeaders(),HttpStatus.OK);	
		
	}
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> deleteMeatrecord(@PathVariable("id") Long id){
		return new ResponseEntity<Boolean>(this.MeatRecordservice.deleteMeatRecord(id),new HttpHeaders(),HttpStatus.OK); 
	}
	

}
