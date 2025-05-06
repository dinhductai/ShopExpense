package com.trangshop.shopexpense.service.impl;

import com.trangshop.shopexpense.model.Customer;
import com.trangshop.shopexpense.service.CustomerService;
import com.trangshop.shopexpense.service.repository.CustomerRepo;
import com.trangshop.shopexpense.service.repository.impl.CustomerRepoImpl;

import java.util.List;

public class CustomerServiceImpl implements CustomerService {
    private CustomerRepo customerRepo;

    public CustomerServiceImpl() {
        this.customerRepo = new CustomerRepoImpl();
    }


    @Override
    public List<Customer> getCustomers(int page, int pageSize) {
        return customerRepo.findAll(page, pageSize);
    }

    @Override
    public int getTotalPages(int pageSize) {
        int totalRecords = customerRepo.getTotalRecords();
        return (int) Math.ceil((double) totalRecords / pageSize);
    }

    @Override
    public void addCustomer(Customer customer) throws IllegalArgumentException {
        if (customerRepo.isPhoneOrEmailExists(customer.getPhone(), customer.getEmail(), null)) {
            throw new IllegalArgumentException("Phone or email already exists.");
        }
        customerRepo.addCustomer(customer);
    }


    @Override
    public void updateCustomer(Customer customer) throws IllegalArgumentException {
        if (customerRepo.isPhoneOrEmailExists(customer.getPhone(), customer.getEmail(), customer.getId())) {
            throw new IllegalArgumentException("Phone or email already exists.");
        }
        customerRepo.updateCustomer(customer);
    }

    @Override
    public void deleteCustomer(int id) throws IllegalArgumentException {
        Customer customer = customerRepo.findById(id);
        if (customer == null) {
            throw new IllegalArgumentException("Customer not found.");
        }
        customerRepo.deleteCustomer(id);
    }

    @Override
    public Customer getCustomerById(int id) {
        return customerRepo.findById(id);
    }
}
