package com.trangshop.shopexpense.model;

public class RevenueByProduct {
    private String productName;
    private double totalRevenue;

    public RevenueByProduct(String productName, double totalRevenue) {
        this.productName = productName;
        this.totalRevenue = totalRevenue;
    }

    public String getProductName() {
        return productName;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }
}