package com.example.asus.herofinder;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class shareDilog extends AppCompatActivity {

    private Button share_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_dilog);

        share_btn = (Button) findViewById(R.id.share_btn);
        share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(shareDilog.this,"Thank you",Toast.LENGTH_SHORT).show();
                ApplicationInfo api = getApplicationContext().getApplicationInfo();
                String apkpath = api.sourceDir;
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
//                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(apkpath)));
                intent.putExtra(Intent.EXTRA_TEXT,"(Hero Finder) I suggest this app for you : https://play.google.com/store/apps/details?id=com.android.chrome");
                intent.setType("text/plain");
                startActivity(intent.createChooser(intent,"ShareVia"));

            }
        });
    }
}