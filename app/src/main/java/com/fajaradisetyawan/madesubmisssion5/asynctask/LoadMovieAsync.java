package com.fajaradisetyawan.madesubmisssion5.asynctask;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import com.fajaradisetyawan.madesubmisssion5.database.MappingHelper;
import com.fajaradisetyawan.madesubmisssion5.model.Movie;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.fajaradisetyawan.madesubmisssion5.database.DatabaseContract.MovieColumns.MOVIE_URI;

public class LoadMovieAsync extends AsyncTask<Void, Void, ArrayList<Movie>> {
    private final WeakReference<Context> weakContext;
    private final WeakReference<LoadMoviesCallback> weakCallback;

    public LoadMovieAsync(Context context, LoadMoviesCallback moviesCallback) {
        weakContext = new WeakReference<>(context);
        weakCallback = new WeakReference<>(moviesCallback);
    }

    @Override
    protected ArrayList<Movie> doInBackground(Void... voids) {
        Context context = weakContext.get();
        Cursor dataCursor = context.getContentResolver().query(MOVIE_URI, null, null, null, null);
        return MappingHelper.mapCursorMovies(dataCursor);
    }

    @Override
    protected void onPostExecute(ArrayList<Movie> movies) {
        super.onPostExecute(movies);
        if (movies != null) {
            weakCallback.get().postExecute(movies);
        }
    }
}
