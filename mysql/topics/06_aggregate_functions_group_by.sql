-- Topic: Aggregate Functions and GROUP BY

USE training_db;

SELECT department, COUNT(*) AS total_employees
FROM employees
GROUP BY department;

SELECT department, AVG(salary) AS average_salary
FROM employees
GROUP BY department;

SELECT department, SUM(salary) AS total_salary
FROM employees
GROUP BY department;

SELECT department, MIN(salary) AS minimum_salary, MAX(salary) AS maximum_salary
FROM employees
GROUP BY department;
