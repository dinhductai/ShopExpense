package com.trangshop.shopexpense.servlet;


import com.trangshop.shopexpense.model.RevenueByCustomer;
import com.trangshop.shopexpense.model.RevenueByProduct;
import com.trangshop.shopexpense.model.RevenueByTime;
import com.trangshop.shopexpense.service.RevenueService;
import com.trangshop.shopexpense.service.impl.RevenueServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/revenue")
public class RevenueServlet extends HttpServlet {

    private RevenueService revenueService;

    @Override
    public void init() throws ServletException {
        this.revenueService = new RevenueServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedIn") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String role = (String) session.getAttribute("role");
        if (!"MANAGER".equals(role)) {
            response.sendRedirect(request.getContextPath() + "/products");
            return;
        }

        // Lấy khoảng thời gian từ request, nếu không có thì dùng mặc định
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        if (startDate == null || endDate == null) {
            LocalDate now = LocalDate.now();
            startDate = now.minusMonths(1).toString(); // Mặc định 1 tháng trước
            endDate = now.toString(); // Đến hiện tại
        }

        // Lấy dữ liệu doanh thu theo ngày, tháng, năm
        List<RevenueByTime> revenueByDay = revenueService.getRevenueByTime("day", startDate, endDate);
        List<RevenueByTime> revenueByMonth = revenueService.getRevenueByTime("month", startDate, endDate);
        List<RevenueByTime> revenueByYear = revenueService.getRevenueByTime("year", startDate, endDate);

        // Lấy doanh thu theo sản phẩm và khách hàng
        List<RevenueByProduct> revenueByProduct = revenueService.getRevenueByProduct(startDate, endDate);
        List<RevenueByCustomer> revenueByCustomer = revenueService.getRevenueByCustomer(startDate, endDate);

        // Đặt dữ liệu vào request để hiển thị
        request.setAttribute("revenueByDay", revenueByDay);
        request.setAttribute("revenueByMonth", revenueByMonth);
        request.setAttribute("revenueByYear", revenueByYear);
        request.setAttribute("revenueByProduct", revenueByProduct);
        request.setAttribute("revenueByCustomer", revenueByCustomer);
        request.setAttribute("startDate", startDate);
        request.setAttribute("endDate", endDate);

        request.getRequestDispatcher("/WEB-INF/views/revenue.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        response.sendRedirect(request.getContextPath() + "/revenue?startDate=" + startDate + "&endDate=" + endDate);
    }
}