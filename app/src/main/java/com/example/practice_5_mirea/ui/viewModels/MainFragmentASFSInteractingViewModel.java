package com.example.practice_5_mirea.ui.viewModels;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.work.WorkInfo;

import com.example.practice_5_mirea.data.repository.FilesRepository;
import com.example.practice_5_mirea.data.repository.FilesRepositoryImpl;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MainFragmentASFSInteractingViewModel extends ViewModel {
    private static final String TAG = MainFragmentASFSInteractingViewModel.class.getSimpleName();
    ExecutorService executorService;

    private MutableLiveData<Boolean> isInputThreadStarted =
            new MutableLiveData<Boolean>(false);
    private MutableLiveData<Boolean> isOutputThreadStarted =
            new MutableLiveData<Boolean>(false);

    private final MutableLiveData<FilesRepository> filesRepo =
            new MutableLiveData<>(new FilesRepositoryImpl());

    public void createFiles(Context context) {
        Objects.requireNonNull(filesRepo.getValue()).createFiles(
                context,
                "appSpecificFile",
                null
        );
    }

    public void stopExecutorService() {
        executorService.shutdown();

        try {
            if (!executorService.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }

        Log.i(TAG, "Files Interacting Thread stopped successfully");
    }

    public String startInput(String content) {
        if (filesRepo.getValue() == null) return "filesRepo is null.";

        if (!isInputThreadStarted.getValue().booleanValue() && !isOutputThreadStarted.getValue().booleanValue()) {
            executorService = Executors.newSingleThreadExecutor();
            isInputThreadStarted.postValue(true);

            Runnable runnableTask = () -> {
                try {
                    Objects.requireNonNull(filesRepo.getValue()).writeIntoAppSpecDS(content);
                    TimeUnit.MILLISECONDS.sleep(300);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            };

            Log.i(TAG, "String inputting into app-specific file started");

            Future<String> future = (Future<String>) executorService.submit(runnableTask);
            try {
                Log.i(TAG, "String inputting into app-specific file finished successfully");
                String res = future.get(8000, TimeUnit.MILLISECONDS);
                stopExecutorService();
                isInputThreadStarted.postValue(false);
                return res;
            } catch (ExecutionException | InterruptedException | TimeoutException e) {
                Log.i(TAG, "String inputting into app-specific file finished with crash");
                stopExecutorService();
                throw new RuntimeException(e);
            }
        }

        Log.i(TAG, "String inputting into app-specific file finished" +
                " with error because InputThread or OutputThread are already started");

        return "InputThread or OutputThread are already started.";
    }

    public LiveData<String> getOutputFileThreadResult() {
        return new MutableLiveData<>(startOutput());
    }

    private String startOutput() {
        if (filesRepo.getValue() == null) return null;

        if (!isInputThreadStarted.getValue().booleanValue() && !isOutputThreadStarted.getValue().booleanValue()) {
            executorService = Executors.newSingleThreadExecutor();
            isInputThreadStarted.postValue(true);

            Callable<String> callable = () -> {
                try {
                    String outputString = Objects.requireNonNull(filesRepo.getValue()).readFromAppSpecDS();
                    TimeUnit.MILLISECONDS.sleep(300);
                    return outputString;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            };

            Log.i(TAG, "Output from app-specific file process started");

            Future<String> future = executorService.submit(callable);

            try {
                Log.i(TAG, "Output from app-specific file process finished successfully");
                String res = future.get(8000, TimeUnit.MILLISECONDS);
                stopExecutorService();
                isInputThreadStarted.postValue(false);
                return res;
            } catch (ExecutionException | InterruptedException | TimeoutException e) {
                Log.i(TAG, "Output from app-specific file process finished with crash");
                stopExecutorService();
                throw new RuntimeException(e);
            }
        }

        Log.i(TAG, "Output from app-specific file process finished with null result " +
                "because InputThread or OutputThread are already started");

        return null;
    }
}
