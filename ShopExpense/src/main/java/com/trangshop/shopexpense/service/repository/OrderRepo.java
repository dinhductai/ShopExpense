package com.trangshop.shopexpense.service.repository;

import com.trangshop.shopexpense.model.Order;
import com.trangshop.shopexpense.model.OrderDetail;

import java.util.List;

public interface OrderRepo {
    List<Order> findAll(int page, int pageSize, String status, String startDate, String endDate);
    int getTotalRecords(String status, String startDate, String endDate);
    List<OrderDetail> getOrderDetails(int orderId);
    void updateOrderStatus(int orderId, String status);
    Order findById(int orderId);
}
