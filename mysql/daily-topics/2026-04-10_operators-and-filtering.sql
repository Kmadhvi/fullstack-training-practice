-- Daily MySQL Practice File
-- Date: 2026-04-10
-- Topic: Operators and Filtering

-- Topic: Operators and Filtering

USE training_db;

SELECT *
FROM employees
WHERE salary BETWEEN 45000 AND 65000;

SELECT *
FROM employees
WHERE department IN ('IT', 'Finance');

SELECT *
FROM employees
WHERE first_name LIKE 'R%';
