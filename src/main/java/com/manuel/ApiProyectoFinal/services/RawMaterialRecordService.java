package com.manuel.ApiProyectoFinal.services;

import java.sql.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.manuel.ApiProyectoFinal.enums.SearchByRawMaterialRecord;
import com.manuel.ApiProyectoFinal.models.RawMaterialRecord;
import com.manuel.ApiProyectoFinal.models.User;
import com.manuel.ApiProyectoFinal.repositories.RawMaterialRecordRepository;
import com.manuel.ApiProyectoFinal.repositories.UserRepository;

@Service
public class RawMaterialRecordService {
	
	@Autowired
	private RawMaterialRecordRepository rawMaterialRecordRepository;
	@Autowired
	private UserRepository userRepository;
	
	public RawMaterialRecord createRawMaterialRecord(RawMaterialRecord newRawMaterial) {
			
		RawMaterialRecord toadd=new RawMaterialRecord();
		toadd.setArrival_date(newRawMaterial.getArrival_date());
		toadd.setCommodity(newRawMaterial.getCommodity().toUpperCase());
		toadd.setEnd_date(newRawMaterial.getEnd_date());
		toadd.setLote(newRawMaterial.getLote());
		toadd.setSigned(newRawMaterial.getSigned());
		toadd.setStart_date(newRawMaterial.getStart_date());
		toadd.setUser(newRawMaterial.getUser());
		toadd.setSupplier(newRawMaterial.getSupplier().toUpperCase());
		return this.rawMaterialRecordRepository.save(toadd);
		
	}
	
	public RawMaterialRecord updateRawMaterialRecord(RawMaterialRecord updateRawMaterial) {
		Optional<RawMaterialRecord> toupdate=this.rawMaterialRecordRepository.findById(updateRawMaterial.getId());
		if(toupdate.isPresent()) {
			RawMaterialRecord upt=toupdate.get();
			upt.setArrival_date(updateRawMaterial.getArrival_date());
			upt.setCommodity(updateRawMaterial.getCommodity().toUpperCase());
			upt.setEnd_date(updateRawMaterial.getEnd_date());
			upt.setLote(updateRawMaterial.getLote());
			upt.setSigned(updateRawMaterial.getSigned());
			upt.setStart_date(updateRawMaterial.getStart_date());
			upt.setUser(updateRawMaterial.getUser());
			upt.setSupplier(updateRawMaterial.getSupplier().toUpperCase());
			return this.rawMaterialRecordRepository.save(upt);
		}else {
			return null;
		}
	}
	
	public boolean deleteRawMaterialRecord(Long id) {
		
		Optional<RawMaterialRecord> todelete=this.rawMaterialRecordRepository.findById(id);
		
		if(todelete.isPresent()) {
			this.rawMaterialRecordRepository.delete(todelete.get());
			return true;
		}else {
			return false;
		}
	}
	
	public Integer getPages(String uid,int size,String search,SearchByRawMaterialRecord caseSearch) {
		Optional<User> getUser=this.userRepository.findById(uid);
		if(getUser.isPresent()) {
			Pageable pageable=PageRequest.of(0, size);
			Page<RawMaterialRecord> page=null;
			Date date=null;
			switch (caseSearch) {
			case ARRIVAL_DATE:
				date= Date.valueOf(search);
				page=this.rawMaterialRecordRepository.findByArrivaldateAndUser(date, getUser.get(), pageable);
				break;
			case COMMODITY:
				page=this.rawMaterialRecordRepository.findByCommodityStartsWithIgnoreCaseAndUser(search, getUser.get(), pageable);
				break;
			case END_DATE:
				date= Date.valueOf(search);
				page=this.rawMaterialRecordRepository.findByEnddateAndUser(date, getUser.get(), pageable);
				break;
			case LOTE:
				page=this.rawMaterialRecordRepository.findByLoteStartsWithIgnoreCaseAndUser(search, getUser.get(), pageable);
				break;
			case START_DATE:
				date = Date.valueOf(search);
				page=this.rawMaterialRecordRepository.findByStartdateAndUser(date,getUser.get(), pageable);
				break;
			case SUPPLIER:
				page=this.rawMaterialRecordRepository.findBySupplierStartsWithIgnoreCaseAndUser(search, getUser.get(), pageable);
				break;
			default:
				page=this.rawMaterialRecordRepository.findByUser(getUser.get(), pageable);
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
	
	public Page<RawMaterialRecord> getAllBy(String uid,String search,SearchByRawMaterialRecord caseSearch,Pageable pageable){
		
		Optional<User> getUser=this.userRepository.findById(uid);
		if(getUser.isPresent()) {
			Page<RawMaterialRecord> page=null;
			Date date=null;
			switch (caseSearch) {
				case ARRIVAL_DATE:
					date= Date.valueOf(search);
					page=this.rawMaterialRecordRepository.findByArrivaldateAndUser(date, getUser.get(), pageable);
					break;
				case COMMODITY:
					page=this.rawMaterialRecordRepository.findByCommodityStartsWithIgnoreCaseAndUser(search, getUser.get(), pageable);
					break;
				case END_DATE:
					date= Date.valueOf(search);
					page=this.rawMaterialRecordRepository.findByEnddateAndUser(date, getUser.get(), pageable);
					break;
				case LOTE:
					page=this.rawMaterialRecordRepository.findByLoteStartsWithIgnoreCaseAndUser(search, getUser.get(), pageable);
					break;
				case START_DATE:
					date = Date.valueOf(search);
					page=this.rawMaterialRecordRepository.findByStartdateAndUser(date,getUser.get(), pageable);
					break;
				case SUPPLIER:
					page=this.rawMaterialRecordRepository.findBySupplierStartsWithIgnoreCaseAndUser(search, getUser.get(), pageable);
					break;
				default:
					page=this.rawMaterialRecordRepository.findByUser(getUser.get(), pageable);
					break;
			}
			
			return page;
		}else {
			return null;
		}
		
	}
	
	
}
