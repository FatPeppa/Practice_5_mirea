package com.example.practice_5_mirea.data.repository;

import android.content.Context;

public interface FilesRepository {
    void writeIntoAppSpecDS(String inputContent);
    boolean writeIntoCommonFilesDS(String inputContent);

    void createFiles(Context context, String appSpecDSFileName, String commonFilesDSFileName);

    String readFromAppSpecDS();
    String readFromCommonFilesDS();
}
