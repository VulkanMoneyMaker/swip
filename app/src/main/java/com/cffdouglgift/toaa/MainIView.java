package com.cffdouglgift.toaa;

import android.webkit.WebResourceError;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;

public interface MainIView extends BaseIView {

    void p1();
    void p2();
    void e1(WebResourceResponse errorResponse);
    void e2(WebResourceError error);
    void e3();
    void ovv(String data);

    WebView getView();
}
