package com.manuel.ApiProyectoFinal.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.manuel.ApiProyectoFinal.models.User;

public interface UserRepository extends JpaRepository<User, String>{
	@Query(value = "SELECT * FROM User_ WHERE lower(name) LIKE lower('?1%')"
			,nativeQuery = true)
	Page<User> findByNameUser(String name,Pageable pageable);
	@Query(value = "SELECT * FROM User_ WHERE lower(gmail) LIKE lower('?1%')"
			,nativeQuery = true)
	Page<User> findByGmailUser(String gmail,Pageable pageable);
	Page<User> findByPhoneStartsWith(String phone,Pageable pageable);
	@Query(value = "SELECT * FROM User_ WHERE lower(uid) LIKE lower('?1%')"
			,nativeQuery = true)
	Page<User> findByUidUser(String uid,Pageable pageable);

}
