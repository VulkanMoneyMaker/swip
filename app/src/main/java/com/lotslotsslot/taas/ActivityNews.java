package com.lotslotsslot.taas;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

public class ActivityNews extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        FrameLayout frameLayout = findViewById(R.id.container);
        if (getIntent() != null) {
            frameLayout.setBackground(getResources().getDrawable(getResId
                    (getIntent().getIntExtra(ActivityStavki.NEWS_ID,
                            0))));
        }
    }

    private Integer getResId(int intExtra) {
        switch (intExtra) {
            case 0:
                return R.drawable.news_1;
            case 1:
                return R.drawable.news_2;
            case 2:
                return R.drawable.news_3;
            default:
                return R.drawable.news_1;
        }
    }
}
