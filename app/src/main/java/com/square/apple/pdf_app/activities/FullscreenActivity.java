package com.square.apple.pdf_app.activities;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import com.square.apple.pdf_app.R;
import com.square.apple.pdf_app.fragments.HomeFragment;

public class FullscreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);

        Fragment fragment = new HomeFragment();
        getFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();

    }
}
