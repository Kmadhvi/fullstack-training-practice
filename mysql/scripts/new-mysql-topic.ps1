param(
    [string]$BasePath = (Join-Path $PSScriptRoot ".."),
    [string]$DateString = (Get-Date -Format "yyyy-MM-dd")
)

$resolvedBasePath = (Resolve-Path $BasePath).Path
$dailyFolder = Join-Path $resolvedBasePath "daily-topics"

if (-not (Test-Path $dailyFolder)) {
    New-Item -ItemType Directory -Path $dailyFolder | Out-Null
}

$topics = @(
    @{
        Slug = "ddl-and-dml"
        Title = "DDL and DML Statements"
        Body = @"
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
"@
    },
    @{
        Slug = "operators-and-filtering"
        Title = "Operators and Filtering"
        Body = @"
-- Topic: Operators and Filtering

USE training_db;

SELECT *
FROM employees
WHERE salary BETWEEN 45000 AND 65000;

SELECT *
FROM employees
WHERE department IN ('IT', 'Finance');

SELECT *
FROM employees
WHERE first_name LIKE 'R%';
"@
    },
    @{
        Slug = "single-row-functions"
        Title = "Single Row Functions"
        Body = @"
-- Topic: Single Row Functions

USE training_db;

SELECT first_name,
       LENGTH(first_name) AS name_length,
       LOWER(department) AS lower_department
FROM employees;
"@
    },
    @{
        Slug = "window-functions"
        Title = "Window Functions"
        Body = @"
-- Topic: Window Functions

USE training_db;

SELECT first_name,
       department,
       salary,
       RANK() OVER (PARTITION BY department ORDER BY salary DESC) AS salary_rank
FROM employees;
"@
    },
    @{
        Slug = "common-table-expressions"
        Title = "Common Table Expressions"
        Body = @"
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
"@
    },
    @{
        Slug = "normalization"
        Title = "Normalization Basics"
        Body = @"
-- Topic: Normalization Basics

-- First Normal Form (1NF): remove repeating groups.
-- Second Normal Form (2NF): remove partial dependency.
-- Third Normal Form (3NF): remove transitive dependency.

CREATE TABLE orders (
    order_id INT PRIMARY KEY,
    customer_name VARCHAR(100),
    customer_city VARCHAR(100)
);
"@
    },
    @{
        Slug = "backup-and-restore-notes"
        Title = "Backup and Restore Notes"
        Body = @"
-- Topic: Backup and Restore Notes

-- Export example:
-- mysqldump -u root -p training_db > training_db_backup.sql

-- Import example:
-- mysql -u root -p training_db < training_db_backup.sql
"@
    }
)

$existingFiles = Get-ChildItem -Path $dailyFolder -Filter "*.sql" -File | Sort-Object Name
$nextTopic = $topics[$existingFiles.Count % $topics.Count]
$fileName = "{0}_{1}.sql" -f $DateString, $nextTopic.Slug
$targetPath = Join-Path $dailyFolder $fileName

if (Test-Path $targetPath) {
    Write-Output "Skipped: $targetPath already exists."
    exit 0
}

$content = @"
-- Daily MySQL Practice File
-- Date: $DateString
-- Topic: $($nextTopic.Title)

$($nextTopic.Body)
"@

Set-Content -Path $targetPath -Value $content -Encoding ascii
Write-Output "Created: $targetPath"
