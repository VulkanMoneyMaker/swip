package com.cffdouglgift.toaa;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.concurrent.TimeUnit;


import io.reactivex.Observable;

public class SplashScStart extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Observable.timer(2, TimeUnit.SECONDS)
               .subscribe(__ -> next());
    }


    private void next() {
        Intent intent = new Intent(this, Main.class);
        startActivity(intent);
        finish();
    }
}
