package com.example.practice_5_mirea.ui.view;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.work.WorkInfo;

import com.example.practice_5_mirea.R;
import com.example.practice_5_mirea.data.dataSource.Files.CommonFilesDataSource;
import com.example.practice_5_mirea.data.models.Product;
import com.example.practice_5_mirea.data.repository.FilesRepository;
import com.example.practice_5_mirea.data.repository.FilesRepositoryImpl;
import com.example.practice_5_mirea.domain.ProductEntityToProductConverter;
import com.example.practice_5_mirea.ui.viewModels.MainFragmentASFSInteractingViewModel;
import com.example.practice_5_mirea.ui.viewModels.MainFragmentCFInteractingViewModel;
import com.example.practice_5_mirea.ui.viewModels.OrderViewModel;
import com.example.practice_5_mirea.ui.viewModels.ProductViewModel;

import java.util.ArrayList;
import java.util.UUID;

public class MainFragment extends Fragment {
    private final int PERMISSION_REQUEST_CODE = 80;
    Button main_fragment_button1;
    Button main_fragment_button2;

    Button main_fragment_button3; //кнопка для сохранения записей из бд в файл ASDS
    Button main_fragment_button4; //кнопка для сохранения записей из бд в файл CFDS

    Button main_fragment_button5; //кнопка для отправки текста из файла ASDS в другое приложение

    TextView main_fragment_text_view;
    public MainFragment() {
        super(R.layout.fragment_main);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        main_fragment_button1 = (Button) getActivity().findViewById(R.id.fragment_main_button1);
        main_fragment_button2 = (Button) getActivity().findViewById(R.id.fragment_main_button2);
        main_fragment_button3 = (Button) getActivity().findViewById(R.id.fragment_main_button3);
        main_fragment_button4 = (Button) getActivity().findViewById(R.id.fragment_main_button4);
        main_fragment_button5 = (Button) getActivity().findViewById(R.id.fragment_main_button5);


        main_fragment_text_view = (TextView) getActivity().findViewById(R.id.text_view);
        OrderViewModel order = new ViewModelProvider(getActivity()).get(OrderViewModel.class);
        order.createOrder(getActivity().getApplicationContext(), null);

        order.getUiState().observe(getViewLifecycleOwner(), uiState -> {
            int arrayLength;
            if (uiState != null && uiState.getOrderedPositions() != null)
                arrayLength = uiState.getOrderedPositions().size();
            else
                arrayLength = 0;
            String info = "На данный момент заказано товаров: " + Integer.toString(arrayLength);
            main_fragment_text_view.setText(info);
        });
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

        ProductViewModel productViewModel = new ViewModelProvider(getActivity()).get(ProductViewModel.class);
        productViewModel.getUiState().getValue().createCurrentInfoKeeper(getActivity().getApplicationContext());
        productViewModel.getUiState().getValue().setCurrentGoodAmount("");
        productViewModel.getUiState().getValue().setCurrentGoodName("");


        FilesRepository filesRepository = new FilesRepositoryImpl(
                getActivity().getApplicationContext(),
                "dbHistoryASDS",
                "dbHistory"
        );

        MainFragmentASFSInteractingViewModel mainFragmentASFSInteractingViewModel
                = new ViewModelProvider(getActivity()).get(MainFragmentASFSInteractingViewModel.class);
        mainFragmentASFSInteractingViewModel.createFiles(getActivity().getApplicationContext());

        if (main_fragment_text_view.getText().equals("На данный момент заказано товаров: 0")) {
            main_fragment_button3.setVisibility(View.GONE);
            main_fragment_button4.setVisibility(View.GONE);
        } else {
            main_fragment_button3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StringBuilder dbStringBuilder = new StringBuilder();
                    ArrayList<Product> orderList = order.getUiState().getValue().getOrderedPositions();
                    if (orderList != null && orderList.size() > 0) {
                        for (Product product : orderList) {
                            dbStringBuilder.append("id: ").append(product.getId())
                                    .append("; name: ")
                                    .append(product.getGoodName())
                                    .append("; amount: ")
                                    .append(product.getGoodAmount())
                                    .append(";\n");
                        }

                        mainFragmentASFSInteractingViewModel.startInput(dbStringBuilder.toString());

                        mainFragmentASFSInteractingViewModel.getOutputFileThreadResult().observe(
                                getActivity(), result -> {
                                    if (result != null) {
                                        Toast.makeText(getActivity(), "В файл AppSpecificDS было записано символов: "
                                                        + result.length(), Toast.LENGTH_SHORT)
                                                .show();
                                    }
                                }
                        );
                    }
                }
            });
            MainFragmentCFInteractingViewModel mainFragmentCFInteractingViewModel
                    = new MainFragmentCFInteractingViewModel(getActivity().getApplication());

            main_fragment_button4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StringBuilder dbStringBuilder = new StringBuilder();
                    ArrayList<Product> orderList = order.getUiState().getValue().getOrderedPositions();
                    if (orderList != null && orderList.size() > 0) {
                        for (Product product : orderList) {
                            dbStringBuilder.append("id: ")
                                    .append(product.getId())
                                    .append("; name: ")
                                    .append(product.getGoodName()).append("; amount: ")
                                    .append(product.getGoodAmount())
                                    .append(";\n");
                        }

                        if (!CommonFilesDataSource.checkPermission(getActivity().getApplicationContext()))
                            requestPermission();

                        mainFragmentCFInteractingViewModel.inputToFile(dbStringBuilder.toString());


                        UUID requestForFileOutputId = mainFragmentCFInteractingViewModel.outputFromFile();

                        mainFragmentCFInteractingViewModel.getWorkInfoByIdLiveData(requestForFileOutputId)
                                .observe(getActivity(), workInfo -> {
                                    if (workInfo.getState() != null &&
                                            workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                                        String result = workInfo.getOutputData().getString("OutputString");
                                                Toast.makeText(getActivity(), "В файл CommonFilesDS было записано символов: "
                                                        + result.length(), Toast.LENGTH_SHORT).show();
                                    }
                        });
                    }
                }
            });

            main_fragment_button5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String fileContent = filesRepository.readFromAppSpecDS();
                    if (fileContent == null || fileContent.length() == 0)
                        Toast.makeText(getActivity(), "На данный момент файл пуст. Заполните файл данными заказа.", Toast.LENGTH_SHORT).show();
                    else {
                        fileContent = "Список товаров в заказе:\n" + fileContent;
                        Intent textMessageIntent = new Intent(Intent.ACTION_SEND);
                        textMessageIntent.setType("text/plain");
                        textMessageIntent.putExtra(Intent.EXTRA_TEXT, fileContent);

                        Intent shareIntent = Intent.createChooser(textMessageIntent, null);
                        startActivity(shareIntent);
                    }
                }
            });
        }
    }
    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(getContext(), "Write External Storage permission allows us to create files. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (!(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {Toast.makeText(getContext(), "No permission given", Toast.LENGTH_LONG).show();}
            break;
        }
    }
}
