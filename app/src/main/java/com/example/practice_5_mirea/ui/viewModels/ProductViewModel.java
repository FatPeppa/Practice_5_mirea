package com.example.practice_5_mirea.ui.viewModels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.practice_5_mirea.data.repository.ProductRepository;
import com.example.practice_5_mirea.data.repository.ProductRepositoryImpl;

import java.util.Objects;

public class ProductViewModel extends ViewModel {
    private final MutableLiveData<ProductRepository> uiState =
            new MutableLiveData<>(new ProductRepositoryImpl());
    public LiveData<ProductRepository> getUiState() {
        return uiState;
    }

    public void createProduct(Context context) {
        Objects.requireNonNull(uiState.getValue()).createCurrentInfoKeeper(context);
    }

    public void inputGoodParameters(String goodName, String goodAmount) {
        ProductRepository productRepository = uiState.getValue();
        if (productRepository != null) {
            productRepository.setCurrentGoodName(goodName);
            productRepository.setCurrentGoodAmount(goodAmount);
        }

        uiState.setValue(productRepository);
    }
}
