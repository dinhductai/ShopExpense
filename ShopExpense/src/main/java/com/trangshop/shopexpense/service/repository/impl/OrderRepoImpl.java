package com.trangshop.shopexpense.service.repository.impl;

import com.trangshop.shopexpense.model.Order;
import com.trangshop.shopexpense.model.OrderDetail;
import com.trangshop.shopexpense.service.DatabaseConnectService;
import com.trangshop.shopexpense.service.impl.DatabaseConnectServiceImpl;
import com.trangshop.shopexpense.service.repository.OrderRepo;
import com.trangshop.shopexpense.service.repository.query.OrderQueries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderRepoImpl implements OrderRepo {
    private DatabaseConnectService databaseConnectService;
    private OrderQueries orderQueries;

    public OrderRepoImpl() {
        this.databaseConnectService = new DatabaseConnectServiceImpl();
        this.orderQueries = new OrderQueries();
    }

    public List<Order> findAll(int page, int pageSize, String status, String startDate, String endDate) {
        List<Order> orders = new ArrayList<>();
        try (Connection conn = databaseConnectService.getConnection()) {
            int offset = (page - 1) * pageSize;

            //nối chuỗi query dựa vào status
            String sql = "SELECT * FROM orders WHERE 1=1 ";
            if (status != null && !status.isEmpty()) {
                sql += " AND status = ?";
            }
            if (startDate != null && endDate != null) {
                sql += " AND order_date BETWEEN ? AND ?";
            }
            sql += " LIMIT ? OFFSET ?";

            PreparedStatement stmt = conn.prepareStatement(sql);

            int paramIndex = 1;
            if (status != null && !status.isEmpty()) {
                stmt.setString(paramIndex++, status);
            }
            if (startDate != null && endDate != null) {
                stmt.setString(paramIndex++, startDate + " 00:00:00");
                stmt.setString(paramIndex++, endDate + " 23:59:59");
            }
            stmt.setInt(paramIndex++, pageSize);
            stmt.setInt(paramIndex++, offset);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                orders.add(new Order(
                        rs.getInt("id"),
                        rs.getString("order_code"),
                        rs.getInt("customer_id"),
                        rs.getInt("user_id"),
                        rs.getInt("discount_id"),
                        rs.getDouble("total_amount"),
                        rs.getString("status"),
                        rs.getTimestamp("order_date").toLocalDateTime()
                ));
            }
            return orders;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getTotalRecords(String status, String startDate, String endDate) {
        try (Connection conn = databaseConnectService.getConnection()){

            String sql = "SELECT COUNT(*) FROM orders WHERE 1=1 ";
            if (status != null && !status.isEmpty()) {
                sql += " AND status = ?";
            }
            if (startDate != null && endDate != null) {
                sql += " AND order_date BETWEEN ? AND ?";
            }

            PreparedStatement stmt = conn.prepareStatement(sql);
            int paramIndex = 1;
            if (status != null && !status.isEmpty()) {
                stmt.setString(paramIndex++, status);
            }
            if (startDate != null && endDate != null) {
                stmt.setString(paramIndex++, startDate + " 00:00:00");
                stmt.setString(paramIndex++, endDate + " 23:59:59");
            }

            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List<OrderDetail> getOrderDetails(int orderId) {
        List<OrderDetail> details = new ArrayList<>();
        try (Connection conn = databaseConnectService.getConnection();
             PreparedStatement stmt = conn.prepareStatement(orderQueries.GET_ORDER_DETAIL)) {

            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                details.add(new OrderDetail(
                        rs.getInt("order_id"),
                        rs.getInt("product_id"),
                        rs.getInt("quantity"),
                        rs.getDouble("unit_price")
                ));
            }
            return details;
        } catch (SQLException e) {
            e.printStackTrace();
            return details;
        }
    }

    public void updateOrderStatus(int orderId, String status) {
        try (Connection conn = databaseConnectService.getConnection();
             PreparedStatement stmt = conn.prepareStatement(orderQueries.UPDATE_ORDER)) {

            stmt.setString(1, status);
            stmt.setInt(2, orderId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Order findById(int orderId) {
        try (Connection conn = databaseConnectService.getConnection();
             PreparedStatement stmt = conn.prepareStatement(orderQueries.FIND_ORDER_BY_ID)) {

            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Order(
                        rs.getInt("id"),
                        rs.getString("order_code"),
                        rs.getInt("customer_id"),
                        rs.getInt("user_id"),
                        rs.getInt("discount_id"),
                        rs.getDouble("total_amount"),
                        rs.getString("status"),
                        rs.getTimestamp("order_date").toLocalDateTime()
                );
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
