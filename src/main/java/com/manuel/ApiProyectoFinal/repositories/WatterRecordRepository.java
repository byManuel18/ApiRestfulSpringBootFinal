package com.manuel.ApiProyectoFinal.repositories;

import java.sql.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.manuel.ApiProyectoFinal.models.User;
import com.manuel.ApiProyectoFinal.models.WatterRecord;

public interface WatterRecordRepository extends JpaRepository<WatterRecord, Long> {
	
	@Query(value="SELECT COUNT(*) FROM watterrecord WHERE date=?1 AND id_user=?2 AND samplingpoint=?3",
			nativeQuery = true)
	int ExistWatterRecord(Date date, String uid,String samplingpoint);
	
	Page<WatterRecord> findByUser(User user, Pageable pageable); 
	Page<WatterRecord> findByDateAndUser(Date date,User user,Pageable pageable);
	Page<WatterRecord> findBySamplingpointStartsWithIgnoreCaseAndUser(String samplingpoint,User user,Pageable pageable);
	
}
