package com.apiRest.repository;

import com.apiRest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
  List<User> findByActive(boolean active);

  List<User> findByNameContainingIgnoreCase(String name);
  
  List<User> findByEmailContainingIgnoreCase(String email);
}
