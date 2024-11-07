package org.happiest.controller;

import org.happiest.apigateway.*;
import org.happiest.constants.LoggerConstants;
import org.happiest.dto.*;
import org.happiest.model.*;
import org.happiest.service.BuyerService;
import org.happiest.service.UserService; // Import logger constants
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Autowired
    private AwarenessInterface awarenessInterface;

    @Autowired
    private SellerInterface sellerInterface;

    @Autowired
    private BuyerInterface buyerInterface;

    @Autowired
    private UserService userService;

    @Autowired
    private LoanEligibilityInterface loanEligibilityInterface;

    @Autowired
    private InsuranceEligibilityInterface insuranceEligibilityInterface;

    @Autowired
    private AdminInterface adminInterface;

    @Autowired
    private CropInterface cropInterface;

    @Autowired
    private MachineryInterface machineryInterface;

    @Autowired
    private ProductInterface productInterface;

    @Autowired
    private OrderInterface orderInterface;

    @Autowired
    private BuyerService buyerService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Users user) {
        logger.info(LoggerConstants.LOG_REGISTERING_USER, user.getEmail());
//encoding
        user.setPassword(encoder.encode(user.getPassword())); // Encrypt the password
        return userService.register(user);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody Users user) {
        logger.info(LoggerConstants.LOG_USER_LOGIN_ATTEMPT, user.getEmail());
        return userService.verify(user);
    }

    @GetMapping("/seller")
    public String sell() {
        logger.info(LoggerConstants.LOG_SELLER_ENDPOINT_ACCESSED);
        return sellerInterface.welcome();
    }

    @GetMapping("/buyer")
    public String buy() {
        logger.info(LoggerConstants.LOG_BUYER_ENDPOINT_ACCESSED);
        return buyerInterface.welcomeBuyer();
    }

    @PostMapping("/upload")
    public ResponseEntity<AwarenessContent> uploadFile(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("file") MultipartFile file) {
        logger.info(LoggerConstants.LOG_FILE_UPLOAD_TITLE, title);
        String contentType = file.getContentType();
        return awarenessInterface.uploadFile(title, description, file, contentType);
    }

    @GetMapping("/stats")
    public ResponseEntity<PlatformStatsDTO> getPlatformStats() {
        logger.info(LoggerConstants.LOG_FETCHING_PLATFORM_STATS);
        return adminInterface.getPlatformStats();
    }

    @DeleteMapping("/user/{email}")
    public ResponseEntity<Void> deleteUser(@PathVariable String email) {
        logger.info(LoggerConstants.LOG_DELETING_USER, email);
        adminInterface.deleteUser(email);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/insurancestatus")
    public ResponseEntity<InsuranceApplicationResponse> applyForInsurance(
            @Validated @RequestBody InsuranceApplicationRequest request) {
        logger.info(LoggerConstants.LOG_INSURANCE_APPLICATION, request);
        return insuranceEligibilityInterface.applyForInsurance(request);
    }

    @PutMapping("/updatebuyer/{buyerId}")
    public ResponseEntity<Buyers> updateBuyer(@PathVariable Long buyerId, @RequestBody BuyerDTO buyerDTO) {
        logger.info(LoggerConstants.LOG_UPDATING_BUYER, buyerId);
        return insuranceEligibilityInterface.updateBuyer(buyerId, buyerDTO);
    }

    @PostMapping("/loanstatus")
    public ResponseEntity<LoanEligibilityResponseDTO> submitLoanApplication(
            @RequestBody LoanApplicationDTO loanApplicationDTO) {
        logger.info(LoggerConstants.LOG_LOAN_APPLICATION_SUBMISSION, loanApplicationDTO);
        return loanEligibilityInterface.submitLoanApplication(loanApplicationDTO);
    }

    @GetMapping("/allmachineries")
    public ResponseEntity<List<Machinery>> getAllMachinery() {
        logger.info(LoggerConstants.LOG_FETCHING_ALL_MACHINERY);
        return machineryInterface.getAllMachinery();
    }

    @GetMapping("/allcrops")
    public ResponseEntity<List<Crop>> getAllCrops() {
        logger.info(LoggerConstants.LOG_FETCHING_ALL_CROPS);
        return cropInterface.getAllCrops();
    }

    @GetMapping("/products")
    public ResponseEntity<List<Object>> getAllProducts() {
        logger.info(LoggerConstants.LOG_FETCHING_ALL_PRODUCTS);
        return productInterface.getAllProducts();
    }

    @GetMapping("/searchproducts")
    public ResponseEntity<List<Object>> searchProducts(@RequestParam String searchTerm) {
        logger.info(LoggerConstants.LOG_SEARCHING_PRODUCTS, searchTerm);
        return productInterface.searchProducts(searchTerm);
    }

    @PostMapping("/placeorder")
    public Order createOrder(@RequestBody Order order) {
        logger.info(LoggerConstants.LOG_CREATING_ORDER, order);
        return orderInterface.createOrder(order);
    }

    @PostMapping("/seller/add")
    public ResponseEntity<?> addProduct(@RequestPart("product") String productJson, @RequestPart("file") MultipartFile file) {
        logger.info(LoggerConstants.LOG_ADDING_PRODUCT, productJson);
        return productInterface.addProduct(productJson, file);
    }

    @GetMapping("/buyer/{id}")
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
}
