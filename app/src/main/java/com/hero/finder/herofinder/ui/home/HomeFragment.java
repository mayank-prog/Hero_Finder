package com.hero.finder.herofinder.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.hero.finder.herofinder.HeroAbout;
import com.hero.finder.herofinder.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    RelativeLayout rl1 , level_finder, hero;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);


            }
        });

//        switch to history page

        rl1 = (RelativeLayout) root.findViewById(R.id.one_toch);
        rl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Open using below History Icon !", Toast.LENGTH_SHORT).show();
//                requireActivity().getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.nav_host_fragment, new SlideshowFragment())
//                        .commit();
            }
        });
        level_finder = (RelativeLayout) root.findViewById(R.id.leve_finder);
        level_finder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), HeroAbout.class);
                startActivity(i);
            }
        });
        hero = (RelativeLayout) root.findViewById(R.id.hero);
        hero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "We are working on it.", Toast.LENGTH_SHORT).show();
            }
        });

//        end history page switching

        return root;
    }

}