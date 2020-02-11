package com.fajaradisetyawan.madesubmisssion5.database;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    public static String TABLE_MOVIE = "movie_table";
    public static String TABLE_TVSHOW = "tvshow_table";
    public static final String AUTHORITY = "com.fajaradisetyawan.madesubmisssion5";
    private static final String SCHEME = "content";

    public static final class MovieColumns implements BaseColumns {
        public static final String ID_MOVIE = "id_movie";
        public static final String MOVIE_TITLE = "movie_title";
        public static final String MOVIE_OVERVIEW = "movie_overview";
        public static final String MOVIE_RELEASE_DATE = "movie_release_date";
        public static final String MOVIE_POSTER_PATH = "movie_photo";
        public static final String MOVIE_BACKDROP_PATH = "movie_backdrop";
        public static final String MOVIE_POPULARITY  = "movie_popularity";
        public static final String MOVIE_GENRE = "movie_genre";
        public static final String MOVIE_RATING_STAR = "movie_rating";

        public static final Uri MOVIE_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_MOVIE)
                .build();
    }

    public static final class TVShowColumns implements BaseColumns {
        public static final String ID_TV = "id_tvshow";
        public static final String TVSHOW_TITLE = "tvshow_title";
        public static final String TVSHOW_OVERVIEW = "tvshow_overview";
        public static final String TVSHOW_RELEASE_DATE = "tvshow_release_date";
        public static final String TVSHOW_POSTER_PATH = "tvshow_photo";
        public static final String TVSHOW_BACKDROP_PATH = "tvshow_backdrop";
        public static final String TVSHOW_POPULARITY = "tvshow_popularity";
        public static final String TVSHOW_GENRE = "tvshow_genre";
        public static final String TVSHOW_RATING_STAR = "tvshow_rating";

        public static final Uri TVSHOW_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_TVSHOW)
                .build();
    }

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }

    public static double getColumnDouble(Cursor cursor, String columnName) {
        return cursor.getDouble(cursor.getColumnIndexOrThrow(columnName));
    }
}
