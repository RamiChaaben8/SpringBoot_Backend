package com.projectSpring.ecom.service;

import com.projectSpring.ecom.repository.OrderRepository;
import com.projectSpring.ecom.repository.ProductRepository;
import com.projectSpring.ecom.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public Map<String, Object> getAdminDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalUsers", userRepository.count());
        stats.put("totalProducts", productRepository.count());
        stats.put("totalOrders", orderRepository.count());
        // Add more complex aggregations here
        return stats;
    }

    public Map<String, Object> getSellerDashboardStats(Long sellerId) {
        Map<String, Object> stats = new HashMap<>();
        // Query specific to seller products and orders
        return stats;
    }

    public Map<String, Object> getCustomerDashboardStats(Long customerId) {
        Map<String, Object> stats = new HashMap<>();
        // Query customer specific past orders, cart state, etc.
        return stats;
    }
}