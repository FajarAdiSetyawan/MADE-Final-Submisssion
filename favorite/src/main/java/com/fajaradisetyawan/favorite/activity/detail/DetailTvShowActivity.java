package com.fajaradisetyawan.favorite.activity.detail;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fajaradisetyawan.favorite.R;
import com.fajaradisetyawan.favorite.model.TvShow;
import com.fajaradisetyawan.favorite.viewmodel.TvShowDetailViewModel;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class DetailTvShowActivity extends AppCompatActivity {
    private TextView tvGenreTvShow, tvRelaseTvShow, tvSinopsisTvShow, tvPopluar, tvTitle;
    private RatingBar ratingBarTvShow;
    private ImageView backDropTvShow, posterTvShow;
    public static String TVSHOW_DETAIL = "tvshow_detail";
    private TvShow tvShow;
    private ScrollView scrollView;
    float rateDetailTvShow;
    private static final String API_KEY = "42f8c3e2a9a24c1da94af7502c14d314";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv_show);

        ratingBarTvShow = findViewById(R.id.ratingBarTvShow);
        tvGenreTvShow = findViewById(R.id.tv_genre_tvshow);
        tvRelaseTvShow = findViewById(R.id.tv_release_tvshow);
        tvPopluar = findViewById(R.id.tv_popular_tvshow);
        tvSinopsisTvShow = findViewById(R.id.tv_synopsis_tvshow);
        backDropTvShow = findViewById(R.id.img_backdrop_tvshow);
        posterTvShow = findViewById(R.id.img_poster_tvshow);
        scrollView = findViewById(R.id.scrollViewTvShow);
        tvTitle = findViewById(R.id.txt_titleTvShow);

        tvShow = getIntent().getParcelableExtra(TVSHOW_DETAIL);

        TvShowDetailViewModel viewModel = ViewModelProviders.of(this).get(TvShowDetailViewModel.class);

        viewModel.setTvShow(tvShow);
        viewModel.getTvShow().observe(this, observerTvshow);

    }

    private Observer<TvShow> observerTvshow = new Observer<TvShow>() {
        @Override
        public void onChanged(@Nullable TvShow tvShow) {
            getGenreTvShow(tvShow.getTvShowID());

            tvTitle.setText(tvShow.getTitleTvShow());
            tvRelaseTvShow.setText(tvShow.getRelaseDateTvShow());
            tvSinopsisTvShow.setText(tvShow.getOverviewTvShow());
            tvPopluar.setText(tvShow.getPopularTvShow());
            rateDetailTvShow = (float) tvShow.getRateingTvShow();
            ratingBarTvShow.setRating(rateDetailTvShow/2);

            Glide.with(DetailTvShowActivity.this)
                    .load("https://image.tmdb.org/t/p/w500/"+ tvShow.getBackdropTvShow())
                    .centerCrop()
                    .placeholder(R.drawable.loading)
                    .into(backDropTvShow);

            Glide.with(DetailTvShowActivity.this)
                    .load("https://image.tmdb.org/t/p/w342/" + tvShow.getPosterTvShow())
                    .centerCrop()
                    .placeholder(R.drawable.loading)
                    .into(posterTvShow);
        }
    };


    private void getGenreTvShow(int idTvShow) {
        String url = "https://api.themoviedb.org/3/movie/" + idTvShow + "?api_key=" + API_KEY + "&language=en-US";

        AsyncHttpClient mClient = new AsyncHttpClient();
        mClient.get(url, new AsyncHttpResponseHandler() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                scrollView.setVisibility(View.VISIBLE);
                try {
                    String responeAPI = new String(responseBody);
                    JSONObject objectAPI = new JSONObject(responeAPI);

                    JSONArray jsonArray = objectAPI.getJSONArray("genres");
                    List<String> genreList = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String genreName = jsonObject.getString("name");
                        genreList.add(genreName);
                    }
                    String genres = TextUtils.join(", ", genreList);
                    tvGenreTvShow.setText(genres);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("onFailure", e.getMessage());
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }
}
