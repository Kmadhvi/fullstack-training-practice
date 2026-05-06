-- Daily MySQL Practice File
-- Date: 2026-04-15
-- Topic: Window Functions

-- Topic: Window Functions

USE training_db;

SELECT first_name,
       department,
       salary,
       RANK() OVER (PARTITION BY department ORDER BY salary DESC) AS salary_rank
FROM employees;
