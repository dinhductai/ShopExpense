package com.trangshop.shopexpense.service.repository;

import com.trangshop.shopexpense.model.RevenueByCustomer;
import com.trangshop.shopexpense.model.RevenueByProduct;
import com.trangshop.shopexpense.model.RevenueByTime;

import java.util.List;

public interface RevenueRepo {
    List<RevenueByTime> getRevenueByTime(String period, String startDate, String endDate);
    List<RevenueByProduct> getRevenueByProduct(String startDate, String endDate);
    List<RevenueByCustomer> getRevenueByCustomer(String startDate, String endDate);
}
