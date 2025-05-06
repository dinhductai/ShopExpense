package com.trangshop.shopexpense.service.repository.query;

public class ProductQueries {
    public static final String FIND_ALL_PRODUCT ="SELECT * FROM products LIMIT ? OFFSET ?";
    public static final String CREATE_NEW_PRODUCT =
            "INSERT INTO products (product_code, name, description, price, stock_quantity, specifications) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
    public static final String COUNT_TOTAL_RECORD ="SELECT COUNT(*) FROM products";
    public static final String UPDATE_PRODUCT ="UPDATE products SET product_code = ?, name = ? " +
            ", description = ?, price = ?, stock_quantity = ?, specifications = ? WHERE id = ?";
    public static final String DELETE_PRODUCT ="DELETE FROM products WHERE id = ?";
    public static final String FIND_PRODUCT_BY_ID ="SELECT * FROM products WHERE id = ?";
}
