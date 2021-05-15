package com.manuel.ApiProyectoFinal.repositories;

import java.sql.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.manuel.ApiProyectoFinal.models.Appliance;
import com.manuel.ApiProyectoFinal.models.TemperatureRecord;
import com.manuel.ApiProyectoFinal.models.User;

public interface TemperatureRecordRepository extends JpaRepository<TemperatureRecord,Long>{
	
	Page<TemperatureRecord> findByApplianceAndUser(Appliance appliance,User user,Pageable pageable);
	Page<TemperatureRecord> findByUser(User user,Pageable pageable);
	Page<TemperatureRecord> findByDateAndUser(Date date,User user,Pageable pageable);
	@Query(value="SELECT COUNT(*) FROM temperaturerecord WHERE date=?1 AND id_user=?2 AND id_appliance=?3",
			nativeQuery = true)
	int ExisttemperatureRecordCreate(Date date,String id_user,Long id_appliance);
	@Query(value="SELECT COUNT(*) FROM temperaturerecord WHERE date=?1 AND id_user=?2 AND id_appliance=?3 AND id!=?4",
			nativeQuery = true)
	int ExisttemperatureRecordUpdate(Date date,String id_user,Long id_appliance,Long id);
	@Query(value="UPDATE temperaturerecord SET date=?1, id_appliance=?2, id_signed=?3, temperature=?4 WHERE id!=?5",
			nativeQuery = true)
	int Update(Date date,Long id_appliance,Long id_signed,double temperature,Long id);
	
}
