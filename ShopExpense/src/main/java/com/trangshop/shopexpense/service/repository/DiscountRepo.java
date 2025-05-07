package com.trangshop.shopexpense.service.repository;

import com.trangshop.shopexpense.model.Discount;

import java.util.List;

public interface DiscountRepo {
    List<Discount> findAll(int page, int pageSize, String status);
    int getTotalRecords(String status);
    void createDiscount(Discount discount);
    void updateDiscount(Discount discount);
    void deleteDiscount(int id);
    boolean isCodeExists(String code, Integer excludeId);

}
