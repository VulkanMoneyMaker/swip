package here.betstavki.cityonline;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class ActivityStavki extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterNews adapterNews;
    public static final String NEWS_ID = "news_id";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stavki);
        recyclerView = findViewById(R.id.recycler_view);
        AdapterNews.AdapterNewsListner adapterNewsListner = this::openNews;
        String[] main_text = getResources().getStringArray(R.array.main_text);
        String[] secondary_text = getResources().getStringArray(R.array.secondary_text);
        ArrayList<Pair<String, String >> resources = new ArrayList<>();
        for (int i = 0; i < main_text.length; ++i) {
            resources.add(new Pair<>(main_text[i], secondary_text[i]));
        }
        adapterNews = new AdapterNews(getApplicationContext(), resources, adapterNewsListner);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapterNews);


    }

    private void openNews(int position) {
        Intent intent = new Intent(this, ActivityNews.class);
        intent.putExtra(NEWS_ID, position);
        startActivity(intent);
    }
}
