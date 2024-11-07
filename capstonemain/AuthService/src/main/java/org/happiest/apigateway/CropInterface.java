package org.happiest.apigateway;


import org.happiest.model.Crop;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name="http://ProductMicroservice/api/crops")
public interface CropInterface
{

    @GetMapping("/allcrops")
    public ResponseEntity<List<Crop>> getAllCrops();
}
