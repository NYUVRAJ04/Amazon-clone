package com.happiest.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import com.happiest.dto.ProductDTO;
import com.happiest.model.Crop;
import com.happiest.model.Machinery;
import com.happiest.repository.CropRepository;
import com.happiest.repository.MachineryRepository;
import com.happiest.service.FileStorageService;
import com.happiest.service.ProductService;
import com.happiest.utility.UploadFileResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;
import java.util.Arrays;
import java.util.List;

class ProductServiceTest {

    @Mock
    private MachineryRepository machineryRepository;

    @Mock
    private CropRepository cropsRepository;

    @Mock
    private FileStorageService fileStorageService;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSearchProducts() {
        String searchTerm = "tractor";
        Machinery machinery = new Machinery();
        machinery.setName("Tractor");
        Crop crop = new Crop();
        crop.setName("Corn");
        when(machineryRepository.findByNameContainingOrCategoryNameContaining(searchTerm, searchTerm))
                .thenReturn(Arrays.asList(machinery));
        when(cropsRepository.findByNameContainingOrCategoryNameContaining(searchTerm, searchTerm))
                .thenReturn(Arrays.asList(crop));
        List<Object> products = productService.searchProducts(searchTerm);
        assertEquals(2, products.size());
        assertTrue(products.contains(machinery));
        assertTrue(products.contains(crop));
    }

    @Test
    void testGetAllProducts() {
        Machinery machinery = new Machinery();
        Crop crop = new Crop();
        when(machineryRepository.findAll()).thenReturn(Arrays.asList(machinery));
        when(cropsRepository.findAll()).thenReturn(Arrays.asList(crop));
        List<Object> products = productService.getAllProducts();
        assertEquals(2, products.size());
        assertTrue(products.contains(machinery));
        assertTrue(products.contains(crop));
    }

    @Test
    void testAddProduct() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setCategory("Machinery");
        productDTO.setName("Tractor");
        productDTO.setAvailability("AVAILABLE");
        productDTO.setCategoryName("Tractors");
        productDTO.setDescription("A powerful tractor");
        productDTO.setRentalPrice(100.0);
        productDTO.setSellingPrice(1000.0);
        MultipartFile file = mock(MultipartFile.class);
        UploadFileResponse uploadFileResponse = new UploadFileResponse("testfile.txt", "http://localhost:9008/uploads/testfile.txt", "image/jpeg", 12345);
        when(fileStorageService.storeFile(file)).thenReturn(uploadFileResponse);
        productService.addProduct(productDTO, file);
        verify(machineryRepository, times(1)).save(any(Machinery.class));
    }

    @Test
    void testGetDistinctCategoryNames() {
        when(machineryRepository.findDistinctCategoryNames()).thenReturn(Arrays.asList("Tractors", "Harvesters"));
        when(cropsRepository.findDistinctCategoryNames()).thenReturn(Arrays.asList("Corn", "Wheat"));
        List<String> categoryNames = productService.getDistinctCategoryNames("machinery");
        assertEquals(2, categoryNames.size());
        assertTrue(categoryNames.contains("Tractors"));
        assertTrue(categoryNames.contains("Harvesters"));
    }

    @Test
    void testUpdateProductByCategoryName() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setCategory("Machinery");
        productDTO.setCategoryName("Tractors");
        productDTO.setAvailability("AVAILABLE");
        productDTO.setName("Updated Tractor");
        productDTO.setDescription("An updated powerful tractor");
        productDTO.setRentalPrice(150.0);
        productDTO.setSellingPrice(1200.0);
        MultipartFile file = mock(MultipartFile.class);
        UploadFileResponse uploadFileResponse = new UploadFileResponse("testfile.txt", "http://localhost:9008/uploads/testfile.txt", "image/jpeg", 12345);
        when(fileStorageService.storeFile(file)).thenReturn(uploadFileResponse);
        Machinery machinery = new Machinery();
        when(machineryRepository.findByCategoryName("Tractors")).thenReturn(Arrays.asList(machinery));
        productService.updateProductByCategoryName(productDTO, file);
        verify(machineryRepository, times(1)).save(any(Machinery.class));
    }

    // Additional test case for updateProductByCategoryName and addProduct
    @Test
    void testUpdateProductByCategoryNameWithNewImage() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setCategory("Machinery");
        productDTO.setCategoryName("Tractors");
        productDTO.setAvailability("AVAILABLE");
        productDTO.setName("Updated Tractor");
        productDTO.setDescription("An updated powerful tractor");
        productDTO.setRentalPrice(150.0);
        productDTO.setSellingPrice(1200.0);
        MultipartFile file = mock(MultipartFile.class);
        UploadFileResponse uploadFileResponse = new UploadFileResponse("testfile.txt", "http://localhost:9008/uploads/testfile.txt", "image/jpeg", 12345);
        when(fileStorageService.storeFile(file)).thenReturn(uploadFileResponse);
        Machinery machinery = new Machinery();
        when(machineryRepository.findByCategoryName("Tractors")).thenReturn(Arrays.asList(machinery));
        productService.updateProductByCategoryName(productDTO, file);
        verify(machineryRepository, times(1)).save(any(Machinery.class));
        assertEquals("http://localhost:9008/uploads/testfile.txt", machinery.getImageUrl());
    }

    @Test
    void testAddProductWithFile() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setCategory("Machinery");
        productDTO.setName("Tractor");
        productDTO.setAvailability("AVAILABLE");
        productDTO.setCategoryName("Tractors");
        productDTO.setDescription("A powerful tractor");
        productDTO.setRentalPrice(100.0);
        productDTO.setSellingPrice(1000.0);
        MultipartFile file = mock(MultipartFile.class);
        UploadFileResponse uploadFileResponse = new UploadFileResponse("testfile.txt", "http://localhost:9008/uploads/testfile.txt", "image/jpeg", 12345);
        when(fileStorageService.storeFile(file)).thenReturn(uploadFileResponse);
        productService.addProduct(productDTO, file);
        verify(machineryRepository, times(1)).save(any(Machinery.class));
    }
}
