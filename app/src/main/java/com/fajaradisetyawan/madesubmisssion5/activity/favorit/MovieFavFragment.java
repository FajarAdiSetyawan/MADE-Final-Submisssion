package com.fajaradisetyawan.madesubmisssion5.activity.favorit;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.fajaradisetyawan.madesubmisssion5.R;
import com.fajaradisetyawan.madesubmisssion5.activity.detail.DetailMovieActivity;
import com.fajaradisetyawan.madesubmisssion5.adapter.movie.MovieFavAdapter;
import com.fajaradisetyawan.madesubmisssion5.model.Movie;
import com.fajaradisetyawan.madesubmisssion5.viewmodel.favorite.FavoriteMovieViewModel;

import java.util.ArrayList;


public class MovieFavFragment extends Fragment {

    private MovieFavAdapter movieFavAdapter;
    private ProgressBar progressBar;

    public MovieFavFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_fav, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.pb_fragmentMovieFav);
        progressBar.setVisibility(View.VISIBLE);

        // Init View Model
        FavoriteMovieViewModel favoriteMovieViewModel = ViewModelProviders.of(this).get(FavoriteMovieViewModel.class);
        favoriteMovieViewModel.setMovies(getContext());
        favoriteMovieViewModel.getMovies().observe(getViewLifecycleOwner(), observerMovies);

        // Init RecyclerView Adapter
        movieFavAdapter = new MovieFavAdapter(getContext());

        RecyclerView rvMovie = view.findViewById(R.id.rv_movie_favorite);
        rvMovie.setLayoutManager(new LinearLayoutManager(getContext()));
        rvMovie.setAdapter(movieFavAdapter);
    }

    private Observer<ArrayList<Movie>> observerMovies = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(@Nullable final ArrayList<Movie> movies) {
            if (movies != null) {
                progressBar.setVisibility(View.GONE);

                movieFavAdapter.setMovieFavAdapter(movies);
                movieFavAdapter.setOnItemClickListener(new MovieFavAdapter.OnItemFavMovieClick() {
                    @Override
                    public void onItemClicked(int position) {
                        Intent intent = new Intent(getActivity(), DetailMovieActivity.class);
                        intent.putExtra(DetailMovieActivity.MOVIE_DETAIL, movies.get(position));
                        startActivity(intent);
                    }
                });
            }
        }
    };
}
