package com.fajaradisetyawan.favorite.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fajaradisetyawan.favorite.model.TvShow;

public class TvShowDetailViewModel extends ViewModel {

    private MutableLiveData<TvShow> tvShows = new MutableLiveData<>();


    public void setTvShow(TvShow tvShow) {
        tvShows.setValue(tvShow);
    }


    public LiveData<TvShow> getTvShow() {
        return tvShows;
    }
}
