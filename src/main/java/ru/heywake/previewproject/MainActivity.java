package ru.heywake.previewproject;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

public class MainActivity extends Activity implements NewsListFragment.OnNewsListFragmentListener {

    Fragment fragmentList, fragmentNews;
    FrameLayout containerList, containerNews;
    FragmentTransaction transaction;

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

        containerList = findViewById(R.id.containerList);
        containerNews = findViewById(R.id.containerNews);

        transaction = getFragmentManager().beginTransaction();


        if (onlyNews) {

            fragmentNews = new NewsFragment();
            transaction.add(containerList.getId(), fragmentNews);
            ((NewsFragment)fragmentNews).setNews(new News(intent.getStringExtra("title"),intent.getStringExtra("description"),intent.getStringExtra("url"),intent.getStringExtra("id")));

        } else {

            Fragment fragmentList = new NewsListFragment();
            transaction.replace(containerList.getId(), fragmentList);
        }

        if (containerNews != null) {
            if (onlyNews) finish();
            fragmentNews = new NewsFragment();
            transaction.replace(containerNews.getId(), fragmentNews);

        }

        transaction.commit();
    }

    @Override
    public void onNewsListFragmentListener(News news) {

        if (fragmentNews != null) ((NewsFragment) fragmentNews).setNews(news);

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
