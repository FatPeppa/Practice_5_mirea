package com.example.practice_5_mirea.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.practice_5_mirea.data.GoodRepository;
import com.example.practice_5_mirea.data.GoodRepositoryImpl;
import com.example.practice_5_mirea.data.OrderRepository;
import com.example.practice_5_mirea.data.OrderRepositoryImpl;

import java.util.ArrayList;

public class OrderViewModel extends ViewModel {
    private final MutableLiveData<OrderRepository> uiState =
            new MutableLiveData<>(new OrderRepositoryImpl(null));
    public LiveData<OrderRepository> getUiState() {
        return uiState;
    }

    public void addGoodToOrder(GoodRepository good) {
        OrderRepository order;
        order = uiState.getValue();
        if (order != null)
            order.putGood(good);
        else {
            ArrayList<GoodRepository> goods = new ArrayList<>();
            goods.add(good);
            order = new OrderRepositoryImpl(goods);
        }

        uiState.setValue(
                order
        );
    }
}
