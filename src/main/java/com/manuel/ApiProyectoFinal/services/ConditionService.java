package com.manuel.ApiProyectoFinal.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manuel.ApiProyectoFinal.exceptions.ExistingObjectException;
import com.manuel.ApiProyectoFinal.exceptions.RecordNotFoundException;
import com.manuel.ApiProyectoFinal.models.Condition;
import com.manuel.ApiProyectoFinal.repositories.ConditionRepository;

@Service
public class ConditionService {
	
	@Autowired
	private ConditionRepository conditionRepository;
	
	public Condition createCondition(Condition newCondition) {
		if(this.conditionRepository.ExistCondition(newCondition.getName().toUpperCase())>0) {
			throw new ExistingObjectException("There is already a condition similar to the one introduced");
		}else {
			Condition toadd=new Condition();
			toadd.setName(newCondition.getName().toUpperCase());
			return this.conditionRepository.save(toadd);
		}
	}
	
	public Condition updateCondition(Condition updateCondition) {
		Optional<Condition> toupdate=this.conditionRepository.findById(updateCondition.getId());
		if(toupdate.isPresent()) {
			if(this.conditionRepository.ExistCondition(updateCondition.getName().toUpperCase())>0) {
				throw new ExistingObjectException("There is already a condition similar to the one introduced");
			}else {
				Condition upCon=toupdate.get();
				upCon.setName(updateCondition.getName().toUpperCase());
				return this.conditionRepository.save(upCon);
			}
		}else {
			throw new RecordNotFoundException("No Condition exist for given id", updateCondition.getId().toString());
		}
	}
	
	public boolean deleteCondition(Long id) {
		Optional<Condition> todelete=this.conditionRepository.findById(id);
		if(todelete.isPresent()){
			this.conditionRepository.delete(todelete.get());
			return true;
		}else {
			return false;
		}
	}
	
	public List<Condition> getAll(){
		return this.conditionRepository.findAll();
	}
}
