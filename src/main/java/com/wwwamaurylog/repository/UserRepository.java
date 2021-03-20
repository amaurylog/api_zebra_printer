package com.wwwamaurylog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wwwamaurylog.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	
	User findByName(String name);

}
