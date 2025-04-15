package com.trangshop.shopexpense.service.impl;

import com.trangshop.shopexpense.model.User;
import com.trangshop.shopexpense.repository.UserRepo;
import com.trangshop.shopexpense.repository.impl.UserRepoImpl;
import com.trangshop.shopexpense.service.DatabaseConnectService;
import com.trangshop.shopexpense.service.UserService;

public class UserServiceImpl implements UserService {
    private UserRepo userRepo;

    public UserServiceImpl() {
        this.userRepo = new UserRepoImpl(); // Khởi tạo userRepo
    }



    @Override
    public User login(String username, String password) {
        // Thêm logic validate nếu cần
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            return null;
        }
        return userRepo.findByUsernameAndPassword(username, password);
    }
}
