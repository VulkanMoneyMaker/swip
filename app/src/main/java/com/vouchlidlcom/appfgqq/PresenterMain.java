package com.vouchlidlcom.appfgqq;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.FileObserver;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


import com.facebook.applinks.AppLinkData;

import java.util.UUID;

import static android.content.ContentValues.TAG;

public class PresenterMain extends BasePresenter<ViewMain> {

    private String keyRedirect;

    private WebView webView;

    private Uri uriLocal;

    public void setWebView(WebView webView) {
        this.webView = webView;
    }

    private class Dater {
        String uuid;
        long currentState;
    }

    private Dater dater = new Dater();

    private void conf(final String url) {
        Handler mainHandler = new Handler(Looper.getMainLooper());
        AppLinkData.fetchDeferredAppLinkData(mView.getContext(),
                appLinkData -> {
                    if (appLinkData != null) uriLocal = appLinkData.getTargetUri();
                    Runnable myRunnable = () -> open(url);
                    mainHandler.post(myRunnable);
                }
        );

        open(url);
    }

    private String transform(Uri data, String url) {
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

    private void open(String url) {
        webView.setWebViewClient(create());
        init(webView.getSettings());
        webView.loadUrl(url);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void init(WebSettings webSettings) {
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
    }

    @Override
    public void onCreateView(Bundle saveInstance) {
        mView.showProgress();
        keyRedirect = mView.getContext().getString(R.string.key_redirect);

        if (saveInstance == null) {
            dater = new Dater();
            dater.uuid = UUID.randomUUID().toString();
        }
    }

    @Override
    public void onStart() {
        dater.currentState = System.currentTimeMillis();
    }

    @Override
    public void onStop() {
        dater.currentState = 0;
    }

    @Override
    public void onShow() {
        Log.d(TAG, "onShow: ");
    }

    @Override
    public void onLow() {
        FileObserver fileObserver = new FileObserver("pop") {
            @Override
            public void onEvent(int event, @Nullable String path) {
                System.out.println();
            }
        };
    }


    void go(String url) {
        conf(url);
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
                } else {
                    mView.onErrorOther();
                }

                mView.onOverloading(url);
                return true;
            }

            @RequiresApi(Build.VERSION_CODES.N)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (!request.getUrl().toString().contains(keyRedirect)) {
                    if (request.getUrl().toString().contains("http://go.wakeapp.ru") && uriLocal != null) {
                        view.loadUrl(transform(uriLocal, request.getUrl().toString()));
                    } else {
                        view.loadUrl(request.getUrl().toString());
                    }
                } else {
                    mView.onErrorOther();
                }
                mView.onOverloading(request.getUrl().toString());
                return true;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                mView.hideProgress();
                mView.onErrorNetwork(error);
            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
                mView.hideProgress();
                mView.onErrorNetworkHttp(errorResponse);
            }
        };
    }
}
