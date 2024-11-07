package org.happiest.test;

import org.happiest.apigateway.*;
import org.happiest.constants.LoggerConstants;
import org.happiest.controller.UserController;
import org.happiest.dto.*;
import org.happiest.model.*;
import org.happiest.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private AwarenessInterface awarenessInterface;

    @Mock
    private SellerInterface sellerInterface;

    @Mock
    private BuyerInterface buyerInterface;

    @Mock
    private LoanEligibilityInterface loanEligibilityInterface;

    @Mock
    private InsuranceEligibilityInterface insuranceEligibilityInterface;

    @Mock
    private AdminInterface adminInterface;

    @Mock
    private CropInterface cropInterface;

    @Mock
    private MachineryInterface machineryInterface;

    @Mock
    private ProductInterface productInterface;

    @Mock
    private OrderInterface orderInterface;

    private BCryptPasswordEncoder encoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        encoder = new BCryptPasswordEncoder(12);
    }

    @Test
    void testRegisterUser() {
        Users user = new Users();
        user.setEmail("test@example.com");
        user.setPassword("password");

        when(userService.register(any())).thenReturn(new ResponseEntity<>("User registered successfully", HttpStatus.CREATED));

        ResponseEntity<String> response = userController.register(user);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("User registered successfully", response.getBody());
        verify(userService).register(any());
    }

    @Test
    void testLoginUser() {
        Users user = new Users();
        user.setEmail("test@example.com");
        user.setPassword("password");

        AuthResponse authResponse = new AuthResponse();
        when(userService.verify(any())).thenReturn(new ResponseEntity<>(authResponse, HttpStatus.OK));

        ResponseEntity<AuthResponse> response = userController.login(user);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(authResponse, response.getBody());
        verify(userService).verify(any());
    }

    @Test
    void testGetAllMachinery() {
        Machinery machinery = new Machinery();
        List<Machinery> machineries = Collections.singletonList(machinery);
        when(machineryInterface.getAllMachinery()).thenReturn(new ResponseEntity<>(machineries, HttpStatus.OK));

        ResponseEntity<List<Machinery>> response = userController.getAllMachinery();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(machineries, response.getBody());
        verify(machineryInterface).getAllMachinery();
    }

    @Test
    void testGetAllCrops() {
        Crop crop = new Crop();
        List<Crop> crops = Collections.singletonList(crop);
        when(cropInterface.getAllCrops()).thenReturn(new ResponseEntity<>(crops, HttpStatus.OK));

        ResponseEntity<List<Crop>> response = userController.getAllCrops();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(crops, response.getBody());
        verify(cropInterface).getAllCrops();
    }

    @Test
    void testDeleteUser() {
        String email = "test@example.com";
        when(adminInterface.deleteUser(email)).thenReturn(String.valueOf(new ResponseEntity<>(HttpStatus.NO_CONTENT)));

        ResponseEntity<Void> response = userController.deleteUser(email);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(adminInterface).deleteUser(email);
    }

    @Test
    void testApplyForInsurance() {
        InsuranceApplicationRequest request = new InsuranceApplicationRequest();
        InsuranceApplicationResponse responseDTO = new InsuranceApplicationResponse();
        when(insuranceEligibilityInterface.applyForInsurance(any())).thenReturn(new ResponseEntity<>(responseDTO, HttpStatus.CREATED));

        ResponseEntity<InsuranceApplicationResponse> response = userController.applyForInsurance(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(responseDTO, response.getBody());
        verify(insuranceEligibilityInterface).applyForInsurance(any());
    }

    // Add more test cases for other endpoints in UserController...

}
