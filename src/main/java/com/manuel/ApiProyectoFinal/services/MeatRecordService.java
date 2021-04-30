package com.manuel.ApiProyectoFinal.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manuel.ApiProyectoFinal.models.MeatRecord;
import com.manuel.ApiProyectoFinal.repositories.MeatRecordRepository;

@Service
public class MeatRecordService {
	@Autowired
	private MeatRecordRepository MeatRecordrepository;
	
	public MeatRecord createMeatRecord(MeatRecord newMeatRecord){
		newMeatRecord.setProduct(newMeatRecord.getProduct().toUpperCase());
		newMeatRecord.setSupplier(newMeatRecord.getSupplier().toUpperCase());
		return this.MeatRecordrepository.save(newMeatRecord);
	}
	
	public boolean deleteMeatRecord(MeatRecord deleteMeatRecord) {
		
		boolean deleted=false;
		
		Optional<MeatRecord> todeleted=this.MeatRecordrepository.findById(deleteMeatRecord.getId());
		
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

}