package com.fajaradisetyawan.favorite.activity.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.fajaradisetyawan.favorite.activity.detail.DetailTvShowActivity;
import com.fajaradisetyawan.favorite.adapter.TvShowAdapter;
import com.fajaradisetyawan.favorite.model.TvShow;
import com.fajaradisetyawan.favorite.viewmodel.TvShowViewModel;

import java.util.ArrayList;


public class TvShowFragment extends Fragment {
    private TvShowAdapter tvShowAdapter;
    private ProgressBar progressBarTvshow;

    public TvShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        progressBarTvshow = view.findViewById(R.id.pb_fragmentTvShow);
        progressBarTvshow.setVisibility(View.VISIBLE);

        // Init View Model
        TvShowViewModel tvShowViewModel = ViewModelProviders.of(this).get(TvShowViewModel.class);
        tvShowViewModel.setTvShows(getContext());
        tvShowViewModel.getTvShows().observe(getViewLifecycleOwner(), observerTvShows);

        // Init RecyclerView Adapter
        tvShowAdapter = new TvShowAdapter(getContext());

        RecyclerView rvTvShow = view.findViewById(R.id.rv_tvshow);
        rvTvShow.setLayoutManager(new LinearLayoutManager(getContext()));
        rvTvShow.setAdapter(tvShowAdapter);
    }

    private Observer<ArrayList<TvShow>> observerTvShows = new Observer<ArrayList<TvShow>>() {
        @Override
        public void onChanged(@Nullable final ArrayList<TvShow> tvShows) {
            if (tvShows != null) {
                progressBarTvshow.setVisibility(View.GONE);

                tvShowAdapter.setTvShows(tvShows);
                tvShowAdapter.setOnItemClickListener(new TvShowAdapter.OnItemTvShowClick() {
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
