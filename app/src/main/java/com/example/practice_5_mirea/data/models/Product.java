package com.example.practice_5_mirea.data.models;

import java.io.Serializable;

public class Product implements Serializable {
    private int id;

    private String goodName;
    private String goodAmount;

    public Product() {}

    public Product(String goodName, String goodAmount) {
        this.goodName = goodName;
        this.goodAmount = goodAmount;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public void setGoodAmount(String goodAmount) {
        this.goodAmount = goodAmount;
    }

    public String getGoodName() {
        return goodName;
    }

    public String getGoodAmount() {
        return goodAmount;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
