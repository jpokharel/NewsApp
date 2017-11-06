package com.example.android.newsapp;

import android.text.TextUtils;
import android.util.Log;

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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiwanpokharel89 on 11/5/2017.
 */

public class NewsAppUtils {

    public NewsAppUtils(){}

    public static List<News> fetchNewsData(String stringUrl){
        URL url = createUrl(stringUrl);
        String jsonResponse = null;
        try{
            jsonResponse = makeHttpRequest(url);
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
        return getNewsData(jsonResponse);
    }

    public static URL createUrl(String stringUrl){
        URL url = null;
        try{
            url=new URL(stringUrl);
        }catch(MalformedURLException e){
            Log.e("NewsAppUtils: ","Malformed URL", e);
        }
        return url;
    }

    public static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse="";
        if(url == null)
            return jsonResponse;
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        try{
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(50000);
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            if(httpURLConnection.getResponseCode() == 200){
                inputStream = httpURLConnection.getInputStream();
                jsonResponse = readFromInputStream(inputStream);
            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(httpURLConnection != null)
                httpURLConnection.disconnect();
            if(inputStream != null)
                inputStream.close();
        }
        return jsonResponse;
    }

    public static String readFromInputStream(InputStream inputStream) throws  IOException{
        StringBuilder stringBuilder = new StringBuilder();
        if(inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String eachLine = bufferedReader.readLine();
            while(eachLine != null){
                stringBuilder.append(eachLine);
                eachLine=bufferedReader.readLine();
            }
        }
        return  stringBuilder.toString();
    }

    public static ArrayList<News> getNewsData(String jsonResponse){
        if(TextUtils.isEmpty(jsonResponse))
            return null;
        ArrayList<News> news = new ArrayList<>();
        try{
            //Parse JSON Data in here...
            JSONObject jsonObject = new JSONObject(jsonResponse);
            //TODO: Add here
        }catch(JSONException ioe){
            ioe.printStackTrace();
        }
        return news;
        //TODO: Add here
    }

}
