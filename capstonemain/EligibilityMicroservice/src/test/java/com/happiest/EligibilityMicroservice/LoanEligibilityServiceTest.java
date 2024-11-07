package com.happiest.EligibilityMicroservice;

import com.happiest.EligibilityMicroservice.dto.LoanApplicationDTO;
import com.happiest.EligibilityMicroservice.dto.LoanEligibilityResponseDTO;
import com.happiest.EligibilityMicroservice.exception.LoanApplicationException;
import com.happiest.EligibilityMicroservice.model.BuyersEntity;
import com.happiest.EligibilityMicroservice.model.LoanApplication;
import com.happiest.EligibilityMicroservice.repository.BuyersRepository;
import com.happiest.EligibilityMicroservice.repository.LoanApplicationRepository;
import com.happiest.EligibilityMicroservice.service.LoanEligibilityService;
import com.happiest.EligibilityMicroservice.utility.ApplicationConstants;
import com.happiest.EligibilityMicroservice.utility.LoanEligibilityHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanEligibilityServiceTest {

    @Mock
    private LoanApplicationRepository loanApplicationRepository;

    @Mock
    private BuyersRepository buyerRepository;

    @Mock
    private LoanEligibilityHelper loanEligibilityHelper;

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private LoanEligibilityService loanEligibilityService;

    private LoanApplicationDTO loanApplicationDTO;
    private BuyersEntity buyer;
    private LoanEligibilityResponseDTO eligibilityResponse;

    @BeforeEach
    void setUp() {
        loanApplicationDTO = new LoanApplicationDTO();
        loanApplicationDTO.setBuyerId(1L);
        loanApplicationDTO.setAmountRequested(BigDecimal.valueOf(50000.00));
        loanApplicationDTO.setPurpose("Agricultural Expansion");
        loanApplicationDTO.setAnnualIncome(100000.00);
        loanApplicationDTO.setCreditScore(700);

        buyer = new BuyersEntity();
        buyer.setBuyerId(1L);
        buyer.setAnnualIncome(100000.00);
        buyer.setCreditScore(700);
        buyer.setEligibleForLoan(true);

        eligibilityResponse = new LoanEligibilityResponseDTO();
        eligibilityResponse.setEligible(true);
        eligibilityResponse.setMessage("Eligible for loan approval");
    }

    @Test
    void testSubmitAndCheckLoanApplication_whenEligible() {
        when(buyerRepository.findById(loanApplicationDTO.getBuyerId())).thenReturn(Optional.of(buyer));
        when(loanEligibilityHelper.checkEligibility(buyer)).thenReturn(eligibilityResponse);

        LoanEligibilityResponseDTO response = loanEligibilityService.submitAndCheckLoanApplication(loanApplicationDTO);

        assertTrue(response.isEligible());
        assertEquals("Eligible for loan approval", response.getMessage());
        verify(loanApplicationRepository, times(1)).save(any(LoanApplication.class));
    }

    @Test
    void testSubmitAndCheckLoanApplication_whenIneligible() {
        eligibilityResponse.setEligible(false);
        eligibilityResponse.setMessage("Not eligible for loan approval");
        when(buyerRepository.findById(loanApplicationDTO.getBuyerId())).thenReturn(Optional.of(buyer));
        when(loanEligibilityHelper.checkEligibility(buyer)).thenReturn(eligibilityResponse);

        LoanEligibilityResponseDTO response = loanEligibilityService.submitAndCheckLoanApplication(loanApplicationDTO);

        assertFalse(response.isEligible());
        assertEquals("Not eligible for loan approval", response.getMessage());
        verify(loanApplicationRepository, times(1)).save(any(LoanApplication.class));
    }

    @Test
    void testSubmitAndCheckLoanApplication_whenBuyerNotFound() {
        when(buyerRepository.findById(loanApplicationDTO.getBuyerId())).thenReturn(Optional.empty());
        when(messageSource.getMessage(ApplicationConstants.LOAN_BUYER_NOT_FOUND, new Object[]{loanApplicationDTO.getBuyerId()}, Locale.getDefault()))
                .thenReturn("Buyer not found");

        LoanApplicationException exception = assertThrows(LoanApplicationException.class, () ->
                loanEligibilityService.submitAndCheckLoanApplication(loanApplicationDTO));

        assertEquals("Buyer not found", exception.getMessage());
        verify(loanApplicationRepository, never()).save(any(LoanApplication.class));
    }

    @Test
    void testSubmitAndCheckLoanApplication_whenEligibilityCheckFails() {
        when(buyerRepository.findById(loanApplicationDTO.getBuyerId())).thenReturn(Optional.of(buyer));
        when(loanEligibilityHelper.checkEligibility(buyer)).thenReturn(null);
        when(messageSource.getMessage(ApplicationConstants.LOAN_ELIGIBILITY_CHECK_FAILED, null, Locale.getDefault()))
                .thenReturn("Eligibility check failed");

        LoanApplicationException exception = assertThrows(LoanApplicationException.class, () ->
                loanEligibilityService.submitAndCheckLoanApplication(loanApplicationDTO));

        assertEquals("Eligibility check failed", exception.getMessage());
    }

    @Test
    void testGetTotalLoansCount_whenSuccessful() {
        when(loanApplicationRepository.count()).thenReturn(10L);

        Long count = loanEligibilityService.getTotalLoansCount();

        assertEquals(10L, count);
        verify(loanApplicationRepository, times(1)).count();
    }

    @Test
    void testGetTotalLoansCount_whenExceptionOccurs() {
        when(loanApplicationRepository.count()).thenThrow(new RuntimeException("Database error"));
        when(messageSource.getMessage(ApplicationConstants.LOAN_APPLICATION_COUNT_FAILED, null, Locale.getDefault()))
                .thenReturn("Failed to retrieve loan application count");

        LoanApplicationException exception = assertThrows(LoanApplicationException.class, () ->
                loanEligibilityService.getTotalLoansCount());

        assertEquals("Failed to retrieve loan application count", exception.getMessage());
    }
}
