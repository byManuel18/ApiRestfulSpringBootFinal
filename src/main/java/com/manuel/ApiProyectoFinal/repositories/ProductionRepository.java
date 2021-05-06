package com.manuel.ApiProyectoFinal.repositories;

import java.sql.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.manuel.ApiProyectoFinal.models.Production;
import com.manuel.ApiProyectoFinal.models.User;

public interface ProductionRepository extends JpaRepository<Production, Long>{
	
	Page<Production> findByDateAndUser(Date date,User user,Pageable pageable);
	Page<Production> findByUser(User user,Pageable pageable);
	Page<Production> findByProductAndUser(String product, User user,Pageable pageable);
}
