package com.happiest.service;

import com.happiest.constants.ProductServiceConstants;
import com.happiest.dto.ProductDTO;
import com.happiest.model.Crop;
import com.happiest.model.Machinery;
import com.happiest.repository.CropRepository;
import com.happiest.repository.MachineryRepository;
import com.happiest.utility.UploadFileResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ProductService {

    private static final Logger logger = LogManager.getLogger(ProductService.class);

    @Autowired
    private MachineryRepository machineryRepository;

    @Autowired
    private CropRepository cropsRepository;

    @Autowired
    private FileStorageService fileStorageService;

    public List<Object> searchProducts(String searchTerm) {
        try {
            logger.info(ProductServiceConstants.LOG_SEARCHING_PRODUCTS, searchTerm);
            List<Machinery> machineries = machineryRepository.findByNameContainingOrCategoryNameContaining(searchTerm, searchTerm);
            List<Crop> crops = cropsRepository.findByNameContainingOrCategoryNameContaining(searchTerm, searchTerm);
            logger.info(ProductServiceConstants.LOG_FOUND_PRODUCTS, machineries.size(), crops.size());
            return Stream.concat(machineries.stream(), crops.stream()).collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error while searching products: {}", e.getMessage());
            throw new RuntimeException("Failed to search products", e);
        }
    }

    public List<Object> getAllProducts() {
        try {
            logger.info(ProductServiceConstants.LOG_RETRIEVING_ALL_PRODUCTS);
            List<Machinery> machineries = machineryRepository.findAll();
            List<Crop> crops = cropsRepository.findAll();
            logger.info(ProductServiceConstants.LOG_FOUND_ALL_PRODUCTS, machineries.size(), crops.size());
            return Stream.concat(machineries.stream(), crops.stream()).collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error while retrieving all products: {}", e.getMessage());
            throw new RuntimeException("Failed to retrieve products", e);
        }
    }

    public void addProduct(ProductDTO productDTO, MultipartFile file) {
        try {
            logger.info(ProductServiceConstants.LOG_ADDING_NEW_PRODUCT, productDTO.getCategory());
            String imageUrl = null;
            if (file != null && !file.isEmpty()) {
                UploadFileResponse uploadFileResponse = fileStorageService.storeFile(file);
                imageUrl = uploadFileResponse.getFileDownloadUri();
                logger.info("Uploaded file with URL: {}", imageUrl);
            }

            if (ProductServiceConstants.CATEGORY_MACHINERY.equalsIgnoreCase(productDTO.getCategory())) {
                Machinery machinery = new Machinery();
                machinery.setMachineryavailability(Machinery.Availability.valueOf(productDTO.getAvailability()));
                machinery.setCategoryName(productDTO.getCategoryName());
                machinery.setDescription(productDTO.getDescription());
                machinery.setImageUrl(imageUrl);
                machinery.setName(productDTO.getName());
                machinery.setRentalPrice(productDTO.getRentalPrice());
                machinery.setSellingPrice(productDTO.getSellingPrice());
                machineryRepository.save(machinery);
                logger.info(ProductServiceConstants.LOG_PRODUCT_SAVED, "Machinery", machinery.getId());
            } else if (ProductServiceConstants.CATEGORY_CROP.equalsIgnoreCase(productDTO.getCategory())) {
                Crop crop = new Crop();
                crop.setCropavailability(Crop.Availability.valueOf(productDTO.getAvailability()));
                crop.setCategoryName(productDTO.getCategoryName());
                crop.setDescription(productDTO.getDescription());
                crop.setImageUrl(imageUrl);
                crop.setName(productDTO.getName());
                crop.setRentalPrice(productDTO.getRentalPrice());
                crop.setSellingPrice(productDTO.getSellingPrice());
                cropsRepository.save(crop);
                logger.info(ProductServiceConstants.LOG_PRODUCT_SAVED, "Crop", crop.getId());
            } else {
                logger.error(ProductServiceConstants.LOG_INVALID_CATEGORY, productDTO.getCategory());
                throw new IllegalArgumentException("Invalid category: " + productDTO.getCategory());
            }
        } catch (Exception e) {
            logger.error("Error while adding product: {}", e.getMessage());
            throw new RuntimeException("Failed to add product", e);
        }
    }

    public List<String> getDistinctCategoryNames(String category) {
        try {
            logger.info(ProductServiceConstants.LOG_RETRIEVING_DISTINCT_CATEGORY_NAMES, category);
            List<String> categoryNames;
            switch (category.toLowerCase()) {
                case ProductServiceConstants.CATEGORY_MACHINERY:
                    categoryNames = machineryRepository.findDistinctCategoryNames();
                    break;
                case "crops":
                    categoryNames = cropsRepository.findDistinctCategoryNames();
                    break;
                default:
                    categoryNames = Stream.concat(
                            machineryRepository.findDistinctCategoryNames().stream(),
                            cropsRepository.findDistinctCategoryNames().stream()
                    ).collect(Collectors.toList());
                    break;
            }
            logger.info(ProductServiceConstants.LOG_FOUND_DISTINCT_CATEGORIES, categoryNames.size());
            return categoryNames;
        } catch (Exception e) {
            logger.error("Error while retrieving distinct category names: {}", e.getMessage());
            throw new RuntimeException("Failed to retrieve distinct category names", e);
        }
    }

    public void updateProductByCategoryName(ProductDTO productDTO, MultipartFile file) {
        try {
            logger.info(ProductServiceConstants.LOG_UPDATING_PRODUCTS, productDTO.getCategoryName());
            String imageUrl;

            if (file != null && !file.isEmpty()) {
                UploadFileResponse uploadFileResponse = fileStorageService.storeFile(file);
                imageUrl = uploadFileResponse.getFileDownloadUri();
                logger.info("Uploaded new image with URL: {}", imageUrl);
            } else {
                imageUrl = null;
            }

            if (ProductServiceConstants.CATEGORY_MACHINERY.equalsIgnoreCase(productDTO.getCategory())) {
                List<Machinery> machineries = machineryRepository.findByCategoryName(productDTO.getCategoryName());
                if (machineries.isEmpty()) {
                    logger.error(ProductServiceConstants.LOG_NO_PRODUCT_FOUND, "machinery", productDTO.getCategoryName());
                    throw new IllegalArgumentException("No machinery found with category name: " + productDTO.getCategoryName());
                }
                machineries.forEach(machinery -> updateMachineryFields(machinery, productDTO, imageUrl));
            } else if (ProductServiceConstants.CATEGORY_CROP.equalsIgnoreCase(productDTO.getCategory())) {
                List<Crop> crops = cropsRepository.findByCategoryName(productDTO.getCategoryName());
                if (crops.isEmpty()) {
                    logger.error(ProductServiceConstants.LOG_NO_PRODUCT_FOUND, "crop", productDTO.getCategoryName());
                    throw new IllegalArgumentException("No crop found with category name: " + productDTO.getCategoryName());
                }
                crops.forEach(crop -> updateCropFields(crop, productDTO, imageUrl));
            } else {
                logger.error(ProductServiceConstants.LOG_INVALID_CATEGORY, productDTO.getCategory());
                throw new IllegalArgumentException("Invalid category: " + productDTO.getCategory());
            }

            logger.info(ProductServiceConstants.LOG_PRODUCTS_UPDATED);
        } catch (Exception e) {
            logger.error("Error while updating product: {}", e.getMessage());
            throw new RuntimeException("Failed to update product", e);
        }
    }

    private void updateMachineryFields(Machinery machinery, ProductDTO productDTO, String imageUrl) {
        machinery.setMachineryavailability(Machinery.Availability.valueOf(productDTO.getAvailability()));
        machinery.setDescription(productDTO.getDescription());
        machinery.setRentalPrice(productDTO.getRentalPrice());
        machinery.setSellingPrice(productDTO.getSellingPrice());
        if (imageUrl != null) {
            machinery.setImageUrl(imageUrl);
        }
        machineryRepository.save(machinery);
    }

    private void updateCropFields(Crop crop, ProductDTO productDTO, String imageUrl) {
        crop.setCropavailability(Crop.Availability.valueOf(productDTO.getAvailability()));
        crop.setDescription(productDTO.getDescription());
        crop.setRentalPrice(productDTO.getRentalPrice());
        crop.setSellingPrice(productDTO.getSellingPrice());
        if (imageUrl != null) {
            crop.setImageUrl(imageUrl);
        }
        cropsRepository.save(crop);
    }
}
