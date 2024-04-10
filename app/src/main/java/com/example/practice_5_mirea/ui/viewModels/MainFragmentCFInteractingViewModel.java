package com.example.practice_5_mirea.ui.viewModels;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.example.practice_5_mirea.workers.CommonFileInputWorker;
import com.example.practice_5_mirea.workers.CommonFileOutputWorker;
import com.google.common.util.concurrent.ListenableFuture;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class MainFragmentCFInteractingViewModel extends ViewModel {
    private final WorkManager mWorkManager;

    public MainFragmentCFInteractingViewModel(@NotNull Application application) {
        super();
        mWorkManager = WorkManager.getInstance(application);
    }

    public void inputToFile(String content) {
        Data.Builder builder = new Data.Builder();

        if (content != null) {
            builder.putString("InputString", content);
            builder.putString("fileName", "CommonFileExample");
        } else return;

        OneTimeWorkRequest inputRequest =
                new OneTimeWorkRequest.Builder(CommonFileInputWorker.class)
                        .setInputData(builder.build())
                        .build();

        mWorkManager.enqueue(inputRequest);
    }

    public LiveData<WorkInfo> getWorkInfoByIdLiveData(UUID requestId) {
        return mWorkManager.getWorkInfoByIdLiveData(requestId);
    }

    public UUID outputFromFile() {
        Data.Builder builder = new Data.Builder();
        builder.putString("fileName", "CommonFileExample");

        UUID requestId = UUID.randomUUID();

        OneTimeWorkRequest outputRequest =
                new OneTimeWorkRequest.Builder(CommonFileOutputWorker.class)
                        .setInputData(builder.build())
                        .setId(requestId)
                        .build();

        mWorkManager.enqueue(outputRequest);
        return requestId;
    }
}
