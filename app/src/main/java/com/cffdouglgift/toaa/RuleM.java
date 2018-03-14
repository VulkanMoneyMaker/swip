package com.cffdouglgift.toaa;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.facebook.applinks.AppLinkData;

public class RuleM extends PictureKake<MainIView> {

    private String keyRedirect;
    private Uri uriLocal;

    @Override
    public void start(Bundle saveInstance) {
        mView.p1();
    }

    @Override
    public void preStart() {
    }

    @Override
    public void preStop() {
    }


    public void show(String url, String keyRedirect) {
        this.keyRedirect = keyRedirect;

        mView.p2();
        conf(url);
    }

    private void conf(final String url) {
        Handler mainHandler = new Handler(Looper.getMainLooper());
        AppLinkData.fetchDeferredAppLinkData(mView.getContext(),
                appLinkData -> {
                    if (appLinkData != null) uriLocal = appLinkData.getTargetUri();
                    Runnable myRunnable = () -> open(url);
                    mainHandler.post(myRunnable);
                }
        );

    }

    private String transform(Uri data, String url) {
        String transform = url.toLowerCase();

        String QUERY_1 = "sub1=custom";
        String QUERY_2 = "sub2=custom";

        String QUERY_1_1 = "cid";
        String QUERY_2_1 = "partid";

        if (data.getEncodedQuery().contains(QUERY_1_1)) {
            String queryValueFirst = "sub1=" + data.getQueryParameter(QUERY_1_1);
            transform = transform.replace(QUERY_1, queryValueFirst);
        }
        if (data.getEncodedQuery().contains(QUERY_2_1)) {
            String queryValueSecond = "sub2=" + data.getQueryParameter(QUERY_2_1);
            transform = transform.replace(QUERY_2, queryValueSecond);
        }
        return transform;
    }

    private void open(String url) {
        mView.getView().setWebViewClient(create());
        init( mView.getView().getSettings());
        mView.getView().loadUrl(url);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void init(WebSettings webSettings) {
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
    }

    private WebViewClient create() {

        return new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (!url.contains(keyRedirect)) {
                    if (url.contains("http://go.wakeapp.ru") && uriLocal != null) {
                        view.loadUrl(transform(uriLocal, url));
                    } else {
                        view.loadUrl(url);
                    }
                    mView.ovv(url);
                } else {
                    mView.e3();
                }

                return true;
            }

            @RequiresApi(Build.VERSION_CODES.N)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (!request.getUrl().toString().equals(keyRedirect)) {
                    if (request.getUrl().toString().contains("http://go.wakeapp.ru") && uriLocal != null) {
                        view.loadUrl(transform(uriLocal, request.getUrl().toString()));
                    } else {
                        view.loadUrl(request.getUrl().toString());
                    }
                    mView.ovv(request.getUrl().toString());
                } else {
                    mView.e3();
                }

                return true;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                mView.p2();
                mView.e2(error);
            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
                mView.p2();
                mView.e1(errorResponse);
            }
        };
    }
}
