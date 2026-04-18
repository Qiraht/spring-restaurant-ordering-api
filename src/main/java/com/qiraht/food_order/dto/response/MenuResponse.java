package com.qiraht.food_order.dto.response;

import java.math.BigDecimal;

public record MenuResponse(
        String id,
        String name,
        String description,
        BigDecimal price,
        Integer stock,
        boolean isAvailable
) {}
