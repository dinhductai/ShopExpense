package com.trangshop.shopexpense.servlet;

import com.trangshop.shopexpense.model.Order;
import com.trangshop.shopexpense.model.OrderDetail;
import com.trangshop.shopexpense.service.OrderService;
import com.trangshop.shopexpense.service.impl.OrderServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/orders")
public class OrderServlet extends HttpServlet {

    private OrderService orderService;

    @Override
    public void init() throws ServletException {
        this.orderService = new OrderServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedIn") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "detail":
                int orderId = Integer.parseInt(request.getParameter("id"));
                List<OrderDetail> details = orderService.getOrderDetails(orderId);
                request.setAttribute("orderDetails", details);
                request.getRequestDispatcher("/WEB-INF/views/order-detail.jsp").forward(request, response);
                break;
            case "update":
                orderId = Integer.parseInt(request.getParameter("id"));
                String status = request.getParameter("status");
                try {
                    orderService.updateOrderStatus(orderId, status);
                } catch (IllegalArgumentException e) {
                    request.setAttribute("error", e.getMessage());
                }
                response.sendRedirect(request.getContextPath() + "/orders");
                break;
            case "list":
            default:
                int page = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
                int pageSize = 10;
                String statusFilter = request.getParameter("status");
                String startDate = request.getParameter("startDate");
                String endDate = request.getParameter("endDate");
                if (startDate == null || endDate == null) {
                    LocalDate now = LocalDate.now();
                    startDate = now.minusMonths(1).toString();
                    endDate = now.toString();
                }

                List<Order> orders = orderService.getOrders(page, pageSize, statusFilter, startDate, endDate);
                int totalPages = orderService.getTotalPages(pageSize, statusFilter, startDate, endDate);
                request.setAttribute("orders", orders);
                request.setAttribute("currentPage", page);
                request.setAttribute("totalPages", totalPages);
                request.setAttribute("statusFilter", statusFilter);
                request.setAttribute("startDate", startDate);
                request.setAttribute("endDate", endDate);
                request.getRequestDispatcher("/WEB-INF/views/orders.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedIn") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String statusFilter = request.getParameter("status");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        response.sendRedirect(request.getContextPath() + "/orders?status=" + (statusFilter != null ? statusFilter : "") +
                "&startDate=" + (startDate != null ? startDate : "") +
                "&endDate=" + (endDate != null ? endDate : ""));
    }
}
