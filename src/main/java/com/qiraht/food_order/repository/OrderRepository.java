package com.qiraht.food_order.repository;

import com.qiraht.food_order.entity.Order;
import com.qiraht.food_order.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findByUserOrderByCreatedAtDesc(User user);
}
