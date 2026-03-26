/*
 * PaymentService.java
 *
 * Copyright (c) 2025 Nguyen. All rights reserved.
 * This software is the confidential and proprietary information of Nguyen.
 */

package com.example.ecommerceapi.service;

import com.example.ecommerceapi.enums.OrderStatus;
import com.example.ecommerceapi.model.Order;
import com.example.ecommerceapi.repository.OrderRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * PaymentService.java
 *
 * @author Nguyen
 */
@Service
@RequiredArgsConstructor
public class PaymentService {
    private final OrderRepository orderRepository;
    private final OrderService orderService;

    @Transactional
    public void createPaymentIntent(Order order) {
        try {
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(order.getTotalAmount().multiply(BigDecimal.valueOf(100)).longValue())
                    .setCurrency("usd")
                    .setDescription("Order #" + order.getId())
                    .putMetadata("orderId", order.getId().toString())
                    .build();

            PaymentIntent paymentIntent = PaymentIntent.create(params);

            order.setStripePaymentIntentId(paymentIntent.getId());
            orderRepository.save(order);

            // Here you would return the client secret to the frontend
            // For now, we'll just log it
            System.out.println("Payment Intent created: " + paymentIntent.getClientSecret());

        } catch (StripeException e) {
            throw new RuntimeException("Failed to create payment intent: " + e.getMessage());
        }
    }

    @Transactional
    public void handlePaymentSuccess(Long orderId, String paymentIntentId) {
        Order order = orderService.getOrderById(orderId, null);
        if (order.getStripePaymentIntentId().equals(paymentIntentId)) {
            orderService.updatePaymentStatus(orderId, "completed", paymentIntentId);
            orderService.updateOrderStatus(orderId, OrderStatus.CONFIRMED);
        }
    }

    @Transactional
    public void handlePaymentFailure(Long orderId) {
        orderService.updatePaymentStatus(orderId, "failed", null);
        orderService.updateOrderStatus(orderId, OrderStatus.CANCELLED);
    }
}
