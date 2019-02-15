package com.square.apple.pdf_app.fragments;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.square.apple.pdf_app.R;
import com.square.apple.pdf_app.activities.MainDrawerActivity;
import com.square.apple.pdf_app.utils.Utilities;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Intent.EXTRA_TEXT;

public class WebviewFragment extends Fragment {

    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {Manifest.permission.CALL_PHONE};
    View view;
    @BindView(R.id.webView)
    WebView webView;
    String url = "http://www.lizbentley.com/disc-app";
    android.app.AlertDialog alertDialog;

    @BindView(R.id.avi_loading)
    AVLoadingIndicatorView avLoadingIndicatorView;

    private static final String HTML = "<!DOCTYPE html><html><body><a href='tel:867-5309'>Click here to call!</a></body></html>";
    private static final String TEL_PREFIX = "tel:";


    public static WebviewFragment createFor(String text) {
        WebviewFragment fragment = new WebviewFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_TEXT, text);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_webview, container, false);
        ButterKnife.bind(this, view);
        customActionBar();
        url = Utilities.getSharedPreferences(getActivity()).getString("url", "http://www.lizbentley.com/disc-app");

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.setWebViewClient(new HelloWebViewClient());
        avLoadingIndicatorView.setVisibility(View.VISIBLE);
        webView.loadData(HTML, "text/html", "utf-8");
        webView.loadUrl(url);


        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //This is the filter
                if (event.getAction() != KeyEvent.ACTION_DOWN)
                    return true;

                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (webView.canGoBack()) {
                        webView.goBack();
                    } else {

                        ((MainDrawerActivity) getActivity()).onBackPressed();
                    }
                    return true;
                }
                return false;
            }
        });

        return view;

    }

    private class HelloWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

            avLoadingIndicatorView.setVisibility(View.VISIBLE);
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {



            if (url.startsWith(TEL_PREFIX)) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(url));
                startActivity(intent);
                return true;
            }
            view.loadUrl(url);


            if (url.endsWith(".mp4")) {
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(url));
                view.getContext().startActivity(intent);
                return true;
            } else {
                return super.shouldOverrideUrlLoading(view, url);
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {

            avLoadingIndicatorView.setVisibility(View.GONE);

            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError
                error) {
            super.onReceivedError(view, request, error);

            avLoadingIndicatorView.setVisibility(View.VISIBLE);
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



