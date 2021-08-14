package com.hero.finder.herofinder.ui.gallery;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.PaymentError;
import com.PaymentSuccess;
import com.hero.finder.herofinder.OutputActivity;
import com.hero.finder.herofinder.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.razorpay.Checkout;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class GalleryFragment extends Fragment implements RadioGroup.OnCheckedChangeListener{

    private GalleryViewModel galleryViewModel;

    RadioButton Monthly1, Quarterly1, Half_yearly1, Yearly1;
    private EditText et_open, et_close,et_high,et_low;
    private Button calculate_btn;
    int Monthly, Quarterly, Half_yearly, Yearly;

    Dialog dialog;
    FirebaseFirestore fstore;
    FirebaseAuth fAuth;
    FirebaseUser CurrentUser;
    DatabaseReference reference;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =  new ViewModelProvider(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);
        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        fAuth = FirebaseAuth.getInstance();
        CurrentUser = fAuth.getCurrentUser();
        fstore = FirebaseFirestore.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();
        Checkout.preload(getActivity().getApplicationContext());
        et_open = (EditText)root.findViewById(R.id.et_open);
        et_high = (EditText)root.findViewById(R.id.et_high);
        et_low = (EditText)root.findViewById(R.id.et_low);
        et_close = (EditText)root.findViewById(R.id.et_close);


        calculate_btn = (Button)root.findViewById(R.id.calculate_btn);
        dialog =new Dialog(getActivity());
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


                //bundel for output
                Bundle bundle = new Bundle();

                bundle.putDouble("Low",Low);
                bundle.putDouble("High",High);
                bundle.putDouble("Close",Close);
                bundle.putDouble("Open",Open);

//                only for check purpos

                String userID = fAuth.getCurrentUser().getUid();
                DocumentReference documentReference=fstore.collection("Subscription").document(userID);
                documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String sid = (String) documentSnapshot.get("Email");
                        String email = fAuth.getCurrentUser().getEmail();
                        if(sid!=null){
                            if (sid.equals(email)) {
                                Intent i = new Intent(getActivity(), OutputActivity.class);
                                i.putExtras(bundle);
                                startActivity(i);
                                return;
                            } else {
                                OpenDialog();
                            }
                        }
                        else{
                            OpenDialog();
                            Toast.makeText(getActivity(), "Subscribe First", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Network error", Toast.LENGTH_SHORT).show();
                    }
                });
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

        galleryViewModel = new ViewModelProvider(requireActivity()).get(GalleryViewModel.class);

        galleryViewModel.getOnSuccess().observe(getViewLifecycleOwner(), onSuccess -> {
            if (onSuccess) {
                PaymentSuccess success = galleryViewModel.getPaymentSuccess();
                onPaymentSuccess(success.s());
            }
        });

        galleryViewModel.getOnError().observe(getViewLifecycleOwner(), onError -> {
            if (onError) {
                PaymentError error = galleryViewModel.getPaymentError();
                onPaymentError(error.s(), error.i());
            }
        });

        return root;
    }
    private int price;
    public void OpenDialog() {
        dialog.setContentView(R.layout.activity_payment);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

      //data from firebase for card redio
        Monthly1 = dialog.findViewById(R.id.Monthly);
        Quarterly1 = dialog.findViewById(R.id.Quarterly);
        Half_yearly1 = dialog.findViewById(R.id.Half_yearly);
        Yearly1 = dialog.findViewById(R.id.yearly);
        reference.child("Price_Dialog").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Monthly1.setText(snapshot.child("Monthly").getValue().toString());
                Quarterly1.setText(snapshot.child("Quarterly").getValue().toString());
                Half_yearly1.setText(snapshot.child("Half yearly").getValue().toString());
                Yearly1.setText(snapshot.child("Yearly").getValue().toString());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Oops! Something went wrong.", Toast.LENGTH_SHORT).show();
            }
        });
        //pricing data
        try {
        onStartDialog();
           } catch(Exception e) {
              Log.e(TAG, "Oops! Something went wrong. Try again.", e);
          }
        //end datafatching for radio set text
        Button pay = (Button) dialog.findViewById(R.id.pay_btn);
        RadioGroup radioGroup = (RadioGroup) dialog.findViewById(R.id.radioOptions);
        radioGroup.setOnCheckedChangeListener(this);
        Checkout.preload(getActivity().getApplicationContext());//payment checkout

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              makepayment();
            }
        });
    }

    public void onStartDialog(){
        reference= FirebaseDatabase.getInstance().getReference().child("Price");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {

                    if (snapshot != null) {
                        Monthly = Integer.parseInt(snapshot.child("Monthly").getValue().toString());
                        Quarterly = Integer.parseInt(snapshot.child("Quarterly").getValue().toString());
                        Half_yearly = Integer.parseInt(snapshot.child("Half yearly").getValue().toString());
                        Yearly = Integer.parseInt(snapshot.child("Yearly").getValue().toString());
                    } else {
                        Toast.makeText(getActivity(), "Please connect to the Hero Finder Support", Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    Toast.makeText(getActivity(), "Oops! Something went wrong.", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(),"Please connect to the Hero Finder Support Help",Toast.LENGTH_LONG).show();
            }
        });
    }
    public void makepayment() {
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_live_DX6GCaGWUlPr1z");
//        checkout.setKeyID("rzp_test_evxJtZIlJlIzZ0");
        checkout.setImage(R.mipmap.logo);
        try {
            JSONObject options = new JSONObject();

            options.put("name", "Hero Finder");
            options.put("description", "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
//            options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", price*100);//pass amount in currency subunits
            options.put("email", CurrentUser.getEmail());
            //options.put("prefill.contact","");
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(getActivity(), options);   //chenge activity to getActivity

        } catch(Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }

    }
    public void onPaymentSuccess(String s) {
        galleryViewModel.setOnSuccess(false);
        AddSubscriptionData();
        dialog.dismiss();
        Checkout.clearUserData(getContext());
        Toast.makeText(getActivity(), "Successfully payment", Toast.LENGTH_SHORT).show();

    }
    public void onPaymentError( String s,int i) {
        galleryViewModel.setOnError(false);
        Checkout.clearUserData(getContext());
//        Toast.makeText(getActivity(), "Payment failed: " ,Toast.LENGTH_SHORT).show();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Payment");
        builder.setMessage("Your Level Finder will start working within an hour as soon as your transaction get successfully completed. Please wait and support us.\n" +
                "In case of payment failure. Please try again.");
        builder.show();
    }

    private void AddSubscriptionData() {

        String Email= CurrentUser.getEmail();
        String uid = CurrentUser.getUid();
        DocumentReference databaseReference = fstore.collection("Subscription").document(uid);
        Map<String,Object> data  =new HashMap<>();
        data.put("UID",uid);
        data.put("Email",Email);
        data.put("Date" ,  FieldValue.serverTimestamp());
        databaseReference.set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getActivity(),"Subscription Successfully.",Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(),"Please connect to the Hero Finder Support Help",Toast.LENGTH_LONG).show();
            }
        });

    }
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch(checkedId){
            case R.id.Monthly:
                price = Monthly;
                 break;
            case R.id.Quarterly:
                price = Quarterly;
                break;
            case R.id.Half_yearly:
                price = Half_yearly;
                break;
            case R.id.yearly:
                price = Yearly;
                break;
            default:
                price = 999;
        }
    }
}
