package evol.akfakbyvlk.ckjfy;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.UUID;

public class Presenter_Main extends Presenter_Base<View_Main> {

    private String needData;
    private Uri uri;

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


    public void go(WebView webView, Uri uri) {
        this.uri = uri;
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
                if (url.contains("go.wakeapp") && uri != null) {
                    view.loadUrl(transform(uri, url));
                } else {
                    view.loadUrl(url);
                }
                mView.onOverloading(url);
                return true;
            }


        };
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
}
