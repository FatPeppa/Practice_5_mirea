package com.example.practice_5_mirea.data.repository;

import com.example.practice_5_mirea.data.models.Good;

public interface GoodRepository {
    String getCurrentGoodName();
    String getCurrentGoodAmount();
    void setCurrentGoodName(String goodName);

    void setCurrentGoodAmount(String goodAmount);
    Good getGood();
}
