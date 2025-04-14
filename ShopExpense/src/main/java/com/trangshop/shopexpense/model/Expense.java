package com.trangshop.shopexpense.model;
import java.util.Date;

//lớp entity đại diện cho bảng expenses
public class Expense {
    private Integer id;
    private Double amount;
    private String description;
    private Integer categoryId;
    private Date createdAt;

    public Expense() {
    }

    public Expense(Integer id, Double amount, String description, Integer categoryId, Date createdAt) {
        this.id = id;
        this.amount = amount;
        this.description = description;
        this.categoryId = categoryId;
        this.createdAt = createdAt;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
