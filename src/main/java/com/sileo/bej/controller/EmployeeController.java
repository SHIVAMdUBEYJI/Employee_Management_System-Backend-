package com.sileo.bej.controller;

import com.sileo.bej.entity.Employee;
import com.sileo.bej.exception.EmployeeAlreadyExists;
import com.sileo.bej.exception.EmployeeDoesNotExistsException;
import com.sileo.bej.repository.EmployeeRepository;
import com.sileo.bej.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

//@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeRepository employeeRepository;

    @PostMapping("/add")
    public ResponseEntity<?> addEmployee(@RequestBody Employee employee) {
        try {
            Employee newEmployee = employeeService.createEmployee(employee);
            return new ResponseEntity<>(newEmployee, HttpStatus.OK);

        } catch (EmployeeAlreadyExists exception) {
            return new ResponseEntity<>("Employee With this email : " + employee.getEmail() + " already exists!!", HttpStatus.CONFLICT);
        }
    }


    @GetMapping("/getAll")
    public Page<Employee> getAllEmployee(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        return employeeRepository.findAll(pageable);
    }


    @GetMapping("/findById/{id}")
    public Optional<Employee> findById(@PathVariable Long id) {
        return employeeRepository.findById(id);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Employee>> searchEmployees(@RequestParam String fullName) {
        List<Employee> employees = employeeService.searchByFullName(fullName);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Employee employee) {
        try {
            return new ResponseEntity<>(employeeService.updateEmployee(id, employee), HttpStatus.OK);
        } catch (EmployeeDoesNotExistsException exception) {
            return new ResponseEntity<>("Employee does not exists !!", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
    }
}

