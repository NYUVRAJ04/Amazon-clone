package com.happiest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.happiest.dto.ProductDTO;
import com.happiest.repository.CropRepository;
import com.happiest.repository.MachineryRepository;
import com.happiest.service.ProductService;
import com.happiest.constants.Constants; // Importing the Constants class
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private MachineryRepository machineryRepository;

    @Autowired
    private CropRepository cropRepository;

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @GetMapping("/search")
    public ResponseEntity<List<Object>> searchProducts(@RequestParam String searchTerm) {
        logger.info(Constants.LOG_SEARCHING_PRODUCTS, searchTerm);
        List<Object> products = productService.searchProducts(searchTerm);
        logger.info(Constants.LOG_FOUND_PRODUCTS_COUNT, products.size());
        return ResponseEntity.ok(products);
    }

    @GetMapping("/products")
    public ResponseEntity<List<Object>> getAllProducts() {
        logger.info(Constants.LOG_FETCHING_ALL_PRODUCTS);
        List<Object> products = productService.getAllProducts();
        logger.info(Constants.LOG_TOTAL_PRODUCTS_FETCHED, products.size());
        return ResponseEntity.ok(products);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestPart("product") String productJson, @RequestPart("file") MultipartFile file) {
        logger.info(Constants.LOG_RECEIVED_ADD_PRODUCT, productJson);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            // Convert JSON string to ProductDTO object
            ProductDTO productDTO = objectMapper.readValue(productJson, ProductDTO.class);

            // Save product with file
            productService.addProduct(productDTO, file);
            logger.info(Constants.LOG_PRODUCT_ADDED_SUCCESSFULLY, productDTO);
            return new ResponseEntity<>("Product added successfully", HttpStatus.CREATED);
        } catch (IOException e) {
            logger.error(Constants.ERROR_PROCESSING_PRODUCT_DATA, e.getMessage());
            return new ResponseEntity<>("Error processing the product data", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IllegalArgumentException e) {
            logger.warn("Illegal argument exception: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/categories")
    public List<String> getDistinctCategoryNames(@RequestParam(required = false) String category) {
        logger.info(Constants.LOG_FETCHING_DISTINCT_CATEGORY_NAMES, category);
        List<String> categoryNames = productService.getDistinctCategoryNames(category);
        logger.info(Constants.LOG_FETCHED_DISTINCT_CATEGORY_NAMES, categoryNames.size());
        return categoryNames;
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateProductByCategoryName(
            @RequestPart("product") String productJson,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        logger.info(Constants.LOG_RECEIVED_UPDATE_PRODUCT, productJson);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            // Convert JSON string to ProductDTO object
            ProductDTO productDTO = objectMapper.readValue(productJson, ProductDTO.class);

            productService.updateProductByCategoryName(productDTO, file);
            logger.info(Constants.LOG_PRODUCT_UPDATED_SUCCESSFULLY, productDTO);
            return ResponseEntity.ok("Product updated successfully");
        } catch (JsonMappingException e) {
            logger.error("JSON mapping error: {}", e.getMessage());
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            logger.error("JSON processing error: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
