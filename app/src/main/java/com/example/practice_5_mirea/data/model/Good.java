package com.example.practice_5_mirea.data.model;

import java.io.Serializable;

public class Good implements Serializable {
    private String goodName;
    private String goodAmount;

    public Good() {}

    public Good(String goodName, String goodAmount) {
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
}
