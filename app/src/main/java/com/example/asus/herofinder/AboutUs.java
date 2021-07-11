package com.example.asus.herofinder;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.asus.herofinder.ui.main.AboutUsFragment;

public class AboutUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, AboutUsFragment.newInstance())
                    .commitNow();
        }
    }
}