package com.trangshop.shopexpense.service;

import com.trangshop.shopexpense.model.User;

public interface UserService {
    User login(String username, String password);

}

