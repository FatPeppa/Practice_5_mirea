package com.example.practice_5_mirea.data.dataSource;

import com.example.practice_5_mirea.data.model.Good;

public class CurrentGoodKeeper {
    private final Good[] currentGood = new Good[1];
    public CurrentGoodKeeper (Good good) {this.currentGood[0] = good;}

    public Good getCurrentGood() {
        return currentGood[0];
    }

    public void SetCurrentGood(Good good) {
        this.currentGood[0] = good;
    }
}
