package com.example.practice_5_mirea.data.repository;

import com.example.practice_5_mirea.data.models.Good;

import java.util.ArrayList;

public interface OrderRepository {

    ArrayList<Good> getOrderedPositions();
    void setOrderedPositions(ArrayList<Good> orderedPositions);
    void putGood(Good good);
    Good getGood(int position);
}
