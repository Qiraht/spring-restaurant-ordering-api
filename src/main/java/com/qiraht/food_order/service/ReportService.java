package com.qiraht.food_order.service;

import com.qiraht.food_order.constant.OrderStatus;
import com.qiraht.food_order.dto.response.SalesReportResponse;
import com.qiraht.food_order.entity.Order;
import com.qiraht.food_order.entity.OrderItems;
import com.qiraht.food_order.repository.OrderItemsRepository;
import com.qiraht.food_order.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ReportService {
    private final OrderRepository orderRepository;
    private final OrderItemsRepository orderItemsRepository;

    public ReportService(OrderRepository orderRepository,
                         OrderItemsRepository orderItemsRepository) {
        this.orderRepository = orderRepository;
        this.orderItemsRepository = orderItemsRepository;
    }

    public SalesReportResponse getSalesReport() {
        // 1. get all COMPLETED orders
        List<Order> completedOrders = orderRepository.findByStatus(OrderStatus.COMPLETED);

        if (completedOrders.isEmpty()) {
            return new SalesReportResponse(
                    BigDecimal.ZERO,
                    0L,
                    0L
            );
        }

        // 2. total revenue
        BigDecimal totalRevenue = completedOrders.stream()
                .map(Order::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        long totalOrders = completedOrders.size();

        // 3. total items sold (sum of all order item quantities)
        List<OrderItems> allItems = orderItemsRepository.findByOrderIn(completedOrders);

        long totalItemsSold = allItems.stream()
                .mapToLong(oi -> oi.getQuantity().longValue())
                .sum();

        return new SalesReportResponse(
                totalRevenue,
                totalOrders,
                totalItemsSold
        );
    }
}
