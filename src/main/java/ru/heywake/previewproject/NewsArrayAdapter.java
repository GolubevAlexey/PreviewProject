package ru.heywake.previewproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


public class NewsArrayAdapter<T extends News> extends ArrayAdapter<T> {


    private List objects;

    public NewsArrayAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater ltInflater = LayoutInflater.from(getContext());
        View view = ltInflater.inflate(R.layout.news_list_item, null, false);

        News tmp = (News) objects.get(position);

        ((TextView) view.findViewById(R.id.title)).setText(tmp.title);

        String description = (tmp.description.length() > 300) ? tmp.description.substring(0, 300) + "..." : tmp.description;
        ((TextView) view.findViewById(R.id.description)).setText(description);

        return view;
    }
}
