package com.fajaradisetyawan.madesubmisssion5.asynctask;

import com.fajaradisetyawan.madesubmisssion5.model.TvShow;

import java.util.ArrayList;

public interface LoadTvShowsCallback {
    void postExecute(ArrayList<TvShow> tvShows);
}
