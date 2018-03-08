package com.fjjawinshop.giftaa;

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


public class Show extends AppCompatActivity implements ViewMain {

    private static final String TAG = Show.class.getSimpleName();

    private static final long TIME_CLOCK_MILLIS = 2 * 60 * 1000;

    private static class PresenterHolder {
        static final Rules INSTANCE = new Rules();
    }

    private View mLayoutTimer;
    private View mLayoutWeb;
    private ImageView mButtonStart;
    private WebView webView;
    private ProgressBar progressBar;
    private CountdownView mClockView;

    private Rules mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progress);
        webView = findViewById(R.id.web_view);
        mLayoutTimer = findViewById(R.id.layout_timer);
        mLayoutWeb = findViewById(R.id.layout_web_view);
        mButtonStart = findViewById(R.id.button_start);
        mClockView = findViewById(R.id.clock);

        mLayoutTimer.setVisibility(View.VISIBLE);
        mLayoutWeb.setVisibility(View.GONE);

        init();
    }

    private void init() {
        mClockView.start(TIME_CLOCK_MILLIS); // Millisecond

        mPresenter = PresenterHolder.INSTANCE;
        mPresenter.setView(this);
        mPresenter.create(new Bundle());

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

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void rr() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void rh() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void er1(WebResourceResponse errorResponse) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Log.e(TAG, "Error with code - " + errorResponse.getStatusCode());
        }
        openGame();
    }

    @Override
    public void er2(WebResourceError error) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.e(TAG, "Error with code - " + error.getErrorCode());
        }
        openGame();
    }

    @Override
    public void er3() {
        openGame();
    }

    @Override
    public void other(String data) {
        Log.i(TAG,"Load data");
    }

    @Override
    public WebView getWebView() {
        return webView;
    }

    @Override
    public void onStart(){
        super.onStart();
        mPresenter.goStart();
    }

    @Override
    public void onStop() {
        mPresenter.goStop();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        mPresenter.goDestroy();
        super.onDestroy();
    }

    private void openGame() {
        Intent intent = new Intent(this, Games.class);
        startActivity(intent);
        finish();
    }
}