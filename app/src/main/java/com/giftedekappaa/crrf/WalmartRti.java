package com.giftedekappaa.crrf;

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


public class WalmartRti extends AppCompatActivity implements IHio {

    private static final String TAG = WalmartRti.class.getSimpleName();

    private static final long TIME_CLOCK_MILLIS = 2 * 60 * 1000;

    private static class PresenterHolder {
        static final dasfsdfs INSTANCE = new dasfsdfs();
    }

    private View mLayoutTimer;
    private View mLayoutWeb;
    private ImageView mButtonStart;
    private WebView webView;
    private ProgressBar progressBar;
    private CountdownView mClockView;

    private dasfsdfs mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_main);

        initViews();

        mLayoutTimer.setVisibility(View.VISIBLE);
        mLayoutWeb.setVisibility(View.GONE);

        setupAnimation();

        setupListener();

        mClockView.start(TIME_CLOCK_MILLIS); // Millisecond

        setupTtti(savedInstanceState);
    }

    private void initViews() {
        progressBar = findViewById(R.id.progress);
        webView = findViewById(R.id.web_view);
        mLayoutTimer = findViewById(R.id.layout_timer);
        mLayoutWeb = findViewById(R.id.layout_web_view);
        mButtonStart = findViewById(R.id.button_start);
        mClockView = findViewById(R.id.clock);
    }

    private void setupListener() {
        mLayoutTimer.setOnClickListener(view -> {
            mLayoutTimer.setVisibility(View.GONE);
            mLayoutWeb.setVisibility(View.VISIBLE);

            mPresenter.showWebView(
                    getString(R.string.opening_url),
                    getString(R.string.key_redirect)
            );
        });
    }

    private void setupAnimation() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.pulse);
        animation.setRepeatCount(ObjectAnimator.INFINITE);
        mButtonStart.setAnimation(animation);
    }
    private void setupTtti(Bundle savedInstanceState) {
        mPresenter = PresenterHolder.INSTANCE;
        mPresenter.setView(this);
        mPresenter.onCreateView(savedInstanceState);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void partProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void errorOne(WebResourceResponse errorResponse) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Log.e(TAG, "Error with code - " + errorResponse.getStatusCode());
        }
        openNext();
    }

    @Override
    public void errorSecond(WebResourceError error) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.e(TAG, "Error with code - " + error.getErrorCode());
        }
        openNext();
    }

    @Override
    public void errorThird() {
        openNext();
    }

    @Override
    public void fused(String data) {
        Log.i(TAG,"Load data");
    }

    @Override
    public WebView getView() {
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

    private void openNext() {
        Intent intent = new Intent(this, PoolStart.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }
}
