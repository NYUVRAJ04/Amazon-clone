package org.happiest.test;

import org.happiest.constants.LoggerConstants;
import org.happiest.controller.SellerController;
import org.happiest.model.Seller;
import org.happiest.service.SellerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SellerControllerTest {

    @InjectMocks
    private SellerController sellerController;

    @Mock
    private SellerService sellerService;

    private static final Logger logger = LoggerFactory.getLogger(SellerControllerTest.class);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetTotalSellers() {
        Long expectedTotalSellers = 5L;
        when(sellerService.getTotalSellers()).thenReturn(expectedTotalSellers);

        Long totalSellers = sellerController.getTotalSellers();

        assertEquals(expectedTotalSellers, totalSellers);
        verify(sellerService).getTotalSellers();
        // Optionally, you could verify logging if needed, but it's often not done in unit tests.
    }

    @Test
    void testGetAllSellers() {
        Seller seller1 = new Seller(); // Initialize as needed
        Seller seller2 = new Seller(); // Initialize as needed
        List<Seller> expectedSellers = new ArrayList<>();
        expectedSellers.add(seller1);
        expectedSellers.add(seller2);
        
        when(sellerService.getAllSellers()).thenReturn(expectedSellers);

        List<Seller> sellers = sellerController.getAllSellers();

        assertEquals(expectedSellers.size(), sellers.size());
        assertEquals(expectedSellers, sellers);
        verify(sellerService).getAllSellers();
    }
}
