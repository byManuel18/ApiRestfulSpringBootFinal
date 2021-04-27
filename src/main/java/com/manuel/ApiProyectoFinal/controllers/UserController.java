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
import com.manuel.ApiProyectoFinal.enums.SearchByUsers;
import com.manuel.ApiProyectoFinal.models.User;
import com.manuel.ApiProyectoFinal.services.UserService;


@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*",maxAge = 3600)
public class UserController {
	@Autowired
	UserService userService;
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@GetMapping("/getTotalPages/{size}")
	public ResponseEntity<Integer> getTotalPages(@PathVariable("size") int size){
		return new ResponseEntity<Integer>(this.userService.getTotalPages(size),new HttpHeaders(),HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@GetMapping("/{uid}")
	public ResponseEntity<User> getUserByUid(@PathVariable("uid") String uid){
		User selected=this.userService.getUserByUid(uid);
		return new ResponseEntity<User>(selected,new HttpHeaders(),HttpStatus.OK);
	}

	@CrossOrigin(origins = "*",maxAge = 3600)
	@GetMapping("/getAllandSearch/{page}/{size}")
	public ResponseEntity<List<User>> getAllUserByandOrder(@PathVariable("page") int page,@PathVariable("size") int size,@RequestParam(name = "order",defaultValue = "ASCENDING") AscDesc order,
			@RequestParam(name="name",defaultValue ="") String name,@RequestParam(name="email",defaultValue ="") String email,@RequestParam(name="uid",defaultValue ="") String uid
			,@RequestParam(name="phone",defaultValue ="") String phone){
		Pageable p=null;
		SearchByUsers search=SearchByUsers.name;
		String characters="";
		if(order==AscDesc.ASCENDING) {
			if(!name.equals("")) {
				p=PageRequest.of(page,size,Sort.by("name").ascending());
				characters=name;
			}else if(!email.equals("")) {
				p=PageRequest.of(page,size,Sort.by("gmail").ascending());
				search=SearchByUsers.email;
				characters=email;
			}else if(!phone.equals("")) {
				p=PageRequest.of(page,size,Sort.by("phone").ascending());
				search=SearchByUsers.phone;
				characters=phone;
			}else if(!uid.equals("")) {
				p=PageRequest.of(page,size,Sort.by("uid").ascending());
				search=SearchByUsers.uid;
				characters=uid;
			}else {
				p=PageRequest.of(page,size,Sort.by("name").ascending());
			}
			
		}else if(order==AscDesc.DESCENDING) {
			if(!name.equals("")) {
				p=PageRequest.of(page,size,Sort.by("name").descending());
				characters=name;
			}else if(!email.equals("")) {
				p=PageRequest.of(page,size,Sort.by("gmail").descending());
				search=SearchByUsers.email;
				characters=email;
			}else if(!phone.equals("")) {
				p=PageRequest.of(page,size,Sort.by("phone").descending());
				search=SearchByUsers.phone;
				characters=phone;
			}else if(!uid.equals("")) {
				p=PageRequest.of(page,size,Sort.by("uid").descending());
				search=SearchByUsers.uid;
				characters=uid;
			}else {
				p=PageRequest.of(page,size,Sort.by("name").descending());
			}
		}
		Page<User> pa=this.userService.getAllUserWith(p,search,characters);
		List<User> li=pa.getContent();
		return new ResponseEntity<List<User>>(li,new HttpHeaders(),HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@PostMapping("/{password}")
	public ResponseEntity<User> createUSer(@Valid @RequestBody User u,@PathVariable("password") String password){
		User created= null;
		created =this.userService.cretateUser(u,password);
		return new ResponseEntity<User>(created,new HttpHeaders(), HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@DeleteMapping("/{uid}")
	public ResponseEntity<Boolean> deleteUser(@PathVariable("uid") String uid){
		
		if(this.userService.deleteUser(uid)) {
			
			return new ResponseEntity<Boolean>(true,new HttpHeaders(),HttpStatus.OK);
		}else {
			
			return new ResponseEntity<Boolean>(false,new HttpHeaders(),HttpStatus.OK);
		}
		
	}
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@PutMapping("/activate/{uid}")
	public ResponseEntity<Boolean> activateUser(@PathVariable("uid") String uid){
		boolean change=this.userService.activateUser(uid);
		return new ResponseEntity<Boolean>(change,new HttpHeaders(),HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@PutMapping("/disable/{uid}")
	public ResponseEntity<Boolean> disableUser(@PathVariable("uid") String uid){
		boolean change=this.userService.disabledUser(uid);
		return new ResponseEntity<Boolean>(change,new HttpHeaders(),HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@PutMapping("/{password}")
	public ResponseEntity<Boolean> updateUser(@Valid @RequestBody User u,@PathVariable("password") String password){
		boolean updated=this.userService.updateUser(u, password);
		return new ResponseEntity<Boolean>(updated,new HttpHeaders(),HttpStatus.OK);
	}
	
	@GetMapping("/exist/{email}")
	public ResponseEntity<Boolean> existEmail(@PathVariable("email") String email){
		boolean exist=this.userService.existEmail(email);
		return new ResponseEntity<Boolean>(exist,new HttpHeaders(),HttpStatus.OK);
		
	}

}
