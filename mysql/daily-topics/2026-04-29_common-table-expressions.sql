-- Daily MySQL Practice File
-- Date: 2026-04-29
-- Topic: Common Table Expressions

-- Topic: Common Table Expressions

USE training_db;

WITH department_totals AS (
    SELECT department, COUNT(*) AS total_staff
    FROM employees
    GROUP BY department
)
SELECT *
FROM department_totals
WHERE total_staff >= 1;
