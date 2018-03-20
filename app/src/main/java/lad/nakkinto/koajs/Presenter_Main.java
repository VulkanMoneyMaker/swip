package lad.nakkinto.koajs;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.giftgoapp.jhq.R;

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


    void go(WebView webView) {
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
        };
    }
}
