package com.manuel.ApiProyectoFinal.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manuel.ApiProyectoFinal.models.Signed;
import com.manuel.ApiProyectoFinal.repositories.SignedRepository;

@Service
public class SignedService {
	
	@Autowired
	SignedRepository signedRepo;
	
	public Signed CreateSigned(Signed news) {
		Signed toadd=new Signed(news.getId(),news.getName().toUpperCase());
		if(this.signedRepo.ExistSigned(toadd.getName().toUpperCase())>0) {
			return null;
		}else {
			return this.signedRepo.save(toadd);
		}
		
	}
	
	public boolean UpdateSigned(Signed uppSig) {
		if(this.signedRepo.ExistSigned(uppSig.getName().toUpperCase())>0) {
			return false;
		}else {
			Signed toUpdate=new Signed(uppSig.getId(),uppSig.getName().toUpperCase());
			this.signedRepo.save(toUpdate);
			return true;
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
