package com.manuel.ApiProyectoFinal.services;

import java.sql.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.manuel.ApiProyectoFinal.enums.WatterRecordSearch;
import com.manuel.ApiProyectoFinal.models.User;
import com.manuel.ApiProyectoFinal.models.WatterRecord;
import com.manuel.ApiProyectoFinal.repositories.UserRepository;
import com.manuel.ApiProyectoFinal.repositories.WatterRecordRepository;

@Service
public class WatterRecordService {
	
	@Autowired
	private WatterRecordRepository watterRecordRepository;
	@Autowired
	private UserRepository userRepository;
	
	public WatterRecord createWatterRecord(WatterRecord newWatterRecord) {
		if(this.watterRecordRepository.ExistWatterRecord(newWatterRecord.getDate(),newWatterRecord.getUser().getUid(),newWatterRecord.getSamplingpoint().toUpperCase())>0) {
			return null;
		}else {
			WatterRecord toadd=new WatterRecord();
			toadd.setCondition(newWatterRecord.getCondition());
			toadd.setDate(newWatterRecord.getDate());
			toadd.setOrganoleptic_control(newWatterRecord.getOrganoleptic_control());
			toadd.setSamplingpoint(newWatterRecord.getSamplingpoint().toUpperCase());
			toadd.setSigned(newWatterRecord.getSigned());
			toadd.setUser(newWatterRecord.getUser());
			return this.watterRecordRepository.save(toadd);
		}
	}
	
	public WatterRecord updateWatterRecord(WatterRecord updateWatterRecord) {
		Optional<WatterRecord> toupdate=this.watterRecordRepository.findById(updateWatterRecord.getId());
		
		if(toupdate.isPresent()) {
			if(this.watterRecordRepository.ExistWatterRecordUpdate(updateWatterRecord.getDate(),updateWatterRecord.getUser().getUid(),updateWatterRecord.getSamplingpoint().toUpperCase(),updateWatterRecord.getId())>0) {
				return null;
			}else {
				WatterRecord upWatt=toupdate.get();
				upWatt.setCondition(updateWatterRecord.getCondition());
				upWatt.setDate(updateWatterRecord.getDate());
				upWatt.setOrganoleptic_control(updateWatterRecord.getOrganoleptic_control());
				upWatt.setSamplingpoint(updateWatterRecord.getSamplingpoint().toUpperCase());
				upWatt.setSigned(updateWatterRecord.getSigned());
				upWatt.setUser(updateWatterRecord.getUser());
				return this.watterRecordRepository.save(upWatt);
			}
			/**/
		}else {
			return null;
		}
	}
	
	public boolean deleteWatterRecord(Long id) {
		Optional<WatterRecord> toupdate=this.watterRecordRepository.findById(id);
		if(toupdate.isPresent()) {
			this.watterRecordRepository.delete(toupdate.get());
			return true;
		}else {
			return false;
		}
	}
	
	public Integer getPages(String uid,String search,WatterRecordSearch caseSearch,int size) {
		
		Optional<User> user=this.userRepository.findById(uid);
		
		if(user.isPresent()) {
			Pageable pageable=PageRequest.of(0, size);
			Page<WatterRecord> page=null;
			switch (caseSearch) {
				case DATE:
						Date date=Date.valueOf(search);
						page=this.watterRecordRepository.findByDateAndUser(date, user.get(),pageable);
					break;
				case SAMPLING_POINT:
						page=this.watterRecordRepository.findBySamplingpointStartsWithIgnoreCaseAndUser(search, user.get(), pageable);
					break;
	
				default:
						page=this.watterRecordRepository.findByUser(user.get(),pageable);
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
	
	public Page<WatterRecord> getAllBy(String search,String uid,WatterRecordSearch caseSearch,Pageable pageable){
		Optional<User> user=this.userRepository.findById(uid);
		if(user.isPresent()) {
			Page<WatterRecord> page=null;
			switch (caseSearch) {
			case DATE:
					Date date=Date.valueOf(search);
					page=this.watterRecordRepository.findByDateAndUser(date, user.get(), pageable);
				break;
			case SAMPLING_POINT:
				page=this.watterRecordRepository.findBySamplingpointStartsWithIgnoreCaseAndUser(search, user.get(), pageable);
			break;

			default:
					page=this.watterRecordRepository.findByUser(user.get(), pageable);
				break;
			}
			return page;
		}else {
			return null;
		}
	}
}
