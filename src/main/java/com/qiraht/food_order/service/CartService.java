package com.qiraht.food_order.service;

import com.qiraht.food_order.constant.CartStatus;
import com.qiraht.food_order.dto.request.CartItemRequest;
import com.qiraht.food_order.entity.Cart;
import com.qiraht.food_order.entity.CartItems;
import com.qiraht.food_order.entity.Menu;
import com.qiraht.food_order.entity.User;
import com.qiraht.food_order.exception.NotFoundException;
import com.qiraht.food_order.exception.ValidationException;
import com.qiraht.food_order.repository.CartItemsRepository;
import com.qiraht.food_order.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemsRepository cartItemsRepository;
    private final UserService userService;
    private final MenuService menuService;

    public CartService(CartRepository cartRepository, CartItemsRepository cartItemsRepository, UserService userService, MenuService menuService) {
        this.cartRepository = cartRepository;
        this.cartItemsRepository = cartItemsRepository;
        this.userService = userService;
        this.menuService = menuService;
    }

    public void addItemsToCart(CartItemRequest request) {
        // Get User info
        User user = userService.getAuthenticatedUserByEmail();

        // find or create active cart
        Cart cart = cartRepository.findCartByUserAndStatus(user, CartStatus.ACTIVE)
                .orElseGet(() -> createNewCartForUser(user));

        // get menu by id
        Menu menu = menuService.getMenuById(request.menuId());

        if (request.quantity() > menu.getStock()) {
            throw new ValidationException("Item stock is not enough");
        }

        if (menu.getDeletedAt() != null) {
            throw new ValidationException("Item is not available");
        }

        // find existing item in cart
        CartItems cartItems = cartItemsRepository.findByCartAndMenu(cart, menu).orElse(null);

        if (cartItems == null) {
            // Create new cart item
            CartItems newItem = new CartItems();
            newItem.setCart(cart);
            newItem.setMenu(menu);
            newItem.setQuantity(request.quantity());

            cartItemsRepository.save(newItem);
        } else {
            // update quantity (addition)
            cartItems.setQuantity(cartItems.getQuantity() + request.quantity());

            cartItemsRepository.save(cartItems);
        }
    }

    public Cart createNewCartForUser(User user) {
        Cart cart = Cart.builder()
                .user(user)
                .status(CartStatus.ACTIVE)
                .build();

        cartRepository.save(cart);

        return cart;
    }

    public Cart getAuthenticatedUserCart() {
        User user = userService.getAuthenticatedUserByEmail();

        return cartRepository.findCartByUserAndStatus(user, CartStatus.ACTIVE)
                .orElse(null);
    }

    public List<CartItems> getAuthenticatedUserCartItems() {
        User user = userService.getAuthenticatedUserByEmail();
        Cart cart = cartRepository.findCartByUserAndStatus(user, CartStatus.ACTIVE).orElse(null);

        if (cart == null) {
            return List.of();
        }

        return cartItemsRepository.findByCart(cart);
    }

    public void updateCartItemQuantity(String id, Integer quantity) {
        UUID itemId = UUID.fromString(id);

        User user = userService.getAuthenticatedUserByEmail();

        // check items in cart
        CartItems items = cartItemsRepository.findById(itemId).orElseThrow(() -> new NotFoundException("Items in cart not found"));

        String menuId = items.getMenu().getId().toString();

        Menu menu = menuService.getMenuById(menuId);

        // stock
        if (menu.getStock() < quantity) {
            throw new ValidationException("Item stock is not enough");
        }

        // menu deleted
        if (menu.getDeletedAt() != null) {
            throw new ValidationException("Item not available");
        }

        // check ownership
        if (!items.getCart().getUser().getId().equals(user.getId())) {
            throw new ValidationException("Cart item does not belong to this user");
        }

        // save new item quantity cart
        items.setQuantity(quantity);

        cartItemsRepository.save(items);
    }

    public void deleteItemsFromCartById(String id) {
        UUID itemId = UUID.fromString(id);

        // get user info
        User user = userService.getAuthenticatedUserByEmail();

        // get cart items
        CartItems items = cartItemsRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Cart item not found"));

        // check cart items ownership
        // need to add 'ADMIN' deletion support
        if (!items.getCart().getUser().getId().equals(user.getId())) {
            throw new ValidationException("Cart item does not belong to this user");
        }

        cartItemsRepository.deleteById(itemId);
    }
}
