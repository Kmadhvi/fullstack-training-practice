CREATE TABLE IF NOT EXISTS departments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(120) NOT NULL,
    code VARCHAR(30) NOT NULL,
    description VARCHAR(500),
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT uk_departments_name UNIQUE (name),
    CONSTRAINT uk_departments_code UNIQUE (code)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(160) NOT NULL,
    email VARCHAR(180) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role ENUM('ROLE_ADMIN','ROLE_DOCTOR','ROLE_NURSE','ROLE_RECEPTIONIST','ROLE_LAB_TECH','ROLE_PHARMACIST','ROLE_PATIENT') NOT NULL,
    department_id BIGINT,
    phone VARCHAR(30),
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    account_non_locked BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT uk_users_email UNIQUE (email),
    CONSTRAINT fk_users_department FOREIGN KEY (department_id) REFERENCES departments(id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS patients (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    mrn VARCHAR(40) NOT NULL,
    first_name VARCHAR(80) NOT NULL,
    last_name VARCHAR(80) NOT NULL,
    gender ENUM('MALE','FEMALE','OTHER') NOT NULL,
    date_of_birth DATE NOT NULL,
    blood_group ENUM('A_POSITIVE','A_NEGATIVE','B_POSITIVE','B_NEGATIVE','AB_POSITIVE','AB_NEGATIVE','O_POSITIVE','O_NEGATIVE','UNKNOWN') NOT NULL DEFAULT 'UNKNOWN',
    phone VARCHAR(30) NOT NULL,
    email VARCHAR(180),
    address VARCHAR(500),
    emergency_contact_name VARCHAR(120),
    emergency_contact_phone VARCHAR(30),
    allergies TEXT,
    medical_history TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT uk_patients_mrn UNIQUE (mrn),
    CONSTRAINT uk_patients_user UNIQUE (user_id),
    CONSTRAINT fk_patients_user FOREIGN KEY (user_id) REFERENCES users(id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS doctors (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    department_id BIGINT NOT NULL,
    license_number VARCHAR(80) NOT NULL,
    specialization VARCHAR(120) NOT NULL,
    qualification VARCHAR(160) NOT NULL,
    consultation_fee DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    available BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT uk_doctors_user UNIQUE (user_id),
    CONSTRAINT uk_doctors_license UNIQUE (license_number),
    CONSTRAINT fk_doctors_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_doctors_department FOREIGN KEY (department_id) REFERENCES departments(id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS appointments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    patient_id BIGINT NOT NULL,
    doctor_id BIGINT NOT NULL,
    appointment_type ENUM('OPD','FOLLOW_UP','EMERGENCY','TELECONSULTATION') NOT NULL,
    status ENUM('SCHEDULED','CHECKED_IN','IN_CONSULTATION','COMPLETED','CANCELLED','NO_SHOW') NOT NULL DEFAULT 'SCHEDULED',
    appointment_at DATETIME NOT NULL,
    reason VARCHAR(500),
    queue_number INT,
    walk_in BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_appointments_patient FOREIGN KEY (patient_id) REFERENCES patients(id),
    CONSTRAINT fk_appointments_doctor FOREIGN KEY (doctor_id) REFERENCES doctors(id),
    INDEX idx_appointments_doctor_time (doctor_id, appointment_at),
    INDEX idx_appointments_patient (patient_id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS admissions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    patient_id BIGINT NOT NULL,
    doctor_id BIGINT NOT NULL,
    status ENUM('ADMITTED','DISCHARGED','TRANSFERRED','CANCELLED') NOT NULL DEFAULT 'ADMITTED',
    ward VARCHAR(80) NOT NULL,
    bed_number VARCHAR(40) NOT NULL,
    admitted_at DATETIME NOT NULL,
    discharged_at DATETIME,
    diagnosis VARCHAR(500),
    discharge_summary TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_admissions_patient FOREIGN KEY (patient_id) REFERENCES patients(id),
    CONSTRAINT fk_admissions_doctor FOREIGN KEY (doctor_id) REFERENCES doctors(id),
    INDEX idx_admissions_status (status)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS consultations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    appointment_id BIGINT,
    patient_id BIGINT NOT NULL,
    doctor_id BIGINT NOT NULL,
    symptoms TEXT,
    diagnosis TEXT,
    notes TEXT,
    follow_up_date DATE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_consultations_appointment FOREIGN KEY (appointment_id) REFERENCES appointments(id),
    CONSTRAINT fk_consultations_patient FOREIGN KEY (patient_id) REFERENCES patients(id),
    CONSTRAINT fk_consultations_doctor FOREIGN KEY (doctor_id) REFERENCES doctors(id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS prescriptions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    consultation_id BIGINT NOT NULL,
    patient_id BIGINT NOT NULL,
    doctor_id BIGINT NOT NULL,
    status ENUM('DRAFT','ISSUED','PARTIALLY_DISPENSED','DISPENSED','CANCELLED') NOT NULL DEFAULT 'ISSUED',
    instructions TEXT,
    issued_at DATETIME NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_prescriptions_consultation FOREIGN KEY (consultation_id) REFERENCES consultations(id),
    CONSTRAINT fk_prescriptions_patient FOREIGN KEY (patient_id) REFERENCES patients(id),
    CONSTRAINT fk_prescriptions_doctor FOREIGN KEY (doctor_id) REFERENCES doctors(id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS prescription_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    prescription_id BIGINT NOT NULL,
    medicine_name VARCHAR(180) NOT NULL,
    dosage VARCHAR(80) NOT NULL,
    frequency VARCHAR(80) NOT NULL,
    duration_days INT NOT NULL,
    quantity INT NOT NULL,
    instructions VARCHAR(300),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_prescription_items_prescription FOREIGN KEY (prescription_id) REFERENCES prescriptions(id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS lab_orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    patient_id BIGINT NOT NULL,
    doctor_id BIGINT NOT NULL,
    consultation_id BIGINT,
    status ENUM('ORDERED','SAMPLE_COLLECTED','IN_PROGRESS','COMPLETED','CANCELLED') NOT NULL DEFAULT 'ORDERED',
    ordered_at DATETIME NOT NULL,
    completed_at DATETIME,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_lab_orders_patient FOREIGN KEY (patient_id) REFERENCES patients(id),
    CONSTRAINT fk_lab_orders_doctor FOREIGN KEY (doctor_id) REFERENCES doctors(id),
    CONSTRAINT fk_lab_orders_consultation FOREIGN KEY (consultation_id) REFERENCES consultations(id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS lab_order_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    lab_order_id BIGINT NOT NULL,
    test_name VARCHAR(180) NOT NULL,
    sample_type VARCHAR(80) NOT NULL,
    status ENUM('ORDERED','SAMPLE_COLLECTED','IN_PROGRESS','COMPLETED','CANCELLED') NOT NULL DEFAULT 'ORDERED',
    result_value VARCHAR(255),
    reference_range VARCHAR(120),
    remarks VARCHAR(500),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_lab_order_items_order FOREIGN KEY (lab_order_id) REFERENCES lab_orders(id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS medicine_inventory (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    medicine_name VARCHAR(180) NOT NULL,
    batch_number VARCHAR(80) NOT NULL,
    manufacturer VARCHAR(160),
    expiry_date DATE NOT NULL,
    quantity_available INT NOT NULL,
    reorder_level INT NOT NULL,
    unit_price DECIMAL(10,2) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT uk_medicine_batch UNIQUE (medicine_name, batch_number),
    INDEX idx_medicine_stock (quantity_available, reorder_level)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS bills (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    patient_id BIGINT NOT NULL,
    bill_number VARCHAR(50) NOT NULL,
    status ENUM('DRAFT','ISSUED','PARTIALLY_PAID','PAID','CANCELLED') NOT NULL DEFAULT 'DRAFT',
    subtotal DECIMAL(12,2) NOT NULL DEFAULT 0.00,
    discount DECIMAL(12,2) NOT NULL DEFAULT 0.00,
    tax DECIMAL(12,2) NOT NULL DEFAULT 0.00,
    total_amount DECIMAL(12,2) NOT NULL DEFAULT 0.00,
    paid_amount DECIMAL(12,2) NOT NULL DEFAULT 0.00,
    issued_at DATETIME,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT uk_bills_number UNIQUE (bill_number),
    CONSTRAINT fk_bills_patient FOREIGN KEY (patient_id) REFERENCES patients(id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS bill_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    bill_id BIGINT NOT NULL,
    item_type ENUM('CONSULTATION','LAB','PHARMACY','ROOM','PROCEDURE','OTHER') NOT NULL,
    description VARCHAR(255) NOT NULL,
    quantity INT NOT NULL,
    unit_price DECIMAL(10,2) NOT NULL,
    amount DECIMAL(12,2) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_bill_items_bill FOREIGN KEY (bill_id) REFERENCES bills(id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS vitals (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    patient_id BIGINT NOT NULL,
    recorded_by BIGINT NOT NULL,
    admission_id BIGINT,
    temperature_celsius DECIMAL(4,1),
    pulse_rate INT,
    respiratory_rate INT,
    systolic_bp INT,
    diastolic_bp INT,
    oxygen_saturation INT,
    weight_kg DECIMAL(5,2),
    notes VARCHAR(500),
    recorded_at DATETIME NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_vitals_patient FOREIGN KEY (patient_id) REFERENCES patients(id),
    CONSTRAINT fk_vitals_user FOREIGN KEY (recorded_by) REFERENCES users(id),
    CONSTRAINT fk_vitals_admission FOREIGN KEY (admission_id) REFERENCES admissions(id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS nursing_notes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    admission_id BIGINT NOT NULL,
    patient_id BIGINT NOT NULL,
    nurse_id BIGINT NOT NULL,
    note TEXT NOT NULL,
    recorded_at DATETIME NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_nursing_notes_admission FOREIGN KEY (admission_id) REFERENCES admissions(id),
    CONSTRAINT fk_nursing_notes_patient FOREIGN KEY (patient_id) REFERENCES patients(id),
    CONSTRAINT fk_nursing_notes_nurse FOREIGN KEY (nurse_id) REFERENCES users(id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS audit_logs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    action VARCHAR(120) NOT NULL,
    entity_name VARCHAR(120) NOT NULL,
    entity_id BIGINT,
    details TEXT,
    ip_address VARCHAR(80),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_audit_logs_user FOREIGN KEY (user_id) REFERENCES users(id),
    INDEX idx_audit_logs_entity (entity_name, entity_id),
    INDEX idx_audit_logs_user (user_id)
) ENGINE=InnoDB;
