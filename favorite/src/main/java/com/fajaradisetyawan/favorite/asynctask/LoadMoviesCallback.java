package com.fajaradisetyawan.favorite.asynctask;

import com.fajaradisetyawan.favorite.model.Movie;

import java.util.ArrayList;

public interface LoadMoviesCallback {
    void postExecute(ArrayList<Movie> movies);
}
