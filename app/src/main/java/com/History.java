package com;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.hero.finder.herofinder.R;

import java.text.DecimalFormat;

public class History extends AppCompatActivity {

    double Low,High,Close;
    TextView goBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

//        exterect here intent
        Low = Double.parseDouble(getIntent().getStringExtra("Lowhis").toString());
        High = Double.parseDouble(getIntent().getStringExtra("Highhis").toString());
        Close = Double.parseDouble(getIntent().getStringExtra("Closehis").toString());

        //Add formula

        double diffrence = High - Low;
        DecimalFormat df = new DecimalFormat("#.00");

        // for resistance values
        double et_Rfinal = Close+(diffrence*0.927);
        et_Rfinal= Double.parseDouble(df.format(et_Rfinal));
        double et_Rmajor = Close+(diffrence*0.691);
        et_Rmajor= Double.parseDouble(df.format(et_Rmajor));
        double et_Rminor = Close+(diffrence*0.545);
        et_Rminor= Double.parseDouble(df.format(et_Rminor));
        double et_RLow = Close+(diffrence*0.309);
        et_RLow= Double.parseDouble(df.format(et_RLow));

        TextView et_Rfinal1 = (TextView) findViewById(R.id.et_Rfinal);
        TextView et_Rmajor1 = (TextView) findViewById(R.id.et_Rmajor);
        TextView et_Rminor1 = (TextView) findViewById(R.id.et_Rminor);
        TextView et_Rlow1 = (TextView) findViewById(R.id.et_RLow);

        et_Rfinal1.setText(Double.toString(et_Rfinal));
        et_Rmajor1.setText(Double.toString(et_Rmajor));
        et_Rminor1.setText(Double.toString(et_Rminor));
        et_Rlow1.setText(Double.toString(et_RLow));

        // for natural values
        double et_Npoint1 = Close+(diffrence*0.073);
        et_Npoint1= Double.parseDouble(df.format(et_Npoint1));
        double et_Npoint2 = Close-(diffrence*0.073);
        et_Npoint2= Double.parseDouble(df.format(et_Npoint2));

        TextView et_Npoint11 = (TextView) findViewById(R.id.et_Npoint1);
        TextView et_Npoint22 = (TextView) findViewById(R.id.et_Npoint2);

        et_Npoint11.setText(Double.toString(et_Npoint1));
        et_Npoint22.setText(Double.toString(et_Npoint2));

        // for supports values
        double et_Slow = Close-(diffrence*0.309);
        et_Slow= Double.parseDouble(df.format(et_Slow));
        double et_Sminor = Close-(diffrence*0.545);
        et_Sminor= Double.parseDouble(df.format(et_Sminor));
        double et_Smajor = Close-(diffrence*0.691);
        et_Smajor= Double.parseDouble(df.format(et_Smajor));
        double et_Sfinal = Close-(diffrence*0.927);
        et_Sfinal= Double.parseDouble(df.format(et_Sfinal));

        TextView et_Slow1 = (TextView) findViewById(R.id.et_Slow);
        TextView et_Sminor1 = (TextView) findViewById(R.id.et_Sminor);
        TextView et_Smajor1 = (TextView) findViewById(R.id.et_Smajor);
        TextView et_Sfinal1 = (TextView) findViewById(R.id.et_Sfinal);

        et_Slow1.setText(Double.toString(et_Slow));
        et_Sminor1.setText(Double.toString(et_Sminor));
        et_Smajor1.setText(Double.toString(et_Smajor));
        et_Sfinal1.setText(Double.toString(et_Sfinal));

        goBack = findViewById(R.id.goBack);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

    }
}