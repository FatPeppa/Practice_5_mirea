package com.example.practice_5_mirea.data.repository;

public interface FilesRepository {
    void writeIntoAppSpecDS(String inputContent);
    boolean writeIntoCommonFilesDS(String inputContent);

    String readFromAppSpecDS();
    String readFromCommonFilesDS();
}
