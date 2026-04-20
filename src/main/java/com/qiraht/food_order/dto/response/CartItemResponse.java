package com.qiraht.food_order.dto.response;

public record CartItemResponse(String id, MenuResponse menu, Integer quantity) {
}
