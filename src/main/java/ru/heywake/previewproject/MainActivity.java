package ru.heywake.previewproject;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import java.util.ArrayList;

public class MainActivity extends Activity implements NewsListFragment.OnNewsListFragmentListener {

    Fragment fragmentList, fragmentNews;
    FrameLayout containerList, containerNews;
    FragmentTransaction transaction;
    News selectNews = null;
    static String TEG = "PreviewProject";

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Intent intent = getIntent();
        boolean onlyNews = false;
        if (intent.getStringExtra("type") != null) onlyNews = true;

        if (savedInstanceState != null) {
            selectNews = new News();
            ArrayList<String> list = savedInstanceState.getStringArrayList("news");
            try {

                selectNews.toNews(list);

            } catch (NewsFromArrayException e) {

                Log.e(TEG, e.getMessage());

            }

        }

        containerList = findViewById(R.id.containerList);
        containerNews = findViewById(R.id.containerNews);

        transaction = getFragmentManager().beginTransaction();


        if (onlyNews) {

            fragmentNews = new NewsFragment();
            transaction.add(containerList.getId(), fragmentNews);
            ((NewsFragment) fragmentNews).setNews(new News(intent.getStringExtra("title"),
                    intent.getStringExtra("description"),
                    intent.getStringExtra("url"),
                    intent.getStringExtra("id")));

        } else {

            Fragment fragmentList = new NewsListFragment();
            transaction.replace(containerList.getId(), fragmentList);
        }

        if (containerNews != null) {

            if (onlyNews) finish();
            fragmentNews = new NewsFragment();
            transaction.replace(containerNews.getId(), fragmentNews);
            ((NewsFragment) fragmentNews).setNews(selectNews);

        }

        transaction.commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if(selectNews != null)
        outState.putStringArrayList("news", selectNews.toList());

    }

    @Override
    public void onNewsListFragmentListener(News news) {

        selectNews = news;
        if (fragmentNews != null) ((NewsFragment) fragmentNews).setNews(selectNews);

        if (containerNews == null) {

            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("type", "only_news");
            intent.putExtra("title", news.title);
            intent.putExtra("url", news.url);
            intent.putExtra("description", news.description);
            startActivity(intent);

        }
    }
}
