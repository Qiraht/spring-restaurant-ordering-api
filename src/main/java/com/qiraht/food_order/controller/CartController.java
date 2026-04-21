package com.qiraht.food_order.controller;

import com.qiraht.food_order.dto.ApiResponse;
import com.qiraht.food_order.dto.request.CartItemRequest;
import com.qiraht.food_order.dto.response.CartItemResponse;
import com.qiraht.food_order.dto.response.CartResponse;
import com.qiraht.food_order.entity.Cart;
import com.qiraht.food_order.entity.CartItems;
import com.qiraht.food_order.helper.CartMapper;
import com.qiraht.food_order.service.CartService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@Validated
@Tag(name = "Cart", description = "Cart related endpoints")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse<Void>> postCart(@Valid @RequestBody CartItemRequest request) {
        cartService.addItemsToCart(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Items successfully added to cart"));
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse<CartResponse>> getCart() {
        Cart cart = cartService.getAuthenticatedUserCart();

        if (cart == null) {
            CartResponse empty = new CartResponse(
                    null,
                    null,
                    List.of()
            );

            return ResponseEntity.ok(ApiResponse.success("success", empty));
        }

        List<CartItems> items = cartService.getAuthenticatedUserCartItems();

        List<CartItemResponse> itemResponses = items.stream()
                .map(CartMapper::toCartItemsResponse)
                .toList();

        CartResponse data = CartMapper.toCartResponse(cart, itemResponses);

        return ResponseEntity.ok(ApiResponse.success("success", data));
    }

    @PutMapping("/{itemId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse<Void>> putItemCart(@PathVariable("itemId") String id, @Valid @RequestBody Integer quantity) {
        cartService.updateCartItemQuantity(id, quantity);

        return ResponseEntity.ok(ApiResponse.success("Items successfully edited"));
    }

    @DeleteMapping("/{itemId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> deleteItemCart(@Valid @PathVariable("itemId") String id) {
        cartService.deleteItemsFromCartById(id);

        return ResponseEntity.noContent().build();
    }
}
