package com.hsbc.funds.model;

import jakarta.persistence.*;

/**
 * JPA Entity for fund other fund classes table
 */
@Entity
@Table(name = "fund_other_fund_classes")
public class FundOtherFundClasses extends FundDataTable {
    
    public FundOtherFundClasses() {
        super();
    }
    
    public FundOtherFundClasses(String productCode, String responseData, java.time.LocalDate updateDate) {
        super(productCode, responseData, updateDate);
    }
}
