package com.trangshop.shopexpense.service.repository.impl;

import com.trangshop.shopexpense.mapper.ProductMapper;
import com.trangshop.shopexpense.model.Product;
import com.trangshop.shopexpense.service.DatabaseConnectService;
import com.trangshop.shopexpense.service.impl.DatabaseConnectServiceImpl;
import com.trangshop.shopexpense.service.repository.ProductRepo;
import com.trangshop.shopexpense.service.repository.query.ProductQueries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductRepoImpl implements ProductRepo {
    private DatabaseConnectService databaseConnectService;
    private ProductQueries productQueries;
    private ProductMapper productMapper;


    public ProductRepoImpl() {
        this.databaseConnectService = new DatabaseConnectServiceImpl();
        this.productQueries = new ProductQueries();
        this.productMapper = new ProductMapper();
    }

    @Override
    public List<Product> findAll(int page, int pageSize) {
        List<Product> products = new ArrayList<>();
        int offset = (page - 1) * pageSize;
        try (Connection conn = databaseConnectService.getConnection();
             PreparedStatement stmt = conn.prepareStatement(productQueries.FIND_ALL_PRODUCT)) {
            stmt.setInt(1, pageSize);
            stmt.setInt(2, offset);
            try(ResultSet rs = stmt.executeQuery()){
            while (rs.next()) {
                Product product = new Product();
                products.add(productMapper.toProduct(product, rs));
            }}
        }catch (Exception e){
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public int getTotalRecords() {
        try (Connection conn = databaseConnectService.getConnection();
             PreparedStatement stmt = conn.prepareStatement(productQueries.COUNT_TOTAL_RECORD)) {
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public void addProduct(Product product) {
        try (Connection conn = databaseConnectService.getConnection();
             PreparedStatement stmt = conn.prepareStatement(productQueries.CREATE_NEW_PRODUCT)) {
            stmt.setString(1, product.getProductCode());
            stmt.setString(2, product.getName());
            stmt.setString(3, product.getDescription());
            stmt.setDouble(4, product.getPrice());
            stmt.setInt(5, product.getStockQuantity());
            stmt.setString(6, product.getSpecifications());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateProduct(Product product) {
        try (Connection conn = databaseConnectService.getConnection();
             PreparedStatement stmt = conn.prepareStatement(productQueries.UPDATE_PRODUCT)) {
            stmt.setString(1, product.getProductCode());
            stmt.setString(2, product.getName());
            stmt.setString(3, product.getDescription());
            stmt.setDouble(4, product.getPrice());
            stmt.setInt(5, product.getStockQuantity());
            stmt.setString(6, product.getSpecifications());
            stmt.setInt(7, product.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteProduct(int id) {
        try (Connection conn = databaseConnectService.getConnection();
             PreparedStatement stmt = conn.prepareStatement(productQueries.DELETE_PRODUCT)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Product findById(int id) {
        try (Connection conn = databaseConnectService.getConnection();
             PreparedStatement stmt = conn.prepareStatement(productQueries.FIND_PRODUCT_BY_ID)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Product(
                        rs.getInt("id"),
                        rs.getString("product_code"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("stock_quantity"),
                        rs.getString("specifications")
                );
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
