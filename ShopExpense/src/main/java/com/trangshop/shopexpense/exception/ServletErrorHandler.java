package com.trangshop.shopexpense.exception;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
//tránh lặp lại code xử lý status code ở tầng servlet
public class ServletErrorHandler {
    private static final Gson gson = new Gson();

    //xử lý lỗi trả ra bad request status code hoặc internal server status code
    public static void handleException(HttpServletResponse resp, Exception e) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        if (e instanceof ExpenseException) {
            System.out.println("Service error: " + e.getMessage());

            resp.setStatus(e.getMessage().contains("not found") ?
                    //
                    HttpServletResponse.SC_NOT_FOUND : HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(gson.toJson("Error: " + e.getMessage()));
        } else {
            System.out.println("Unexpected error: " + e.getMessage());
            //500 status code
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(gson.toJson("Unexpected error: " + e.getMessage()));
        }
    }
}
