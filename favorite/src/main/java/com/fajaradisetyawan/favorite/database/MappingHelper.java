package com.fajaradisetyawan.favorite.database;

import android.database.Cursor;

import com.fajaradisetyawan.favorite.model.Movie;
import com.fajaradisetyawan.favorite.model.TvShow;

import java.util.ArrayList;

import static com.fajaradisetyawan.favorite.database.DatabaseContract.MovieColumns.ID_MOVIE;
import static com.fajaradisetyawan.favorite.database.DatabaseContract.MovieColumns.MOVIE_BACKDROP_PATH;
import static com.fajaradisetyawan.favorite.database.DatabaseContract.MovieColumns.MOVIE_GENRE;
import static com.fajaradisetyawan.favorite.database.DatabaseContract.MovieColumns.MOVIE_OVERVIEW;
import static com.fajaradisetyawan.favorite.database.DatabaseContract.MovieColumns.MOVIE_POPULARITY;
import static com.fajaradisetyawan.favorite.database.DatabaseContract.MovieColumns.MOVIE_POSTER_PATH;
import static com.fajaradisetyawan.favorite.database.DatabaseContract.MovieColumns.MOVIE_RATING_STAR;
import static com.fajaradisetyawan.favorite.database.DatabaseContract.MovieColumns.MOVIE_RELEASE_DATE;
import static com.fajaradisetyawan.favorite.database.DatabaseContract.MovieColumns.MOVIE_TITLE;
import static com.fajaradisetyawan.favorite.database.DatabaseContract.TVShowColumns.ID_TV;
import static com.fajaradisetyawan.favorite.database.DatabaseContract.TVShowColumns.TVSHOW_BACKDROP_PATH;
import static com.fajaradisetyawan.favorite.database.DatabaseContract.TVShowColumns.TVSHOW_GENRE;
import static com.fajaradisetyawan.favorite.database.DatabaseContract.TVShowColumns.TVSHOW_OVERVIEW;
import static com.fajaradisetyawan.favorite.database.DatabaseContract.TVShowColumns.TVSHOW_POPULARITY;
import static com.fajaradisetyawan.favorite.database.DatabaseContract.TVShowColumns.TVSHOW_POSTER_PATH;
import static com.fajaradisetyawan.favorite.database.DatabaseContract.TVShowColumns.TVSHOW_RATING_STAR;
import static com.fajaradisetyawan.favorite.database.DatabaseContract.TVShowColumns.TVSHOW_RELEASE_DATE;
import static com.fajaradisetyawan.favorite.database.DatabaseContract.TVShowColumns.TVSHOW_TITLE;

public class MappingHelper {
    public static ArrayList<Movie> mapCursorMovies(Cursor cursor) {
        ArrayList<Movie> movies = new ArrayList<>();

        while (cursor.moveToNext()) {
            Movie movie = new Movie();
            movie.setMovieID(cursor.getInt(cursor.getColumnIndexOrThrow(ID_MOVIE)));
            movie.setTitleMovie(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_TITLE)));
            movie.setOverviewMovie(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_OVERVIEW)));
            movie.setDatereleaseMovie(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_RELEASE_DATE)));
            movie.setGenreMovie(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_GENRE)));
            movie.setPopularityMovie(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_POPULARITY)));
            movie.setPosterMovie(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_POSTER_PATH)));
            movie.setBackdropMovie(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_BACKDROP_PATH)));
            movie.setRatingstar(cursor.getDouble(cursor.getColumnIndexOrThrow(MOVIE_RATING_STAR)));

            movies.add(movie);
        }

        return movies;
    }

    public static Movie mapCursorObjMovie(Cursor cursor) {
        cursor.moveToFirst();

        if (cursor.getCount() > 0) {
            Movie movie = new Movie();
            movie.setMovieID(cursor.getInt(cursor.getColumnIndexOrThrow(ID_MOVIE)));
            movie.setTitleMovie(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_TITLE)));
            movie.setOverviewMovie(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_OVERVIEW)));
            movie.setDatereleaseMovie(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_RELEASE_DATE)));
            movie.setGenreMovie(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_GENRE)));
            movie.setPopularityMovie(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_POPULARITY)));
            movie.setPosterMovie(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_POSTER_PATH)));
            movie.setBackdropMovie(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_BACKDROP_PATH)));
            movie.setRatingstar(cursor.getDouble(cursor.getColumnIndexOrThrow(MOVIE_RATING_STAR)));

            cursor.close();
            return movie;
        } else {
            cursor.close();
            return null;
        }
    }

    public static ArrayList<TvShow> mapCursorTvShows(Cursor cursor) {
        ArrayList<TvShow> shows = new ArrayList<>();

        while (cursor.moveToNext()) {
            TvShow tvShow = new TvShow();
            tvShow.setTvShowID(cursor.getInt(cursor.getColumnIndexOrThrow(ID_TV)));
            tvShow.setTitleTvShow(cursor.getString(cursor.getColumnIndexOrThrow(TVSHOW_TITLE)));
            tvShow.setOverviewTvShow(cursor.getString(cursor.getColumnIndexOrThrow(TVSHOW_OVERVIEW)));
            tvShow.setRelaseDateTvShow(cursor.getString(cursor.getColumnIndexOrThrow(TVSHOW_RELEASE_DATE)));
            tvShow.setRateingTvShow(cursor.getDouble(cursor.getColumnIndexOrThrow(TVSHOW_RATING_STAR)));
            tvShow.setGenreTvShow(cursor.getString(cursor.getColumnIndexOrThrow(TVSHOW_GENRE)));
            tvShow.setPopularTvShow(cursor.getString(cursor.getColumnIndexOrThrow(TVSHOW_POPULARITY)));
            tvShow.setPosterTvShow(cursor.getString(cursor.getColumnIndexOrThrow(TVSHOW_POSTER_PATH)));
            tvShow.setBackdropTvShow(cursor.getString(cursor.getColumnIndexOrThrow(TVSHOW_BACKDROP_PATH)));

            shows.add(tvShow);
        }

        return shows;
    }

    public static TvShow mapCursorObjTvShow(Cursor cursor) {
        cursor.moveToFirst();

        if (cursor.getCount() > 0) {
            TvShow tvShow = new TvShow();
            tvShow.setTvShowID(cursor.getInt(cursor.getColumnIndexOrThrow(ID_TV)));
            tvShow.setTitleTvShow(cursor.getString(cursor.getColumnIndexOrThrow(TVSHOW_TITLE)));
            tvShow.setOverviewTvShow(cursor.getString(cursor.getColumnIndexOrThrow(TVSHOW_OVERVIEW)));
            tvShow.setRelaseDateTvShow(cursor.getString(cursor.getColumnIndexOrThrow(TVSHOW_RELEASE_DATE)));
            tvShow.setRateingTvShow(cursor.getDouble(cursor.getColumnIndexOrThrow(TVSHOW_RATING_STAR)));
            tvShow.setGenreTvShow(cursor.getString(cursor.getColumnIndexOrThrow(TVSHOW_GENRE)));
            tvShow.setPopularTvShow(cursor.getString(cursor.getColumnIndexOrThrow(TVSHOW_POPULARITY)));
            tvShow.setPosterTvShow(cursor.getString(cursor.getColumnIndexOrThrow(TVSHOW_POSTER_PATH)));
            tvShow.setBackdropTvShow(cursor.getString(cursor.getColumnIndexOrThrow(TVSHOW_BACKDROP_PATH)));

            cursor.close();
            return tvShow;
        } else {
            cursor.close();
            return null;
        }
    }
}
