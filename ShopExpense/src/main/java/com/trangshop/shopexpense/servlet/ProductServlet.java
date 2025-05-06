package com.trangshop.shopexpense.servlet;

import com.trangshop.shopexpense.model.Product;
import com.trangshop.shopexpense.service.ProductService;
import com.trangshop.shopexpense.service.impl.ProductServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/products")
public class ProductServlet extends HttpServlet {

    private ProductService productService;

    @Override
    public void init() throws ServletException {
        this.productService = new ProductServiceImpl();
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
                request.getRequestDispatcher("/WEB-INF/views/add-product.jsp").forward(request, response);
                break;
            case "edit":
                int id = Integer.parseInt(request.getParameter("id"));
                Product product = productService.getProductById(id);
                request.setAttribute("product", product);
                request.getRequestDispatcher("/WEB-INF/views/edit-product.jsp").forward(request, response);
                break;
            case "delete":
                id = Integer.parseInt(request.getParameter("id"));
                productService.deleteProduct(id);
                response.sendRedirect(request.getContextPath() + "/products");
                break;
            case "list":
            default:
                int page = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
                int pageSize = 10;
                List<Product> products = productService.getProducts(page, pageSize);
                int totalPages = productService.getTotalPages(pageSize);
                request.setAttribute("products", products);
                request.setAttribute("currentPage", page);
                request.setAttribute("totalPages", totalPages);
                request.getRequestDispatcher("/WEB-INF/views/products.jsp").forward(request, response);
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
            response.sendRedirect(request.getContextPath() + "/products");
            return;
        }

        switch (action) {
            case "add":
                Product newProduct = new Product(
                        0, // ID sẽ được tự động tăng bởi database
                        request.getParameter("productCode"),
                        request.getParameter("name"),
                        request.getParameter("description"),
                        Double.parseDouble(request.getParameter("price")),
                        Integer.parseInt(request.getParameter("stockQuantity")),
                        request.getParameter("specifications")
                );
                productService.addProduct(newProduct);
                response.sendRedirect(request.getContextPath() + "/products");
                break;
            case "edit":
                Product updatedProduct = new Product(
                        Integer.parseInt(request.getParameter("id")),
                        request.getParameter("productCode"),
                        request.getParameter("name"),
                        request.getParameter("description"),
                        Double.parseDouble(request.getParameter("price")),
                        Integer.parseInt(request.getParameter("stockQuantity")),
                        request.getParameter("specifications")
                );
                productService.updateProduct(updatedProduct);
                response.sendRedirect(request.getContextPath() + "/products");
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/products");
                break;
        }
    }
}