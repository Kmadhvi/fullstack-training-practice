INSERT INTO departments (id, name, code, description, active) VALUES
(1, 'Administration', 'ADMIN', 'Hospital administration and operations', TRUE),
(2, 'General Medicine', 'MED', 'Outpatient and inpatient general medicine', TRUE),
(3, 'Nursing', 'NURS', 'Inpatient nursing care', TRUE),
(4, 'Laboratory', 'LAB', 'Diagnostic laboratory services', TRUE),
(5, 'Pharmacy', 'PHARM', 'Medication inventory and dispensing', TRUE),
(6, 'Reception', 'REC', 'Front desk and appointment operations', TRUE)
ON DUPLICATE KEY UPDATE name = VALUES(name), description = VALUES(description), active = VALUES(active);

INSERT INTO users (id, full_name, email, password_hash, role, department_id, phone, enabled, account_non_locked) VALUES
(1, 'System Admin', 'admin@hms.com', '$2a$10$EU83vgY42t/G1HuBk/jyGO61BcLjrv/dsvTOfagrgXirSutQpNAFK', 'ROLE_ADMIN', 1, '9000000001', TRUE, TRUE),
(2, 'Dr. Meera Sharma', 'doctor@hms.com', '$2a$10$EU83vgY42t/G1HuBk/jyGO61BcLjrv/dsvTOfagrgXirSutQpNAFK', 'ROLE_DOCTOR', 2, '9000000002', TRUE, TRUE),
(3, 'Nurse Arjun Rao', 'nurse@hms.com', '$2a$10$EU83vgY42t/G1HuBk/jyGO61BcLjrv/dsvTOfagrgXirSutQpNAFK', 'ROLE_NURSE', 3, '9000000003', TRUE, TRUE),
(4, 'Riya Reception', 'reception@hms.com', '$2a$10$EU83vgY42t/G1HuBk/jyGO61BcLjrv/dsvTOfagrgXirSutQpNAFK', 'ROLE_RECEPTIONIST', 6, '9000000004', TRUE, TRUE),
(5, 'Kabir Labtech', 'lab@hms.com', '$2a$10$EU83vgY42t/G1HuBk/jyGO61BcLjrv/dsvTOfagrgXirSutQpNAFK', 'ROLE_LAB_TECH', 4, '9000000005', TRUE, TRUE),
(6, 'Anika Pharmacist', 'pharmacy@hms.com', '$2a$10$EU83vgY42t/G1HuBk/jyGO61BcLjrv/dsvTOfagrgXirSutQpNAFK', 'ROLE_PHARMACIST', 5, '9000000006', TRUE, TRUE),
(7, 'Rahul Verma', 'patient@hms.com', '$2a$10$EU83vgY42t/G1HuBk/jyGO61BcLjrv/dsvTOfagrgXirSutQpNAFK', 'ROLE_PATIENT', NULL, '9000000007', TRUE, TRUE)
ON DUPLICATE KEY UPDATE full_name = VALUES(full_name), password_hash = VALUES(password_hash), role = VALUES(role), department_id = VALUES(department_id), enabled = VALUES(enabled), account_non_locked = VALUES(account_non_locked);

INSERT INTO doctors (id, user_id, department_id, license_number, specialization, qualification, consultation_fee, available) VALUES
(1, 2, 2, 'MCI-2024-1001', 'Internal Medicine', 'MBBS, MD', 650.00, TRUE)
ON DUPLICATE KEY UPDATE specialization = VALUES(specialization), consultation_fee = VALUES(consultation_fee), available = VALUES(available);

INSERT INTO patients (id, user_id, mrn, first_name, last_name, gender, date_of_birth, blood_group, phone, email, address, emergency_contact_name, emergency_contact_phone, allergies, medical_history) VALUES
(1, 7, 'MRN-2026-0001', 'Rahul', 'Verma', 'MALE', '1992-08-14', 'O_POSITIVE', '9000000007', 'patient@hms.com', 'Bengaluru, Karnataka', 'Neha Verma', '9000000017', 'Penicillin', 'Mild asthma')
ON DUPLICATE KEY UPDATE phone = VALUES(phone), allergies = VALUES(allergies), medical_history = VALUES(medical_history);

INSERT INTO medicine_inventory (id, medicine_name, batch_number, manufacturer, expiry_date, quantity_available, reorder_level, unit_price) VALUES
(1, 'Paracetamol 500mg', 'PCM-2401', 'CarePharma', '2027-12-31', 500, 100, 2.50),
(2, 'Amoxicillin 500mg', 'AMX-2402', 'MedLife Labs', '2027-06-30', 120, 50, 8.00)
ON DUPLICATE KEY UPDATE quantity_available = VALUES(quantity_available), reorder_level = VALUES(reorder_level), unit_price = VALUES(unit_price);
