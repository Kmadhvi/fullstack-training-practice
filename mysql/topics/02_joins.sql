-- Topic: Joins

USE training_db;

CREATE TABLE IF NOT EXISTS departments (
    department_id INT PRIMARY KEY AUTO_INCREMENT,
    department_name VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS staff (
    staff_id INT PRIMARY KEY AUTO_INCREMENT,
    staff_name VARCHAR(50),
    department_id INT,
    manager_id INT NULL
);

INSERT INTO departments (department_name)
VALUES ('HR'), ('IT'), ('Finance');

INSERT INTO staff (staff_name, department_id, manager_id)
VALUES
    ('Anita', 1, NULL),
    ('Karan', 2, 1),
    ('Meera', 3, 1),
    ('Vikas', NULL, NULL);

SELECT s.staff_name, d.department_name
FROM staff s
INNER JOIN departments d
    ON s.department_id = d.department_id;

SELECT s.staff_name, d.department_name
FROM staff s
LEFT JOIN departments d
    ON s.department_id = d.department_id;

SELECT s.staff_name, d.department_name
FROM staff s
RIGHT JOIN departments d
    ON s.department_id = d.department_id;

SELECT e.staff_name AS employee_name, m.staff_name AS manager_name
FROM staff e
LEFT JOIN staff m
    ON e.manager_id = m.staff_id;
