package com.trangshop.shopexpense.service;

import com.trangshop.shopexpense.model.Expense;

import java.util.List;

public interface ExpenseService {
    List<Expense> getAllExpenses(int page,int size);
    Expense getExpenseById(int id);
    Expense createExpense(Expense newExpense);
    Expense updateExpense(Expense expenseUpdate);
    void deleteExpense(int idExpense);
}
