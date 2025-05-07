package com.trangshop.shopexpense.service;

import com.trangshop.shopexpense.model.Order;
import com.trangshop.shopexpense.model.OrderDetail;

import java.util.List;

public interface OrderService {
    List<Order> getOrders(int page, int pageSize, String status, String startDate, String endDate);
    int getTotalPages(int pageSize, String status, String startDate, String endDate);
    List<OrderDetail> getOrderDetails(int orderId);
    void updateOrderStatus(int orderId, String status);

    Order getOrderById(int orderId);
}
