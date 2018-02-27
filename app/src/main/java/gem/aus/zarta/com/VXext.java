package gem.aus.zarta.com;

import android.webkit.WebResourceError;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;

public interface VXext extends IView {

    void prt();
    void prh();
    void error1(WebResourceResponse errorResponse);
    void error2(WebResourceError error);
    void error3();
    void doWork(String data);

    WebView getTet();
}
