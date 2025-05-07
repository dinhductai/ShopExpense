package com.trangshop.shopexpense.service.repository.query;

public class DiscountQueries {
    public static final String CREATE_DISCOUNT =
            "INSERT INTO discounts (code, discount_value, discount_type, start_date, end_date, status) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
    public static final String UPDATE_DISCOUNT =
            "UPDATE discounts SET code = ?, discount_value = ?, discount_type = ?, start_date = ?, end_date = ?, " +
                    "status = ? WHERE id = ?";
    public static final String DELETE_DISCOUNT_BY_ID = "DELETE FROM discounts WHERE id = ?";
    public static final String CHECK_DISCOUNT_USING_BY_ID = "SELECT COUNT(*) FROM orders WHERE discount_id = ?";
    public static final String CHECK_CODE_EXISTING = "SELECT COUNT(*) FROM discounts WHERE code = ? AND id != ?";
}
