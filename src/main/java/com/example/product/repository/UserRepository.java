package com.example.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.product.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
  User findByUsername(String username);
}