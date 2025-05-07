package com.trangshop.shopexpense.servlet;

import com.trangshop.shopexpense.model.Discount;
import com.trangshop.shopexpense.service.DiscountService;
import com.trangshop.shopexpense.service.impl.DiscountServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/discounts")
public class DiscountServlet extends HttpServlet {

    private DiscountService discountService;

    @Override
    public void init() throws ServletException {
        this.discountService = new DiscountServiceImpl();
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
            case "create":
                request.getRequestDispatcher("/WEB-INF/views/discount-create.jsp").forward(request, response);
                break;
            case "edit":
                int id = Integer.parseInt(request.getParameter("id"));
                Discount discount = discountService.getDiscounts(1, 1, null).stream()
                        .filter(p -> p.getId() == id).findFirst().orElse(null);
                request.setAttribute("discount", discount);
                request.getRequestDispatcher("/WEB-INF/views/discount-edit.jsp").forward(request, response);
                break;
            case "delete":
                id = Integer.parseInt(request.getParameter("id"));
                try {
                    discountService.deleteDiscount(id);
                    session.setAttribute("success", "Discount deleted successfully.");
                } catch (IllegalArgumentException e) {
                    session.setAttribute("error", e.getMessage());
                }
                response.sendRedirect(request.getContextPath() + "/discounts");
                break;
            case "list":
            default:
                int page = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
                int pageSize = 10;
                String statusFilter = request.getParameter("status");

                List<Discount> discounts = discountService.getDiscounts(page, pageSize, statusFilter);
                int totalPages = discountService.getTotalPages(pageSize, statusFilter);
                request.setAttribute("discounts", discounts);
                request.setAttribute("currentPage", page);
                request.setAttribute("totalPages", totalPages);
                request.setAttribute("statusFilter", statusFilter);
                request.getRequestDispatcher("/WEB-INF/views/discounts.jsp").forward(request, response);
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

        String action = request.getParameter("action");
        if ("create".equals(action)) {
            String code = request.getParameter("code");
            double discountValue = Double.parseDouble(request.getParameter("discountValue"));
            String discountType = request.getParameter("discountType");
            LocalDateTime startDate = LocalDateTime.parse(request.getParameter("startDate") + "T00:00:00");
            LocalDateTime endDate = LocalDateTime.parse(request.getParameter("endDate") + "T23:59:59");
            Discount discount = new Discount(0, code, discountValue, discountType, startDate, endDate, "");
            try {
                discountService.createDiscount(discount);
                session.setAttribute("success", "Discount created successfully.");
            } catch (IllegalArgumentException e) {
                session.setAttribute("error", e.getMessage());
            }
            response.sendRedirect(request.getContextPath() + "/discounts");
        } else if ("update".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            String code = request.getParameter("code");
            double discountValue = Double.parseDouble(request.getParameter("discountValue"));
            String discountType = request.getParameter("discountType");
            LocalDateTime startDate = LocalDateTime.parse(request.getParameter("startDate") + "T00:00:00");
            LocalDateTime endDate = LocalDateTime.parse(request.getParameter("endDate") + "T23:59:59");
            Discount discount = new Discount(id, code, discountValue, discountType, startDate, endDate, "");
            try {
                discountService.updateDiscount(discount);
                session.setAttribute("success", "Discount updated successfully.");
            } catch (IllegalArgumentException e) {
                session.setAttribute("error", e.getMessage());
            }
            response.sendRedirect(request.getContextPath() + "/discounts");
        }
    }
}