package com.trangshop.shopexpense.mapper;

import com.trangshop.shopexpense.model.Expense;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ExpenseMapper {
    //map dữ liệu từ result set qua object
    public Expense toExpense(Expense expense, ResultSet rs) {
        try{
            //thứ tự set dữ liệu phải đúng với thứ tự dl được lấy ra tại rs
            expense.setId(rs.getInt("id"));
            expense.setAmount(rs.getDouble("amount"));
            expense.setDescription(rs.getString("description"));
            expense.setExpenseDate(rs.getDate("expense_date"));
            expense.setPaymentMethod(rs.getString("payment_method"));
            expense.setLocation(rs.getString("location"));
            expense.setNote(rs.getString("note"));
            expense.setCategoryId(rs.getInt("category_id"));
            expense.setUserId(rs.getInt("user_id"));
            expense.setCreatedAt(rs.getDate("created_at"));
            return expense;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
