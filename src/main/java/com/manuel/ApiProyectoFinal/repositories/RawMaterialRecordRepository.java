package com.manuel.ApiProyectoFinal.repositories;

import java.sql.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.manuel.ApiProyectoFinal.models.RawMaterialRecord;
import com.manuel.ApiProyectoFinal.models.User;

public interface RawMaterialRecordRepository extends JpaRepository<RawMaterialRecord, Long>{
	
	Page<RawMaterialRecord> findByCommodityStartsWithIgnoreCaseAndUser(String commodity,User user,Pageable pageable);
	Page<RawMaterialRecord> findByLoteStartsWithIgnoreCaseAndUser(String lote,User user,Pageable pageable);
	Page<RawMaterialRecord> findBySupplierStartsWithIgnoreCaseAndUser(String supplier,User user,Pageable pageable);
	Page<RawMaterialRecord> findByArrivaldateAndUser(Date arrivaldate,User user,Pageable pageable);
	Page<RawMaterialRecord> findByStartdateAndUser(Date startdate,User user,Pageable pageable);
	Page<RawMaterialRecord> findByEnddateAndUser(Date enddate,User user,Pageable pageable);
	
	Page<RawMaterialRecord> findByUser(User user,Pageable pageable);
}
