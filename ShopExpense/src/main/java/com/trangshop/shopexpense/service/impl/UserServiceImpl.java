package com.trangshop.shopexpense.service.impl;

import com.trangshop.shopexpense.model.User;
import com.trangshop.shopexpense.service.repository.UserRepo;
import com.trangshop.shopexpense.service.repository.impl.UserRepoImpl;
import com.trangshop.shopexpense.service.UserService;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserServiceImpl implements UserService {
    private UserRepo userRepo;

    public UserServiceImpl() {
        this.userRepo = new UserRepoImpl(); // Khởi tạo userRepo
    }



    @Override
    public User login(String username, String password) {
        if (username == null || password == null) {
            return null;
        }
        String hashedPassword = md5(password);
        if (hashedPassword == null) {
            return null;
        }
        User user = userRepo.findByUsernameAndPassword(username, hashedPassword);
        if (user != null && (user.getRole().equals("MANAGER") || user.getRole().equals("EMPLOYEE"))) {
            return user;
        }
        return null;
    }

    //mã hóa pass bằng thuật toán md5
    private String md5(String input) {
        try {
            //khai báo thuaat toán
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : messageDigest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
