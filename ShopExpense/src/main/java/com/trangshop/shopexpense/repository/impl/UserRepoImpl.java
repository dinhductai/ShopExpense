package com.trangshop.shopexpense.repository.impl;

import com.trangshop.shopexpense.model.User;
import com.trangshop.shopexpense.repository.UserRepo;
import com.trangshop.shopexpense.service.DatabaseConnectService;
import com.trangshop.shopexpense.service.impl.DatabaseConnectServiceImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepoImpl implements UserRepo {
    //khai báo để có thể kết nối database
    private DatabaseConnectService databaseConnectService;

    public UserRepoImpl() {
        this.databaseConnectService = new DatabaseConnectServiceImpl();
    }
    @Override
    public User findByUsernameAndPassword(String username, String password) {
        //query lấy thông tin để trả ra service
        String sql = "SELECT * FROM user WHERE username = ? AND password = ? ";
        User user = new User();
        //kết nối csdl và gán dữ liệu
        try (Connection conn = databaseConnectService.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            //thứ tự set biến và kiểu dữ liệu biến phải đúng với query
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            //resultset có dữ liệu thì gán vào đối tượng user
            if (rs.next()) {
                 user.setId(rs.getInt("id"));
                 user.setUsername(rs.getString("username"));
                 user.setPassword(rs.getString("password"));
            }else //nếu mà ko có dữ liệu trong result set thì đồng nghĩa là kết quả của query = null
                    //user ko được tìm ra
                System.out.println("User not found");
        } catch (SQLException e) {
            System.out.println("An error occured while trying to find the user");
            e.printStackTrace();
        }
        //ko tìm thấy user thì trả ra null,xử lý exception ở service
        if(user != null){
            return user;
        }
        else return null;

    }
}
