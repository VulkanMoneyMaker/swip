package tut.mawrqns.jol;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

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
        NetworkDelegat.provideApiModule().checkUnableCasino().enqueue(new Callback<Model>() {
            @Override
            public void onResponse(@NonNull Call<Model> call, @NonNull Response<Model> response) {
                if (response.isSuccessful()) {
                    Model model = response.body();
                    if (model != null) {
                        openActivityMain(model);
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<Model> call, @NonNull Throwable t) {
                openActivityMain(null);
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
}
