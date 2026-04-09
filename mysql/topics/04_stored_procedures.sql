-- Topic: Stored Procedures

USE training_db;

DELIMITER //

CREATE PROCEDURE GetEmployeesByDepartment(IN dept_name VARCHAR(50))
BEGIN
    SELECT employee_id, first_name, last_name, salary
    FROM employees
    WHERE department = dept_name;
END //

CREATE PROCEDURE AddEmployee(
    IN p_first_name VARCHAR(50),
    IN p_last_name VARCHAR(50),
    IN p_department VARCHAR(50),
    IN p_salary DECIMAL(10, 2),
    IN p_hire_date DATE
)
BEGIN
    INSERT INTO employees (
        first_name,
        last_name,
        department,
        salary,
        hire_date
    )
    VALUES (
        p_first_name,
        p_last_name,
        p_department,
        p_salary,
        p_hire_date
    );
END //

DELIMITER ;

CALL GetEmployeesByDepartment('IT');

CALL AddEmployee('Neha', 'Kapoor', 'Marketing', 48000.00, '2024-02-12');
