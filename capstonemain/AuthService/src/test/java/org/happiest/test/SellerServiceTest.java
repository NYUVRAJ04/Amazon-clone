package org.happiest.test;


import org.happiest.exception.SellerServiceException;
import org.happiest.model.Seller;
import org.happiest.repository.SellerRepository;
import org.happiest.service.SellerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SellerServiceTest {

    @InjectMocks
    private SellerService sellerService;

    @Mock
    private SellerRepository sellerRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetTotalSellers_Success() {
        when(sellerRepository.count()).thenReturn(5L);

        Long totalSellers = sellerService.getTotalSellers();

        assertEquals(5L, totalSellers);
        verify(sellerRepository, times(1)).count();
    }

    @Test
    public void testGetTotalSellers_Exception() {
        when(sellerRepository.count()).thenThrow(new RuntimeException("Database error"));

        SellerServiceException exception = assertThrows(SellerServiceException.class, () -> {
            sellerService.getTotalSellers();
        });

        assertEquals("An error occurred while counting sellers", exception.getMessage());
        assertTrue(exception.getCause() instanceof RuntimeException);
        verify(sellerRepository, times(1)).count();
    }

    @Test
    public void testGetAllSellers_Success() {
        // Create SellerDto with id
        Seller seller1 = new Seller();
        seller1.setSellerId(1L);

        Seller seller2 = new Seller();
        seller2.setSellerId(2L);

        List<Seller> sellers = Arrays.asList(seller1, seller2); // List of SellerDto
        when(sellerRepository.findAll()).thenReturn(sellers); // Ensure this is compatible

        List<Seller> result = sellerService.getAllSellers(); // Update service method accordingly

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getSellerId()); // Verify id
        assertEquals(2L, result.get(1).getSellerId()); // Verify id
        verify(sellerRepository, times(1)).findAll();
    }

    @Test
    public void testGetAllSellers_Exception() {
        when(sellerRepository.findAll()).thenThrow(new RuntimeException("Database error"));

        SellerServiceException exception = assertThrows(SellerServiceException.class, () -> {
            sellerService.getAllSellers();
        });

        assertEquals("An error occurred while retrieving sellers", exception.getMessage());
        assertTrue(exception.getCause() instanceof RuntimeException);
        verify(sellerRepository, times(1)).findAll();
    }
}
