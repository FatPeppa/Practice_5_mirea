package com.example.practice_5_mirea.ui.view;

import static com.example.practice_5_mirea.InputsValidator.checkGoodAmount;
import static com.example.practice_5_mirea.InputsValidator.checkGoodName;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.practice_5_mirea.R;
import com.example.practice_5_mirea.ui.viewModels.GoodViewModel;

public class FirstFragment extends Fragment {
    Button first_fragment_button;
    EditText first_fragment_edit_text1;
    EditText first_fragment_edit_text2;

    public FirstFragment() {super(R.layout.fragment_first);}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        first_fragment_button = (Button) getActivity().findViewById(R.id.fragment_first_button);
        first_fragment_edit_text1 = (EditText) getActivity().findViewById(R.id.fragment_first_edit_text1);
        first_fragment_edit_text2 = (EditText) getActivity().findViewById(R.id.fragment_first_edit_text2);
        GoodViewModel goodViewModel = new ViewModelProvider(getActivity()).get(GoodViewModel.class);

        goodViewModel.getUiState().observe(getViewLifecycleOwner(), uiState -> {
            if (uiState.getCurrentGoodName() != null && uiState.getCurrentGoodAmount() != null) {
                first_fragment_edit_text1.setText(uiState.getCurrentGoodName());
                first_fragment_edit_text2.setText(uiState.getCurrentGoodAmount());
            }
        });

        first_fragment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String good_name = first_fragment_edit_text1.getText().toString();
                String good_amount = first_fragment_edit_text2.getText().toString();

                if (checkGoodName(good_name)) {
                    //order.setGoodName(good_name);
                    //bundle.putSerializable("Order", order);

                    if (checkGoodAmount(good_amount)) {
                        //order.setGoodAmount(good_amount);

                        goodViewModel.inputGoodParameters(good_name, good_amount);

                        //Bundle bundle = new Bundle();
                        //bundle.putSerializable("Order", order);
                        Navigation.findNavController(view).navigate(R.id.action_firstFragment_to_secondFragment);
                    } else {
                        first_fragment_edit_text2.setText("");
                        first_fragment_edit_text2.setHint(getResources().getString(R.string.fragment_first_egit_text_hint2));
                    }
                } else {
                    first_fragment_edit_text1.setText("");
                    first_fragment_edit_text1.setHint(getResources().getString(R.string.fragment_first_egit_text_hint1));
                }
            }
        });
    }
}
