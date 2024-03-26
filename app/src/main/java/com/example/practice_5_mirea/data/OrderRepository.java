package com.example.practice_5_mirea.data;

import java.util.ArrayList;

public interface OrderRepository {
    ArrayList<GoodRepository> orderedPositions = null;

    ArrayList<GoodRepository> getOrderedPositions();
    void setOrderedPositions(ArrayList<GoodRepository> orderedPositions);
    void putGood(GoodRepository good);
    GoodRepository getGood(int position);
}
