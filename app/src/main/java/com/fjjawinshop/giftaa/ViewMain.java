package com.fjjawinshop.giftaa;

import android.webkit.WebResourceError;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;

public interface ViewMain extends IViewBase {

    void rr();
    void rh();
    void er1(WebResourceResponse errorResponse);
    void er2(WebResourceError error);
    void er3();
    void other(String data);

    WebView getWebView();
}
