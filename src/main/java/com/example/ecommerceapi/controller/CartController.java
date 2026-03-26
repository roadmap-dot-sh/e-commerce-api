/*
 * CartController.java
 *
 * Copyright (c) 2025 Nguyen. All rights reserved.
 * This software is the confidential and proprietary information of Nguyen.
 */

package com.example.ecommerceapi.controller;

import com.example.ecommerceapi.model.Cart;
import com.example.ecommerceapi.model.User;
import com.example.ecommerceapi.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * CartController.java
 *
 * @author Nguyen
 */
@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<Cart> getCart(@AuthenticationPrincipal UserDetails userDetails) {
        User user = (User) userDetails;
        return ResponseEntity.ok(cartService.getCartByUser(user));
    }

    @PostMapping("/add/{productId}")
    public ResponseEntity<Cart> addToCart(@AuthenticationPrincipal UserDetails userDetails,
                                          @PathVariable Long productId,
                                          @RequestParam int quantity) {
        User user = (User) userDetails;
        return ResponseEntity.ok(cartService.addToCart(user, productId, quantity));
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<Cart> removeFromCart(@AuthenticationPrincipal UserDetails userDetails,
                                               @PathVariable Long productId) {
        User user = (User) userDetails;
        return ResponseEntity.ok(cartService.removeFromCart(user, productId));
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<Cart> updateCartItem(@AuthenticationPrincipal UserDetails userDetails,
                                               @PathVariable Long productId,
                                               @RequestParam int quantity) {
        User user = (User) userDetails;
        return ResponseEntity.ok(cartService.updateCartItemQuantity(user, productId, quantity));
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearCart(@AuthenticationPrincipal UserDetails userDetails) {
        User user = (User) userDetails;
        cartService.clearCart(user);
        return ResponseEntity.ok().build();
    }
}
