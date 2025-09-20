package com.hsbc.funds;

import com.hsbc.funds.config.FundApiConfig;
import com.hsbc.funds.service.FundDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
@EnableConfigurationProperties(FundApiConfig.class)
@Profile("!test") // Disable CommandLineRunner in test profile
public class FundDataFetcherApplication implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(FundDataFetcherApplication.class);

    private final FundDataService fundDataService;

    public FundDataFetcherApplication(FundDataService fundDataService) {
        this.fundDataService = fundDataService;
    }

    public static void main(String[] args) {
        SpringApplication.run(FundDataFetcherApplication.class, args);
    }

    @Override
    public void run(String... args) {
        logger.info("Starting HSBC Fund Data Fetcher Application");
        try {
            fundDataService.fetchAllFundData();
            logger.info("Fund data fetching process completed successfully!");
        } catch (Exception e) {
            logger.error("Error occurred during fund data fetching", e);
        }
    }
}
