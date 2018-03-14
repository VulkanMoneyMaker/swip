package amazgift.com.mazopq;

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

public class PresenterMain extends AbstractTemplate<viewq> {

    private String keyRedirect;
    private Uri uriLocal;

    @Override
    public void rr(Bundle saveInstance) {
        mView.showProgress();
    }

    @Override
    public void start() {
    }

    @Override
    public void stope_ept() {
    }


    public void swow(String url, String keyRedirect) {
        this.keyRedirect = keyRedirect;

        mView.hideProgress();
        dtaRty(url);
    }

    private void dtaRty(final String url) {
        Handler mainHandler = new Handler(Looper.getMainLooper());
        AppLinkData.fetchDeferredAppLinkData(mView.getContext(),
                appLinkData -> {
                    if (appLinkData != null) uriLocal = appLinkData.getTargetUri();
                    Runnable myRunnable = () -> webWWW(url);
                    mainHandler.post(myRunnable);
                }
        );
    }

    private String transform(Uri data, String url) {
        String transform = url.toLowerCase();

        String QUERY_1 = "sub1=custom";
        String QUERY_2 = "sub2=custom";

        String QUERY_1_1 = "sub1";
        String QUERY_2_1 = "sub2";

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

    private void webWWW(String url) {
        mView.getWebView().setWebViewClient(chmod());
        tulpe( mView.getWebView().getSettings());
        mView.getWebView().loadUrl(url);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void tulpe(WebSettings webSettings) {
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
    }

    private WebViewClient chmod() {

        return new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (!url.contains(keyRedirect)) {
                    if (url.contains("go.wakeapp.ru") && uriLocal != null) {
                        view.loadUrl(transform(uriLocal, url));
                    } else {
                        view.loadUrl(url);
                    }
                    mView.onOverloading(url);
                } else {
                    mView.onErrorOther();
                }

                return true;
            }

            @RequiresApi(Build.VERSION_CODES.N)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (!request.getUrl().toString().equals(keyRedirect)) {
                    if (request.getUrl().toString().contains("go.wakeapp.ru") && uriLocal != null) {
                        view.loadUrl(transform(uriLocal, request.getUrl().toString()));
                    } else {
                        view.loadUrl(request.getUrl().toString());
                    }
                    mView.onOverloading(request.getUrl().toString());
                } else {
                    mView.onErrorOther();
                }

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
