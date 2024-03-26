package com.example.practice_5_mirea.data;

import java.util.ArrayList;

public class OrderRepositoryImpl implements OrderRepository {
    ArrayList<GoodRepository> orderedPositions;

    public OrderRepositoryImpl(ArrayList<GoodRepository> orderedPositions) {
        this.orderedPositions = orderedPositions;
    }

    @Override
    public ArrayList<GoodRepository> getOrderedPositions() {
        return orderedPositions;
    }

    @Override
    public void setOrderedPositions(ArrayList<GoodRepository> orderedPositions) {
        this.orderedPositions = orderedPositions;
    }

    @Override
    public void putGood(GoodRepository good) {
        if (orderedPositions != null)
            orderedPositions.add(good);
        else {
            orderedPositions = new ArrayList<>();
            orderedPositions.add(good);
        }
    }

    @Override
    public GoodRepository getGood(int position) {
        if (orderedPositions != null)
            return orderedPositions.get(position);
        return null;
    }
}