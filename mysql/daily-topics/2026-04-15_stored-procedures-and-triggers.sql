-- Daily MySQL Practice File
-- Date: 2026-04-15
-- Topic: Stored Procedures and Triggers

-- Topic: Stored Procedures and Triggers

USE training_db;

DELIMITER //
CREATE PROCEDURE raise_salary_by_department(
    IN p_department VARCHAR(50),
    IN p_pct DECIMAL(5,2)
)
BEGIN
    DECLARE v_rows INT DEFAULT 0;

    UPDATE employees
    SET salary = salary + (salary * p_pct / 100)
    WHERE department = p_department;

    SET v_rows = ROW_COUNT();

    INSERT INTO audit_log(action_name, action_detail, created_at)
    VALUES (
        'raise_salary_by_department',
        CONCAT('department=', p_department, ', rows=', v_rows),
        NOW()
    );
END//
DELIMITER ;

DELIMITER //
CREATE TRIGGER employees_before_update
BEFORE UPDATE ON employees
FOR EACH ROW
BEGIN
    IF NEW.salary < OLD.salary THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Salary cannot be decreased by this operation';
    END IF;
END//
DELIMITER ;

CALL raise_salary_by_department('IT', 8.5);
