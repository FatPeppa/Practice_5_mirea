package com.example.practice_5_mirea.data.dataSource.Files;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;

import androidx.core.content.ContextCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class CommonFilesDataSource {
    private final String fileName;
    private final Context context;
    private File file;

    public CommonFilesDataSource(Context context, String fileName) {
        this.fileName = fileName;
        this.context = context;
    }

    public boolean writeContent(String inputContent) {
        if (!inputContent.isEmpty()) {
            String state = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                if (checkPermission(this.context)) {
                    File sdcard = Environment.getExternalStorageDirectory();

                    if (file == null || !file.exists()) {
                        String dir = sdcard.getAbsolutePath() + "/someFolder/";
                        file = new File(dir + fileName + ".txt");

                        try {
                            new File(dir).mkdirs();
                            file.createNewFile();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    FileOutputStream os = null;
                    try {
                        os = new FileOutputStream(file);
                        os.write(inputContent.getBytes());
                        os.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    return false;
                }
            }
        }

        return true;
    }

    public String readFile() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File sdcard = Environment.getExternalStorageDirectory();
            String dir = sdcard.getAbsolutePath() + "/someFolder/";
            file = new File(dir + fileName + ".txt");

            if (!file.exists()) return null;

            FileInputStream fis = null;

            try {
                fis = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

            String contents = null;
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            StringBuilder sb = new StringBuilder();

            try (BufferedReader reader = new BufferedReader(isr)) {
                String line = reader.readLine();
                while (line != null) {
                    sb.append(line).append('\n');
                    line = reader.readLine();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                contents = sb.toString();
            }

            return contents;
        }
        return null;
    }


    public static boolean checkPermission(Context context) {
        int result = ContextCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }
}
