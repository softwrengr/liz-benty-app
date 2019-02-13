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
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.square.apple.pdf_app.R;
import com.square.apple.pdf_app.utils.Utilities;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_pdf_view, container, false);
//        ButterKnife.bind(this, parentView);
        customActionBar();
        pdfView = parentView.findViewById(R.id.pdf_view);
        initUI();
        initUIpdf();


        return parentView;
    }

    private void initUI() {


        pdfView.fromAsset(Utilities.getSharedPreferences(getActivity()).getString("pdf_file_name", ""))
                .enableSwipe(true) // allows to block changing pages using swipe
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

    private void initUIpdf() {
        ButterKnife.bind(this, parentView);

        btnDominant.setOnClickListener(this);
        btnInfluncer.setOnClickListener(this);
        btnSteady.setOnClickListener(this);
        btnConscientious.setOnClickListener(this);
        tvOrginalLink.setOnClickListener(this);

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
            case R.id.tv_orginal_link:
                Utilities.putValueInEditor(getActivity()).putString("title", "liz bentley").commit();
                Utilities.putValueInEditor(getActivity()).putString("url", "http://www.lizbentley.com/disc-app").commit();
                Utilities.withOutBackStackConnectFragment(getActivity(), new WebviewFragment());
                break;
        }


    }

    private void gotoPdf(String strTitle, String strPdf) {
        Utilities.putValueInEditor(getActivity()).putString("title", strTitle).commit();
        Utilities.putValueInEditor(getActivity()).putString("pdf_file_name", strPdf).commit();
        Utilities.withOutBackStackConnectFragment(getActivity(), new PdfViewFragment());
    }
}
