package com.qiraht.food_order.dto.response;

import java.math.BigDecimal;

public record OrderItemsResponse(
        String id,
        MenuResponse menu,
        Integer quantity,
        BigDecimal priceAtSale
) {
}
