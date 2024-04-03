package com.example.practice_5_mirea.data.dataSource.SP;

import static android.provider.Settings.System.getString;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.practice_5_mirea.R;

public class SharedPreferencesDataSource {
    private final String fileName = "com.example.practice_5_mirea.PREFERENCE_FILE_KEY";
    private final Context context;
    private final SharedPreferences sharedPref;

    public SharedPreferencesDataSource(Context context) {
        this.context = context;

        sharedPref = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }

    public void writeContent(String key, String content) {
        SharedPreferences.Editor editor = sharedPref.edit();
        if (!sharedPref.getString(key, "").equals("")) editor.remove(key);
        editor.putString("R.string." + key, content);
        editor.apply();
    }

    public void writeContent(String key, int content) {
        SharedPreferences.Editor editor = sharedPref.edit();
        if (sharedPref.getInt(key, -1) != -1) editor.remove(key);
        editor.putInt("R.integer." + key, content);
        editor.apply();
    }

    public void writeContent(String key, boolean content) {
        SharedPreferences.Editor editor = sharedPref.edit();
        if (sharedPref.getBoolean(key, false)) editor.remove(key);
        editor.putBoolean("R.boolean." + key, content);
        editor.apply();
    }

    public int getIntRecord(String key) {
        int defaultValue = -1;
        return sharedPref.getInt(key, defaultValue);
    }

    public String getStringRecord(String key) {
        String defaultValue = "";
        return sharedPref.getString(key, defaultValue);
    }

    public boolean getBooleanRecord(String key) {
        boolean defaultValue = false;
        return sharedPref.getBoolean(key, defaultValue);
    }
}
