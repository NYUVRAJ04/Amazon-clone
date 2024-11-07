package com.happiest.feign;

import com.happiest.model.Buyers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "http://AuthService/buyerdata")
public interface BuyerClient {

    @GetMapping("/buyers/count")
    public Long getTotalFarmers();

    @GetMapping("/allbuyers")
    public List<Buyers> getAllBuyers();


}
