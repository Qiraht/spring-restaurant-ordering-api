package com.qiraht.food_order.dto.response;

import java.math.BigDecimal;

public record SalesReportResponse(
        BigDecimal totalRevenue,
        Long totalOrders,
        Long totalItemsSold
) {
}
