package com.happiest.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "machinery-service")
public interface MachineryClient {

    @GetMapping("/api/machineries/count")
    Long getTotalMachineries();
}
