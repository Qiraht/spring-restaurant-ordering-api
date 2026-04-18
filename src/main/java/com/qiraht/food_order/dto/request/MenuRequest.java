package com.qiraht.food_order.dto.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record MenuRequest(
        @NotBlank
        String name,

        String description,

        @NotNull
        @DecimalMin(value = "0.01")
        BigDecimal price,

        @NotNull
        @Min(1)
        Integer stock
) {
}
