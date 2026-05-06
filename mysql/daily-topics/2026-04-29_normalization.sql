-- Daily MySQL Practice File
-- Date: 2026-04-29
-- Topic: Normalization Basics

-- Topic: Normalization Basics

-- First Normal Form (1NF): remove repeating groups.
-- Second Normal Form (2NF): remove partial dependency.
-- Third Normal Form (3NF): remove transitive dependency.

CREATE TABLE orders (
    order_id INT PRIMARY KEY,
    customer_name VARCHAR(100),
    customer_city VARCHAR(100)
);
