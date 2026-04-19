package com.projectSpring.ecom.service;

import com.projectSpring.ecom.entity.Order;
import com.projectSpring.ecom.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartService cartService;

    public Order createOrderFromCart(Long userId) {
        // 1. Fetch Cart
        // 2. Final stock check for all items
        // 3. Create Order and OrderItems
        // 4. Generate Order Number
        Order order = new Order();
        // order.setOrderNumber(generateOrderNumber());
        // 5. Clear Cart
        // 6. Save Order
        return orderRepository.save(order);
    }

    public Order updateOrderStatus(Long orderId, String newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        // order.setStatus(newStatus);
        return orderRepository.save(order);
    }

    public Order cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        // 1. Check if order can be cancelled (e.g., up to SHIPPING state)
        // 2. order.setStatus("CANCELLED");
        // 3. Return stock
        return orderRepository.save(order);
    }

    private String generateOrderNumber() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        String randomStr = UUID.randomUUID().toString().substring(0, 5).toUpperCase();
        return "ORD-" + year + "-" + randomStr;
    }
}