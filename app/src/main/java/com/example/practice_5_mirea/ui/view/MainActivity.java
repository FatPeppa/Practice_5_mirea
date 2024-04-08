package com.example.practice_5_mirea.ui.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.practice_5_mirea.R;

public class MainActivity extends AppCompatActivity {
    String sharedText = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();

        if (intent != null
                && Intent.ACTION_SEND.equals(intent.getAction()))
        {
            sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        }
    }


    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = getIntent();

        if (intent != null
                && Intent.ACTION_SEND.equals(intent.getAction()))
        {
            Bundle bundle = new Bundle();
            bundle.putString("sharedText", sharedText);

            View view = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment).getView();
            Navigation.findNavController(view).navigate(R.id.firstFragment, bundle);
        }
    }
}