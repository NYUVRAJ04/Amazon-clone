package org.happiest.apigateway;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient(name = "http://ProductMicroservice/api/products")
public interface ProductInterface {
    @GetMapping("/products")
    public ResponseEntity<List<Object>> getAllProducts();

    @GetMapping("/search")
    public ResponseEntity<List<Object>> searchProducts(@RequestParam String searchTerm);


    @PostMapping(value = "/add" ,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addProduct(@RequestPart("product") String productJson, @RequestPart("file") MultipartFile file);


}
