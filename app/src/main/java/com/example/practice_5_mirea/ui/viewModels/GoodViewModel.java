package com.example.practice_5_mirea.ui.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.practice_5_mirea.data.repository.GoodRepository;
import com.example.practice_5_mirea.data.repository.GoodRepositoryImpl;

public class GoodViewModel extends ViewModel {
    private final MutableLiveData<GoodRepository> uiState =
            new MutableLiveData<>(new GoodRepositoryImpl(null, null));
    public LiveData<GoodRepository> getUiState() {
        return uiState;
    }

    public void inputGoodParameters(String goodName, String goodAmount) {
        uiState.setValue(
                new GoodRepositoryImpl(goodName, goodAmount)
        );
    }
}
