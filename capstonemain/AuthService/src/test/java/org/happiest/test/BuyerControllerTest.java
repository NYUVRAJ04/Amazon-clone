package org.happiest.test;

import org.happiest.constants.LoggerConstants;
import org.happiest.controller.BuyerController;
import org.happiest.model.Buyers;
import org.happiest.service.BuyerService;
import org.happiest.service.SellerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class BuyerControllerTest {

    @InjectMocks
    private BuyerController buyerController;

    @Mock
    private BuyerService buyerService;

    @Mock
    private SellerService sellerService;

    private static final Logger logger = LoggerFactory.getLogger(BuyerControllerTest.class);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetBuyerById_Found() {
        Long buyerId = 1L;
        Buyers buyer = new Buyers(); // Initialize with necessary fields
        buyer.setBuyerId(buyerId);

        when(buyerService.getBuyerById(buyerId)).thenReturn(Optional.of(buyer));

        ResponseEntity<Buyers> responseEntity = buyerController.getBuyerById(buyerId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(buyer, responseEntity.getBody());
    }

    @Test
    void testGetBuyerById_NotFound() {
        Long buyerId = 1L;

        when(buyerService.getBuyerById(buyerId)).thenReturn(Optional.empty());

        ResponseEntity<Buyers> responseEntity = buyerController.getBuyerById(buyerId);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(null, responseEntity.getBody());
    }

    @Test
    void testGetTotalBuyers() {
        long expectedCount = 10L;

        when(buyerService.getTotalFarmers()).thenReturn(expectedCount);

        Long totalBuyers = buyerController.getTotalFarmers();

        assertEquals(expectedCount, totalBuyers);
    }

    @Test
    void testGetTotalSellers() {
        long expectedCount = 5L;

        when(sellerService.getTotalSellers()).thenReturn(expectedCount);

        Long totalSellers = buyerController.getTotalSellers();

        assertEquals(expectedCount, totalSellers);
    }

    @Test
    void testGetAllBuyers() {
        Buyers buyer1 = new Buyers(); // Initialize with necessary fields
        buyer1.setBuyerId(1L);
        Buyers buyer2 = new Buyers(); // Initialize with necessary fields
        buyer2.setBuyerId(2L);
        List<Buyers> buyersList = Arrays.asList(buyer1, buyer2);

        when(buyerService.getAllBuyers()).thenReturn(buyersList);

        List<Buyers> retrievedBuyers = buyerController.getAllBuyers();

        assertEquals(buyersList.size(), retrievedBuyers.size());
        assertEquals(buyersList, retrievedBuyers);
    }
}
