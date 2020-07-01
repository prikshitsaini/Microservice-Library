package com.assignment.microservices.userservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assignment.microservices.userservice.bean.User;
import com.assignment.microservices.userservice.dao.UserRepository;
import com.assignment.microservices.userservice.exceptions.BaseException;
import com.assignment.microservices.userservice.exceptions.UserNotFoundException;

@Service
public class UserService implements IUserService {

	@Autowired
	private UserRepository repository;

	@Override
	public User getUserDetail(Integer id) {
		if (id == null) {
			throw new BaseException(" Request param can't be null . " + id);
		}

		return repository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("No user found for user id :" + String.valueOf(id)));

	}

	@Override
	public boolean checkUserExist(int id) {
		return repository.existsById(id);
	}

	@Override
	public List<User> getUsers() {
		return repository.findAll();
	}

	@Override
	public boolean addUser(User user) {
		List<User> list = repository.findByFirstNameAndLastNameAndContactNo(user.getFirstName(), user.getLastName(),
				user.getContactNo());
		if (list.size() > 0) {
			return false;
		} else {
			repository.save(user);
			return true;
		}
	}
}
