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


}
