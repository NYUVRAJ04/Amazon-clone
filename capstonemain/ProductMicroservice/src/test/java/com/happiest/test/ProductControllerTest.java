package com.happiest.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.happiest.controller.ProductController;
import com.happiest.dto.ProductDTO;
import com.happiest.repository.CropRepository;
import com.happiest.repository.MachineryRepository;
import com.happiest.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    @Mock
    private MachineryRepository machineryRepository;

    @Mock
    private CropRepository cropRepository;

    @Mock
    private MultipartFile file;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void searchProducts_ShouldReturnListOfProducts() {
        List<Object> products = Arrays.asList(new Object(), new Object());
        when(productService.searchProducts("test")).thenReturn(products);

        ResponseEntity<List<Object>> response = productController.searchProducts("test");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(productService, times(1)).searchProducts("test");
    }

    @Test
    void getAllProducts_ShouldReturnListOfAllProducts() {
        List<Object> products = Arrays.asList(new Object(), new Object());
        when(productService.getAllProducts()).thenReturn(products);

        ResponseEntity<List<Object>> response = productController.getAllProducts();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(productService, times(1)).getAllProducts();
    }

    @Test
    void addProduct_ShouldReturnCreatedStatus() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        String productJson = objectMapper.writeValueAsString(productDTO);

        doNothing().when(productService).addProduct(any(ProductDTO.class), any(MultipartFile.class));

        ResponseEntity<?> response = productController.addProduct(productJson, file);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Product added successfully", response.getBody());
        verify(productService, times(1)).addProduct(any(ProductDTO.class), any(MultipartFile.class));
    }

    @Test
    void getDistinctCategoryNames_ShouldReturnListOfCategories() {
        List<String> categories = Arrays.asList("Category1", "Category2");
        when(productService.getDistinctCategoryNames(null)).thenReturn(categories);

        List<String> categoryNames = productController.getDistinctCategoryNames(null);

        assertEquals(2, categoryNames.size());
        verify(productService, times(1)).getDistinctCategoryNames(null);
    }

    @Test
    void updateProductByCategoryName_ShouldReturnOkStatus() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        String productJson = objectMapper.writeValueAsString(productDTO);

        doNothing().when(productService).updateProductByCategoryName(any(ProductDTO.class), any(MultipartFile.class));

        ResponseEntity<String> response = productController.updateProductByCategoryName(productJson, file);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Product updated successfully", response.getBody());
        verify(productService, times(1)).updateProductByCategoryName(any(ProductDTO.class), any(MultipartFile.class));
    }
}
