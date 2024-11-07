package com.happiest.test;

import com.happiest.controller.OrderController;
import com.happiest.model.Order;
import com.happiest.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderService orderService;

    private Order order;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        order = new Order();
        order.setOrderId(1L);
        // set other properties...
    }

    @Test
    void createOrder_ShouldReturnCreatedOrder() {
        when(orderService.createOrder(any(Order.class))).thenReturn(order);

        Order createdOrder = orderController.createOrder(order);

        assertEquals(order, createdOrder);
    }

    @Test
    void getAllOrders_ShouldReturnListOfOrders() {
        List<Order> orders = new ArrayList<>();
        orders.add(order);

        when(orderService.getAllOrders()).thenReturn(orders);

        List<Order> result = orderController.getAllOrders();

        assertEquals(orders.size(), result.size());
    }

    @Test
    void getOrderById_ShouldReturnOrder_WhenFound() {
        when(orderService.getOrderById(1L)).thenReturn(Optional.of(order));

        Optional<Order> result = orderController.getOrderById(1L);

        assertTrue(result.isPresent());
        assertEquals(order, result.get());
    }

    @Test
    void getOrderById_ShouldReturnEmpty_WhenNotFound() {
        when(orderService.getOrderById(1L)).thenReturn(Optional.empty());

        Optional<Order> result = orderController.getOrderById(1L);

        assertFalse(result.isPresent());
    }

    @Test
    void deleteOrder_ShouldInvokeDeleteOrder() {
        doNothing().when(orderService).deleteOrder(1L);

        orderController.deleteOrder(1L);

        verify(orderService, times(1)).deleteOrder(1L);
    }
}
