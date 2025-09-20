package com.hsbc.funds.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Response model for fund search API
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FundSearchResponse {
    
    @JsonProperty("pagination")
    private Pagination pagination;
    
    @JsonProperty("products")
    private List<Product> products;
    
    @JsonProperty("entityUpdatedTime")
    private String entityUpdatedTime;
    
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Pagination {
        @JsonProperty("totalNumberOfRecords")
        private int totalNumberOfRecords;
        
        @JsonProperty("numberOfRecords")
        private int numberOfRecords;
        
        @JsonProperty("startDetail")
        private int startDetail;
        
        @JsonProperty("endDetail")
        private int endDetail;
        
        // Getters and setters
        public int getTotalNumberOfRecords() { return totalNumberOfRecords; }
        public void setTotalNumberOfRecords(int totalNumberOfRecords) { this.totalNumberOfRecords = totalNumberOfRecords; }
        public int getNumberOfRecords() { return numberOfRecords; }
        public void setNumberOfRecords(int numberOfRecords) { this.numberOfRecords = numberOfRecords; }
        public int getStartDetail() { return startDetail; }
        public void setStartDetail(int startDetail) { this.startDetail = startDetail; }
        public int getEndDetail() { return endDetail; }
        public void setEndDetail(int endDetail) { this.endDetail = endDetail; }
    }
    
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Product {
        @JsonProperty("header")
        private Header header;
        
        // We don't need to parse the full response, just the header for product code
        // Other fields like summary, profile, rating, performance, risk, holdings, purchase are available
        
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Header {
            @JsonProperty("name")
            private String name;
            
            @JsonProperty("market")
            private String market;
            
            @JsonProperty("productType")
            private String productType;
            
            @JsonProperty("currency")
            private String currency;
            
            @JsonProperty("categoryCode")
            private String categoryCode;
            
            @JsonProperty("categoryName")
            private String categoryName;
            
            @JsonProperty("familyCode")
            private String familyCode;
            
            @JsonProperty("familyName")
            private String familyName;
            
            @JsonProperty("categoryLevel0Code")
            private String categoryLevel0Code;
            
            @JsonProperty("categoryLevel0Name")
            private String categoryLevel0Name;
            
            @JsonProperty("categoryLevel1Code")
            private String categoryLevel1Code;
            
            @JsonProperty("categoryLevel1Name")
            private String categoryLevel1Name;
            
            @JsonProperty("investmentRegionCode")
            private String investmentRegionCode;
            
            @JsonProperty("investmentRegionName")
            private String investmentRegionName;
            
            @JsonProperty("siFundCatCde")
            private String siFundCatCde;
            
            @JsonProperty("prodAltNumSeg")
            private List<ProdAltNumSeg> prodAltNumSeg;
            
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class ProdAltNumSeg {
                @JsonProperty("prodCdeAltClassCde")
                private String prodCdeAltClassCde;
                
                @JsonProperty("prodAltNum")
                private String prodAltNum;
                
                // Getters and setters
                public String getProdCdeAltClassCde() { return prodCdeAltClassCde; }
                public void setProdCdeAltClassCde(String prodCdeAltClassCde) { this.prodCdeAltClassCde = prodCdeAltClassCde; }
                public String getProdAltNum() { return prodAltNum; }
                public void setProdAltNum(String prodAltNum) { this.prodAltNum = prodAltNum; }
            }
            
            // Getters and setters
            public String getName() { return name; }
            public void setName(String name) { this.name = name; }
            public String getMarket() { return market; }
            public void setMarket(String market) { this.market = market; }
            public String getProductType() { return productType; }
            public void setProductType(String productType) { this.productType = productType; }
            public String getCurrency() { return currency; }
            public void setCurrency(String currency) { this.currency = currency; }
            public String getCategoryCode() { return categoryCode; }
            public void setCategoryCode(String categoryCode) { this.categoryCode = categoryCode; }
            public String getCategoryName() { return categoryName; }
            public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
            public String getFamilyCode() { return familyCode; }
            public void setFamilyCode(String familyCode) { this.familyCode = familyCode; }
            public String getFamilyName() { return familyName; }
            public void setFamilyName(String familyName) { this.familyName = familyName; }
            public String getCategoryLevel0Code() { return categoryLevel0Code; }
            public void setCategoryLevel0Code(String categoryLevel0Code) { this.categoryLevel0Code = categoryLevel0Code; }
            public String getCategoryLevel0Name() { return categoryLevel0Name; }
            public void setCategoryLevel0Name(String categoryLevel0Name) { this.categoryLevel0Name = categoryLevel0Name; }
            public String getCategoryLevel1Code() { return categoryLevel1Code; }
            public void setCategoryLevel1Code(String categoryLevel1Code) { this.categoryLevel1Code = categoryLevel1Code; }
            public String getCategoryLevel1Name() { return categoryLevel1Name; }
            public void setCategoryLevel1Name(String categoryLevel1Name) { this.categoryLevel1Name = categoryLevel1Name; }
            public String getInvestmentRegionCode() { return investmentRegionCode; }
            public void setInvestmentRegionCode(String investmentRegionCode) { this.investmentRegionCode = investmentRegionCode; }
            public String getInvestmentRegionName() { return investmentRegionName; }
            public void setInvestmentRegionName(String investmentRegionName) { this.investmentRegionName = investmentRegionName; }
            public String getSiFundCatCde() { return siFundCatCde; }
            public void setSiFundCatCde(String siFundCatCde) { this.siFundCatCde = siFundCatCde; }
            public List<ProdAltNumSeg> getProdAltNumSeg() { return prodAltNumSeg; }
            public void setProdAltNumSeg(List<ProdAltNumSeg> prodAltNumSeg) { this.prodAltNumSeg = prodAltNumSeg; }
        }
        
        // Getters and setters
        public Header getHeader() { return header; }
        public void setHeader(Header header) { this.header = header; }
    }
    
    // Getters and setters
    public Pagination getPagination() { return pagination; }
    public void setPagination(Pagination pagination) { this.pagination = pagination; }
    public List<Product> getProducts() { return products; }
    public void setProducts(List<Product> products) { this.products = products; }
    public String getEntityUpdatedTime() { return entityUpdatedTime; }
    public void setEntityUpdatedTime(String entityUpdatedTime) { this.entityUpdatedTime = entityUpdatedTime; }
}
