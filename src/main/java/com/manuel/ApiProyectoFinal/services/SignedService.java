package com.manuel.ApiProyectoFinal.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manuel.ApiProyectoFinal.exceptions.ExistingObjectException;
import com.manuel.ApiProyectoFinal.exceptions.RecordNotFoundException;
import com.manuel.ApiProyectoFinal.models.Signed;
import com.manuel.ApiProyectoFinal.repositories.SignedRepository;

@Service
public class SignedService {
	
	@Autowired
	SignedRepository signedRepo;
	
	public Signed CreateSigned(Signed news) {
		Signed toadd=new Signed(news.getId(),news.getName().toUpperCase());
		if(this.signedRepo.ExistSigned(toadd.getName().toUpperCase())>0) {
			throw new ExistingObjectException("There is already a signed similar to the one introduced");
		}else {
			return this.signedRepo.save(toadd);
		}
		
	}
	
	public Signed UpdateSigned(Signed uppSig) {
		
		Optional<Signed> toupdate=this.signedRepo.findById(uppSig.getId());
		if(toupdate.isPresent()) {
			if(this.signedRepo.ExistSigned(uppSig.getName().toUpperCase())>0) {
				throw new ExistingObjectException("There is already a signed similar to the one introduced");
			}else {
				Signed toUpdate=toupdate.get();
				toUpdate.setName(uppSig.getName().toUpperCase());
				return this.signedRepo.save(toUpdate);
				
			}
		}else {
			throw new RecordNotFoundException("No Signed exist for given id",uppSig.getId().toString());
		}
	}
	
	public boolean DeleteSigned(Long id) {
		
		Optional<Signed> toDelete=this.signedRepo.findById(id);
		
		if(toDelete.isPresent()) {
			this.signedRepo.delete(toDelete.get());
			return true;
		}else {
			return false;
		}
		
	}
	
	public List<Signed> getAllSigned(){
		return this.signedRepo.findAll();
	}
}
