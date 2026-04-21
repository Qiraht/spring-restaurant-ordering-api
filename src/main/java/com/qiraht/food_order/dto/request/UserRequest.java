package com.qiraht.food_order.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequest(
        @NotBlank
        @Size(min = 2, max = 16)
        String firstName,

        @NotBlank
        @Size(min = 2, max = 16)
        String lastName,

        @NotBlank
        @Email
        String email,

        @NotBlank
        @Size(min = 6)
        String password
) {
}
