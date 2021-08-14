package com.hero.finder.herofinder.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.hero.finder.herofinder.R;

public class TandCFragment extends Fragment {

    private tandcMainViewModel mViewModel;

    private CheckBox checkBox;
    private Button accept_btn;

    public static TandCFragment newInstance() {
        return new TandCFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.main_fragmenttanc, container, false);
//        return inflater.inflate(R.layout.main_fragmenttanc, container, false);

        checkBox = (CheckBox) root.findViewById(R.id.check_Box);
        accept_btn = (Button) root.findViewById(R.id.accept_btn);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkBox.isChecked()){
                    accept_btn.setEnabled(true);
                }
            }
        });
        accept_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Accepted", Toast.LENGTH_SHORT).show();
                accept_btn.setEnabled(false);
            }
        });
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(tandcMainViewModel.class);
        // TODO: Use the ViewModel
    }


}