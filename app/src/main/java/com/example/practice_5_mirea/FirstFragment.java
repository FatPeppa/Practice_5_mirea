package com.example.practice_5_mirea;

import static com.example.practice_5_mirea.MainFragment.isNumeric;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class FirstFragment extends Fragment {
    Button first_fragment_button;
    TextView first_fragment_text_view;
    EditText first_fragment_edit_text;

    public FirstFragment() {
        super(R.layout.fragment_first);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        first_fragment_button = (Button) getActivity().findViewById(R.id.fragment_first_button);
        first_fragment_text_view = (TextView) getActivity().findViewById(R.id.fragment_first_text_view2);
        first_fragment_edit_text = (EditText) getActivity().findViewById(R.id.fragment_first_edit_text);

        String good_name = parseInputsGoods(this.getArguments().getStringArrayList("goodName"),
                GoodParameterType.GOOD_NAME);
        first_fragment_text_view.setText(good_name);

        first_fragment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String good_amount = first_fragment_edit_text.getText().toString();

                if (good_amount.length() > 0 && isNumeric(good_amount)) {
                    ArrayList<String> message =  new ArrayList<String>();
                    message.add(good_name);
                    message.add(good_amount);

                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList("goodNameGoodAmount",  message);
                    Navigation.findNavController(view).navigate(R.id.action_firstFragment_to_secondFragment, bundle);
                } else {
                    first_fragment_edit_text.setText("");
                    first_fragment_edit_text.setHint(getResources().getString(R.string.fragment_main_egit_text_hint));
                }
            }
        });
    }

    public static String parseInputsGoods(ArrayList<String> array, GoodParameterType type) {
        switch (type) {
            case GOOD_NAME:
                return array.get(0);
            case GOOD_AMOUNT:
                return array.get(1);
            default:
                return null;
        }
    }
}
