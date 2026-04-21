package com.qiraht.food_order.service;

import com.qiraht.food_order.constant.OrderStatus;
import com.qiraht.food_order.entity.*;
import com.qiraht.food_order.exception.NotFoundException;
import com.qiraht.food_order.exception.ValidationException;
import com.qiraht.food_order.repository.OrderItemsRepository;
import com.qiraht.food_order.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemsRepository orderItemsRepository;
    private final CartService cartService;
    private final MenuService menuService;
    private final UserService userService;

    public OrderService(
            OrderRepository orderRepository,
            OrderItemsRepository orderItemsRepository,
            CartService cartService,
            MenuService menuService,
            UserService userService
    ) {
        this.orderRepository = orderRepository;
        this.orderItemsRepository = orderItemsRepository;
        this.cartService = cartService;
        this.menuService = menuService;
        this.userService = userService;
    }

    @Transactional
    public String checkoutAuthenticatedUserCart() {
        // TODO: check user cart
        Cart cart = cartService.getAuthenticatedUserCart();

        if (cart == null) {
            throw new ValidationException("User cart is empty");
        }

        // TODO: get all items from carts
        List<CartItems> cartItems = cartService.getAuthenticatedUserCartItems();

        // TODO: validate items stock and deletion
        for (CartItems c: cartItems) {
            menuService.checkoutMenuById(c.getMenu().getId().toString(), c.getQuantity());
        }

        // Create new Order
        Order order = Order.builder()
                .user(cart.getUser())
                .totalAmount(BigDecimal.ZERO)
                .build();

        orderRepository.save(order);

        BigDecimal total = BigDecimal.ZERO;

        // TODO: insert cart items to order items
        for (CartItems c: cartItems) {
            Menu menu = c.getMenu();
            BigDecimal unitPrice = menu.getPrice();
            BigDecimal lineTotal = unitPrice.multiply(BigDecimal.valueOf(c.getQuantity()));

            OrderItems oi = OrderItems.builder()
                    .order(order)
                    .menu(menu)
                    .quantity(c.getQuantity())
                    .priceAtSale(unitPrice)
                    .build();

            orderItemsRepository.save(oi);

            total = total.add(lineTotal);
        }

        order.setTotalAmount(total);
        orderRepository.save(order);

        return order.getId().toString();
    }

    public List<Order> getAuthenticatedUserOrders() {
        User user = userService.getAuthenticatedUserByEmail();
        return orderRepository.findByUserOrderByCreatedAtDesc(user);
    }

    public Order getAuthenticatedUserOrderById(String id) {
        User user = userService.getAuthenticatedUserByEmail();

        UUID orderId = UUID.fromString(id);

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found"));

        if (!order.getUser().getId().equals(user.getId())) {
            throw new ValidationException("Order does not belong to this user");
        }

        return order;
    }

    public List<OrderItems> getOrderItemsFromOrderId(String orderId) {
        Order order = getAuthenticatedUserOrderById(orderId);
        return orderItemsRepository.findByOrder(order);
    }

    public void modifyOrderStatusById(String id) {
        UUID orderId = UUID.fromString(id);

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found"));

        if (order.getStatus().equals(OrderStatus.COMPLETED)) {
            throw new ValidationException("Order already completed");
        }

        order.setStatus(OrderStatus.COMPLETED);

        orderRepository.save(order);
    }
}
