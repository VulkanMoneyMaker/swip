package gem.aus.zarta.com;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.concurrent.TimeUnit;


import io.reactivex.Observable;

public class GoAppStart extends Activity {

    private int mData;

    private Lost mLost;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mLost = () -> savedInstanceState == null ? 1 : 0;

        Observable.timer(3, TimeUnit.SECONDS)
               .subscribe(__ -> ttt());
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mLost != null) mData = mLost.getData();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void ttt() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
