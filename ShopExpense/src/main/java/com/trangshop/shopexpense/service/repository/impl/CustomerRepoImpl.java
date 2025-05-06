package com.trangshop.shopexpense.service.repository.impl;

import com.trangshop.shopexpense.model.Customer;
import com.trangshop.shopexpense.service.DatabaseConnectService;
import com.trangshop.shopexpense.service.impl.DatabaseConnectServiceImpl;
import com.trangshop.shopexpense.service.repository.CustomerRepo;
import com.trangshop.shopexpense.service.repository.query.CustomerQueries;
import com.trangshop.shopexpense.service.repository.query.RevenueQueries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepoImpl implements CustomerRepo {
    private DatabaseConnectService databaseConnectService;
    private CustomerQueries customerQueries;

    public CustomerRepoImpl() {
        this.databaseConnectService = new DatabaseConnectServiceImpl();
        this.customerQueries = new CustomerQueries();
    }

    @Override
    public List<Customer> findAll(int page, int pageSize) {
        List<Customer> customers = new ArrayList<>();
        try (Connection conn = databaseConnectService.getConnection();
             PreparedStatement stmt = conn.prepareStatement(customerQueries.FIND_ALL_CUSTOMER)) {
            int offset = (page - 1) * pageSize;
            stmt.setInt(1, pageSize);
            stmt.setInt(2, offset);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Customer customer = new Customer(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getString("email")
                );
                customers.add(customer);
            }
            return customers;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getTotalRecords() {
        try (Connection conn = databaseConnectService.getConnection();
             PreparedStatement stmt = conn.prepareStatement(customerQueries.GET_TOTAL_RECORD)) {
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public void addCustomer(Customer customer) {
        try (Connection conn = databaseConnectService.getConnection();
             PreparedStatement stmt = conn.prepareStatement(customerQueries.CREATE_NEW_CUSTOMER)) {
            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getAddress());
            stmt.setString(3, customer.getPhone());
            stmt.setString(4, customer.getEmail());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateCustomer(Customer customer) {
        try (Connection conn = databaseConnectService.getConnection();
             PreparedStatement stmt = conn.prepareStatement(customerQueries.UPDATE_CUSTOMER)) {
            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getAddress());
            stmt.setString(3, customer.getPhone());
            stmt.setString(4, customer.getEmail());
            stmt.setInt(5, customer.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCustomer(int id) {
        try (Connection conn = databaseConnectService.getConnection();
             PreparedStatement stmt = conn.prepareStatement(customerQueries.DELETE_CUSTOMER);
             //kiểm tra đơn hàng liên quan
             PreparedStatement checkStmt = conn.prepareStatement(customerQueries.CHECK_ORDER)) {

            checkStmt.setInt(1, id);
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            int orderCount = rs.getInt(1);

            if (orderCount > 0) {
                throw new SQLException("Cannot delete customer with existing orders.");
            }

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Customer findById(int id) {
        try (Connection conn = databaseConnectService.getConnection();
             PreparedStatement stmt = conn.prepareStatement(customerQueries.FIND_CUSTOMER_BY_ID)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Customer(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getString("email")
                );
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean isPhoneOrEmailExists(String phone, String email, Integer excludeId) {
        try (Connection conn = databaseConnectService.getConnection();
             PreparedStatement stmt = conn.prepareStatement(customerQueries.CHECK_PHONE_NUMBER_OR_EMAIL_EXISTING)) {
            stmt.setString(1, phone);
            stmt.setString(2, email);
            stmt.setInt(3, excludeId != null ? excludeId : 0);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
