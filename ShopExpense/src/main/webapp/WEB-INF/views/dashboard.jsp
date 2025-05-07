<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ShopExpense - Admin Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: Arial, sans-serif;
        }

        body {
            min-height: 100vh;
        }

        .sidebar {
            width: 250px;
            background-color: #2c3e50;
            color: white;
            position: fixed;
            height: 100%;
            padding-top: 20px;
        }

        .sidebar h2 {
            text-align: center;
            margin-bottom: 30px;
            font-size: 24px;
        }

        .sidebar a {
            display: block;
            color: white;
            padding: 15px 20px;
            text-decoration: none;
            font-size: 18px;
            transition: background 0.3s;
        }

        .sidebar a:hover {
            background-color: #34495e;
        }

        .main-content {
            margin-left: 250px;
            padding: 20px;
            min-height: 100vh;
            background-image: url('https://images.unsplash.com/photo-1516321318423-f06f85e504b3?q=80&w=2070&auto=format&fit=crop');
            background-size: cover;
            background-position: center;
            background-attachment: fixed;
            display: flex;
            justify-content: center;
            align-items: center;
            color: white;
            text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.7);
        }

        .footer {
            position: fixed;
            bottom: 0;
            width: calc(100% - 250px);
            margin-left: 250px;
            background-color: rgba(0, 0, 0, 0.7);
            color: white;
            text-align: center;
            padding: 10px 0;
        }
    </style>
</head>
<body>
<%
    String role = (String) session.getAttribute("role");
    if (session.getAttribute("user") == null || !"ADMIN".equals(role)) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!-- Sidebar -->
<div class="sidebar">
    <h2>ShopExpense Admin</h2>
    <a href="${pageContext.request.contextPath}/products" class="btn btn-link text-white w-100 text-start">Manage Products</a>
    <a href="${pageContext.request.contextPath}/discounts" class="btn btn-link text-white w-100 text-start">Manage Discounts</a>
    <a href="${pageContext.request.contextPath}/customers" class="btn btn-link text-white w-100 text-start">Manage Users</a>
    <a href="${pageContext.request.contextPath}/orders" class="btn btn-link text-white w-100 text-start">Manage Orders</a>
    <a href="${pageContext.request.contextPath}/revenue" class="btn btn-link text-white w-100 text-start">Manage Revenue</a>
    <a href="${pageContext.request.contextPath}/api/logout" class="btn btn-link text-white w-100 text-start">Logout</a>
</div>

<!-- Main Content -->
<div class="main-content">
    <!-- Không có nội dung chi tiết, chỉ nền ảnh -->
</div>

<!-- Footer -->
<div class="footer">
    <p>© 2025 ShopExpense. All rights reserved. Contact us at support@shopexpense.com</p>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>