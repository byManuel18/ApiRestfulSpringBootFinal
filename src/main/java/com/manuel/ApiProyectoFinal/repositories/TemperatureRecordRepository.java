package com.manuel.ApiProyectoFinal.repositories;

import java.sql.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.manuel.ApiProyectoFinal.models.Appliance;
import com.manuel.ApiProyectoFinal.models.TemperatureRecord;
import com.manuel.ApiProyectoFinal.models.User;

public interface TemperatureRecordRepository extends JpaRepository<TemperatureRecord, Long>{
	
	Page<TemperatureRecord> findByApplianceAndUser(Appliance appliance,User user,Pageable pageable);
	Page<TemperatureRecord> findByUser(User user,Pageable pageable);
	Page<TemperatureRecord> findByDateAndUser(Date date,User user,Pageable pageable);
	@Query(value="SELECT COUNT(*) FROM temperaturerecord WHERE date=?1 AND id_user=?2 AND id_appliance",
			nativeQuery = true)
	int ExisttemperatureRecordCreate(Date date,String id_user,Long id_appliance);
	@Query(value="SELECT COUNT(*) FROM temperaturerecord WHERE date=?1 AND id_user=?2 AND id_appliance AND id!=id",
			nativeQuery = true)
	int ExisttemperatureRecordUpdate(Date date,String id_user,Long id_appliance,Long id);
}
