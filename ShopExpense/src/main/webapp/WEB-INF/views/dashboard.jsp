<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Shopexpense - Admin Dashboard</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: Arial, sans-serif;
        }

        body {
            display: flex;
            min-height: 100vh;
        }

        /* Sidebar */
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

        /* Main Content */
        .main-content {
            margin-left: 250px;
            flex: 1;
            background-image: url('https://images.unsplash.com/photo-1516321318423-f06f85e504b3?q=80&w=2070&auto=format&fit=crop');
            background-size: cover;
            background-position: center;
            background-attachment: fixed;
            display: flex;
            justify-content: center;
            align-items: center;
            color: white;
            text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.7);
            padding: 20px;
        }

        .dashboard-text {
            text-align: center;
            max-width: 600px;
            background-color: rgba(0, 0, 0, 0.5);
            padding: 30px;
            border-radius: 10px;
        }

        .dashboard-text h1 {
            font-size: 36px;
            margin-bottom: 20px;
        }

        .dashboard-text p {
            font-size: 18px;
            line-height: 1.6;
        }
    </style>
</head>
<body>
<%
    // Kiểm tra session và vai trò
    String role = (String) session.getAttribute("role");
    if (session.getAttribute("user") == null || !"MANAGER".equals(role)) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!-- Sidebar -->
<div class="sidebar">
    <h2>Shopexpense Admin</h2>
    <a href="products">Manage Products</a>
    <a href="discounts">Manage Discounts</a>
    <a href="customers">Manage Customers</a>
    <a href="orders">Manage Orders</a>
    <a href="order-details">Manage Order Details</a>
    <a href="users">Manage Users</a>
    <a href="logout">Logout</a>
</div>

<!-- Main Content -->
<div class="main-content">
    <div class="dashboard-text">
        <h1>Welcome to Shopexpense</h1>
        <p>
            Shopexpense is your one-stop destination for cutting-edge technology products!
            Explore our wide range of smartphones, accessories, and more.
            As an admin, you can manage products, discounts, customers, orders, and users
            with ease from this dashboard.
        </p>
    </div>
</div>
</body>
</html>