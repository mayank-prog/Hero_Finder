package com.example.asus.herofinder;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class OutputActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output);

        Bundle bundle = getIntent().getExtras();

        // for resistence
        double Rfinal = bundle.getDouble("et_Rfinal");
        double Rmajor = bundle.getDouble("et_Rmajor");
        double Rminor = bundle.getDouble("et_Rminor");
        double Rlow = bundle.getDouble("et_RLow");
        TextView et_Rfinal = (TextView) findViewById(R.id.et_Rfinal);
        TextView et_Rmajor = (TextView) findViewById(R.id.et_Rmajor);
        TextView et_Rminor = (TextView) findViewById(R.id.et_Rminor);
        TextView et_Rlow = (TextView) findViewById(R.id.et_RLow);


        et_Rfinal.setText(Double.toString(Rfinal));
        et_Rmajor.setText(Double.toString(Rmajor));
        et_Rminor.setText(Double.toString(Rminor));
        et_Rlow.setText(Double.toString(Rlow));

        // for natural

        double natural1 = bundle.getDouble("et_Npoint1");
        double natural2 = bundle.getDouble("et_Npoint2");
        TextView et_Npoint1 = (TextView) findViewById(R.id.et_Npoint1);
        TextView et_Npoint2 = (TextView) findViewById(R.id.et_Npoint2);

        et_Npoint1.setText(Double.toString(natural1));
        et_Npoint2.setText(Double.toString(natural2));

        //for support values

        double Slow = bundle.getDouble("et_Slow");
        double Sminor = bundle.getDouble("et_Sminor");
        double Smajor = bundle.getDouble("et_Smajor");
        double Sfinal = bundle.getDouble("et_Sfinal");
        TextView et_Slow = (TextView) findViewById(R.id.et_Slow);
        TextView et_Sminor = (TextView) findViewById(R.id.et_Sminor);
        TextView et_Smajor = (TextView) findViewById(R.id.et_Smajor);
        TextView et_Sfinal = (TextView) findViewById(R.id.et_Sfinal);

        et_Slow.setText(Double.toString(Slow));
        et_Sminor.setText(Double.toString(Sminor));
        et_Smajor.setText(Double.toString(Smajor));
        et_Sfinal.setText(Double.toString(Sfinal));


        //history
    }

}
