package com.wwwamaurylog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wwwamaurylog.entity.User;
import com.wwwamaurylog.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repository;
	
	public List<User> getUsers() {
		return repository.findAll();
	}
	
	public User SaveUser(User user) {
		return repository.save(user);
	}
	
	public String deleteUser(int id) {
		repository.deleteById(id);
		return "User removed";
	}
	
	public User updateUser(User user) {
		User existingUser = repository.findById(user.getId()).orElse(null);
		existingUser.setName(user.getName());
		existingUser.setEmail(user.getEmail());
		existingUser.setAddress(user.getAddress());
		return repository.save(existingUser);
	}
	
	
}
