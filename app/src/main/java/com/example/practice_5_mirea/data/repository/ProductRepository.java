package com.example.practice_5_mirea.data.repository;

import android.content.Context;

import com.example.practice_5_mirea.data.models.Product;

public interface ProductRepository {
    String getCurrentGoodName();
    String getCurrentGoodAmount();
    void setCurrentGoodName(String goodName);

    void setCurrentGoodAmount(String goodAmount);
    Product getGood();

    void createCurrentInfoKeeper(Context context);
}
