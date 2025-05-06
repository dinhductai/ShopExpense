package com.trangshop.shopexpense.model;

public class RevenueByCustomer {
    private String customerName;
    private double totalRevenue;

    public RevenueByCustomer(String customerName, double totalRevenue) {
        this.customerName = customerName;
        this.totalRevenue = totalRevenue;
    }

    public String getCustomerName() {
        return customerName;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }
}