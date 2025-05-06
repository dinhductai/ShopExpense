package com.trangshop.shopexpense.service.repository.impl;

import com.trangshop.shopexpense.model.RevenueByCustomer;
import com.trangshop.shopexpense.model.RevenueByProduct;
import com.trangshop.shopexpense.model.RevenueByTime;
import com.trangshop.shopexpense.service.DatabaseConnectService;
import com.trangshop.shopexpense.service.impl.DatabaseConnectServiceImpl;
import com.trangshop.shopexpense.service.repository.RevenueRepo;
import com.trangshop.shopexpense.service.repository.query.RevenueQueries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RevenueRepoImpl implements RevenueRepo {

    private DatabaseConnectService databaseConnectService;
    private RevenueQueries revenueQueries;
    public RevenueRepoImpl() {
        this.databaseConnectService = new DatabaseConnectServiceImpl();
        this.revenueQueries = new RevenueQueries();
    }
    @Override
    public List<RevenueByTime> getRevenueByTime(String period, String startDate, String endDate) {
        List<RevenueByTime> revenues = new ArrayList<>();
        try (Connection conn = databaseConnectService.getConnection();
             PreparedStatement stmt = conn.prepareStatement(revenueQueries.GET_REVENUE_BY_TIME)) {
            String dateFormat;
            switch (period) {
                case "day":
                    dateFormat = "%Y-%m-%d";
                    break;
                case "month":
                    dateFormat = "%Y-%m";
                    break;
                case "year":
                    dateFormat = "%Y";
                    break;
                default:
                    return revenues;
            }
            stmt.setString(1, dateFormat);
            stmt.setString(2, startDate);
            stmt.setString(3, endDate);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                revenues.add(new RevenueByTime(
                        rs.getString("time_period"),
                        rs.getDouble("total_revenue")
                ));
            }
            return revenues;
        } catch (SQLException e) {
           e.printStackTrace();
           return null;
        }
    }

    @Override
    public List<RevenueByProduct> getRevenueByProduct(String startDate, String endDate) {
        List<RevenueByProduct> revenues = new ArrayList<>();
        try (Connection conn = databaseConnectService.getConnection();
             PreparedStatement stmt = conn.prepareStatement(revenueQueries.GET_REVENUE_BY_PRODUCT)) {
            stmt.setString(1, startDate);
            stmt.setString(2, endDate);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                revenues.add(new RevenueByProduct(
                        rs.getString("name"),
                        rs.getDouble("total_revenue")
                ));
            }
            return revenues;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public List<RevenueByCustomer> getRevenueByCustomer(String startDate, String endDate) {
        List<RevenueByCustomer> revenues = new ArrayList<>();
        try (Connection conn = databaseConnectService.getConnection();
             PreparedStatement stmt = conn.prepareStatement(revenueQueries.GET_REVENUE_BY_CUSTOMER)) {
            stmt.setString(1, startDate);
            stmt.setString(2, endDate);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                revenues.add(new RevenueByCustomer(
                        rs.getString("name"),
                        rs.getDouble("total_revenue")
                ));
            }
            return revenues;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
