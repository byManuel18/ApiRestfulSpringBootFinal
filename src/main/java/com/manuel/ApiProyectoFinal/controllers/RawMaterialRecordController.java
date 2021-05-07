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
import com.manuel.ApiProyectoFinal.enums.SearchByRawMaterialRecord;
import com.manuel.ApiProyectoFinal.models.RawMaterialRecord;
import com.manuel.ApiProyectoFinal.services.RawMaterialRecordService;

@RestController
@RequestMapping("/rawmaterialrecord")
@CrossOrigin(origins = "*",maxAge = 3600)
public class RawMaterialRecordController {
	
	@Autowired
	private RawMaterialRecordService rawMaterialRecordService;
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@GetMapping("/getPages/{uid}/{size}")
	public ResponseEntity<Integer> getPages(@PathVariable("uid") String uid,@PathVariable("size") int size, @RequestParam(name = "lote", defaultValue ="") String lote,
			@RequestParam(name = "commodity", defaultValue ="") String commodity,@RequestParam(name = "supplier", defaultValue ="") String supplier,
			@RequestParam(name = "arrival_date", defaultValue ="") String arrival_date,@RequestParam(name = "start_date", defaultValue ="") String start_date,
			@RequestParam(name = "end_date", defaultValue ="") String end_date){
		String cadena="";
		SearchByRawMaterialRecord searchCase=SearchByRawMaterialRecord.NO;
		
		if(!lote.equals("")) {
			cadena=lote;
			searchCase=SearchByRawMaterialRecord.LOTE;
		}else if(!commodity.equals("")) {
			cadena=commodity;
			searchCase=SearchByRawMaterialRecord.COMMODITY;
		}else if(!supplier.equals("")) {
			cadena=supplier;
			searchCase=SearchByRawMaterialRecord.SUPPLIER;
		}else if(!arrival_date.equals("")){
			cadena=arrival_date;
			searchCase=SearchByRawMaterialRecord.ARRIVAL_DATE;
		}else if(!end_date.equals("")) {
			cadena=end_date;
			searchCase=SearchByRawMaterialRecord.END_DATE;
		}else if(!start_date.equals("")) {
			cadena=start_date;
			searchCase=SearchByRawMaterialRecord.START_DATE;
		}
		Integer totalpages=this.rawMaterialRecordService.getPages(uid, size, cadena, searchCase);
		
		return new ResponseEntity<Integer>(totalpages,new HttpHeaders(),HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@GetMapping("/getBy/{uid}/{page}/{size}")
	public ResponseEntity<List<RawMaterialRecord>> getAllBy(@PathVariable("uid") String uid,@PathVariable("size") int size,@PathVariable("page") int page,@RequestParam(name = "lote", defaultValue ="") String lote,
			@RequestParam(name = "commodity", defaultValue ="") String commodity,@RequestParam(name = "supplier", defaultValue ="") String supplier,
			@RequestParam(name = "arrival_date", defaultValue ="") String arrival_date,@RequestParam(name = "start_date", defaultValue ="") String start_date,
			@RequestParam(name = "end_date", defaultValue ="") String end_date,@RequestParam(name = "order",defaultValue = "ASCENDING") AscDesc order ){
		String cadena="";
		Pageable pageable=null;
		SearchByRawMaterialRecord searchCase=SearchByRawMaterialRecord.NO;
		List<RawMaterialRecord> lista=new ArrayList<RawMaterialRecord>();
		Page<RawMaterialRecord> pageList=null;
		if(!lote.equals("")) {
			cadena=lote;
			searchCase=SearchByRawMaterialRecord.LOTE;
			if(order==AscDesc.ASCENDING) {
				pageable=PageRequest.of(page, size,Sort.by("lote").ascending());
			}else {
				pageable=PageRequest.of(page, size,Sort.by("lote").descending());
			}
		}else if(!commodity.equals("")) {
			cadena=commodity;
			searchCase=SearchByRawMaterialRecord.COMMODITY;
			if(order==AscDesc.ASCENDING) {
				pageable=PageRequest.of(page, size,Sort.by("commodity").ascending());
			}else {
				pageable=PageRequest.of(page, size,Sort.by("commodity").descending());
			}
		}else if(!supplier.equals("")) {
			cadena=supplier;
			searchCase=SearchByRawMaterialRecord.SUPPLIER;
			if(order==AscDesc.ASCENDING) {
				pageable=PageRequest.of(page, size,Sort.by("supplier").ascending());
			}else {
				pageable=PageRequest.of(page, size,Sort.by("supplier").descending());
			}
		}else if(!arrival_date.equals("")){
			cadena=arrival_date;
			searchCase=SearchByRawMaterialRecord.ARRIVAL_DATE;
			if(order==AscDesc.ASCENDING) {
				pageable=PageRequest.of(page, size,Sort.by("arrivaldate").ascending());
			}else {
				pageable=PageRequest.of(page, size,Sort.by("arrivaldate").descending());
			}
		}else if(!end_date.equals("")) {
			cadena=end_date;
			searchCase=SearchByRawMaterialRecord.END_DATE;
			if(order==AscDesc.ASCENDING) {
				pageable=PageRequest.of(page, size,Sort.by("enddate").ascending());
			}else {
				pageable=PageRequest.of(page, size,Sort.by("enddate").descending());
			}
		}else if(!start_date.equals("")) {
			cadena=start_date;
			searchCase=SearchByRawMaterialRecord.START_DATE;
			if(order==AscDesc.ASCENDING) {
				pageable=PageRequest.of(page, size,Sort.by("startdate").ascending());
			}else {
				pageable=PageRequest.of(page, size,Sort.by("startdate").descending());
			}
		}else {
			if(order==AscDesc.ASCENDING) {
				pageable=PageRequest.of(page, size,Sort.by("arrivaldate").ascending());
			}else {
				pageable=PageRequest.of(page, size,Sort.by("arrivaldate").descending());
			}
		}
		pageList=this.rawMaterialRecordService.getAllBy(uid, cadena, searchCase, pageable);
		if(pageList!=null) {
			lista.addAll(pageList.getContent());
		}
		return new ResponseEntity<List<RawMaterialRecord>>(lista,new HttpHeaders(),HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@PostMapping()
	public ResponseEntity<RawMaterialRecord> createRawMaterialRecord(@Valid @RequestBody RawMaterialRecord newRawMaterialRecord){
		return new ResponseEntity<RawMaterialRecord>(this.rawMaterialRecordService.createRawMaterialRecord(newRawMaterialRecord),new HttpHeaders(),HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@PutMapping()
	public ResponseEntity<RawMaterialRecord> updateRawMaterialRecord(@Valid @RequestBody RawMaterialRecord updateRawMaterialRecord){
		return new ResponseEntity<RawMaterialRecord>(this.rawMaterialRecordService.updateRawMaterialRecord(updateRawMaterialRecord),new HttpHeaders(),HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> deleteRawMaterialRecord(@PathVariable("id") Long id){
		boolean deleted=this.rawMaterialRecordService.deleteRawMaterialRecord(id);
		return new ResponseEntity<Boolean>(deleted,new HttpHeaders(),HttpStatus.OK);
	}
	
	
	
	
	
	

}
