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
    public Expense createExpense(Expense expense) {
        try {
            // Kiểm tra dữ liệu đầu vào
            if (expense == null) {
                throw new ExpenseException("Expense cannot be null");
            }
            if (expense.getAmount() <= 0) {
                throw new ExpenseException("Amount must be greater than 0");
            }
            if (expense.getDescription() == null || expense.getDescription().trim().isEmpty()) {
                throw new ExpenseException("Description cannot be empty");
            }
            if (expense.getCategoryId() <= 0) {
                throw new ExpenseException("Invalid category ID");
            }
            if (expense.getUserId() <= 0) {
                throw new ExpenseException("Invalid user ID");
            }

            // Gọi repository để thêm chi tiêu
            Expense createdExpense = expenseRepo.create(expense);
            System.out.println("Created expense with ID: " + createdExpense.getId());
            return createdExpense;
        } catch (Exception e) {
            System.out.println("Error creating expense: " + e.getMessage());
            throw new ExpenseException("Error creating expense: " + e.getMessage(), e);
        }
    }


}
