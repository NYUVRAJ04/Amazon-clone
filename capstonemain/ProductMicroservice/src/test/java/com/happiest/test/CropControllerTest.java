package com.happiest.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.happiest.controller.CropController;
import com.happiest.model.Crop;
import com.happiest.service.CropService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class CropControllerTest {

    @InjectMocks
    private CropController cropController;

    @Mock
    private CropService cropService;

    @Mock
    private MultipartFile mockFile;

    private Crop crop;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        crop = new Crop();
        crop.setId(1L);
        crop.setName("Test Crop");
        crop.setCategoryName("Vegetable");
    }

    @Test
    public void testCreateCrop() throws IOException {
        String cropJson = new ObjectMapper().writeValueAsString(crop);
        when(cropService.saveCrop(any(Crop.class), eq(mockFile))).thenReturn(crop);

        ResponseEntity<Crop> response = cropController.createCrop(cropJson, mockFile);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(crop, response.getBody());
        verify(cropService).saveCrop(any(Crop.class), eq(mockFile));
    }

    @Test
    public void testGetAllCrops() {
        List<Crop> crops = new ArrayList<>();
        crops.add(crop);
        when(cropService.getAllCrops()).thenReturn(crops);

        ResponseEntity<List<Crop>> response = cropController.getAllCrops();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(crop, response.getBody().get(0));
        verify(cropService).getAllCrops();
    }

    @Test
    public void testGetCropByIdFound() {
        when(cropService.getCropById(1L)).thenReturn(Optional.of(crop));

        ResponseEntity<Crop> response = cropController.getCropById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(crop, response.getBody());
        verify(cropService).getCropById(1L);
    }

    @Test
    public void testGetCropByIdNotFound() {
        when(cropService.getCropById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Crop> response = cropController.getCropById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(cropService).getCropById(1L);
    }

    @Test
    public void testDeleteCrop() {
        doNothing().when(cropService).deleteCrop(1L);

        ResponseEntity<Void> response = cropController.deleteCrop(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(cropService).deleteCrop(1L);
    }

    @Test
    public void testGetDistinctCategoryNames() {
        List<String> categories = new ArrayList<>();
        categories.add("Vegetable");
        when(cropService.getDistinctCategoryNames()).thenReturn(categories);

        ResponseEntity<List<String>> response = cropController.getDistinctCategoryNames();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("Vegetable", response.getBody().get(0));
        verify(cropService).getDistinctCategoryNames();
    }

    @Test
    public void testUpdateCrop() throws IOException {
        String cropJson = new ObjectMapper().writeValueAsString(crop);
        when(cropService.updateCrop(eq(1L), any(Crop.class), eq(mockFile))).thenReturn(crop);

        ResponseEntity<Crop> response = cropController.updateCrop(1L, cropJson, mockFile);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(crop, response.getBody());
        verify(cropService).updateCrop(eq(1L), any(Crop.class), eq(mockFile));
    }
}
