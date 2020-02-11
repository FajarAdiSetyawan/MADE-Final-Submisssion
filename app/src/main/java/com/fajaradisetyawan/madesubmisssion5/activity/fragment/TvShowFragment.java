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
import com.fajaradisetyawan.madesubmisssion5.activity.detail.DetailTvShowActivity;
import com.fajaradisetyawan.madesubmisssion5.adapter.tvshow.TvShowAdapter;
import com.fajaradisetyawan.madesubmisssion5.model.TvShow;
import com.fajaradisetyawan.madesubmisssion5.viewmodel.main.TvShowViewModel;

import java.util.ArrayList;



public class TvShowFragment extends Fragment {

    private TvShowAdapter tvShowAdapter;
    private ProgressBar progressBarTvshow;
    private TvShowViewModel tvShowViewModel;

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

        tvShowViewModel = ViewModelProviders.of(getActivity()).get(TvShowViewModel.class);

        if (!tvShowViewModel.query) {
            tvShowViewModel.setTvShows();
        }
        tvShowViewModel.getTvShow().observe(getViewLifecycleOwner(), getTvShowObserve);

        tvShowAdapter = new TvShowAdapter(getContext());
        tvShowAdapter.notifyDataSetChanged();

        RecyclerView rvTvShow = view.findViewById(R.id.rv_tvshow);
        rvTvShow.setLayoutManager(new LinearLayoutManager(getContext()));
        rvTvShow.setAdapter(tvShowAdapter);

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
                progressBarTvshow.setVisibility(View.VISIBLE);
                tvShowViewModel.getTvShowSeacrh(query);
                tvShowViewModel.query = true;
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }


    private Observer<ArrayList<TvShow>> getTvShowObserve = new Observer<ArrayList<TvShow>>() {
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
