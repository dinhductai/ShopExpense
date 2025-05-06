package com.trangshop.shopexpense.service.impl;

import com.trangshop.shopexpense.model.Product;
import com.trangshop.shopexpense.service.ProductService;
import com.trangshop.shopexpense.service.repository.ProductRepo;
import com.trangshop.shopexpense.service.repository.impl.ProductRepoImpl;

import java.util.List;

public class ProductServiceImpl implements ProductService {
    private ProductRepo productRepo;

    public ProductServiceImpl() {
        this.productRepo = new ProductRepoImpl();
    }

    public List<Product> getProducts(int page, int pageSize) {
        return productRepo.findAll(page, pageSize);
    }

    public int getTotalPages(int pageSize) {
        int totalRecords = productRepo.getTotalRecords();
        return (int) Math.ceil((double) totalRecords / pageSize);
    }

    public void addProduct(Product product) {
        productRepo.addProduct(product);
    }

    public void updateProduct(Product product) {
        productRepo.updateProduct(product);
    }

    public void deleteProduct(int id) {
        productRepo.deleteProduct(id);
    }

    public Product getProductById(int id) {
        return productRepo.findById(id);
    }
}
