package com.example.practice_5_mirea.ui.view;

import static com.example.practice_5_mirea.domain.InputsValidator.checkGoodAmount;
import static com.example.practice_5_mirea.domain.InputsValidator.checkGoodName;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.example.practice_5_mirea.api.DTO.response_dto.BookDTO;
import com.example.practice_5_mirea.api.FakeRestAPI;
import com.example.practice_5_mirea.api.RetrofitFactory;
import com.example.practice_5_mirea.domain.InputTextExecutor;
import com.example.practice_5_mirea.domain.InputsValidator;
import com.example.practice_5_mirea.ui.viewModels.ProductViewModel;

import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FirstFragment extends Fragment {
    public final String URL_API =
            "https://fakerestapi.azurewebsites.net/";

    ImageView imageView;
    Button get_book_name_from_website_button;
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
        get_book_name_from_website_button = (Button) getActivity().findViewById(R.id.button);

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

        get_book_name_from_website_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRandomBookTitleFromApi();
            }
        });
    }

    private void setRandomBookTitleFromApi() {
        Retrofit retrofit =
                RetrofitFactory.getRetrofit(URL_API);
        FakeRestAPI fakeRestAPI =
                retrofit.create(FakeRestAPI.class);

        Call<List<BookDTO>> call = fakeRestAPI.getBooks();

        call.enqueue(new Callback<List<BookDTO>>() {
            @Override
            public void onResponse(Call<List<BookDTO>> call,
                    Response<List<BookDTO>> response) {
                if (response.isSuccessful()) {
                    List<BookDTO> books = response.body();
                    Log.d("Api Response", "Getting books from api finished successfully");

                    Random random = new Random();
                    int random_index = random.nextInt(books.size());
                    first_fragment_edit_text1.setText(books.get(random_index).getTitle());

                    Toast.makeText(getContext(), "Наименование одной из "
                            + books.size() + " полученных книг установлено успешно", Toast.LENGTH_LONG).show();
                } else {
                    Log.d("Api Response", "Getting books from api finished unsuccessfully");
                    Toast.makeText(getContext(), "Произошла ошибка во время выполнения операции.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
            @Override
            public void onFailure(Call<List<BookDTO>> call,
                    Throwable t) {
                Log.d("Api Call", "Error occurred.");
                Toast.makeText(getContext(), "Произошла ошибка во время выполнения операции.", Toast.LENGTH_LONG).show();
            }
        });

        Log.d("API LOG","getRandomBookTitleFromApi() method call");
    }
}
