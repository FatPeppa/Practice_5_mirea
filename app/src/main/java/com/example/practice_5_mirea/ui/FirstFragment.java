package com.example.practice_5_mirea.ui;

import static com.example.practice_5_mirea.InputsValidator.checkGoodAmount;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.practice_5_mirea.data.GoodOrderRepository;
import com.example.practice_5_mirea.R;

public class FirstFragment extends Fragment {
    GoodOrderRepository order;
    Button first_fragment_button;
    TextView first_fragment_text_view;
    EditText first_fragment_edit_text;

    public FirstFragment() {super(R.layout.fragment_first);}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        order = (GoodOrderRepository)  getArguments().getSerializable("Order");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        first_fragment_button = (Button) getActivity().findViewById(R.id.fragment_first_button);
        first_fragment_text_view = (TextView) getActivity().findViewById(R.id.fragment_first_text_view2);
        first_fragment_edit_text = (EditText) getActivity().findViewById(R.id.fragment_first_edit_text);

        String good_name = order.getGoodName();
        first_fragment_text_view.setText(good_name);

        first_fragment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String good_amount = first_fragment_edit_text.getText().toString();

                if (checkGoodAmount(good_amount)) {
                    order.setGoodAmount(good_amount);

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("Order", order);
                    Navigation.findNavController(view).navigate(R.id.action_firstFragment_to_secondFragment, bundle);
                } else {
                    first_fragment_edit_text.setText("");
                    first_fragment_edit_text.setHint(getResources().getString(R.string.fragment_main_egit_text_hint));
                }
            }
        });
    }
}
