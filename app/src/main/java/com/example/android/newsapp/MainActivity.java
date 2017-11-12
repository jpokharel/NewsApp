package com.example.android.newsapp;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    private static String BASE_URL = "http://content.guardianapis.com/search?q=debates&api-key=test";
    NewsDataAdapter newsDataAdapter;
    private TextView emptyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext()
                .getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        newsDataAdapter = new NewsDataAdapter(this, new ArrayList<News>());
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(newsDataAdapter);
        emptyTextView = (TextView) findViewById(R.id.empty_text_view);
        listView.setEmptyView(emptyTextView);

        if (networkInfo != null && networkInfo.isConnectedOrConnecting())
            getLoaderManager().initLoader(1, null, this);
        else {
            View progressBar = findViewById(R.id.progress_bar);
            progressBar.setVisibility(View.GONE);
            emptyTextView.setText(R.string.no_internet_text);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String url = newsDataAdapter.getItem(position).getUrl();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        return new NewsLoader(this, BASE_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {
        View progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
        emptyTextView.setText(R.string.no_news_text);
        newsDataAdapter.clear();
        if (data != null && !data.isEmpty())
            newsDataAdapter.addAll(data);
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        newsDataAdapter.clear();
    }
}
