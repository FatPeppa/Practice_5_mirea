package com.example.practice_5_mirea.ui.viewModels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.practice_5_mirea.data.models.Product;
import com.example.practice_5_mirea.data.repository.OrderRepository;
import com.example.practice_5_mirea.data.repository.OrderRepositoryImpl;

import java.util.ArrayList;
import java.util.Objects;

public class OrderViewModel extends ViewModel {
    private final MutableLiveData<OrderRepository> uiState =
            new MutableLiveData<>(new OrderRepositoryImpl());
    public LiveData<OrderRepository> getUiState() {
        return uiState;
    }

    public void createOrder(Context context, ArrayList<Product> products) {
        Objects.requireNonNull(uiState.getValue()).createDatabase(context, products);
    }

    public void addGoodToOrder(Product product) {
        OrderRepository order = uiState.getValue();

        if (order == null) return;
        else order.putGood(product);

        uiState.setValue(
                order
        );
    }
}
