package com.hsbc.funds.repository;

import com.hsbc.funds.model.FundProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * JPA Repository for FundProduct entity
 */
@Repository
public interface FundProductRepository extends JpaRepository<FundProduct, Long> {
    
    List<FundProduct> findByProductCodeOrderByCreatedAtDesc(String productCode);
    
    List<FundProduct> findByUpdateDate(LocalDate updateDate);
    
    @Query("SELECT DISTINCT f.productCode FROM FundProduct f ORDER BY f.productCode")
    List<String> findDistinctProductCodes();
    
    long countByProductCode(String productCode);
}
