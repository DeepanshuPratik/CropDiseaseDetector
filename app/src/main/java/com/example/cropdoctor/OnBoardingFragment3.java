package com.example.cropdoctor;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import maes.tech.intentanim.CustomIntent;

public class OnBoardingFragment3 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FloatingActionButton fab;

        ViewGroup root = (ViewGroup)inflater.inflate(R.layout.fragment_on_boarding3,container,false);
        fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),DescriptionPlant1.class);
                startActivity(i);
                CustomIntent.customType(getActivity(),"right-to-left");
            }
        });
        return root;
    }
}
