package com.assignment.microservices.userservice.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.microservices.userservice.bean.User;
import com.assignment.microservices.userservice.service.IUserService;

@RestController
public class UserServiceController {

	@Autowired
	IUserService userService;

	@GetMapping("/user/{uid}")
	public User retrieveUserDetails(@PathVariable int uid) {

		return userService.getUserDetail(uid).get();
	}

	@GetMapping("/user/check/{uid}")
	public boolean userExist(@PathVariable int uid) {
		return userService.checkUserExist(uid);
	}
	
	@GetMapping("/users")
	public List<User> retrieveUsers() {
		List<User> users = new ArrayList<User>();
		userService.getUsers().forEach(e -> users.add(e));
		return users;
	}

	@PostMapping("/user/add")
	public ResponseEntity<String> addUser(@Valid @RequestBody User user) {
		boolean flag = userService.addUser(user);
		if (flag == false) {
			return new ResponseEntity<String>("User Already Exits", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<String>("User Created Successfully of id:" + user.getId(), HttpStatus.CREATED);
	}

}
