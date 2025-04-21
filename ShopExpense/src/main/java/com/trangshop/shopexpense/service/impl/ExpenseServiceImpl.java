package com.trangshop.shopexpense.service.impl;

import com.trangshop.shopexpense.exception.ExpenseException;
import com.trangshop.shopexpense.model.Expense;
import com.trangshop.shopexpense.repository.ExpenseRepo;
import com.trangshop.shopexpense.repository.impl.ExpenseRepoImpl;
import com.trangshop.shopexpense.service.ExpenseService;

import java.util.List;

public class ExpenseServiceImpl implements ExpenseService {
    private ExpenseRepo expenseRepo;

    public ExpenseServiceImpl() {
        this.expenseRepo = new ExpenseRepoImpl();
    }
    @Override
    public List<Expense> getAllExpenses(int page,int size) {
        try {
            List<Expense> expenses = expenseRepo.findAll(page, size);
            if (expenses.isEmpty()) {
                throw new ExpenseException("No expenses found for page=" + page + ", size=" + size);
            }
            return expenses;
        } catch (Exception e) {
            throw new ExpenseException("Error retrieving expenses: " + e.getMessage());
        }
    }

    @Override
    public Expense getExpenseById(int id) {
        if (id <= 0) {
            throw new ExpenseException("Invalid expense ID: ID must be positive ");
        }
        try {
            Expense expense = expenseRepo.findById(id);
            if (expense == null) {
                throw new ExpenseException("Expense with ID " + id + " not found ");
            }
            System.out.println("Retrieved expense with ID: " + id);
            return expense;
        } catch (Exception e) {
            throw new ExpenseException("Failed to retrieve expense: " + e.getMessage());
        }
    }

    @Override
    public Expense createExpense(Expense newExpense) {
        try {
            //kiểm tra dữ liệu đầu vào
            if (newExpense == null) {
                throw new ExpenseException("Expense cannot be null");
            }
            if (newExpense.getAmount() <= 0) {
                throw new ExpenseException("Amount must be greater than 0");
            }
            if (newExpense.getDescription() == null || newExpense.getDescription().trim().isEmpty()) {
                throw new ExpenseException("Description cannot be empty");
            }
            if (newExpense.getCategoryId() <= 0) {
                throw new ExpenseException("Invalid category ID");
            }
            if (newExpense.getUserId() <= 0) {
                throw new ExpenseException("Invalid user ID");
            }
            // gọi repository để thêm chi tiêu
            return expenseRepo.create(newExpense);
        } catch (Exception e) {
            throw new ExpenseException("Error creating expense: " + e.getMessage());
        }
    }

    @Override
    public Expense updateExpense(Expense expenseUpdate) {
        // validate các trường mới
        if (expenseUpdate == null || expenseUpdate.getId() <= 0) {
            throw new ExpenseException("Invalid expense ID");
        }
        if (expenseUpdate.getAmount() <= 0 || expenseUpdate.getCategoryId() <= 0) {
            throw new ExpenseException("Invalid amount or category");
        }
        if (expenseUpdate.getExpenseDate() == null) {
            throw new ExpenseException("Expense date is required");
        }
        if (expenseUpdate.getPaymentMethod() == null || expenseUpdate.getPaymentMethod().trim().isEmpty()) {
            throw new ExpenseException("Payment method is required");
        }
        return expenseRepo.update(expenseUpdate);
    }

    @Override
    public void deleteExpense(int idExpense) {
    //kiểm tra ID hợp lệ
        if (idExpense <= 0) {
            throw new ExpenseException("Invalid expense ID: ID must be positive");
        }
        try {
            // gọi repository để xóa
            expenseRepo.delete(idExpense);
        } catch (ExpenseException e) {
            //lỗi từ repository (như không tìm thấy chi tiêu)
            throw new ExpenseException("Failed to delete expense: " + e.getMessage());
        }
    }


}
