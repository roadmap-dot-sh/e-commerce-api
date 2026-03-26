/*
 * CartItemRepository.java
 *
 * Copyright (c) 2025 Nguyen. All rights reserved.
 * This software is the confidential and proprietary information of Nguyen.
 */

package com.example.ecommerceapi.repository;

import com.example.ecommerceapi.model.CartItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * CartItemRepository.java
 *
 * @author Nguyen
 */
@Repository
public interface CartItemRepository extends CrudRepository<CartItem, Long> {
}
