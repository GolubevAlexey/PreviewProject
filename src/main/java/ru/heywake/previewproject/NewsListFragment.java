package ru.heywake.previewproject;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class NewsListFragment extends Fragment {

    private OnNewsListFragmentListener mListener;
    private JSONObject jsonObject;
    private List<News> listNews = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.news_list_view, null);
    }

    public NewsListFragment() {

    }

    public interface OnNewsListFragmentListener {
        public void onNewsListFragmentListener(News news);

    }

    @Override
    public void onStop() {
        super.onStop();
        if (listNews != null) listNews.clear();
    }

    public void messageLog(String message) {

        ((TextView) getView().findViewById(R.id.textView)).setText(message);
    }

    @Override
    public void onStart() {
        super.onStart();

        AsyncTask<String, Integer, String> asyncTask = new AsyncTask<String, Integer, String>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                messageLog("Pleas wait.\nNews is loading...");
            }

            @Override
            protected String doInBackground(String... strings) {

                String fullString = "";

                try {

                    URL url = new URL("https://www.eveonline.com/rss/json/news");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        fullString += line;
                    }
                    reader.close();

                } catch (Exception e) {
                    //
                }

                return fullString;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try {

                    jsonObject = new JSONObject("{\"news\":" + s + "}");

                    JSONArray jsArray = jsonObject.getJSONArray("news");
                    JSONObject jo;

                    for (int i = 0; i < jsArray.length(); i++) {
                        jo = jsArray.getJSONObject(i);
                        listNews.add(new News(jo.getString("title"), jo.getString("description"), jo.getString("link"), jo.getString("id")));
                    }


                    DBArchive dbHelper = new DBArchive(getContext());

                    for(News el: listNews)
                    dbHelper.insertValue(el);

                    //SQLiteDatabase db = dbHelper.getWritableDatabase();

                   // Cursor cursor = db.rawQuery("Select * From entry2", new String[]{});

                   // while(cursor.moveToNext()) {
                    //    Log.w("123", " cursor " + cursor.getString(0)+ cursor.getString(3));
                   // }

                    if (isResumed()) {
                        ListView lv = (ListView) getView().findViewById(R.id.listView);
                        NewsArrayAdapter<News> adapter = new NewsArrayAdapter<News>(getContext(), R.layout.news_list_item, listNews);
                        getView().findViewById(R.id.textView).setVisibility(View.GONE);
                        lv.setAdapter(adapter);
                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                mListener.onNewsListFragmentListener(listNews.get(position));
                            }
                        });
                    }

                    //mListener.onNewsListFragmentListener(listNews.get(0));

                } catch (JSONException e) {
                    messageLog("Sorry.\nLoad error\nJSON is wrong or URL is wrong or Internet is wrong");
                }
            }
        };

        asyncTask.execute();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mListener = (OnNewsListFragmentListener) getActivity();
    }

    final public void method() {

    }

}

