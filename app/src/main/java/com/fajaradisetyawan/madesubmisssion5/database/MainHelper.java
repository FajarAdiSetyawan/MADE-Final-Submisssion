package com.fajaradisetyawan.madesubmisssion5.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.fajaradisetyawan.madesubmisssion5.database.DatabaseContract.TABLE_MOVIE;
import static com.fajaradisetyawan.madesubmisssion5.database.DatabaseContract.TABLE_TVSHOW;

public class MainHelper {
    private static final String DATABASE_TABLE_MOVIE = TABLE_MOVIE;
    private static final String DATABASE_TABLE_SHOW = TABLE_TVSHOW;
    private static DatabaseHelper databaseHelper;
    private static MainHelper INSTANCE;

    private static SQLiteDatabase sqLiteDatabase;

    private MainHelper(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public static MainHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MainHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        sqLiteDatabase = databaseHelper.getWritableDatabase();
    }

    public void close() {
        databaseHelper.close();

        if (sqLiteDatabase.isOpen())
            sqLiteDatabase.close();
    }

    /**
     *
     * THIS IS MOVIE MODEL HELPER
     *
     **/

    public Cursor getAllMovies() {
        return sqLiteDatabase.query(DATABASE_TABLE_MOVIE, null, null, null,
                null, null, DatabaseContract.MovieColumns.ID_MOVIE + " ASC", null);
    }

    public long insertMovie(ContentValues values) {
        return sqLiteDatabase.insert(DATABASE_TABLE_MOVIE, null, values);
    }

    public int deleteMovie(int id) {
        return sqLiteDatabase.delete(DATABASE_TABLE_MOVIE, DatabaseContract.MovieColumns.ID_MOVIE + " = '" + id + "'", null);
    }

    public Cursor getMovie(int id) {
        return sqLiteDatabase.query(DATABASE_TABLE_MOVIE, null, DatabaseContract.MovieColumns.ID_MOVIE + " = '" + id + "'", null,
                null, null, null, null);
    }

    /**
     *
     * THIS IS TV SHOW MODEL HELPER
     *
     **/

    public Cursor getAllTvShows() {

        return sqLiteDatabase.query(DATABASE_TABLE_SHOW, null, null, null,
                null, null, DatabaseContract.TVShowColumns.ID_TV + " ASC", null);
    }

    public long insertTvShow(ContentValues values) {

        return sqLiteDatabase.insert(DATABASE_TABLE_SHOW, null, values);
    }

    public int deleteTvShow(int id) {
        return sqLiteDatabase.delete(DATABASE_TABLE_SHOW, DatabaseContract.TVShowColumns.ID_TV + " = '" + id + "'", null);
    }

    public Cursor getTVShow(int id) {
        return sqLiteDatabase.query(DATABASE_TABLE_SHOW, null, DatabaseContract.TVShowColumns.ID_TV + " = '" + id + "'", null,
                null, null, null, null);
    }
}
