package com.hsbc.funds.model;

import jakarta.persistence.*;

/**
 * JPA Entity for fund product table
 */
@Entity
@Table(name = "fund_product")
public class FundProduct extends FundDataTable {
    
    public FundProduct() {
        super();
    }
    
    public FundProduct(String productCode, String responseData, java.time.LocalDate updateDate) {
        super(productCode, responseData, updateDate);
    }
}
