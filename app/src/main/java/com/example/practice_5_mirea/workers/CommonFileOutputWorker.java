package com.example.practice_5_mirea.workers;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.ListenableWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.practice_5_mirea.data.repository.FilesRepository;
import com.example.practice_5_mirea.data.repository.FilesRepositoryImpl;

import org.jetbrains.annotations.NotNull;

public class CommonFileOutputWorker extends Worker {
    private static final String TAG = CommonFileOutputWorker.class.getSimpleName();
    FilesRepository filesRepository;
    public CommonFileOutputWorker(
            @NotNull Context appContext,
            @NotNull WorkerParameters workerParameters) {
        super(appContext, workerParameters);
    }
    @NonNull
    @Override
    public Result doWork() {
        Log.i(TAG, "doWork: start");

        String fileName = getInputData().getString("fileName");

        filesRepository = new FilesRepositoryImpl();
        filesRepository.createFiles(getApplicationContext(), null, fileName);

        String outputString = null;


        outputString = filesRepository.readFromCommonFilesDS();

        Data output = new Data.Builder()
                .putString("OutputString", outputString)
                .build();

        Log.i(TAG, "doWork: finished successfully");
        return Result.success(output);
    }
}
