package com.hsbc.funds.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hsbc.funds.model.*;
import com.hsbc.funds.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class FundDataService {
    private static final Logger logger = LoggerFactory.getLogger(FundDataService.class);
    
    private final FundSearchService fundSearchService;
    private final ProductApiService productApiService;
    private final QuoteDetailApiService quoteDetailApiService;
    private final FundQuoteSummaryApiService fundQuoteSummaryApiService;
    private final HoldingAllocationApiService holdingAllocationApiService;
    private final TopTenHoldingsApiService topTenHoldingsApiService;
    private final OtherFundClassesApiService otherFundClassesApiService;
    
    private final FundProductRepository fundProductRepository;
    private final FundQuoteDetailRepository fundQuoteDetailRepository;
    private final FundQuoteSummaryRepository fundQuoteSummaryRepository;
    private final FundHoldingAllocationRepository fundHoldingAllocationRepository;
    private final FundTopTenHoldingsRepository fundTopTenHoldingsRepository;
    private final FundOtherFundClassesRepository fundOtherFundClassesRepository;
    
    private final ExecutorService executorService;
    private final ObjectMapper objectMapper;
    
    @Autowired
    public FundDataService(FundSearchService fundSearchService,
                          ProductApiService productApiService,
                          QuoteDetailApiService quoteDetailApiService,
                          FundQuoteSummaryApiService fundQuoteSummaryApiService,
                          HoldingAllocationApiService holdingAllocationApiService,
                          TopTenHoldingsApiService topTenHoldingsApiService,
                          OtherFundClassesApiService otherFundClassesApiService,
                          FundProductRepository fundProductRepository,
                          FundQuoteDetailRepository fundQuoteDetailRepository,
                          FundQuoteSummaryRepository fundQuoteSummaryRepository,
                          FundHoldingAllocationRepository fundHoldingAllocationRepository,
                          FundTopTenHoldingsRepository fundTopTenHoldingsRepository,
                          FundOtherFundClassesRepository fundOtherFundClassesRepository) {
        this.fundSearchService = fundSearchService;
        this.productApiService = productApiService;
        this.quoteDetailApiService = quoteDetailApiService;
        this.fundQuoteSummaryApiService = fundQuoteSummaryApiService;
        this.holdingAllocationApiService = holdingAllocationApiService;
        this.topTenHoldingsApiService = topTenHoldingsApiService;
        this.otherFundClassesApiService = otherFundClassesApiService;
        this.fundProductRepository = fundProductRepository;
        this.fundQuoteDetailRepository = fundQuoteDetailRepository;
        this.fundQuoteSummaryRepository = fundQuoteSummaryRepository;
        this.fundHoldingAllocationRepository = fundHoldingAllocationRepository;
        this.fundTopTenHoldingsRepository = fundTopTenHoldingsRepository;
        this.fundOtherFundClassesRepository = fundOtherFundClassesRepository;
        this.executorService = Executors.newFixedThreadPool(10);
        this.objectMapper = new ObjectMapper();
    }
    
    @Transactional
    public void fetchAllFundData() throws IOException {
        logger.info("Starting fund data fetching process");
        
        List<String> fundCodes = fundSearchService.fetchAllFundCodes();
        logger.info("Fetched {} fund codes. Starting parallel API calls.", fundCodes.size());
        
        for (String productCode : fundCodes) {
            processFundCode(productCode);
        }
        
        executorService.shutdown();
        logger.info("Completed processing all fund codes");
    }
    
    private void processFundCode(String productCode) {
        LocalDate updateDate = LocalDate.now();
        
        CompletableFuture<Void> productTask = CompletableFuture.runAsync(() -> {
            try {
                String response = productApiService.getProductData(productCode);
                JsonNode jsonNode = objectMapper.readTree(response);
                FundProduct fundProduct = new FundProduct();
                fundProduct.setProductCode(productCode);
                fundProduct.setResponseData(jsonNode);
                fundProduct.setUpdateDate(updateDate);
                fundProductRepository.save(fundProduct);
                logger.debug("Saved product data for fund: {}", productCode);
            } catch (Exception e) {
                logger.error("Error fetching product data for fund {}: {}", productCode, e.getMessage());
            }
        }, executorService);
        
        CompletableFuture<Void> quoteDetailTask = CompletableFuture.runAsync(() -> {
            try {
                String response = quoteDetailApiService.getQuoteDetailData(productCode);
                JsonNode jsonNode = objectMapper.readTree(response);
                FundQuoteDetail quoteDetail = new FundQuoteDetail();
                quoteDetail.setProductCode(productCode);
                quoteDetail.setResponseData(jsonNode);
                quoteDetail.setUpdateDate(updateDate);
                fundQuoteDetailRepository.save(quoteDetail);
                logger.debug("Saved quote detail data for fund: {}", productCode);
            } catch (Exception e) {
                logger.error("Error fetching quote detail data for fund {}: {}", productCode, e.getMessage());
            }
        }, executorService);
        
        CompletableFuture<Void> fundQuoteSummaryTask = CompletableFuture.runAsync(() -> {
            try {
                String response = fundQuoteSummaryApiService.getFundQuoteSummaryData(productCode);
                JsonNode jsonNode = objectMapper.readTree(response);
                FundQuoteSummary quoteSummary = new FundQuoteSummary();
                quoteSummary.setProductCode(productCode);
                quoteSummary.setResponseData(jsonNode);
                quoteSummary.setUpdateDate(updateDate);
                fundQuoteSummaryRepository.save(quoteSummary);
                logger.debug("Saved fund quote summary data for fund: {}", productCode);
            } catch (Exception e) {
                logger.error("Error fetching fund quote summary data for fund {}: {}", productCode, e.getMessage());
            }
        }, executorService);
        
        CompletableFuture<Void> holdingAllocationTask = CompletableFuture.runAsync(() -> {
            try {
                String response = holdingAllocationApiService.getHoldingAllocationData(productCode);
                JsonNode jsonNode = objectMapper.readTree(response);
                FundHoldingAllocation holdingAllocation = new FundHoldingAllocation();
                holdingAllocation.setProductCode(productCode);
                holdingAllocation.setResponseData(jsonNode);
                holdingAllocation.setUpdateDate(updateDate);
                fundHoldingAllocationRepository.save(holdingAllocation);
                logger.debug("Saved holding allocation data for fund: {}", productCode);
            } catch (Exception e) {
                logger.error("Error fetching holding allocation data for fund {}: {}", productCode, e.getMessage());
            }
        }, executorService);
        
        CompletableFuture<Void> topTenHoldingsTask = CompletableFuture.runAsync(() -> {
            try {
                String response = topTenHoldingsApiService.getTopTenHoldingsData(productCode);
                JsonNode jsonNode = objectMapper.readTree(response);
                FundTopTenHoldings topTenHoldings = new FundTopTenHoldings();
                topTenHoldings.setProductCode(productCode);
                topTenHoldings.setResponseData(jsonNode);
                topTenHoldings.setUpdateDate(updateDate);
                fundTopTenHoldingsRepository.save(topTenHoldings);
                logger.debug("Saved top ten holdings data for fund: {}", productCode);
            } catch (Exception e) {
                logger.error("Error fetching top ten holdings data for fund {}: {}", productCode, e.getMessage());
            }
        }, executorService);
        
        CompletableFuture<Void> otherFundClassesTask = CompletableFuture.runAsync(() -> {
            try {
                String response = otherFundClassesApiService.getOtherFundClassesData(productCode);
                JsonNode jsonNode = objectMapper.readTree(response);
                FundOtherFundClasses otherFundClasses = new FundOtherFundClasses();
                otherFundClasses.setProductCode(productCode);
                otherFundClasses.setResponseData(jsonNode);
                otherFundClasses.setUpdateDate(updateDate);
                fundOtherFundClassesRepository.save(otherFundClasses);
                logger.debug("Saved other fund classes data for fund: {}", productCode);
            } catch (Exception e) {
                logger.error("Error fetching other fund classes data for fund {}: {}", productCode, e.getMessage());
            }
        }, executorService);
        
        // Wait for all tasks to complete
        CompletableFuture.allOf(productTask, quoteDetailTask, fundQuoteSummaryTask, 
                               holdingAllocationTask, topTenHoldingsTask, otherFundClassesTask)
                .join();
        
        logger.info("Completed processing fund: {}", productCode);
    }
}
