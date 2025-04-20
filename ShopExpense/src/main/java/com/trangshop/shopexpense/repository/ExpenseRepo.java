package com.trangshop.shopexpense.repository;

import com.trangshop.shopexpense.model.Expense;

import java.util.List;

public interface ExpenseRepo {
    List<Expense> findAll(int page,int size);
    Expense create(Expense expense);
    Expense update(Expense expenseUpdate);
    void delete(int idExpense);

}
