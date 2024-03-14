package com.blogger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogger.model.User;
import com.blogger.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private MyUtility myUtility;
	
	
	public boolean checkEmail(String email) {
		if(userRepository.existsByEmail(email))
			return true;
		return false;
	}
	
	public User saveUser(User user) {
		return userRepository.save(user);
		
	}
	
	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	public Integer authenticateUser(String email ,String password) {
		User user=null;
		if(userRepository.existsByEmail(email)) {
			user = userRepository.findByEmail(email);
			if(myUtility.verifyPassword(user.getPassword(),password)) {
				return user.getUserId();
			}
		}
		return null;
	}
	public User getUserById(Integer userId) {
		return userRepository.findById(userId).get();
	}
	
}
