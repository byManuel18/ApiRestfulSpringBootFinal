package com.manuel.ApiProyectoFinal.services;

import java.sql.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.manuel.ApiProyectoFinal.enums.SearchByWasteRecord;
import com.manuel.ApiProyectoFinal.exceptions.ExistingObjectException;
import com.manuel.ApiProyectoFinal.exceptions.RecordNotFoundException;
import com.manuel.ApiProyectoFinal.models.User;
import com.manuel.ApiProyectoFinal.models.WasteRecord;
import com.manuel.ApiProyectoFinal.repositories.UserRepository;
import com.manuel.ApiProyectoFinal.repositories.WasteRecordRepository;

@Service
public class WasteRecordService {
	
	@Autowired
	private WasteRecordRepository wasteRecordRepository;
	@Autowired
	private UserRepository userRepository;
	
	public WasteRecord createWasteRecord(WasteRecord newWasteRecord) {
		
		if(this.wasteRecordRepository.ExistWasteRecord(newWasteRecord.getDate(), newWasteRecord.getUser().getUid())>0){
			
			throw new ExistingObjectException("There is already a signed similar to the one introduced");
			
		}else {
			WasteRecord tocreate=new WasteRecord();
			tocreate.setAmount(newWasteRecord.getAmount());
			tocreate.setDate(newWasteRecord.getDate());
			tocreate.setPerson(newWasteRecord.getPerson().toUpperCase());
			tocreate.setSigned(newWasteRecord.getSigned());
			tocreate.setUser(newWasteRecord.getUser());
			return this.wasteRecordRepository.save(tocreate);
		}
	}
	
	public WasteRecord updateWasteRecord(WasteRecord updateWasteRecord) {
		Optional<WasteRecord> toUpdate=this.wasteRecordRepository.findById(updateWasteRecord.getId());
		
		if(toUpdate.isPresent()){
			if(this.wasteRecordRepository.ExistWasteRecordUpdate(updateWasteRecord.getDate(), updateWasteRecord.getUser().getUid(),updateWasteRecord.getId())>0) {
				throw new ExistingObjectException("There is already a signed similar to the one introduced");
			}else {
				WasteRecord toUp=toUpdate.get();
				toUp.setAmount(updateWasteRecord.getAmount());
				toUp.setDate(updateWasteRecord.getDate());
				toUp.setPerson(updateWasteRecord.getPerson().toUpperCase());
				toUp.setSigned(updateWasteRecord.getSigned());
				toUp.setUser(updateWasteRecord.getUser());
				return this.wasteRecordRepository.save(toUp);
			}
			
		}else {
			throw new RecordNotFoundException("No Waste Record exist for given id",updateWasteRecord.getId().toString());
		}
	}
	
	public boolean deleteWasteRecord(Long id) {
		Optional<WasteRecord> todelete=this.wasteRecordRepository.findById(id);
		if(todelete.isPresent()) {
			this.wasteRecordRepository.delete(todelete.get());
			return true;
		}else {
			return false;
		}
	}
	
	public Integer getPages(String uid,String search,int size,SearchByWasteRecord caseSearch){
		
		Optional<User> getUser=this.userRepository.findById(uid);
		if(getUser.isPresent()) {
			Pageable pageable=PageRequest.of(0, size);
			Page<WasteRecord> page=null;
			switch (caseSearch) {
			case DATE:
					Date date=Date.valueOf(search);
					page=this.wasteRecordRepository.findByDateAndUser(date, getUser.get(), pageable);
				break;
			case PERSON:
					page=this.wasteRecordRepository.findByPersonStartsWithIgnoreCaseAndUser(search, getUser.get(), pageable);
				break;
			default:
				page=this.wasteRecordRepository.findByUser(getUser.get(), pageable);
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
	
	public Page<WasteRecord> getAllBy(String search,String uid,SearchByWasteRecord caseSearch,Pageable pageable){
		Optional<User> user=this.userRepository.findById(uid);
		if(user.isPresent()) {
			Page<WasteRecord> page=null;
			switch (caseSearch) {
			case DATE:
					Date date=Date.valueOf(search);
					page=this.wasteRecordRepository.findByDateAndUser(date, user.get(), pageable);
				break;
			case PERSON:
					page=this.wasteRecordRepository.findByPersonStartsWithIgnoreCaseAndUser(search, user.get(), pageable);
				break;
				
			default:
					page=this.wasteRecordRepository.findByUser(user.get(), pageable);
				break;
			}
			return page;
		}else {
			return null;
		}
	}
}
