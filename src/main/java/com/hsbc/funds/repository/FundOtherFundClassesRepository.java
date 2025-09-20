package com.hsbc.funds.repository;

import com.hsbc.funds.model.FundOtherFundClasses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * JPA Repository for FundOtherFundClasses entity
 */
@Repository
public interface FundOtherFundClassesRepository extends JpaRepository<FundOtherFundClasses, Long> {
    
    List<FundOtherFundClasses> findByProductCodeOrderByCreatedAtDesc(String productCode);
    
    List<FundOtherFundClasses> findByUpdateDate(LocalDate updateDate);
}
