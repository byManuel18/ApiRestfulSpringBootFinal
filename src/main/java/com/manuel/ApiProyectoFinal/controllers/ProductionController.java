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
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.manuel.ApiProyectoFinal.enums.AscDesc;
import com.manuel.ApiProyectoFinal.enums.SearchByProduction;
import com.manuel.ApiProyectoFinal.models.Production;
import com.manuel.ApiProyectoFinal.services.ProductionService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/production")
@Api(tags = "REGISTRO_PRODUCCION")
@CrossOrigin(origins = "*",maxAge = 3600)
public class ProductionController {
	
	@Autowired
	ProductionService productionService;
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@PostMapping()
	public ResponseEntity<Production> createProduction(@Valid @RequestBody Production newProduction){
		System.out.println(newProduction);
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
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@GetMapping("getPages/{uid}/{size}")
	public ResponseEntity<Integer> getPages(@PathVariable("uid") String uid,@PathVariable("size") int size,@RequestParam(name = "date", defaultValue ="") String date,
			@RequestParam(name = "product", defaultValue ="") String product){
		String cadena="";
		SearchByProduction caseSearch=SearchByProduction.NO;
		if(!date.equals(date)){
			cadena=date;
			caseSearch=SearchByProduction.DATE;
		}else if(!product.equals("")) {
			cadena=product;
			caseSearch=SearchByProduction.PRODUCT;
		}
		
		Integer totalpages=this.productionService.getPages(size, uid, cadena, caseSearch);
		
		return new ResponseEntity<Integer>(totalpages,new HttpHeaders(),HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@GetMapping("/getBy/{uid}/{page}/{size}")
	public ResponseEntity<List<Production>> getAllBy(@PathVariable("uid") String uid, @PathVariable("page") int page,@PathVariable("size") int size,
			@RequestParam(name = "order",defaultValue = "ASCENDING") AscDesc order,@RequestParam(name = "date", defaultValue ="") String date,
			@RequestParam(name = "product", defaultValue ="") String product ){
	
		String cadena="";
		SearchByProduction searchCase=SearchByProduction.NO;
		Page<Production> pageList=null;
		List<Production> listProduction=new ArrayList<Production>();
		Pageable pageable=null;
		
		if(order==AscDesc.ASCENDING) {
			pageable=PageRequest.of(page, size,Sort.by("date").ascending());
		}else {
			pageable=PageRequest.of(page, size,Sort.by("date").descending());
		}
		
		if(!date.equals(date)){
			cadena=date;
			searchCase=SearchByProduction.DATE;
		}else if(!product.equals("")) {
			cadena=product;
			searchCase=SearchByProduction.PRODUCT;
		}
		
		
		pageList=this.productionService.getAllBy(uid, pageable, searchCase, cadena);
		
		if(pageList!=null) {
			listProduction.addAll(pageList.getContent());
		}
		
		return new ResponseEntity<List<Production>>(listProduction,new HttpHeaders(),HttpStatus.OK);
	}
}
