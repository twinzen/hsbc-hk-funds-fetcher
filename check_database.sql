-- Check database status
SELECT 'fund_product' as table_name, COUNT(*) as record_count FROM fund_product
UNION ALL
SELECT 'fund_quote_detail' as table_name, COUNT(*) as record_count FROM fund_quote_detail
UNION ALL
SELECT 'fund_quote_summary' as table_name, COUNT(*) as record_count FROM fund_quote_summary
UNION ALL
SELECT 'fund_holding_allocation' as table_name, COUNT(*) as record_count FROM fund_holding_allocation
UNION ALL
SELECT 'fund_top_ten_holdings' as table_name, COUNT(*) as record_count FROM fund_top_ten_holdings
UNION ALL
SELECT 'fund_other_fund_classes' as table_name, COUNT(*) as record_count FROM fund_other_fund_classes
ORDER BY table_name;
