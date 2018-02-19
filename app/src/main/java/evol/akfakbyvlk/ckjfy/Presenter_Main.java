package evol.akfakbyvlk.ckjfy;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;



import java.util.UUID;

public class Presenter_Main extends Presenter_Base<View_Main> {

    private String needData;

    private class Dater {
        String uuid;
        long currentState;
    }

    private Dater dater = new Dater();

    @Override
    public void onCreateView(Bundle saveInstance) {
        mView.showProgress();
        needData = mView.getContext().getString(R.string.opening_url);

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
    public Const df() {
        return null;
    }


    public void go(WebView webView) {
        mView.hideProgress();
        webView.setWebViewClient(base());
        init(webView.getSettings());
        webView.loadUrl(needData);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void init(WebSettings webSettings) {
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
    }

    private WebViewClient base() {
        return new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                mView.onOverloading(url);
                return true;
            }

            @RequiresApi(Build.VERSION_CODES.N)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
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
