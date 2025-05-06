package com.trangshop.shopexpense.service.repository;

import com.trangshop.shopexpense.model.Product;

import java.util.List;

public interface ProductRepo {
    List<Product> findAll(int page, int pageSize);
    int getTotalRecords();
    void addProduct(Product product);
    void updateProduct(Product product);
    void deleteProduct(int id);
    Product findById(int id);
}
