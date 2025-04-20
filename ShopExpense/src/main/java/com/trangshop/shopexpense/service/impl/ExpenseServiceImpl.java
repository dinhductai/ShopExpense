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
        } catch (ExpenseException e) {
            throw e; //ném lại exception từ repository
        } catch (Exception e) {
            throw new ExpenseException("Error retrieving expenses: " + e.getMessage(), e);
        }
    }

    @Override
    public Expense getExpenseById(int id) {
        if (id <= 0) {
            throw new ExpenseException("Invalid expense ID: ID must be positive");
        }
        try {
            Expense expense = expenseRepo.findById(id);
            if (expense == null) {
                throw new ExpenseException("Expense with ID " + id + " not found");
            }
            System.out.println("Retrieved expense with ID: " + id);
            return expense;
        } catch (Exception e) {
            System.out.println("Error retrieving expense: " + e.getMessage());
            throw new ExpenseException("Failed to retrieve expense: " + e.getMessage());
        }
    }

    @Override
    public Expense createExpense(Expense newExpense) {
        try {
            // Kiểm tra dữ liệu đầu vào
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

            // Gọi repository để thêm chi tiêu
            Expense createdExpense = expenseRepo.create(newExpense);
            System.out.println("Created expense with ID: " + createdExpense.getId());
            return createdExpense;
        } catch (Exception e) {
            System.out.println("Error creating expense: " + e.getMessage());
            throw new ExpenseException("Error creating expense: " + e.getMessage(), e);
        }
    }

    @Override
    public Expense updateExpense(Expense expenseUpdate) {
        if (expenseUpdate == null || expenseUpdate.getId() <= 0) {
            throw new ExpenseException("Invalid expense ID");
        }
        if (expenseUpdate.getAmount() <= 0 || expenseUpdate.getCategoryId() <= 0) {
            throw new ExpenseException("Invalid amount or category");
        }
        if (expenseUpdate.getExpenseDate() == null) {
            throw new ExpenseException("Expense date is required");
        }
        // Validate các trường mới
        if (expenseUpdate.getPaymentMethod() == null || expenseUpdate.getPaymentMethod().trim().isEmpty()) {
            throw new ExpenseException("Payment method is required");
        }
        return expenseRepo.update(expenseUpdate);
    }

    @Override
    public void deleteExpense(int idExpense) {
    // Kiểm tra ID hợp lệ
        if (idExpense <= 0) {
            throw new ExpenseException("Invalid expense ID: ID must be positive");
        }
        try {
            // Gọi repository để xóa
            expenseRepo.delete(idExpense);
        } catch (ExpenseException e) {
            // Lỗi từ repository (như không tìm thấy chi tiêu)
            System.out.println("Error deleting expense: " + e.getMessage());
            throw e; // Ném lại để Servlet xử lý status code
        }
    }


}
