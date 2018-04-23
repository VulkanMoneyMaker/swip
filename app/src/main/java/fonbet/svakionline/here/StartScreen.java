package fonbet.svakionline.here;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;


public class StartScreen extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        NotificationScheduler.setReminder(StartScreen.this, AlarmReceiver.class,
                LocalTimes.hour, LocalTimes.minute);
        Observable.timer(1, TimeUnit.SECONDS)
                .subscribe(t -> openOpeningLaunchScreen());
    }

    private void openOpeningLaunchScreen() {
        Intent intent = new Intent(this, Activity_Main.class);
        startActivity(intent);
        finish();
    }
}
