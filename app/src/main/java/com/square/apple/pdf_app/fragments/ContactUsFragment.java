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


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactUsFragment extends Fragment {

    View parentView;
    @BindView(R.id.btn_schedule)
    Button btnSchedule;
    @BindView(R.id.tv_link)TextView tvLink;

    public static ContactUsFragment createFor(String text) {
        ContactUsFragment fragment = new ContactUsFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_TEXT, text);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_contact_us, container, false);
        ButterKnife.bind(this,parentView);
        customActionBar();

        btnSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utilities.putValueInEditor(getActivity()).putString("title", "Schedule").commit();
                Utilities.putValueInEditor(getActivity()).putString("url", "http://www.lizbentley.com/disc-app").commit();
                Utilities.withOutBackStackConnectFragment(getActivity(),new WebviewFragment());

            }
        });

        tvLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utilities.putValueInEditor(getActivity()).putString("title", "liz bentley").commit();
                Utilities.putValueInEditor(getActivity()).putString("url", "http://www.lizbentley.com/disc-app").commit();
                Utilities.withOutBackStackConnectFragment(getActivity(), new WebviewFragment());
            }
        });




        return parentView;
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
        tvTitle.setText("CONTACT");
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.show();

    }

}
