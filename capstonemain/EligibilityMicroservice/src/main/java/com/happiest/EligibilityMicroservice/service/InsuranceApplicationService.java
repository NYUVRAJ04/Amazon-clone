package com.happiest.EligibilityMicroservice.service;


import com.happiest.EligibilityMicroservice.dto.BuyerDTO;
import com.happiest.EligibilityMicroservice.dto.InsuranceApplicationRequest;
import com.happiest.EligibilityMicroservice.dto.InsuranceApplicationResponse;
import com.happiest.EligibilityMicroservice.exception.InsuranceApplicationException;
import com.happiest.EligibilityMicroservice.model.BuyersEntity;
import com.happiest.EligibilityMicroservice.model.InsuranceApplication;
import com.happiest.EligibilityMicroservice.repository.BuyersRepository;
import com.happiest.EligibilityMicroservice.repository.InsuranceApplicationRepository;
import com.happiest.EligibilityMicroservice.exception.ResourceNotFoundException;
import com.happiest.EligibilityMicroservice.utility.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class InsuranceApplicationService {

    @Autowired
    private InsuranceApplicationRepository insuranceApplicationRepository;

    @Autowired
    private BuyersRepository buyersRepository;

    @Autowired
    private BuyerHelper buyerHelper;

    @Autowired
    private AuthServiceClient authServiceClient; // Feign Client for Auth Service

    @Autowired
    private EligibilityEvaluator eligibilityEvaluator;

    @Autowired
    private InsuranceApplicationMapper applicationMapper;

    @Autowired
    private MessageSource messageSource;

    @Transactional
    public BuyersEntity updateBuyer(Long buyerId, BuyerDTO buyerDTO) {
        try {
            BuyersEntity buyersEntity = buyersRepository.findById(buyerId)
                    .orElseThrow(() -> new ResourceNotFoundException(messageSource.getMessage("buyer.not.found", new Object[]{buyerId}, Locale.getDefault())));

            buyerHelper.updateFromDTO(buyersEntity, buyerDTO);
            buyersEntity.setEligibleForInsurance(eligibilityEvaluator.evaluateInsuranceEligibility(buyersEntity));

            return buyersRepository.save(buyersEntity);
        } catch (Exception ex) {
            throw new InsuranceApplicationException(ApplicationConstants.UPDATE_BUYER_FAILED, ex);
        }
    }

    @Transactional
    public InsuranceApplicationResponse applyForInsurance(InsuranceApplicationRequest request) {
        try {
            BuyerDTO buyerDTO = authServiceClient.isBuyerExists(request.getBuyerId());
            if (buyerDTO == null) {
                throw new ResourceNotFoundException(messageSource.getMessage("buyer.not.found", new Object[]{request.getBuyerId()}, Locale.getDefault()));
            }

            BuyersEntity buyersEntity = buyersRepository.findById(request.getBuyerId())
                    .orElseGet(() -> buyersRepository.save(new BuyersEntity()));

            BuyerHelper.updateFromDTO(buyersEntity, buyerDTO);
            buyersEntity.setEligibleForInsurance(eligibilityEvaluator.evaluateInsuranceEligibility(buyersEntity));
            String status = buyersEntity.isEligibleForInsurance() ? "APPROVED" : "REJECTED";

            InsuranceApplication application = new InsuranceApplication(request, buyersEntity, status);
            InsuranceApplication savedApplication = insuranceApplicationRepository.save(application);

            return applicationMapper.mapToResponse(savedApplication);
        } catch (Exception ex) {
            throw new InsuranceApplicationException(ApplicationConstants.INSURANCE_APPLICATION_FAILED, ex);
        }
    }

    public List<InsuranceApplicationResponse> getApplicationsForBuyer(Long buyerId) {
        return insuranceApplicationRepository.findByBuyerBuyerId(buyerId).stream()
                .map(applicationMapper::mapToResponse)
                .collect(Collectors.toList());
    }

    public InsuranceApplicationResponse getApplicationById(Long applicationId) {
        try {
            InsuranceApplication application = insuranceApplicationRepository.findById(applicationId)
                    .orElseThrow(() -> new ResourceNotFoundException(messageSource.getMessage("application.not.found", new Object[]{applicationId}, Locale.getDefault())));
            return applicationMapper.mapToResponse(application);
        } catch (Exception ex) {
            throw new InsuranceApplicationException( ApplicationConstants.INSURANCE_APPLICATION_FAILED, ex);
        }
    }

    @Transactional
    public InsuranceApplicationResponse updateApplicationStatus(Long applicationId, String status) {
        try {
            InsuranceApplication application = insuranceApplicationRepository.findById(applicationId)
                    .orElseThrow(() -> new ResourceNotFoundException((messageSource.getMessage("application.not.found", new Object[]{applicationId}, Locale.getDefault()))));
            application.setStatus(status);
            insuranceApplicationRepository.save(application);
            return applicationMapper.mapToResponse(application);
        } catch (Exception ex) {
            throw new InsuranceApplicationException(ApplicationConstants.INSURANCE_APPLICATION_FAILED, ex);
        }
    }

    public Long getTotalInsuranceCount() {
        try {
            return insuranceApplicationRepository.count();
        } catch (Exception ex) {
            throw new InsuranceApplicationException(ApplicationConstants.COUNT_INSURANCE_FAILED, ex);
        }
    }
}
