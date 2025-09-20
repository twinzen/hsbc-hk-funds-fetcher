package com.hsbc.funds.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Base entity class for all fund data tables
 */
@MappedSuperclass
public abstract class FundDataTable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "product_code", nullable = false, length = 50)
    private String productCode;
    
    @Column(name = "response_data", columnDefinition = "jsonb", nullable = false)
    @JdbcTypeCode(SqlTypes.JSON)
    private JsonNode responseData;
    
    @Column(name = "update_date", nullable = false)
    private LocalDate updateDate;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private java.time.LocalDateTime createdAt;
    
    public FundDataTable() {
        this.createdAt = java.time.LocalDateTime.now();
    }
    
    public FundDataTable(String productCode, String responseData, LocalDate updateDate) {
        this();
        this.productCode = productCode;
        this.updateDate = updateDate;
        try {
            ObjectMapper mapper = new ObjectMapper();
            this.responseData = mapper.readTree(responseData);
        } catch (Exception e) {
            throw new RuntimeException("Error parsing JSON response data", e);
        }
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getProductCode() {
        return productCode;
    }
    
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
    
    public JsonNode getResponseData() {
        return responseData;
    }
    
    public void setResponseData(JsonNode responseData) {
        this.responseData = responseData;
    }
    
    public LocalDate getUpdateDate() {
        return updateDate;
    }
    
    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }
    
    public java.time.LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(java.time.LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
