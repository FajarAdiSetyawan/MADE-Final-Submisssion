package com.fajaradisetyawan.madesubmisssion5.viewmodel.main;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fajaradisetyawan.madesubmisssion5.model.TvShow;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TvShowViewModel extends ViewModel {

    private static final String API_KEY = "42f8c3e2a9a24c1da94af7502c14d314";
    private MutableLiveData<ArrayList<TvShow>> listTvShow = new MutableLiveData<>();
    private final ArrayList<TvShow> tvShows = new ArrayList<>();
    public boolean query = false;

    public void setTvShows() {
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.themoviedb.org/3/discover/tv?api_key=" + API_KEY + "&language=en-US";

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject itemTV = list.getJSONObject(i);
                        TvShow itemTvShow = new TvShow(itemTV);
                        tvShows.add(itemTvShow);
                    }

                    listTvShow.postValue(tvShows);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }

    public LiveData<ArrayList<TvShow>> getTvShow() {
        return listTvShow;
    }

    public void getTvShowSeacrh(final String title) {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<TvShow> tvShows = new ArrayList<>();
        final String url = "https://api.themoviedb.org/3/search/tv?api_key=" + API_KEY +
                "&language=en-US&query=" + title;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject object = new JSONObject(result);
                    JSONArray results = object.getJSONArray("results");

                    for (int i = 0; i < results.length(); i++) {
                        JSONObject tvshowObj = results.getJSONObject(i);
                        TvShow show = new TvShow(tvshowObj);
                        tvShows.add(show);
                    }

                    listTvShow.postValue(tvShows);

                } catch (Exception e) {
                    Log.d("Exception: ", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure: ", error.getMessage());
            }
        });
    }

}
