package com.trangshop.shopexpense.repository.impl;

import com.mysql.cj.jdbc.ConnectionWrapper;
import com.trangshop.shopexpense.exception.ExpenseException;
import com.trangshop.shopexpense.model.Expense;
import com.trangshop.shopexpense.repository.ExpenseRepo;
import com.trangshop.shopexpense.service.DatabaseConnectService;
import com.trangshop.shopexpense.service.impl.DatabaseConnectServiceImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExpenseRepoImpl implements ExpenseRepo {
    private DatabaseConnectService databaseConnectService;

    public ExpenseRepoImpl() {
        this.databaseConnectService =  new DatabaseConnectServiceImpl();
    }

    @Override
    public List<Expense> findAll(int page,int size) {

        String sql = "select * from expenses limit ? offset ?";

        List<Expense> expenses =  new ArrayList<>();
        int offSet = (page - 1) * size; //offset 0 - page thứ 1 với size là 10,lấy từ bản ghi thứ 1
        try(Connection conn = databaseConnectService.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ){
            pstmt.setInt(1,size); //limit số lượng expense trên mỗi lần load dữ liệu, 10 20 30
            pstmt.setInt(2,offSet); //page đang đứng là bao nhiêu, 1 2 3 4 ...
            try(ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Expense expense = new Expense();
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
                    expenses.add(expense);
                }
            }
        } catch (SQLException e) {
                throw new ExpenseException("Database error while retrieving expenses: " + e.getMessage(), e);
        } catch (Exception e) {
                throw new ExpenseException("Unexpected error while retrieving expenses: " + e.getMessage(), e);
        }

        return expenses;
    }

    @Override
    public Expense create(Expense expense) {
        String sql = "insert into expenses (amount, description, expense_date, payment_method, location, " +
                "note, category_id, user_id, created_at) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = databaseConnectService.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setDouble(1, expense.getAmount());
            pstmt.setString(2, expense.getDescription());
            pstmt.setDate(3, expense.getExpenseDate()
                    != null ? new java.sql.Date(expense.getExpenseDate().getTime()) : null);
            pstmt.setString(4, expense.getPaymentMethod());
            pstmt.setString(5, expense.getLocation());
            pstmt.setString(6, expense.getNote());
            pstmt.setInt(7, expense.getCategoryId());
            pstmt.setInt(8, expense.getUserId());
            pstmt.setDate(9, expense.getCreatedAt()
                    != null ? new java.sql.Date(expense.getCreatedAt().getTime())
                    : new java.sql.Date(System.currentTimeMillis()));

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new ExpenseException("Failed to create expense");
            }

            // Lấy ID được sinh tự động
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    expense.setId(rs.getInt(1));
                } else {
                    throw new ExpenseException("Failed to retrieve generated ID for expense");
                }
            }
            System.out.println("Inserted expense with ID: " + expense.getId());
            return expense;
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            throw new ExpenseException("Database error while creating expense: " + e.getMessage(), e);
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
            throw new ExpenseException("Unexpected error while creating expense: " + e.getMessage(), e);
        }
    }

    @Override
    public Expense update(Expense expenseUpdate) {
        String sql = "UPDATE expenses SET user_id = ?, category_id = ?, amount = ?, description = ?, expense_date = ?, " +
                "payment_method = ?, location = ?, note = ? WHERE id = ?";
        try (Connection conn = databaseConnectService.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, expenseUpdate.getUserId());
            stmt.setInt(2, expenseUpdate.getCategoryId());
            stmt.setDouble(3, expenseUpdate.getAmount());
            stmt.setString(4, expenseUpdate.getDescription());
            stmt.setDate(5, expenseUpdate.getExpenseDate());
            stmt.setString(6, expenseUpdate.getPaymentMethod());
            stmt.setString(7, expenseUpdate.getLocation());
            stmt.setString(8, expenseUpdate.getNote());
            stmt.setInt(9, expenseUpdate.getId());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new ExpenseException("Expense with ID " + expenseUpdate.getId() + " not found");
            }
            return expenseUpdate;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
