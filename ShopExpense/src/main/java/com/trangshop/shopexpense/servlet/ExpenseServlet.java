package com.trangshop.shopexpense.servlet;
import com.google.gson.Gson;
import com.trangshop.shopexpense.exception.ExpenseException;
import com.trangshop.shopexpense.model.Expense;
import com.trangshop.shopexpense.service.ExpenseService;
import com.trangshop.shopexpense.service.impl.ExpenseServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import static com.mysql.cj.conf.PropertyKey.logger;


//url test:  Http method:Get  http://localhost:8080/kieutrangshop/expenses?page=2&size=10
//api lấy tất cả expense, có hỗ trợ phân trang với page size đc truyền từ fe
@WebServlet("/expenses/*")
public class ExpenseServlet extends HttpServlet {
    private ExpenseService expenseService;
    private Gson gson; //thư viện Gson của google, config dependency ở file pom,
    // dùng để chuyển đổi giữa đối tượng Java (như List<Expense>) và chuỗi JSON (kiểu dữ liệu mà fe nhận đc và xử lý)


//   phương thức khởi tạo servlet, được gọi khi Servlet được tải lần đầu
//    khởi tạo expenseService và gson
    @Override
    public void init() throws ServletException {
        //một instance của interface ExpenseService,triển khai bởi lớp thực thi ExpenseServiceImpl,
        //chịu trách nhiệm xử lý logic nghiệp vụ, như lấy list expense
        this.expenseService = new ExpenseServiceImpl();
        this.gson = new Gson();
    }

    //get api method
    //xử lý các HTTP GET request tới /expenses
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // set header response là json,cho postman,browser biết kiểu dl trả về là json
        resp.setContentType("application/json");
        //response đc mã hóa hỗ trợ tiếng vịt
        resp.setCharacterEncoding("UTF-8");

        try {
            String pathInfo = req.getPathInfo();
            if (pathInfo != null && pathInfo.length() > 1) {
                // Xử lý GET /expenses/{id}
                String expenseId = pathInfo.substring(1);
                int id = Integer.parseInt(expenseId); // Service sẽ xử lý lỗi ID
                Expense expense = expenseService.getExpenseById(id);
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write(gson.toJson(expense));
            } else {
                // đọc tham số phân trang từ query
                //set mặc định trước,nếu api truyền về ko có page size,thì mặc định lấy 10 raw expense đầu tiên
                int page = 1;
                int size = 10;

                //lấy và xử lý tham số, set mặc định nếu không hợp lệ
                try {
                    if (req.getParameter("page") != null) {
                        page = Integer.parseInt(req.getParameter("page"));
                        if (page < 1) page = 1; // Set mặc định nếu page < 1
                    }
                    if (req.getParameter("size") != null) {
                        size = Integer.parseInt(req.getParameter("size"));
                        if (size < 1) size = 10; // Set mặc định nếu size < 1
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid page or size parameter, using defaults: page=1, size=10");
                    page = 1;
                    size = 10;
                }
                // gọi service lấy danh sách phân trang
                List<Expense> expenses = expenseService.getAllExpenses(page, size);

                //set status cho response status code  cs_ok = 200
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter() //ghi chuỗi json vào servlet response
                        .write(gson.toJson(expenses)); //chuyển list expense thành chuỗi dạng json
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(gson.toJson("Error retrieving expenses: " + e.getMessage()));
        }
    }

    // get api method
    // thêm chi tiêu mới /expenses
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            //đọc JSON từ request body
            //req.getReader() sẽ trả về BufferedReader dạng một luồng văn bản
            BufferedReader reader = req.getReader();
//            chuyển đổi (deserialize) dữ liệu JSON từ BufferedReader thành một đối tượng Java, ở đây là Expense
            Expense expenseUpdate = gson.fromJson(reader, Expense.class);
//            System.out.println("Received expense: " + gson.toJson(expenseUpdate));

            // gọi service để thêm chi tiêu
            Expense createdExpense = expenseService.createExpense(expenseUpdate);

            //trả về chi tiêu đã tạo
            resp.setStatus(HttpServletResponse.SC_CREATED); // HTTP 201
            resp.getWriter().write(gson.toJson(createdExpense));
        } catch (ExpenseException e) {
            System.out.println("Service error: " + e.getMessage());
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // HTTP 400 cho lỗi nghiệp vụ
            resp.getWriter().write(gson.toJson("Error: " + e.getMessage()));
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // HTTP 500
            resp.getWriter().write(gson.toJson("Unexpected error"));
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            // Lấy ID từ URL (ví dụ: /expenses/1 -> "1")
            String pathInfo = req.getPathInfo();
            if (pathInfo == null || pathInfo.length() <= 1) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write(gson.toJson("Error: Missing expense ID"));
                return;
            }
            String expenseId = pathInfo.substring(1); // Bỏ dấu "/" đầu tiên
            int id;
            try {
                id = Integer.parseInt(expenseId);
                if (id < 1) {
                    throw new NumberFormatException("Invalid ID");
                }
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write(gson.toJson("Error: Invalid expense ID"));
                return;
            }

            // Đọc JSON từ request body
            BufferedReader reader = req.getReader();
            Expense expenseUpdate = gson.fromJson(reader, Expense.class);
            // Gán ID từ URL vào expenseUpdate
            expenseUpdate.setId(id);

            // Validate dữ liệu đầu vào
            if (expenseUpdate.getAmount() <= 0 || expenseUpdate.getCategoryId() <= 0) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write(gson.toJson("Error: Invalid amount or category"));
                return;
            }

            // Gọi service để cập nhật chi tiêu
            Expense updatedExpense = expenseService.updateExpense(expenseUpdate);

            // Trả về chi tiêu đã cập nhật
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(gson.toJson(updatedExpense));
        } catch (ExpenseException e) {
            System.out.println("Service error: " + e.getMessage());
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(gson.toJson("Error: " + e.getMessage()));
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(gson.toJson("Unexpected error"));
        }

    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            // Lấy ID từ URL
            String pathInfo = req.getPathInfo();
            String expenseId = pathInfo.substring(1);
            int id = Integer.parseInt(expenseId); // Service sẽ xử lý lỗi ID không hợp lệ

            // Gọi service để xóa chi tiêu
            expenseService.deleteExpense(id);
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (ExpenseException e) {
            System.out.println("Service error: " + e.getMessage());
            resp.setStatus(e.getMessage().contains("not found") ?
                    HttpServletResponse.SC_NOT_FOUND : HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(gson.toJson("Error: " + e.getMessage()));
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(gson.toJson("Unexpected error"));
        }
    }
}