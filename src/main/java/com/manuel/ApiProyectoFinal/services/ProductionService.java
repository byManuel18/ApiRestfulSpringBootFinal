package com.manuel.ApiProyectoFinal.services;

import java.sql.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.manuel.ApiProyectoFinal.enums.SearchByProduction;
import com.manuel.ApiProyectoFinal.exceptions.RecordNotFoundException;
import com.manuel.ApiProyectoFinal.models.Production;
import com.manuel.ApiProyectoFinal.models.User;
import com.manuel.ApiProyectoFinal.repositories.ProductionRepository;
import com.manuel.ApiProyectoFinal.repositories.UserRepository;

@Service
public class ProductionService {
	
	@Autowired
	ProductionRepository productionrepository;
	@Autowired 
	UserRepository userRespository;
	
	public Production createProduction(Production newProduction) {
		Production toadd=new Production();
		toadd.setAmount(newProduction.getAmount());
		toadd.setDate(newProduction.getDate());
		toadd.setProduct(newProduction.getProduct().toUpperCase());
		toadd.setSigned(newProduction.getSigned());
		toadd.setUser(newProduction.getUser());
		toadd.setListRawMaterialRecord(newProduction.getListRawMaterialRecord());
		toadd.setListTraceabilityOfMeat(newProduction.getListTraceabilityOfMeat());
		return this.productionrepository.save(toadd);
	}
	
	public Production updateProduction(Production updateProduction) {
		Optional<Production> toadd=this.productionrepository.findById(updateProduction.getId());
		if(toadd.isPresent()) {
			Production tosave=toadd.get();
			tosave.setAmount(updateProduction.getAmount());
			tosave.setDate(updateProduction.getDate());
			tosave.setProduct(updateProduction.getProduct().toUpperCase());
			tosave.setSigned(updateProduction.getSigned());
			tosave.setUser(updateProduction.getUser());
			tosave.setListRawMaterialRecord(updateProduction.getListRawMaterialRecord());
			tosave.setListTraceabilityOfMeat(updateProduction.getListTraceabilityOfMeat());
			return this.productionrepository.save(tosave);
		}else {
			throw new RecordNotFoundException("No Production Record exist for given id",updateProduction.getId().toString());
		}
	}
	
	public boolean deleteProduction(Long id) {
		Optional<Production> todelete=this.productionrepository.findById(id);
		
		if(todelete.isPresent()) {
			this.productionrepository.delete(todelete.get());
			return true;
		}else {
			return false;
		}
	}
	
	public Integer getPages(int size, String uid, String search,SearchByProduction caseSearch) {
		
		Optional<User> getUser=this.userRespository.findById(uid);
		if(getUser.isPresent()) {
			Pageable pageable =PageRequest.of(0, size);
			Page<Production> page= null;
			
			switch (caseSearch) {
			case DATE:
					Date date=Date.valueOf(search);
					page=this.productionrepository.findByDateAndUser(date, getUser.get(), pageable);
					
				break;
			case PRODUCT:
					page=this.productionrepository.findByProductStartsWithIgnoreCaseAndUser(search, getUser.get(), pageable);
				break;

			default:
					page=this.productionrepository.findByUser(getUser.get(), pageable);
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
	
	public Page<Production> getAllBy(String uid,Pageable pageable,SearchByProduction caseSearch,String search){
		Optional<User> getUser=this.userRespository.findById(uid);
		if(getUser.isPresent()) {
			Page<Production> page=null;
			
			switch (caseSearch) {
			case DATE:
					Date date=Date.valueOf(search);
					page=this.productionrepository.findByDateAndUser(date, getUser.get(), pageable);
					
				break;
			case PRODUCT:
					page=this.productionrepository.findByProductStartsWithIgnoreCaseAndUser(search, getUser.get(), pageable);
				break;

			default:
					page=this.productionrepository.findByUser(getUser.get(), pageable);
				break;
			}
			
			return page;
			
		}else {
			return null;
		}
	}
}
