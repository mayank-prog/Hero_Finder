package com.hero.finder.herofinder;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;

public class OutputActivity extends AppCompatActivity {

    private Button save_btn;
    private EditText c_name;
    FirebaseAuth fAuth;
    FirebaseUser CurrentUser;
    double open,Low,High,Close;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output);

        Bundle bundle = getIntent().getExtras();

        // for output
        Low = bundle.getDouble("Low");
        High = bundle.getDouble("High");
        Close = bundle.getDouble("Close");
        open = bundle.getDouble("Open");

        //formula
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

        // save data for make history
        save_btn = findViewById(R.id.save_btn);
        c_name = findViewById(R.id.c_name);

        c_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkIputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveData();
            }
        });

    }

    private void checkIputs() {

            if(!TextUtils.isEmpty(c_name.getText())){
                save_btn.setEnabled(true);
            }else{
                save_btn.setEnabled(false);
            }
    }

    public void SaveData(){

        fAuth = FirebaseAuth.getInstance();
        CurrentUser = fAuth.getCurrentUser();
        String userID = fAuth.getCurrentUser().getUid();
        c_name = findViewById(R.id.c_name);
        String c_name1=c_name.getText().toString().trim();
        c_name1=c_name1.replaceAll("[^a-zA-Z0-9]", "_");
        String Open1 = String.valueOf(open);
        String Low1 = String.valueOf(Low);
        String High1 = String.valueOf(High);
        String Close1 = String.valueOf(Close);
        Toast.makeText(getApplicationContext(),"History Data Successfuly Inserted",Toast.LENGTH_LONG).show();
        stockData data = new stockData(Low1, High1, Close1, Open1, c_name1);
        FirebaseDatabase db =FirebaseDatabase.getInstance();
        DatabaseReference reference =db.getReference("History");
        reference.child(userID).child(c_name1).setValue(data);
        c_name.setText(null);

    }

}
