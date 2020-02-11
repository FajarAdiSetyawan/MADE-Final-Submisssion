package com.fajaradisetyawan.madesubmisssion5.viewmodel.main;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fajaradisetyawan.madesubmisssion5.model.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MovieViewModel extends ViewModel {

    private static final String API_KEY = "42f8c3e2a9a24c1da94af7502c14d314";
    private MutableLiveData<ArrayList<Movie>> listMovie = new MutableLiveData<>();
    private final ArrayList<Movie> movies = new ArrayList<>();
    public boolean query = false;

    public void setMovies() {
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.themoviedb.org/3/discover/movie?api_key=" + API_KEY + "&language=en-US";

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject itemMovie = list.getJSONObject(i);
                        Movie itemMovieView = new Movie(itemMovie);
                        movies.add(itemMovieView);
                    }

                    listMovie.postValue(movies);
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

    public LiveData<ArrayList<Movie>> getMovies() {
        return listMovie;
    }

    public void setMoviesSearch(final String title) {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Movie> movies = new ArrayList<>();
        final String url = "https://api.themoviedb.org/3/search/movie?api_key=" + API_KEY +
                "&language=en-US&query=" + title;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject object = new JSONObject(result);
                    JSONArray results = object.getJSONArray("results");

                    for (int i = 0; i < results.length(); i++) {
                        JSONObject movieObj = results.getJSONObject(i);
                        Movie movie = new Movie(movieObj);
                        movies.add(movie);
                    }

                    listMovie.postValue(movies);

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

    public LiveData<ArrayList<Movie>> getMoviesSearch() {
        return listMovie;
    }
}
