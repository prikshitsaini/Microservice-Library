package com.assignment.microservices.userservice.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.assignment.microservices.userservice.bean.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
		
	List<User> findByFirstNameAndLastNameAndContactNo(String firstName, String lastName,Long contactNo);
	
}
