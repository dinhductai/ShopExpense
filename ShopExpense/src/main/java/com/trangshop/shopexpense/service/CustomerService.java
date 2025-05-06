package com.trangshop.shopexpense.service;

import com.trangshop.shopexpense.model.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> getCustomers(int page, int pageSize);
    int getTotalPages(int pageSize);
    void addCustomer(Customer customer);
    void updateCustomer(Customer customer);
    void deleteCustomer(int id);
    Customer getCustomerById(int id);
}
