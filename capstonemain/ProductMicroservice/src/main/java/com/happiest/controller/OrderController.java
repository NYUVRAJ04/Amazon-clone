package com.happiest.controller;

import com.happiest.constants.Constants; // Importing the Constants class
import com.happiest.model.Order;
import com.happiest.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @PostMapping("/addorder")
    public Order createOrder(@RequestBody Order order) {
        logger.info(Constants.LOG_CREATING_ORDER, order);
        Order createdOrder = orderService.createOrder(order);
        logger.info(Constants.LOG_ORDER_CREATED_SUCCESS, createdOrder.getOrderId());
        return createdOrder;
    }

    @GetMapping("/allorders")
    public List<Order> getAllOrders() {
        logger.info(Constants.LOG_RETRIEVING_ALL_ORDERS);
        List<Order> orders = orderService.getAllOrders();
        logger.info(Constants.LOG_TOTAL_ORDERS_FOUND, orders.size());
        return orders;
    }

    @GetMapping("/{orderId}")
    public Optional<Order> getOrderById(@PathVariable Long orderId) {
        logger.info(Constants.LOG_RETRIEVING_ORDER_BY_ID, orderId);
        Optional<Order> order = orderService.getOrderById(orderId);
        if (order.isPresent()) {
            logger.info(Constants.LOG_ORDER_FOUND, order.get());
        } else {
            logger.warn(Constants.LOG_ORDER_NOT_FOUND + orderId);
        }
        return order;
    }

    @DeleteMapping("/{orderId}")
    public void deleteOrder(@PathVariable Long orderId) {
        logger.info(Constants.LOG_DELETING_ORDER, orderId);
        orderService.deleteOrder(orderId);
        logger.info(Constants.LOG_ORDER_DELETED_SUCCESS, orderId);
    }
}
