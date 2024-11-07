package org.happiest.service;

import org.happiest.constants.ServiceLoggerConstants;
import org.happiest.exception.ServiceException;
import org.happiest.model.Buyers;
import org.happiest.repository.BuyerRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BuyerService {

    private static final Logger logger = LogManager.getLogger(BuyerService.class);

    @Autowired
    private BuyerRepository buyerRepository;

    public List<Buyers> getAllBuyers() throws ServiceException {
        logger.info(ServiceLoggerConstants.ATTEMPTING_TO_FETCH_ALL_BUYERS);

        try {
            return buyerRepository.findAll();
        } catch (DataAccessException dae) {
            logger.error(ServiceLoggerConstants.DATABASE_ERROR_FETCHING_ALL_BUYERS + ": {}", dae.getMessage(), dae);
            throw new ServiceException(ServiceLoggerConstants.DATABASE_ERROR_FETCHING_ALL_BUYERS, dae);
        }
    }

    public Optional<Buyers> getBuyerById(Long id) throws ServiceException {
        logger.info(ServiceLoggerConstants.ATTEMPTING_TO_FETCH_BUYER_BY_ID, id);

        try {
            return buyerRepository.findById(id);
        } catch (DataAccessException dae) {
            logger.error(ServiceLoggerConstants.DATABASE_ERROR_FETCHING_BUYER_BY_ID + ": {}", id, dae.getMessage(), dae);
            throw new ServiceException(ServiceLoggerConstants.DATABASE_ERROR_FETCHING_BUYER_BY_ID, dae);
        }
    }

    public Long getTotalFarmers() throws ServiceException {
        logger.info(ServiceLoggerConstants.ATTEMPTING_TO_GET_TOTAL_FARMERS);

        try {
            return buyerRepository.count();
        } catch (DataAccessException dae) {
            logger.error(ServiceLoggerConstants.DATABASE_ERROR_RETRIEVING_FARMER_COUNT + ": {}", dae.getMessage(), dae);
            throw new ServiceException(ServiceLoggerConstants.DATABASE_ERROR_RETRIEVING_FARMER_COUNT, dae);
        }
    }
}
