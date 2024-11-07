package com.happiest.EligibilityMicroservice.service;

import com.happiest.EligibilityMicroservice.dto.LoanApplicationDTO;
import com.happiest.EligibilityMicroservice.dto.LoanEligibilityResponseDTO;
import com.happiest.EligibilityMicroservice.exception.LoanApplicationException;
import com.happiest.EligibilityMicroservice.model.BuyersEntity;
import com.happiest.EligibilityMicroservice.model.LoanApplication;
import com.happiest.EligibilityMicroservice.repository.BuyersRepository;
import com.happiest.EligibilityMicroservice.repository.LoanApplicationRepository;
import com.happiest.EligibilityMicroservice.utility.ApplicationConstants;
import com.happiest.EligibilityMicroservice.utility.LoanEligibilityHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class LoanEligibilityService {

    @Autowired
    private LoanApplicationRepository loanApplicationRepository;

    @Autowired
    private BuyersRepository buyerRepository;

    @Autowired
    private LoanEligibilityHelper loanEligibilityHelper;  // Inject the helper class

    @Autowired
    MessageSource messageSource;

    /**
     * Submits a loan application and checks the eligibility of the buyer.
     * If the buyer is eligible, the loan application status is set to APPROVED; otherwise, it remains PENDING.
     *
     * @param loanApplicationDTO Data Transfer Object for the loan application.
     * @return LoanEligibilityResponseDTO indicating whether the buyer is eligible or not.
     */
    public LoanEligibilityResponseDTO submitAndCheckLoanApplication(LoanApplicationDTO loanApplicationDTO) {
        try {
            // Set buyer from the buyerId
            BuyersEntity buyer = buyerRepository.findById(loanApplicationDTO.getBuyerId())
                    .orElseThrow(() -> new LoanApplicationException(
                            messageSource.getMessage(ApplicationConstants.LOAN_BUYER_NOT_FOUND,
                                    new Object[]{loanApplicationDTO.getBuyerId()}, Locale.getDefault())));

            // Update buyer details
            buyer.setAnnualIncome(loanApplicationDTO.getAnnualIncome());
            buyer.setCreditScore(loanApplicationDTO.getCreditScore());
            buyer.setEligibleForLoan(buyer.isEligibleForLoan());  // Logic to determine eligibility should be in helper

            // Save updated buyer information
                buyerRepository.save(buyer);

            // Create a new loan application
            LoanApplication loanApplication = new LoanApplication();
            loanApplication.setBuyer(buyer);
            loanApplication.setAmountRequested(loanApplicationDTO.getAmountRequested());
            loanApplication.setPurpose(loanApplicationDTO.getPurpose());
            loanApplication.setIncomeVerification(loanApplicationDTO.getIncomeVerification());
            loanApplication.setCreditScore(loanApplicationDTO.getCreditScore());
            loanApplication.setDebtToIncomeRatio(loanApplicationDTO.getDebtToIncomeRatio());
            loanApplication.setCollateral(loanApplicationDTO.getCollateral());
            loanApplication.setStatus(LoanApplication.Status.REJECTED); // Default status

            // Check eligibility using the helper
            LoanEligibilityResponseDTO response = loanEligibilityHelper.checkEligibility(buyer);

            // Ensure the response is not null
            if (response == null) {
                throw new LoanApplicationException(
                        messageSource.getMessage(ApplicationConstants.LOAN_ELIGIBILITY_CHECK_FAILED, null, Locale.getDefault()));
            }

            // Update loan application status based on eligibility
            if (response.isEligible()) {
                loanApplication.setStatus(LoanApplication.Status.APPROVED);
            }

            // Save the loan application regardless of the eligibility outcome
            loanApplicationRepository.save(loanApplication);
            return response;
        }  catch (LoanApplicationException ex) {
            // Re-throw custom exceptions
            throw ex;
        } catch (Exception ex) {
            // Handle any unexpected exceptions
            throw new LoanApplicationException(
                    messageSource.getMessage(ApplicationConstants.LOAN_APPLICATION_FAILED, null, Locale.getDefault()), ex);
        }
    }

    public Long getTotalLoansCount() {
        try {

            return loanApplicationRepository.count();
        } catch (Exception ex) {
            throw new LoanApplicationException(messageSource.getMessage(ApplicationConstants.LOAN_APPLICATION_COUNT_FAILED, null, Locale.getDefault()), ex);
        }
    }
}