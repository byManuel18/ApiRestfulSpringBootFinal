package com.manuel.ApiProyectoFinal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.manuel.ApiProyectoFinal.models.Signed;

public interface SignedRepository extends JpaRepository<Signed,Long>{
	
	@Query(value="SELECT COUNT(*) FROM signed WHERE name =?1",
			nativeQuery = true)
	int ExistSigned(String name);
}
