package com.example.practice_5_mirea.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.practice_5_mirea.data.GoodOrderRepository;
import com.example.practice_5_mirea.R;


public class SecondFragment extends Fragment {
    GoodOrderRepository order;

    TextView second_fragment_text_view;

    public SecondFragment() {super(R.layout.fragment_second);}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        order = (GoodOrderRepository) getArguments().getSerializable("Order");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        second_fragment_text_view = getActivity().findViewById(R.id.fragment_second_text_view2);

        String result = order.getGoodAmount() + " " + order.getGoodName();
        second_fragment_text_view.setText(result);
    }
}
