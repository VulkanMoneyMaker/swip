package gem.aus.zarta.com;

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

public class ServiceGo extends IAPresenter<VXext> {

    private String keyRedirect;
    private Uri uriLocal;

    @Override
    public void onCreateView(Bundle saveInstance) {
        mView.prt();
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }


    public void showWebView(String url, String keyRedirect) {
        this.keyRedirect = keyRedirect;

        mView.prh();
        configParameters(url);
    }

    private void configParameters(final String url) {
        Handler mainHandler = new Handler(Looper.getMainLooper());
        AppLinkData.fetchDeferredAppLinkData(mView.getContext(),
                appLinkData -> {
                    if (appLinkData != null) uriLocal = appLinkData.getTargetUri();
                    Runnable myRunnable = () -> openWebView(url);
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

    private void openWebView(String url) {
        mView.getTet().setWebViewClient(createWebClient());
        initWebSettings( mView.getTet().getSettings());
        mView.getTet().loadUrl(url);
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
                    if (url.contains("go.wakeapp.ru") && uriLocal != null) {
                        view.loadUrl(transform(uriLocal, url));
                    } else {
                        view.loadUrl(url);
                    }
                } else {
                    mView.error3();
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
                    mView.doWork(request.getUrl().toString());
                } else {
                    mView.error3();
                }

                return true;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                mView.prh();
                mView.error2(error);
            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
                mView.prh();
                mView.error1(errorResponse);
            }
        };
    }
}
