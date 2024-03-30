package com.example.practice_5_mirea.ui.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.practice_5_mirea.R;

public class MainFragment extends Fragment {
    Button main_fragment_button1;
    Button main_fragment_button2;
    EditText main_fragment_edit_text;
    public MainFragment() {
        super(R.layout.fragment_main);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        main_fragment_button1 = (Button) getActivity().findViewById(R.id.fragment_main_button1);
        main_fragment_button2 = (Button) getActivity().findViewById(R.id.fragment_main_button2);

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
    }
}
