package com.hsbc.funds.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "fund")
public class FundApiConfig {
    private String searchApiUrl;
    private int searchPageSize;
    private String productApiUrl;
    private String quoteDetailApiUrl;
    private String fundQuoteSummaryApiUrl;
    private String holdingAllocationApiUrl;
    private String topTenHoldingsApiUrl;
    private String otherFundClassesApiUrl;

    // Getters and Setters
    public String getSearchApiUrl() {
        return searchApiUrl;
    }

    public void setSearchApiUrl(String searchApiUrl) {
        this.searchApiUrl = searchApiUrl;
    }

    public int getSearchPageSize() {
        return searchPageSize;
    }

    public void setSearchPageSize(int searchPageSize) {
        this.searchPageSize = searchPageSize;
    }

    public String getProductApiUrl() {
        return productApiUrl;
    }

    public void setProductApiUrl(String productApiUrl) {
        this.productApiUrl = productApiUrl;
    }

    public String getQuoteDetailApiUrl() {
        return quoteDetailApiUrl;
    }

    public void setQuoteDetailApiUrl(String quoteDetailApiUrl) {
        this.quoteDetailApiUrl = quoteDetailApiUrl;
    }

    public String getFundQuoteSummaryApiUrl() {
        return fundQuoteSummaryApiUrl;
    }

    public void setFundQuoteSummaryApiUrl(String fundQuoteSummaryApiUrl) {
        this.fundQuoteSummaryApiUrl = fundQuoteSummaryApiUrl;
    }

    public String getHoldingAllocationApiUrl() {
        return holdingAllocationApiUrl;
    }

    public void setHoldingAllocationApiUrl(String holdingAllocationApiUrl) {
        this.holdingAllocationApiUrl = holdingAllocationApiUrl;
    }

    public String getTopTenHoldingsApiUrl() {
        return topTenHoldingsApiUrl;
    }

    public void setTopTenHoldingsApiUrl(String topTenHoldingsApiUrl) {
        this.topTenHoldingsApiUrl = topTenHoldingsApiUrl;
    }

    public String getOtherFundClassesApiUrl() {
        return otherFundClassesApiUrl;
    }

    public void setOtherFundClassesApiUrl(String otherFundClassesApiUrl) {
        this.otherFundClassesApiUrl = otherFundClassesApiUrl;
    }
}
