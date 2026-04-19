package com.projectSpring.ecom.service;

import com.projectSpring.ecom.entity.Cart;
import com.projectSpring.ecom.entity.CartItem;
import com.projectSpring.ecom.repository.CartRepository;
import com.projectSpring.ecom.repository.CartItemRepository;
import com.projectSpring.ecom.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    public Cart getCartForUser(Long userId) {
        // Fetch or create cart for user
        return new Cart();
    }

    public Cart addItemToCart(Long userId, Long productId, int quantity) {
        // Check stock via ProductService or repo
        // Add or update CartItem
        return new Cart();
    }

    public Cart removeItemFromCart(Long userId, Long cartItemId) {
        // Remove item from cart
        return new Cart();
    }

    public Cart applyCoupon(Long userId, String couponCode) {
        // Validate coupon code and date
        // Update cart total
        return new Cart();
    }
}