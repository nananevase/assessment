package com.example.product.config;

import org.springframework.security.core.AuthenticationException;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface UnauthorizedHandler {
  void handle(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex) throws IOException, ServletException;
}