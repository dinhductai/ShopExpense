package com.trangshop.shopexpense.service.repository.query;

public class UserQueries {
    public final String GET_USER_INFORMATION = "SELECT * FROM users WHERE username = ? AND password = ?";
}
