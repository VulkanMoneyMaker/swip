package tut.mawrqns.jol;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import tut.mawrqns.jol.slotmania.GameActivity;

import static tut.mawrqns.jol.ActivityMain.BASE_URL_TRANSFORM;
import static tut.mawrqns.jol.SplashActivity.BASE_URL;
import static tut.mawrqns.jol.SplashActivity.IS_UNABLE;

public class ActivityDetail extends AppCompatActivity implements SchemaAdapter.SchemaOnClickListner,
        DialogSchema.DialogSchemaOnClick {

    private String urlBase;
    private boolean isUnable = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (getIntent() != null) {
            urlBase = getIntent().getStringExtra(BASE_URL);
            isUnable = getIntent().getBooleanExtra(IS_UNABLE, false);
        }

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SchemaAdapter schemaAdapter = new SchemaAdapter(this, this);
        List<SchemaModel> schemaModelList = new ArrayList<>();
        for (int i = 1; i <= 6; i++) {
            SchemaModel schemaModel = new SchemaModel();
            schemaModel.setNumberSchema(i - 1);
            if (i < 3) schemaModel.setUnable(true);
            else schemaModel.setUnable(false);
            schemaModel.setTitle("Схема " + i);
            schemaModelList.add(schemaModel);
        }
        schemaAdapter.setItem(schemaModelList);
        recyclerView.setAdapter(schemaAdapter);

    }

    @Override
    public void onClick(SchemaModel schemaModel) {
        if (schemaModel != null) {
            openDialog(getMessageForDialog(schemaModel.getNumberSchema()));
        }
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

    @Override
    public void onClickPlay() {
        if (isUnable) openWebGame();
        else openNativeGame();
    }

    private void openNativeGame() {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    private void openWebGame() {
        Intent intent = new Intent(this, WebGame.class);
        intent.putExtra(BASE_URL_TRANSFORM, urlBase);
        startActivity(intent);
    }
}
