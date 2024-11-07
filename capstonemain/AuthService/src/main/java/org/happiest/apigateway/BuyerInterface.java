package org.happiest.apigateway;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name ="http://BuyerMicroservice/buy")
public interface BuyerInterface {

    @GetMapping("/products")
    public String welcomeBuyer();
}
