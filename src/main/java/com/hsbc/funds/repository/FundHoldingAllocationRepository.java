package com.hsbc.funds.repository;

import com.hsbc.funds.model.FundHoldingAllocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * JPA Repository for FundHoldingAllocation entity
 */
@Repository
public interface FundHoldingAllocationRepository extends JpaRepository<FundHoldingAllocation, Long> {
    
    List<FundHoldingAllocation> findByProductCodeOrderByCreatedAtDesc(String productCode);
    
    List<FundHoldingAllocation> findByUpdateDate(LocalDate updateDate);
}
