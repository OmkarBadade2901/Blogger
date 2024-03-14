package com.blogger.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogger.model.User;



public interface UserRepository extends JpaRepository<User, Integer>{

	public boolean existsByEmail(String email);
	
	public User findByEmail(String email);
}
