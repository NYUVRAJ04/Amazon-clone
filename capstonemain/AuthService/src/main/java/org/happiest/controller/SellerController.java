package org.happiest.controller;

 // Import logger constants
import org.happiest.constants.LoggerConstants;
import org.happiest.model.Seller;
import org.happiest.service.SellerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sellers")
@CrossOrigin
public class SellerController {

    private static final Logger logger = LoggerFactory.getLogger(SellerController.class);

    @Autowired
    private SellerService sellerService;

    @GetMapping("/count")
    public Long getTotalSellers() {
        logger.info(LoggerConstants.LOG_FETCHING_TOTAL_SELLERS);
        Long totalSellers = sellerService.getTotalSellers();
        logger.info(LoggerConstants.LOG_TOTAL_SELLERS_COUNT, totalSellers);
        return totalSellers;
    }

    @GetMapping("/allsellers")
    public List<Seller> getAllSellers() {
        logger.info(LoggerConstants.LOG_FETCHING_ALL_SELLERS);
        List<Seller> sellers = sellerService.getAllSellers();
        logger.info(LoggerConstants.LOG_TOTAL_SELLERS_RETRIEVED, sellers.size());
        return sellers;
    }
}
