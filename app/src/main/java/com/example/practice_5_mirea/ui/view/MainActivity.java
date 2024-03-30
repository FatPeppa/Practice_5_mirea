package com.example.practice_5_mirea.ui.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.example.practice_5_mirea.R;
import com.example.practice_5_mirea.ui.viewModels.GoodViewModel;
import com.example.practice_5_mirea.ui.viewModels.OrderViewModel;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GoodViewModel goodViewModel = new ViewModelProvider(this).get(GoodViewModel.class);
        OrderViewModel orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
    }
}