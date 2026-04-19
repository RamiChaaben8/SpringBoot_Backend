package com.projectSpring.ecom.controller;

import com.projectSpring.ecom.entity.Cart;
import com.projectSpring.ecom.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping("/{userId}")
    public ResponseEntity<Cart> getCart(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.getCartForUser(userId));
    }

    @PostMapping("/{userId}/items")
    public ResponseEntity<Cart> addItem(@PathVariable Long userId, @RequestBody AddItemRequest request) {
        return ResponseEntity.ok(cartService.addItemToCart(userId, request.productId(), request.quantity()));
    }

    @DeleteMapping("/{userId}/items/{cartItemId}")
    public ResponseEntity<Cart> removeItem(@PathVariable Long userId, @PathVariable Long cartItemId) {
        return ResponseEntity.ok(cartService.removeItemFromCart(userId, cartItemId));
    }

    @PostMapping("/{userId}/coupon")
    public ResponseEntity<Cart> applyCoupon(@PathVariable Long userId, @RequestParam String couponCode) {
        return ResponseEntity.ok(cartService.applyCoupon(userId, couponCode));
    }

    public record AddItemRequest(Long productId, int quantity) {}
}