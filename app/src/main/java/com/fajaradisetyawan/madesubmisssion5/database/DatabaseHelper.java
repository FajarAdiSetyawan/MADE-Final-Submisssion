package com.fajaradisetyawan.madesubmisssion5.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "submission5";

    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_MOVIE = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY," +
                    " %s TEXT NULL," +
                    " %s TEXT NULL," +
                    " %s TEXT NULL," +
                    " %s TEXT NULL," +
                    " %s TEXT NULL," +
                    " %s TEXT NULL," +
                    " %s TEXT NULL," +
                    " %s REAL NULL)",


            DatabaseContract.TABLE_MOVIE,
            DatabaseContract.MovieColumns.ID_MOVIE,
            DatabaseContract.MovieColumns.MOVIE_TITLE,
            DatabaseContract.MovieColumns.MOVIE_OVERVIEW,
            DatabaseContract.MovieColumns.MOVIE_RELEASE_DATE,
            DatabaseContract.MovieColumns.MOVIE_POSTER_PATH,
            DatabaseContract.MovieColumns.MOVIE_BACKDROP_PATH,
            DatabaseContract.MovieColumns.MOVIE_POPULARITY,
            DatabaseContract.MovieColumns.MOVIE_GENRE,
            DatabaseContract.MovieColumns.MOVIE_RATING_STAR
    );

    private static final String SQL_CREATE_TABLE_TVSHOW = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY," +
                    " %s TEXT NULL," +
                    " %s TEXT NULL," +
                    " %s TEXT NULL," +
                    " %s TEXT NULL," +
                    " %s TEXT NULL," +
                    " %s TEXT NULL," +
                    " %s TEXT NULL," +
                    " %s REAL NULL)",


            DatabaseContract.TABLE_TVSHOW,
            DatabaseContract.TVShowColumns.ID_TV,
            DatabaseContract.TVShowColumns.TVSHOW_TITLE,
            DatabaseContract.TVShowColumns.TVSHOW_OVERVIEW,
            DatabaseContract.TVShowColumns.TVSHOW_RELEASE_DATE,
            DatabaseContract.TVShowColumns.TVSHOW_POSTER_PATH,
            DatabaseContract.TVShowColumns.TVSHOW_BACKDROP_PATH,
            DatabaseContract.TVShowColumns.TVSHOW_POPULARITY,
            DatabaseContract.TVShowColumns.TVSHOW_GENRE,
            DatabaseContract.TVShowColumns.TVSHOW_RATING_STAR
    );

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_MOVIE);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_TVSHOW);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_MOVIE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_TVSHOW);
        onCreate(sqLiteDatabase);
    }
}
