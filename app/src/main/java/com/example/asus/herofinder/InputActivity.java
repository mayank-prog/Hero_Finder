package com.example.asus.herofinder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

//import android.support.v7.app.AppCompatActivity;

public class InputActivity extends AppCompatActivity {

    private EditText et_open, et_close,et_high,et_low;
    private Button calculate_btn;

    double et_Rfinal,et_Rmajor,et_Rminor,et_RLow;
    double et_Npoint1,et_Npoint2;
    double et_Slow,et_Sminor,et_Smajor,et_Sfinal;
    double diffrence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        et_open = (EditText) findViewById(R.id.et_open);
        et_high = (EditText) findViewById(R.id.et_high);
        et_low = (EditText) findViewById(R.id.et_low);
        et_close = (EditText) findViewById(R.id.et_close);

        calculate_btn = (Button) findViewById(R.id.calculate_btn);

        calculate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String open = et_open.getText().toString().trim();
                String high = et_high.getText().toString().trim();
                String low  = et_low.getText().toString().trim();
                String close = et_close.getText().toString().trim();

                if(close.isEmpty()){
                    et_open.setError("Enter Close Price");
                    return;
                }
                if(open.isEmpty()){
                    et_high.setError("Enter Open Price");
                    return;
                }
                if(high.isEmpty()){
                    et_low.setError("Enter High Price");
                    return;
                }
                if(low.isEmpty()){
                    et_close.setError("Enter Low Price");
                    return;
                }
                double Open = Double.parseDouble(et_open.getText().toString());
                double High = Double.parseDouble(et_high.getText().toString());
                double Low = Double.parseDouble(et_low.getText().toString());
                double Close = Double.parseDouble(et_close.getText().toString());

                diffrence = High - Low;

                //for resistence values
                et_Rfinal = Close+(diffrence*0.927);
                et_Rmajor = Close+(diffrence*0.691);
                et_Rminor = Close+(diffrence*0.545);
                et_RLow = Close+(diffrence*0.309);


                // for natural values
                et_Npoint1 = Close+(diffrence*0.073);
                et_Npoint2 = Close-(diffrence*0.073);

                //for support values

                et_Slow = Close-(diffrence*0.309);
                et_Sminor = Close-(diffrence*0.545);
                et_Smajor = Close-(diffrence*0.691);
                et_Sfinal = Close-(diffrence*0.927);

                Bundle bundle = new Bundle();

                //bundle for resistence
                bundle.putDouble("et_Rfinal",et_Rfinal);
                bundle.putDouble("et_Rmajor",et_Rmajor);
                bundle.putDouble("et_Rminor",et_Rminor);
                bundle.putDouble("et_RLow",et_RLow);

                //bundle for natural

                bundle.putDouble("et_Npoint1",et_Npoint1);
                bundle.putDouble("et_Npoint2",et_Npoint2);

                // bundle for support values

                bundle.putDouble("et_Slow",et_Slow);
                bundle.putDouble("et_Sminor",et_Sminor);
                bundle.putDouble("et_Smajor",et_Smajor);
                bundle.putDouble("et_Sfinal",et_Sfinal);


                Intent i = new Intent(InputActivity.this,OutputActivity.class);
                i.putExtras(bundle);
                startActivity(i);
            }
        });

        TextView reset_btn = (TextView) findViewById(R.id.reset_btn);
        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_open.setText("");
                et_close.setText("");
                et_low.setText("");
                et_high.setText("");
            }
        });
    }
}
