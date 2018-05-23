package fere.betstavki.cityonline;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ActivityNews extends AppCompatActivity {

    TextView tv_toolbar;
    TextView tv_secondary_text;
    TextView tv_main_text;
    TextView tv_large_text;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        tv_toolbar = findViewById(R.id.tv_toolbar);
        tv_secondary_text = findViewById(R.id.tv_secondary_text);
        tv_main_text = findViewById(R.id.tv_main_text);
        tv_large_text = findViewById(R.id.tv_large_text_text);


        if (getIntent() != null) {
            setData(getIntent().getIntExtra(ActivityStavki.NEWS_ID, 0));
        }
    }

    private void setData(int intExtra) {

        String[] main_text = getResources().getStringArray(R.array.main_text);
        String[] secondary_text = getResources().getStringArray(R.array.secondary_text);
        String[] large_text = getResources().getStringArray(R.array.large_text);

        tv_toolbar.setText(main_text[intExtra]);
        tv_main_text.setText(main_text[intExtra]);
        tv_secondary_text.setText(secondary_text[intExtra]);
        tv_large_text.setText(large_text[intExtra]);
    }
}
