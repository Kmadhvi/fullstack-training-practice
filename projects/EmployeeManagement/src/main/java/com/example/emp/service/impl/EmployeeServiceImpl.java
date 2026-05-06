package com.example.emp.service.impl;

import com.example.emp.dto.EmployeeResponseDTO;
import com.example.emp.entity.Employee;
import com.example.emp.repository.EmployeeRepository;
import com.example.emp.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public EmployeeResponseDTO findById(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        return maptoDto(employee.get());
    }

    @Override
    public EmployeeResponseDTO saveEmployee(Employee employee) {
        Employee savedEmployee = employeeRepository.save(employee);
        return maptoDto(savedEmployee);
    }

    @Override
    public EmployeeResponseDTO updateEmployee(Employee employee) {
        Employee updatedEmployee = employeeRepository.save(employee);
        return maptoDto(updatedEmployee);
    }

    @Override
    public void deleteEmployee(Employee employee) {
        employeeRepository.delete(employee);
    }

    @Override
    public List<EmployeeResponseDTO> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeResponseDTO> employeeList = new ArrayList<>();
        for (Employee employee : employees) {
            employeeList.add(maptoDto(employee));
        }
        return employeeList;
    }

    @Override
    public List<EmployeeResponseDTO> getAllEmployeesByName(String name) {
//        List<Employee> employees = employeeRepository.findByName(name);
//        List<Employee> employees = employeeRepository.findEmployeeByName(name);
        List<Employee> employees = employeeRepository.findEmpByName(name);
        List<EmployeeResponseDTO> employeeList = new ArrayList<>();
        for (Employee employee : employees) {
            employeeList.add(maptoDto(employee));
        }
        return employeeList;
    }

    private EmployeeResponseDTO maptoDto(Employee employee) {
        EmployeeResponseDTO dto = new EmployeeResponseDTO();

        dto.setId(employee.getId());
        dto.setName(employee.getName());
        dto.setDepartment(employee.getDepartment());

        return dto;
    }
}
