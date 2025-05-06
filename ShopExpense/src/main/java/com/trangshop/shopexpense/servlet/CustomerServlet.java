package com.trangshop.shopexpense.servlet;
import com.trangshop.shopexpense.model.Customer;
import com.trangshop.shopexpense.service.CustomerService;
import com.trangshop.shopexpense.service.impl.CustomerServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/customers")
public class CustomerServlet extends HttpServlet {

    private CustomerService customerService;

    @Override
    public void init() throws ServletException {
        this.customerService = new CustomerServiceImpl();
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
            case "add":
                request.getRequestDispatcher("/WEB-INF/views/add-customer.jsp").forward(request, response);
                break;
            case "edit":
                int id = Integer.parseInt(request.getParameter("id"));
                Customer customer = customerService.getCustomerById(id);
                request.setAttribute("customer", customer);
                request.getRequestDispatcher("/WEB-INF/views/edit-customer.jsp").forward(request, response);
                break;
            case "delete":
                id = Integer.parseInt(request.getParameter("id"));
                try {
                    customerService.deleteCustomer(id);
                } catch (IllegalArgumentException e) {
                    request.setAttribute("error", e.getMessage());
                }
                response.sendRedirect(request.getContextPath() + "/customers");
                break;
            case "list":
            default:
                int page = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
                int pageSize = 10;
                List<Customer> customers = customerService.getCustomers(page, pageSize);
                int totalPages = customerService.getTotalPages(pageSize);
                request.setAttribute("customers", customers);
                request.setAttribute("currentPage", page);
                request.setAttribute("totalPages", totalPages);
                request.getRequestDispatcher("/WEB-INF/views/customers.jsp").forward(request, response);
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
        if (action == null) {
            response.sendRedirect(request.getContextPath() + "/customers");
            return;
        }

        try {
            switch (action) {
                case "add":
                    Customer newCustomer = new Customer(
                            0,
                            request.getParameter("name"),
                            request.getParameter("address"),
                            request.getParameter("phone"),
                            request.getParameter("email")
                    );
                    customerService.addCustomer(newCustomer);
                    break;
                case "edit":
                    Customer updatedCustomer = new Customer(
                            Integer.parseInt(request.getParameter("id")),
                            request.getParameter("name"),
                            request.getParameter("address"),
                            request.getParameter("phone"),
                            request.getParameter("email")
                    );
                    customerService.updateCustomer(updatedCustomer);
                    break;
            }
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/" + (action.equals("add") ? "add-customer.jsp" : "edit-customer.jsp")).forward(request, response);
            return;
        }
        response.sendRedirect(request.getContextPath() + "/customers");
    }
}
