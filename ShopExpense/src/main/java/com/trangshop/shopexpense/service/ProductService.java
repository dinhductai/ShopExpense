package com.trangshop.shopexpense.service;

import com.trangshop.shopexpense.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> getProducts(int page, int pageSize);
    int getTotalPages(int pageSize);
    void addProduct(Product product);
    void updateProduct(Product product);
    void deleteProduct(int id);
    Product getProductById(int id);
}
