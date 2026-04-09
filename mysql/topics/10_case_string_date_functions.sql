-- Topic: CASE, String Functions, and Date Functions

USE training_db;

SELECT first_name,
       salary,
       CASE
           WHEN salary >= 60000 THEN 'High'
           WHEN salary >= 50000 THEN 'Medium'
           ELSE 'Low'
       END AS salary_band
FROM employees;

SELECT first_name,
       UPPER(first_name) AS upper_name,
       CONCAT(first_name, ' ', last_name) AS full_name
FROM employees;

SELECT first_name,
       hire_date,
       DATEDIFF(CURDATE(), hire_date) AS days_with_company
FROM employees;
