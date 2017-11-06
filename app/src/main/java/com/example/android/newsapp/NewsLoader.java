package com.example.android.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by jiwanpokharel89 on 11/5/2017.
 */

public class NewsLoader extends AsyncTaskLoader<List<News>> {
    private String url;

    public NewsLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    public List<News> loadInBackground() {
        if(this.url == null)
            return null;
        return NewsAppUtils.fetchNewsData(url);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
