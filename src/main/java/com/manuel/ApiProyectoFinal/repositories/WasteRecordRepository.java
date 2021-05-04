package com.manuel.ApiProyectoFinal.repositories;

import java.sql.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.manuel.ApiProyectoFinal.models.User;
import com.manuel.ApiProyectoFinal.models.WasteRecord;

public interface WasteRecordRepository extends JpaRepository<WasteRecord, Long>{
	
	@Query(value="SELECT COUNT(*) FROM wasterecord WHERE date=?1 AND id_user=?2",
			nativeQuery = true)
	int ExistWasteRecord(Date date, String uid);
	Page<WasteRecord> findByUser(User user, Pageable pageable);
	Page<WasteRecord> findByDateAndUser(Date date,User user,Pageable pageable);
	Page<WasteRecord> findByPersonStartsWithIgnoreCaseAndUser(String person,User user,Pageable pageable);

}
