package com.manuel.ApiProyectoFinal.repositories;

import java.sql.Date;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.manuel.ApiProyectoFinal.models.TraceabilityOfMeat;
import com.manuel.ApiProyectoFinal.models.User;

public interface TraceabilityOfMeatReppository extends JpaRepository<TraceabilityOfMeat, Long>{
	
	@Query(value="SELECT COUNT(*) FROM traceabilityofmeat WHERE id_user=?1 AND id_meatrecord=?2",
			nativeQuery = true)
	int ExistTraceabilityOfMeat(String id_user,Long id_id_meatrecord);
	
	@Query(value="SELECT COUNT(*) FROM traceabilityofmeat WHERE id_user=?1 AND id_meatrecord=?2 AND id!=?3",
			nativeQuery = true)
	int ExistTraceabilityOfMeatUpdate(String id_user,Long id_id_meatrecord,Long id);
	
	Page<TraceabilityOfMeat> findByUser(User user,Pageable pageable);
	Page<TraceabilityOfMeat> findByArrivaldateAndUser(Date arrivaldate,User user,Pageable pageable);
	Page<TraceabilityOfMeat> findByStartdateAndUser(Date startdate,User user,Pageable pageable);
	Page<TraceabilityOfMeat> findByEnddateAndUser(Date enddate,User user,Pageable pageable);
	@Query(value="DELETE FROM production_tracmeat WHERE traceabilityofmeat_id=?1")
	int deleteProductionTrazMeat(Long id_trazOfMeat);
}
