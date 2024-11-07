
package com.happiest;

import com.happiest.dto.PlatformStatsDTO;
import com.happiest.feign.BuyerClient;
import com.happiest.feign.InsuranceClient;
import com.happiest.feign.LoanClient;
import com.happiest.feign.SellerClient;
import com.happiest.service.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class AdminServiceTest {

    @Mock
    private BuyerClient buyerClient;

    @Mock
    private SellerClient sellerClient;

    @Mock
    private LoanClient loanClient;

    @Mock
    private InsuranceClient insuranceClient;

    @InjectMocks
    private AdminService adminService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPlatformStats() {
        when(buyerClient.getTotalFarmers()).thenReturn(10L);
        when(sellerClient.getTotalSellers()).thenReturn(20L);
        when(loanClient.getTotalLoanApplications()).thenReturn(30L);
        when(insuranceClient.getTotalInsuranceApplications()).thenReturn(40L);

        PlatformStatsDTO stats = adminService.getPlatformStats();

        assertEquals(10L, stats.getTotalFarmers());
        assertEquals(20L, stats.getTotalSellers());
        assertEquals(30L, stats.getTotalLoanApplications());
        assertEquals(40L, stats.getTotalInsuranceApplications());

        verify(buyerClient).getTotalFarmers();
        verify(sellerClient).getTotalSellers();
        verify(loanClient).getTotalLoanApplications();
        verify(insuranceClient).getTotalInsuranceApplications();
    }


    @Test
    void testGetPlatformStats_withException() {
        when(buyerClient.getTotalFarmers()).thenThrow(new RuntimeException("Service Unavailable"));

        assertThrows(RuntimeException.class, () -> adminService.getPlatformStats());

        verify(buyerClient).getTotalFarmers();
        verifyNoMoreInteractions(sellerClient, loanClient, insuranceClient);
    }



}
