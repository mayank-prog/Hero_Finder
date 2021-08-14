package com.hero.finder.herofinder;

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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResetPasswprdFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResetPasswprdFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ResetPasswprdFragment() {
        // Required empty public constructor
    }
    private EditText registeredEmail;
    private Button resetPassword;
    private TextView goBack;
    private FrameLayout parentFrameLayout;
    private FirebaseAuth firebaseAuth;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ResetPasswprdFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ResetPasswprdFragment newInstance(String param1, String param2) {
        ResetPasswprdFragment fragment = new ResetPasswprdFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reset_passwprd, container, false);
        registeredEmail = view.findViewById(R.id.forgot_password_email);
        resetPassword = (Button) view.findViewById(R.id.reset_password_btn);
        goBack = (TextView) view.findViewById(R.id.forgot_password_goBack);
        parentFrameLayout = getActivity().findViewById(R.id.register_framelayout);
        firebaseAuth = FirebaseAuth.getInstance();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        registeredEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!TextUtils.isEmpty(resetPassword.getText())){
                    resetPassword.setEnabled(true);
                }else{
                    resetPassword.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.sendPasswordResetEmail(registeredEmail.getText().toString())
                       .addOnCompleteListener(new OnCompleteListener<Void>() {
                           @Override
                           public void onComplete(@NonNull Task<Void> task) {
                               if(task.isSuccessful()){
                                  Toast.makeText(getActivity(),"Email sent successfully!",Toast.LENGTH_LONG).show();
                               }else{
                                String error = task.getException().getMessage();
                                   Toast.makeText(getActivity(),error,Toast.LENGTH_SHORT).show();
                               }
                           }
                       });
            }
        });

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              setFragment(new SignInFragment());
            }
        });
    }


    private void setFragment(Fragment fragment) {

        FragmentTransaction fragmentTransation = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransation.setCustomAnimations(R.anim.slide_from_left,R.anim.slideout_from_rigth);
        fragmentTransation.replace(parentFrameLayout.getId(),fragment);
        fragmentTransation.commit();
    }
}