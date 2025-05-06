package com.trangshop.shopexpense.service.repository.query;

public class RevenueQueries {
    public static final String GET_REVENUE_BY_TIME =
            "SELECT DATE_FORMAT(order_date, ?) as time_period, SUM(total_amount) as total_revenue " +
            "FROM orders " +
            "WHERE order_date BETWEEN ? AND ? AND status = 'DELIVERED' " +
            "GROUP BY time_period " +
            "ORDER BY time_period";

    public static final String GET_REVENUE_BY_PRODUCT =
            "SELECT p.name, SUM(od.quantity * od.unit_price) as total_revenue " +
                    "FROM order_details od " +
                    "JOIN products p ON od.product_id = p.id " +
                    "JOIN orders o ON od.order_id = o.id " +
                    "WHERE o.order_date BETWEEN ? AND ? AND o.status = 'DELIVERED' " +
                    "GROUP BY p.id, p.name " +
                    "ORDER BY total_revenue DESC";
    public static final String GET_REVENUE_BY_CUSTOMER =
            "SELECT c.name, SUM(o.total_amount) as total_revenue " +
                    "FROM orders o " +
                    "JOIN customers c ON o.customer_id = c.id " +
                    "WHERE o.order_date BETWEEN ? AND ? AND o.status = 'DELIVERED' " +
                    "GROUP BY c.id, c.name " +
                    "ORDER BY total_revenue DESC";
}
