package com.example.practice_5_mirea.data.repository;

import android.content.Context;

import com.example.practice_5_mirea.data.dataSource.Files.AppSpecificDataSource;
import com.example.practice_5_mirea.data.dataSource.Files.CommonFilesDataSource;

import java.io.Serializable;

public class FilesRepositoryImpl implements FilesRepository, Serializable {
    private AppSpecificDataSource appSpecificDataSource = null;
    private CommonFilesDataSource commonFilesDataSource = null;

    public FilesRepositoryImpl() {}

    public FilesRepositoryImpl(Context context, String appSpecDSFileName, String commonFilesDSFileName) {
        appSpecificDataSource = new AppSpecificDataSource(context, appSpecDSFileName);
        commonFilesDataSource = new CommonFilesDataSource(context, commonFilesDSFileName);
    }

    @Override
    public void createFiles(Context context, String appSpecDSFileName, String commonFilesDSFileName) {
        if (appSpecDSFileName != null)
            appSpecificDataSource = new AppSpecificDataSource(context, appSpecDSFileName);
        if (commonFilesDSFileName != null)
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
