<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ShopExpense - Order Manager</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body class="bg-light">
<div class="container mt-5">
    <h1 class="text-primary mb-4">Order Manager</h1>
    <div class="card shadow mb-4">
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/orders" method="POST" class="row g-3">
                <div class="col-md-3">
                    <label for="status" class="form-label">Status</label>
                    <select class="form-select" id="status" name="status">
                        <option value="">All</option>
                        <option value="PENDING" ${statusFilter == 'PENDING' ? 'selected' : ''}>Pending</option>
                        <option value="SHIPPED" ${statusFilter == 'SHIPPED' ? 'selected' : ''}>Shipped</option>
                        <option value="DELIVERED" ${statusFilter == 'DELIVERED' ? 'selected' : ''}>Delivered</option>
                        <option value="CANCELLED" ${statusFilter == 'CANCELLED' ? 'selected' : ''}>Cancelled</option>
                    </select>
                </div>
                <div class="col-md-3">
                    <label for="startDate" class="form-label">Start Date</label>
                    <input type="date" class="form-control" id="startDate" name="startDate" value="${startDate}" required>
                </div>
                <div class="col-md-3">
                    <label for="endDate" class="form-label">End Date</label>
                    <input type="date" class="form-control" id="endDate" name="endDate" value="${endDate}" required>
                </div>
                <div class="col-md-3 d-flex align-items-end">
                    <button type="submit" class="btn btn-primary w-100">Filter</button>
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
                        <th>Order Code</th>
                        <th>Customer</th>
                        <th>Total Amount</th>
                        <th>Status</th>
                        <th>Order Date</th>
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="order" items="${orders}">
                        <c:set var="customer" value="${order.customerId != null ? customerService.getCustomerById(order.customerId).name : 'Unknown'}" />
                        <tr>
                            <td>${order.orderCode}</td>
                            <td>${customer}</td>
                            <td>$${order.totalAmount}</td>
                            <td>${order.status}</td>
                            <td>${order.orderDate}</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/orders?action=detail&id=${order.id}" class="btn btn-info btn-sm me-1">Detail</a>
                                <c:if test="${order.status != 'DELIVERED' && order.status != 'CANCELLED'}">
                                    <a href="${pageContext.request.contextPath}/orders?action=update&id=${order.id}&status=SHIPPED" class="btn btn-warning btn-sm me-1">Ship</a>
                                    <a href="${pageContext.request.contextPath}/orders?action=update&id=${order.id}&status=DELIVERED" class="btn btn-success btn-sm me-1">Deliver</a>
                                    <a href="${pageContext.request.contextPath}/orders?action=update&id=${order.id}&status=CANCELLED" class="btn btn-danger btn-sm">Cancel</a>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="d-flex justify-content-between align-items-center mt-4">
                <a href="${pageContext.request.contextPath}/orders?page=${currentPage - 1}&status=${statusFilter}&startDate=${startDate}&endDate=${endDate}" class="btn btn-primary ${currentPage == 1 ? 'disabled' : ''}">Previous</a>
                <span>Page ${currentPage} of ${totalPages}</span>
                <a href="${pageContext.request.contextPath}/orders?page=${currentPage + 1}&status=${statusFilter}&startDate=${startDate}&endDate=${endDate}" class="btn btn-primary ${currentPage == totalPages ? 'disabled' : ''}">Next</a>
            </div>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>