package com.example.emp.service;

import com.example.emp.dto.EmployeeResponseDTO;
import com.example.emp.entity.Employee;

import java.util.List;

public interface EmployeeService {
    EmployeeResponseDTO findById(Long id);

    EmployeeResponseDTO saveEmployee(Employee employee);

    EmployeeResponseDTO updateEmployee(Employee employee);

    void deleteEmployee(Employee employee);

    List<EmployeeResponseDTO> getAllEmployees();

    List<EmployeeResponseDTO> getAllEmployeesByName(String name);
}
