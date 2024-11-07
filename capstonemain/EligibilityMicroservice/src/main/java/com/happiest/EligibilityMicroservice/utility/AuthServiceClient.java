// src/main/java/com/happiest/EligibilityMicroservice/client/AuthServiceClient.java

package com.happiest.EligibilityMicroservice.utility;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.happiest.EligibilityMicroservice.dto.BuyerDTO;

// Replace 'auth-service' with the actual service name or use a URL
@FeignClient(name = "http://AuthService/buyerdata")
public interface AuthServiceClient {

    /**
     * Endpoint to check if a buyer exists.
     * GET /api/auth/buyers/{buyerId}
     */
    @GetMapping("/{buyerId}")
    public BuyerDTO isBuyerExists(@PathVariable("buyerId") Long buyerId);
}
