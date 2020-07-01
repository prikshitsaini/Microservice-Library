package com.assignment.microservices.userservice.service;

import java.util.List;
import java.util.Optional;

import com.assignment.microservices.userservice.bean.User;
import com.assignment.microservices.userservice.exceptions.BaseException;
import com.assignment.microservices.userservice.exceptions.UserNotFoundException;

public interface IUserService {
	
	User getUserDetail(Integer id) throws UserNotFoundException,BaseException ;
	
	boolean checkUserExist(int id);
	
	boolean addUser(User user);
	
	List<User> getUsers();
	
}
