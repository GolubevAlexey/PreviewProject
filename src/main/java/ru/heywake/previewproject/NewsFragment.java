package ru.heywake.previewproject;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class NewsFragment extends Fragment {

    OnNewsFragmentListener mListener;
    News news = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.news_view, null);
    }

    public NewsFragment() {

    }


    public void setNews(News news) {

        this.news = news;

        if (isResumed())
            setNewsAction();

    }

    private void setNewsAction() {

        if (news != null) {
            ((TextView) getView().findViewById(R.id.title)).setText(news.title);

            TextView tv = (TextView) getView().findViewById(R.id.url);
            tv.setText(news.url);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = ((TextView) v).getText().toString();
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                }
            });

            ((TextView) getView().findViewById(R.id.description)).setText(news.description);
        }

    }

    public interface OnNewsFragmentListener {
        public void onSingletonListener(Integer i);

    }

    @Override
    public void onResume() {
        super.onResume();
        setNewsAction();
    }

    final public void method() {

    }

}

