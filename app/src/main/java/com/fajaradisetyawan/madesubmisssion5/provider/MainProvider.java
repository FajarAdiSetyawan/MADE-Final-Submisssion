package com.fajaradisetyawan.madesubmisssion5.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.fajaradisetyawan.madesubmisssion5.database.MainHelper;

import static com.fajaradisetyawan.madesubmisssion5.database.DatabaseContract.AUTHORITY;
import static com.fajaradisetyawan.madesubmisssion5.database.DatabaseContract.MovieColumns.MOVIE_URI;
import static com.fajaradisetyawan.madesubmisssion5.database.DatabaseContract.TABLE_MOVIE;
import static com.fajaradisetyawan.madesubmisssion5.database.DatabaseContract.TABLE_TVSHOW;
import static com.fajaradisetyawan.madesubmisssion5.database.DatabaseContract.TVShowColumns.TVSHOW_URI;

public class MainProvider extends ContentProvider {
    private static final int MOVIE = 1;
    private static final int MOVIE_ID = 2;
    private static final int TVSHOW = 11;
    private static final int TVSHOW_ID = 12;

    private MainHelper mainHelper;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(AUTHORITY, TABLE_MOVIE, MOVIE);
        sUriMatcher.addURI(AUTHORITY, TABLE_MOVIE + "/#", MOVIE_ID);
        sUriMatcher.addURI(AUTHORITY, TABLE_TVSHOW, TVSHOW);
        sUriMatcher.addURI(AUTHORITY, TABLE_TVSHOW + "/#", TVSHOW_ID);
    }

    public MainProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int deleted;

        switch (sUriMatcher.match(uri)) {
            case MOVIE_ID:
                getContext().getContentResolver().notifyChange(MOVIE_URI, null);
                return mainHelper.deleteMovie(Integer.valueOf(uri.getLastPathSegment()));
            case TVSHOW_ID:
                getContext().getContentResolver().notifyChange(TVSHOW_URI, null);
                return mainHelper.deleteTvShow(Integer.valueOf(uri.getLastPathSegment()));
            default:
                return 0;
        }
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long added;
        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                added = mainHelper.insertMovie(values);
                return Uri.parse(MOVIE_URI + "/" + added);
            case TVSHOW:
                added = mainHelper.insertTvShow(values);
                return Uri.parse(TVSHOW_URI + "/" + added);
            default:
                return null;
        }
    }

    @Override
    public boolean onCreate() {
        mainHelper = MainHelper.getInstance(getContext());
        mainHelper.open();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                cursor = mainHelper.getAllMovies();
                break;
            case MOVIE_ID:
                cursor = mainHelper.getMovie(Integer.valueOf(uri.getLastPathSegment()));
                break;
            case TVSHOW:
                cursor = mainHelper.getAllTvShows();
                break;
            case TVSHOW_ID:
                cursor = mainHelper.getTVShow(Integer.valueOf(uri.getLastPathSegment()));
                break;
            default:
                cursor = null;
                break;
        }

        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
