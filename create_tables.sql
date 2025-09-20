-- PostgreSQL SQL Script to create tables for HSBC Fund Data Fetcher
-- This script creates 6 tables to store responses from the 6 different APIs

-- Enable UUID extension if needed
-- CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- 1. Fund Product Table (Product API)
CREATE TABLE IF NOT EXISTS fund_product (
    id BIGSERIAL PRIMARY KEY,
    product_code VARCHAR(50) NOT NULL,
    response_data JSONB NOT NULL,
    update_date DATE NOT NULL,
    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_fund_product_code UNIQUE (product_code)
);

-- 2. Fund Quote Detail Table (Quote Detail API)
CREATE TABLE IF NOT EXISTS fund_quote_detail (
    id BIGSERIAL PRIMARY KEY,
    product_code VARCHAR(50) NOT NULL,
    response_data JSONB NOT NULL,
    update_date DATE NOT NULL,
    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_fund_quote_detail_code UNIQUE (product_code)
);

-- 3. Fund Quote Summary Table (Fund Quote Summary API)
CREATE TABLE IF NOT EXISTS fund_quote_summary (
    id BIGSERIAL PRIMARY KEY,
    product_code VARCHAR(50) NOT NULL,
    response_data JSONB NOT NULL,
    update_date DATE NOT NULL,
    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_fund_quote_summary_code UNIQUE (product_code)
);

-- 4. Fund Holding Allocation Table (Holding Allocation API)
CREATE TABLE IF NOT EXISTS fund_holding_allocation (
    id BIGSERIAL PRIMARY KEY,
    product_code VARCHAR(50) NOT NULL,
    response_data JSONB NOT NULL,
    update_date DATE NOT NULL,
    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_fund_holding_allocation_code UNIQUE (product_code)
);

-- 5. Fund Top Ten Holdings Table (Top Ten Holdings API)
CREATE TABLE IF NOT EXISTS fund_top_ten_holdings (
    id BIGSERIAL PRIMARY KEY,
    product_code VARCHAR(50) NOT NULL,
    response_data JSONB NOT NULL,
    update_date DATE NOT NULL,
    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_fund_top_ten_holdings_code UNIQUE (product_code)
);

-- 6. Fund Other Fund Classes Table (Other Fund Classes API)
CREATE TABLE IF NOT EXISTS fund_other_fund_classes (
    id BIGSERIAL PRIMARY KEY,
    product_code VARCHAR(50) NOT NULL,
    response_data JSONB NOT NULL,
    update_date DATE NOT NULL,
    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_fund_other_fund_classes_code UNIQUE (product_code)
);

-- Create indexes for better query performance
CREATE INDEX IF NOT EXISTS idx_fund_product_code ON fund_product(product_code);
CREATE INDEX IF NOT EXISTS idx_fund_product_update_date ON fund_product(update_date);
CREATE INDEX IF NOT EXISTS idx_fund_product_created_at ON fund_product(created_at);

CREATE INDEX IF NOT EXISTS idx_fund_quote_detail_code ON fund_quote_detail(product_code);
CREATE INDEX IF NOT EXISTS idx_fund_quote_detail_update_date ON fund_quote_detail(update_date);
CREATE INDEX IF NOT EXISTS idx_fund_quote_detail_created_at ON fund_quote_detail(created_at);

CREATE INDEX IF NOT EXISTS idx_fund_quote_summary_code ON fund_quote_summary(product_code);
CREATE INDEX IF NOT EXISTS idx_fund_quote_summary_update_date ON fund_quote_summary(update_date);
CREATE INDEX IF NOT EXISTS idx_fund_quote_summary_created_at ON fund_quote_summary(created_at);

CREATE INDEX IF NOT EXISTS idx_fund_holding_allocation_code ON fund_holding_allocation(product_code);
CREATE INDEX IF NOT EXISTS idx_fund_holding_allocation_update_date ON fund_holding_allocation(update_date);
CREATE INDEX IF NOT EXISTS idx_fund_holding_allocation_created_at ON fund_holding_allocation(created_at);

CREATE INDEX IF NOT EXISTS idx_fund_top_ten_holdings_code ON fund_top_ten_holdings(product_code);
CREATE INDEX IF NOT EXISTS idx_fund_top_ten_holdings_update_date ON fund_top_ten_holdings(update_date);
CREATE INDEX IF NOT EXISTS idx_fund_top_ten_holdings_created_at ON fund_top_ten_holdings(created_at);

CREATE INDEX IF NOT EXISTS idx_fund_other_fund_classes_code ON fund_other_fund_classes(product_code);
CREATE INDEX IF NOT EXISTS idx_fund_other_fund_classes_update_date ON fund_other_fund_classes(update_date);
CREATE INDEX IF NOT EXISTS idx_fund_other_fund_classes_created_at ON fund_other_fund_classes(created_at);

-- Create JSONB indexes for efficient querying of JSON data
CREATE INDEX IF NOT EXISTS idx_fund_product_response_gin ON fund_product USING GIN (response_data);
CREATE INDEX IF NOT EXISTS idx_fund_quote_detail_response_gin ON fund_quote_detail USING GIN (response_data);
CREATE INDEX IF NOT EXISTS idx_fund_quote_summary_response_gin ON fund_quote_summary USING GIN (response_data);
CREATE INDEX IF NOT EXISTS idx_fund_holding_allocation_response_gin ON fund_holding_allocation USING GIN (response_data);
CREATE INDEX IF NOT EXISTS idx_fund_top_ten_holdings_response_gin ON fund_top_ten_holdings USING GIN (response_data);
CREATE INDEX IF NOT EXISTS idx_fund_other_fund_classes_response_gin ON fund_other_fund_classes USING GIN (response_data);

-- Create a view to get all fund data for a specific product code
CREATE OR REPLACE VIEW fund_data_complete AS
SELECT 
    p.product_code,
    p.response_data as product_data,
    qd.response_data as quote_detail_data,
    qs.response_data as quote_summary_data,
    ha.response_data as holding_allocation_data,
    tth.response_data as top_ten_holdings_data,
    ofc.response_data as other_fund_classes_data,
    p.update_date,
    p.created_at
FROM fund_product p
LEFT JOIN fund_quote_detail qd ON p.product_code = qd.product_code
LEFT JOIN fund_quote_summary qs ON p.product_code = qs.product_code
LEFT JOIN fund_holding_allocation ha ON p.product_code = ha.product_code
LEFT JOIN fund_top_ten_holdings tth ON p.product_code = tth.product_code
LEFT JOIN fund_other_fund_classes ofc ON p.product_code = ofc.product_code;

-- Grant permissions (adjust as needed for your environment)
-- GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO your_app_user;
-- GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA public TO your_app_user;

-- Display table information
SELECT 
    table_name,
    column_name,
    data_type,
    is_nullable,
    column_default
FROM information_schema.columns 
WHERE table_name IN (
    'fund_product', 
    'fund_quote_detail', 
    'fund_quote_summary', 
    'fund_holding_allocation', 
    'fund_top_ten_holdings', 
    'fund_other_fund_classes'
)
ORDER BY table_name, ordinal_position;

COMMENT ON TABLE fund_product IS 'Stores responses from the Product API';
COMMENT ON TABLE fund_quote_detail IS 'Stores responses from the Quote Detail API';
COMMENT ON TABLE fund_quote_summary IS 'Stores responses from the Fund Quote Summary API';
COMMENT ON TABLE fund_holding_allocation IS 'Stores responses from the Holding Allocation API';
COMMENT ON TABLE fund_top_ten_holdings IS 'Stores responses from the Top Ten Holdings API';
COMMENT ON TABLE fund_other_fund_classes IS 'Stores responses from the Other Fund Classes API';

COMMENT ON COLUMN fund_product.response_data IS 'JSONB column storing the complete API response';
COMMENT ON COLUMN fund_product.product_code IS 'Fund product code used as foreign key across all tables';
COMMENT ON COLUMN fund_product.update_date IS 'Date when the data was last updated (YYYY-MM-DD format)';
COMMENT ON COLUMN fund_product.created_at IS 'Timestamp when the record was created in the database';
