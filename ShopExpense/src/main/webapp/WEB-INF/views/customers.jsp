<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ShopExpense - Customer Manager</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body class="bg-light">
<div class="container mt-5">
    <h1 class="text-primary mb-4">Customer Manager</h1>
    <div class="card shadow">
        <div class="card-body">
            <div class="d-flex justify-content-end mb-3">
                <a href="${pageContext.request.contextPath}/customers?action=add" class="btn btn-success">Add New Customer</a>
            </div>
            <div class="table-responsive">
                <table class="table table-bordered table-hover">
                    <thead class="table-primary">
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Address</th>
                        <th>Phone</th>
                        <th>Email</th>
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="customer" items="${customers}">
                        <tr>
                            <td>${customer.id}</td>
                            <td>${customer.name}</td>
                            <td>${customer.address}</td>
                            <td>${customer.phone}</td>
                            <td>${customer.email}</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/customers?action=edit&id=${customer.id}" class="btn btn-warning btn-sm me-1">Edit</a>
                                <a href="${pageContext.request.contextPath}/customers?action=delete&id=${customer.id}" class="btn btn-danger btn-sm" onclick="return confirm('Are you sure you want to delete this customer?')">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="d-flex justify-content-between align-items-center mt-4">
                <a href="${pageContext.request.contextPath}/customers?page=${currentPage - 1}" class="btn btn-primary ${currentPage == 1 ? 'disabled' : ''}">Previous</a>
                <span>Page ${currentPage} of ${totalPages}</span>
                <a href="${pageContext.request.contextPath}/customers?page=${currentPage + 1}" class="btn btn-primary ${currentPage == totalPages ? 'disabled' : ''}">Next</a>
            </div>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>