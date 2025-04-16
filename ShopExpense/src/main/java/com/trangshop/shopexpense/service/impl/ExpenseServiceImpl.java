package com.trangshop.shopexpense.service.impl;

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
        return expenseRepo.findAll(page,size);
    }
}
