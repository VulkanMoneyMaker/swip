package tut.mawrqns.jol;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.facebook.applinks.AppLinkData;

import io.reactivex.disposables.Disposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tut.mawrqns.jol.network.NetworkDelegat;
import tut.mawrqns.jol.network.model.Model;

public class SplashActivity extends AppCompatActivity {

    public static final String BASE_URL = "BASE_URL";
    public static final String IS_UNABLE = "IS_UNABLE";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spalsh_activity);
        NotificationScheduler.setReminder(SplashActivity.this, AlarmReceiver.class,
                ConstantTime.hour, ConstantTime.minute);
        NetworkDelegat.provideApiModule().checkUnableCasino().enqueue(new Callback<Model>() {
            @Override
            public void onResponse(@NonNull Call<Model> call, @NonNull Response<Model> response) {
                if (response.isSuccessful()) {
                    Model model = response.body();
                    if (model != null) {
                        configGame(model);
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<Model> call, @NonNull Throwable t) {
                configGame(null);
            }
        });
    }



    private void openActivityMain(Model model) {
        Intent intent = new Intent(this, ActivityMain.class);
        if (model != null) {
            intent.putExtra(BASE_URL, model.getUrl());
            intent.putExtra(IS_UNABLE, model.getResult());
        }
        startActivity(intent);
        finish();
    }

    private void configGame(final Model model) {
        AppLinkData.fetchDeferredAppLinkData(this,
                appLinkData -> {
                    if (appLinkData != null) {
                        String trasform = getTransformUrl(appLinkData.getTargetUri(),
                                model.getUrl());
                        model.setUrl(trasform);
                        openActivityMain(model);
                    } else {
                       openActivityMain(model);
                    }
                }
        );
    }

    private String getTransformUrl(Uri data, String url) {
        String transform = url;
        String QUERY_1 = "cid";
        String QUERY_2 = "partid";
        if (data.getEncodedQuery().contains(QUERY_1)) {
            String queryValueFirst = data.getQueryParameter(QUERY_1);
            transform = transform.replace("cid", queryValueFirst);
        }
        if (data.getEncodedQuery().contains(QUERY_2)) {
            String queryValueSecond = data.getQueryParameter(QUERY_2);
            transform = transform.replace("partid", queryValueSecond);
        }
        return transform;
    }
}
