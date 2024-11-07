package com.happiest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.happiest.model.Crop;
import com.happiest.service.CropService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/crops")
@CrossOrigin
public class CropController {

    private static final Logger logger = LoggerFactory.getLogger(CropController.class);

    @Autowired
    private CropService cropService;

    @PostMapping("/adddata")
    public ResponseEntity<Crop> createCrop(
            @RequestPart("crop") String cropJson,
            @RequestPart("file") MultipartFile file) throws IOException {
        logger.info("Creating a new crop with data: {}", cropJson);
        ObjectMapper objectMapper = new ObjectMapper();
        Crop crop = objectMapper.readValue(cropJson, Crop.class);
        Crop savedCrop = cropService.saveCrop(crop, file);
        logger.info("Crop created successfully with ID: {}", savedCrop.getId());
        return new ResponseEntity<>(savedCrop, HttpStatus.CREATED);
    }

    @GetMapping("/allcrops")
    public ResponseEntity<List<Crop>> getAllCrops() {
        logger.info("Fetching all crops");
        List<Crop> cropList = cropService.getAllCrops();
        logger.info("Total crops fetched: {}", cropList.size());
        return new ResponseEntity<>(cropList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Crop> getCropById(@PathVariable Long id) {
        logger.info("Fetching crop with ID: {}", id);
        Optional<Crop> crop = cropService.getCropById(id);
        return crop.map(value -> {
            logger.info("Crop found with ID: {}", id);
            return new ResponseEntity<>(value, HttpStatus.OK);
        }).orElseGet(() -> {
            logger.warn("Crop not found with ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        });
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCrop(@PathVariable Long id) {
        logger.info("Deleting crop with ID: {}", id);
        cropService.deleteCrop(id);
        logger.info("Crop deleted with ID: {}", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<String>> getDistinctCategoryNames() {
        logger.info("Fetching distinct crop category names");
        List<String> categoryNames = cropService.getDistinctCategoryNames();
        logger.info("Distinct categories fetched: {}", categoryNames.size());
        return new ResponseEntity<>(categoryNames, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Crop> updateCrop(
            @PathVariable Long id,
            @RequestParam("crop") String cropJson,
            @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        logger.info("Updating crop with ID: {} and data: {}", id, cropJson);
        ObjectMapper objectMapper = new ObjectMapper();
        Crop updatedCrop = objectMapper.readValue(cropJson, Crop.class);
        Crop updated = cropService.updateCrop(id, updatedCrop, file);
        logger.info("Crop updated successfully with ID: {}", updated.getId());
        return ResponseEntity.ok(updated);
    }
}
