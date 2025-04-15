package com.trangshop.shopexpense.service.impl;

import com.trangshop.shopexpense.service.DatabaseConnectService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectServiceImpl implements DatabaseConnectService {

    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    //kết nối database bằng jdbc
    private Connection getConnection() {
        try{
            return DriverManager.getConnection(URL, USER, PASSWORD);
        }catch (SQLException e){
            //nếu kết nối lỗi thì trả null
            System.out.println("An error occured while connecting to the database"+
                    e.getMessage());
            return null;
        }
    }


}
