package com.vouchlidlcom.appfgqq;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.facebook.applinks.AppLinkData;
import com.vouchlidlcom.appfgqq.network.NetworkDelegat;
import com.vouchlidlcom.appfgqq.network.model.ModelRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainSplsSPls extends Activity {

    public static final String BASE_KEY_URL = "BASE_KEY_URL";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (isOnline()) {
            openWebGame("node");
        } else {
            openGame();
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private void openWebGame(String url) {
        Intent intent = new Intent(this, MainSplsh.class);
        intent.putExtra(BASE_KEY_URL, url);
        startActivity(intent);
        finish();
    }


    private void openGame() {
        Intent intent = new Intent(this, GameRule.class);
        startActivity(intent);
        finish();
    }
}
