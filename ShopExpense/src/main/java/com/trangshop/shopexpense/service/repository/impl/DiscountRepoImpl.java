package com.trangshop.shopexpense.service.repository.impl;

import com.trangshop.shopexpense.model.Discount;
import com.trangshop.shopexpense.service.DatabaseConnectService;
import com.trangshop.shopexpense.service.impl.DatabaseConnectServiceImpl;
import com.trangshop.shopexpense.service.repository.DiscountRepo;
import com.trangshop.shopexpense.service.repository.query.DiscountQueries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DiscountRepoImpl implements DiscountRepo {
    private DatabaseConnectService databaseConnectService;
    private DiscountQueries discountQueries;

    public DiscountRepoImpl() {
        this.discountQueries =  new DiscountQueries();
        this.databaseConnectService = new DatabaseConnectServiceImpl();
    }

    @Override
    public List<Discount> findAll(int page, int pageSize, String status) {
        List<Discount> discounts = new ArrayList<>();
        try (Connection conn = databaseConnectService.getConnection()) {
            int offset = (page - 1) * pageSize;
            String sql = "SELECT * FROM discounts WHERE 1=1";
            if (status != null && !status.isEmpty()) {
                sql += " AND status = ?";
            }
            sql += " LIMIT ? OFFSET ?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            int paramIndex = 1;
            if (status != null && !status.isEmpty()) {
                stmt.setString(paramIndex++, status);
            }
            stmt.setInt(paramIndex++, pageSize);
            stmt.setInt(paramIndex++, offset);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                discounts.add(new Discount(
                        rs.getInt("id"),
                        rs.getString("code"),
                        rs.getDouble("discount_value"),
                        rs.getString("discount_type"),
                        rs.getTimestamp("start_date").toLocalDateTime(),
                        rs.getTimestamp("end_date").toLocalDateTime(),
                        rs.getString("status")
                ));
            }
            return discounts;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public int getTotalRecords(String status) {
        try (Connection conn = databaseConnectService.getConnection()) {
            String sql = "SELECT COUNT(*) FROM discounts WHERE 1=1";
            if (status != null && !status.isEmpty()) {
                sql += " AND status = ?";
            }

            PreparedStatement stmt = conn.prepareStatement(sql);
            int paramIndex = 1;
            if (status != null && !status.isEmpty()) {
                stmt.setString(paramIndex++, status);
            }

            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    @Override
    public void createDiscount(Discount discount) {
        try (Connection conn = databaseConnectService.getConnection();
             PreparedStatement stmt = conn.prepareStatement(discountQueries.CREATE_DISCOUNT)) {
                stmt.setString(1, discount.getCode());
                stmt.setDouble(2, discount.getDiscountValue());
                stmt.setString(3, discount.getDiscountType());
                stmt.setTimestamp(4, java.sql.Timestamp.valueOf(discount.getStartDate()));
                stmt.setTimestamp(5, java.sql.Timestamp.valueOf(discount.getEndDate()));
                stmt.setString(6, discount.getStatus());
                stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void updateDiscount(Discount discount){
        try (Connection conn = databaseConnectService.getConnection();
             PreparedStatement stmt = conn.prepareStatement(discountQueries.UPDATE_DISCOUNT)) {
                stmt.setString(1, discount.getCode());
                stmt.setDouble(2, discount.getDiscountValue());
                stmt.setString(3, discount.getDiscountType());
                stmt.setTimestamp(4, java.sql.Timestamp.valueOf(discount.getStartDate()));
                stmt.setTimestamp(5, java.sql.Timestamp.valueOf(discount.getEndDate()));
                stmt.setString(6, discount.getStatus());
                stmt.setInt(7, discount.getId());
                int rowsUpdated = stmt.executeUpdate();
                if (rowsUpdated == 0) {
                    throw new SQLException("Discount with ID " + discount.getId() + " not found.");
                }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void deleteDiscount(int id)  {
        try (Connection conn = databaseConnectService.getConnection();
             PreparedStatement stmt = conn.prepareStatement(discountQueries.DELETE_DISCOUNT_BY_ID);
             PreparedStatement checkStmt = conn.prepareStatement(discountQueries.CHECK_DISCOUNT_USING_BY_ID)) {
                checkStmt.setInt(1, id);
                ResultSet rs = checkStmt.executeQuery();
                rs.next();
                if (rs.getInt(1) > 0) {
                    throw new SQLException("Cannot delete discount used in orders.");
                }
                stmt.setInt(1, id);
                int rowsDeleted = stmt.executeUpdate();
                if (rowsDeleted == 0) {
                    throw new SQLException("Discount with ID " + id + " not found.");
                }
            } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isCodeExists(String code, Integer excludeId) {
        try (Connection conn = databaseConnectService.getConnection();
             PreparedStatement stmt = conn.prepareStatement(discountQueries.CHECK_CODE_EXISTING)){
            stmt.setString(1, code);
            stmt.setInt(2, excludeId != null ? excludeId : 0);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
