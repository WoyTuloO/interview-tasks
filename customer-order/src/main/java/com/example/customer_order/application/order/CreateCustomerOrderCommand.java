package com.example.customer_order.application.order;

import java.util.UUID;

public record CreateCustomerOrderCommand(
        String customerId,
        String productSku,
        int quantity
) {}