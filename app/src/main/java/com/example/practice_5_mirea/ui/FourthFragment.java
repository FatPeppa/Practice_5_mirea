package com.example.practice_5_mirea.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.practice_5_mirea.R;
import com.example.practice_5_mirea.data.model.Good;
import com.example.practice_5_mirea.data.repository.GoodRepository;

public class FourthFragment extends Fragment {
    Button goBack;
    TextView goodInfo;
    public FourthFragment() {
        super(R.layout.fragment_fourth);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        goodInfo = (TextView) getActivity().findViewById(R.id.fragment_fourth_text_view2);
        goBack = (Button) getActivity().findViewById(R.id.fragment_fourth_button);

        Bundle args = getArguments();
        Good good = null;

        if (args != null) {
            good = (Good) args.getSerializable("Good");
            String info = good != null ? good.getGoodName() + " " + good.getGoodAmount() : null;
            goodInfo.setText(info);
        } else {
            Toast.makeText(getActivity(), "An error with good transition occurred!", Toast.LENGTH_SHORT).show();
        }

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_fourthFragment_to_thirdFragment);
            }
        });
    }
}
