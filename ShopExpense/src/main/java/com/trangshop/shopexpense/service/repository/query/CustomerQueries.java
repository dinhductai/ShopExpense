package com.trangshop.shopexpense.service.repository.query;

public class CustomerQueries {
    public static final String FIND_ALL_CUSTOMER= "SELECT * FROM customers LIMIT ? OFFSET ?";
    public static final String GET_TOTAL_RECORD = "SELECT COUNT(*) FROM customers";
    public static final String CREATE_NEW_CUSTOMER = "INSERT INTO customers (name, address, phone, email) VALUES (?, ?, ?, ?)";
    public static final String UPDATE_CUSTOMER = "UPDATE customers SET name = ?, address = ?, phone = ?, email = ? WHERE id = ?";
    public static final String DELETE_CUSTOMER = "DELETE FROM customers WHERE id = ?";
    public static final String CHECK_ORDER =  "SELECT COUNT(*) FROM orders WHERE customer_id = ?";
    public static final String FIND_CUSTOMER_BY_ID = "SELECT * FROM customers WHERE id = ?";
    public static final String CHECK_PHONE_NUMBER_OR_EMAIL_EXISTING = "SELECT COUNT(*) FROM customers WHERE (phone = ? OR email = ?) AND id != ?";


}
