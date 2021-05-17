package com.manuel.ApiProyectoFinal.services;

import java.sql.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.manuel.ApiProyectoFinal.enums.SearchByTraceabilityOfMeat;
import com.manuel.ApiProyectoFinal.models.TraceabilityOfMeat;
import com.manuel.ApiProyectoFinal.models.User;
import com.manuel.ApiProyectoFinal.repositories.TraceabilityOfMeatReppository;
import com.manuel.ApiProyectoFinal.repositories.UserRepository;

@Service
public class TraceabilityOfMeatService {
	
	@Autowired
	private TraceabilityOfMeatReppository traceabilityOfMeatRepository;
	@Autowired
	private UserRepository userRepository;
	
	public TraceabilityOfMeat createTraceabilityOfMeat(TraceabilityOfMeat newTraceabilityOfMeat){
		if(this.traceabilityOfMeatRepository.ExistTraceabilityOfMeat(newTraceabilityOfMeat.getUser().getUid(), 
				newTraceabilityOfMeat.getMeatrecord().getId())>0) {
			return null;
		}else {
			TraceabilityOfMeat toadd=new TraceabilityOfMeat();
			toadd.setArrivaldate(newTraceabilityOfMeat.getMeatrecord().getDate());
			toadd.setEnddate(newTraceabilityOfMeat.getEnddate());
			toadd.setMeatrecord(newTraceabilityOfMeat.getMeatrecord());
			toadd.setSigned(newTraceabilityOfMeat.getSigned());
			toadd.setStartdate(newTraceabilityOfMeat.getStartdate());
			toadd.setUser(newTraceabilityOfMeat.getUser());
	
			return this.traceabilityOfMeatRepository.save(toadd);
		}
	}
	
	public TraceabilityOfMeat updateTraceabilityOfMeat(TraceabilityOfMeat updateTraceabilityOfMeat) {
		Optional<TraceabilityOfMeat> toUpdate=this.traceabilityOfMeatRepository.findById(updateTraceabilityOfMeat.getId());
		if(toUpdate.isPresent()) {
			if(this.traceabilityOfMeatRepository.ExistTraceabilityOfMeatUpdate(updateTraceabilityOfMeat.getUser().getUid(), 
					updateTraceabilityOfMeat.getMeatrecord().getId(),updateTraceabilityOfMeat.getId())>0) {
				return null;
			}else {
				TraceabilityOfMeat update=toUpdate.get();
				update.setArrivaldate(updateTraceabilityOfMeat.getMeatrecord().getDate());
				update.setEnddate(updateTraceabilityOfMeat.getEnddate());
				update.setMeatrecord(updateTraceabilityOfMeat.getMeatrecord());
				update.setSigned(updateTraceabilityOfMeat.getSigned());
				update.setStartdate(updateTraceabilityOfMeat.getStartdate());
				update.setUser(updateTraceabilityOfMeat.getUser());
				
				return this.traceabilityOfMeatRepository.save(update);
			}
		}else {
			return null;
		}
	}
	
	public boolean deleteTraceabilityOfMeatRepository(Long id) {
		Optional<TraceabilityOfMeat> todelete=this.traceabilityOfMeatRepository.findById(id);
		if(todelete.isPresent()) {
			this.traceabilityOfMeatRepository.deleteProductionTrazMeat(id);
			this.traceabilityOfMeatRepository.delete(todelete.get());
			return true;
		}else {
			return false;
		}
	}
	
	public Integer getPages(String uid,String search,int size,SearchByTraceabilityOfMeat caseSearch) {
		Optional<User> getUser=this.userRepository.findById(uid);
		if(getUser.isPresent()) {
			Pageable pageable=PageRequest.of(0, size);
			Page<TraceabilityOfMeat> page=null;
			Date date=null;
			switch (caseSearch) {
			case ARRIVAL_DATE:
					date=Date.valueOf(search);
					page=this.traceabilityOfMeatRepository.findByArrivaldateAndUser(date, getUser.get(), pageable);
				break;
				
			case END_DATE:
					date=Date.valueOf(search);
					page=this.traceabilityOfMeatRepository.findByEnddateAndUser(date, getUser.get(), pageable);
				break;
			case START_DATE:
					date=Date.valueOf(search);
					page=this.traceabilityOfMeatRepository.findByStartdateAndUser(date, getUser.get(), pageable);
				break;
			default:
					page=this.traceabilityOfMeatRepository.findByUser(getUser.get(), pageable);
				break;
			}
			if(page!=null) {
				return page.getTotalPages();
			}else {
				return 0;
			}
		}else {
			return 0;
		}
	}
	
	public Page<TraceabilityOfMeat> getAllBy(String uid,String search,SearchByTraceabilityOfMeat caseSearch,Pageable pageable){
		Optional<User> getUser=this.userRepository.findById(uid);
		if(getUser.isPresent()) {
			Page<TraceabilityOfMeat> page=null;
			Date date=null;
			
			switch (caseSearch) {
			case ARRIVAL_DATE:
					date=Date.valueOf(search);
					page=this.traceabilityOfMeatRepository.findByArrivaldateAndUser(date, getUser.get(), pageable);
				break;
				
			case END_DATE:
					date=Date.valueOf(search);
					page=this.traceabilityOfMeatRepository.findByEnddateAndUser(date, getUser.get(), pageable);
				break;
			case START_DATE:
					date=Date.valueOf(search);
					page=this.traceabilityOfMeatRepository.findByStartdateAndUser(date, getUser.get(), pageable);
				break;
			default:
					page=this.traceabilityOfMeatRepository.findByUser(getUser.get(), pageable);
				break;
			}
			return page;
		}else {
			return null;
		}
	}
}
