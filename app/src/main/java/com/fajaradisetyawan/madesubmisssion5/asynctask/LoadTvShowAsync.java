package com.fajaradisetyawan.madesubmisssion5.asynctask;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import com.fajaradisetyawan.madesubmisssion5.database.MappingHelper;
import com.fajaradisetyawan.madesubmisssion5.model.TvShow;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.fajaradisetyawan.madesubmisssion5.database.DatabaseContract.TVShowColumns.TVSHOW_URI;

public class LoadTvShowAsync extends AsyncTask<Void, Void, ArrayList<TvShow>> {
    private final WeakReference<Context> weakContext;
    private final WeakReference<LoadTvShowsCallback> weakCallback;

    public LoadTvShowAsync(Context context, LoadTvShowsCallback showsCallback) {
        weakContext = new WeakReference<>(context);
        weakCallback = new WeakReference<>(showsCallback);
    }

    @Override
    protected ArrayList<TvShow> doInBackground(Void... voids) {
        Context context = weakContext.get();
        Cursor dataCursor = context.getContentResolver().query(TVSHOW_URI, null, null, null, null);
        return MappingHelper.mapCursorTvShows(dataCursor);
    }

    @Override
    protected void onPostExecute(ArrayList<TvShow> tvShows) {
        super.onPostExecute(tvShows);
        if (tvShows != null) {
            weakCallback.get().postExecute(tvShows);
        }
    }
}
