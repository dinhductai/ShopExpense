package com.trangshop.shopexpense.service.impl;

import com.trangshop.shopexpense.model.User;
import com.trangshop.shopexpense.repository.UserRepo;
import com.trangshop.shopexpense.service.DatabaseConnectService;
import com.trangshop.shopexpense.service.UserService;

public class UserServiceImpl implements UserService {
    private UserRepo userRepo;

    public UserServiceImpl() {
    }

    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public User login(String username, String password) {
        return null;
    }
}
