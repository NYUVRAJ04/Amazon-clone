package com.happiest.service;

import com.happiest.constants.Constants;
import com.happiest.exception.OrderNotFoundException;
import com.happiest.exception.OrderProcessingException;
import com.happiest.model.Buyers;
import com.happiest.model.Order;
import com.happiest.repository.BuyerRepo;
import com.happiest.repository.OrderRepository;
import com.happiest.utility.MailBody;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private static final Logger logger = LogManager.getLogger(OrderService.class);



    @Autowired
    private EmailService emailService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private BuyerRepo buyerRepo;

    public Order createOrder(Order order) {
        logger.info(Constants.LOG_CREATING_ORDER, order);
        try {
            Order savedOrder = orderRepository.save(order);
            logger.info(Constants.LOG_ORDER_CREATED_SUCCESS, savedOrder.getOrderId());

            Optional<Buyers> buyersOptional = buyerRepo.findById(order.getBuyerId());
            if (buyersOptional.isPresent()) {
                String buyerEmail = buyersOptional.get().getUser().getEmail();

                // Ensure that the email is not null or empty
                if (buyerEmail != null && !buyerEmail.isEmpty()) {
                    // Prepare and send the email
                    String subject = "Order Confirmation - Order #" + order.getOrderId();
                    String text = "Dear Customer,\n\n" +
                            "Thank you for your order!\n" +
                            "Order Details:\n" +
                            "Order ID: " + order.getOrderId() + "\n" +
                            "Total Amount: $" + order.getTotalPrice() + "\n\n" +
                            "We appreciate your business and hope you enjoy your purchase.\n\n" +
                            "Best regards,\nThe Happiest Team";

                    MailBody mailBody = new MailBody(buyerEmail, subject, text);
                    emailService.sendSimpleMessage(mailBody);
                } else {
                    logger.warn("Buyer email is not available for order ID: {}", order.getOrderId());
                }
            } else {
                logger.warn("Buyer not found for buyer ID: {}", order.getBuyerId());
                // Handle this case, e.g., by throwing an exception or logging an error
            }
            return savedOrder;
        } catch (Exception e) {
            logger.error("Error creating order: {}", order, e);
            throw new OrderProcessingException("Could not create order. Please try again.", e);
        }
    }

    public List<Order> getAllOrders() {
        logger.info(Constants.LOG_RETRIEVING_ALL_ORDERS);
        try {
            List<Order> orders = orderRepository.findAll();
            logger.info(Constants.LOG_TOTAL_ORDERS_FOUND, orders.size());
            return orders;
        } catch (Exception e) {
            logger.error("Error retrieving all orders", e);
            throw new OrderProcessingException("Could not retrieve orders. Please try again.", e);
        }
    }

    public Optional<Order> getOrderById(Long orderId) {
        logger.info(Constants.LOG_RETRIEVING_ORDER_BY_ID, orderId);

        // Retrieve the order from the repository
        Optional<Order> order = orderRepository.findById(orderId);

        // Log whether the order was found or not
        if (order.isPresent()) {
            logger.info(Constants.LOG_ORDER_FOUND, orderId);
        } else {
            logger.warn(Constants.LOG_ORDER_NOT_FOUND, orderId);
        }

        // Return the Optional
        return order;
    }


    public void deleteOrder(Long orderId) {
        logger.info(Constants.LOG_DELETING_ORDER, orderId);
        try {
            if (!orderRepository.existsById(orderId)) {
                logger.warn(Constants.LOG_ORDER_NOT_FOUND, orderId);
                throw new OrderNotFoundException("Order not found with ID: " + orderId);
            }
            orderRepository.deleteById(orderId);
            logger.info(Constants.LOG_ORDER_DELETED_SUCCESS, orderId);
        } catch (OrderNotFoundException e) {
            logger.warn(e.getMessage());
            throw e; // rethrow to handle at a higher level
        } catch (Exception e) {
            logger.error("Error deleting order with ID: {}", orderId, e);
            throw new OrderProcessingException("Could not delete order. Please try again.", e);
        }
    }
}
