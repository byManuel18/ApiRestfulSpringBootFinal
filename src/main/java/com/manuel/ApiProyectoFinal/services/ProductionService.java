package com.manuel.ApiProyectoFinal.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manuel.ApiProyectoFinal.models.Production;
import com.manuel.ApiProyectoFinal.repositories.ProductionRepository;

@Service
public class ProductionService {
	
	@Autowired
	private ProductionRepository productionrepository;
	
	public Production createProduction(Production newProduction) {
		Production toadd=new Production();
		toadd.setAmount(newProduction.getAmount());
		toadd.setDate(newProduction.getDate());
		toadd.setProduct(newProduction.getProduct().toUpperCase());
		toadd.setSigned(newProduction.getSigned());
		toadd.setUser(newProduction.getUser());
		toadd.setListRawMaterialRecord(newProduction.getListRawMaterialRecord());
		toadd.setListTraceabilityOfMeat(newProduction.getListTraceabilityOfMeat());
		System.out.println(toadd);
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
			return null;
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
}
