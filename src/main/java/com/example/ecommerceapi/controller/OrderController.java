/*
 * OrderController.java
 *
 * Copyright (c) 2025 Nguyen. All rights reserved.
 * This software is the confidential and proprietary information of Nguyen.
 */

package com.example.ecommerceapi.controller;

import com.example.ecommerceapi.dto.CheckoutRequest;
import com.example.ecommerceapi.model.Order;
import com.example.ecommerceapi.model.User;
import com.example.ecommerceapi.service.OrderService;
import com.example.ecommerceapi.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * OrderController.java
 *
 * @author Nguyen
 */
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;
    private final PaymentService paymentService;

    public OrderController(OrderService orderService, PaymentService paymentService) {
        this.orderService = orderService;
        this.paymentService = paymentService;
    }

    @PostMapping("/checkout")
    public ResponseEntity<Order> checkout(@AuthenticationPrincipal UserDetails userDetails,
                                          @RequestBody CheckoutRequest request) {
        User user = (User) userDetails;
        Order order = orderService.createOrderFromCart(user, request.getShippingAddress());

        // Create Stripe payment intent
        paymentService.createPaymentIntent(order);

        return ResponseEntity.ok(order);
    }

    @GetMapping
    public ResponseEntity<List<Order>> getUserOrders(@AuthenticationPrincipal UserDetails userDetails) {
        User user = (User) userDetails;
        return ResponseEntity.ok(orderService.getUserOrders(user));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@AuthenticationPrincipal UserDetails userDetails,
                                              @PathVariable Long orderId) {
        User user = (User) userDetails;
        return ResponseEntity.ok(orderService.getOrderById(orderId, user));
    }

    @PostMapping("/{orderId}/payment-success")
    public ResponseEntity<Void> handlePaymentSuccess(@PathVariable Long orderId,
                                                     @RequestParam String paymentIntentId) {
        paymentService.handlePaymentSuccess(orderId, paymentIntentId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{orderId}/payment-failed")
    public ResponseEntity<Void> handlePaymentFailure(@PathVariable Long orderId) {
        paymentService.handlePaymentFailure(orderId);
        return ResponseEntity.ok().build();
    }
}
