package pom.jnapwngo.trus;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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
        List<String> text = new ArrayList<>();
        text.add("test 1");
        text.add("test 2");
        text.add("test 3");

        List<Integer> icons = new ArrayList<>();
        icons.add(R.drawable.icon_1);
        icons.add(R.drawable.icon_2);
        icons.add(R.drawable.icon_3);

        AdapterNews.AdapterNewsListner adapterNewsListner = position -> {
            openNews(position);
        };
        adapterNews = new AdapterNews(getApplicationContext(), text, icons, adapterNewsListner);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapterNews);


    }

    private void openNews(int position) {
        Intent intent = new Intent(this, ActivityNews.class);
        intent.putExtra(NEWS_ID, position);
        startActivity(intent);
    }
}
