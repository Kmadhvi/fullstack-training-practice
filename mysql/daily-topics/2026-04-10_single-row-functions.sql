-- Daily MySQL Practice File
-- Date: 2026-04-10
-- Topic: Single Row Functions

-- Topic: Single Row Functions

USE training_db;

SELECT first_name,
       LENGTH(first_name) AS name_length,
       LOWER(department) AS lower_department
FROM employees;
