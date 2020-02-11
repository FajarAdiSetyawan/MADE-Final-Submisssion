package com.fajaradisetyawan.favorite.viewmodel;

import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.HandlerThread;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fajaradisetyawan.favorite.asynctask.LoadMovieAsync;
import com.fajaradisetyawan.favorite.asynctask.LoadMoviesCallback;
import com.fajaradisetyawan.favorite.model.Movie;

import java.util.ArrayList;

import static com.fajaradisetyawan.favorite.database.DatabaseContract.MovieColumns.MOVIE_URI;

public class MovieViewModel extends ViewModel implements LoadMoviesCallback {
    private MutableLiveData<ArrayList<Movie>> movieList = new MutableLiveData<>();

    public void setMovies(Context context) {
        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());

        DataObserver myObserver = new DataObserver(handler, context, this);
        context.getContentResolver().registerContentObserver(
                MOVIE_URI, true, myObserver);

        new LoadMovieAsync(context, this).execute();
    }

    public LiveData<ArrayList<Movie>> getMovies() {
        return movieList;
    }

    @Override
    public void postExecute(ArrayList<Movie> movies) {
        if (movies != null) {
            movieList.setValue(movies);
        }
    }

    public static class DataObserver extends ContentObserver {
        final Context context;
        final LoadMoviesCallback callback;

        public DataObserver(Handler handler, Context context, LoadMoviesCallback callback) {
            super(handler);
            this.context = context;
            this.callback = callback;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            new LoadMovieAsync(context, callback).execute();
        }
    }
}
