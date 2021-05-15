package com.manuel.ApiProyectoFinal.services;

import java.sql.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.manuel.ApiProyectoFinal.enums.SearchByTemperatureRecord;
import com.manuel.ApiProyectoFinal.models.Appliance;
import com.manuel.ApiProyectoFinal.models.TemperatureRecord;
import com.manuel.ApiProyectoFinal.models.User;
import com.manuel.ApiProyectoFinal.repositories.ApplianceRepository;
import com.manuel.ApiProyectoFinal.repositories.TemperatureRecordRepository;
import com.manuel.ApiProyectoFinal.repositories.UserRepository;

@Service
public class TemperatureRecordService {
	
	@Autowired
	private TemperatureRecordRepository temperatureRecordRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ApplianceRepository applianceRepository;
	
	public TemperatureRecord createTemperatureRecord(TemperatureRecord newTemperatureRecord) {
		
		if(this.temperatureRecordRepository.ExisttemperatureRecordCreate(newTemperatureRecord.getDate(),newTemperatureRecord.getUser().getUid(),newTemperatureRecord.getAppliance().getId())>0) {
			return null;
		}else {
			TemperatureRecord toadd=new TemperatureRecord();
			toadd.setAppliance(newTemperatureRecord.getAppliance());
			toadd.setDate(newTemperatureRecord.getDate());
			toadd.setSigned(newTemperatureRecord.getSigned());
			toadd.setTemperature(newTemperatureRecord.getTemperature());
			toadd.setUser(newTemperatureRecord.getUser());
			
			return this.temperatureRecordRepository.save(toadd);
		}
		
	}
	
	public TemperatureRecord updateTemperatureRecord(TemperatureRecord updateTemperatureRecord) {
	
		Optional<TemperatureRecord> toupdate=this.temperatureRecordRepository.findById(updateTemperatureRecord.getId());
		if(toupdate.isPresent()) {
			if(this.temperatureRecordRepository.ExisttemperatureRecordUpdate(updateTemperatureRecord.getDate(),updateTemperatureRecord.getUser().getUid(),updateTemperatureRecord.getAppliance().getId(),updateTemperatureRecord.getId())>0) {
				return null;
			}else {
				TemperatureRecord toUp=toupdate.get();
				toUp.setAppliance(updateTemperatureRecord.getAppliance());
				toUp.setDate(updateTemperatureRecord.getDate());
				toUp.setSigned(updateTemperatureRecord.getSigned());
				toUp.setTemperature(updateTemperatureRecord.getTemperature());
				toUp.setUser(updateTemperatureRecord.getUser());
				
				return this.temperatureRecordRepository.save(updateTemperatureRecord);
			}
		}else {
			return null;
		}
	}
	
	public boolean deleteTemperatureRecord(Long id) {
		Optional<TemperatureRecord> todelete=this.temperatureRecordRepository.findById(id);
		if(todelete.isPresent()) {
			this.temperatureRecordRepository.delete(todelete.get());
			return true;
		}else {
			return false;
		}
	}
	
	public Integer getPages(String uid,String search,int size,SearchByTemperatureRecord caseSearch) {
		Optional<User> getUser=this.userRepository.findById(uid);
		if(getUser.isPresent()) {
			Pageable pageable=PageRequest.of(0, size);
			Page<TemperatureRecord> page=null;
			
			switch (caseSearch) {
			case APPLIANCE:
					Long id=Long.valueOf(search);
					Optional<Appliance> getAppliance=this.applianceRepository.findById(id);
					if(getAppliance.isPresent()) {
						page=this.temperatureRecordRepository.findByApplianceAndUser(getAppliance.get(), getUser.get(), pageable);
					}
				break;
			case DATE:
					Date date=Date.valueOf(search);
					page=this.temperatureRecordRepository.findByDateAndUser(date, getUser.get(), pageable);
				break;

			default:
					page=this.temperatureRecordRepository.findByUser(getUser.get(), pageable);
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
	
	public Page<TemperatureRecord> getAllBy(String uid,String search,SearchByTemperatureRecord caseSearch,Pageable pageable){
		Optional<User> getUser=this.userRepository.findById(uid);
		if(getUser.isPresent()) {
			Page<TemperatureRecord> page=null;
			switch (caseSearch) {
			case APPLIANCE:
					Long id=Long.valueOf(search);
					Optional<Appliance> getAppliance=this.applianceRepository.findById(id);
					if(getAppliance.isPresent()) {
						page=this.temperatureRecordRepository.findByApplianceAndUser(getAppliance.get(), getUser.get(), pageable);
					}
				break;
			case DATE:
					Date date=Date.valueOf(search);
					page=this.temperatureRecordRepository.findByDateAndUser(date, getUser.get(), pageable);
				break;

			default:
					page=this.temperatureRecordRepository.findByUser(getUser.get(), pageable);
				break;
			}
			return page;
		}else {
			return null;
		}
		
	}
}
