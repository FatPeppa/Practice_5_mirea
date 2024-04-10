package com.example.practice_5_mirea.workers;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.practice_5_mirea.data.repository.FilesRepository;
import com.example.practice_5_mirea.data.repository.FilesRepositoryImpl;

import org.jetbrains.annotations.NotNull;

public class CommonFileInputWorker extends Worker {
    private static final String TAG = CommonFileInputWorker.class.getSimpleName();
    FilesRepository filesRepository;
    public CommonFileInputWorker(
            @NotNull Context appContext,
            @NotNull WorkerParameters workerParameters) {
        super(appContext, workerParameters);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.i(TAG, "doWork: start");

        String inputString = getInputData().getString("InputString");
        String fileName = getInputData().getString("fileName");

        filesRepository = new FilesRepositoryImpl();
        filesRepository.createFiles(getApplicationContext(), null, fileName);

        if (filesRepository.writeIntoCommonFilesDS(inputString)) {
            Log.i(TAG, "doWork: finished successfully");
            return Result.success();
        }
        else {
            Log.i(TAG, "doWork: finished with error");
            return Result.failure();
        }
    }
}
