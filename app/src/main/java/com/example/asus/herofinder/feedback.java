package com.example.asus.herofinder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.asus.herofinder.ui.main.FeedbackFragment;

public class feedback extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, FeedbackFragment.newInstance())
                    .commitNow();
        }
    }
}