package com.hero.finder.herofinder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.hero.finder.herofinder.ui.main.HelpFragment;

public class Help extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, HelpFragment.newInstance())
                    .commitNow();
        }
    }
}