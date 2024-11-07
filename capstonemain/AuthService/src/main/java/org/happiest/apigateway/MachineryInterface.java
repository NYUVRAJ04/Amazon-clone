package org.happiest.apigateway;


import org.happiest.model.Machinery;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name="http://ProductMicroservice/api/machinery")
public interface MachineryInterface
{
    @GetMapping("/allmachineries")
    public ResponseEntity<List<Machinery>> getAllMachinery();

}
