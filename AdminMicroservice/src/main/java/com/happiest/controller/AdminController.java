package com.happiest.controller;

import com.happiest.dto.PlatformStatsDTO;
import com.happiest.feign.BuyerClient;
import com.happiest.feign.SellerClient;
import com.happiest.model.Buyers;
import com.happiest.model.Seller;
import com.happiest.service.AdminService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/admin")
@CrossOrigin
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private BuyerClient buyerClient;

    @Autowired
    private SellerClient sellerClient;

    @Autowired
    private MessageSource messageSource;

    private static final Logger logger = LogManager.getLogger(AdminController.class);


    @GetMapping("/stats")
    public ResponseEntity<PlatformStatsDTO> getPlatformStats(Locale locale) {
        logger.info("Fetching platform statistics");
        try {
            PlatformStatsDTO platformStats = adminService.getPlatformStats();
            String message = messageSource.getMessage("platform.stats.fetch.success", null, locale);
            logger.info(message);
            return ResponseEntity.ok(platformStats);
        } catch (Exception ex) {
            String errorMessage = messageSource.getMessage("platform.stats.fetch.failure", null, locale);
            logger.error(errorMessage, ex);
            return ResponseEntity.status(500).body(null);
        }
    }


    @DeleteMapping("/user/{email}")
    public String deleteUser(@PathVariable String email, Locale locale) {
        logger.info("Deleting user with email: {}", email);
        return messageSource.getMessage("user.deleted.success", null, locale);
    }

    @GetMapping("/getallbuyers")
    public List<Buyers> getBuyers()
    {
        return buyerClient.getAllBuyers();
    }

    @GetMapping("/allsellers")
    public List<Seller> getAllSellers(){
        return sellerClient.getAllSellers();
    }
}
