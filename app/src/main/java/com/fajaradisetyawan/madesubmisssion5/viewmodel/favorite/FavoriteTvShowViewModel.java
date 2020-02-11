package com.fajaradisetyawan.madesubmisssion5.viewmodel.favorite;

import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.HandlerThread;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fajaradisetyawan.madesubmisssion5.asynctask.LoadTvShowAsync;
import com.fajaradisetyawan.madesubmisssion5.asynctask.LoadTvShowsCallback;
import com.fajaradisetyawan.madesubmisssion5.model.TvShow;

import java.util.ArrayList;

import static com.fajaradisetyawan.madesubmisssion5.database.DatabaseContract.TVShowColumns.TVSHOW_URI;

public class FavoriteTvShowViewModel extends ViewModel implements LoadTvShowsCallback {
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
    public void postExecute(ArrayList<TvShow> tvShows) {
        if (tvShows != null) {
            showList.setValue(tvShows);
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
