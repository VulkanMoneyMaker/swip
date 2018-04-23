package fonbet.svakionline.here;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.TextView;

public class ActivityNews extends AppCompatActivity {

    TextView tv_title;
    TextView tv_content;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        tv_title = findViewById(R.id.tv_title);
        tv_content = findViewById(R.id.tv_content);
        if (getIntent() != null) {
            getResId(getIntent().getIntExtra(ActivityStavki.NEWS_ID,
                            0));
        }
    }

    private void getResId(int intExtra) {
        String title = "";
        String content = "";
        switch (intExtra) {
            case 1:
                title = getString(R.string.title_2);
                content = getString(R.string.content_2);
                break;
            case 2:
                title = getString(R.string.title_3);
                content = getString(R.string.content_3);
                break;
            case 3:
                title = getString(R.string.title_4);
                content = getString(R.string.content_4);
                break;
            case 4:
                title = getString(R.string.title_5);
                content = getString(R.string.content_5);
                break;
            default:
                title = getString(R.string.title_1);
                content = getString(R.string.content_1);
        }

        tv_title.setText(title);
        tv_content.setText(content);
    }
}
