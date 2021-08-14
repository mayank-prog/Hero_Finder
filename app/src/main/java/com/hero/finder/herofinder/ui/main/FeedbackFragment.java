package com.hero.finder.herofinder.ui.main;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.hero.finder.herofinder.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FeedbackFragment extends Fragment {

    private MainViewModel mViewModel;

    private CheckBox CheckBoxResponse;
    private Button SendFeedback_btn;
    private EditText EditText_FeedbackBody;
    FirebaseAuth fAuth;
    FirebaseUser CurrentUser;
    FirebaseDatabase db;

    public static FeedbackFragment newInstance() {
        return new FeedbackFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.main_fragment3, container, false);

        CheckBoxResponse = (CheckBox) root.findViewById(R.id.CheckBoxResponse);
        SendFeedback_btn = (Button) root.findViewById(R.id.SendFeedback_btn);
        EditText_FeedbackBody = (EditText) root.findViewById(R.id.EditText_FeedbackBody);
        CheckBoxResponse.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(CheckBoxResponse.isChecked()){
                    if(!TextUtils.isEmpty(EditText_FeedbackBody.getText())){
                        SendFeedback_btn.setEnabled(true);
                    }
                }
            }
        });
        SendFeedback_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveData();
                SendFeedback_btn.setEnabled(false);
                EditText_FeedbackBody.setText(null);
            }
        });

        return root;
    }

    public void SaveData(){

        fAuth = FirebaseAuth.getInstance();
        CurrentUser = fAuth.getCurrentUser();
        String userID = fAuth.getCurrentUser().getUid();
//        String DATA = EditText_FeedbackBody.getText().toString()
        Toast.makeText(getActivity(),"Your Feedback is submitted.",Toast.LENGTH_SHORT).show();
        FirebaseDatabase db =FirebaseDatabase.getInstance();
        DatabaseReference reference =db.getReference("feedback");
        reference.child(userID).setValue(EditText_FeedbackBody.getText().toString());


    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        // TODO: Use the ViewModel
    }

}