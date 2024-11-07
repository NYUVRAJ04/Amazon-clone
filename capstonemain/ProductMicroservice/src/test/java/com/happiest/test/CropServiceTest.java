package com.happiest.test;

import com.happiest.exception.ResourceNotFoundException;
import com.happiest.model.Crop;
import com.happiest.repository.CropRepository;
import com.happiest.service.CropService;
import com.happiest.service.FileStorageService;
import com.happiest.utility.UploadFileResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CropServiceTest {

    @Mock
    private CropRepository cropRepository;

    @Mock
    private FileStorageService fileStorageService;

    @InjectMocks
    private CropService cropService;

    private Crop crop;
    private MultipartFile file;

    @BeforeEach
    void setUp() {
        crop = new Crop();
        crop.setId(1L);
        crop.setName("Wheat");
        crop.setCategoryName("Grains");

        file = mock(MultipartFile.class);
    }

    @Test
    void saveCrop_withFile_shouldStoreFileAndSaveCrop() {
        // Arrange
        when(file.isEmpty()).thenReturn(false);
        UploadFileResponse uploadFileResponse = new UploadFileResponse("testFileName", "testUrl", "image/png", 12345L);
        when(fileStorageService.storeFile(file)).thenReturn(uploadFileResponse);
        when(cropRepository.save(crop)).thenReturn(crop);

        // Act
        Crop savedCrop = cropService.saveCrop(crop, file);

        // Assert
        assertNotNull(savedCrop);
        assertEquals("testUrl", savedCrop.getImageUrl());
        verify(cropRepository, times(1)).save(crop);
        verify(fileStorageService, times(1)).storeFile(file);
    }

    @Test
    void getAllCrops_shouldReturnAllCrops() {
        // Arrange
        when(cropRepository.findAll()).thenReturn(Arrays.asList(crop));

        // Act
        List<Crop> crops = cropService.getAllCrops();

        // Assert
        assertFalse(crops.isEmpty());
        assertEquals(1, crops.size());
        assertEquals(crop, crops.get(0));
        verify(cropRepository, times(1)).findAll();
    }

    @Test
    void getCropById_whenCropExists_shouldReturnCrop() {
        // Arrange
        when(cropRepository.findById(1L)).thenReturn(Optional.of(crop));

        // Act
        Crop foundCrop = cropService.getCropById(1L).orElse(null);

        // Assert
        assertNotNull(foundCrop);
        assertEquals(1L, foundCrop.getId());
        verify(cropRepository, times(1)).findById(1L);
    }

    @Test
    void getCropById_whenCropDoesNotExist_shouldThrowResourceNotFoundException() {
        // Arrange
        when(cropRepository.findById(2L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> cropService.getCropById(2L));
        verify(cropRepository, times(1)).findById(2L);
    }

    @Test
    void deleteCrop_shouldDeleteCrop() {
        // Act
        cropService.deleteCrop(1L);

        // Assert
        verify(cropRepository, times(1)).deleteById(1L);
    }

    @Test
    void getDistinctCategoryNames_shouldReturnDistinctCategories() {
        // Arrange
        List<String> categories = Arrays.asList("Grains", "Vegetables");
        when(cropRepository.findDistinctCategoryNames()).thenReturn(categories);

        // Act
        List<String> distinctCategories = cropService.getDistinctCategoryNames();

        // Assert
        assertNotNull(distinctCategories);
        assertEquals(2, distinctCategories.size());
        assertTrue(distinctCategories.contains("Grains"));
        assertTrue(distinctCategories.contains("Vegetables"));
        verify(cropRepository, times(1)).findDistinctCategoryNames();
    }

    @Test
    void updateCrop_withNewDescription_shouldUpdateDescriptionOnly() {
        // Arrange
        Crop updatedCrop = new Crop();
        updatedCrop.setDescription("New Description");
        when(cropRepository.findById(1L)).thenReturn(Optional.of(crop));
        when(cropRepository.save(any(Crop.class))).thenReturn(crop);

        // Act
        Crop result = cropService.updateCrop(1L, updatedCrop, null);

        // Assert
        assertEquals("New Description", result.getDescription());
        verify(cropRepository, times(1)).save(any(Crop.class));
        verifyNoInteractions(fileStorageService);  // Ensure no file upload occurs
    }


    @Test
    void updateCrop_withFile_shouldUpdateCropAndStoreFile() {
        // Arrange
        Crop updatedCrop = new Crop();
        updatedCrop.setName("Corn");
        when(cropRepository.findById(1L)).thenReturn(Optional.of(crop));
        when(file.isEmpty()).thenReturn(false);

        // Updated UploadFileResponse instantiation with four parameters
        UploadFileResponse uploadFileResponse = new UploadFileResponse("newFileName", "newTestUrl", "image/jpeg", 56789L);
        when(fileStorageService.storeFile(file)).thenReturn(uploadFileResponse);
        when(cropRepository.save(any(Crop.class))).thenReturn(crop);

        // Act
        Crop result = cropService.updateCrop(1L, updatedCrop, file);

        // Assert
        assertEquals("Corn", result.getName());
        assertEquals("newTestUrl", result.getImageUrl());
        verify(cropRepository, times(1)).save(any(Crop.class));
    }

    @Test
    void updateCrop_whenCropDoesNotExist_shouldThrowResourceNotFoundException() {
        // Arrange
        Crop updatedCrop = new Crop();
        when(cropRepository.findById(2L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> cropService.updateCrop(2L, updatedCrop, file));
        verify(cropRepository, times(1)).findById(2L);
    }
}
