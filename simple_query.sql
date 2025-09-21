-- Simpler approach: filter out records with null assetClasses first
SELECT 
    product_code AS fund_code,
    asset_class->>'fundShreClsName' AS fund_class_name,
    prod_alt_num_segment->>'prodAltNum' AS fund_class_fund_code
FROM 
    fund_other_fund_classes
    LEFT JOIN LATERAL (
        SELECT asset_class
        FROM jsonb_array_elements(response_data->'assetClasses') AS asset_class
    ) AS ac ON true
    LEFT JOIN LATERAL (
        SELECT prod_alt_num_segment
        FROM jsonb_array_elements(ac.asset_class->'prodAltNumSegs') AS prod_alt_num_segment
        WHERE prod_alt_num_segment->>'prodCdeAltClassCde' = 'M'
    ) AS pans ON true
WHERE 
    response_data IS NOT NULL
    AND response_data->'assetClasses' IS NOT NULL
    AND jsonb_typeof(response_data->'assetClasses') = 'array'
    AND ac.asset_class IS NOT NULL;
