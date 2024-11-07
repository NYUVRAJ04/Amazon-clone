package com.happiest.EligibilityMicroservice;

import com.happiest.EligibilityMicroservice.dto.BuyerDTO;
import com.happiest.EligibilityMicroservice.dto.InsuranceApplicationRequest;
import com.happiest.EligibilityMicroservice.dto.InsuranceApplicationResponse;
import com.happiest.EligibilityMicroservice.exception.InsuranceApplicationException;
import com.happiest.EligibilityMicroservice.exception.ResourceNotFoundException;
import com.happiest.EligibilityMicroservice.model.BuyersEntity;
import com.happiest.EligibilityMicroservice.model.InsuranceApplication;
import com.happiest.EligibilityMicroservice.repository.BuyersRepository;
import com.happiest.EligibilityMicroservice.repository.InsuranceApplicationRepository;
import com.happiest.EligibilityMicroservice.service.InsuranceApplicationService;
import com.happiest.EligibilityMicroservice.utility.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class InsuranceApplicationServiceTest {

    @Mock
    private InsuranceApplicationRepository insuranceApplicationRepository;

    @Mock
    private BuyersRepository buyersRepository;

    @Mock
    private BuyerHelper buyerHelper;

    @Mock
    private AuthServiceClient authServiceClient;

    @Mock
    private EligibilityEvaluator eligibilityEvaluator;

    @Mock
    private InsuranceApplicationMapper applicationMapper;

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private InsuranceApplicationService insuranceApplicationService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    public void testApplyForInsuranceSuccess() {
//        InsuranceApplicationRequest request = new InsuranceApplicationRequest();
//        request.setBuyerId(1L);
//        request.setInsuranceType("Life");
//
//        BuyerDTO buyerDTO = new BuyerDTO();
//        buyerDTO.setBuyerId(1L);
//        buyerDTO.setCreditScore(700);
//
//        BuyersEntity buyersEntity = new BuyersEntity();
//        buyersEntity.setEligibleForInsurance(true);
//
//        when(authServiceClient.isBuyerExists(request.getBuyerId())).thenReturn(buyerDTO);
//        when(buyersRepository.findById(request.getBuyerId())).thenReturn(Optional.of(buyersEntity));
//        when(eligibilityEvaluator.evaluateInsuranceEligibility(buyersEntity)).thenReturn(true);
//        when(insuranceApplicationRepository.save(any())).thenReturn(new InsuranceApplication());
//        when(applicationMapper.mapToResponse(any())).thenReturn(new InsuranceApplicationResponse());
//
//        InsuranceApplicationResponse response = insuranceApplicationService.applyForInsurance(request);
//
//        assertNotNull(response);
//    }

//    @Test
//    public void testApplyForInsuranceBuyerNotFound() {
//        InsuranceApplicationRequest request = new InsuranceApplicationRequest();
//        request.setBuyerId(1L);
//
//        when(authServiceClient.isBuyerExists(request.getBuyerId())).thenReturn(null);
//        when(messageSource.getMessage("buyer.not.found", new Object[]{request.getBuyerId()}, Locale.getDefault())).thenReturn("Buyer not found");
//
//        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
//                insuranceApplicationService.applyForInsurance(request));
//
//        assertEquals("Buyer not found", exception.getMessage());
//    }

    @Test
    public void testUpdateBuyerSuccess() {
        Long buyerId = 1L;
        BuyerDTO buyerDTO = new BuyerDTO();
        buyerDTO.setBuyerId(buyerId);
        buyerDTO.setCreditScore(750);

        BuyersEntity buyersEntity = new BuyersEntity();
        buyersEntity.setBuyerId(buyerId);
        buyersEntity.setEligibleForInsurance(true);

        when(buyersRepository.findById(buyerId)).thenReturn(Optional.of(buyersEntity));
        when(eligibilityEvaluator.evaluateInsuranceEligibility(buyersEntity)).thenReturn(true);
        when(buyersRepository.save(any())).thenReturn(buyersEntity);

        assertDoesNotThrow(() -> insuranceApplicationService.updateBuyer(buyerId, buyerDTO));
        verify(buyersRepository, times(1)).save(buyersEntity);
    }

//    @Test
//    public void testUpdateBuyerNotFound() {
//        Long buyerId = 1L;
//        BuyerDTO buyerDTO = new BuyerDTO();
//        buyerDTO.setBuyerId(buyerId);
//
//        when(buyersRepository.findById(buyerId)).thenReturn(Optional.empty());
//        when(messageSource.getMessage("buyer.not.found", new Object[]{buyerId}, Locale.getDefault())).thenReturn("Buyer not found");
//
//        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
//                insuranceApplicationService.updateBuyer(buyerId, buyerDTO));
//
//        assertEquals("Buyer not found", exception.getMessage());
//    }

    @Test
    public void testUpdateApplicationStatusSuccess() {
        Long applicationId = 1L;
        String newStatus = "Approved";

        InsuranceApplication application = new InsuranceApplication();
        application.setApplicationId(applicationId);
        application.setStatus("Pending");

        when(insuranceApplicationRepository.findById(applicationId)).thenReturn(Optional.of(application));
        when(insuranceApplicationRepository.save(application)).thenReturn(application);

        assertDoesNotThrow(() -> insuranceApplicationService.updateApplicationStatus(applicationId, newStatus));
        assertEquals("Approved", application.getStatus());
    }

//    @Test
//    public void testUpdateApplicationStatusNotFound() {
//        Long applicationId = 1L;
//        String newStatus = "Approved";
//
//        when(insuranceApplicationRepository.findById(applicationId)).thenReturn(Optional.empty());
//        when(messageSource.getMessage("application.not.found", new Object[]{applicationId}, Locale.getDefault())).thenReturn("Application not found");
//
//        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
//                insuranceApplicationService.updateApplicationStatus(applicationId, newStatus));
//
//        assertEquals("Application not found", exception.getMessage());
//    }
}
