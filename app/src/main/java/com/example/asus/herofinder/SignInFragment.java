package com.example.asus.herofinder;

import android.content.Intent;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends Fragment {


    public SignInFragment() {
        // Required empty public constructor
    }

    private TextView dontHaveAnAccount;
    private FrameLayout parentFrameLayout;
    private Button sign_in_btn;

    private ProgressBar progressBar;

    private TextView forgotPassword;

    private EditText email,password;

    private FirebaseAuth firebaseAuth;

    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_sign_in, container, false);
        dontHaveAnAccount = (TextView) view.findViewById(R.id.tv_dont_have_an_account);
        forgotPassword = view.findViewById(R.id.sign_in_forgot_password);
        parentFrameLayout = (FrameLayout) getActivity().findViewById(R.id.register_framelayout);
        email = view.findViewById(R.id.sign_in_email);
        password = view.findViewById(R.id.sign_in_password);
        progressBar = view.findViewById(R.id.sign_in_progressbar);
        sign_in_btn = view.findViewById(R.id.sign_in_btn);

        firebaseAuth = FirebaseAuth.getInstance();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dontHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SignUpFragment());
            }

        });
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new ResetPasswprdFragment());
            }
        });
        //@author Mayank.sharma3
        email.addTextChangedListener(new TextWatcher() {
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
        password.addTextChangedListener(new TextWatcher() {
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

        sign_in_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEmailAndPassword();
            }
        });
    }


    private void setFragment(Fragment fragment) {
            FragmentTransaction fragmentTransation = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransation.setCustomAnimations(R.anim.slide_from_right,R.anim.slideout_from_left);
            fragmentTransation.replace(parentFrameLayout.getId(),fragment);
            fragmentTransation.commit();
        }


    private void checkIputs() {
           if(!TextUtils.isEmpty(email.getText())){
               if(!TextUtils.isEmpty(password.getText())){
                      sign_in_btn.setEnabled(true);
               }else{
                   sign_in_btn.setEnabled(false);
               }
           }else{
                  sign_in_btn.setEnabled(false);
           }
      }

      private void checkEmailAndPassword(){
         if(email.getText().toString().matches(emailPattern)){
             if(password.length()>8){

                     progressBar.setVisibility(View.VISIBLE);
                     sign_in_btn.setEnabled(false);
                     firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                             .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                 @Override
                                 public void onComplete(@NonNull Task<AuthResult> task) {
                                     if(task.isSuccessful()){
                                         Intent intent = new Intent(getActivity(), MainActivity.class);
                                         startActivity(intent);
                                         getActivity().finish();
                                     }else{
                                         progressBar.setVisibility(View.INVISIBLE);
                                         sign_in_btn.setEnabled(true);
                                         String error = task.getException().getMessage();
                                         Toast.makeText(getActivity(),error,Toast.LENGTH_SHORT).show();
                                     }
                                 }
                             });
             }else{
                 Toast.makeText(getActivity(),"Incorrect Email or Password",Toast.LENGTH_SHORT).show();
             }
         }else{
             Toast.makeText(getActivity(),"Incorrect Email or Password",Toast.LENGTH_SHORT).show();
         }

      }
    }






