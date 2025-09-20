-- Simplified PostgreSQL SQL Script for HSBC Fund Data Fetcher
-- Run this script to create the 6 tables for storing API responses

-- Create database (run this separately if needed)
-- CREATE DATABASE fundsdb;

-- Connect to the database and run the following:

-- 1. Fund Product Table
CREATE TABLE fund_product (
    id BIGSERIAL PRIMARY KEY,
    product_code VARCHAR(50) UNIQUE NOT NULL,
    response_data JSONB NOT NULL,
    update_date DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2. Fund Quote Detail Table
CREATE TABLE fund_quote_detail (
    id BIGSERIAL PRIMARY KEY,
    product_code VARCHAR(50) UNIQUE NOT NULL,
    response_data JSONB NOT NULL,
    update_date DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 3. Fund Quote Summary Table
CREATE TABLE fund_quote_summary (
    id BIGSERIAL PRIMARY KEY,
    product_code VARCHAR(50) UNIQUE NOT NULL,
    response_data JSONB NOT NULL,
    update_date DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 4. Fund Holding Allocation Table
CREATE TABLE fund_holding_allocation (
    id BIGSERIAL PRIMARY KEY,
    product_code VARCHAR(50) UNIQUE NOT NULL,
    response_data JSONB NOT NULL,
    update_date DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 5. Fund Top Ten Holdings Table
CREATE TABLE fund_top_ten_holdings (
    id BIGSERIAL PRIMARY KEY,
    product_code VARCHAR(50) UNIQUE NOT NULL,
    response_data JSONB NOT NULL,
    update_date DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 6. Fund Other Fund Classes Table
CREATE TABLE fund_other_fund_classes (
    id BIGSERIAL PRIMARY KEY,
    product_code VARCHAR(50) UNIQUE NOT NULL,
    response_data JSONB NOT NULL,
    update_date DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create basic indexes
CREATE INDEX idx_product_code ON fund_product(product_code);
CREATE INDEX idx_quote_detail_code ON fund_quote_detail(product_code);
CREATE INDEX idx_quote_summary_code ON fund_quote_summary(product_code);
CREATE INDEX idx_holding_allocation_code ON fund_holding_allocation(product_code);
CREATE INDEX idx_top_ten_holdings_code ON fund_top_ten_holdings(product_code);
CREATE INDEX idx_other_fund_classes_code ON fund_other_fund_classes(product_code);
