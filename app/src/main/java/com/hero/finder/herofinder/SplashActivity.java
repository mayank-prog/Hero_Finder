  package com.hero.finder.herofinder;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;

import androidx.appcompat.app.AppCompatActivity;

import com.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

   private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        firebaseAuth = FirebaseAuth.getInstance();

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser =  firebaseAuth.getCurrentUser();
        if(currentUser == null){
            SystemClock.sleep(3000);
            Intent registerIntent = new Intent(SplashActivity.this,RegisterActivity.class);
            startActivity(registerIntent);
            finish();
        }else{
              SystemClock.sleep(3000);
            Intent MainIntent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(MainIntent);
            finish();
        }
    }
}
