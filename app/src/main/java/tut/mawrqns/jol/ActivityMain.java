package tut.mawrqns.jol;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tut.mawrqns.jol.network.NetworkDelegat;
import tut.mawrqns.jol.network.model.Model;


public class ActivityMain extends AppCompatActivity {

    private String url1;
    private String url2;
    private String url3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnVulkan = findViewById(R.id.btn_vulkan);
        Button btnPlatinum = findViewById(R.id.btn_platinum);
        Button btnAdmiral = findViewById(R.id.btn_admiral);
        NetworkDelegat.provideApiModule().check().enqueue(new Callback<Model>() {
            @Override
            public void onResponse(@NonNull Call<Model> call, @NonNull Response<Model> response) {
                if (response.isSuccessful()) {
                    Model model = response.body();
                    if (model != null) parseModel(model);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Model> call, @NonNull Throwable t) {

            }
        });
        btnVulkan.setOnClickListener(__-> {
            if (url1 != null) openLink(url1);
        });
        btnPlatinum.setOnClickListener(__-> {
            if (url2 != null) openLink(url2);
        });
        btnAdmiral.setOnClickListener(__-> {
            if (url3 != null) openLink(url3);
        });


    }

    private void openLink(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

    private void parseModel(Model model) {
        String[] str = model.getUrl().split("&&");
        for (int i = 0; i < str.length; i++) {
            if (i == 0) url1 = str[i];
            if (i == 1) url2 = str[i];
            if (i == 2) url3 = str[i];
        }
    }

}
