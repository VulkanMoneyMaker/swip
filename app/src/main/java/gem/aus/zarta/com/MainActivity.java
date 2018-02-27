package gem.aus.zarta.com;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.widget.ProgressBar;



public class MainActivity extends AppCompatActivity implements VXext {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static class PresenterHolder {
        static final ServiceGo INSTANCE = new ServiceGo();
    }

    private Dart mDatrt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_main);

        mDatrt = state -> {
            switch (state) {
                case 0: return 122L;
                case 1: return 255L;
                default: return 300L;
            }
        };

        progressBar = findViewById(R.id.progress);
        webView = findViewById(R.id.web_view);

        mPresenter = PresenterHolder.INSTANCE;
        mPresenter.setView(this);
        mPresenter.onCreateView(savedInstanceState);
        mPresenter.showWebView(
                getString(R.string.opening_url),
                getString(R.string.key_redirect)
        );
    }

    private WebView webView;
    private ProgressBar progressBar;
    private ServiceGo mPresenter;


    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void prt() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void prh() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void error1(WebResourceResponse errorResponse) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Log.e(TAG, "Error with code - " + mDatrt.needed(errorResponse.getStatusCode()));
        }
        openGame();
    }

    @Override
    public void error2(WebResourceError error) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.e(TAG, "Error with code - " +  mDatrt.needed(error.getErrorCode()));
        }

        openGame();
    }

    @Override
    public void error3() {
        openGame();
    }

    @Override
    public void doWork(String data) {
        Log.i(TAG,"Load data");
    }

    @Override
    public WebView getTet() {
        return webView;
    }

    @Override
    public void onStart(){
        super.onStart();
        mPresenter.onStart();
    }

    @Override
    public void onStop() {
        mPresenter.onStop();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        mPresenter.onDestroy();
        super.onDestroy();
    }

    private void openGame() {
        Intent intent = new Intent(this, NeeghtScreen.class);
        startActivity(intent);
        finish();
    }
}
