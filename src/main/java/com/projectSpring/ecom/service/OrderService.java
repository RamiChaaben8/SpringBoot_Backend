package com.projectSpring.ecom.service;

import com.projectSpring.ecom.entity.*;
import com.projectSpring.ecom.repository.CartRepository;
import com.projectSpring.ecom.repository.OrderRepository;
import com.projectSpring.ecom.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    public List<Order> getOrdersBySeller(Long sellerId) {
        return orderRepository.findOrdersBySellerId(sellerId);
    }

    @Transactional
    public Order createOrderFromCart(Long userId) {
        User customer = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Cart cart = cartService.getCartForUser(userId);
        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        double subTotal = cart.getItems().stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();

        Order order = Order.builder()
                .customer(customer)
                .orderNumber(generateOrderNumber())
                .status(Order.OrderStatus.PENDING)
                .subTotal(subTotal)
                .shippingCost(0.0)
                .totalAmount(subTotal)
                .orderDate(System.currentTimeMillis())
                .items(new ArrayList<>())
                .build();

        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .product(cartItem.getProduct())
                    .variant(cartItem.getVariant())
                    .quantity(cartItem.getQuantity())
                    .unitPrice(cartItem.getProduct().getPrice())
                    .build();
            order.getItems().add(orderItem);
            
            // Deduct stock
            Product product = cartItem.getProduct();
            product.setStock(product.getStock() - cartItem.getQuantity());
        }

        Order savedOrder = orderRepository.save(order);
        
        // Clear cart
        cart.getItems().clear();
        cartRepository.save(cart);

        return savedOrder;
    }

    public Order updateOrderStatus(Long orderId, String newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(Order.OrderStatus.valueOf(newStatus.toUpperCase()));
        return orderRepository.save(order);
    }

    public Order cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(Order.OrderStatus.CANCELLED);
        return orderRepository.save(order);
    }

    private String generateOrderNumber() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        String randomStr = UUID.randomUUID().toString().substring(0, 5).toUpperCase();
        return "ORD-" + year + "-" + randomStr;
    }
}