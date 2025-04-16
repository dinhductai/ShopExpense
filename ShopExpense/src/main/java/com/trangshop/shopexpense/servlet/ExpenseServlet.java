package com.trangshop.shopexpense.servlet;
import com.trangshop.shopexpense.model.Expense;
import com.trangshop.shopexpense.service.ExpenseService;
import com.trangshop.shopexpense.service.impl.ExpenseServiceImpl;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;


//http://localhost:8080/kieutrangshop/expenses?page=2&size=10
@WebServlet("/expenses")
public class ExpenseServlet extends HttpServlet {
    private ExpenseService expenseService;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        this.expenseService = new ExpenseServiceImpl();
        this.gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            // Đọc tham số phân trang từ query string
            //set mặc định trước
            int page = 1;
            int size = 10;

            // Nếu client có truyền page và size, thì lấy
            if (req.getParameter("page") != null) {
                page = Integer.parseInt(req.getParameter("page"));
            }
            if (req.getParameter("size") != null) {
                size = Integer.parseInt(req.getParameter("size"));
            }

            // Gọi service lấy danh sách phân trang
            List<Expense> expenses = expenseService.getAllExpenses(page, size);

            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(gson.toJson(expenses));
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(gson.toJson("Error retrieving expenses: " + e.getMessage()));
        }
    }

}