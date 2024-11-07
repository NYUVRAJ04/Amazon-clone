package com.happiest.test;

import com.happiest.constants.Constants;
import com.happiest.exception.ResourceNotFoundException;
import com.happiest.model.Machinery;
import com.happiest.repository.MachineryRepository;
import com.happiest.service.FileStorageService;
import com.happiest.service.MachineryService;
import com.happiest.utility.UploadFileResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MachineryServiceTest {

    @InjectMocks
    private MachineryService machineryService;

    @Mock
    private MachineryRepository machineryRepository;

    @Mock
    private FileStorageService fileStorageService;

    @Mock
    private MultipartFile mockFile;

    private Machinery mockMachinery;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMachinery = new Machinery();
        mockMachinery.setId(1L);
        mockMachinery.setName("Tractor");
        mockMachinery.setDescription("A high power tractor");
        mockMachinery.setRentalPrice(500.0);
    }

    @Test
    void testSaveMachineryWithFile() throws Exception {
        // Mock file upload response
        UploadFileResponse uploadFileResponse = new UploadFileResponse("tractor.jpg", "http://example.com/tractor.jpg", "image/jpeg", 1024L);
        when(fileStorageService.storeFile(mockFile)).thenReturn(uploadFileResponse);

        when(machineryRepository.save(any(Machinery.class))).thenReturn(mockMachinery);

        Machinery result = machineryService.saveMachinery(mockMachinery, mockFile);

        assertNotNull(result);
        assertEquals(mockMachinery.getName(), result.getName());
        assertEquals("http://example.com/tractor.jpg", result.getImageUrl());
        verify(machineryRepository, times(1)).save(mockMachinery);
    }

    @Test
    void testSaveMachineryWithoutFile() {
        when(machineryRepository.save(any(Machinery.class))).thenReturn(mockMachinery);

        Machinery result = machineryService.saveMachinery(mockMachinery, null);

        assertNotNull(result);
        assertEquals(mockMachinery.getName(), result.getName());
        assertNull(result.getImageUrl());  // Ensure no image URL is set
        verify(machineryRepository, times(1)).save(mockMachinery);
    }

    @Test
    void testGetAllMachinery() {
        List<Machinery> machineryList = Arrays.asList(mockMachinery, new Machinery());
        when(machineryRepository.findAll()).thenReturn(machineryList);

        List<Machinery> result = machineryService.getAllMachinery();

        assertEquals(2, result.size());
        verify(machineryRepository, times(1)).findAll();
    }

    @Test
    void testGetMachineryByIdFound() {
        when(machineryRepository.findById(1L)).thenReturn(Optional.of(mockMachinery));

        Optional<Machinery> result = machineryService.getMachineryById(1L);

        assertTrue(result.isPresent());
        assertEquals(mockMachinery.getId(), result.get().getId());
        verify(machineryRepository, times(1)).findById(1L);
    }

    @Test
    void testGetMachineryByIdNotFound() {
        when(machineryRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            machineryService.getMachineryById(1L);
        });

        assertEquals(Constants.ERROR_MACHINERY_NOT_FOUND + "1", exception.getMessage());
    }

    @Test
    void testDeleteMachineryFound() {
        when(machineryRepository.findById(1L)).thenReturn(Optional.of(mockMachinery));

        machineryService.deleteMachinery(1L);

        verify(machineryRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteMachineryNotFound() {
        when(machineryRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            machineryService.deleteMachinery(1L);
        });

        assertEquals(Constants.ERROR_MACHINERY_NOT_FOUND + "1", exception.getMessage());
    }

    @Test
    void testGetDistinctCategoryNames() {
        List<String> categories = Arrays.asList("Tractor", "Harvester");
        when(machineryRepository.findDistinctCategoryNames()).thenReturn(categories);

        List<String> result = machineryService.getDistinctCategoryNames();

        assertEquals(2, result.size());
        assertEquals("Tractor", result.get(0));
        verify(machineryRepository, times(1)).findDistinctCategoryNames();
    }

    @Test
    void testUpdateMachineryWithFile() throws Exception {
        when(machineryRepository.findById(1L)).thenReturn(Optional.of(mockMachinery));
        UploadFileResponse uploadFileResponse = new UploadFileResponse("newtractor.jpg", "http://example.com/newtractor.jpg", "image/jpeg", 1024L);
        when(fileStorageService.storeFile(mockFile)).thenReturn(uploadFileResponse);

        mockMachinery.setName("Updated Tractor");

        when(machineryRepository.save(any(Machinery.class))).thenReturn(mockMachinery);

        Machinery updatedMachinery = new Machinery();
        updatedMachinery.setName("Updated Tractor");

        Machinery result = machineryService.updateMachinery(1L, updatedMachinery, mockFile);

        assertNotNull(result);
        assertEquals("Updated Tractor", result.getName());
        assertEquals("http://example.com/newtractor.jpg", result.getImageUrl());
        verify(machineryRepository, times(1)).save(mockMachinery);
    }

    @Test
    void testUpdateMachineryWithoutFile() {
        when(machineryRepository.findById(1L)).thenReturn(Optional.of(mockMachinery));
        mockMachinery.setName("Updated Tractor");
        when(machineryRepository.save(any(Machinery.class))).thenReturn(mockMachinery);

        Machinery updatedMachinery = new Machinery();
        updatedMachinery.setName("Updated Tractor");

        Machinery result = machineryService.updateMachinery(1L, updatedMachinery, null);

        assertNotNull(result);
        assertEquals("Updated Tractor", result.getName());
        assertNull(result.getImageUrl());  // Ensure image URL is not modified
        verify(machineryRepository, times(1)).save(mockMachinery);
    }
}
