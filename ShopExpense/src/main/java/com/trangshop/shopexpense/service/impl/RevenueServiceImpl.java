package com.trangshop.shopexpense.service.impl;

import com.trangshop.shopexpense.model.RevenueByCustomer;
import com.trangshop.shopexpense.model.RevenueByProduct;
import com.trangshop.shopexpense.model.RevenueByTime;
import com.trangshop.shopexpense.service.RevenueService;
import com.trangshop.shopexpense.service.repository.RevenueRepo;
import com.trangshop.shopexpense.service.repository.impl.RevenueRepoImpl;

import java.util.List;

public class RevenueServiceImpl implements RevenueService {
    private RevenueRepo revenueRepo;

    public RevenueServiceImpl() {
        this.revenueRepo = new RevenueRepoImpl();
    }
    @Override
    public List<RevenueByTime> getRevenueByTime(String period, String startDate, String endDate) {
        return revenueRepo.getRevenueByTime(period, startDate, endDate);
    }
    @Override
    public List<RevenueByProduct> getRevenueByProduct(String startDate, String endDate) {
        return revenueRepo.getRevenueByProduct(startDate, endDate);
    }
    @Override
    public List<RevenueByCustomer> getRevenueByCustomer(String startDate, String endDate) {
        return revenueRepo.getRevenueByCustomer(startDate, endDate);
    }
}
