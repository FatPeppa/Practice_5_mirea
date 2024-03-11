package com.example.practice_5_mirea;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class SecondFragment extends Fragment {
    TextView second_fragment_text_view;
    public SecondFragment() {
        super(R.layout.fragment_second);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        second_fragment_text_view = (TextView) getActivity().findViewById(R.id.fragment_second_text_view2);

        Bundle bundle = this.getArguments();
        ArrayList<String> message = bundle.getStringArrayList("goodNameGoodAmount");
        String good_name = FirstFragment.parseInputsGoods(message, GoodParameterType.GOOD_NAME);
        String good_amount = FirstFragment.parseInputsGoods(message, GoodParameterType.GOOD_AMOUNT);
        String result = good_amount + " " + good_name;
        second_fragment_text_view.setText(result);
    }
}
