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
import com.manuel.ApiProyectoFinal.models.Signed;
import com.manuel.ApiProyectoFinal.services.SignedService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("/signed")
@Api(tags = "FIRMADO")
@CrossOrigin(origins = "*",maxAge = 3600)
public class SignedController {
	
	@Autowired
	SignedService signedServ;
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@GetMapping("/getAll")
	@ApiOperation(value = "Obtiene una Lista de Firmado",notes = "")
	public ResponseEntity<List<Signed>> getAllSigned(){
		
		List<Signed> allSigned=this.signedServ.getAllSigned();
		
		return new ResponseEntity<List<Signed>>(allSigned,new HttpHeaders(),HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@PostMapping()
	@ApiOperation(value = "Crear un Registro de Firmado",notes = "Crear un registro de Firmado devolviendo el Frimado creado con su id. Lanza excepción si existe uno similar")
	public ResponseEntity<Signed> createSigned(@Valid @RequestBody Signed newSig)throws ExistingObjectException{
		
		Signed addedSigned=this.signedServ.CreateSigned(newSig);
		
		return new ResponseEntity<Signed>(addedSigned,new HttpHeaders(),HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@PutMapping()
	@ApiOperation(value = "Actualizar un Registro de Firmado",notes = "Actualizar un registro de Firmado devolviendo el Frimado actualizado. Lanza exepción si existe uno similar o si no existe ")
	public ResponseEntity<Signed> updateSigned(@Valid @RequestBody Signed uppSig)throws ExistingObjectException,RecordNotFoundException{
		return new ResponseEntity<Signed>(this.signedServ.UpdateSigned(uppSig),new HttpHeaders(),HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Eliminar un Firmado",notes = "Elimina un firmado mediante su id. Devuelve FALSE si no lo ha encontrado y TRUE si lo ha eliminado")
	public ResponseEntity<Boolean> deleteSigned(@PathVariable("id") Long id){
		
		boolean deletedSigned=this.signedServ.DeleteSigned(id);
		
		return new ResponseEntity<Boolean>(deletedSigned,new HttpHeaders(),HttpStatus.OK);
		
	}

}
