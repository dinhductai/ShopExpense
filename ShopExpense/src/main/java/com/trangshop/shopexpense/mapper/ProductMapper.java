package com.trangshop.shopexpense.mapper;

import com.trangshop.shopexpense.model.Product;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductMapper {
    public Product toProduct(Product product, ResultSet rs){
        try{
            product.setId(rs.getInt("id"));
            product.setProductCode(rs.getString("product_code"));
            product.setName(rs.getString("name"));
            product.setDescription(rs.getString("description"));
            product.setPrice(rs.getDouble("price"));
            product.setStockQuantity(rs.getInt("stock_quantity"));
            product.setSpecifications(rs.getString("specifications"));
            return product;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
