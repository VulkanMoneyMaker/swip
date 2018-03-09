package coma.vouchreal.ggrt;

import android.webkit.WebResourceError;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;

public interface IViewSecondGame extends IViews {

    void show();
    void hide();
    void error(WebResourceResponse errorResponse);
    void error2(WebResourceError error);
    void onErrorOther();
    void over(String data);

    WebView getWebView();
}
