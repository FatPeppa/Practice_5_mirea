package com.example.practice_5_mirea.data.repository;

import android.content.Context;

import com.example.practice_5_mirea.data.dataSource.Files.AppSpecificDataSource;
import com.example.practice_5_mirea.data.dataSource.Files.CommonFilesDataSource;

public class FilesRepositoryImpl implements FilesRepository{
    private final AppSpecificDataSource appSpecificDataSource;
    private final CommonFilesDataSource commonFilesDataSource;

    public FilesRepositoryImpl(Context context, String appSpecDSFileName, String commonFilesDSFileName) {
        appSpecificDataSource = new AppSpecificDataSource(context, appSpecDSFileName);
        commonFilesDataSource = new CommonFilesDataSource(context, commonFilesDSFileName);
    }

    @Override
    public void writeIntoAppSpecDS(String inputContent) {
        appSpecificDataSource.writeAppSpecificDS("\n" + inputContent);
    }

    @Override
    public boolean writeIntoCommonFilesDS(String inputContent) {
        return commonFilesDataSource.writeContent("\n" + inputContent);
    }

    @Override
    public String readFromAppSpecDS() {
        return appSpecificDataSource.readAppSpecificDS();
    }

    @Override
    public String readFromCommonFilesDS() {
        return commonFilesDataSource.readFile();
    }
}
