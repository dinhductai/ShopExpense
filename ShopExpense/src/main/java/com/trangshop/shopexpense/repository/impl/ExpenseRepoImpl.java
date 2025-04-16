package com.trangshop.shopexpense.repository.impl;

import com.mysql.cj.jdbc.ConnectionWrapper;
import com.trangshop.shopexpense.model.Expense;
import com.trangshop.shopexpense.repository.ExpenseRepo;
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
            }catch (SQLException e) {
                System.out.println("SQLException in ExpenseRepoImpl: " + e.getMessage());
                e.printStackTrace();
            }

        }catch (Exception e){
            System.out.println("Exception in ExpenseRepoImpl: " + e.getMessage());
            e.printStackTrace();
        }
        return expenses;
    }
}
