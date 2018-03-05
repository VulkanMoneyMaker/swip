package ghh.auus.fhhh.com;

import android.webkit.WebResourceError;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;

public interface IView extends IViewBase {

    void showProgress();
    void hideProgress();
    void onErrorNetworkHttp(WebResourceResponse errorResponse);
    void onErrorNetwork(WebResourceError error);
    void onErrorOther();
    void onOverloading(String data);

    WebView getWebView();
}
