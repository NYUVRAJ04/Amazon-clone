package com.happiest;

import com.happiest.controller.AdminController;
import com.happiest.dto.PlatformStatsDTO;
import com.happiest.feign.BuyerClient;
import com.happiest.feign.SellerClient;
import com.happiest.service.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AdminControllerTest {

    @InjectMocks
    private AdminController adminController;

    @Mock
    private AdminService adminService;

    @Mock
    private BuyerClient buyerClient;

    @Mock
    private SellerClient sellerClient;

    @Mock
    private MessageSource messageSource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPlatformStats() {
        PlatformStatsDTO statsDTO = new PlatformStatsDTO(100L, 50L, 30L, 40L, 60L);
        when(adminService.getPlatformStats()).thenReturn(statsDTO);
        when(messageSource.getMessage("platform.stats.fetch.success", null, Locale.ENGLISH)).thenReturn("Successfully fetched stats");

        ResponseEntity<PlatformStatsDTO> response = adminController.getPlatformStats(Locale.ENGLISH);

        assertEquals(ResponseEntity.ok(statsDTO), response);
        verify(adminService, times(1)).getPlatformStats();
    }

    @Test
    void testDeleteUser() {
        when(messageSource.getMessage("user.deleted.success", null, Locale.ENGLISH)).thenReturn("User deleted successfully");
        String response = adminController.deleteUser("test@example.com", Locale.ENGLISH);
        assertEquals("User deleted successfully", response);
    }
}
