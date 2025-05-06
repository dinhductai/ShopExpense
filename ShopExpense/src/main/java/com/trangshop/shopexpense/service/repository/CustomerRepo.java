package com.trangshop.shopexpense.service.repository;

import com.trangshop.shopexpense.model.Customer;

import java.util.List;

public interface CustomerRepo {
    List<Customer> findAll(int page, int pageSize);
    int getTotalRecords();
    void addCustomer(Customer customer);
    void updateCustomer(Customer customer);
    void deleteCustomer(int id);
    Customer findById(int id);
    boolean isPhoneOrEmailExists(String phone, String email, Integer excludeId);
}
