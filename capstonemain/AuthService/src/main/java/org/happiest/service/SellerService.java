package org.happiest.service;

import org.happiest.constants.SellerServiceExceptionMessages;
import org.happiest.exception.SellerServiceException;
import org.happiest.model.Seller;
import org.happiest.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellerService {

    @Autowired
    private SellerRepository sellerRepository;

    public Long getTotalSellers() {
        try {
            return sellerRepository.count();
        } catch (Exception e) {
            // Handle the exception with a custom exception
            throw new SellerServiceException(SellerServiceExceptionMessages.COUNT_SELLERS_ERROR, e);
        }
    }

    public List<Seller> getAllSellers() {
        try {
            return sellerRepository.findAll();
        } catch (Exception e) {
            // Handle the exception with a custom exception
            throw new SellerServiceException(SellerServiceExceptionMessages.RETRIEVE_SELLERS_ERROR, e);
        }
    }
}
