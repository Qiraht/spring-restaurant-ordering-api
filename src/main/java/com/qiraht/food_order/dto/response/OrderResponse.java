package com.qiraht.food_order.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderResponse(
        String id,
        BigDecimal totalAmount,
        LocalDateTime createdAt
) {
}
