package com.manuel.ApiProyectoFinal.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.manuel.ApiProyectoFinal.enums.AscDesc;
import com.manuel.ApiProyectoFinal.models.User;
import com.manuel.ApiProyectoFinal.services.UserService;


@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*",maxAge = 3600)
public class UserController {
	@Autowired
	UserService userService;
	
	@GetMapping("/getTotalPages/{size}")
	public ResponseEntity<Integer> getTotalPages(@PathVariable("size") int size){
		return new ResponseEntity<Integer>(this.userService.getTotalPages(size),new HttpHeaders(),HttpStatus.OK);
	}
	
	@GetMapping("/getUserbyName/{page}/{size}/{name}")
	public ResponseEntity<List<User>> getUserbyName(@PathVariable("page") int page,@PathVariable("size") int size, @PathVariable("name") String name){
		Pageable p=PageRequest.of(page,size);
		Page<User> pa=this.userService.findByName(name,p);
		List<User> li=pa.getContent();
		return new ResponseEntity<List<User>>(li,new HttpHeaders(),HttpStatus.OK);
	}
	
	@GetMapping("/getAll/{page}/{size}")
	public ResponseEntity<List<User>> getAllUser(@PathVariable("page") int page,@PathVariable("size") int size,@RequestParam(name = "order",defaultValue = "ASCENDING") AscDesc order){
		Pageable p=null;
		if(order!=null) {
			if(order==AscDesc.ASCENDING) {
				p=PageRequest.of(page,size,Sort.by("name").ascending());
			}else if(order==AscDesc.DESCENDING) {
				p=PageRequest.of(page,size,Sort.by("name").descending());
			}
		}else {
			p=PageRequest.of(page,size);
		}
		
		Page<User> pa=this.userService.getAllUser(p);
		List<User> li=pa.getContent();
		return new ResponseEntity<List<User>>(li,new HttpHeaders(),HttpStatus.OK);
	}
	
	
	@PostMapping("/{password}")
	public ResponseEntity<User> createUSer(@Valid @RequestBody User u,@PathVariable("password") String password){
		User created= null;
		created =this.userService.cretateUser(u,password);
		return new ResponseEntity<User>(created,new HttpHeaders(), HttpStatus.OK);
	}
	
	@DeleteMapping("/{uid}")
	public ResponseEntity<Boolean> deleteUser(@PathVariable("uid") String uid){
		
		if(this.userService.deleteUser(uid)) {
			
			return new ResponseEntity<Boolean>(true,new HttpHeaders(),HttpStatus.OK);
		}else {
			
			return new ResponseEntity<Boolean>(false,new HttpHeaders(),HttpStatus.OK);
		}
		
	}
	
	@PutMapping("/activate/{uid}")
	public ResponseEntity<Boolean> activateUser(@PathVariable("uid") String uid){
		boolean change=this.userService.activateUser(uid);
		return new ResponseEntity<Boolean>(change,new HttpHeaders(),HttpStatus.OK);
	}
	
	@PutMapping("/disable/{uid}")
	public ResponseEntity<Boolean> disableUser(@PathVariable("uid") String uid){
		boolean change=this.userService.disabledUser(uid);
		return new ResponseEntity<Boolean>(change,new HttpHeaders(),HttpStatus.OK);
	}
	
	@PutMapping("/{password}")
	public ResponseEntity<Boolean> updateUser(@Valid @RequestBody User u,@PathVariable("password") String password){
		boolean updated=this.userService.updateUser(u, password);
		return new ResponseEntity<Boolean>(updated,new HttpHeaders(),HttpStatus.OK);
	}

}
