package com.lidlvoucher.goapper;

import android.webkit.WebResourceError;
import android.webkit.WebResourceResponse;

public interface ViewMain extends ViewBase {

    void showProgress();
    void hideProgress();
    void onErrorNetworkHttp(WebResourceResponse errorResponse);
    void onErrorNetwork(WebResourceError error);
    void onOverloading(String data);
}
