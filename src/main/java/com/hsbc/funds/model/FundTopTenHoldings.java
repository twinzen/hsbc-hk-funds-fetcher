package com.hsbc.funds.model;

import jakarta.persistence.*;

/**
 * JPA Entity for fund top ten holdings table
 */
@Entity
@Table(name = "fund_top_ten_holdings")
public class FundTopTenHoldings extends FundDataTable {
    
    public FundTopTenHoldings() {
        super();
    }
    
    public FundTopTenHoldings(String productCode, String responseData, java.time.LocalDate updateDate) {
        super(productCode, responseData, updateDate);
    }
}
