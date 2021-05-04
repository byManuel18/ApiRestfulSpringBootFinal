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
import com.manuel.ApiProyectoFinal.models.Condition;
import com.manuel.ApiProyectoFinal.services.ConditionService;

@RestController
@RequestMapping("/condition")
@CrossOrigin(origins = "*",maxAge = 3600)
public class ConditionController {
	
	@Autowired
	ConditionService conditionService;
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@GetMapping("/getAll")
	public ResponseEntity<List<Condition>> getAll(){
		return new ResponseEntity<List<Condition>>(this.conditionService.getAll(),new HttpHeaders(),HttpStatus.OK);
				
	}
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@PostMapping()
	public ResponseEntity<Condition> createCondition(@Valid @RequestBody Condition newCondition){
		return new ResponseEntity<Condition>(this.conditionService.createCondition(newCondition),new HttpHeaders(),HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@PutMapping()
	public ResponseEntity<Condition> updateCondition(@Valid @RequestBody Condition updateCondition){
		return new ResponseEntity<Condition>(this.conditionService.updateCondition(updateCondition),new HttpHeaders(),HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> deleteCondition(@PathVariable("id") Long id){
		return new ResponseEntity<Boolean>(this.conditionService.deleteCondition(id),new HttpHeaders(),HttpStatus.OK);
	}
	
	
}
