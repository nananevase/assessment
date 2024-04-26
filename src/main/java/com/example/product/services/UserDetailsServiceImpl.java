package com.example.product.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.product.entity.User;
import com.example.product.repository.UserRepository;

import jakarta.annotation.PostConstruct;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	/*
	 * @PostConstruct public void initUsers() { String username1 = "user1"; String
	 * password1 = "password1"; // Replace with a strong String username2 = "user2";
	 * String password2 = "password2";
	 * 
	 * PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // Create a
	 * password encoder String hashedPassword1 = passwordEncoder.encode(password1);
	 * String hashedPassword2 = passwordEncoder.encode(password2);
	 * 
	 * User user1 = new User(username1, hashedPassword1); User user2 = new
	 * User(username2, hashedPassword2);
	 * 
	 * userRepository.save(user1); userRepository.save(user2); }
	 */

	@Override
	public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		return new UserDetailsImpl(user);
	}
}