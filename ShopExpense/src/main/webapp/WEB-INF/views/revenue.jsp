<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ShopExpense - Revenue Manager</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body class="bg-light">
<div class="container mt-5">
    <h1 class="text-primary mb-4">Revenue Manager</h1>
    <div class="card shadow mb-4">
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/revenue" method="POST" class="row g-3">
                <div class="col-md-5">
                    <label for="startDate" class="form-label">Start Date</label>
                    <input type="date" class="form-control" id="startDate" name="startDate" value="${startDate}" required>
                </div>
                <div class="col-md-5">
                    <label for="endDate" class="form-label">End Date</label>
                    <input type="date" class="form-control" id="endDate" name="endDate" value="${endDate}" required>
                </div>
                <div class="col-md-2 d-flex align-items-end">
                    <button type="submit" class="btn btn-primary w-100">Filter</button>
                </div>
            </form>
        </div>
    </div>
    <ul class="nav nav-tabs mb-4" id="revenueTab" role="tablist">
        <li class="nav-item" role="presentation">
            <button class="nav-link active" id="by-time-tab" data-bs-toggle="tab" data-bs-target="#by-time" type="button" role="tab">By Time</button>
        </li>
        <li class="nav-item" role="presentation">
            <button class="nav-link" id="by-product-tab" data-bs-toggle="tab" data-bs-target="#by-product" type="button" role="tab">By Product</button>
        </li>
        <li class="nav-item" role="presentation">
            <button class="nav-link" id="by-customer-tab" data-bs-toggle="tab" data-bs-target="#by-customer" type="button" role="tab">By Customer</button>
        </li>
    </ul>
    <div class="tab-content" id="revenueTabContent">
        <div class="tab-pane fade show active" id="by-time" role="tabpanel">
            <div class="card shadow">
                <div class="card-body">
                    <h3>Revenue by Time</h3>
                    <h5>By Day</h5>
                    <c:if test="${empty revenueByDay}">
                        <div class="alert alert-info">No revenue data available for this period.</div>
                    </c:if>
                    <c:if test="${not empty revenueByDay}">
                        <div class="table-responsive">
                            <table class="table table-bordered table-hover">
                                <thead class="table-primary">
                                <tr>
                                    <th>Date</th>
                                    <th>Total Revenue</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="revenue" items="${revenueByDay}">
                                    <tr>
                                        <td>${revenue.timePeriod}</td>
                                        <td>$${revenue.totalRevenue}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </c:if>
                    <h5>By Month</h5>
                    <c:if test="${empty revenueByMonth}">
                        <div class="alert alert-info">No revenue data available for this period.</div>
                    </c:if>
                    <c:if test="${not empty revenueByMonth}">
                        <div class="table-responsive">
                            <table class="table table-bordered table-hover">
                                <thead class="table-primary">
                                <tr>
                                    <th>Month</th>
                                    <th>Total Revenue</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="revenue" items="${revenueByMonth}">
                                    <tr>
                                        <td>${revenue.timePeriod}</td>
                                        <td>$${revenue.totalRevenue}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </c:if>
                    <h5>By Year</h5>
                    <c:if test="${empty revenueByYear}">
                        <div class="alert alert-info">No revenue data available for this period.</div>
                    </c:if>
                    <c:if test="${not empty revenueByYear}">
                        <div class="table-responsive">
                            <table class="table table-bordered table-hover">
                                <thead class="table-primary">
                                <tr>
                                    <th>Year</th>
                                    <th>Total Revenue</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="revenue" items="${revenueByYear}">
                                    <tr>
                                        <td>${revenue.timePeriod}</td>
                                        <td>$${revenue.totalRevenue}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
        <div class="tab-pane fade" id="by-product" role="tabpanel">
            <div class="card shadow">
                <div class="card-body">
                    <h3>Revenue by Product</h3>
                    <c:if test="${empty revenueByProduct}">
                        <div class="alert alert-info">No revenue data available for this period.</div>
                    </c:if>
                    <c:if test="${not empty revenueByProduct}">
                        <div class="table-responsive">
                            <table class="table table-bordered table-hover">
                                <thead class="table-primary">
                                <tr>
                                    <th>Product Name</th>
                                    <th>Total Revenue</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="revenue" items="${revenueByProduct}">
                                    <tr>
                                        <td>${revenue.productName}</td>
                                        <td>$${revenue.totalRevenue}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
        <div class="tab-pane fade" id="by-customer" role="tabpanel">
            <div class="card shadow">
                <div class="card-body">
                    <h3>Revenue by Customer</h3>
                    <c:if test="${empty revenueByCustomer}">
                        <div class="alert alert-info">No revenue data available for this period.</div>
                    </c:if>
                    <c:if test="${not empty revenueByCustomer}">
                        <div class="table-responsive">
                            <table class="table table-bordered table-hover">
                                <thead class="table-primary">
                                <tr>
                                    <th>Customer Name</th>
                                    <th>Total Revenue</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="revenue" items="${revenueByCustomer}">
                                    <tr>
                                        <td>${revenue.customerName}</td>
                                        <td>$${revenue.totalRevenue}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>