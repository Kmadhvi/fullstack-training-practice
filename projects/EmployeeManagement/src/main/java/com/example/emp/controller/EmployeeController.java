package com.example.emp.controller;

import com.example.emp.dto.EmployeeResponseDTO;
import com.example.emp.entity.Employee;
import com.example.emp.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/employee")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @GetMapping("/list")
    public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployees() {
        try {
            return ResponseEntity.ok(employeeService.getAllEmployees());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> getEmployeesById(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(employeeService.findById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/")
    public ResponseEntity<EmployeeResponseDTO> saveEmployee(@RequestBody Employee employee) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.saveEmployee(employee));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/")
    public ResponseEntity<EmployeeResponseDTO> updateEmployee(@RequestBody Employee employee) {
        try {
            return ResponseEntity.ok(employeeService.updateEmployee(employee));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/")
    public ResponseEntity<Void> deleteEmployee(@RequestBody Employee employee) {
        try {
            employeeService.deleteEmployee(employee);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/byName")
    public ResponseEntity<List<EmployeeResponseDTO>> getEmployeesByName(@RequestParam(name = "name") String name) {
        try {
            return ResponseEntity.ok(employeeService.getAllEmployeesByName(name));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
