package com.fajaradisetyawan.favorite.asynctask;

import com.fajaradisetyawan.favorite.model.TvShow;

import java.util.ArrayList;

public interface LoadTvShowsCallback {
    void postExecute(ArrayList<TvShow> tvShows);
}
