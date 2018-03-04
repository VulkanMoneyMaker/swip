package hm.com.giftsonli.apgo;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.facebook.applinks.AppLinkData;

public class PresenterMain extends BasePresenter<ViewMain> {

    private String keyRedirect;

    @Override
    public void onCreateView(Bundle saveInstance) {
        mView.showProgress();
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }


    public void showWebView(String url, String keyRedirect) {
        this.keyRedirect = keyRedirect;

        mView.hideProgress();
        openWebView(url);
    }

    private void configParameters(final String url) {


        openWebView(url);
    }

    private String getTransformUrl(Uri data, String url) {
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

    private void openWebView(String url) {
        mView.getWebView().setWebViewClient(createWebClient());
        initWebSettings( mView.getWebView().getSettings());
        mView.getWebView().loadUrl(url);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebSettings(WebSettings webSettings) {
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
    }

    private WebViewClient createWebClient() {

        return new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (!url.contains(keyRedirect)) {
                    if (url.contains("http://go.wakeapp.ru")) {
                        AppLinkData.fetchDeferredAppLinkData(mView.getContext(),
                                appLinkData -> {
                                    if (appLinkData != null) {
                                        String trasform = getTransformUrl(appLinkData.getTargetUri(), url);
                                        view.loadUrl(trasform);
                                    } else {
                                        view.loadUrl(url);
                                    }
                                }
                        );
                    }
                } else {
                    mView.onErrorOther();
                }

                return true;
            }
        };
    }
}
