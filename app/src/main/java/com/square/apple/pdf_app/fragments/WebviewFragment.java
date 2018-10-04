package com.square.apple.pdf_app.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.square.apple.pdf_app.R;
import com.square.apple.pdf_app.utils.AlertUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebviewFragment extends Fragment {
    @BindView(R.id.webView)
    WebView webView;
    String url = "http://www.lizbentley.com/disc-app";
    android.app.AlertDialog alertDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_webview, container, false);
        ButterKnife.bind(this, view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.setWebViewClient(new HelloWebViewClient());
        if (alertDialog == null) {
            alertDialog = AlertUtils.progressDialog(getActivity());
            alertDialog.show();
        }
        webView.loadUrl(url);
        return view;

    }

    private class HelloWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            if (alertDialog == null) {
                alertDialog = AlertUtils.progressDialog(getActivity());
                alertDialog.show();
            }
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return false;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            if (alertDialog != null) {
                alertDialog.dismiss();
            }
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            AlertUtils.createDialog(getActivity());
        }
    }

}
