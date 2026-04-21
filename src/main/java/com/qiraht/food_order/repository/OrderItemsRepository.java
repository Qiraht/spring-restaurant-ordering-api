package com.qiraht.food_order.repository;

import com.qiraht.food_order.entity.Order;
import com.qiraht.food_order.entity.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderItemsRepository extends JpaRepository<OrderItems, UUID> {
    List<OrderItems> findByOrder(Order order);
}
