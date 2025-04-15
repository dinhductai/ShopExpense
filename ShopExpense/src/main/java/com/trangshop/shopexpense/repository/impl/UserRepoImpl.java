package com.trangshop.shopexpense.repository.impl;

import com.trangshop.shopexpense.model.User;
import com.trangshop.shopexpense.repository.UserRepo;
import com.trangshop.shopexpense.service.DatabaseConnectService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepoImpl implements UserRepo {
    //khai báo để có thể kết nối database
    private DatabaseConnectService databaseConnectService;


    @Override
    public User findByUsernameAndPassword(String username, String password) {
        //query lấy thông tin để trả ra service
        String sql = "SELECT * FROM managers WHERE username = ? AND password = ?";
        try (Connection conn = databaseConnectService.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"));
            }else //nếu mà ko có dữ liệu trong result set thì đồng nghĩa là kết quả của query = null
                    //user ko được tìm ra
                System.out.println("User not found");
        } catch (SQLException e) {
            System.out.println("An error occured while trying to find the user");
            e.printStackTrace();
        }
        //ko tìm thấy user thì trả ra null,xử lý exception ở service
        return null;
    }
}
