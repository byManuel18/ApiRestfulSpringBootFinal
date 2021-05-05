package com.manuel.ApiProyectoFinal.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.manuel.ApiProyectoFinal.models.Appliance;
import com.manuel.ApiProyectoFinal.models.User;

public interface ApplianceRepository extends JpaRepository<Appliance, Long>{
	@Query(value="SELECT COUNT(*) FROM appliance WHERE name =?1 AND id_usuario=?2",
			nativeQuery = true)
	int ExistAppliance(String name,String id_usuario);
	
	List<Appliance> findByUser(User user);
}
