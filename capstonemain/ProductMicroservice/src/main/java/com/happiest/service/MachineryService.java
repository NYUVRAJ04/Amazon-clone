package com.happiest.service;

import com.happiest.constants.Constants;
import com.happiest.exception.FileStorageException;
import com.happiest.exception.ResourceNotFoundException;
import com.happiest.model.Machinery;
import com.happiest.repository.MachineryRepository;
import com.happiest.utility.UploadFileResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class MachineryService {

    private static final Logger logger = LogManager.getLogger(MachineryService.class);

    @Autowired
    private MachineryRepository machineryRepository;

    @Autowired
    private FileStorageService fileStorageService;

    public Machinery saveMachinery(Machinery machinery, MultipartFile file) {
        logger.info(Constants.LOG_SAVING_MACHINERY, machinery.getName());
        try {
            if (file != null && !file.isEmpty()) {
                UploadFileResponse uploadFileResponse = fileStorageService.storeFile(file);
                machinery.setImageUrl(uploadFileResponse.getFileDownloadUri());
                logger.debug(Constants.LOG_IMAGE_URL_SET, uploadFileResponse.getFileDownloadUri());
            }
            Machinery savedMachinery = machineryRepository.save(machinery);
            logger.info(Constants.LOG_MACHINERY_SAVED_SUCCESSFULLY, savedMachinery.getId());
            return savedMachinery;
        } catch (FileStorageException e) {
            logger.error("File storage error while saving machinery: {}", machinery.getName(), e);
            throw new RuntimeException("Could not save machinery due to file storage error.", e);
        } catch (Exception e) {
            logger.error("Error saving machinery: {}", machinery.getName(), e);
            throw new RuntimeException("Could not save machinery. Please try again.", e);
        }
    }

    public List<Machinery> getAllMachinery() {
        logger.info("Retrieving all machinery records");
        List<Machinery> machineryList = machineryRepository.findAll();
        logger.info(Constants.LOG_TOTAL_MACHINERY_RECORDS, machineryList.size());
        return machineryList;
    }

    public Optional<Machinery> getMachineryById(Long id) {
        logger.info(Constants.LOG_RETRIEVING_MACHINERY, id);
        return machineryRepository.findById(id).or(() -> {
            logger.warn(Constants.ERROR_MACHINERY_NOT_FOUND + id);
            throw new ResourceNotFoundException(Constants.ERROR_MACHINERY_NOT_FOUND + id);
        });
    }

    public void deleteMachinery(Long id) {
        logger.info(Constants.LOG_DELETING_MACHINERY, id);
        machineryRepository.findById(id).ifPresentOrElse(
                machinery -> {
                    machineryRepository.deleteById(id);
                    logger.info(Constants.LOG_MACHINERY_DELETED_SUCCESSFULLY, id);
                },
                () -> {
                    logger.warn(Constants.ERROR_MACHINERY_NOT_FOUND + id);
                    throw new ResourceNotFoundException(Constants.ERROR_MACHINERY_NOT_FOUND + id);
                });
    }

    public List<String> getDistinctCategoryNames() {
        logger.info("Retrieving distinct machinery category names");
        List<String> categoryNames = machineryRepository.findDistinctCategoryNames();
        logger.info(Constants.LOG_CATEGORY_NAMES_RETRIEVED, categoryNames.size());
        return categoryNames;
    }

    public Machinery updateMachinery(Long id, Machinery updatedMachinery, MultipartFile file) {
        logger.info(Constants.LOG_UPDATING_MACHINERY, id);
        Machinery existingMachinery = machineryRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn(Constants.ERROR_MACHINERY_NOT_FOUND + id);
                    return new ResourceNotFoundException(Constants.ERROR_MACHINERY_NOT_FOUND + id);
                });

        // Set fields from updatedMachinery object if they are provided
        setUpdatedMachineryFields(existingMachinery, updatedMachinery);

        // Update image if a new file is provided
        if (file != null && !file.isEmpty()) {
            try {
                UploadFileResponse uploadFileResponse = fileStorageService.storeFile(file);
                existingMachinery.setImageUrl(uploadFileResponse.getFileDownloadUri());
                logger.debug(Constants.LOG_IMAGE_URL_SET, uploadFileResponse.getFileDownloadUri());
            } catch (FileStorageException e) {
                logger.error("File storage error while updating machinery with ID: {}", id, e);
                throw new RuntimeException("Could not update file for machinery due to file storage error.", e);
            } catch (Exception e) {
                logger.error("Error updating file for machinery with ID: {}", id, e);
                throw new RuntimeException("Could not update file for machinery. Please try again.", e);
            }
        }

        Machinery savedMachinery = machineryRepository.save(existingMachinery);
        logger.info("Machinery with ID: {} updated successfully", id);
        return savedMachinery;
    }

    private void setUpdatedMachineryFields(Machinery existingMachinery, Machinery updatedMachinery) {
        if (updatedMachinery.getName() != null) {
            existingMachinery.setName(updatedMachinery.getName());
            logger.debug("Updated name: {}", updatedMachinery.getName());
        }
        if (updatedMachinery.getDescription() != null) {
            existingMachinery.setDescription(updatedMachinery.getDescription());
            logger.debug("Updated description: {}", updatedMachinery.getDescription());
        }
        if (updatedMachinery.getRentalPrice() != null) {
            existingMachinery.setRentalPrice(updatedMachinery.getRentalPrice());
            logger.debug("Updated rental price: {}", updatedMachinery.getRentalPrice());
        }
        // Add additional fields as necessary
    }
}
