package com.sileo.bej.repository;

import com.sileo.bej.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
   Optional<Employee>  findByEmail(String email);
   List<Employee> findByFullNameContainingIgnoreCase(String fullName);



}
