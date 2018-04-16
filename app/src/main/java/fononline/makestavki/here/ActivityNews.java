package fononline.makestavki.here;


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
            case 1:
                return R.drawable.news_3_min;
            case 2:
                return R.drawable.news_1_min;
            case 3:
                return R.drawable.news_4_min;
            case 4:
                return R.drawable.news_5_min;
            default:
                return R.drawable.news_2_min;
        }
    }
}
