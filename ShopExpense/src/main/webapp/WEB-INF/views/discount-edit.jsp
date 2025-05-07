<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ShopExpense - Edit Discount</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body class="bg-light">
<div class="container mt-5">
    <h1 class="text-primary mb-4">Edit Discount</h1>
    <div class="card shadow">
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/discounts" method="POST">
                <input type="hidden" name="action" value="update">
                <input type="hidden" name="id" value="${discount.id}">
                <div class="mb-3">
                    <label for="code" class="form-label">Code</label>
                    <input type="text" class="form-control" id="code" name="code" value="${discount.code}" required>
                </div>
                <div class="mb-3">
                    <label for="discountValue" class="form-label">Discount Value</label>
                    <input type="number" step="0.01" class="form-control" id="discountValue" name="discountValue" value="${discount.discountValue}" required>
                </div>
                <div class="mb-3">
                    <label for="discountType" class="form-label">Discount Type</label>
                    <select class="form-select" id="discountType" name="discountType" required>
                        <option value="PERCENTAGE" ${discount.discountType == 'PERCENTAGE' ? 'selected' : ''}>Percentage (%)</option>
                        <option value="FIXED_AMOUNT" ${discount.discountType == 'FIXED_AMOUNT' ? 'selected' : ''}>Fixed Amount ($)</option>
                    </select>
                </div>
                <div class="mb-3">
                    <label for="startDate" class="form-label">Start Date</label>
                    <input type="date" class="form-control" id="startDate" name="startDate" value="${discount.startDate.toLocalDate()}" required>
                </div>
                <div class="mb-3">
                    <label for="endDate" class="form-label">End Date</label>
                    <input type="date" class="form-control" id="endDate" name="endDate" value="${discount.endDate.toLocalDate()}" required>
                </div>
                <button type="submit" class="btn btn-primary">Update</button>
                <a href="${pageContext.request.contextPath}/discounts" class="btn btn-secondary">Cancel</a>
            </form>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>