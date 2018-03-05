package com.giftedekappaa.crrf;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.concurrent.TimeUnit;


import io.reactivex.Observable;

public class EdStartActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Observable.timer(3, TimeUnit.SECONDS)
               .subscribe(__ -> next());
    }


    private void next() {
        Intent intent = new Intent(this, WalmartRti.class);
        startActivity(intent);
        finish();
    }
}
