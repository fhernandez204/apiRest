package com.apiRest.repository;

import com.apiRest.model.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhoneRepository extends JpaRepository<Phone, Long> {

  List<Phone> findByNumberContainingIgnoreCase(String number);
}
