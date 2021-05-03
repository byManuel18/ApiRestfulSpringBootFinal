package com.manuel.ApiProyectoFinal.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
			update.setProduct(updateMeatRecord.getProduct().toUpperCase());
			update.setSupplier(updateMeatRecord.getSupplier().toUpperCase());
			return this.MeatRecordrepository.save(update);
		}else {
			return null;
		}
	}
	
	public List<MeatRecord> getAllMeatRecord(){
		return this.MeatRecordrepository.findAll();
	}
	
	public Page<MeatRecord> getAllBy(String product, String uid,Pageable pageable){
		
		Optional<User> getuser=this.UserRepository.findById(uid);
		if(getuser.isPresent()) {
			if(product!=null&&product!="") {
				System.out.println("SI");
				return this.MeatRecordrepository.findByProductStartsWithIgnoreCaseAndUser(product, getuser.get(), pageable);
			}else {
				System.out.println("SI");
				return this.MeatRecordrepository.findByUser(getuser.get(), pageable);
			}
		}else {
			System.out.println("Aquí no plis");
			return null;
		}
	}
	
	public Integer getPages(String uid,int size,String product) {
		Optional<User> user=this.UserRepository.findById(uid);
		if(user.isPresent()) {
			Pageable pageable=PageRequest.of(0, size);
			Page<MeatRecord> page=null;
			if(product!=null&&product!="") {
				return this.MeatRecordrepository.findByUser(user.get(), pageable).getTotalPages();
			}else {
				return this.MeatRecordrepository.findByProductStartsWithIgnoreCaseAndUser(product, user.get(), pageable).getTotalPages();
			}
		}else {
			return 0;
		}
	}

}
