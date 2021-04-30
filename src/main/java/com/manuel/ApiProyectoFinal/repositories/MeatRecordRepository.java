package com.manuel.ApiProyectoFinal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;


import com.manuel.ApiProyectoFinal.models.MeatRecord;

public interface MeatRecordRepository extends JpaRepository<MeatRecord,Long>{
	
	
}
