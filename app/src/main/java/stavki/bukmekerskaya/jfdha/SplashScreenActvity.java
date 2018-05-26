package fere.betstavki.cityonline;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;


public class SplashScreenActvity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        NotificationScheduler.setReminder(SplashScreenActvity.this, AlarmReceiver.class,
                ConstantTime.hour, ConstantTime.minute);
        Observable.timer(20, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(t -> requestPermissions());
    }

    private void openOpeningLaunchScreen() {
        Intent intent = new Intent(this, Activity_Main.class);
        startActivity(intent);
        finish();
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SplashScreenActvity.this);
        builder.setTitle("Разрешения для приложения")
                .setMessage("Для корректной работы приложения необходимо дать разрешения на камеру и харнилище данных!")
                .setCancelable(false)
                .setNegativeButton("ОК", (dialog, id) -> {
                    dialog.cancel();
                    requestPermissions();
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void requestPermissions() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        ).subscribe(granted -> {
            if (granted) {
                openOpeningLaunchScreen();
            } else {
                showDialog();
            }
        }, Throwable::printStackTrace);
    }

}
