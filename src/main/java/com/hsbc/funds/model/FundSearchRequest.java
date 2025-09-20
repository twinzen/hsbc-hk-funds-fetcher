package com.hsbc.funds.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Request model for fund search API
 */
public class FundSearchRequest {
    
    @JsonProperty("productType")
    private String productType;
    
    @JsonProperty("returnOnlyNumberOfMatches")
    private boolean returnOnlyNumberOfMatches;
    
    @JsonProperty("detailedCriterias")
    private List<DetailedCriteria> detailedCriterias;
    
    @JsonProperty("sortBy")
    private String sortBy;
    
    @JsonProperty("sortOrder")
    private String sortOrder;
    
    @JsonProperty("numberOfRecords")
    private int numberOfRecords;
    
    @JsonProperty("startDetail")
    private int startDetail;
    
    @JsonProperty("endDetail")
    private int endDetail;
    
    @JsonProperty("sortCriterias")
    private List<SortCriteria> sortCriterias;
    
    @JsonProperty("holdings")
    private List<Holdings> holdings;
    
    @JsonProperty("restrOnlScribInd")
    private String restrOnlScribInd;
    
    // Getters and Setters
    public String getProductType() { return productType; }
    public void setProductType(String productType) { this.productType = productType; }
    
    public boolean isReturnOnlyNumberOfMatches() { return returnOnlyNumberOfMatches; }
    public void setReturnOnlyNumberOfMatches(boolean returnOnlyNumberOfMatches) { this.returnOnlyNumberOfMatches = returnOnlyNumberOfMatches; }
    
    public List<DetailedCriteria> getDetailedCriterias() { return detailedCriterias; }
    public void setDetailedCriterias(List<DetailedCriteria> detailedCriterias) { this.detailedCriterias = detailedCriterias; }
    
    public String getSortBy() { return sortBy; }
    public void setSortBy(String sortBy) { this.sortBy = sortBy; }
    
    public String getSortOrder() { return sortOrder; }
    public void setSortOrder(String sortOrder) { this.sortOrder = sortOrder; }
    
    public int getNumberOfRecords() { return numberOfRecords; }
    public void setNumberOfRecords(int numberOfRecords) { this.numberOfRecords = numberOfRecords; }
    
    public int getStartDetail() { return startDetail; }
    public void setStartDetail(int startDetail) { this.startDetail = startDetail; }
    
    public int getEndDetail() { return endDetail; }
    public void setEndDetail(int endDetail) { this.endDetail = endDetail; }
    
    public List<SortCriteria> getSortCriterias() { return sortCriterias; }
    public void setSortCriterias(List<SortCriteria> sortCriterias) { this.sortCriterias = sortCriterias; }
    
    public List<Holdings> getHoldings() { return holdings; }
    public void setHoldings(List<Holdings> holdings) { this.holdings = holdings; }
    
    public String getRestrOnlScribInd() { return restrOnlScribInd; }
    public void setRestrOnlScribInd(String restrOnlScribInd) { this.restrOnlScribInd = restrOnlScribInd; }
    
    // Inner classes
    public static class DetailedCriteria {
        @JsonProperty("criteriaKey")
        private String criteriaKey;
        
        @JsonProperty("criteriaValue")
        private String criteriaValue;
        
        @JsonProperty("operator")
        private String operator;
        
        public DetailedCriteria() {}
        
        public DetailedCriteria(String criteriaKey, String criteriaValue, String operator) {
            this.criteriaKey = criteriaKey;
            this.criteriaValue = criteriaValue;
            this.operator = operator;
        }
        
        // Getters and Setters
        public String getCriteriaKey() { return criteriaKey; }
        public void setCriteriaKey(String criteriaKey) { this.criteriaKey = criteriaKey; }
        
        public String getCriteriaValue() { return criteriaValue; }
        public void setCriteriaValue(String criteriaValue) { this.criteriaValue = criteriaValue; }
        
        public String getOperator() { return operator; }
        public void setOperator(String operator) { this.operator = operator; }
    }
    
    public static class SortCriteria {
        @JsonProperty("sortKey")
        private String sortKey;
        
        @JsonProperty("sortOrder")
        private String sortOrder;
        
        public SortCriteria() {}
        
        public SortCriteria(String sortKey, String sortOrder) {
            this.sortKey = sortKey;
            this.sortOrder = sortOrder;
        }
        
        // Getters and Setters
        public String getSortKey() { return sortKey; }
        public void setSortKey(String sortKey) { this.sortKey = sortKey; }
        
        public String getSortOrder() { return sortOrder; }
        public void setSortOrder(String sortOrder) { this.sortOrder = sortOrder; }
    }
    
    public static class Holdings {
        @JsonProperty("criteriaKey")
        private String criteriaKey;
        
        @JsonProperty("criteriaValue")
        private boolean criteriaValue;
        
        @JsonProperty("top")
        private String top;
        
        @JsonProperty("others")
        private boolean others;
        
        public Holdings() {}
        
        public Holdings(String criteriaKey, boolean criteriaValue, String top, boolean others) {
            this.criteriaKey = criteriaKey;
            this.criteriaValue = criteriaValue;
            this.top = top;
            this.others = others;
        }
        
        // Getters and Setters
        public String getCriteriaKey() { return criteriaKey; }
        public void setCriteriaKey(String criteriaKey) { this.criteriaKey = criteriaKey; }
        
        public boolean isCriteriaValue() { return criteriaValue; }
        public void setCriteriaValue(boolean criteriaValue) { this.criteriaValue = criteriaValue; }
        
        public String getTop() { return top; }
        public void setTop(String top) { this.top = top; }
        
        public boolean isOthers() { return others; }
        public void setOthers(boolean others) { this.others = others; }
    }
}
