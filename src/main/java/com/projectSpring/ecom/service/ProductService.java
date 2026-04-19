package com.projectSpring.ecom.service;

import com.projectSpring.ecom.entity.Product;
import com.projectSpring.ecom.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        // Update fields (add specific field updates as needed)
        return productRepository.save(existing);
    }

    public void softDelete(Long id) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        // Assuming your Product entity has an active/deleted flag
        // existing.setActive(false);
        productRepository.save(existing);
    }

    public Product getProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Page<Product> searchProducts(String keyword, Pageable pageable) {
        // Implement search logic, for example:
        // return productRepository.findByNameContainingIgnoreCase(keyword, pageable);
        return productRepository.findAll(pageable);
    }
}