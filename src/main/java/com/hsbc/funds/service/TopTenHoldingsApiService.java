package com.hsbc.funds.service;

import com.hsbc.funds.config.FundApiConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class TopTenHoldingsApiService {
    private static final Logger logger = LoggerFactory.getLogger(TopTenHoldingsApiService.class);
    private final ApiClient apiClient;
    private final FundApiConfig fundApiConfig;
    
    // Hardcoded URL for now
    private static final String TOP_TEN_HOLDINGS_API_URL = "https://investments3.personal-banking.hsbc.com.hk/shp/wealth-mobile-mds-shp-api-hk-hbap-prod-proxy/v0/wmds/topTenHoldings";

    @Autowired
    public TopTenHoldingsApiService(ApiClient apiClient, FundApiConfig fundApiConfig) {
        this.apiClient = apiClient;
        this.fundApiConfig = fundApiConfig;
    }

    public String getTopTenHoldingsData(String productCode) throws IOException {
        String jsonBodyParam = String.format(
                "{\"productType\":\"UT\",\"prodAltNum\":\"%s\",\"prodCdeAltClassCde\":\"M\",\"market\":\"HK\"}",
                productCode
        );

        String encodedJsonBody = URLEncoder.encode(jsonBodyParam, StandardCharsets.UTF_8);
        String url = TOP_TEN_HOLDINGS_API_URL + "?body=" + encodedJsonBody;

        Map<String, String> headers = new HashMap<>();
        headers.put("x-hsbc-channel-id", "OHI");
        headers.put("x-hsbc-locale", "en_GB");
        headers.put("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/138.0.0.0 Safari/537.36");

        logger.info("Calling Top Ten Holdings API for product: {}", productCode);
        return apiClient.get(url, headers);
    }
}
