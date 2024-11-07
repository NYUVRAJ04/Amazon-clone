package com.happiest.test;

import com.happiest.exception.OrderNotFoundException;
import com.happiest.exception.OrderProcessingException;
import com.happiest.model.Order;
import com.happiest.repository.OrderRepository;
import com.happiest.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    private Order order;

    @BeforeEach
    void setUp() {
        order = new Order();
        order.setOrderId(1L);
        // Set other necessary fields for the order if required
    }

    @Test
    void testCreateOrder_Success() {
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order createdOrder = orderService.createOrder(order);

        assertNotNull(createdOrder);
        assertEquals(order.getOrderId(), createdOrder.getOrderId());
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testCreateOrder_Exception() {
        when(orderRepository.save(any(Order.class))).thenThrow(new RuntimeException("DB error"));

        Exception exception = assertThrows(OrderProcessingException.class, () -> {
            orderService.createOrder(order);
        });

        assertTrue(exception.getMessage().contains("Could not create order. Please try again."));
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testGetAllOrders_Success() {
        when(orderRepository.findAll()).thenReturn(Arrays.asList(order));

        var orders = orderService.getAllOrders();

        assertNotNull(orders);
        assertEquals(1, orders.size());
        assertEquals(order.getOrderId(), orders.get(0).getOrderId());
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void testGetAllOrders_Exception() {
        when(orderRepository.findAll()).thenThrow(new RuntimeException("DB error"));

        Exception exception = assertThrows(OrderProcessingException.class, () -> {
            orderService.getAllOrders();
        });

        assertTrue(exception.getMessage().contains("Could not retrieve orders. Please try again."));
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void testGetOrderById_Success() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        Optional<Order> retrievedOrder = orderService.getOrderById(1L);

        assertTrue(retrievedOrder.isPresent());
        assertEquals(order.getOrderId(), retrievedOrder.get().getOrderId());
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    void testGetOrderById_NotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Order> retrievedOrder = orderService.getOrderById(1L);

        assertFalse(retrievedOrder.isPresent());
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteOrder_Success() {
        when(orderRepository.existsById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> orderService.deleteOrder(1L));
        verify(orderRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteOrder_NotFound() {
        when(orderRepository.existsById(1L)).thenReturn(false);

        Exception exception = assertThrows(OrderNotFoundException.class, () -> {
            orderService.deleteOrder(1L);
        });

        assertTrue(exception.getMessage().contains("Order not found with ID: 1"));
        verify(orderRepository, never()).deleteById(1L);
    }

    @Test
    void testDeleteOrder_Exception() {
        when(orderRepository.existsById(1L)).thenReturn(true);
        doThrow(new RuntimeException("DB error")).when(orderRepository).deleteById(1L);

        Exception exception = assertThrows(OrderProcessingException.class, () -> {
            orderService.deleteOrder(1L);
        });

        assertTrue(exception.getMessage().contains("Could not delete order. Please try again."));
        verify(orderRepository, times(1)).deleteById(1L);
    }
}
