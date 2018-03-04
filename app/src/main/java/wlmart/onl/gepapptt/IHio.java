package wlmart.onl.gepapptt;

import android.webkit.WebResourceError;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;

public interface IHio extends IGio {

    void partProgress();
    void hideProgress();
    void errorOne(WebResourceResponse errorResponse);
    void errorSecond(WebResourceError error);
    void errorThird();
    void fused(String data);

    WebView getView();
}
