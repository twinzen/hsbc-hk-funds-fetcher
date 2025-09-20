package com.hsbc.funds.model;

import jakarta.persistence.*;

/**
 * JPA Entity for fund quote summary table
 */
@Entity
@Table(name = "fund_quote_summary")
public class FundQuoteSummary extends FundDataTable {
    
    public FundQuoteSummary() {
        super();
    }
    
    public FundQuoteSummary(String productCode, String responseData, java.time.LocalDate updateDate) {
        super(productCode, responseData, updateDate);
    }
}
