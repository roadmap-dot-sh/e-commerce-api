/*
 * ProductRequest.java
 *
 * Copyright (c) 2025 Nguyen. All rights reserved.
 * This software is the confidential and proprietary information of Nguyen.
 */

package com.example.ecommerceapi.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * ProductRequest.java
 *
 * @author Nguyen
 */
@Data
public class ProductRequest {
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stockQuantity;
    private String imageUrl;
    private String category;
    private boolean active = true;
}
