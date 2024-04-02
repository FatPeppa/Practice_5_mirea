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
import com.example.practice_5_mirea.data.models.Product;
import com.example.practice_5_mirea.ui.viewModels.ProductViewModel;
import com.example.practice_5_mirea.ui.viewModels.OrderViewModel;

import java.util.ArrayList;
import java.util.Objects;


public class SecondFragment extends Fragment {

    TextView second_fragment_text_view;
    Button secondFragmentButton1;
    Button secondFragmentButton2;

    public SecondFragment() {super(R.layout.fragment_second);}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //к листу
        secondFragmentButton2 = (Button) getActivity().findViewById(R.id.fragment_second_button2);
        //к товару
        secondFragmentButton1 = (Button) getActivity().findViewById(R.id.fragment_second_button1);
        //Информация о товаре
        second_fragment_text_view = getActivity().findViewById(R.id.fragment_second_text_view2);

        ProductViewModel productViewModel = new ViewModelProvider(getActivity()).get(ProductViewModel.class);
        OrderViewModel orderViewModel = new ViewModelProvider(getActivity()).get(OrderViewModel.class);
        productViewModel.getUiState().observe(getViewLifecycleOwner(), uiState -> {
            String info = uiState.getCurrentGoodName() + " " + uiState.getCurrentGoodAmount();
            second_fragment_text_view.setText(info);
        });

        //String result = order.getGoodAmount() + " " + order.getGoodName();
        //second_fragment_text_view.setText(result);

        //к товару
        secondFragmentButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_secondFragment_to_firstFragment);
            }
        });

        //к листу
        secondFragmentButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Objects.requireNonNull(orderViewModel.getUiState().getValue()).getOrderedPositions() == null)
                    orderViewModel.getUiState().getValue().createDatabase(getActivity().getApplicationContext(), null);

                ArrayList<Product> products = orderViewModel.getUiState().getValue().getOrderedPositions();
                int id = 0;
                if (products != null) id = products.size();

                Product productToInput = productViewModel.getUiState().getValue().getGood();
                productToInput.setId(id);

                orderViewModel.addGoodToOrder(productToInput);
                productViewModel.inputGoodParameters(null, null);

                Navigation.findNavController(view).navigate(R.id.action_secondFragment_to_thirdFragment);
            }
        });
    }
}
