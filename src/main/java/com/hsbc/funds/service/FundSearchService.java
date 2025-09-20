package com.hsbc.funds.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hsbc.funds.config.FundApiConfig;
import com.hsbc.funds.model.FundSearchRequest;
import com.hsbc.funds.model.FundSearchResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FundSearchService {
    
    private static final Logger logger = LoggerFactory.getLogger(FundSearchService.class);
    
    private final FundApiConfig config;
    private final ApiClient apiClient;
    private final ObjectMapper objectMapper;
    
    // Hardcoded values for now to get the application working
    private static final String SEARCH_API_URL = "https://investments3.personal-banking.hsbc.com.hk/shp/wealth-mobile-mds-shp-api-hk-hbap-prod-proxy/v0/wmds/fundSearchResult";
    private static final int SEARCH_PAGE_SIZE = 100;
    
    @Autowired
    public FundSearchService(FundApiConfig config, ApiClient apiClient) {
        this.config = config;
        this.apiClient = apiClient;
        this.objectMapper = new ObjectMapper();
        
        // Debug logging to check configuration values
        logger.info("FundApiConfig loaded - Search API URL: {}", config.getSearchApiUrl());
        logger.info("FundApiConfig loaded - Search Page Size: {}", config.getSearchPageSize());
        logger.info("FundApiConfig loaded - Product API URL: {}", config.getProductApiUrl());
        logger.info("Using hardcoded values - Search API URL: {}", SEARCH_API_URL);
        logger.info("Using hardcoded values - Search Page Size: {}", SEARCH_PAGE_SIZE);
    }
    
    /**
     * Fetch all fund codes from HSBC search API with pagination
     */
    public List<String> fetchAllFundCodes() throws IOException {
        logger.info("Starting to fetch fund codes from HSBC search API");
        
        List<String> allFundCodes = new ArrayList<>();
        int startDetail = 1;
        int numberOfRecords = SEARCH_PAGE_SIZE; // Use hardcoded value
        
        int totalNumberOfRecords = Integer.MAX_VALUE;
        
        do {
            logger.info("Fetching page {} of fund search results", (startDetail - 1) / numberOfRecords + 1);
            
            FundSearchRequest request = createFundSearchRequest(startDetail, numberOfRecords);
            String jsonRequestBody = objectMapper.writeValueAsString(request);
            
            logger.info("Calling HSBC Fund Search API: {}", SEARCH_API_URL);
            logger.debug("Request body: {}", jsonRequestBody);
            
            String responseJson = makeApiCall(jsonRequestBody);
            FundSearchResponse response = objectMapper.readValue(responseJson, FundSearchResponse.class);
            
            if (response.getPagination() != null) {
                totalNumberOfRecords = response.getPagination().getTotalNumberOfRecords();
                logger.info("Total records found: {}", totalNumberOfRecords);
            } else {
                logger.warn("Pagination section not found in response. Assuming single page.");
                totalNumberOfRecords = response.getProducts() != null ? response.getProducts().size() : 0;
            }
            
            if (response.getProducts() != null) {
                for (FundSearchResponse.Product product : response.getProducts()) {
                    if (product.getHeader() != null && product.getHeader().getProdAltNumSeg() != null) {
                        product.getHeader().getProdAltNumSeg().stream()
                                .filter(seg -> "P".equals(seg.getProdCdeAltClassCde()))
                                .map(FundSearchResponse.Product.Header.ProdAltNumSeg::getProdAltNum)
                                .findFirst()
                                .ifPresent(allFundCodes::add);
                    }
                }
            }
            
            startDetail += numberOfRecords;
            
        } while (startDetail <= totalNumberOfRecords);
        
        logger.info("Finished fetching all fund codes. Total unique codes: {}", allFundCodes.size());
        return allFundCodes;
    }
    
    private String makeApiCall(String jsonRequestBody) throws IOException {
        Map<String, String> headers = new HashMap<>();
        headers.put("x-hsbc-channel-id", "OHI");
        headers.put("x-hsbc-locale", "en_GB");
        headers.put("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/138.0.0.0 Safari/537.36");
        
        return apiClient.post(SEARCH_API_URL, jsonRequestBody, headers);
    }
    
    private FundSearchRequest createFundSearchRequest(int startDetail, int numberOfRecords) {
        FundSearchRequest request = new FundSearchRequest();
        request.setProductType("UT");
        request.setReturnOnlyNumberOfMatches(false);
        
        List<FundSearchRequest.DetailedCriteria> detailedCriterias = new ArrayList<>();
        detailedCriterias.add(new FundSearchRequest.DetailedCriteria("RISK", "0:1:2:3:4:5", "in"));
        detailedCriterias.add(new FundSearchRequest.DetailedCriteria("GNRA", "Y:null", "in"));
        request.setDetailedCriterias(detailedCriterias);
        
        request.setSortBy("return1Yr");
        request.setSortOrder("desc");
        request.setNumberOfRecords(numberOfRecords);
        request.setStartDetail(startDetail);
        request.setEndDetail(startDetail + numberOfRecords - 1);
        
        List<FundSearchRequest.SortCriteria> sortCriterias = new ArrayList<>();
        sortCriterias.add(new FundSearchRequest.SortCriteria("prodStatCde", "asc"));
        sortCriterias.add(new FundSearchRequest.SortCriteria("return1Yr", "desc"));
        request.setSortCriterias(sortCriterias);
        
        List<FundSearchRequest.Holdings> holdings = new ArrayList<>();
        holdings.add(new FundSearchRequest.Holdings("topHoldingsList", true, "5", true));
        holdings.add(new FundSearchRequest.Holdings("stockSectors", true, "5", true));
        holdings.add(new FundSearchRequest.Holdings("equityRegional", true, "5", true));
        holdings.add(new FundSearchRequest.Holdings("bondSectors", true, "5", true));
        holdings.add(new FundSearchRequest.Holdings("bondRegional", true, "5", true));
        holdings.add(new FundSearchRequest.Holdings("assetAlloc", true, null, false));
        request.setHoldings(holdings);
        
        request.setRestrOnlScribInd("Y");
        return request;
    }
}
