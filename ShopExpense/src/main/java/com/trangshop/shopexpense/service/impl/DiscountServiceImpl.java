package com.trangshop.shopexpense.service.impl;

import com.trangshop.shopexpense.model.Discount;
import com.trangshop.shopexpense.service.DiscountService;
import com.trangshop.shopexpense.service.repository.DiscountRepo;
import com.trangshop.shopexpense.service.repository.impl.DiscountRepoImpl;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class DiscountServiceImpl implements DiscountService {
    private DiscountRepo discountRepo;

    public DiscountServiceImpl() {
        this.discountRepo = new DiscountRepoImpl();
    }

    public List<Discount> getDiscounts(int page, int pageSize, String status) {
        return discountRepo.findAll(page, pageSize, status);
    }

    public int getTotalPages(int pageSize, String status) {
        int totalRecords = discountRepo.getTotalRecords(status);
        return (int) Math.ceil((double) totalRecords / pageSize);
    }

    public void createDiscount(Discount discount) throws IllegalArgumentException {
        if (discount.getDiscountValue() < 0) {
            throw new IllegalArgumentException("Discount value must be greater than or equal to 0.");
        }
        if ("PERCENTAGE".equals(discount.getDiscountType()) && discount.getDiscountValue() > 100) {
            throw new IllegalArgumentException("Discount percentage must not exceed 100.");
        }
        if (discount.getStartDate().isAfter(discount.getEndDate())) {
            throw new IllegalArgumentException("Start date must be before end date.");
        }
        if (discountRepo.isCodeExists(discount.getCode(), null)) {
            throw new IllegalArgumentException("Discount code already exists.");
        }
        discount.setStatus(isActive(discount) ? "ACTIVE" : "EXPIRED");
            discountRepo.createDiscount(discount);

    }

    public void updateDiscount(Discount discount) throws IllegalArgumentException {
        if (discount.getDiscountValue() < 0) {
            throw new IllegalArgumentException("Discount value must be greater than or equal to 0.");
        }
        if ("PERCENTAGE".equals(discount.getDiscountType()) && discount.getDiscountValue() > 100) {
            throw new IllegalArgumentException("Discount percentage must not exceed 100.");
        }
        if (discount.getStartDate().isAfter(discount.getEndDate())) {
            throw new IllegalArgumentException("Start date must be before end date.");
        }
        if (discountRepo.isCodeExists(discount.getCode(), discount.getId())) {
            throw new IllegalArgumentException("Discount code already exists.");
        }
        discount.setStatus(isActive(discount) ? "ACTIVE" : "EXPIRED");
            discountRepo.updateDiscount(discount);

    }

    public void deleteDiscount(int id) throws IllegalArgumentException {
            discountRepo.deleteDiscount(id);
    }

    public boolean isActive(Discount discount) {
        LocalDateTime now = LocalDateTime.now();
        return !now.isBefore(discount.getStartDate()) && !now.isAfter(discount.getEndDate());
    }
}
