package com.hsbc.funds.model;

import jakarta.persistence.*;

/**
 * JPA Entity for fund holding allocation table
 */
@Entity
@Table(name = "fund_holding_allocation")
public class FundHoldingAllocation extends FundDataTable {
    
    public FundHoldingAllocation() {
        super();
    }
    
    public FundHoldingAllocation(String productCode, String responseData, java.time.LocalDate updateDate) {
        super(productCode, responseData, updateDate);
    }
}
