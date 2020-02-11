package com.fajaradisetyawan.madesubmisssion5.asynctask;

import com.fajaradisetyawan.madesubmisssion5.model.Movie;

import java.util.ArrayList;

public interface LoadMoviesCallback {
    void postExecute(ArrayList<Movie> movies);
}
