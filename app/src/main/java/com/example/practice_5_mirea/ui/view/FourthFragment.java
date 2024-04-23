package com.example.practice_5_mirea.ui.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.practice_5_mirea.R;
import com.example.practice_5_mirea.api.DTO.request_dto.BookToPublishDTO;
import com.example.practice_5_mirea.api.DTO.response_dto.BookDTO;
import com.example.practice_5_mirea.api.FakeRestAPI;
import com.example.practice_5_mirea.api.RetrofitFactory;
import com.example.practice_5_mirea.data.models.Product;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FourthFragment extends Fragment {
    public final String URL_API =
            "https://fakerestapi.azurewebsites.net/";
    View onViewCreatedView = null;

    String productName = null;
    String productAmount = null;

    Button goBack;
    Button postBookWithProductNameButton;
    Button deleteBookWithProductNameButton;
    TextView goodInfo;
    public FourthFragment() {
        super(R.layout.fragment_fourth);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onViewCreatedView = view;

        goodInfo = (TextView) getActivity().findViewById(R.id.fragment_fourth_text_view2);
        goBack = (Button) getActivity().findViewById(R.id.fragment_fourth_button);
        postBookWithProductNameButton = (Button) getActivity().findViewById(R.id.button2);
        deleteBookWithProductNameButton = (Button) getActivity().findViewById(R.id.button3);

        Bundle args = getArguments();
        Product product = null;

        if (args != null) {
            product = (Product) args.getSerializable("Good");
            String info = product != null ? product.getGoodName() + " " + product.getGoodAmount() : null;
            if (info != null) {this.productName = product.getGoodName(); this.productAmount = product.getGoodAmount();}
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

        postBookWithProductNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Runnable CreateABookWithProductTitleApiRunnableTask = new Runnable() {
                    @Override
                    public void run() {
                        createABookWithProductTitleApi();
                    }
                };

                Thread thread = new Thread(CreateABookWithProductTitleApiRunnableTask);
                thread.start();
            }
        });

        deleteBookWithProductNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Runnable DeleteABookWithProductTitleApiRunnableTask = new Runnable() {
                    @Override
                    public void run() {
                        deleteABookByTitle();
                    }
                };

                Thread thread = new Thread(DeleteABookWithProductTitleApiRunnableTask);
                thread.start();
            }
        });
    }

    private List<BookDTO> getBookListFromApi() {
        Retrofit retrofit =
                RetrofitFactory.getRetrofit(URL_API);
        FakeRestAPI fakeRestAPI =
                retrofit.create(FakeRestAPI.class);

        Call<List<BookDTO>> call = fakeRestAPI.getBooks();

        Response<List<BookDTO>> response = null;
        try {
            Log.d("API LOG","getRandomBookTitleFromApi() method call started");
            response = call.execute();
            if (response.isSuccessful()) {
                Log.d("API LOG","getRandomBookTitleFromApi() call finished successfully");
                return response.body();
            } else {
                Log.d("API LOG","getRandomBookTitleFromApi() call finished with error");
                return null;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createABookWithProductTitleApi() {
        Retrofit retrofit =
                RetrofitFactory.getRetrofit(URL_API);
        FakeRestAPI fakeRestAPI =
                retrofit.create(FakeRestAPI.class);

        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.O) {
            return;
        }

        if (productName == null || productName.length() == 0) {
            onViewCreatedView.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getContext(), "Для создания книги введите наименование товара.", Toast.LENGTH_LONG).show();
                }
            });

            return;
        }

        Random random = new Random();
        LocalDateTime now = LocalDateTime.now();

        List<BookDTO> bookDTOList = getBookListFromApi();

        BookToPublishDTO bookToPublishDTO = new BookToPublishDTO();

        bookToPublishDTO.setId(bookDTOList.size() + 1);
        bookToPublishDTO.setTitle(this.productName);
        bookToPublishDTO.setPageCount(random.nextInt(1000));
        bookToPublishDTO.setPublishDate(now.toString());

        Call<Void> call = fakeRestAPI.postBook(bookToPublishDTO);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call,
                                   Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("Api Response", "Posting a book finished successfully");

                    onViewCreatedView.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(), "Книга успешно опубликована.", Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    Log.d("Api Response", "Posting a book finished unsuccessfully");

                    onViewCreatedView.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(), "При публикации книги произошла ошибка.", Toast.LENGTH_LONG).show();
                        }
                    });
                    return;
                }
            }
            @Override
            public void onFailure(Call<Void> call,
                                  Throwable t) {
                Log.d("Api Call", "Error occurred.");
                onViewCreatedView.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "Произошла ошибка во время выполнения операции.", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        Log.d("API LOG","createABookWithProductTitleApi() method call");
    }

    private void deleteABookByTitle() {
        Retrofit retrofit =
                RetrofitFactory.getRetrofit(URL_API);
        FakeRestAPI fakeRestAPI =
                retrofit.create(FakeRestAPI.class);

        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.O) {
            return;
        }

        if (productName == null || productName.length() == 0) {
            onViewCreatedView.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getContext(), "Для удаления книги введите наименование товара.", Toast.LENGTH_LONG).show();
                }
            });

            return;
        }

        List<BookDTO> bookDTOList = getBookListFromApi();

        if (bookDTOList == null || bookDTOList.size() == 0) {
            onViewCreatedView.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getContext(), "Книги недоступны для удаления.", Toast.LENGTH_LONG).show();
                }
            });

            return;
        }

        int bookToDeleteId = -1;

        for (BookDTO bookDTO : bookDTOList) {
            if (bookDTO.getTitle().equals(productName)) {
                bookToDeleteId = bookDTO.getId();
            }
        }

        if (bookToDeleteId == -1) {
            onViewCreatedView.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getContext(), "Книги c указанным именем не существует.", Toast.LENGTH_LONG).show();
                }
            });

            return;
        }

        Call<Void> call = fakeRestAPI.deleteBookWithId(bookToDeleteId);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call,
                                   Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("Api Response", "Deleting a book finished successfully");
                    onViewCreatedView.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(), "Книга успешно удалена.", Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    Log.d("Api Response", "Deleting a book finished unsuccessfully");
                    onViewCreatedView.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(), "Книга не удалена в связи с ошибками.", Toast.LENGTH_LONG).show();
                        }
                    });

                    return;
                }
            }
            @Override
            public void onFailure(Call<Void> call,
                                  Throwable t) {
                Log.d("Api Call", "Error occurred.");
            }
        });

        Log.d("API LOG","deleteABookByTitle() method call");
    }
}
