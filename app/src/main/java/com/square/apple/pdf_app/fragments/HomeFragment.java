package com.square.apple.pdf_app.fragments;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.square.apple.pdf_app.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment {
    View view;
    @BindView(R.id.btn_dominant)
    Button btnDominant;
    @BindView(R.id.btn_influncer)
    Button btnInfluncer;
    @BindView(R.id.btn_steady)
    Button btnSteady;
    @BindView(R.id.btn_conscientious)
    Button btnConscientious;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        initUI();
        return view;
    }
    private void initUI() {
        ButterKnife.bind(this, view);
        btnDominant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new DominantFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).addToBackStack("").commit();
            }
        });

        btnInfluncer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new InfluncerFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).addToBackStack("").commit();
            }
        });
        btnSteady.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new SteadyFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).addToBackStack("").commit();
            }
        });
        btnConscientious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new ConscientiousFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).addToBackStack("").commit();
            }
        });

    }


}
