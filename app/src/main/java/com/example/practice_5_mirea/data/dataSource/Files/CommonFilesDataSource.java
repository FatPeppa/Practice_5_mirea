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
                if (checkPermission()) {
                    File sdcard = Environment.getExternalStorageDirectory();

                    if (file == null || !file.exists()) {
                        //File dir = new File(sdcard.getAbsolutePath() + "/test");
                        //if (!dir.mkdir()) return false;
                        //dir.mkdir();
                        String dir = sdcard.getAbsolutePath() + "/someFolder/";
                        file = new File(dir + fileName + ".txt");

                        //file = new File(sdcard.getAbsolutePath() + "/" + fileName + ".txt");
                        try {
                            new File(dir).mkdirs();     // make sure to call mkdirs() when creating new directory
                            file.createNewFile();

                            //file.createNewFile();
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
                    //requestPermission(); // Code for permission
                }
            }
        }

        return true;
    }

    public String readFile() {
        if (file == null || !file.exists()) return null;

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


    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    //перенести в код главного фрагмента
    /*
    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(MainActivity.this, "Write External Storage permission allows us to create files. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] = = PackageManager.PERMISSION_GRANTED) {
                Log.e("value", "Permission Granted, Now you can use local drive .");
            } else {
                Log.e("value", "Permission Denied, You cannot use local drive .");
            }
            break;
        }
    }*/
}
