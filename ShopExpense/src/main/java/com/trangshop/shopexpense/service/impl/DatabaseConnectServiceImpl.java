package com.trangshop.shopexpense.service.impl;

import com.trangshop.shopexpense.service.DatabaseConnectService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectServiceImpl implements DatabaseConnectService {

    private static final String URL = "jdbc:mysql://localhost:3306/shopexpense";
    private static final String USER = "root";
    private static final String PASSWORD = "mysqlcuatai123*";

    @Override
    public Connection getConnection() throws SQLException {
        try {
            // Kiểm tra driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("MySQL JDBC Driver loaded successfully");

            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Database connection established successfully");
            return conn;
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found: " + e.getMessage());
            throw new SQLException("MySQL JDBC Driver not found", e);
        } catch (SQLException e) {
            System.out.println("SQLException while connecting to database: " + e.getMessage());
            throw e;
        }
    }


}
