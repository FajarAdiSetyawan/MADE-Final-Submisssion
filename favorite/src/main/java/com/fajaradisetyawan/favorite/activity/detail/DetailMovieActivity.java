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

import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fajaradisetyawan.favorite.R;
import com.fajaradisetyawan.favorite.model.Movie;
import com.fajaradisetyawan.favorite.viewmodel.MovieDetailViewModel;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class DetailMovieActivity extends AppCompatActivity {
    private TextView tvGenreMovie;
    private TextView tvRelase;
    private TextView tvSinopsis;
    private TextView tvTitile;
    private TextView tvPopluar;
    private RatingBar ratingBarMovie;
    private ImageView backDropMovie, posterMovie;
    float rateDetailMovie;
    private ScrollView scrollView;
    private Movie movie;
    public static String MOVIE_DETAIL = "movie_detail";
    private static final String API_KEY = "42f8c3e2a9a24c1da94af7502c14d314";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        ratingBarMovie = findViewById(R.id.ratingBarMovie);

        tvGenreMovie = findViewById(R.id.tv_genre_movie);
        tvTitile = findViewById(R.id.txt_titleMovie);
        tvRelase = findViewById(R.id.tv_release_movie);
        tvSinopsis = findViewById(R.id.tv_synopsis_movie);
        tvPopluar = findViewById(R.id.tv_popular_movie);

        posterMovie = findViewById(R.id.img_poster_movie);
        backDropMovie = findViewById(R.id.img_backdrop_movie);
        scrollView = findViewById(R.id.scrollViewMovie);

        movie = getIntent().getParcelableExtra(MOVIE_DETAIL);

        MovieDetailViewModel viewModel = ViewModelProviders.of(this).get(MovieDetailViewModel.class);

        viewModel.setMovie(movie);
        viewModel.getMovie().observe(this, observerMovie);
    }

    private Observer<Movie> observerMovie = new Observer<Movie>() {
        @Override
        public void onChanged(@Nullable Movie movie) {
            getGenreMovie(movie.getMovieID());

            tvTitile.setText(movie.getTitleMovie());
            tvRelase.setText(movie.getDatereleaseMovie());
            tvSinopsis.setText(movie.getOverviewMovie());
            tvPopluar.setText(movie.getPopularityMovie());
            rateDetailMovie = (float) movie.getRatingstar();
            ratingBarMovie.setRating(rateDetailMovie/2);

            Glide.with(DetailMovieActivity.this)
                    .load("https://image.tmdb.org/t/p/w500/"+ movie.getBackdropMovie())
                    .centerCrop()
                    .placeholder(R.drawable.loading)
                    .into(backDropMovie);

            Glide.with(DetailMovieActivity.this)
                    .load("https://image.tmdb.org/t/p/w342/" + movie.getPosterMovie())
                    .centerCrop()
                    .placeholder(R.drawable.loading)
                    .into(posterMovie);
        }
    };


    private void getGenreMovie(int idMovie) {
        String url = "https://api.themoviedb.org/3/movie/" + idMovie + "?api_key=" + API_KEY + "&language=en-US";

        AsyncHttpClient mClient = new AsyncHttpClient();
        mClient.get(url, new AsyncHttpResponseHandler() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
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
                    tvGenreMovie.setText(genres);
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
