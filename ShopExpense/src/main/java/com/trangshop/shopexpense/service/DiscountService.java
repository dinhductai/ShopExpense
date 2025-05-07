package com.trangshop.shopexpense.service;

import com.trangshop.shopexpense.model.Discount;

import java.util.List;

public interface DiscountService {
    List<Discount> getDiscounts(int page, int pageSize, String status);
    int getTotalPages(int pageSize, String status);
    void createDiscount(Discount discount);
    void updateDiscount(Discount discount);
    void deleteDiscount(int id);
    boolean isActive(Discount discount);
}
