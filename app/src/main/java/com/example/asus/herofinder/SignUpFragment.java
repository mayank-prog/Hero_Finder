package com.example.asus.herofinder;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {


    public SignUpFragment() {
        // Required empty public constructor
    }

    private FrameLayout parentFrameLayout;
    private TextView alreadyHaveAnAccount;

    private EditText email,fullName,password,confirmPassword;

    private Button signUpBtn;

    private ProgressBar progressBar;

    private FirebaseAuth firebaseAuth;

    private FirebaseFirestore firebaseFirestore;

    private String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        parentFrameLayout = (FrameLayout) getActivity().findViewById(R.id.register_framelayout);

        alreadyHaveAnAccount = (TextView) view.findViewById(R.id.tv_already_have_an_account);
        email = view.findViewById(R.id.sign_up_email);
        fullName = view.findViewById(R.id.sign_up_fullName);
        password = view.findViewById(R.id.sign_up_password);
        confirmPassword = view.findViewById(R.id.sign_up_confirm_password);

        signUpBtn = view.findViewById(R.id.sign_up_btn);

        progressBar = view.findViewById(R.id.sign_up_progressbar);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore =FirebaseFirestore.getInstance();
        return  view;
    }

    @Override

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        alreadyHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SignInFragment());
            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        fullName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
             checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        confirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
             checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEmailAndPassword();
            }
        });

}

    private void setFragment(Fragment fragment) {

        FragmentTransaction fragmentTransation = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransation.setCustomAnimations(R.anim.slide_from_left,R.anim.slideout_from_rigth);
        fragmentTransation.replace(parentFrameLayout.getId(),fragment);
        fragmentTransation.commit();
    }

    private void checkInputs(){
        if(!TextUtils.isEmpty(email.getText())){
            if (!TextUtils.isEmpty(fullName.getText())){
                if (!TextUtils.isEmpty(password.getText()) && password.length()>=8){
                    if(!TextUtils.isEmpty(confirmPassword.getText())){
                        signUpBtn.setEnabled(true);
                    }else{
                        signUpBtn.setEnabled(false);
                    }
                }else{
                        signUpBtn.setEnabled(false);
                }

            } else{
                    signUpBtn.setEnabled(false);
            }
        }else {
              signUpBtn.setEnabled(false);
        }
    }

    private void checkEmailAndPassword(){

        Drawable erroricon = getResources().getDrawable(R.drawable.ic_erroricone);
        erroricon.setBounds(0,0,erroricon.getIntrinsicWidth(),erroricon.getIntrinsicHeight());
    if (email.getText().toString().matches(emailpattern)){
        if (password.getText().toString().equals(confirmPassword.getText().toString())){

            progressBar.setVisibility(View.VISIBLE);
            signUpBtn.setEnabled(false);
            firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                   .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                          if(task.isSuccessful()){

                              Map<Object,String> userdata = new HashMap<>();
                              userdata.put("Name",fullName.getText().toString());
                              userdata.put("Email",email.getText().toString());

                              firebaseFirestore.collection("USERS")
                                      .add(userdata)
                                      .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                          @Override
                                          public void onComplete(@NonNull Task<DocumentReference> task) {
                                              if (task.isSuccessful()){

                                                  Intent mainIntent = new Intent(getActivity(), MainActivity.class);
                                                  startActivity(mainIntent);
                                                  getActivity().finish();

                                              }else{
                                                  progressBar.setVisibility(View.INVISIBLE);
                                                  signUpBtn.setEnabled(true);
                                                  String error = task.getException().getMessage();
                                                  Toast.makeText(getActivity(),error,Toast.LENGTH_SHORT).show();
                                              }
                                          }
                                      });

                          }else{
                              progressBar.setVisibility(View.INVISIBLE);
                              signUpBtn.setEnabled(true);
                              String error = task.getException().getMessage();
                              Toast.makeText(getActivity(),error,Toast.LENGTH_SHORT).show();
                          }
                       }
                   });
        }else{
            confirmPassword.setError("Password does't Matched",erroricon);
        }

    }else{
       email.setError("Invalid Email!",erroricon);
    }
    }
}
