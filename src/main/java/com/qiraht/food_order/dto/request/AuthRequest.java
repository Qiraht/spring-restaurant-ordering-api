package com.qiraht.food_order.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record AuthRequest(
        @Email
        @NotBlank
        String email,

        @NotBlank
        @Min(6)
        String password
) {
}
