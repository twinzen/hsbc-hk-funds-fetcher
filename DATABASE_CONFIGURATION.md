# Database Configuration - Complete Setup

## ✅ **Database Successfully Configured!**

### **🔗 Connection Details:**
- **Host**: localhost
- **Port**: 5434
- **Database**: hsbcfunds
- **Username**: postgres
- **Password**: password
- **Connection String**: `jdbc:postgresql://localhost:5434/hsbcfunds`

### **📊 Database Schema:**
The following 6 tables have been created to store API responses:

1. **`fund_product`** - Product API responses
2. **`fund_quote_detail`** - Quote Detail API responses
3. **`fund_quote_summary`** - Quote Summary API responses
4. **`fund_holding_allocation`** - Holding Allocation API responses
5. **`fund_top_ten_holdings`** - Top Ten Holdings API responses
6. **`fund_other_fund_classes`** - Other Fund Classes API responses

### **🗄️ Table Structure:**
```sql
CREATE TABLE fund_product (
    id BIGSERIAL PRIMARY KEY,                    -- Auto-incrementing ID
    product_code VARCHAR(50) UNIQUE NOT NULL,    -- Fund product code (foreign key)
    response_data JSONB NOT NULL,                -- Complete API response as JSON
    update_date DATE NOT NULL,                   -- Date when data was updated
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- Record creation timestamp
);
```

### **⚙️ Application Configuration:**
The `application.properties` has been updated with your database settings:

```properties
# Database Configuration - PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5434/hsbcfunds
spring.datasource.username=postgres
spring.datasource.password=password
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

### **🚀 How to Run the Application:**

#### **1. Compile the Application:**
```bash
mvn clean compile
```

#### **2. Run the Spring Boot Application:**
```bash
mvn spring-boot:run
```

#### **3. Or Build and Run JAR:**
```bash
mvn clean package
java -jar target/funds-fetcher-1.0.0.jar
```

### **📈 What Happens When You Run:**

1. **Database Connection**: Application connects to PostgreSQL
2. **Schema Validation**: Hibernate validates table structure
3. **Fund Search**: Calls HSBC fund search API to get all fund codes
4. **Parallel Processing**: For each fund code, calls 6 APIs simultaneously
5. **Data Storage**: Saves all responses to PostgreSQL as JSONB
6. **Progress Logging**: Shows detailed progress and any errors

### **🔍 Verify Database Connection:**

The application logs will show:
```
INFO  com.zaxxer.hikari.HikariDataSource - HikariPool-1 - Added connection org.postgresql.jdbc.PgConnection@...
INFO  com.zaxxer.hikari.HikariDataSource - HikariPool-1 - Start completed.
```

### **📊 Sample Queries to Check Data:**

After running the application, you can query your database:

```sql
-- Check if data was inserted
SELECT COUNT(*) FROM fund_product;

-- Get all fund codes
SELECT DISTINCT product_code FROM fund_product ORDER BY product_code;

-- Get sample data
SELECT product_code, update_date, created_at 
FROM fund_product 
LIMIT 10;

-- Query JSONB data
SELECT 
    product_code,
    response_data->'payload'->0->'attributeMap'->>'prodName' as product_name,
    response_data->'payload'->0->'attributeMap'->>'riskLvlCde' as risk_level
FROM fund_product 
LIMIT 5;
```

### **🎯 Next Steps:**

1. **Run the Application**: `mvn spring-boot:run`
2. **Monitor Logs**: Watch for progress and any errors
3. **Check Database**: Verify data is being stored
4. **Update API URLs**: Replace placeholder URLs with real HSBC endpoints

The database is now fully configured and ready to store all 6 API responses! 🚀
