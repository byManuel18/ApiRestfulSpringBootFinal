package com.manuel.ApiProyectoFinal.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.manuel.ApiProyectoFinal.models.MeatRecord;
import com.manuel.ApiProyectoFinal.models.User;

public interface MeatRecordRepository extends JpaRepository<MeatRecord,Long>{
	
	Page<MeatRecord> findByProductStartsWithIgnoreCaseAndUser(String product,User user,Pageable pageable);
	Page<MeatRecord> findByUser(User user,Pageable pageable);
}
