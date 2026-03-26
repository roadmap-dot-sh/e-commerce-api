/*
 * CheckoutRequest.java
 *
 * Copyright (c) 2025 Nguyen. All rights reserved.
 * This software is the confidential and proprietary information of Nguyen.
 */

package com.example.ecommerceapi.dto;

import lombok.Data;

/**
 * CheckoutRequest.java
 *
 * @author Nguyen
 */
@Data
public class CheckoutRequest {
    private String shippingAddress;
    private String paymentMethod;
}
