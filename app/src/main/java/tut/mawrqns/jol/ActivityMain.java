package tut.mawrqns.jol;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.applinks.AppLinkData;

import java.util.ArrayList;
import java.util.List;

import tut.mawrqns.jol.slotmania.GameActivity;

import static tut.mawrqns.jol.SplashActivity.BASE_URL;
import static tut.mawrqns.jol.SplashActivity.IS_UNABLE;


public class ActivityMain extends AppCompatActivity implements DialogSchema.DialogSchemaOnClick,
        DialogComment.DialogCommentListner {

    public static final String BASE_URL_TRANSFORM = "BASE_URL_TRANSFORM";
    private String urlBase;
    private boolean isUnable = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnVulkan = findViewById(R.id.btn_vulkan);
        Button btnPlatinum = findViewById(R.id.btn_platinum);
        Button btnAdmiral = findViewById(R.id.btn_admiral);
        Button btnCommnet = findViewById(R.id.comment);
        TextView tvDetail = findViewById(R.id.tv_detail);
        openDialog(getString(R.string.text_schema));
        if (getIntent() != null) {
            urlBase = getIntent().getStringExtra(BASE_URL);
            isUnable = getIntent().getBooleanExtra(IS_UNABLE, false);
        }
        btnVulkan.setOnClickListener(__ -> {
            if (isUnable) configGame(urlBase);
            else openNativeGame();
        });
        btnPlatinum.setOnClickListener(__ -> {
            if (isUnable) configGame(urlBase);
            else openNativeGame();
        });
        btnAdmiral.setOnClickListener(__ -> {
            if (isUnable) configGame(urlBase);
            else openNativeGame();
        });
        tvDetail.setOnClickListener(__->openDetail());
        btnCommnet.setOnClickListener(__ -> openCommentDialog());

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
    }

    private void openDetail() {
        Intent intent = new Intent(this, ActivityDetail.class);
        startActivity(intent);
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

    private void openGame(String url) {
        Intent intent = new Intent(this, WebGame.class);
        intent.putExtra(BASE_URL_TRANSFORM, url);
        startActivity(intent);
    }

    @Override
    public void onClickPlay() {
        if (isUnable) configGame(urlBase);
        else openNativeGame();
    }

    private void openNativeGame() {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    private void configGame(final String url) {
        AppLinkData.fetchDeferredAppLinkData(this,
                appLinkData -> {
                    if (appLinkData != null) {
                        String trasform = getTransformUrl(appLinkData.getTargetUri(), url);
                        if (!trasform.equals(url)) openGame(trasform);
                    }
                }
        );

        openGame(url);
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

    public void openCommentDialog() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog_comment");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        DialogComment dialogFragment = DialogComment.newInstance();
        dialogFragment.show(ft, "dialog_comment");
    }

    @Override
    public void onComment() {
        Toast.makeText(this, getString(R.string.toast_commet), Toast.LENGTH_LONG).show();
    }
}
