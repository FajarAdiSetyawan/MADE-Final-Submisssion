package com.fajaradisetyawan.madesubmisssion5.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.fajaradisetyawan.madesubmisssion5.R;
import com.fajaradisetyawan.madesubmisssion5.model.Movie;

import java.util.concurrent.ExecutionException;

import static com.fajaradisetyawan.madesubmisssion5.database.DatabaseContract.MovieColumns.MOVIE_URI;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory{
    private Context context;
    private Cursor cursor;
    private int appWidgetId;


    public StackRemoteViewsFactory(Context contexts, Intent intent) {
        context = contexts;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

        if (cursor != null) {
            cursor.close();
        }

        final long identitiyToken = Binder.clearCallingIdentity();

        cursor = context.getContentResolver().query(MOVIE_URI, null, null, null, null);

        Binder.restoreCallingIdentity(identitiyToken);

    }

    @Override
    public void onDestroy() {
        if (cursor != null) cursor.close();
    }

    @Override
    public int getCount() {
        if (cursor == null) return 0;
        else return cursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        if (position == AdapterView.INVALID_POSITION ||
                cursor == null || !cursor.moveToPosition(position))
        {
            return null;
        }

        Movie movies = getItem(position);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.item_widget);

        Bitmap bmp = null;
        String poster_url = "https://image.tmdb.org/t/p/w342/" + movies.getPosterMovie();
        String movie_title = movies.getTitleMovie();
        String date = movies.getDatereleaseMovie();
        try {

            bmp = Glide.with(context)
                    .asBitmap()
                    .load(poster_url)
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();

        } catch (InterruptedException | ExecutionException e) {
            Log.d("Widget Load Error", "error");
        }

        remoteViews.setImageViewBitmap(R.id.img_widget, bmp);

        Bundle extras = new Bundle();
        extras.putString(StackWidget.EXTRA_ITEM, movie_title + "\n" + date);

        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        remoteViews.setOnClickFillInIntent(R.id.imageView, fillInIntent);

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        if (cursor.moveToPosition(position)) {
            return cursor.getLong(0);
        } else return position;
    }

    private Movie getItem(int position) {
        if (!cursor.moveToPosition(position)) {
            throw new IllegalStateException("Position Invalid");
        }
        return new Movie(cursor);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
