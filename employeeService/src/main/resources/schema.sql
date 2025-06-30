CREATE DATABASE IF NOT EXISTS employee_db;
USE employee_db;

-- Create employees table (reordered columns to match Java class)
CREATE TABLE employees (
    emp_id BIGINT NOT NULL,
    emp_name VARCHAR(255),
    emp_phone BIGINT,
    emp_address VARCHAR(255),
    emp_role VARCHAR(255),
    emp_gender VARCHAR(255),
    warehouse_id BIGINT,
    authId BIGINT NOT NULL,
    PRIMARY KEY (emp_id)
) ENGINE=InnoDB;

-- Create employees sequence table
CREATE TABLE employees_seq (
    next_val BIGINT
) ENGINE=InnoDB;

-- Initialize employees sequence
INSERT INTO employees_seq VALUES (1);

-- Create general sequence table
CREATE TABLE sequence (
    next_val BIGINT
) ENGINE=InnoDB;

-- Initialize general sequence
INSERT INTO sequence VALUES (1);

-- Create warehouse table
CREATE TABLE warehouse (
    warehouse_id BIGINT NOT NULL,
    description VARCHAR(255),
    warehouse_address VARCHAR(255),
    warehouse_capacity INT NOT NULL,
    warehouse_current_capacity INT NOT NULL,
    warehouse_name VARCHAR(255),
    PRIMARY KEY (warehouse_id)
) ENGINE=InnoDB;

-- Create warehouse-employee relationship table
CREATE TABLE warehouse_employee_list (
    warehouse_warehouse_id BIGINT NOT NULL,
    employee_list VARCHAR(255),
    CONSTRAINT FKmupx8pica0t9cf52ej9m8b38r FOREIGN KEY (warehouse_warehouse_id)
        REFERENCES warehouse (warehouse_id)
) ENGINE=InnoDB;
