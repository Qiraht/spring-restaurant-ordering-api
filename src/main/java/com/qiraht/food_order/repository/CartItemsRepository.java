package com.qiraht.food_order.repository;

import com.qiraht.food_order.entity.Cart;
import com.qiraht.food_order.entity.CartItems;
import com.qiraht.food_order.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartItemsRepository extends JpaRepository<CartItems, UUID> {
    Optional<CartItems> findByCartAndMenu(Cart cart, Menu menu);

    List<CartItems> findByCart(Cart cart);
}
