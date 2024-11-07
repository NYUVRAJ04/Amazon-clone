package com.happiest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.happiest.constants.Constants; // Importing the Constants class
import com.happiest.model.Machinery;
import com.happiest.service.MachineryService;
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
@RequestMapping("/api/machinery")
@CrossOrigin
public class MachineryController {

    @Autowired
    private MachineryService machineryService;

    private static final Logger logger = LoggerFactory.getLogger(MachineryController.class);

    @PostMapping("/adddata")
    public ResponseEntity<Machinery> createMachinery(
            @RequestPart("machinery") String machineryJson,
            @RequestPart("file") MultipartFile file) throws IOException {
        logger.info(Constants.LOG_SAVING_MACHINERY, machineryJson);

        // Convert JSON string to Machinery object
        ObjectMapper objectMapper = new ObjectMapper();
        Machinery machinery = objectMapper.readValue(machineryJson, Machinery.class);

        // Save machinery with file
        Machinery savedMachinery = machineryService.saveMachinery(machinery, file);
        logger.info(Constants.LOG_MACHINERY_SAVED_SUCCESSFULLY, savedMachinery.getId());
        return new ResponseEntity<>(savedMachinery, HttpStatus.CREATED);
    }

    @GetMapping("/allmachineries")
    public ResponseEntity<List<Machinery>> getAllMachinery() {
        logger.info(Constants.LOG_RETRIEVING_ALL_MACHINERIES);
        List<Machinery> machineryList = machineryService.getAllMachinery();
        logger.info(Constants.LOG_TOTAL_MACHINERY_RECORDS, machineryList.size());
        return new ResponseEntity<>(machineryList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Machinery> getMachineryById(@PathVariable Long id) {
        logger.info(Constants.LOG_RETRIEVING_MACHINERY, id);
        Optional<Machinery> machinery = machineryService.getMachineryById(id);
        return machinery.map(value -> {
            logger.info(Constants.LOG_ORDER_FOUND, value);
            return new ResponseEntity<>(value, HttpStatus.OK);
        }).orElseGet(() -> {
            logger.warn(Constants.ERROR_MACHINERY_NOT_FOUND + id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        });
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMachinery(@PathVariable Long id) {
        logger.info(Constants.LOG_DELETING_MACHINERY, id);
        machineryService.deleteMachinery(id);
        logger.info(Constants.LOG_MACHINERY_DELETED_SUCCESSFULLY, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<String>> getDistinctCategoryNames() {
        logger.info(Constants.LOG_CATEGORY_NAMES_RETRIEVED);
        List<String> categoryNames = machineryService.getDistinctCategoryNames();
        logger.info(Constants.LOG_TOTAL_MACHINERY_RECORDS, categoryNames.size());
        return new ResponseEntity<>(categoryNames, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Machinery> updateMachinery(
            @PathVariable Long id,
            @RequestParam("machinery") String machineryJson,
            @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {

        logger.info(Constants.LOG_UPDATING_MACHINERY, id);
        // Convert the machinery JSON string to a Machinery object
        ObjectMapper objectMapper = new ObjectMapper();
        Machinery updatedMachinery = objectMapper.readValue(machineryJson, Machinery.class);

        // Call service to update machinery with the parsed object and file
        Machinery updated = machineryService.updateMachinery(id, updatedMachinery, file);
        logger.info(Constants.LOG_MACHINERY_SAVED_SUCCESSFULLY, updated.getId());
        return ResponseEntity.ok(updated);
    }
}
