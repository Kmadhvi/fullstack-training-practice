-- Topic: Subqueries

USE training_db;

SELECT first_name, salary
FROM employees
WHERE salary > (
    SELECT AVG(salary)
    FROM employees
);

SELECT first_name
FROM employees
WHERE department IN (
    SELECT department_name
    FROM departments
);

SELECT department, MAX(salary) AS highest_salary
FROM employees
GROUP BY department
HAVING MAX(salary) > (
    SELECT AVG(salary)
    FROM employees
);
