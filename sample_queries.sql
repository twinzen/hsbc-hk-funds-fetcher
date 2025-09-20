-- Sample PostgreSQL queries for HSBC Fund Data Fetcher
-- These queries demonstrate how to work with the JSONB data

-- 1. Get all fund codes from the product table
SELECT DISTINCT product_code FROM fund_product ORDER BY product_code;

-- 2. Get the latest data for a specific fund
SELECT 
    product_code,
    response_data,
    update_date,
    created_at
FROM fund_product 
WHERE product_code = 'U62680'
ORDER BY created_at DESC 
LIMIT 1;

-- 3. Query specific fields from JSONB data (Product API)
SELECT 
    product_code,
    response_data->'payload'->0->>'productAlternativeNumber' as product_alt_number,
    response_data->'payload'->0->'attributeMap'->>'prodName' as product_name,
    response_data->'payload'->0->'attributeMap'->>'riskLvlCde' as risk_level,
    response_data->'payload'->0->'attributeMap'->>'prodNavPrcAmt' as nav_price,
    update_date
FROM fund_product 
WHERE product_code = 'U62680';

-- 4. Get all funds with their risk levels
SELECT 
    product_code,
    response_data->'payload'->0->'attributeMap'->>'riskLvlCde' as risk_level,
    response_data->'payload'->0->'attributeMap'->>'prodName' as product_name
FROM fund_product 
WHERE response_data->'payload'->0->'attributeMap'->>'riskLvlCde' IS NOT NULL
ORDER BY risk_level;

-- 5. Count funds by risk level
SELECT 
    response_data->'payload'->0->'attributeMap'->>'riskLvlCde' as risk_level,
    COUNT(*) as fund_count
FROM fund_product 
WHERE response_data->'payload'->0->'attributeMap'->>'riskLvlCde' IS NOT NULL
GROUP BY risk_level
ORDER BY risk_level;

-- 6. Get funds updated on a specific date
SELECT 
    product_code,
    response_data->'payload'->0->'attributeMap'->>'prodName' as product_name,
    update_date
FROM fund_product 
WHERE update_date = '2025-09-19'
ORDER BY product_code;

-- 7. Get all data for a specific fund across all APIs
SELECT 
    'product' as api_type,
    product_code,
    response_data,
    update_date
FROM fund_product 
WHERE product_code = 'U62680'

UNION ALL

SELECT 
    'quote_detail' as api_type,
    product_code,
    response_data,
    update_date
FROM fund_quote_detail 
WHERE product_code = 'U62680'

UNION ALL

SELECT 
    'quote_summary' as api_type,
    product_code,
    response_data,
    update_date
FROM fund_quote_summary 
WHERE product_code = 'U62680'

UNION ALL

SELECT 
    'holding_allocation' as api_type,
    product_code,
    response_data,
    update_date
FROM fund_holding_allocation 
WHERE product_code = 'U62680'

UNION ALL

SELECT 
    'top_ten_holdings' as api_type,
    product_code,
    response_data,
    update_date
FROM fund_top_ten_holdings 
WHERE product_code = 'U62680'

UNION ALL

SELECT 
    'other_fund_classes' as api_type,
    product_code,
    response_data,
    update_date
FROM fund_other_fund_classes 
WHERE product_code = 'U62680'

ORDER BY api_type;

-- 8. Find funds with specific characteristics using JSONB operators
-- Find funds with risk level 5
SELECT 
    product_code,
    response_data->'payload'->0->'attributeMap'->>'prodName' as product_name,
    response_data->'payload'->0->'attributeMap'->>'riskLvlCde' as risk_level
FROM fund_product 
WHERE response_data @> '{"payload": [{"attributeMap": {"riskLvlCde": "5"}}]}';

-- 9. Get funds with NAV price greater than 10
SELECT 
    product_code,
    response_data->'payload'->0->'attributeMap'->>'prodName' as product_name,
    response_data->'payload'->0->'attributeMap'->>'prodNavPrcAmt' as nav_price
FROM fund_product 
WHERE (response_data->'payload'->0->'attributeMap'->>'prodNavPrcAmt')::numeric > 10
ORDER BY (response_data->'payload'->0->'attributeMap'->>'prodNavPrcAmt')::numeric DESC;

-- 10. Get statistics about the data
SELECT 
    'Total Funds' as metric,
    COUNT(DISTINCT product_code) as value
FROM fund_product

UNION ALL

SELECT 
    'Product Records' as metric,
    COUNT(*)::text as value
FROM fund_product

UNION ALL

SELECT 
    'Quote Detail Records' as metric,
    COUNT(*)::text as value
FROM fund_quote_detail

UNION ALL

SELECT 
    'Quote Summary Records' as metric,
    COUNT(*)::text as value
FROM fund_quote_summary

UNION ALL

SELECT 
    'Holding Allocation Records' as metric,
    COUNT(*)::text as value
FROM fund_holding_allocation

UNION ALL

SELECT 
    'Top Ten Holdings Records' as metric,
    COUNT(*)::text as value
FROM fund_top_ten_holdings

UNION ALL

SELECT 
    'Other Fund Classes Records' as metric,
    COUNT(*)::text as value
FROM fund_other_fund_classes;
