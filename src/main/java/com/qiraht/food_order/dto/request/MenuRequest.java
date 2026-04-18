package com.qiraht.food_order.dto.request;

import java.math.BigDecimal;

public record MenuRequest(
        String name,
        String description,
        BigDecimal price,
        Integer stock
) {
}
