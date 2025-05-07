package com.trangshop.shopexpense.service.repository.query;

public class OrderQueries {
    public static final String GET_ORDER_DETAIL =
            "SELECT * FROM order_details WHERE order_id = ?";
    public static final String UPDATE_ORDER = "UPDATE orders SET status = ? WHERE id = ?";
    public static final String FIND_ORDER_BY_ID = "SELECT * FROM orders WHERE id = ?";
}
