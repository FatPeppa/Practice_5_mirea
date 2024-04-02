package com.example.practice_5_mirea.data.dataSource.Files;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class AppSpecificDataSource {
    private final Context appSpDSContext;
    private final File dsFile;
    private final String dsFileName;

    public AppSpecificDataSource(Context context, String filename) {
        this.appSpDSContext = context;
        this.dsFileName = filename;
        this.dsFile = new File(context.getFilesDir(), filename);
    }

    public String readAppSpecificDS() {
        if (!dsFile.exists()) return null;

        FileInputStream fis = null;

        try {
            fis = appSpDSContext.openFileInput(dsFileName);
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

    public void writeAppSpecificDS(String inputContent) {
        try (FileOutputStream fos = appSpDSContext.openFileOutput(dsFileName, Context.MODE_PRIVATE)) {
            fos.write(inputContent.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
