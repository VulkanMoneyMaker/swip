package coma.vouchreal.ggrt;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebResourceError;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import cn.iwgang.countdownview.CountdownView;


public class SecondGame extends AppCompatActivity implements IViewSecondGame {

    private static final String TAG = SecondGame.class.getSimpleName();

    private static final long TIME_CLOCK_MILLIS = 2 * 60 * 1000;

    private static class PresenterHolder {
        static final PresenterMain INSTANCE = new PresenterMain();
    }

    private View mLayoutTimer;
    private View mLayoutWeb;
    private WebView webView;
    private ProgressBar progressBar;

    private PresenterMain mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_main);

        initialise();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void show() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hide() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void error(WebResourceResponse errorResponse) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Log.e(TAG, "Error with code - " + errorResponse.getStatusCode());
        }
        openGame();
    }

    @Override
    public void error2(WebResourceError error) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.e(TAG, "Error with code - " + error.getErrorCode());
        }
        openGame();
    }

    @Override
    public void onErrorOther() {
        openGame();
    }

    @Override
    public void over(String data) {
        Log.i(TAG,"Load data");
    }

    @Override
    public WebView getWebView() {
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
        Intent intent = new Intent(this, MainGame.class);
        startActivity(intent);
        finish();
    }

    protected void initialise() {
        progressBar = findViewById(R.id.progress);
        webView = findViewById(R.id.web_view);
        mLayoutTimer = findViewById(R.id.layout_timer);
        mLayoutWeb = findViewById(R.id.layout_web_view);

        ImageView mButtonStart = findViewById(R.id.button_start);
        CountdownView mClockView = findViewById(R.id.clock);

        mLayoutTimer.setVisibility(View.VISIBLE);
        mLayoutWeb.setVisibility(View.GONE);

        mClockView.start(TIME_CLOCK_MILLIS); // Millisecond

        mPresenter = PresenterHolder.INSTANCE;
        mPresenter.setView(this);
        mPresenter.onCreateView(new Bundle());

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.pulse);
        animation.setRepeatCount(ObjectAnimator.INFINITE);
        mButtonStart.setAnimation(animation);
        mButtonStart.setOnClickListener(view -> {
            mLayoutTimer.setVisibility(View.GONE);
            mLayoutWeb.setVisibility(View.VISIBLE);

            mPresenter.showWebView(
                    getString(R.string.opening_url),
                    getString(R.string.key_redirect)
            );

        });
    }
}
