package com.manuel.ApiProyectoFinal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.manuel.ApiProyectoFinal.models.Condition;

public interface ConditionRepository extends JpaRepository<Condition, Long>{
	
	@Query(value="SELECT COUNT(*) FROM condition WHERE name =?1",
			nativeQuery = true)
	int ExistCondition(String name);
}
