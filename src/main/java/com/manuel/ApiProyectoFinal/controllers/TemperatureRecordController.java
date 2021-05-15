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
import com.manuel.ApiProyectoFinal.enums.SearchByTemperatureRecord;
import com.manuel.ApiProyectoFinal.models.TemperatureRecord;
import com.manuel.ApiProyectoFinal.services.TemperatureRecordService;

@RestController
@RequestMapping("/temperaturerecord")
@CrossOrigin(origins = "*",maxAge = 3600)
public class TemperatureRecordController {
	
	@Autowired
	TemperatureRecordService temperatureRecordService;
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@PostMapping()
	public ResponseEntity<TemperatureRecord> createTemperatureRecord(@Valid @RequestBody TemperatureRecord newTemperatureRecord){
		
		return new ResponseEntity<TemperatureRecord>(this.temperatureRecordService.createTemperatureRecord(newTemperatureRecord),new HttpHeaders(),HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@PutMapping()
	public ResponseEntity<TemperatureRecord> updateTemperatureRecord(@Valid @RequestBody TemperatureRecord updateTemperatureRecord){
		System.out.println(updateTemperatureRecord);
		//return new ResponseEntity<TemperatureRecord>(this.temperatureRecordService.updateTemperatureRecord(updateTemperatureRecord),new HttpHeaders(),HttpStatus.OK);
		return new ResponseEntity<TemperatureRecord>(updateTemperatureRecord,new HttpHeaders(),HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> deleteTemperatureRecord(@PathVariable("id") Long id){
		return new ResponseEntity<Boolean>(this.temperatureRecordService.deleteTemperatureRecord(id),new HttpHeaders(),HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@GetMapping("/getPages/{uid}/{size}")
	public ResponseEntity<Integer> getPages(@PathVariable("uid") String uid,@PathVariable("size") int size,
			@RequestParam(name = "date", defaultValue ="") String date,@RequestParam(name = "appliance", defaultValue ="") String appliance){
		
		String cadena="";
		SearchByTemperatureRecord searchCase=SearchByTemperatureRecord.NO;
		if(!date.equals("")){
			cadena=date;
			searchCase=SearchByTemperatureRecord.DATE;
		}else if(!appliance.equals("")) {
			searchCase=SearchByTemperatureRecord.APPLIANCE;
			cadena=appliance;
		}
		Integer totalpages=this.temperatureRecordService.getPages(uid, cadena, size, searchCase);
		
		return new ResponseEntity<Integer>(totalpages,new HttpHeaders(),HttpStatus.OK);
		
	}
	
	@CrossOrigin(origins = "*",maxAge = 3600)
	@GetMapping("/getBy/{uid}/{page}/{size}")
	public ResponseEntity<List<TemperatureRecord>> getAllBy(@PathVariable("uid") String uid,@PathVariable("size") int size,@PathVariable("page") int page,
			@RequestParam(name = "date", defaultValue ="") String date,@RequestParam(name = "appliance", defaultValue ="") String appliance,
			@RequestParam(name = "order",defaultValue = "ASCENDING") AscDesc order) {
		
		String cadena="";
		Page<TemperatureRecord> pageList=null;
		List<TemperatureRecord> listatemp=new ArrayList<TemperatureRecord>();
		Pageable pageable=null;
		SearchByTemperatureRecord caseSearch=SearchByTemperatureRecord.NO;
		
		if(!date.equals("")) {
			cadena=date;
			caseSearch=SearchByTemperatureRecord.DATE;
		
		}else if(!appliance.equals("")) {
			cadena=appliance;
			caseSearch=SearchByTemperatureRecord.APPLIANCE;

		}
		if(order==AscDesc.ASCENDING) {
			pageable=PageRequest.of(page, size,Sort.by("date").ascending());
		}else {
			pageable=PageRequest.of(page, size,Sort.by("date").descending());
		}
		
		pageList=this.temperatureRecordService.getAllBy(uid, cadena, caseSearch, pageable);
		if(pageList!=null) {
			listatemp.addAll(pageList.getContent());
		}
		return new ResponseEntity<List<TemperatureRecord>>(listatemp,new HttpHeaders(),HttpStatus.OK);
	}
	
	
	
	
}
