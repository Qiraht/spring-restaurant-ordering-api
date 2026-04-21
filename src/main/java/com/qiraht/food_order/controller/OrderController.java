package com.qiraht.food_order.controller;

import com.qiraht.food_order.dto.ApiResponse;
import com.qiraht.food_order.dto.response.OrderCompleteResponse;
import com.qiraht.food_order.dto.response.OrderResponse;
import com.qiraht.food_order.entity.Order;
import com.qiraht.food_order.entity.OrderItems;
import com.qiraht.food_order.helper.OrderMapper;
import com.qiraht.food_order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@Validated
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/checkout")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse<String>> postOrderCheckout() {
        String data = orderService.checkoutAuthenticatedUserCart();

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.success("Order created successfully", data));
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getOrders() {
        List<Order> orders = orderService.getAuthenticatedUserOrders();

        List<OrderResponse> data = orders.stream()
                .map(OrderMapper::toOrderResponse).toList();

        return ResponseEntity.ok(ApiResponse.success("success", data));
    }

    @GetMapping("/{orderId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse<OrderCompleteResponse>> getOrderById(@PathVariable("orderId") String id) {
        Order order = orderService.getAuthenticatedUserOrderById(id);

        List<OrderItems> orderItems = orderService.getOrderItemsFromOrderId(order.getId().toString());

        OrderCompleteResponse data = new OrderCompleteResponse(
                OrderMapper.toOrderResponse(order),
                orderItems.stream().map(OrderMapper::toOrderItemsResponse).toList());

        return ResponseEntity.ok(ApiResponse.success("success", data));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> pathOrderById(@PathVariable String id) {
        // TODO: used for changing order status
        orderService.modifyOrderStatusById(id);

        return ResponseEntity.ok(ApiResponse.success("Order successfully processed"));
    }
}
