package com.fajaradisetyawan.madesubmisssion5.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.fajaradisetyawan.madesubmisssion5.database.DatabaseContract;

import org.json.JSONObject;

import static com.fajaradisetyawan.madesubmisssion5.database.DatabaseContract.getColumnDouble;
import static com.fajaradisetyawan.madesubmisssion5.database.DatabaseContract.getColumnInt;
import static com.fajaradisetyawan.madesubmisssion5.database.DatabaseContract.getColumnString;

public class Movie implements Parcelable {
    private int movieID;
    private String titleMovie, overviewMovie, datereleaseMovie, genreMovie, posterMovie, backdropMovie, popularityMovie;
    double ratingstar;

    public Movie(JSONObject object) {
        try {
            this.movieID = object.getInt("id");
            this.titleMovie = object.getString("title");
            this.overviewMovie = object.getString("overview");
            this.datereleaseMovie = object.getString("release_date");
            this.ratingstar = object.getDouble("vote_average");
            this.posterMovie = object.getString("poster_path" );
            this.backdropMovie = object.getString("backdrop_path");
            this.popularityMovie = object.getString("popularity");
        } catch (Exception e){
            e.printStackTrace();
            Log.d("Error Data", e.getMessage());
        }
    }

    public Movie() {

    }

    public Movie(Cursor cursor) {
        this.movieID = getColumnInt(cursor, DatabaseContract.MovieColumns.ID_MOVIE);
        this.titleMovie = getColumnString(cursor, DatabaseContract.MovieColumns.MOVIE_TITLE);
        this.overviewMovie = getColumnString(cursor, DatabaseContract.MovieColumns.MOVIE_OVERVIEW);
        this.datereleaseMovie = getColumnString(cursor, DatabaseContract.MovieColumns.MOVIE_RELEASE_DATE);
        this.genreMovie = getColumnString(cursor, DatabaseContract.MovieColumns.MOVIE_GENRE);
        this.ratingstar = getColumnDouble(cursor, DatabaseContract.MovieColumns.MOVIE_RATING_STAR);
        this.popularityMovie = getColumnString(cursor, DatabaseContract.MovieColumns.MOVIE_POPULARITY);
        this.posterMovie = getColumnString(cursor, DatabaseContract.MovieColumns.MOVIE_POSTER_PATH);
        this.backdropMovie = getColumnString(cursor, DatabaseContract.MovieColumns.MOVIE_BACKDROP_PATH);
    }

    protected Movie(Parcel in) {
        movieID = in.readInt();
        titleMovie = in.readString();
        overviewMovie = in.readString();
        datereleaseMovie = in.readString();
        genreMovie = in.readString();
        posterMovie = in.readString();
        backdropMovie = in.readString();
        popularityMovie = in.readString();
        ratingstar = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(movieID);
        dest.writeString(titleMovie);
        dest.writeString(overviewMovie);
        dest.writeString(datereleaseMovie);
        dest.writeString(genreMovie);
        dest.writeString(posterMovie);
        dest.writeString(backdropMovie);
        dest.writeString(popularityMovie);
        dest.writeDouble(ratingstar);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };


    public int getMovieID() {
        return movieID;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    public String getTitleMovie() {
        return titleMovie;
    }

    public void setTitleMovie(String titleMovie) {
        this.titleMovie = titleMovie;
    }

    public String getOverviewMovie() {
        return overviewMovie;
    }

    public void setOverviewMovie(String overviewMovie) {
        this.overviewMovie = overviewMovie;
    }

    public String getDatereleaseMovie() {
        return datereleaseMovie;
    }

    public void setDatereleaseMovie(String datereleaseMovie) {
        this.datereleaseMovie = datereleaseMovie;
    }

    public String getGenreMovie() {
        return genreMovie;
    }

    public void setGenreMovie(String genreMovie) {
        this.genreMovie = genreMovie;
    }

    public String getPosterMovie() {
        return posterMovie;
    }

    public void setPosterMovie(String posterMovie) {
        this.posterMovie = posterMovie;
    }

    public String getBackdropMovie() {
        return backdropMovie;
    }

    public void setBackdropMovie(String backdropMovie) {
        this.backdropMovie = backdropMovie;
    }

    public String getPopularityMovie() {
        return popularityMovie;
    }

    public void setPopularityMovie(String popularityMovie) {
        this.popularityMovie = popularityMovie;
    }

    public double getRatingstar() {
        return ratingstar;
    }

    public void setRatingstar(double ratingstar) {
        this.ratingstar = ratingstar;
    }


}
