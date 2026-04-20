package com.qiraht.food_order.helper;

import com.qiraht.food_order.dto.response.CartItemResponse;
import com.qiraht.food_order.dto.response.CartResponse;
import com.qiraht.food_order.entity.Cart;
import com.qiraht.food_order.entity.CartItems;

import java.util.List;

public class CartMapper {
    public static CartResponse toCartResponse(Cart cart, List<CartItemResponse> items) {
        return new CartResponse(
                cart.getId().toString(),
                cart.getStatus(),
                items
        );
    }

    public static CartItemResponse toCartItemsResponse(CartItems cartItems) {
        return new CartItemResponse(
                cartItems.getId().toString(),
                MenuMapper.toResponse(cartItems.getMenu()),
                cartItems.getQuantity()
        );
    }
}
