-- Analyze the data structure to understand what we're working with
SELECT 
    product_code,
    jsonb_typeof(response_data->'assetClasses') as asset_classes_type,
    CASE 
        WHEN response_data->'assetClasses' IS NULL THEN 'NULL'
        WHEN jsonb_typeof(response_data->'assetClasses') = 'array' THEN 'ARRAY'
        ELSE 'OTHER'
    END as asset_classes_status,
    CASE 
        WHEN response_data->'assetClasses' IS NULL THEN 'No assetClasses'
        WHEN jsonb_typeof(response_data->'assetClasses') = 'array' THEN 
            'Array with ' || jsonb_array_length(response_data->'assetClasses') || ' elements'
        ELSE 'Not an array'
    END as description
FROM fund_other_fund_classes
WHERE response_data IS NOT NULL
ORDER BY asset_classes_type, product_code
LIMIT 20;
