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
import com.manuel.ApiProyectoFinal.enums.SearchByWasteRecord;
import com.manuel.ApiProyectoFinal.models.WasteRecord;
import com.manuel.ApiProyectoFinal.services.WasteRecordService;

@RestController
@RequestMapping("/wasterecord")
@CrossOrigin(origins = "*",maxAge = 3600)
public class WasteRecordController {
	
	@Autowired
	WasteRecordService wasteRecordService;
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@PostMapping()
	public ResponseEntity<WasteRecord> createWasteRecord(@Valid @RequestBody WasteRecord newWasteRecord){
		return new ResponseEntity<WasteRecord>(this.wasteRecordService.createWasteRecord(newWasteRecord),new HttpHeaders(),HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@PutMapping()
	public ResponseEntity<WasteRecord> updateWasteRecord(@Valid @RequestBody WasteRecord updateWasteRecord){
		return new ResponseEntity<WasteRecord>(this.wasteRecordService.updateWasteRecord(updateWasteRecord),new HttpHeaders(),HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> deleteWasteRecord(@PathVariable("id") Long id ){
		return new ResponseEntity<Boolean>(this.wasteRecordService.deleteWasteRecord(id),new HttpHeaders(),HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@GetMapping("/getPages/{uid}/{size}")
	public ResponseEntity<Integer> getPages(@PathVariable("uid") String uid, @PathVariable("size") int size,
			@RequestParam(name = "date", defaultValue ="") String date,@RequestParam(name = "person", defaultValue ="") String person){
		
		SearchByWasteRecord caseSearch=SearchByWasteRecord.NO;
		String cadena="";
		if(!person.equals("")) {
			cadena=person;
			caseSearch=SearchByWasteRecord.PERSON;
		}else if(!date.equals("")) {
			cadena=date;
			caseSearch=SearchByWasteRecord.DATE;
		}
		
		Integer totalpages=this.wasteRecordService.getPages(uid, cadena, size, caseSearch);
		
		return new ResponseEntity<Integer>(totalpages,new HttpHeaders(),HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@GetMapping("/getBy/{uid}/{page}/{size}")
	public ResponseEntity<List<WasteRecord>> getAllBy(@PathVariable("uid") String uid, @PathVariable("size") int size,
			@RequestParam(name = "date", defaultValue ="") String date,@RequestParam(name = "person", defaultValue ="") String person,
			@PathVariable("page")int page,@RequestParam(name = "order",defaultValue = "ASCENDING") AscDesc order){
		String cadena="";
		Pageable pageable=null;
		SearchByWasteRecord caseSearch=SearchByWasteRecord.NO;
		Page<WasteRecord> pageList=null;
		List<WasteRecord> listWasteRecord=new ArrayList<WasteRecord>();
		
		if(!person.equals("")) {
			cadena=person;
			caseSearch=SearchByWasteRecord.PERSON;
			if(order==AscDesc.ASCENDING) {
				pageable=PageRequest.of(page, size,Sort.by("person").ascending());
			}else {
				pageable=PageRequest.of(page, size,Sort.by("person").descending());
			}
		}else if(!date.equals("")) {
			cadena=date;
			caseSearch=SearchByWasteRecord.DATE;
			if(order==AscDesc.ASCENDING) {
				pageable=PageRequest.of(page, size,Sort.by("date").ascending());
			}else {
				pageable=PageRequest.of(page, size,Sort.by("date").descending());
			}
		}else {
			if(order==AscDesc.ASCENDING) {
				pageable=PageRequest.of(page, size,Sort.by("date").ascending());
			}else {
				pageable=PageRequest.of(page, size,Sort.by("date").descending());
			}
		}
		
		pageList=this.wasteRecordService.getAllBy(cadena, uid, caseSearch, pageable);
		if(pageList!=null) {
			listWasteRecord.addAll(pageList.getContent());
		}
		
		return null;
	}
	
}
