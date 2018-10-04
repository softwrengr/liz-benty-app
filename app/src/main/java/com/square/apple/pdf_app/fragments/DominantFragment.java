package com.square.apple.pdf_app.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.square.apple.pdf_app.R;
import com.square.apple.pdf_app.dataModels.GetAllResponse;
import com.square.apple.pdf_app.networking.ApiClient;
import com.square.apple.pdf_app.networking.ApiInterface;
import com.square.apple.pdf_app.utils.AlertUtils;
import com.square.apple.pdf_app.utils.Connectivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DominantFragment extends Fragment {
    AlertDialog alertDialog;
    private boolean valid = false;
    String strType, strPdfUrl;
    View view;

    @BindView(R.id.btn_dominant)
    Button btnDominant;
    @BindView(R.id.btn_influncer)
    Button btnInfluncer;
    @BindView(R.id.btn_steady)
    Button btnSteady;
    @BindView(R.id.btn_conscientious)
    Button btnConscientious;

    @BindView(R.id.pdfView)
    PDFView pdfView;

    @BindView(R.id.tv_orginal_link)
    TextView tvOriginalLink;

    String strDominantLink = "http://www.lizbentley.com/disc-app";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_dominant, container, false);
        changeStatusBarColor();
        customActionBar();
        initUI();
        return view;
    }

    private void initUI() {
        ButterKnife.bind(this, view);
        if (Connectivity.isConnected(getActivity())) {
            alertDialog = AlertUtils.createProgressDialog(getActivity());
            alertDialog.show();
            getDominantPdf();
        } else {
            Toast.makeText(getActivity(), "something went wrong", Toast.LENGTH_SHORT).show();
        }

        //bottom layout
        btnDominant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new DominantFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            }
        });

        btnInfluncer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new InfluncerFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            }
        });
        btnSteady.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new SteadyFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            }
        });
        btnConscientious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new ConscientiousFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            }
        });

        tvOriginalLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(strDominantLink)));
                Fragment fragment = new WebviewFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
            }
        });
    }


    private void getDominantPdf() {
        ApiInterface services = ApiClient.getApiClient().create(ApiInterface.class);
        Call<GetAllResponse> getPdf = services.getPdf("dominant");

        getPdf.enqueue(new Callback<GetAllResponse>() {
            @Override
            public void onResponse(Call<GetAllResponse> call, Response<GetAllResponse> response) {
                if (response.body().getSuccess()) {
                    strPdfUrl = response.body().getAllPdf().getUrl();
                    loadPdf(strPdfUrl);
                }

            }

            @Override
            public void onFailure(Call<GetAllResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "failed", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void loadPdf(String strPdfUrl) {

        new Load().execute();
    }

    public class Load extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                InputStream input = new URL(strPdfUrl).openStream();
                pdfView.fromStream(input).onLoad(new OnLoadCompleteListener() {
                    @Override
                    public void loadComplete(int nbPages) {
                        alertDialog.dismiss();
                    }
                }).load();


            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }


    public void customActionBar() {
        android.support.v7.app.ActionBar mActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setDisplayHomeAsUpEnabled(false);
        mActionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.dominant)));
        LayoutInflater mInflater = LayoutInflater.from(getActivity());
        View mCustomView = mInflater.inflate(R.layout.custom_actionbar, null);
        TextView tvTitle = mCustomView.findViewById(R.id.title);
        tvTitle.setText("(D) Dominant");
        ImageView ivHome = mCustomView.findViewById(R.id.ivHome);
        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new HomeFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack("aaa").commit();
            }
        });
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.show();

    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.dominant));
        }
    }


}
