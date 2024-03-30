package com.example.practice_5_mirea.data.repository;

import com.example.practice_5_mirea.data.dataSource.CurrentGoodKeeper;
import com.example.practice_5_mirea.data.models.Good;

import java.io.Serializable;

public class GoodRepositoryImpl implements GoodRepository, Serializable {
    private CurrentGoodKeeper currentGoodKeeper;
    public GoodRepositoryImpl() {}
    public GoodRepositoryImpl(String goodName, String goodAmount) {
        currentGoodKeeper = new CurrentGoodKeeper(new Good(goodName, goodAmount));
    }
    public String getCurrentGoodName() {
        return currentGoodKeeper.getCurrentGood().getGoodName();
    }

    public String getCurrentGoodAmount() {
        return currentGoodKeeper.getCurrentGood().getGoodAmount();
    }

    public void setCurrentGoodName(String goodName) {
        Good currentGood = currentGoodKeeper.getCurrentGood();
        currentGood.setGoodName(goodName);
        currentGoodKeeper.SetCurrentGood(currentGood);
    }
    public void setCurrentGoodAmount(String goodAmount) {
        Good currentGood = currentGoodKeeper.getCurrentGood();
        currentGood.setGoodAmount(goodAmount);
        currentGoodKeeper.SetCurrentGood(currentGood);
    }
    public Good getGood() {
        return currentGoodKeeper.getCurrentGood();
    }
}

