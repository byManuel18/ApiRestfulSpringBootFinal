package com.manuel.ApiProyectoFinal.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.manuel.ApiProyectoFinal.enums.AscDesc;
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
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@GetMapping("/getPages/{uid}/{size}")
	public ResponseEntity<Integer> getPages(@PathVariable("uid") String uid,@PathVariable("size") int size, @RequestParam(name = "product", defaultValue = "") String product){
		Integer np=this.MeatRecordservice.getPages(uid, size, product);
		return new ResponseEntity<Integer>(np,new HttpHeaders(),HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@GetMapping("/getBy/{uid}/{page}/{size}")
	public ResponseEntity<List<MeatRecord>> getAllBy(@PathVariable("uid") String uid,@PathVariable("size") int size,@PathVariable("page") int page ,@RequestParam(name = "product", defaultValue = "") String product
			,@RequestParam(name = "order",defaultValue = "ASCENDING") AscDesc order){
		Pageable pageable=null;
		if(order==AscDesc.ASCENDING) {
			pageable=PageRequest.of(page, size,Sort.by("product").ascending());
		}else {
			pageable=PageRequest.of(page, size,Sort.by("product").descending());
		}
		Page<MeatRecord> pageList=this.MeatRecordservice.getAllBy(product, uid, pageable);
		List<MeatRecord> lista=new ArrayList<MeatRecord>();
		if(pageList!=null) {
			lista.addAll(pageList.getContent());
		}
		return new ResponseEntity<List<MeatRecord>>(lista,new HttpHeaders(),HttpStatus.OK);
	}
	
	
	
	

}
