package org.happiest.test;

import org.happiest.exception.UserNotFoundException;
import org.happiest.model.UserPrincipal;
import org.happiest.model.Users;
import org.happiest.repository.UserRepository;
import org.happiest.service.MyUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MyUserDetailsServiceTest {

    @InjectMocks
    private MyUserDetailsService myUserDetailsService;

    @Mock
    private UserRepository userRepository;

    private Users testUser;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        testUser = new Users();
        testUser.setEmail("test@example.com");
        testUser.setPassword("testPassword");
        testUser.setName("Test User"); // Ensure name is set
        // Set other properties of testUser as needed
    }


    @Test
    public void testLoadUserByEmail_ErrorWhileLoading() {
        when(userRepository.findByEmail("error@example.com")).thenThrow(new RuntimeException("Database error"));

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            myUserDetailsService.loadUserByEmail("error@example.com");
        });

        assertEquals("An error occurred while loading user by email: error@example.com", exception.getMessage());
    }
}


