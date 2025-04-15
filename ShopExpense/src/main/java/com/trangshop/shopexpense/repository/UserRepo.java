package com.trangshop.shopexpense.repository;

import com.trangshop.shopexpense.model.User;

public interface UserRepo {
    User findByUsernameAndPassword(String username, String password);

}
