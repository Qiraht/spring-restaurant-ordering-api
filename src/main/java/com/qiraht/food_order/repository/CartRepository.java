package com.qiraht.food_order.repository;

import com.qiraht.food_order.constant.CartStatus;
import com.qiraht.food_order.entity.Cart;
import com.qiraht.food_order.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<Cart, UUID> {
    Optional<Cart> findCartByUserAndStatus(User user, CartStatus status);
}
