package com.manuel.ApiProyectoFinal.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import com.manuel.ApiProyectoFinal.models.User;

public interface UserRepository extends JpaRepository<User, String>{
	
	Page<User> findByNameStartsWith(String name,Pageable pageable);
	Page<User> findByGmailStartsWith(String gmail,Pageable pageable);
	Page<User> findByPhoneStartsWith(String phone,Pageable pageable);

}
