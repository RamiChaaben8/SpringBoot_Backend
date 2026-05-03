package com.projectSpring.ecom.service;

import com.projectSpring.ecom.entity.Order;
import com.projectSpring.ecom.entity.User;
import com.projectSpring.ecom.repository.OrderRepository;
import com.projectSpring.ecom.repository.ProductRepository;
import com.projectSpring.ecom.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public Map<String, Object> getAdminDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalUsers", userRepository.count());
        stats.put("totalSellers", userRepository.findAll().stream().filter(u -> u.getRole() == User.Role.SELLER).count());
        stats.put("totalOrders", orderRepository.count());
        
        double totalRevenue = orderRepository.findAll().stream()
                .filter(o -> o.getStatus() != Order.OrderStatus.CANCELLED)
                .mapToDouble(Order::getTotalAmount)
                .sum();
        stats.put("totalRevenue", totalRevenue);
        
        return stats;
    }

    public Map<String, Object> getSellerDashboardStats(Long sellerId) {
        Map<String, Object> stats = new HashMap<>();
        List<Order> sellerOrders = orderRepository.findOrdersBySellerId(sellerId);
        
        stats.put("myProductsCount", productRepository.findAll().stream().filter(p -> p.getSeller().getId().equals(sellerId)).count());
        stats.put("pendingOrdersCount", sellerOrders.stream().filter(o -> o.getStatus() == Order.OrderStatus.PENDING).count());
        
        double totalSales = sellerOrders.stream()
                .filter(o -> o.getStatus() != Order.OrderStatus.CANCELLED)
                .flatMap(o -> o.getItems().stream())
                .filter(i -> i.getProduct().getSeller().getId().equals(sellerId))
                .mapToDouble(i -> i.getUnitPrice() * i.getQuantity())
                .sum();
        stats.put("totalSales", totalSales);
        
        return stats;
    }

    public Map<String, Object> getCustomerDashboardStats(Long customerId) {
        Map<String, Object> stats = new HashMap<>();
        User customer = userRepository.findById(customerId).orElseThrow();
        List<Order> orders = orderRepository.findByCustomer(customer);
        
        stats.put("myOrdersCount", orders.size());
        stats.put("cartItemsCount", 0); // Placeholder, will be updated by cart service or frontend
        
        double totalSpent = orders.stream()
                .filter(o -> o.getStatus() != Order.OrderStatus.CANCELLED)
                .mapToDouble(Order::getTotalAmount)
                .sum();
        stats.put("totalSpent", totalSpent);
        
        return stats;
    }
}