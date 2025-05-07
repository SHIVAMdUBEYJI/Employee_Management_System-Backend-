package com.sileo.bej.service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.sileo.bej.entity.Employee;
import com.sileo.bej.entity.EmployeeOrder;
import com.sileo.bej.exception.EmployeeAlreadyExists;
import com.sileo.bej.exception.EmployeeDoesNotExistsException;
import com.sileo.bej.repository.EmployeeOrderRepository;
import com.sileo.bej.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final EmployeeOrderRepository orderRepository;

    @Value("${razorpay.key.id}")
    private String razorpayKey;
    @Value("${razorpay.key.secret}")
    private String razorpaySecret;
    private RazorpayClient razorpayClient;

    public EmployeeOrder createOrder(EmployeeOrder employeeOrder) throws RazorpayException {

        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", employeeOrder.getAmount() *100);
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", ""); // optional but good practice
        orderRequest.put("payment_capture", 1); // auto capture after payment

        this.razorpayClient = new RazorpayClient(razorpayKey, razorpaySecret);
        Order razorpayOrder = razorpayClient.orders.create(orderRequest);

        employeeOrder.setOrderId(razorpayOrder.get("id"));
        employeeOrder.setOrderStatus("CREATED");
        orderRepository.save(employeeOrder);
        return employeeOrder;


    }

    public Employee createEmployee(Employee employee) throws EmployeeAlreadyExists {
        Optional<Employee> existingEmployee = employeeRepository.findByEmail(employee.getEmail());
        if (existingEmployee.isPresent()) {
            throw new EmployeeAlreadyExists("Employee With this email ID already Exists,Please enter new Email!!");
        }
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Long id, Employee updatedEmployee) throws EmployeeDoesNotExistsException {
        Employee existingEmployee = employeeRepository.findById(id).orElseThrow(() -> new EmployeeDoesNotExistsException("Employee does not exists ,Please Check again!!"));
        existingEmployee.setFullName(updatedEmployee.getFullName());
        existingEmployee.setAddress(updatedEmployee.getAddress());
        existingEmployee.setDesignation(updatedEmployee.getDesignation());
        existingEmployee.setDepartment(updatedEmployee.getDepartment());
        existingEmployee.setDisability(updatedEmployee.getDisability());
        existingEmployee.setEmail(updatedEmployee.getEmail());
        existingEmployee.setSalary(updatedEmployee.getSalary());
        existingEmployee.setTypeOfDisability(updatedEmployee.getDisability());
        existingEmployee.setJoiningDate(updatedEmployee.getJoiningDate());
        existingEmployee.setContact(updatedEmployee.getContact());
        return employeeRepository.save(existingEmployee);
    }

    Optional<Employee> FindEmployeeByEmail(String email) {
        return employeeRepository.findByEmail(email);
    }

    public List<Employee> searchByFullName(String fullName) {
        return employeeRepository.findByFullNameContainingIgnoreCase(fullName);
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }


}
