package com.fajaradisetyawan.madesubmisssion5.activity.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.fajaradisetyawan.madesubmisssion5.R;
import com.fajaradisetyawan.madesubmisssion5.activity.detail.DetailMovieActivity;
import com.fajaradisetyawan.madesubmisssion5.adapter.movie.MovieAdapter;
import com.fajaradisetyawan.madesubmisssion5.model.Movie;
import com.fajaradisetyawan.madesubmisssion5.viewmodel.main.MovieViewModel;

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

        movieViewModel = ViewModelProviders.of(getActivity()).get(MovieViewModel.class);

        if (!movieViewModel.query) {
            movieViewModel.setMovies();
        }
        movieViewModel.getMovies().observe(getViewLifecycleOwner(), getMovieObserve);

        movieAdapter = new MovieAdapter(getContext());

        RecyclerView rvMovie = view.findViewById(R.id.rv_movie);
        rvMovie.setLayoutManager(new LinearLayoutManager(getContext()));
        rvMovie.setAdapter(movieAdapter);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_search, menu);

        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setQueryHint(Html.fromHtml("<font color = #ffffff>" + getResources().getString(R.string.search)));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                progressBarMovie.setVisibility(View.VISIBLE);
                movieViewModel.setMoviesSearch(query);
                movieViewModel.query = true;
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private Observer<ArrayList<Movie>> getMovieObserve = new Observer<ArrayList<Movie>>() {
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

    @Override
    public void onResume() {
        super.onResume();
    }
}
