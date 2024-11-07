package com.happiest.service;

import com.happiest.exception.ResourceNotFoundException;
import com.happiest.model.Crop;
import com.happiest.repository.CropRepository;
import com.happiest.constants.MessageConstants;
import com.happiest.utility.UploadFileResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class CropService {

    private static final Logger logger = LogManager.getLogger(CropService.class);

    @Autowired
    private CropRepository cropRepository;

    @Autowired
    private FileStorageService fileStorageService;

    // Create or update crop
    public Crop saveCrop(Crop crop, MultipartFile file) {
        logger.info(MessageConstants.CROP_SAVE_ATTEMPT, crop.getName());

        try {
            if (file != null && !file.isEmpty()) {
                logger.debug(MessageConstants.CROP_FILE_PROVIDED, crop.getName());
                UploadFileResponse uploadFileResponse = fileStorageService.storeFile(file);
                String imageUrl = uploadFileResponse.getFileDownloadUri();
                crop.setImageUrl(imageUrl);
                logger.debug(MessageConstants.CROP_FILE_UPLOADED, imageUrl);
            }

            Crop savedCrop = cropRepository.save(crop);
            logger.info(MessageConstants.CROP_SAVE_SUCCESS, savedCrop.getId());
            return savedCrop;
        } catch (Exception e) {
            logger.error(MessageConstants.CROP_SAVE_ERROR, e);
            throw new RuntimeException("Failed to save crop: " + e.getMessage());
        }
    }

    // Get all crops
    public List<Crop> getAllCrops() {
        logger.info(MessageConstants.CROP_FETCH_ALL);
        return cropRepository.findAll();
    }

    // Get crop by ID
    public Optional<Crop> getCropById(Long id) {
        logger.info(MessageConstants.CROP_FETCH_ID, id);

        Optional<Crop> crop = cropRepository.findById(id);
        if (crop.isPresent()) {
            return crop;  // Crop found, return the Optional
        } else {
            logger.error(MessageConstants.CROP_NOT_FOUND, id);
            throw new ResourceNotFoundException("Crop not found with ID " + id);
        }
    }


    // Delete crop by ID
    public void deleteCrop(Long id) {
        logger.info(MessageConstants.CROP_DELETE_ID, id);
        try {
            cropRepository.deleteById(id);
            logger.info(MessageConstants.CROP_DELETE_SUCCESS, id);
        } catch (Exception e) {
            logger.error(MessageConstants.CROP_DELETE_ERROR, e);
            throw new RuntimeException("Failed to delete crop with ID " + id + ": " + e.getMessage());
        }
    }

    // Get distinct crop categories
    public List<String> getDistinctCategoryNames() {
        logger.info(MessageConstants.CROP_FETCH_CATEGORIES);
        return cropRepository.findDistinctCategoryNames();
    }

    // Update crop
    public Crop updateCrop(Long id, Crop updatedCrop, MultipartFile file) {
        logger.info(MessageConstants.CROP_UPDATE_ATTEMPT, id);

        return cropRepository.findById(id).map(existingCrop -> {
            logger.debug(MessageConstants.CROP_FOUND_UPDATE_FIELDS, id);

            if (updatedCrop.getName() != null) {
                existingCrop.setName(updatedCrop.getName());
                logger.debug(MessageConstants.CROP_UPDATE_NAME, updatedCrop.getName());
            }
            if (updatedCrop.getDescription() != null) {
                existingCrop.setDescription(updatedCrop.getDescription());
                logger.debug(MessageConstants.CROP_UPDATE_DESCRIPTION);
            }
            if (updatedCrop.getRentalPrice() != null) {
                existingCrop.setRentalPrice(updatedCrop.getRentalPrice());
                logger.debug(MessageConstants.CROP_UPDATE_RENTAL_PRICE, updatedCrop.getRentalPrice());
            }
            if (updatedCrop.getSellingPrice() != null) {
                existingCrop.setSellingPrice(updatedCrop.getSellingPrice());
                logger.debug(MessageConstants.CROP_UPDATE_SELLING_PRICE, updatedCrop.getSellingPrice());
            }
            if (updatedCrop.getCropavailability() != null) {
                existingCrop.setCropavailability(updatedCrop.getCropavailability());
                logger.debug(MessageConstants.CROP_UPDATE_AVAILABILITY);
            }
            if (updatedCrop.getCategoryName() != null) {
                existingCrop.setCategoryName(updatedCrop.getCategoryName());
                logger.debug(MessageConstants.CROP_UPDATE_CATEGORY_NAME, updatedCrop.getCategoryName());
            }

            if (file != null && !file.isEmpty()) {
                logger.debug(MessageConstants.CROP_NEW_FILE_PROVIDED, id);
                UploadFileResponse uploadFileResponse = fileStorageService.storeFile(file);
                String imageUrl = uploadFileResponse.getFileDownloadUri();
                existingCrop.setImageUrl(imageUrl);
                logger.debug(MessageConstants.CROP_IMAGE_URL_UPDATED, imageUrl);
            }

            Crop updatedCropEntity = cropRepository.save(existingCrop);
            logger.info(MessageConstants.CROP_UPDATE_SUCCESS, updatedCropEntity.getId());
            return updatedCropEntity;
        }).orElseThrow(() -> {
            logger.error(MessageConstants.CROP_NOT_FOUND, id);
            return new ResourceNotFoundException("Crop not found with ID " + id);
        });
    }
}
