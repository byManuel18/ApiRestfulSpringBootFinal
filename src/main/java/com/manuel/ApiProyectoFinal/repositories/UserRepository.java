package com.manuel.ApiProyectoFinal.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import com.manuel.ApiProyectoFinal.models.User;

public interface UserRepository extends JpaRepository<User, String>{
	
	Page<User> findByNameStartsWithIgnoreCase(String name,Pageable pageable);
	Page<User> findByGmailStartsWithIgnoreCase(String gmail,Pageable pageable);
	Page<User> findByPhoneStartsWithIgnoreCase(String phone,Pageable pageable);
	Page<User> findByUidStartsWithIgnoreCase(String uid,Pageable pageable);

}
