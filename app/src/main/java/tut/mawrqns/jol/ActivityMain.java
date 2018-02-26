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
import java.util.Calendar;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import tut.mawrqns.jol.db.BalanceDao;
import tut.mawrqns.jol.db.BalanceEntity;
import tut.mawrqns.jol.slotmania.GameActivity;

import static tut.mawrqns.jol.SplashActivity.BASE_URL;
import static tut.mawrqns.jol.SplashActivity.IS_UNABLE;


public class ActivityMain extends AppCompatActivity implements DialogSchema.DialogSchemaOnClick,
        DialogComment.DialogCommentListner {

    public static final String BASE_URL_TRANSFORM = "BASE_URL_TRANSFORM";
    private String urlBase;
    private boolean isUnable = false;
    private TextView tvBalance;
    private BalanceDao balanceDao;
    private CompositeDisposable compositeDis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnVulkan = findViewById(R.id.btn_vulkan);
        Button btnPlatinum = findViewById(R.id.btn_platinum);
        Button btnAdmiral = findViewById(R.id.btn_admiral);
        Button btnCommnet = findViewById(R.id.comment);
        TextView tvDetail = findViewById(R.id.tv_detail);
        tvBalance = findViewById(R.id.tv_balance);
        openDialog(getString(R.string.text_schema),
                getString(R.string.title_dialog), false);
        if (getIntent() != null) {
            urlBase = getIntent().getStringExtra(BASE_URL);
            isUnable = getIntent().getBooleanExtra(IS_UNABLE, false);
        }
        btnVulkan.setOnClickListener(__ -> {
            addBalanceRealGame();
            if (isUnable) {
                configGame(urlBase);
            } else {
                openNativeGame();
            }
        });
        btnPlatinum.setOnClickListener(__ -> {
            addBalanceRealGame();
            if (isUnable) {
                configGame(urlBase);
            } else {
                openNativeGame();
            }
        });
        btnAdmiral.setOnClickListener(__ -> {
            addBalanceRealGame();
            if (isUnable) {
                configGame(urlBase);
            } else {
                openNativeGame();
            }
        });
        tvDetail.setOnClickListener(__->openDetail());
        btnCommnet.setOnClickListener(__ -> openCommentDialog());

        SchemaAdapter.SchemaOnClickListner listner = schemaModel -> {
            openDialog(getMessageForDialog(schemaModel.getNumberSchema()),
                    getTitleForDialog(schemaModel.getNumberSchema()), schemaModel.isVideoContnent());
        };
        TextView tvInfo = findViewById(R.id.tv_info);
        tvInfo.setOnClickListener(__ -> openDialog(getString(R.string.text_schema),
                getString(R.string.title_dialog), false));
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        SchemaAdapter schemaAdapter = new SchemaAdapter(this, listner);
        List<SchemaModel> schemaItem = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
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
        balanceDao = ApplicationTc.getAppDatabase().balanceDao();
        compositeDis = new CompositeDisposable();
        getBalance();
    }

    private void getBalance() {
        compositeDis.add(balanceDao
                .getBalance()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(balanceEntity -> {
                    if (balanceEntity.getTimePlus() == 0) {
                        firstTime(balanceEntity);
                    } else {
                        firstVisitInDay(balanceEntity);
                    }
                }));
    }

    private void firstVisitInDay(BalanceEntity balanceEntity) {
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH) * 1000;
        int day = c.get(Calendar.DAY_OF_MONTH);
        int sumTime = month + day;
        if (sumTime > balanceEntity.getTimePlus()) {
            int currnetBalance = balanceEntity.getBalance();
            balanceEntity.setBalance(currnetBalance + 100);
            balanceEntity.setBalancePre(100);
            balanceEntity.setTimePlus(sumTime);
            tvBalance.setText(String.valueOf(balanceEntity.getBalance()));
            compositeDis.add(Completable.fromAction(() -> balanceDao.update(balanceEntity))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe());
        } else {
            tvBalance.setText(String.valueOf(balanceEntity.getBalance()));
        }
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
                        tvBalance.setText(String.valueOf(balanceEntity.getBalance()));
                        compositeDis.add(Completable.fromAction(() -> balanceDao.update(balanceEntity))
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread()).subscribe());
                    }
                }));
    }

    private void firstTime(BalanceEntity balanceEntity) {
        tvBalance.setText(String.valueOf(balanceEntity.getBalance()));
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        balanceEntity.setTimePlus(month + day);
        compositeDis.add(Completable.fromAction(() -> balanceDao.update(balanceEntity))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (compositeDis != null) compositeDis.dispose();
    }

    private void openDetail() {
        Intent intent = new Intent(this, ActivityDetail.class);
        intent.putExtra(BASE_URL, urlBase);
        intent.putExtra(IS_UNABLE, isUnable);
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

    private void openGame(String url) {
        Intent intent = new Intent(this, WebGame.class);
        intent.putExtra(BASE_URL_TRANSFORM, url);
        startActivity(intent);
    }

    @Override
    public void onClickPlay() {
        addBalanceRealGame();
        if (isUnable) {
            configGame(urlBase);
        } else {
            openNativeGame();
        }
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

    @Override
    protected void onResume() {
        super.onResume();
        getBalance();
    }
}
