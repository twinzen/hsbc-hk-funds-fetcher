# HSBC HK Funds Fetcher

A Java application to fetch fund data from HSBC APIs and store it in a database.

## Overview

This application follows a three-step process:

1. **Fund Search API**: Calls the HSBC fund search API to get all fund codes with pagination support
2. **Parallel Data Fetching**: For each fund code, makes 6 parallel API calls to retrieve different information:
   - Fund Basic Information
   - Fund Performance
   - Fund Holdings
   - Fund Risk Metrics
   - Fund Fees
   - Fund Documents
3. **Data Storage**: Saves all API responses into 6 different database tables with product code relationships

## Features

- **Real HSBC API Integration**: Uses actual HSBC fund search API with proper authentication headers
- **Pagination Support**: Handles paginated fund search API responses with proper pagination logic
- **Product Code Extraction**: Correctly extracts product codes from `prodAltNumSeg` where `prodCdeAltClassCde="P"`
- **Parallel Processing**: Uses CompletableFuture for concurrent API calls
- **Database Storage**: Stores raw JSON responses in separate tables
- **Error Handling**: Continues processing even if individual fund data fails
- **Comprehensive Logging**: Comprehensive logging with SLF4J and Logback
- **Configuration Management**: Externalized configuration via properties files

## API Integration

### Fund Search API
- **Method**: POST
- **URL**: `https://investments3.personal-banking.hsbc.com.hk/shp/wealth-mobile-mds-shp-api-hk-hbap-prod-proxy/v0/wmds/fundSearchResult`
- **Headers**: 
  - `x-hsbc-locale: OHI`
  - `x-hsbc-locale: en_GB`
- **Request Body**: JSON with search criteria, pagination, and holdings information
- **Response**: JSON with pagination info and products array
- **Product Code Extraction**: From `products[].header.prodAltNumSeg` where `prodCdeAltClassCde="P"`

### Pagination Logic
The API uses `startDetail` and `endDetail` parameters for pagination:
- Page 1: `startDetail=1, endDetail=100`
- Page 2: `startDetail=101, endDetail=200`
- And so on...

The response includes pagination metadata:
```json
{
  "pagination": {
    "totalNumberOfRecords": 1440,
    "numberOfRecords": 5,
    "startDetail": 1,
    "endDetail": 5
  }
}
```

## Project Structure

```
src/
├── main/
│   ├── java/com/hsbc/funds/
│   │   ├── FundDataFetcherApplication.java    # Main application class
│   │   ├── config/
│   │   │   └── ApplicationConfig.java         # Configuration constants
│   │   ├── model/                             # Data models
│   │   │   ├── FundDataTable.java            # Base model class
│   │   │   ├── FundSearchRequest.java        # Fund search API request model
│   │   │   ├── FundSearchResponse.java       # Fund search API response model
│   │   │   ├── FundBasicInfo.java           # Basic info table model
│   │   │   ├── FundPerformance.java         # Performance table model
│   │   │   ├── FundHoldings.java            # Holdings table model
│   │   │   ├── FundRiskMetrics.java         # Risk metrics table model
│   │   │   ├── FundFees.java                # Fees table model
│   │   │   └── FundDocuments.java           # Documents table model
│   │   ├── repository/
│   │   │   └── FundDataRepository.java       # Database operations
│   │   └── service/
│   │       ├── ApiClient.java                # HTTP client wrapper with POST support
│   │       ├── FundSearchService.java        # Fund search API service
│   │       └── FundDataService.java          # Main data fetching service
│   └── resources/
│       ├── application.properties            # Application configuration
│       └── logback.xml                      # Logging configuration
└── test/
    └── java/com/hsbc/funds/
        ├── FundDataFetcherApplicationTest.java # Unit tests
        └── FundSearchServiceTest.java          # Fund search service tests
```

## Database Schema

The application creates 6 tables to store fund data:

1. **fund_basic_info**: Basic fund information
2. **fund_performance**: Fund performance data
3. **fund_holdings**: Fund holdings information
4. **fund_risk_metrics**: Risk metrics data
5. **fund_fees**: Fee information
6. **fund_documents**: Document references

Each table contains:
- `id`: Auto-increment primary key
- `product_code`: Fund/product identifier (extracted from API response)
- `response_data`: Raw JSON response from API
- `update_date`: Date when the data was fetched (YYYY-MM-DD)
- `created_at`: Timestamp when record was created

## Configuration

The application is configured for the real HSBC API:

1. **Fund Search API**: Already configured with the correct HSBC endpoint
2. **Fund Data APIs**: Placeholder URLs - update these with actual endpoints
3. **Pagination**: Configured for 100 records per page
4. **Threading**: 10 threads for parallel processing

## Building and Running

### Build the project
```bash
mvn clean compile
```

### Run tests
```bash
mvn test
```

### Run the application
```bash
mvn exec:java
```

### Package the application
```bash
mvn clean package
```

## API Request Example

The fund search API request includes:

```json
{
    "productType": "UT",
    "returnOnlyNumberOfMatches": false,
    "detailedCriterias": [
        {
            "criteriaKey": "RISK",
            "criteriaValue": "0:1:2:3:4:5",
            "operator": "in"
        },
        {
            "criteriaKey": "GNRA",
            "criteriaValue": "Y:null",
            "operator": "in"
        }
    ],
    "sortBy": "return1Yr",
    "sortOrder": "desc",
    "numberOfRecords": 100,
    "startDetail": 1,
    "endDetail": 100,
    "sortCriterias": [
        {
            "sortKey": "prodStatCde",
            "sortOrder": "asc"
        },
        {
            "sortKey": "return1Yr",
            "sortOrder": "desc"
        }
    ],
    "holdings": [
        {
            "criteriaKey": "topHoldingsList",
            "criteriaValue": true,
            "top": "5",
            "others": true
        }
    ],
    "restrOnlScribInd": "Y"
}
```

## Dependencies

- **OkHttp**: HTTP client for API calls with POST support
- **Jackson**: JSON processing for request/response serialization
- **H2 Database**: In-memory database (can be replaced with production database)
- **SLF4J + Logback**: Logging framework
- **JUnit 5**: Testing framework

## Error Handling

The application includes comprehensive error handling:
- Individual fund processing failures don't stop the entire process
- HTTP timeouts and connection errors are logged and handled gracefully
- Database connection issues are handled with proper cleanup
- All exceptions are logged with appropriate context
- API authentication errors are properly logged

## Performance Considerations

- Uses thread pool for parallel API calls (configurable pool size)
- Implements proper pagination to handle large datasets (1440+ funds)
- Raw JSON storage minimizes processing overhead
- Connection pooling for database operations
- Proper HTTP client configuration with timeouts

## Next Steps

1. **Update Fund Data API URLs**: Replace placeholder URLs with actual HSBC fund data endpoints
2. **Add Authentication**: Implement proper authentication for fund data APIs
3. **Configure Production Database**: Replace H2 with production database
4. **Add Retry Logic**: Implement retry logic for failed API calls
5. **Add Monitoring**: Add monitoring and alerting for API failures
6. **Data Validation**: Add data validation and transformation if needed
7. **Rate Limiting**: Implement rate limiting to respect API limits

## Testing

The application includes comprehensive tests:
- Unit tests for all model classes
- Integration tests for API client
- Database operation tests
- Fund search service tests with mock data

Run tests with:
```bash
mvn test
```

## Logging

The application uses SLF4J with Logback for logging:
- Console output with timestamps
- File logging with daily rotation
- Configurable log levels
- Structured logging for better debugging

Logs are written to:
- Console (INFO level and above)
- `logs/fund-fetcher.log` (with daily rotation)
