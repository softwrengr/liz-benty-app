package com.square.apple.pdf_app.fragments;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.square.apple.pdf_app.R;
import com.square.apple.pdf_app.utils.Utilities;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Intent.EXTRA_TEXT;

public class HomeFragment extends Fragment implements View.OnClickListener {
    View view;
    @BindView(R.id.btn_dominant)
    Button btnDominant;
    @BindView(R.id.btn_influncer)
    Button btnInfluncer;
    @BindView(R.id.btn_steady)
    Button btnSteady;
    @BindView(R.id.btn_conscientious)
    Button btnConscientious;


    public static HomeFragment createFor(String text) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_TEXT, text);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        initUI();
        customActionBar();
        return view;
    }

    private void initUI() {
        ButterKnife.bind(this, view);
        btnDominant.setOnClickListener(this);
        btnInfluncer.setOnClickListener(this);
        btnSteady.setOnClickListener(this);
        btnConscientious.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_dominant:
                gotoPdf("(D) Dominant", "dominant.pdf");
                break;
            case R.id.btn_influncer:
                gotoPdf("(I) Infuencer", "influencer.pdf");
                break;
            case R.id.btn_steady:
                gotoPdf("(S) Steady", "steady.pdf");
                break;
            case R.id.btn_conscientious:
                gotoPdf("(C) Concientious", "concientious.pdf");
                break;

        }


    }

    private void gotoPdf(String strTitle, String strPdf) {
        Utilities.putValueInEditor(getActivity()).putString("title", strTitle).commit();
        Utilities.putValueInEditor(getActivity()).putString("pdf_file_name", strPdf).commit();
        Utilities.withOutBackStackConnectFragment(getActivity(), new PdfViewFragment());
    }

    public void customActionBar() {
        android.support.v7.app.ActionBar mActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setDisplayHomeAsUpEnabled(false);
        mActionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));
        LayoutInflater mInflater = LayoutInflater.from(getActivity());
        View mCustomView = mInflater.inflate(R.layout.custom_actionbar, null);
        TextView tvTitle = mCustomView.findViewById(R.id.title);
        tvTitle.setText("Home");
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.show();

    }


}
