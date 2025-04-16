package com.trangshop.shopexpense.servlet;

import com.trangshop.shopexpense.model.User;
import com.trangshop.shopexpense.service.UserService;
import com.trangshop.shopexpense.service.impl.UserServiceImpl;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//http://localhost:8080/kieutrangshop/login?username=admin&password=admin
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserService userService;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        this.userService = new UserServiceImpl();
        this.gson = new Gson();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        User user = userService.login(username, password);
        Map<String, Object> response = new HashMap<>();
        if (user != null) {
            response.put("success", true);
            response.put("message", "Login successful");
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.put("success", false);
            response.put("message", "Invalid username or password");
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

        resp.getWriter().write(gson.toJson(response));
    }
}