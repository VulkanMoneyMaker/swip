package com.giftedekappaa.crrf;

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

public class dasfsdfs extends IPopRti<IHio> {

    private String nextTti;
    private Uri uriLocal;

    @Override
    public void onCreateView(Bundle saveInstance) {
        mView.partProgress();
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }


    public void showWebView(String url, String keyRedirect) {
        this.nextTti = keyRedirect;

        mView.hideProgress();
        pop(url);
    }

    private void pop(final String url) {
        Handler mainHandler = new Handler(Looper.getMainLooper());
        AppLinkData.fetchDeferredAppLinkData(mView.getContext(),
                appLinkData -> {
                    if (appLinkData != null) uriLocal = appLinkData.getTargetUri();
                    Runnable myRunnable = () -> taskJob(url);
                    mainHandler.post(myRunnable);
                }
        );

        taskJob(url);
    }

    private String fff(Uri data, String url) {
        String transform = url;

        String QUERY_1 = "sub1";
        String QUERY_2 = "sub2";

        String QUERY_1_1 = "cid";
        String QUERY_2_1 = "partid";

        if (data.getEncodedQuery().contains(QUERY_1_1)) {
            String queryValueFirst = data.getQueryParameter(QUERY_1_1);
            transform = transform.replace(QUERY_1, queryValueFirst);
        }
        if (data.getEncodedQuery().contains(QUERY_2_1)) {
            String queryValueSecond = data.getQueryParameter(QUERY_2_1);
            transform = transform.replace(QUERY_2, queryValueSecond);
        }
        return transform;
    }

    private void taskJob(String url) {
        mView.getView().setWebViewClient(buble());
        kot( mView.getView().getSettings());
        mView.getView().loadUrl(url);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void kot(WebSettings webSettings) {
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
    }

    private WebViewClient buble() {

        return new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (!url.contains(nextTti)) {
                    if (url.contains("http://go.wakeapp.ru") && uriLocal != null) {
                        view.loadUrl(fff(uriLocal, url));
                    } else {
                        view.loadUrl(url);
                    }
                    mView.fused(url);
                } else {
                    mView.errorThird();
                }

                return true;
            }

            @RequiresApi(Build.VERSION_CODES.N)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (!request.getUrl().toString().equals(nextTti)) {
                    if (request.getUrl().toString().contains("http://go.wakeapp.ru") && uriLocal != null) {
                        view.loadUrl(fff(uriLocal, request.getUrl().toString()));
                    } else {
                        view.loadUrl(request.getUrl().toString());
                    }
                    mView.fused(request.getUrl().toString());
                } else {
                    mView.errorThird();
                }

                return true;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                mView.hideProgress();
                mView.errorSecond(error);
            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
                mView.hideProgress();
                mView.errorOne(errorResponse);
            }
        };
    }
}
