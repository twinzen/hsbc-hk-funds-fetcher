package com.hsbc.funds.repository;

import com.hsbc.funds.model.FundTopTenHoldings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * JPA Repository for FundTopTenHoldings entity
 */
@Repository
public interface FundTopTenHoldingsRepository extends JpaRepository<FundTopTenHoldings, Long> {
    
    List<FundTopTenHoldings> findByProductCodeOrderByCreatedAtDesc(String productCode);
    
    List<FundTopTenHoldings> findByUpdateDate(LocalDate updateDate);
}
