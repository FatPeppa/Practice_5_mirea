package com.example.practice_5_mirea.data;

public interface GoodRepository {
    String goodName = null;
    String goodAmount = null;

    String getGoodName();
    String getGoodAmount();
    void setGoodName(String goodName);
    void setGoodAmount(String goodAmount);
}
