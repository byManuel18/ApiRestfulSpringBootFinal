package com.manuel.ApiProyectoFinal.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manuel.ApiProyectoFinal.exceptions.ExistingObjectException;
import com.manuel.ApiProyectoFinal.exceptions.RecordNotFoundException;
import com.manuel.ApiProyectoFinal.models.Appliance;
import com.manuel.ApiProyectoFinal.models.User;
import com.manuel.ApiProyectoFinal.repositories.ApplianceRepository;
import com.manuel.ApiProyectoFinal.repositories.UserRepository;

@Service
public class ApplianceService {
	
	@Autowired
	private ApplianceRepository applianceRepository;
	@Autowired
	private UserRepository userRepository;
	public Appliance createAppliance(Appliance newAppliance) {
		if(this.applianceRepository.ExistAppliance(newAppliance.getName().toUpperCase(),newAppliance.getUser().getUid())>0) {
			throw new ExistingObjectException("There is already a appliance similar to the one introduced");
		}else {
			Appliance toadd=new Appliance();
			toadd.setName(newAppliance.getName().toUpperCase());
			toadd.setUser(newAppliance.getUser());
			return this.applianceRepository.save(toadd);
		}
	}
	
	public Appliance updateAppliance(Appliance updateAppliance) {
			
			Optional<Appliance> toUpdate=this.applianceRepository.findById(updateAppliance.getId());
			if(toUpdate.isPresent()) {
				if(this.applianceRepository.ExistAppliance(updateAppliance.getName().toUpperCase(), updateAppliance.getUser().getUid())>0) {
					throw new ExistingObjectException("There is already a appliance similar to the one introduced");
				}else {
					Appliance toUp=toUpdate.get();
					toUp.setName(updateAppliance.getName().toUpperCase());
					toUp.setUser(updateAppliance.getUser());
					return this.applianceRepository.save(toUp);
				}
			}else {
				throw new RecordNotFoundException("No Apliance exist for given id", updateAppliance.getId().toString());
			}
			
	}
	
	public boolean deleteAppliance(Long id) {
		Optional<Appliance> toDelete=this.applianceRepository.findById(id);
		if(toDelete.isPresent()) {
			this.applianceRepository.delete(toDelete.get());
			return true;
		}else {
			return false;
		}
	}
	
	public List<Appliance> getAllAppliance(String uid){
		Optional<User> user=this.userRepository.findById(uid);
		if(user.isPresent()) {
			return this.applianceRepository.findByUser(user.get());
		}else {
			return new ArrayList<Appliance>();
		}
	}
}
