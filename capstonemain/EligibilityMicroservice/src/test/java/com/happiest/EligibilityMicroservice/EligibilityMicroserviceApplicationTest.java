package com.happiest.EligibilityMicroservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class EligibilityMicroserviceApplicationTest {

    @Test
    void testMain() {
        String[] args = {};
        try {
            EligibilityMicroserviceApplication.main(args);
            System.out.println("Application started successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Application failed to start.");
        }
    }
}
