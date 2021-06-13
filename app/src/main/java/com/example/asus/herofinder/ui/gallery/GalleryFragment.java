package com.example.asus.herofinder.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.asus.herofinder.OutputActivity;
import com.example.asus.herofinder.R;

import java.text.DecimalFormat;


//import com.R;
public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;

    private EditText et_open, et_close,et_high,et_low;
    private Button calculate_btn;

    double et_Rfinal,et_Rmajor,et_Rminor,et_RLow;
    double et_Npoint1,et_Npoint2;
    double et_Slow,et_Sminor,et_Smajor,et_Sfinal;
    double diffrence;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);
        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        et_open = (EditText)root.findViewById(R.id.et_open);
        et_high = (EditText)root.findViewById(R.id.et_high);
        et_low = (EditText)root.findViewById(R.id.et_low);
        et_close = (EditText)root.findViewById(R.id.et_close);

        calculate_btn = (Button)root.findViewById(R.id.calculate_btn);

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

                DecimalFormat df = new DecimalFormat("#.00");

                //for resistence values
                et_Rfinal = Close+(diffrence*0.927);
                et_Rfinal= Double.parseDouble(df.format(et_Rfinal));
                et_Rmajor = Close+(diffrence*0.691);
                et_Rmajor= Double.parseDouble(df.format(et_Rmajor));
                et_Rminor = Close+(diffrence*0.545);
                et_Rminor= Double.parseDouble(df.format(et_Rminor));
                et_RLow = Close+(diffrence*0.309);
                et_RLow= Double.parseDouble(df.format(et_RLow));


                // for natural values
                et_Npoint1 = Close+(diffrence*0.073);
                et_Npoint1= Double.parseDouble(df.format(et_Npoint1));
                et_Npoint2 = Close-(diffrence*0.073);
                et_Npoint2= Double.parseDouble(df.format(et_Npoint2));

                //for support values

                et_Slow = Close-(diffrence*0.309);
                et_Slow= Double.parseDouble(df.format(et_Slow));
                et_Sminor = Close-(diffrence*0.545);
                et_Sminor= Double.parseDouble(df.format(et_Sminor));
                et_Smajor = Close-(diffrence*0.691);
                et_Smajor= Double.parseDouble(df.format(et_Smajor));
                et_Sfinal = Close-(diffrence*0.927);
                et_Sfinal= Double.parseDouble(df.format(et_Sfinal));

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


                Intent i = new Intent(getActivity(), OutputActivity.class);
                i.putExtras(bundle);
                startActivity(i);
            }
        });

        TextView reset_btn = (TextView)root.findViewById(R.id.reset_btn);
        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_open.setText("");
                et_close.setText("");
                et_low.setText("");
                et_high.setText("");
            }
        });
        return root;
    }
}