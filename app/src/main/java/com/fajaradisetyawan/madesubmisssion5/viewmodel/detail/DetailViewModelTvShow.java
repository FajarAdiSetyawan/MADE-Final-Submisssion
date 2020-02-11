package com.fajaradisetyawan.madesubmisssion5.viewmodel.detail;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fajaradisetyawan.madesubmisssion5.model.TvShow;

public class DetailViewModelTvShow extends ViewModel {

    private MutableLiveData<TvShow> tvShows = new MutableLiveData<>();


    public void setTvShow(TvShow tvShow) {
        tvShows.setValue(tvShow);
    }


    public LiveData<TvShow> getTvShow() {
        return tvShows;
    }
}
