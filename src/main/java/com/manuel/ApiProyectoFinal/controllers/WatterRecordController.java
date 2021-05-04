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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.manuel.ApiProyectoFinal.enums.AscDesc;
import com.manuel.ApiProyectoFinal.enums.WatterRecordSearch;
import com.manuel.ApiProyectoFinal.models.WatterRecord;
import com.manuel.ApiProyectoFinal.services.WatterRecordService;

@RestController
@RequestMapping("/watterrecord")
@CrossOrigin(origins = "*",maxAge = 3600)
public class WatterRecordController {
	
	@Autowired
	WatterRecordService watterRecordService;
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@PostMapping()
	public ResponseEntity<WatterRecord> createWatterRecord(@Valid @RequestBody WatterRecord newWatterRecord){
		return new ResponseEntity<WatterRecord>(this.watterRecordService.createWatterRecord(newWatterRecord),new HttpHeaders(),HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@PutMapping()
	public ResponseEntity<WatterRecord> updateWatterRecord(@Valid @RequestBody WatterRecord updateWatterRecord){
		return new ResponseEntity<WatterRecord>(this.watterRecordService.updateWatterRecord(updateWatterRecord),new HttpHeaders(),HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> deleteWatterRecord(@PathVariable("id") Long id){
		return new ResponseEntity<Boolean>(this.watterRecordService.deleteWatterRecord(id),new HttpHeaders(),HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@GetMapping("/getPages/{uid}/{size}")
	public ResponseEntity<Integer> getPages(@PathVariable("uid") String uid,@PathVariable("size") int size, @RequestParam(name = "date", defaultValue ="") String date){
		String cadena="";
		WatterRecordSearch caseSearch=WatterRecordSearch.NO;
		
		if(!date.equals("")) {
			cadena =date;
			caseSearch=WatterRecordSearch.DATE;
		}
		
		Integer totalpages=this.watterRecordService.getPages(uid, cadena, caseSearch, size);
		
		return new ResponseEntity<Integer>(totalpages,new  HttpHeaders(),HttpStatus.OK);
		
	}
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@GetMapping("/getBy/{uid}/{page}/{size}")
	public ResponseEntity<List<WatterRecord>> getAllBy(@PathVariable("page") int page,@PathVariable("uid") String uid,@PathVariable("size") int size, @RequestParam(name = "date", defaultValue ="") String date,
			@RequestParam(name = "order",defaultValue = "ASCENDING") AscDesc order){
		String cadena="";
		WatterRecordSearch caseSearch=WatterRecordSearch.NO;
		Pageable pageable=null;
		Page<WatterRecord> pageList=null;
		List<WatterRecord> listWatterRecord=new ArrayList<WatterRecord>();
		
		if(!date.equals("")) {
			cadena=date;
			caseSearch=WatterRecordSearch.DATE;
			
		}
		
		if(order==AscDesc.ASCENDING) {
			pageable=PageRequest.of(page, size,Sort.by("date").ascending());
		}else {
			pageable=PageRequest.of(page, size,Sort.by("date").descending());
		}
		
		pageList=this.watterRecordService.getAllBy(cadena, uid, caseSearch, pageable);
		
		if(pageList!=null){
			listWatterRecord.addAll(pageList.getContent());
		}
		return new ResponseEntity<List<WatterRecord>>(listWatterRecord,new HttpHeaders(),HttpStatus.OK);
	}
}
