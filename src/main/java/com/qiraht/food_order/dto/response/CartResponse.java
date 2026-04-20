package com.qiraht.food_order.dto.response;

import com.qiraht.food_order.constant.CartStatus;

import java.util.List;

public record CartResponse(
        String id,
        CartStatus status,
        List<CartItemResponse> items) {
}
