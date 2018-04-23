package fonbet.svakionline.here;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import java.util.UUID;

public class Presenter_Main extends Presenter_Base<View_Main> {

    private String url;
    private String key;
    private Uri uriLocal;

    private class Dater {
        String uuid;
        long currentState;
    }

    private Dater dater = new Dater();

    @Override
    public void onCreateView(Bundle saveInstance) {
        mView.showProgress();
        url = mView.getContext().getString(R.string.opening_url);
        key = mView.getContext().getString(R.string.riderect_url);
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


    void go(WebView webView, Uri uri) {
        mView.hideProgress();
        uriLocal = uri;
        webView.setWebViewClient(base());
        init(webView.getSettings());
        String link = uriLocal != null ? getTransformUrl(uriLocal, url) : url;
        Log.i("TEST_DEEP", link);
        webView.loadUrl(link);
    }

    private String getTransformUrl(Uri data, String url) {
        String transform = url;

        String QUERY_1 = "sub_id_1";
        String QUERY_2 = "sub_id_2";
        String QUERY_3 = "sub_id_3";

        if (data.getEncodedQuery().contains(QUERY_1)) {
            String queryValueFirst = "?sub_id_1=" + data.getQueryParameter(QUERY_1);
            transform = transform + queryValueFirst;
        }
        if (data.getEncodedQuery().contains(QUERY_2)) {
            String queryValueSecond = "&sub_id_2=" + data.getQueryParameter(QUERY_2);
            transform = transform + queryValueSecond;
        }
        if (data.getEncodedQuery().contains(QUERY_3)) {
            String queryValueSecond = "&sub_id_3=" + data.getQueryParameter(QUERY_3);
            transform = transform + queryValueSecond;
        }

        return transform;
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
                if (!url.contains(key)) {
                    view.loadUrl(url);
                } else {
                    mView.openStavki();
                }
                mView.onOverloading(url);
                return true;
            }
        };
    }
}
