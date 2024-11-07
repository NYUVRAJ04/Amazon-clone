package org.happiest.controller;


import org.happiest.constants.LoggerConstants;
import org.happiest.model.Buyers;
import org.happiest.service.BuyerService;
import org.happiest.service.SellerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/buyerdata")
@CrossOrigin
public class BuyerController {

    private static final Logger logger = LoggerFactory.getLogger(BuyerController.class);

    @Autowired
    private BuyerService buyerService;

    @Autowired
    private SellerService sellerService;

    @GetMapping("/{id}")
    public ResponseEntity<Buyers> getBuyerById(@PathVariable Long id) {
        logger.info(LoggerConstants.LOG_FETCHING_BUYER_BY_ID, id);
        Optional<Buyers> buyer = buyerService.getBuyerById(id);

        return buyer.map(buyers -> {
            logger.info(LoggerConstants.LOG_BUYER_FOUND, buyers);
            return new ResponseEntity<>(buyers, HttpStatus.OK);
        }).orElseGet(() -> {
            logger.warn(LoggerConstants.LOG_BUYER_NOT_FOUND, id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        });
    }

    @GetMapping("/buyers/count")
    public Long getTotalFarmers() {
        long count = buyerService.getTotalFarmers();
        logger.info(LoggerConstants.LOG_TOTAL_BUYERS, count);
        return count;
    }

    @GetMapping("/sellers/count")
    public Long getTotalSellers() {
        long count = sellerService.getTotalSellers();
        logger.info(LoggerConstants.LOG_TOTAL_SELLERS, count);
        return count;
    }

    @GetMapping("/allbuyers")
    public List<Buyers> getAllBuyers() {
        logger.info(LoggerConstants.LOG_FETCHING_ALL_BUYERS);
        List<Buyers> buyersList = buyerService.getAllBuyers();
        logger.info(LoggerConstants.LOG_TOTAL_BUYERS_RETRIEVED, buyersList.size());
        return buyersList;
    }
}
