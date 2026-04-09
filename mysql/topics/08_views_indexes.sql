-- Topic: Views and Indexes

USE training_db;

CREATE OR REPLACE VIEW high_salary_employees AS
SELECT employee_id, first_name, last_name, department, salary
FROM employees
WHERE salary >= 55000;

SELECT *
FROM high_salary_employees;

CREATE INDEX idx_employees_department
ON employees (department);

CREATE INDEX idx_employees_hire_date
ON employees (hire_date);
