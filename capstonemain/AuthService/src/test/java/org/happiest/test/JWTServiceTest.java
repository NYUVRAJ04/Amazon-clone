package org.happiest.test;

import org.happiest.exception.TokenExpiredException;
import org.happiest.exception.TokenInvalidException;
import org.happiest.service.JWTService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class JWTServiceTest {

    private JWTService jwtService;

    @Mock
    private UserDetails userDetails;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtService = new JWTService();
    }

    @Test
    public void testGenerateToken() {
        String username = "test@example.com";
        String token = jwtService.generateToken(username);

        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    public void testExtractUserEmail() {
        String username = "test@example.com";
        String token = jwtService.generateToken(username);

        String extractedEmail = jwtService.extractUserEmail(token);

        assertEquals(username, extractedEmail);
    }

    @Test
    public void testValidateToken_Success() {
        String username = "test@example.com";
        String token = jwtService.generateToken(username);
        when(userDetails.getUsername()).thenReturn(username);

        boolean isValid = jwtService.validateToken(token, userDetails);

        assertTrue(isValid);
    }

    @Test
    public void testValidateToken_InvalidToken() {
        String token = "invalidToken";
        when(userDetails.getUsername()).thenReturn("test@example.com");

        boolean isValid = jwtService.validateToken(token, userDetails);

        assertFalse(isValid);
    }

    @Test
    public void testValidateToken_ExpiredToken() {
        String username = "test@example.com";
        String token = jwtService.generateToken(username);

        // Simulate token expiration by waiting
        try {
            Thread.sleep(100); // Wait for 1 hour (longer than the token expiration time)
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        boolean isValid = jwtService.validateToken(token, userDetails);

        assertFalse(isValid);
    }


    @Test
    public void testExtractUserEmail_TokenInvalidException() {
        assertThrows(TokenInvalidException.class, () -> {
            jwtService.extractUserEmail("invalidToken");
        });
    }

    @Test
    public void testIsTokenExpired() {
        String username = "test@example.com";
        String token = jwtService.generateToken(username);

        assertFalse(jwtService.isTokenExpired(token));
    }

    @Test
    public void testIsTokenExpired_TokenInvalidException() {
        assertTrue(jwtService.isTokenExpired("invalidToken"));
    }
}
