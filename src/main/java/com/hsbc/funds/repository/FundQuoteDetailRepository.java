package com.hsbc.funds.repository;

import com.hsbc.funds.model.FundQuoteDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * JPA Repository for FundQuoteDetail entity
 */
@Repository
public interface FundQuoteDetailRepository extends JpaRepository<FundQuoteDetail, Long> {
    
    List<FundQuoteDetail> findByProductCodeOrderByCreatedAtDesc(String productCode);
    
    List<FundQuoteDetail> findByUpdateDate(LocalDate updateDate);
}
