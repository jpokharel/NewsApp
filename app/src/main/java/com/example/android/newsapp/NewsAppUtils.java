package com.example.android.newsapp;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by jiwanpokharel89 on 11/5/2017.
 */

public class NewsAppUtils {

    private static final int READ_TIMEOUT = 10000;
    private static final int CONNECT_TIMEOUT = 15000;
    private static final String RESPONSE = "response";
    private static final String RESULTS = "results";
    private static final String ID = "id";
    private static final String TITLE = "webTitle";
    private static final String SECTION = "sectionName";
    private static final String AUTHOR = "authors";
    private static final String URL = "webUrl";
    private static final String DATE_PUBLISHED = "webPublicationDate";

    public NewsAppUtils() {
    }

    public static List<News> fetchNewsData(String stringUrl) {
        URL url = createUrl(stringUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return getNewsData(jsonResponse);
    }

    public static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e("NewsAppUtils: ", "Malformed URL", e);
        }
        return url;
    }

    public static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null)
            return jsonResponse;
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(READ_TIMEOUT);
            httpURLConnection.setConnectTimeout(CONNECT_TIMEOUT);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            if (httpURLConnection.getResponseCode() == 200) {
                inputStream = httpURLConnection.getInputStream();
                jsonResponse = readFromInputStream(inputStream);
            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null)
                httpURLConnection.disconnect();
            if (inputStream != null)
                inputStream.close();
        }
        return jsonResponse;
    }

    public static String readFromInputStream(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String eachLine = bufferedReader.readLine();
            while (eachLine != null) {
                stringBuilder.append(eachLine);
                eachLine = bufferedReader.readLine();
            }
        }
        return stringBuilder.toString();
    }

    public static ArrayList<News> getNewsData(String jsonResponse) {
        if (TextUtils.isEmpty(jsonResponse))
            return null;
        ArrayList<News> news = new ArrayList<>();
        try {
            //Parse JSON Data in here...
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONObject newsObject = jsonObject.getJSONObject(RESPONSE);
            JSONObject eachJson;
            if (newsObject.has(RESULTS)) {
                JSONArray resultsArray = newsObject.getJSONArray(RESULTS);
                for (int i = 0; i < resultsArray.length(); i++) {
                    eachJson = resultsArray.getJSONObject(i);
                    String id = eachJson.getString(ID);
                    String title = eachJson.getString(TITLE);
                    String section = eachJson.getString(SECTION);
                    String author = "";
                    if (eachJson.has(AUTHOR)) {
                        JSONArray authors = eachJson.optJSONArray(AUTHOR); //To handle no authors.
                        for (int n = 0; n < authors.length(); n++) {
                            if (author == "")
                                author = authors.getString(n);
                            else
                                author += (", " + authors.getString(n));
                        }
                    }
                    String datePublished = "";
                    if (eachJson.has(DATE_PUBLISHED))
                        datePublished = getFormattedDate(eachJson.getString(DATE_PUBLISHED));
                    String url = eachJson.getString(URL);

                    news.add(new News(id, title, section, author, datePublished, url));
                }
            }
        } catch (JSONException jsonException) {
            Log.e("NewsAppUtils.java", "Problem parsing News App Data!");
            jsonException.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return news;
    }

    @SuppressLint("SimpleDateFormat")
    private static String getFormattedDate(String dateString) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        SimpleDateFormat outputFormat = new SimpleDateFormat("LLL dd, yyyy");
        Date date = formatter.parse(dateString.replaceAll("Z$", "+0000"));
        return outputFormat.format(date);
    }

}
