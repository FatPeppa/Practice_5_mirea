package com.example.practice_5_mirea.data.repository;

import com.example.practice_5_mirea.data.dataSource.OrderKeeper;
import com.example.practice_5_mirea.data.models.Good;

import java.util.ArrayList;
import java.util.HashMap;

public class OrderRepositoryImpl implements OrderRepository {
    OrderKeeper orderKeeper = null;

    public OrderRepositoryImpl(ArrayList<Good> values) {
        if (values != null) {
            this.orderKeeper = new OrderKeeper(new HashMap<Integer, Good>());
            for (Good good : values) {
                orderKeeper.putGoodToOrder(good);
            }
        }
    }

    @Override
    public ArrayList<Good> getOrderedPositions() {
        if (orderKeeper != null)
            return orderKeeper.getOrder();
        return null;
    }
    @Override
    public void setOrderedPositions(ArrayList<Good> orderedPositions) {
        if (orderKeeper == null)
            orderKeeper = new OrderKeeper(new HashMap<Integer, Good>());
        for (Good good : orderedPositions) {
            orderKeeper.putGoodToOrder(good);
        }
    }
    @Override
    public void putGood(Good good) {
        if (orderKeeper != null)
            orderKeeper.putGoodToOrder(good);
        else {
            orderKeeper = new OrderKeeper(new HashMap<Integer, Good>());
            orderKeeper.putGoodToOrder(good);
        }
    }
    @Override
    public Good getGood(int position) {
        if (orderKeeper != null)
            return orderKeeper.getGoodById(position);
        return null;
    }
}