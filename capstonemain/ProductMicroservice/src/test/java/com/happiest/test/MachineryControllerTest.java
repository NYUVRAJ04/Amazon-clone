package com.happiest.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.happiest.controller.MachineryController;
import com.happiest.model.Machinery;
import com.happiest.service.MachineryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MachineryControllerTest {

    @InjectMocks
    private MachineryController machineryController;

    @Mock
    private MachineryService machineryService;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testCreateMachinery() throws IOException {
        // Given
        Machinery machinery = new Machinery();
        machinery.setId(1L);
        String machineryJson = objectMapper.writeValueAsString(machinery);
        MultipartFile file = mock(MultipartFile.class);

        when(machineryService.saveMachinery(any(Machinery.class), any(MultipartFile.class))).thenReturn(machinery);

        // When
        ResponseEntity<Machinery> response = machineryController.createMachinery(machineryJson, file);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(machinery, response.getBody());
        verify(machineryService, times(1)).saveMachinery(any(Machinery.class), any(MultipartFile.class));
    }

    @Test
    public void testGetAllMachinery() {
        // Given
        Machinery machinery1 = new Machinery();
        Machinery machinery2 = new Machinery();
        List<Machinery> machineryList = Arrays.asList(machinery1, machinery2);
        when(machineryService.getAllMachinery()).thenReturn(machineryList);

        // When
        ResponseEntity<List<Machinery>> response = machineryController.getAllMachinery();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(machineryList.size(), response.getBody().size());
        verify(machineryService, times(1)).getAllMachinery();
    }

    @Test
    public void testGetMachineryById_Found() {
        // Given
        Machinery machinery = new Machinery();
        machinery.setId(1L);
        when(machineryService.getMachineryById(1L)).thenReturn(Optional.of(machinery));

        // When
        ResponseEntity<Machinery> response = machineryController.getMachineryById(1L);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(machinery, response.getBody());
        verify(machineryService, times(1)).getMachineryById(1L);
    }

    @Test
    public void testGetMachineryById_NotFound() {
        // Given
        when(machineryService.getMachineryById(1L)).thenReturn(Optional.empty());

        // When
        ResponseEntity<Machinery> response = machineryController.getMachineryById(1L);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(machineryService, times(1)).getMachineryById(1L);
    }

    @Test
    public void testDeleteMachinery() {
        // Given
        Long id = 1L;

        // When
        ResponseEntity<Void> response = machineryController.deleteMachinery(id);

        // Then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(machineryService, times(1)).deleteMachinery(id);
    }

    @Test
    public void testGetDistinctCategoryNames() {
        // Given
        List<String> categories = Arrays.asList("Tractor", "Plough");
        when(machineryService.getDistinctCategoryNames()).thenReturn(categories);

        // When
        ResponseEntity<List<String>> response = machineryController.getDistinctCategoryNames();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(categories.size(), response.getBody().size());
        verify(machineryService, times(1)).getDistinctCategoryNames();
    }

    @Test
    public void testUpdateMachinery() throws IOException {
        // Given
        Machinery machinery = new Machinery();
        machinery.setId(1L);
        String machineryJson = objectMapper.writeValueAsString(machinery);
        MultipartFile file = mock(MultipartFile.class);

        when(machineryService.updateMachinery(eq(1L), any(Machinery.class), any(MultipartFile.class)))
                .thenReturn(machinery);

        // When
        ResponseEntity<Machinery> response = machineryController.updateMachinery(1L, machineryJson, file);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(machinery, response.getBody());
        verify(machineryService, times(1)).updateMachinery(eq(1L), any(Machinery.class), any(MultipartFile.class));
    }
}
