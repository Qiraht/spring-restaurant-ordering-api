package com.qiraht.food_order.dto.response;

import java.util.List;

public record OrderCompleteResponse(
        OrderResponse orderResponse,
        List<OrderItemsResponse> orderItemsResponses
) {
}
