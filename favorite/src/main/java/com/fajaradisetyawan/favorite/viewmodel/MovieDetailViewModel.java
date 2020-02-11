package com.fajaradisetyawan.favorite.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fajaradisetyawan.favorite.model.Movie;

public class MovieDetailViewModel extends ViewModel {
    private MutableLiveData<Movie> movie = new MutableLiveData<>();

    public void setMovie(Movie movies) {
        movie.setValue(movies);
    }

    public LiveData<Movie> getMovie() {
        return movie;
    }
}
