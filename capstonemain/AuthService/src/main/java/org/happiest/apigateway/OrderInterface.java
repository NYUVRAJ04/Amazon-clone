package org.happiest.apigateway;


import org.happiest.model.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="http://ProductMicroservice/api/orders")
public interface OrderInterface
{
    @PostMapping("/addorder")
    public Order createOrder(@RequestBody Order order);

}
