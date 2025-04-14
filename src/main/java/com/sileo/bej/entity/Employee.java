package com.sileo.bej.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "employees_info")
@Data
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String address;
    private String designation;
    private String department;
    private String joiningDate;
    private Double salary;
    private String disability;
    private String typeOfDisability;
    @Column(unique = true,nullable = false)
    private String email;
    private String contact;
   




}
