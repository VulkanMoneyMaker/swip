package go.giftt.kupaaq.com;

import android.webkit.WebResourceError;
import android.webkit.WebResourceResponse;

public interface View_Main extends View_Base {

    void showProgress();
    void hideProgress();
    void onErrorNetworkHttp(WebResourceResponse errorResponse);
    void onErrorNetwork(WebResourceError error);
    void onOverloading(String data);
}
