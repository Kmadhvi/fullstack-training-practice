-- Topic: Transactions

USE training_db;

CREATE TABLE IF NOT EXISTS accounts (
    account_id INT PRIMARY KEY AUTO_INCREMENT,
    account_holder VARCHAR(100),
    balance DECIMAL(10, 2)
);

INSERT INTO accounts (account_holder, balance)
VALUES
    ('Riya', 10000.00),
    ('Aman', 8000.00);

START TRANSACTION;

UPDATE accounts
SET balance = balance - 1000
WHERE account_id = 1;

UPDATE accounts
SET balance = balance + 1000
WHERE account_id = 2;

COMMIT;

SELECT *
FROM accounts;
