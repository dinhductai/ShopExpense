<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ShopExpense - Discount Manager</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body class="bg-light">
<div class="container mt-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h1 class="text-primary">Discount Manager</h1>
        <div>
            <a href="${pageContext.request.contextPath}/products" class="btn btn-info me-2">View Products</a>
            <a href="${pageContext.request.contextPath}/customers" class="btn btn-info me-2">View Customers</a>
            <a href="${pageContext.request.contextPath}/orders" class="btn btn-info me-2">View Orders</a>
            <a href="${pageContext.request.contextPath}/logout" class="btn btn-danger">Logout</a>
        </div>
    </div>
    <c:if test="${not empty error}">
        <div class="alert alert-danger" role="alert">${error}</div>
        <c:remove var="error" scope="session"/>
    </c:if>
    <c:if test="${not empty success}">
        <div class="alert alert-success" role="alert">${success}</div>
        <c:remove var="success" scope="session"/>
    </c:if>
    <div class="card shadow mb-4">
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/discounts" method="GET" class="row g-3">
                <div class="col-md-3">
                    <label for="status" class="form-label">Status</label>
                    <select class="form-select" id="status" name="status">
                        <option value="">All</option>
                        <option value="ACTIVE" ${statusFilter == 'ACTIVE' ? 'selected' : ''}>Active</option>
                        <option value="EXPIRED" ${statusFilter == 'EXPIRED' ? 'selected' : ''}>Expired</option>
                    </select>
                </div>
                <div class="col-md-3 d-flex align-items-end">
                    <button type="submit" class="btn btn-primary w-100">Filter</button>
                </div>
                <div class="col-md-6 text-end">
                    <a href="${pageContext.request.contextPath}/discounts?action=create" class="btn btn-success">Create New Discount</a>
                </div>
            </form>
        </div>
    </div>
    <div class="card shadow">
        <div class="card-body">
            <div class="table-responsive">
                <table class="table table-bordered table-hover">
                    <thead class="table-primary">
                    <tr>
                        <th>Code</th>
                        <th>Discount Value</th>
                        <th>Type</th>
                        <th>Start Date</th>
                        <th>End Date</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="discount" items="${discounts}">
                        <tr>
                            <td>${discount.code}</td>
                            <td>${discount.discountValue}${discount.discountType == 'PERCENTAGE' ? '%' : '$'}</td>
                            <td>${discount.discountType}</td>
                            <td>${discount.startDate}</td>
                            <td>${discount.endDate}</td>
                            <td>${discount.status}</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/discounts?action=edit&id=${discount.id}" class="btn btn-warning btn-sm me-1">Edit</a>
                                <a href="${pageContext.request.contextPath}/discounts?action=delete&id=${discount.id}" class="btn btn-danger btn-sm" onclick="return confirm('Are you sure you want to delete this discount?')">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="d-flex justify-content-between align-items-center mt-4">
                <a href="${pageContext.request.contextPath}/discounts?page=${currentPage - 1}&status=${statusFilter}" class="btn btn-primary ${currentPage == 1 ? 'disabled' : ''}">Previous</a>
                <span>Page ${currentPage} of ${totalPages}</span>
                <a href="${pageContext.request.contextPath}/discounts?page=${currentPage + 1}&status=${statusFilter}" class="btn btn-primary ${currentPage == totalPages ? 'disabled' : ''}">Next</a>
            </div>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>