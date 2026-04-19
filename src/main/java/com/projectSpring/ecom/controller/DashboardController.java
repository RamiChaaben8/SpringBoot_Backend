package com.projectSpring.ecom.controller;

import com.projectSpring.ecom.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> getAdminStats() {
        return ResponseEntity.ok(dashboardService.getAdminDashboardStats());
    }

    @GetMapping("/seller/{sellerId}")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<Map<String, Object>> getSellerStats(@PathVariable Long sellerId) {
        return ResponseEntity.ok(dashboardService.getSellerDashboardStats(sellerId));
    }

    @GetMapping("/customer/{customerId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Map<String, Object>> getCustomerStats(@PathVariable Long customerId) {
        return ResponseEntity.ok(dashboardService.getCustomerDashboardStats(customerId));
    }
}