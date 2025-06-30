CREATE DATABASE IF NOT EXISTS warehouse_db;
USE warehouse_db;
-- CREATE TABLE IF NOT EXISTS warehouse (
--     warehouse_id VARCHAR(255) PRIMARY KEY,
--     employee_list VARCHAR(1255),
--     wearhouse_address VARCHAR(255),
--     wearhouse_capacity INT,
--     wearhouse_current INT,
--     wearhouse_description TEXT,
--     wearhouse_name VARCHAR(255),
--     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
--     updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
-- ) 

-- Create the sequence table (for ID generation)
CREATE TABLE sequence (
    next_val BIGINT
) ENGINE=InnoDB;

-- Insert initial value into the sequence table
INSERT INTO sequence VALUES (1);

-- Create the warehouse table with columns ordered as in the entity
CREATE TABLE warehouse (
    warehouse_id BIGINT NOT NULL,
    warehouse_name VARCHAR(255),
    warehouse_address VARCHAR(255),
    warehouse_capacity INTEGER NOT NULL,
    warehouse_current_capacity INTEGER NOT NULL,
    description VARCHAR(255),
    PRIMARY KEY (warehouse_id)
) ENGINE=InnoDB;

-- Create the warehouse_employee_list table for the ElementCollection List<String> employeeList
CREATE TABLE warehouse_employee_list (
    warehouse_warehouse_id BIGINT NOT NULL,
    employee_list VARCHAR(255)
) ENGINE=InnoDB;
