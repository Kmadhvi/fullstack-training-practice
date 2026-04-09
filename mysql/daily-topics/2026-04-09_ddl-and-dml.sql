-- Daily MySQL Practice File
-- Date: 2026-04-09
-- Topic: DDL and DML Statements

-- Topic: DDL and DML Statements

CREATE DATABASE IF NOT EXISTS practice_db;
USE practice_db;

CREATE TABLE learners (
    learner_id INT PRIMARY KEY AUTO_INCREMENT,
    learner_name VARCHAR(100) NOT NULL,
    city VARCHAR(50)
);

INSERT INTO learners (learner_name, city)
VALUES ('Arjun', 'Delhi'), ('Sneha', 'Pune');

UPDATE learners
SET city = 'Mumbai'
WHERE learner_id = 2;

DELETE FROM learners
WHERE learner_id = 1;

SELECT *
FROM learners;
