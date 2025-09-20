package com.hsbc.funds.model;

import jakarta.persistence.*;

/**
 * JPA Entity for fund quote detail table
 */
@Entity
@Table(name = "fund_quote_detail")
public class FundQuoteDetail extends FundDataTable {
    
    public FundQuoteDetail() {
        super();
    }
    
    public FundQuoteDetail(String productCode, String responseData, java.time.LocalDate updateDate) {
        super(productCode, responseData, updateDate);
    }
}
