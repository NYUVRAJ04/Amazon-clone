package com.happiest.feign;

import com.happiest.model.Seller;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@FeignClient(name = "http://AuthService/sellers")
public interface SellerClient
{
    @GetMapping("/count")
    public Long getTotalSellers();

    @GetMapping("/allsellers")
    public List<Seller> getAllSellers();

}
