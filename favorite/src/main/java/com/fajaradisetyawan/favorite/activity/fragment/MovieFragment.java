package com.fajaradisetyawan.favorite.activity.fragment;

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

import com.fajaradisetyawan.favorite.R;
import com.fajaradisetyawan.favorite.activity.detail.DetailMovieActivity;
import com.fajaradisetyawan.favorite.adapter.MovieAdapter;
import com.fajaradisetyawan.favorite.model.Movie;
import com.fajaradisetyawan.favorite.viewmodel.MovieViewModel;

import java.util.ArrayList;


public class MovieFragment extends Fragment {
    private MovieAdapter movieAdapter;
    private ProgressBar progressBarMovie;
    private MovieViewModel movieViewModel;

    public MovieFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        progressBarMovie = view.findViewById(R.id.pb_fragmentMovie);
        progressBarMovie.setVisibility(View.VISIBLE);

        // Init View Model
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.setMovies(getContext());
        movieViewModel.getMovies().observe(getViewLifecycleOwner(), observerMovies);

        // Init RecyclerView Adapter
        movieAdapter = new MovieAdapter(getContext());

        RecyclerView rvMovie = view.findViewById(R.id.rv_movie);
        rvMovie.setLayoutManager(new LinearLayoutManager(getContext()));
        rvMovie.setAdapter(movieAdapter);
    }

    private Observer<ArrayList<Movie>> observerMovies = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(@Nullable final ArrayList<Movie> movies) {
            if (movies != null) {
                progressBarMovie.setVisibility(View.GONE);

                movieAdapter.setMovies(movies);
                movieAdapter.setOnItemClickListener(new MovieAdapter.OnItemMovieClick() {
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
