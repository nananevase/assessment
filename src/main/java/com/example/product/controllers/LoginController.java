package com.example.product.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.product.config.JwtTokenProvider;
import com.example.product.config.UserPrincipal;
import com.example.product.pojo.LoginRequest;
import com.example.product.pojo.LoginResponse;
import com.example.product.repository.UserRepository;
import com.example.product.services.UserDetailsImpl;
import com.example.product.services.UserDetailsServiceImpl;


@RestController
@RequestMapping("/api/auth")
public class LoginController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtTokenProvider jwtTokenProvider;
  
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserDetailsServiceImpl userDetailsService;

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
    Authentication authentication = authenticate(loginRequest.getUsername(), loginRequest.getPassword());
    
    
    UserPrincipal userDetails =(UserPrincipal)  authentication.getPrincipal();
    UserDetailsImpl user = (UserDetailsImpl) userDetailsService.loadUserByUsername(userDetails.getUsername());
    if (user == null) {
      throw new UsernameNotFoundException("User not found with username: " + userDetails.getUsername());
    }
    
    
    String token = jwtTokenProvider.generateToken(user.getUser());
    return ResponseEntity.ok(new LoginResponse(token));
  }

  private Authentication authenticate(String username, String password) {
	UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(username);
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    return authenticationManager.authenticate(authenticationToken);
  }
}