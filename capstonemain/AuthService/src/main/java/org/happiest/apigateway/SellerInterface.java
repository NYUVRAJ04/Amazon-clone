package org.happiest.apigateway;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name ="http://SellerMicroservice/sell")
public interface SellerInterface {

    @GetMapping("/products")
    public String welcome();
}
