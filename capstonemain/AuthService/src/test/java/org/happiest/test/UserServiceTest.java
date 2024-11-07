package org.happiest.test;

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
import org.happiest.service.JWTService;
import org.happiest.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BuyerRepository buyerRepository;

    @Mock
    private SellerRepository sellerRepository;

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private JWTService jwtService;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    private Users user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new Users();
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setRole("buyer"); // Change this based on tests
        user.setName("Test User"); // Ensure name is set
    }

    @Test
    void testRegisterBuyer() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(any(Users.class))).thenReturn(user);
        when(buyerRepository.save(any(Buyers.class))).thenReturn(new Buyers());

        ResponseEntity<String> response = userService.register(user);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals("User registered successfully with role: buyer", response.getBody());
        verify(userRepository).save(any(Users.class));
        verify(buyerRepository).save(any(Buyers.class));
    }

    @Test
    void testRegisterSeller() {
        user.setRole("seller");
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(any(Users.class))).thenReturn(user);
        when(sellerRepository.save(any(Seller.class))).thenReturn(new Seller());

        ResponseEntity<String> response = userService.register(user);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals("User registered successfully with role: seller", response.getBody());
        verify(userRepository).save(any(Users.class));
        verify(sellerRepository).save(any(Seller.class));
    }

    @Test
    void testRegisterEmailAlreadyExists() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        Exception exception = assertThrows(UserServiceException.class, () -> userService.register(user));

        assertEquals("Error during registration: Email is already registered", exception.getMessage());
        verify(userRepository, never()).save(any(Users.class));
    }

    @Test
    void testVerifyBuyer() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user.getEmail())).thenReturn("mockedToken");
        when(passwordEncoder.matches(any(CharSequence.class), anyString())).thenReturn(true);
        Buyers buyer = new Buyers();
        buyer.setUser(user);
        when(buyerRepository.findByUserEmail(user.getEmail())).thenReturn(Optional.of(buyer));

        AuthResponse authResponse = userService.verify(user).getBody();

        assertNotNull(authResponse);
        assertEquals("mockedToken", authResponse.getToken());
        assertEquals(user.getName(), authResponse.getName());
        assertEquals("buyer", authResponse.getRole());
    }

    @Test
    void testVerifySeller() {
        user.setRole("seller");
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user.getEmail())).thenReturn("mockedToken");
        when(passwordEncoder.matches(any(CharSequence.class), anyString())).thenReturn(true);
        Seller seller = new Seller();
        seller.setUser(user);
        when(sellerRepository.findByUserEmail(user.getEmail())).thenReturn(Optional.of(seller));

        AuthResponse authResponse = userService.verify(user).getBody();

        assertNotNull(authResponse);
        assertEquals("mockedToken", authResponse.getToken());
        assertEquals(user.getName(), authResponse.getName());
        assertEquals("seller", authResponse.getRole());
    }

    @Test
    void testVerifyUserNotFound() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());

        Exception exception = assertThrows(UserServiceException.class, () -> userService.verify(user));

        assertEquals("Authentication failed: User not found", exception.getMessage());
    }

    @Test
    void testVerifyInvalidPassword() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(any(CharSequence.class), anyString())).thenReturn(false);

        Exception exception = assertThrows(UserServiceException.class, () -> userService.verify(user));

        assertEquals("Authentication failed: Invalid password", exception.getMessage());
    }
}
