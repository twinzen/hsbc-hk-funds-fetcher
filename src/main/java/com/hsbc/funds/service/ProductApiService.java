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
public class ProductApiService {
    private static final Logger logger = LoggerFactory.getLogger(ProductApiService.class);
    private final ApiClient apiClient;
    private final FundApiConfig fundApiConfig;
    
    // Hardcoded URL for now
    private static final String PRODUCT_API_URL = "https://investments3.personal-banking.hsbc.com.hk/shp/wealth-mobile-utb-tp-public-shp-api-hk-hbap-prod-proxy/v0/amh/ut/product";

    @Autowired
    public ProductApiService(ApiClient apiClient, FundApiConfig fundApiConfig) {
        this.apiClient = apiClient;
        this.fundApiConfig = fundApiConfig;
    }

    public String getProductData(String productCode) throws IOException {
        String jsonBodyParam = String.format(
                "{\"productIds\":[{\"productTypeCode\":\"UT\",\"productAlternativeNumber\":\"%s\",\"productAlternativeClassificationCode\":\"M\",\"countryProductTradableCode\":\"HK\"}],\"attrName\":[\"riskLvlCde\",\"ccyInvstCde\",\"invstInitMinAmt\",\"fundRtainMinAmt\",\"rdmMinAmt\",\"fundHouseCde\",\"fundCatCde\",\"ccyProdCde\",\"prodName\",\"topSellProdInd\",\"ccyProdTradeCde\",\"utRdmMinNum\",\"utRtainMinNum\",\"allowBuyProdInd\",\"allowSellProdInd\",\"allowSellMipProdInd\",\"allowSwOutProdInd\",\"prodStatCde\",\"ccyProdMktPrcCde\",\"prodNavPrcAmt\",\"prodBidPrcAmt\",\"prcEffDt\",\"deAuthFundInd\",\"piFundInd\",\"setlLeadTmScrib\",\"setlLeadTmRdm\",\"restrOnlScribInd\",\"dcmlPlaceTradeUnitNum\",\"invstMipMinAmt\",\"invstMipIncrmMinAmt\",\"gbaAcctTrdb\",\"esgInd\",\"finDocURL_FACTSHEET\",\"finDocURL_PROSPECTUS\",\"finDocURL_ANULRPT\",\"finDocURL_INTRMRPT\",\"restrOnlScribInd\",\"fundSwInMinAmt\",\"fundSwOutMinAmt\",\"fundSwOutRtainMinAmt\",\"utSwOutRtainMinNum\",\"siFundInd\"]}",
                productCode
        );

        String encodedJsonBody = URLEncoder.encode(jsonBodyParam, StandardCharsets.UTF_8);
        String url = PRODUCT_API_URL + "?body=" + encodedJsonBody;

        Map<String, String> headers = new HashMap<>();
        headers.put("x-hsbc-channel-id", "OHI");
        headers.put("x-hsbc-locale", "en_GB");
        headers.put("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/138.0.0.0 Safari/537.36");

        logger.info("Calling Product API for product: {}", productCode);
        return apiClient.get(url, headers);
    }
}
