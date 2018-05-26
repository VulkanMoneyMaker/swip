package stavki.bukmekerskaya.jfdha;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivityNews extends AppCompatActivity {

    private ImageView mImageContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        mImageContent = findViewById(R.id.image_content);


        if (getIntent() != null) {
            setData(getIntent().getIntExtra(ActivityStavki.NEWS_ID, 0));
        }
    }

    private void setData(int intExtra) {

        int[] drawableIds = {
                R.drawable.news_opened_1,
                R.drawable.news_opened_2,
                R.drawable.news_opened_3,
                R.drawable.news_opened_4,
                R.drawable.news_opened_5
        };

        Drawable drawable = ContextCompat.getDrawable(this, drawableIds[intExtra]);
        mImageContent.setImageDrawable(drawable);
    }
}
