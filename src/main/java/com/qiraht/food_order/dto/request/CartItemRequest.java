package com.qiraht.food_order.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CartItemRequest(
        @NotNull
        String menuId,

        @NotNull
        @Min(1)
        Integer quantity
) {
}
