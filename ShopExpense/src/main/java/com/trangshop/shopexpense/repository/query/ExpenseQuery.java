package com.trangshop.shopexpense.repository.query;

public class ExpenseQuery {
    public final String FIND_ALL_EXPENSE = "select * from expenses limit ? offset ?";
    public final String FIND_ONE_EXPENSE = "select * from expenses where id = ?";
    public final String CREATE_EXPENSE =
            "insert into expenses (amount, description, expense_date, payment_method, location, " +
                    "note, category_id, user_id, created_at) " +
                    "values (?, ?, ?, ?, ?, ?, ?, ?, ?) ";

    public final String UPDATE_EXPENSE =
            "UPDATE expenses SET user_id = ?, category_id = ?, amount = ?, description = ?, expense_date = ?, " +
                    "payment_method = ?, location = ?, note = ? WHERE id = ? ";

    public final String DELETE_EXPENSE = "delete from expenses where id = ? ";

}
