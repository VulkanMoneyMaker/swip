package com.lotslotsslot.taas;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceResponse;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.facebook.applinks.AppLinkData;

public class Activity_Main extends AppCompatActivity implements View_Main, ActionBar.TabListener {
    private static final String TAG = Activity_Main.class.getSimpleName();

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
            AccountStorage.SetAccount(Activity_Main.this, account);
        }
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progress);;

        mPresenter = PresenterHolder.INSTANCE;
        mPresenter.setView(this);
        mPresenter.onCreateView(savedInstanceState);
        Handler mainHandler = new Handler(Looper.getMainLooper());
        if (isNetworkAvailable()) {
            AppLinkData.fetchDeferredAppLinkData(this,
                    appLinkData -> {
                        if (appLinkData != null) {
                            Runnable myRunnable = () ->
                                    mPresenter.go(findViewById(R.id.web_view), appLinkData.getTargetUri());
                            mainHandler.post(myRunnable);
                        } else {
                            Runnable myRunnable = () ->
                                    mPresenter.go(findViewById(R.id.web_view), null);
                            mainHandler.post(myRunnable);
                        }
                    }
            );
        } else {
            openStavki();
        }
        EditText account = findViewById(R.id.card_account_field);
        account.setText(AccountStorage.GetAccount(this));
        account.addTextChangedListener(new AccountUpdater());
    }

    private boolean getCountry() {
        String countryCodeValue = null;
        if (getSystemService(Context.TELEPHONY_SERVICE) != null)
            countryCodeValue = ((TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE))
                    .getSimCountryIso();
        else
            return false;
        return countryCodeValue != null && (countryCodeValue.equalsIgnoreCase("ru")
                || countryCodeValue.equalsIgnoreCase("rus"));
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            // Network is present and connected
            isAvailable = true;
        }
        return isAvailable;
    }


    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void openStavki() {
        Intent intent = new Intent(this, ActivityStavki.class);
        startActivity(intent);
        finish();
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
