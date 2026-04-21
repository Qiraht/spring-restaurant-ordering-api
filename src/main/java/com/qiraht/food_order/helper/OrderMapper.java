package com.qiraht.food_order.helper;

import com.qiraht.food_order.dto.response.OrderItemsResponse;
import com.qiraht.food_order.dto.response.OrderResponse;
import com.qiraht.food_order.entity.Order;
import com.qiraht.food_order.entity.OrderItems;

public class OrderMapper {
    public static OrderResponse toOrderResponse(Order order) {
        return new OrderResponse(
                order.getId().toString(),
                order.getTotalAmount(),
                order.getCreatedAt()
        );
    }

    public static OrderItemsResponse toOrderItemsResponse(OrderItems orderItems) {
        return new OrderItemsResponse(
                orderItems.getId().toString(),
                MenuMapper.toResponse(orderItems.getMenu()),
                orderItems.getQuantity(),
                orderItems.getPriceAtSale()
        );
    }
}
