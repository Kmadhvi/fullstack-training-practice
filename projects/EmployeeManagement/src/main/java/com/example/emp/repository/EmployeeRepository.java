package com.example.emp.repository;

import com.example.emp.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Custom Finder Method
    List<Employee> findByName(String name);

    // JPQL
    @Query("select e from Employee e where e.name= :n")
    List<Employee> findEmployeeByName(@Param("n") String name);

    // Native Query
    @Query(value = "select * from Employee where emp_name= :n", nativeQuery = true)
    List<Employee> findEmpByName(@Param("n") String name);
}
