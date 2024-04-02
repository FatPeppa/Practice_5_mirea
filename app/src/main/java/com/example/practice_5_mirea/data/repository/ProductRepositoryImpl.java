package com.example.practice_5_mirea.data.repository;

import android.content.Context;

import com.example.practice_5_mirea.data.dataSource.CurrentGoodKeeper;
import com.example.practice_5_mirea.data.dataSource.SP.SharedPreferencesDataSource;
import com.example.practice_5_mirea.data.models.Product;

import java.io.Serializable;

public class ProductRepositoryImpl implements ProductRepository, Serializable {
    private SharedPreferencesDataSource currentProductInfoKeeper;
    public ProductRepositoryImpl() {}

    public void createCurrentInfoKeeper(Context context) {
        if (currentProductInfoKeeper == null)
            currentProductInfoKeeper = new SharedPreferencesDataSource(context);
    }

    public String getCurrentGoodName() {
        if (currentProductInfoKeeper == null) return null;
        else return currentProductInfoKeeper.getStringRecord("R.string.GoodName");
    }

    public String getCurrentGoodAmount() {
        if (currentProductInfoKeeper == null) return null;
        else return currentProductInfoKeeper.getStringRecord("R.string.GoodAmount");
    }

    public void setCurrentGoodName(String goodName) {
        if (currentProductInfoKeeper == null) return;
        else currentProductInfoKeeper.writeContent("GoodName", goodName);
    }
    public void setCurrentGoodAmount(String goodAmount) {
        if (currentProductInfoKeeper == null) return;
        else currentProductInfoKeeper.writeContent("GoodAmount", goodAmount);
    }
    public Product getGood() {
        if (currentProductInfoKeeper == null) return null;
        else return new Product(
                currentProductInfoKeeper.getStringRecord("R.string.GoodName"),
                currentProductInfoKeeper.getStringRecord("R.string.GoodAmount")
        );
    }
}

