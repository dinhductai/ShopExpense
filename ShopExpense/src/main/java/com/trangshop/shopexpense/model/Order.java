package com.trangshop.shopexpense.model;
import java.time.LocalDateTime;

public class Order {
    private int id;
    private String orderCode;
    private Integer customerId;
    private Integer userId;
    private Integer discountId;
    private double totalAmount;
    private String status;
    private LocalDateTime orderDate;

    public Order(int id, String orderCode, Integer customerId, Integer userId, Integer discountId, double totalAmount, String status, LocalDateTime orderDate) {
        this.id = id;
        this.orderCode = orderCode;
        this.customerId = customerId;
        this.userId = userId;
        this.discountId = discountId;
        this.totalAmount = totalAmount;
        this.status = status;
        this.orderDate = orderDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Integer discountId) {
        this.discountId = discountId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }
}