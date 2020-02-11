package com.fajaradisetyawan.favorite.viewmodel;

import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.HandlerThread;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fajaradisetyawan.favorite.asynctask.LoadTvShowAsync;
import com.fajaradisetyawan.favorite.asynctask.LoadTvShowsCallback;
import com.fajaradisetyawan.favorite.model.TvShow;

import java.util.ArrayList;

import static com.fajaradisetyawan.favorite.database.DatabaseContract.TVShowColumns.TVSHOW_URI;

public class TvShowViewModel extends ViewModel implements LoadTvShowsCallback {
    private MutableLiveData<ArrayList<TvShow>> showList = new MutableLiveData<>();

    public void setTvShows(Context context) {
        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());

        DataObserver myObserver = new DataObserver(handler, context, this);
        context.getContentResolver().registerContentObserver(
                TVSHOW_URI, true, myObserver);

        new LoadTvShowAsync(context, this).execute();
    }

    public LiveData<ArrayList<TvShow>> getTvShows() {
        return showList;
    }

    @Override
    public void postExecute(ArrayList<TvShow> shows) {
        if (shows != null) {
            showList.setValue(shows);
        }
    }

    public static class DataObserver extends ContentObserver {
        final Context context;
        final LoadTvShowsCallback callback;

        public DataObserver(Handler handler, Context context, LoadTvShowsCallback callback) {
            super(handler);
            this.context = context;
            this.callback = callback;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            new LoadTvShowAsync(context, callback).execute();
        }
    }
}
