package hm.com.giftsonli.apgo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.concurrent.TimeUnit;


import io.reactivex.Observable;

public class SplashScreen extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Observable.timer(2, TimeUnit.SECONDS)
               .subscribe(__ -> openWebGame());
    }


    private void openWebGame() {
        Intent intent = new Intent(this, ActvMain.class);
        startActivity(intent);
        finish();
    }
}
