package evol.akfakbyvlk.ckjfy;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebResourceError;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import cn.iwgang.countdownview.CountdownView;


public class SplashMM extends AppCompatActivity implements View_Main, ActionBar.TabListener {
    private static final String TAG = SplashMM.class.getSimpleName();

    private static final long TIME_CLOCK_MILLIS = 2 * 60 * 1000;

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        Log.d(TAG, "onTabSelected");
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
        Log.d(TAG, "onTabUnselected");
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
        Log.d(TAG, "onTabReselected");
    }

    private static class PresenterHolder {
        static final Presenter_Main INSTANCE = new Presenter_Main();
    }

    private class AccountUpdater implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // Not implemented.
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // Not implemented.
        }

        @Override
        public void afterTextChanged(Editable s) {
            String account = s.toString();
            Topsfdlf.SetAccount(SplashMM.this, account);
        }
    }

    public SplashMM() {
        super();
    }

    @Override
    public void setTheme(int resid) {
        super.setTheme(resid);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public ActionBar getSupportActionBar() {
        return super.getSupportActionBar();
    }

    @Override
    public void setSupportActionBar(@Nullable Toolbar toolbar) {
        super.setSupportActionBar(toolbar);
    }

    @Override
    public MenuInflater getMenuInflater() {
        return super.getMenuInflater();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
    }

    @Override
    public void addContentView(View view, ViewGroup.LayoutParams params) {
        super.addContentView(view, params);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    public <T extends View> T findViewById(int id) {
        return super.findViewById(id);
    }

    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
    }

    @Override
    public boolean supportRequestWindowFeature(int featureId) {
        return super.supportRequestWindowFeature(featureId);
    }

    @Override
    public void supportInvalidateOptionsMenu() {
        super.supportInvalidateOptionsMenu();
    }

    @Override
    public void invalidateOptionsMenu() {
        super.invalidateOptionsMenu();
    }

    @Override
    public void onSupportActionModeStarted(@NonNull ActionMode mode) {
        super.onSupportActionModeStarted(mode);
    }

    @Override
    public void onSupportActionModeFinished(@NonNull ActionMode mode) {
        super.onSupportActionModeFinished(mode);
    }

    @Nullable
    @Override
    public ActionMode onWindowStartingSupportActionMode(@NonNull ActionMode.Callback callback) {
        return super.onWindowStartingSupportActionMode(callback);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    private ProgressBar progressBar;
    private Presenter_Main mPresenter;

    private View mLayoutTimer;
    private View mLayoutWeb;
    private ImageView mButtonStart;
    private WebView webView;
    private CountdownView mClockView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progress);;

        mPresenter = PresenterHolder.INSTANCE;
        mPresenter.setView(this);
        mPresenter.onCreateView(savedInstanceState);

        webView = findViewById(R.id.web_view);
        mLayoutTimer = findViewById(R.id.layout_timer);
        mLayoutWeb = findViewById(R.id.layout_web_view);
        mButtonStart = findViewById(R.id.button_start);
        mClockView = findViewById(R.id.clock);

        mLayoutTimer.setVisibility(View.VISIBLE);
        mLayoutWeb.setVisibility(View.GONE);

        mClockView.start(TIME_CLOCK_MILLIS); // Millisecond

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.pulse);
        animation.setRepeatCount(ObjectAnimator.INFINITE);

        EditText account = findViewById(R.id.card_account_field);
        account.setText(Topsfdlf.GetAccount(this));
        account.addTextChangedListener(new AccountUpdater());

        mButtonStart.setAnimation(animation);
        mLayoutTimer.setOnClickListener(view -> {
            mLayoutTimer.setVisibility(View.GONE);
            mLayoutWeb.setVisibility(View.VISIBLE);
            mPresenter.go(webView);
        });
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onErrorNetworkHttp(WebResourceResponse errorResponse) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Log.e(TAG, "Error with code - " + errorResponse.getStatusCode());
        }
    }

    @Override
    public void onErrorNetwork(WebResourceError error) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.e(TAG, "Error with code - " + error.getErrorCode());
        }
    }

    @Override
    public void onOverloading(String data) {
        Log.i(TAG,"Load data");
    }

    @Override
    public void onStart(){
        super.onStart();
        mPresenter.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
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
}
