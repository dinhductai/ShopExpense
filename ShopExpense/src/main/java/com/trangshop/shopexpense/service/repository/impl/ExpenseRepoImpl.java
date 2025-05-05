package com.trangshop.shopexpense.service.repository.impl;

import com.trangshop.shopexpense.exception.ExpenseException;
import com.trangshop.shopexpense.mapper.ExpenseMapper;
import com.trangshop.shopexpense.model.Expense;
import com.trangshop.shopexpense.service.repository.ExpenseRepo;
import com.trangshop.shopexpense.service.repository.query.ExpenseQuery;
import com.trangshop.shopexpense.service.DatabaseConnectService;
import com.trangshop.shopexpense.service.impl.DatabaseConnectServiceImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExpenseRepoImpl implements ExpenseRepo {
    private DatabaseConnectService databaseConnectService;
    private ExpenseMapper expenseMapper;
    private ExpenseQuery expenseQuery;
    public ExpenseRepoImpl() {
        this.databaseConnectService =  new DatabaseConnectServiceImpl();
        this.expenseMapper = new ExpenseMapper();
        this.expenseQuery = new ExpenseQuery();
    }

    @Override
    public List<Expense> findAll(int page,int size) {
        List<Expense> expenses =  new ArrayList<>();
        int offSet = (page - 1) * size; //offset 0 - page thứ 1 với size là 10,lấy từ bản ghi thứ 1
        try(Connection conn = databaseConnectService.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(expenseQuery.FIND_ALL_EXPENSE);
            ){
            pstmt.setInt(1,size); //limit số lượng expense trên mỗi lần load dữ liệu, 10 20 30
            pstmt.setInt(2,offSet); //page đang đứng là bao nhiêu, 1 2 3 4 ...
            try(ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Expense expense = new Expense();
                    expenses.add(expenseMapper.toExpense(expense,rs));
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
    public Expense findById(int id) {
        try (Connection conn = databaseConnectService.getConnection();
             PreparedStatement stmt = conn.prepareStatement(expenseQuery.FIND_ONE_EXPENSE)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Expense expense = new Expense();
                return expenseMapper.toExpense(expense,rs);
            }
            return null; // Service sẽ xử lý trường hợp không tìm thấy
        } catch (SQLException e) {
            System.out.println("Database error retrieving expense: " + e.getMessage());
            throw new ExpenseException("Error retrieving expense: " + e.getMessage());
        }    }

    @Override
    public Expense create(Expense expense) {
        try (Connection conn = databaseConnectService.getConnection();
             //PreparedStatement.RETURN_GENERATED_KEYS dùng để
             //yêu cầu cơ sở dữ liệu trả về giá trị khóa chính tự động tăng (khóa chinh của expense vừa đc tạo)
             PreparedStatement pstmt = conn.prepareStatement(expenseQuery.CREATE_EXPENSE, PreparedStatement.RETURN_GENERATED_KEYS)) {
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
        try (Connection conn = databaseConnectService.getConnection();
             PreparedStatement stmt = conn.prepareStatement(expenseQuery.UPDATE_EXPENSE)) {
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

    @Override
    public void delete(int idExpense) {
        try (Connection conn = databaseConnectService.getConnection();
             PreparedStatement stmt = conn.prepareStatement(expenseQuery.DELETE_EXPENSE)) {
            stmt.setInt(1, idExpense);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new ExpenseException("Expense with ID " + idExpense + " not found");
            }
        } catch (SQLException e) {
            throw new ExpenseException("Error deleting expense: " + e.getMessage());
        }
    }
}
