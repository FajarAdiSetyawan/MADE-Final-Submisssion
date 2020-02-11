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
import com.fajaradisetyawan.madesubmisssion5.activity.detail.DetailTvShowActivity;
import com.fajaradisetyawan.madesubmisssion5.adapter.tvshow.TvShowFavAdapter;
import com.fajaradisetyawan.madesubmisssion5.model.TvShow;
import com.fajaradisetyawan.madesubmisssion5.viewmodel.favorite.FavoriteTvShowViewModel;

import java.util.ArrayList;



public class TvShowFavFragment extends Fragment {
    private TvShowFavAdapter tvShowFavAdapter;
    private ProgressBar progressBarTvshow;

    public TvShowFavFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show_fav, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        progressBarTvshow = view.findViewById(R.id.pb_fragmentTvShowFav);
        progressBarTvshow.setVisibility(View.VISIBLE);

        // Init View Model
        FavoriteTvShowViewModel favoriteTvShowViewModel = ViewModelProviders.of(this).get(FavoriteTvShowViewModel.class);
        favoriteTvShowViewModel.setTvShows(getContext());
        favoriteTvShowViewModel.getTvShows().observe(getViewLifecycleOwner(), observerTvShows);

        // Init RecyclerView Adapter
        tvShowFavAdapter = new TvShowFavAdapter(getContext());

        RecyclerView rvTvShow = view.findViewById(R.id.rv_tvshow_favorite);
        rvTvShow.setLayoutManager(new LinearLayoutManager(getContext()));
        rvTvShow.setAdapter(tvShowFavAdapter);
    }

    private Observer<ArrayList<TvShow>> observerTvShows = new Observer<ArrayList<TvShow>>() {
        @Override
        public void onChanged(@Nullable final ArrayList<TvShow> tvShows) {
            if (tvShows != null) {
                progressBarTvshow.setVisibility(View.GONE);

                tvShowFavAdapter.setFavTvShows(tvShows);
                tvShowFavAdapter.setOnItemClickListener(new TvShowFavAdapter.OnItemFavTvShowClick() {
                    @Override
                    public void onItemClicked(int position) {
                        Intent intent = new Intent(getActivity(), DetailTvShowActivity.class);
                        intent.putExtra(DetailTvShowActivity.TVSHOW_DETAIL, tvShows.get(position));
                        startActivity(intent);
                    }
                });
            }
        }
    };
}
