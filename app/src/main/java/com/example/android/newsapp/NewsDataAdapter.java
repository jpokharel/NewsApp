package com.example.android.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jiwanpokharel89 on 10/29/2017.
 */

public class NewsDataAdapter extends ArrayAdapter<News> {
    public NewsDataAdapter(@NonNull Context context, @NonNull ArrayList<News> objects) {
        super(context, 0, objects);
    }

    public static int getHighlightColor(String section) {
        //Return color background for "Section" text.
        if (section.equalsIgnoreCase("science"))
            return R.color.science;
        else if (section.equalsIgnoreCase("technology"))
            return R.color.technology;
        else if (section.equalsIgnoreCase("politics"))
            return R.color.politics;
        else if (section.equalsIgnoreCase("society"))
            return R.color.society;
        else if (section.equalsIgnoreCase("opinion"))
            return R.color.opinion;
        else if (section.equalsIgnoreCase("stage"))
            return R.color.stage;
        else
            return R.color.others;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);

        News newsData = getItem(position);
        assert newsData != null;

        TextView titleText = (TextView) convertView.findViewById(R.id.title_text_view);
        titleText.setText(newsData.getTitle());

        TextView newsDescription = (TextView) convertView.findViewById(R.id.description_text_view);
        newsDescription.setText(newsData.getId());

        TextView section = (TextView) convertView.findViewById(R.id.section_text_view);
        section.setText(newsData.getSection());
        section.setBackgroundResource(getHighlightColor(newsData.getSection()));


        TextView author = (TextView) convertView.findViewById(R.id.author_text_view);
        author.setText(newsData.getAuthor());

        TextView publishedDate = (TextView) convertView.findViewById(R.id.published_date_text_view);
        publishedDate.setText(newsData.getDatePublished());


        return convertView;
    }
}
