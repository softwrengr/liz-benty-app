package com.square.apple.pdf_app.fragments;


import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.square.apple.pdf_app.R;
import com.square.apple.pdf_app.dataModels.GetAllResponse;
import com.square.apple.pdf_app.networking.ApiClient;
import com.square.apple.pdf_app.networking.ApiInterface;
import com.square.apple.pdf_app.utils.Connectivity;
import com.square.apple.pdf_app.utils.Utilities;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PdfViewFragment extends Fragment implements View.OnClickListener {

    View parentView;

    PDFView pdfView;
    @BindView(R.id.btn_dominant)
    Button btnDominant;
    @BindView(R.id.btn_influncer)
    Button btnInfluncer;
    @BindView(R.id.btn_steady)
    Button btnSteady;
    @BindView(R.id.btn_conscientious)
    Button btnConscientious;
    @BindView(R.id.tv_orginal_link)
    TextView tvOrginalLink;

    @BindView(R.id.avi_loading)
    AVLoadingIndicatorView avLoadingIndicatorView;

    String strPdfUrl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        parentView = inflater.inflate(R.layout.fragment_pdf_view, container, false);
        customActionBar();
        pdfView = parentView.findViewById(R.id.pdf_view);
        initUI();
//        loadPdfFile();

        LoadPdfFromAsset();
        return parentView;
    }

    private void LoadPdfFromAsset() {
        pdfView.fromAsset(Utilities.getSharedPreferences(getActivity()).getString("pdf_file_name", "")).onLoad(new OnLoadCompleteListener() {
            @Override
            public void loadComplete(int nbPages) {
                avLoadingIndicatorView.setVisibility(View.GONE);
            }
        }).enableSwipe(true) // allows to block changing pages using swipe
                .enableAnnotationRendering(true) // render annotations (such as comments, colors or forms)
                .password(null)
                .enableDoubletap(false)
                .enableAntialiasing(false) // improve rendering a little bit on low-res screens
                .onLoad(new OnLoadCompleteListener() {
                    @Override
                    public void loadComplete(int nbPages) {
                        avLoadingIndicatorView.setVisibility(View.GONE);
                    }
                })
                .enableAntialiasing(false)
                .load();

        pdfView.zoomTo(3);
        pdfView.setMinZoom(3);
        pdfView.setMaxZoom(3);
        pdfView.enableRenderDuringScale(true);

    }


    private void initUI() {
        ButterKnife.bind(this, parentView);
        btnDominant.setOnClickListener(this);
        btnInfluncer.setOnClickListener(this);
        btnSteady.setOnClickListener(this);
        btnConscientious.setOnClickListener(this);
        tvOrginalLink.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_dominant:
                gotoPdf("(D) Dominant");
                Utilities.putValueInEditor(getActivity()).putString("pdf_file_name", "dominant.pdf").commit();
                break;
            case R.id.btn_influncer:
                gotoPdf("(I) Infuencer");
                Utilities.putValueInEditor(getActivity()).putString("pdf_file_name", "influencer.pdf").commit();
                break;
            case R.id.btn_steady:
                Utilities.putValueInEditor(getActivity()).putString("pdf_file_name", "steady.pdf").commit();
                gotoPdf("(S) Steady");
                break;
            case R.id.btn_conscientious:
                Utilities.putValueInEditor(getActivity()).putString("pdf_file_name", "concientious.pdf").commit();
                gotoPdf("(C) Concientious");
                break;
            case R.id.tv_orginal_link:
                Utilities.putValueInEditor(getActivity()).putString("title", "liz bentley").commit();
                Utilities.putValueInEditor(getActivity()).putString("url", "http://www.lizbentley.com/disc-app").commit();
                Utilities.withOutBackStackConnectFragment(getActivity(), new WebviewFragment());
                break;
        }

        customActionBar();

//        loadPdfFile();

    }

    private void loadPdfFile() {
        if (Connectivity.isConnected(getActivity())) {
            avLoadingIndicatorView.setVisibility(View.VISIBLE);
            getDominantPdf();
        } else {
            Toast.makeText(getActivity(), "something went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    private void gotoPdf(String strTitle) {
        Utilities.putValueInEditor(getActivity()).putString("title", strTitle).commit();
//        LoadPdfFromAsset();
        Utilities.withOutBackStackConnectFragment(getActivity(), new PdfViewFragment());

    }


    private void getDominantPdf() {
        ApiInterface services = ApiClient.getApiClient().create(ApiInterface.class);
        Call<GetAllResponse> getPdf = services.getPdf(Utilities.getSharedPreferences(getActivity()).getString("pdf_file_name", ""));

        getPdf.enqueue(new Callback<GetAllResponse>() {
            @Override
            public void onResponse(Call<GetAllResponse> call, Response<GetAllResponse> response) {
                if (response.body().getSuccess()) {
                    strPdfUrl = response.body().getAllPdf().getUrl();
                    loadPdf();
                }
            }

            @Override
            public void onFailure(Call<GetAllResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadPdf() {

        new Load().execute();
    }

    public class Load extends AsyncTask<Void, Void, Void> {

        @SuppressLint("WrongThread")
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                InputStream input = new URL(strPdfUrl).openStream();
                pdfView.fromStream(input).onLoad(new OnLoadCompleteListener() {
                    @Override
                    public void loadComplete(int nbPages) {
                        avLoadingIndicatorView.setVisibility(View.GONE);
                    }
                }).enableSwipe(true) // allows to block changing pages using swipe
                        .enableAnnotationRendering(true) // render annotations (such as comments, colors or forms)
                        .password(null)
                        .enableDoubletap(false)
                        .enableAntialiasing(false) // improve rendering a little bit on low-res screens
                        .onLoad(new OnLoadCompleteListener() {
                            @Override
                            public void loadComplete(int nbPages) {
                                avLoadingIndicatorView.setVisibility(View.GONE);
                            }
                        })
                        .enableAntialiasing(false)
                        .load();

                pdfView.zoomTo(3);
                pdfView.setMinZoom(3);
                pdfView.setMaxZoom(3);
                pdfView.enableRenderDuringScale(true);


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
        mActionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));
        LayoutInflater mInflater = LayoutInflater.from(getActivity());
        View mCustomView = mInflater.inflate(R.layout.custom_actionbar, null);
        TextView tvTitle = mCustomView.findViewById(R.id.title);
        tvTitle.setText(Utilities.getSharedPreferences(getActivity()).getString("title", ""));
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.show();

    }


}
