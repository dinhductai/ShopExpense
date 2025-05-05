package com.trangshop.shopexpense.service.repository.impl;

import com.trangshop.shopexpense.model.User;
import com.trangshop.shopexpense.service.repository.UserRepo;
import com.trangshop.shopexpense.service.DatabaseConnectService;
import com.trangshop.shopexpense.service.impl.DatabaseConnectServiceImpl;
import com.trangshop.shopexpense.service.repository.query.UserQueries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepoImpl implements UserRepo {
    //khai báo để có thể kết nối database
    private DatabaseConnectService databaseConnectService;
    private UserQueries userQueries;
    public UserRepoImpl() {
        this.databaseConnectService = new DatabaseConnectServiceImpl();
        this.userQueries =  new UserQueries();
    }
    @Override
    public User findByUsernameAndPassword(String username, String hashedPassword) {
        //kết nối csdl và gán dữ liệu
        try (Connection conn = databaseConnectService.getConnection();
             PreparedStatement stmt = conn.prepareStatement(userQueries.GET_USER_INFORMATION)) {
            //thứ tự set biến và kiểu dữ liệu biến phải đúng với query
            stmt.setString(1, username);
            stmt.setString(2, hashedPassword);
            ResultSet rs = stmt.executeQuery();
            //resultset có dữ liệu thì gán vào đối tượng user
            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role")
                );
            } else //nếu mà ko có dữ liệu trong result set thì đồng nghĩa là kết quả của query = null
                //user ko được tìm ra
                return null;
        } catch (SQLException e) {
            System.out.println("An error occured while trying to find the user");
            e.printStackTrace();
        }
        //ko tìm thấy user thì trả ra null,xử lý exception ở service
        return null;
    }
}
