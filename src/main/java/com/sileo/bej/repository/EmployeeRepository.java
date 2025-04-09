package com.sileo.bej.repository;

import com.sileo.bej.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    Employee findByEmail(String email);


}
