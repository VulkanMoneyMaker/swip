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

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import tut.mawrqns.jol.db.BalanceDao;
import tut.mawrqns.jol.slotmania.GameActivity;

import static tut.mawrqns.jol.ActivityMain.BASE_URL_TRANSFORM;
import static tut.mawrqns.jol.SplashActivity.BASE_URL;
import static tut.mawrqns.jol.SplashActivity.IS_UNABLE;

public class ActivityDetail extends AppCompatActivity implements SchemaAdapter.SchemaOnClickListner,
        DialogSchema.DialogSchemaOnClick {

    private String urlBase;
    private boolean isUnable = false;
    private BalanceDao balanceDao;
    private CompositeDisposable compositeDis;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (getIntent() != null) {
            urlBase = getIntent().getStringExtra(BASE_URL);
            isUnable = getIntent().getBooleanExtra(IS_UNABLE, false);
        }
        compositeDis = new CompositeDisposable();
        balanceDao = ApplicationTc.getAppDatabase().balanceDao();
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SchemaAdapter schemaAdapter = new SchemaAdapter(this, this);
        List<SchemaModel> schemaItem = new ArrayList<>();
        for (int i = 1; i <= 8; i++) {
            SchemaModel shemaModel = new SchemaModel();
            shemaModel.setTitle("Совет " + i);
            shemaModel.setNumberSchema(i - 1);
            if (i < 4) shemaModel.setUnable(true);
            else shemaModel.setUnable(false);
            if (i == 3) shemaModel.setVideoContnent(true);
            else shemaModel.setVideoContnent(false);
            schemaItem.add(shemaModel);


        }
        schemaAdapter.setItem(schemaItem);
        recyclerView.setAdapter(schemaAdapter);

    }

    @Override
    public void onClick(SchemaModel schemaModel) {
        if (schemaModel != null) {
            openDialog(getMessageForDialog(schemaModel.getNumberSchema()),
                    getTitleForDialog(schemaModel.getNumberSchema()), schemaModel.isVideoContnent());
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

    private String getTitleForDialog(int numberSchema) {
        String schemaString;
        switch (numberSchema) {
            case 0:
                schemaString = getString(R.string.schema_title_1);
                break;
            case 1:
                schemaString = getString(R.string.schema_title_2);
                break;
            case 2:
                schemaString = getString(R.string.schema_title_3);
                break;
            default:
                schemaString = "";
        }
        return schemaString;
    }


    private void openDialog(String text, String title, boolean isVideoContent) {
        if (title.isEmpty()) return;
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        DialogSchema dialogFragment = DialogSchema.newInstance(text, title, isVideoContent);
        dialogFragment.show(ft, "dialog");
    }

    @Override
    public void onClickPlay() {
        addBalanceRealGame();
        if (isUnable) openWebGame();
        else openNativeGame();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (compositeDis != null) compositeDis.dispose();
    }

    private void addBalanceRealGame() {
        compositeDis.add(balanceDao
                .getBalance()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(balanceEntity -> {
                    if (balanceEntity.getBalancePre() < 300) {
                        int balancePre = balanceEntity.getBalancePre();
                        int balance = balanceEntity.getBalance();
                        balanceEntity.setBalancePre(balancePre + 100);
                        balanceEntity.setBalance(balance + 100);
                        compositeDis.add(Completable.fromAction(() -> balanceDao.update(balanceEntity))
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread()).subscribe());
                    }
                }));
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
