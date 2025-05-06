package com.trangshop.shopexpense.model;

public class RevenueByTime {
    private String timePeriod; // Ngày, tháng, hoặc năm
    private double totalRevenue;

    public RevenueByTime(String timePeriod, double totalRevenue) {
        this.timePeriod = timePeriod;
        this.totalRevenue = totalRevenue;
    }

    public String getTimePeriod() {
        return timePeriod;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }
}