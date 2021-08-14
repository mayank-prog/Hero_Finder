package com.hero.finder.herofinder;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.hero.finder.herofinder.ui.main.TandCFragment;

public class TandC extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tand_c_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, TandCFragment.newInstance())
                    .commitNow();
        }
    }
}