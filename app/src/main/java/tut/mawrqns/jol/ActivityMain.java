package tut.mawrqns.jol;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tut.mawrqns.jol.network.NetworkDelegat;
import tut.mawrqns.jol.network.model.Model;


public class ActivityMain extends AppCompatActivity implements DialogSchema.DialogSchemaOnClick{

    private String url1;
    private String url2;
    private String url3;
    private TextView textDialog;
    private Button btnOk;
    private Button btnNegative;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openDialog(getString(R.string.text_schema));
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
        btnVulkan.setOnClickListener(__ -> {
            if (url1 != null) openLink(url1);
        });
        btnPlatinum.setOnClickListener(__ -> {
            if (url2 != null) openLink(url2);
        });
        btnAdmiral.setOnClickListener(__ -> {
            if (url3 != null) openLink(url3);
        });
        SchemaAdapter.SchemaOnClickListner listner = schemaModel -> {
            openDialog(getMessageForDialog(schemaModel.getNumberSchema()));
        };
        TextView tvInfo = findViewById(R.id.tv_info);
        tvInfo.setOnClickListener(__ -> openDialog(getString(R.string.text_schema)));
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 4);
        recyclerView.setLayoutManager(layoutManager);
        SchemaAdapter schemaAdapter = new SchemaAdapter(this, listner);
        List<SchemaModel> schemaItem = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            SchemaModel shemaModel = new SchemaModel();
            shemaModel.setTitle("Схема " + i);
            shemaModel.setNumberSchema(i - 1);
            if (i < 3) shemaModel.setUnable(true);
            else shemaModel.setUnable(false);

            schemaItem.add(shemaModel);


        }
        schemaAdapter.setItem(schemaItem);
        recyclerView.setAdapter(schemaAdapter);
        initDialog();

    }

    private void initDialog() {

    }

    private String getMessageForDialog(int numberSchema) {
        String schemaString;
        switch (numberSchema) {
            case 0:
                schemaString = getString(R.string.schema1);
                break;
            case 1:
                schemaString = getString(R.string.schema2);
                break;
            default:
                schemaString = "";
        }
        return schemaString;
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

    private void openDialog(String text) {
        if (text.isEmpty()) return;
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        DialogSchema dialogFragment = DialogSchema.newInstance(text);
        dialogFragment.show(ft, "dialog");
    }

    private void openGame() {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClickPlay() {
        openGame();
    }
}
