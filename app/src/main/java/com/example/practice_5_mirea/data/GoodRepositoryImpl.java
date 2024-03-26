package com.example.practice_5_mirea.data;

import java.io.Serializable;

public class GoodRepositoryImpl implements GoodRepository, Serializable {
    private String goodName;
    private String goodAmount;

    public GoodRepositoryImpl() {}
    public GoodRepositoryImpl(String goodName, String goodAmount) {
        this.goodName = goodName;
        this.goodAmount = goodAmount;
    }

    public String getGoodName() {
        return goodName;
    }

    public String getGoodAmount() {
        return goodAmount;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public void setGoodAmount(String goodAmount) {
        this.goodAmount = goodAmount;
    }
}

