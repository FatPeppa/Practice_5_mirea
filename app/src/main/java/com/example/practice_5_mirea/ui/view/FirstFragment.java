package com.example.practice_5_mirea.ui.view;

import static com.example.practice_5_mirea.domain.InputsValidator.checkGoodAmount;
import static com.example.practice_5_mirea.domain.InputsValidator.checkGoodName;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.practice_5_mirea.R;
import com.example.practice_5_mirea.domain.InputTextExecutor;
import com.example.practice_5_mirea.domain.InputsValidator;
import com.example.practice_5_mirea.ui.viewModels.ProductViewModel;

public class FirstFragment extends Fragment {
    ImageView imageView;
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

        first_fragment_edit_text1 = (EditText) getActivity().findViewById(R.id.fragment_first_edit_text1);
        first_fragment_edit_text2 = (EditText) getActivity().findViewById(R.id.fragment_first_edit_text2);
        imageView = (ImageView) getActivity().findViewById(R.id.imageView);
        AnimatedVectorDrawable drawable = (AnimatedVectorDrawable) imageView.getDrawable();
        drawable.reset();

        ProductViewModel productViewModel = new ViewModelProvider(getActivity()).get(ProductViewModel.class);
        productViewModel.getUiState().getValue().createCurrentInfoKeeper(getActivity().getApplicationContext());

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String sharedText = bundle.getString("sharedText");
            if (sharedText != null && InputsValidator.checkSharedProductInfo(sharedText)) {
                String name = InputTextExecutor.getProductName(sharedText);
                String amount = InputTextExecutor.getProductAmount(sharedText);
                productViewModel.inputGoodParameters(name, amount);

                Toast.makeText(getContext(), "Параметры товара успешно считаны.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(), "Ошибка при чтении параметров товара из текста: некорректный формат", Toast.LENGTH_LONG).show();
            }
        }

        productViewModel.getUiState().observe(getViewLifecycleOwner(), uiState -> {
            if (uiState.getCurrentGoodName() != null && uiState.getCurrentGoodAmount() != null) {
                first_fragment_edit_text1.setText(uiState.getCurrentGoodName());
                first_fragment_edit_text2.setText(uiState.getCurrentGoodAmount());
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String good_name = first_fragment_edit_text1.getText().toString();
                String good_amount = first_fragment_edit_text2.getText().toString();

                if (checkGoodName(good_name)) {
                    if (checkGoodAmount(good_amount)) {
                        drawable.start();
                        view.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                productViewModel.inputGoodParameters(good_name, good_amount);
                                Navigation.findNavController(view).navigate(R.id.action_firstFragment_to_secondFragment);
                            }
                        }, 400);
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
