package com.happiest.EligibilityMicroservice;

import com.happiest.EligibilityMicroservice.exception.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    // Test case for handling MethodArgumentNotValidException
//    @Test
//    void testHandleValidationExceptions() {
//        // Prepare a mock MethodArgumentNotValidException
//        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
//        FieldError fieldError = new FieldError("objectName", "fieldName", "error message");
//
//        // Setup the behavior of the mocked exception
//        when(exception.getBindingResult().getAllErrors()).thenReturn(List.of(fieldError));
//
//        // Invoke the method
//        ResponseEntity<Map<String, String>> response = globalExceptionHandler.handleValidationExceptions(exception);
//
//        // Assertions
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//        Map<String, String> errors = response.getBody();
//        assertNotNull(errors);
//        assertEquals(1, errors.size());
//        assertTrue(errors.containsKey("fieldName"));
//        assertEquals("error message", errors.get("fieldName"));
//    }

    // Test case for ResourceNotFoundException
    @Test
    void testHandleResourceNotFoundException() {
        String message = "Resource not found";
        ResourceNotFoundException exception = new ResourceNotFoundException(message);

        ResponseEntity<?> response = globalExceptionHandler.handleResourceNotFoundException(exception, null);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(message, response.getBody());
    }

    // Test case for InsuranceApplicationException
    @Test
    void testHandleInsuranceApplicationException() {
        String message = "Insurance application error";
        InsuranceApplicationException exception = new InsuranceApplicationException(message);

        ResponseEntity<?> response = globalExceptionHandler.handleInsuranceApplicationException(exception, null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(message, response.getBody());
    }

    // Test case for general Exception handling
    @Test
    void testHandleGeneralException() {
        Exception exception = new Exception("General error occurred");

        ResponseEntity<String> response = globalExceptionHandler.handleExceptions(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An error occurred: General error occurred", response.getBody());
    }

    // Test case for BuyerNotFoundException
    @Test
    void testHandleBuyerNotFoundException() {
        String message = "Buyer not found";
        BuyerNotFoundException exception = new BuyerNotFoundException(message);

        ResponseEntity<?> response = globalExceptionHandler.handleBuyerNotFoundException(exception, null);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(message, response.getBody());
    }

    // Test case for LoanApplicationException
    @Test
    void testHandleLoanApplicationException() {
        String message = "Loan application error";
        LoanApplicationException exception = new LoanApplicationException(message);

        ResponseEntity<?> response = globalExceptionHandler.handleLoanApplicationException(exception, null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(message, response.getBody());
    }
}
