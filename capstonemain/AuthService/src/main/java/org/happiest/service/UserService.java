package org.happiest.service;

import org.happiest.constants.UserServiceExceptionMessages;
import org.happiest.dto.AuthResponse;
import org.happiest.exception.UserServiceException;
import org.happiest.model.Admin;
import org.happiest.model.Buyers;
import org.happiest.model.Seller;
import org.happiest.model.Users;
import org.happiest.repository.AdminRepository;
import org.happiest.repository.BuyerRepository;
import org.happiest.repository.SellerRepository;
import org.happiest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository repo;

    @Autowired
    private BuyerRepository buyerRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthenticationManager authManager;

//    @Bean
//    public RestTemplate restTemplate() {
//        return new RestTemplate();
//    }

    // Method to register users based on their role
    public ResponseEntity<String> register(Users user) {
        String role = user.getRole();

        // Save the user in the Users table
        Users savedUser = repo.save(user);


        switch (role.toLowerCase()) {
            case "admin":
                Optional<Users> existingAdmin = repo.findByRole("admin");
                if (existingAdmin.isPresent()) {
                    System.out.println(UserServiceExceptionMessages.ADMIN_EXISTS);
                } else {
                    Admin admin = new Admin();
                    admin.setUser(savedUser);
                    admin.setAdminId(Admin.generateAdminId(12345));
                    adminRepository.save(admin);
                }
                break;
            case "seller":
                Seller seller = new Seller();
                seller.setUser(savedUser);
                seller.setBusinessName("Default Business Name");
                seller.setGstNumber("GST12345");
                sellerRepository.save(seller);
                break;
            case "buyer":
                Buyers buyer = new Buyers();
                buyer.setUser(savedUser);
                buyerRepository.save(buyer);
                break;
            default:
                throw new UserServiceException("Invalid role specified");
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("User registered successfully with role: " + role);
    }

    public ResponseEntity<AuthResponse> verify(Users user) {

        Optional<Users> existingUserOpt = repo.findByEmail(user.getEmail());

        if (existingUserOpt.isPresent()) {
            Users existingUser = existingUserOpt.get();
            System.out.println("User found: " + existingUser.getEmail());

            String encodedPassword = existingUser.getPassword();
            String rawPassword = user.getPassword();
            System.out.println("Encoded password from DB: " + encodedPassword);
            System.out.println("Raw password provided: " + rawPassword);

            // Ensure no leading or trailing whitespaces
            rawPassword = rawPassword.trim();

            boolean matches = passwordEncoder.matches(rawPassword, encodedPassword);
            System.out.println("Password match result: " + matches);

            if (matches) {
                    System.out.println("Password matched for user: " + existingUser.getEmail());

                String token = jwtService.generateToken(existingUser.getEmail());
                String name = existingUser.getName();
                String role = existingUser.getRole();

                Long buyerId = null;
                Long sellerId = null;

                switch (role.toLowerCase()) {
                    case "buyer":
                        Optional<Buyers> buyerOpt = buyerRepository.findByUserEmail(existingUser.getEmail());
                        if (buyerOpt.isPresent()) {
                            buyerId = buyerOpt.get().getBuyerId();
                            System.out.println("Buyer ID found: " + buyerId);
                        } else {
                            throw new UserServiceException(UserServiceExceptionMessages.BUYER_NOT_FOUND);
                        }
                        break;
                    case "seller":
                        Optional<Seller> sellerOpt = sellerRepository.findByUserEmail(existingUser.getEmail());
                        if (sellerOpt.isPresent()) {
                            sellerId = sellerOpt.get().getSellerId();
                            System.out.println("Seller ID found: " + sellerId);
                        } else {
                            throw new UserServiceException(UserServiceExceptionMessages.SELLER_NOT_FOUND);
                        }
                        break;
                }

                AuthResponse authResponse = new AuthResponse(token, name, role, buyerId, sellerId, null);
                return ResponseEntity.ok(authResponse);
            } else {
                System.out.println("Password did not match for user: " + existingUser.getEmail());
                throw new UserServiceException(UserServiceExceptionMessages.INVALID_PASSWORD);
            }
        } else {
            System.out.println("User not found with email: " + user.getEmail());
            throw new UserServiceException(UserServiceExceptionMessages.USER_NOT_FOUND);
        }
    }
}
