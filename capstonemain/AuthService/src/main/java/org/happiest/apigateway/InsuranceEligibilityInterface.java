package org.happiest.apigateway;

import org.happiest.dto.BuyerDTO;
import org.happiest.dto.InsuranceApplicationRequest;
import org.happiest.dto.InsuranceApplicationResponse;
import org.happiest.model.Buyers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "http://EligibilityMicroservice/insurance")
public interface InsuranceEligibilityInterface
{
    @PostMapping("/apply")
    public ResponseEntity<InsuranceApplicationResponse> applyForInsurance(
            @Validated @RequestBody InsuranceApplicationRequest request);

    @PutMapping("/updatebuyer/{buyerId}")
    public ResponseEntity<Buyers> updateBuyer(@PathVariable Long buyerId, @RequestBody BuyerDTO buyerDTO);
}
