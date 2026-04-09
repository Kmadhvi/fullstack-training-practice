-- Topic: Basic SELECT Queries

CREATE DATABASE IF NOT EXISTS training_db;
USE training_db;

CREATE TABLE IF NOT EXISTS employees (
    employee_id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    department VARCHAR(50),
    salary DECIMAL(10, 2),
    hire_date DATE
);

INSERT INTO employees (first_name, last_name, department, salary, hire_date)
VALUES
    ('Asha', 'Sharma', 'HR', 45000.00, '2023-01-10'),
    ('Rahul', 'Verma', 'IT', 65000.00, '2022-06-15'),
    ('Priya', 'Nair', 'Finance', 55000.00, '2021-09-20');

SELECT * FROM employees;

SELECT first_name, department
FROM employees;

SELECT *
FROM employees
WHERE department = 'IT';

SELECT *
FROM employees
WHERE salary > 50000;

SELECT *
FROM employees
ORDER BY salary DESC;

SELECT *
FROM employees
LIMIT 2;
