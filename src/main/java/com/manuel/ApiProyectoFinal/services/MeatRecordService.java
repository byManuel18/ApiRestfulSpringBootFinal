package com.manuel.ApiProyectoFinal.services;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.manuel.ApiProyectoFinal.enums.SearchByMeatRecord;
import com.manuel.ApiProyectoFinal.exceptions.RecordNotFoundException;
import com.manuel.ApiProyectoFinal.models.MeatRecord;
import com.manuel.ApiProyectoFinal.models.User;
import com.manuel.ApiProyectoFinal.repositories.MeatRecordRepository;
import com.manuel.ApiProyectoFinal.repositories.UserRepository;

@Service
public class MeatRecordService {
	@Autowired
	private MeatRecordRepository MeatRecordrepository;
	
	@Autowired
	private UserRepository UserRepository;
	
	public MeatRecord createMeatRecord(MeatRecord newMeatRecord){
		MeatRecord toadd=new MeatRecord();
		toadd.setDate(newMeatRecord.getDate());
		toadd.setLote(newMeatRecord.getLote());
		toadd.setProduct(newMeatRecord.getProduct().toUpperCase());
		toadd.setSigned(newMeatRecord.getSigned());
		toadd.setSupplier(newMeatRecord.getSupplier().toUpperCase());
		toadd.setUser(newMeatRecord.getUser());
		return this.MeatRecordrepository.save(toadd);
	}
	
	public boolean deleteMeatRecord(Long id) {
		
		boolean deleted=false;
		
		Optional<MeatRecord> todeleted=this.MeatRecordrepository.findById(id);
		
		if(todeleted.isPresent()) {
			this.MeatRecordrepository.delete(todeleted.get());
			deleted=true;
		}
			
		return deleted;
		
	}
	
	public MeatRecord updateMeatRecord(MeatRecord updateMeatRecord) {
		Optional<MeatRecord> toupdate=this.MeatRecordrepository.findById(updateMeatRecord.getId());
		if(toupdate.isPresent()) {
			MeatRecord update=toupdate.get();
			update.setDate(updateMeatRecord.getDate());
			update.setLote(updateMeatRecord.getLote());
			update.setProduct(updateMeatRecord.getProduct().toUpperCase());
			update.setSigned(updateMeatRecord.getSigned());
			update.setSupplier(updateMeatRecord.getSupplier().toUpperCase());
			update.setUser(updateMeatRecord.getUser());
			return this.MeatRecordrepository.save(update);
		}else {
			throw new RecordNotFoundException("No Meat Record exist for given id",updateMeatRecord.getId().toString());
		}
	}
	
	public List<MeatRecord> getAllMeatRecord(){
		return this.MeatRecordrepository.findAll();
	}
	
	public Page<MeatRecord> getAllBy(String product, String uid,Pageable pageable){
		
		Optional<User> getuser=this.UserRepository.findById(uid);
		if(getuser.isPresent()) {
			if(product!=null&&product!="") {
				Page<MeatRecord> p=this.MeatRecordrepository.findByProductStartsWithIgnoreCaseAndUser(product, getuser.get(), pageable);
				return p;
			}else {
				
				Page<MeatRecord> p=this.MeatRecordrepository.findByUser(getuser.get(), pageable);
				return p;
			}
		}else {
			return null;
		}
	}
	
	public Page<MeatRecord> getAllBy(String search, String uid,Pageable pageable,SearchByMeatRecord caseSearch){
			
			Optional<User> getuser=this.UserRepository.findById(uid);
			if(getuser.isPresent()) {
				Page<MeatRecord> page=null;
				switch (caseSearch) {
				case DATE:
					Date date=Date.valueOf(search);
					page=this.MeatRecordrepository.findByDateAndUser(date, getuser.get(), pageable);
					break;
				case LOTE:
					page=this.MeatRecordrepository.findByLoteStartsWithIgnoreCaseAndUser(search, getuser.get(), pageable);
					break;
				case PRODUCT:
					page=this.MeatRecordrepository.findByProductStartsWithIgnoreCaseAndUser(search, getuser.get(), pageable);
					break;
				case SUPPLIER:
					page=this.MeatRecordrepository.findBySupplierStartsWithIgnoreCaseAndUser(search, getuser.get(), pageable);
					break;
				default:
					page=this.MeatRecordrepository.findByUser(getuser.get(), pageable);
					break;
				}
				return page;
			}else {
				return null;
			}
		}
	
	
	public Integer getPages(String uid,int size,String search,SearchByMeatRecord caseSearch) {
	
		Optional<User> user=this.UserRepository.findById(uid);
		if(user.isPresent()) {
			Pageable pageable=PageRequest.of(0, size);
			Page<MeatRecord> page=null;
			switch (caseSearch) {
			case DATE:
					Date date=Date.valueOf(search);
					page=this.MeatRecordrepository.findByDateAndUser(date, user.get(), pageable);
				break;
			case LOTE:
					page=this.MeatRecordrepository.findByLoteStartsWithIgnoreCaseAndUser(search, user.get(), pageable);
				break;
			case PRODUCT:
					page=this.MeatRecordrepository.findByProductStartsWithIgnoreCaseAndUser(search, user.get(), pageable);
				break;
			case SUPPLIER:
					page=this.MeatRecordrepository.findBySupplierStartsWithIgnoreCaseAndUser(search, user.get(), pageable);
				break;
			default:
					page=this.MeatRecordrepository.findByUser(user.get(), pageable);
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

}
