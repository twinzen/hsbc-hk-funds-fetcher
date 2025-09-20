package com.hsbc.funds.repository;

import com.hsbc.funds.model.FundQuoteSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * JPA Repository for FundQuoteSummary entity
 */
@Repository
public interface FundQuoteSummaryRepository extends JpaRepository<FundQuoteSummary, Long> {
    
    List<FundQuoteSummary> findByProductCodeOrderByCreatedAtDesc(String productCode);
    
    List<FundQuoteSummary> findByUpdateDate(LocalDate updateDate);
}
