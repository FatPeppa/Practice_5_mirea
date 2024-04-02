package com.example.practice_5_mirea.data.repository;

import android.content.Context;

import com.example.practice_5_mirea.data.models.Product;

import java.util.ArrayList;

public interface OrderRepository {

    ArrayList<Product> getOrderedPositions();
    void setOrderedPositions(ArrayList<Product> orderedPositions);
    void putGood(Product product);
    Product getGood(int position);

    void createDatabase(Context context, ArrayList<Product> values);

    void cleanDatabase();
}
