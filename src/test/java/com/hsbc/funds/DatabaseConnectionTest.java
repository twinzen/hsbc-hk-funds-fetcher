package com.hsbc.funds;

import com.hsbc.funds.model.FundProduct;
import com.hsbc.funds.repository.FundProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test database connection and basic operations
 */
@SpringBootTest
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:postgresql://localhost:5434/hsbcfunds",
    "spring.datasource.username=postgres",
    "spring.datasource.password=password",
    "spring.jpa.hibernate.ddl-auto=update",
    "spring.profiles.active=test"
})
class DatabaseConnectionTest {
    
    @Autowired
    private FundProductRepository fundProductRepository;
    
    @Test
    void testDatabaseConnection() {
        assertNotNull(fundProductRepository);
    }
    
    @Test
    void testSaveAndRetrieveFundProduct() {
        // Create a test fund product
        FundProduct testProduct = new FundProduct(
            "TEST001", 
            "{\"test\": \"data\", \"productCode\": \"TEST001\"}", 
            LocalDate.now()
        );
        
        // Save to database
        FundProduct savedProduct = fundProductRepository.save(testProduct);
        assertNotNull(savedProduct.getId());
        assertEquals("TEST001", savedProduct.getProductCode());
        
        // Retrieve from database
        var retrievedProducts = fundProductRepository.findByProductCodeOrderByCreatedAtDesc("TEST001");
        assertFalse(retrievedProducts.isEmpty());
        assertEquals("TEST001", retrievedProducts.get(0).getProductCode());
        
        // Clean up
        fundProductRepository.delete(savedProduct);
    }
}
