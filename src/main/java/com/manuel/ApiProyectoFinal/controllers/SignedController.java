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
import com.manuel.ApiProyectoFinal.models.Signed;
import com.manuel.ApiProyectoFinal.services.SignedService;

import io.swagger.annotations.Api;


@RestController
@RequestMapping("/signed")
@Api(tags = "FIRMADO")
@CrossOrigin(origins = "*",maxAge = 3600)
public class SignedController {
	
	@Autowired
	SignedService signedServ;
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@GetMapping("/getAll")
	public ResponseEntity<List<Signed>> getAllSigned(){
		
		List<Signed> allSigned=this.signedServ.getAllSigned();
		
		return new ResponseEntity<List<Signed>>(allSigned,new HttpHeaders(),HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@PostMapping()
	public ResponseEntity<Signed> createSigned(@Valid @RequestBody Signed newSig){
		
		Signed addedSigned=this.signedServ.CreateSigned(newSig);
		
		return new ResponseEntity<Signed>(addedSigned,new HttpHeaders(),HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@PutMapping()
	public ResponseEntity<Boolean> updateSigned(@Valid @RequestBody Signed uppSig){
		boolean updated=this.signedServ.UpdateSigned(uppSig);
		return new ResponseEntity<Boolean>(updated,new HttpHeaders(),HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> deleteSigned(@PathVariable("id") Long id){
		
		boolean deletedSigned=this.signedServ.DeleteSigned(id);
		
		return new ResponseEntity<Boolean>(deletedSigned,new HttpHeaders(),HttpStatus.OK);
		
	}

}
