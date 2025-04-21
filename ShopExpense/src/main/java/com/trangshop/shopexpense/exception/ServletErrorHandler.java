package com.trangshop.shopexpense.exception;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
public class ServletErrorHandler {
    private static final Gson gson = new Gson();

    public static void handleException(HttpServletResponse resp, Exception e) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        if (e instanceof ExpenseException) {
            System.out.println("Service error: " + e.getMessage());
            resp.setStatus(e.getMessage().contains("not found") ?
                    HttpServletResponse.SC_NOT_FOUND : HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(gson.toJson("Error: " + e.getMessage()));
        } else {
            System.out.println("Unexpected error: " + e.getMessage());
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(gson.toJson("Unexpected error: " + e.getMessage()));
        }
    }
}
