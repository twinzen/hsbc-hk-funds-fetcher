-- Corrected query that handles null assetClasses properly
SELECT 
    product_code AS fund_code,
    asset_class->>'fundShreClsName' AS fund_class_name,
    prod_alt_num_segment->>'prodAltNum' AS fund_class_fund_code
FROM 
    fund_other_fund_classes
    LEFT JOIN LATERAL (
        SELECT asset_class
        FROM jsonb_array_elements(
            COALESCE(response_data->'assetClasses', '[]'::jsonb)
        ) AS asset_class
    ) AS ac ON true
    LEFT JOIN LATERAL (
        SELECT prod_alt_num_segment
        FROM jsonb_array_elements(
            COALESCE(ac.asset_class->'prodAltNumSegs', '[]'::jsonb)
        ) AS prod_alt_num_segment
        WHERE prod_alt_num_segment->>'prodCdeAltClassCde' = 'M'
    ) AS pans ON true
WHERE 
    response_data IS NOT NULL
    AND response_data->'assetClasses' IS NOT NULL  -- Only include records that have assetClasses
    AND jsonb_typeof(response_data->'assetClasses') = 'array'  -- Only include records where assetClasses is an array
    AND ac.asset_class IS NOT NULL
    AND asset_class->>'fundShreClsName' IS NOT NULL;
