package com.example.practice_5_mirea.data.repository;

import com.example.practice_5_mirea.data.model.Good;
import com.example.practice_5_mirea.data.repository.GoodRepository;

import java.util.ArrayList;

public interface OrderRepository {

    ArrayList<Good> getOrderedPositions();
    void setOrderedPositions(ArrayList<Good> orderedPositions);
    void putGood(Good good);
    Good getGood(int position);
}
