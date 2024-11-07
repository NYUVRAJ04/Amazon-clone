package org.happiest.apigateway;

import org.happiest.dto.PlatformStatsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "http://AdminMicroservice/admin")
public interface AdminInterface
{
    @GetMapping("/stats")
    public ResponseEntity<PlatformStatsDTO> getPlatformStats();

    @DeleteMapping("/user/{email}")
    public String deleteUser(@PathVariable String email);
}
