package com.example.practice_5_mirea.ui.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.practice_5_mirea.R;
import com.example.practice_5_mirea.ui.viewModels.OrderViewModel;
import com.example.practice_5_mirea.ui.viewModels.ProductViewModel;

public class MainFragment extends Fragment {
    Button main_fragment_button1;
    Button main_fragment_button2;
    TextView main_fragment_text_view;
    public MainFragment() {
        super(R.layout.fragment_main);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        main_fragment_button1 = (Button) getActivity().findViewById(R.id.fragment_main_button1);
        main_fragment_button2 = (Button) getActivity().findViewById(R.id.fragment_main_button2);
        main_fragment_text_view = (TextView) getActivity().findViewById(R.id.text_view);
        OrderViewModel order = new ViewModelProvider(getActivity()).get(OrderViewModel.class);
        order.createOrder(getActivity().getApplicationContext(), null);

        order.getUiState().observe(getViewLifecycleOwner(), uiState -> {
            int arrayLength;
            if (uiState != null && uiState.getOrderedPositions() != null)
                arrayLength = uiState.getOrderedPositions().size();
            else
                arrayLength = 0;
            String info = "На данный момент заказано товаров: " + Integer.toString(arrayLength);
            main_fragment_text_view.setText(info);
        });
        //переход к товару
        main_fragment_button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_firstFragment);
            }
        });
        //переход к списку
        main_fragment_button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_thirdFragment);
            }
        });

        ProductViewModel productViewModel = new ViewModelProvider(getActivity()).get(ProductViewModel.class);
        productViewModel.getUiState().getValue().createCurrentInfoKeeper(getActivity().getApplicationContext());
        productViewModel.getUiState().getValue().setCurrentGoodAmount("");
        productViewModel.getUiState().getValue().setCurrentGoodName("");
    }
}
