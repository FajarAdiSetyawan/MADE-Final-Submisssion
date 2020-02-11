package com.fajaradisetyawan.madesubmisssion5.viewmodel.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fajaradisetyawan.madesubmisssion5.model.Movie;

public class DetailViewModelMovie extends ViewModel {
    private MutableLiveData<Movie> movie = new MutableLiveData<>();

    public void setMovie(Movie movies) {
        movie.setValue(movies);
    }

    public LiveData<Movie> getMovie() {
        return movie;
    }
}
