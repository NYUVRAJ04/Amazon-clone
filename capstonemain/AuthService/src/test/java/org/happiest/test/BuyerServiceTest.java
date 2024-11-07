package org.happiest.test;

import org.happiest.constants.BuyerServiceConstants;
import org.happiest.exception.ServiceException;
import org.happiest.model.Buyers;
import org.happiest.repository.BuyerRepository;
import org.happiest.service.BuyerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BuyerServiceTest {

    @InjectMocks
    private BuyerService buyerService;

    @Mock
    private BuyerRepository buyerRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllBuyers_Success() throws ServiceException {
        Buyers buyer1 = new Buyers(); // Assuming Buyers has a default constructor
        Buyers buyer2 = new Buyers();
        when(buyerRepository.findAll()).thenReturn(Arrays.asList(buyer1, buyer2));

        assertEquals(2, buyerService.getAllBuyers().size());
        verify(buyerRepository, times(1)).findAll();
    }



    @Test
    void getBuyerById_Success() throws ServiceException {
        Long buyerId = 1L;
        Buyers buyer = new Buyers();
        when(buyerRepository.findById(buyerId)).thenReturn(Optional.of(buyer));

        Optional<Buyers> result = buyerService.getBuyerById(buyerId);
        assertTrue(result.isPresent());
        assertEquals(buyer, result.get());
        verify(buyerRepository, times(1)).findById(buyerId);
    }

    @Test
    void getBuyerById_NotFound() throws ServiceException {
        Long buyerId = 1L;
        when(buyerRepository.findById(buyerId)).thenReturn(Optional.empty());

        Optional<Buyers> result = buyerService.getBuyerById(buyerId);
        assertFalse(result.isPresent());
        verify(buyerRepository, times(1)).findById(buyerId);
    }



    @Test
    void getTotalFarmers_Success() throws ServiceException {
        when(buyerRepository.count()).thenReturn(5L);

        Long totalFarmers = buyerService.getTotalFarmers();
        assertEquals(5L, totalFarmers);
        verify(buyerRepository, times(1)).count();
    }


}
