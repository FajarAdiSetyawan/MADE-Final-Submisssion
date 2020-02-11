package com.fajaradisetyawan.madesubmisssion5.activity.detail;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fajaradisetyawan.madesubmisssion5.R;
import com.fajaradisetyawan.madesubmisssion5.database.MainHelper;
import com.fajaradisetyawan.madesubmisssion5.database.MappingHelper;
import com.fajaradisetyawan.madesubmisssion5.model.Movie;
import com.fajaradisetyawan.madesubmisssion5.viewmodel.detail.DetailViewModelMovie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

import static com.fajaradisetyawan.madesubmisssion5.database.DatabaseContract.MovieColumns.ID_MOVIE;
import static com.fajaradisetyawan.madesubmisssion5.database.DatabaseContract.MovieColumns.MOVIE_BACKDROP_PATH;
import static com.fajaradisetyawan.madesubmisssion5.database.DatabaseContract.MovieColumns.MOVIE_GENRE;
import static com.fajaradisetyawan.madesubmisssion5.database.DatabaseContract.MovieColumns.MOVIE_OVERVIEW;
import static com.fajaradisetyawan.madesubmisssion5.database.DatabaseContract.MovieColumns.MOVIE_POPULARITY;
import static com.fajaradisetyawan.madesubmisssion5.database.DatabaseContract.MovieColumns.MOVIE_POSTER_PATH;
import static com.fajaradisetyawan.madesubmisssion5.database.DatabaseContract.MovieColumns.MOVIE_RATING_STAR;
import static com.fajaradisetyawan.madesubmisssion5.database.DatabaseContract.MovieColumns.MOVIE_RELEASE_DATE;
import static com.fajaradisetyawan.madesubmisssion5.database.DatabaseContract.MovieColumns.MOVIE_TITLE;
import static com.fajaradisetyawan.madesubmisssion5.database.DatabaseContract.MovieColumns.MOVIE_URI;

public class DetailMovieActivity extends AppCompatActivity {

    private TextView tvGenreMovie;
    private TextView tvRelase;
    private TextView tvSinopsis;
    private TextView tvPopluar;
    private RatingBar ratingBarMovie;
    private ImageView backDropMovie, posterMovie;
    private CoordinatorLayout coordinatorLayout;
    private Uri uri;
    Toolbar toolbar;
    float rateDetailMovie;
    private boolean isFavorite;
    private Movie movie, movieFromDB;
    private MenuItem menuItem;
    public static String MOVIE_DETAIL = "movie_detail";
    private static final String API_KEY = "42f8c3e2a9a24c1da94af7502c14d314";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        ratingBarMovie = findViewById(R.id.ratingBarMovie);

        tvGenreMovie = findViewById(R.id.tv_genre_movie);
        tvRelase = findViewById(R.id.tv_release_movie);
        tvSinopsis = findViewById(R.id.tv_synopsis_movie);
        tvPopluar = findViewById(R.id.tv_popular_movie);

        posterMovie = findViewById(R.id.img_poster_movie);
        backDropMovie = findViewById(R.id.img_backdrop_movie);

        coordinatorLayout = findViewById(R.id.detailMovie);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        movie = getIntent().getParcelableExtra(MOVIE_DETAIL);

        DetailViewModelMovie viewModel = ViewModelProviders.of(this).get(DetailViewModelMovie.class);

        if (getIntent().hasExtra(MOVIE_DETAIL)) {
            movie = getIntent().getParcelableExtra(MOVIE_DETAIL);

            movieFromDB = null;
            uri = Uri.parse(MOVIE_URI + "/" + movie.getMovieID());
            if (uri != null) {
                Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                if (cursor != null) {
                    movieFromDB = MappingHelper.mapCursorObjMovie(cursor);
                }
            }

            if (movieFromDB != null) {
                Log.d("DetailMovie", "movie exist");
                movie.setMovieID(movieFromDB.getMovieID());
                isFavorite = true;
            } else {
                Log.d("DetailMovie", "movie null");
                isFavorite = false;
            }

            viewModel.setMovie(movie);
            viewModel.getMovie().observe(this, observerMovie);

        }

    }

    private Observer<Movie> observerMovie = new Observer<Movie>() {
        @Override
        public void onChanged(@Nullable Movie movie) {
            getGenreMovie(movie.getMovieID());

            toolbar.setTitle(movie.getTitleMovie());
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_favorite, menu);
        menuItem = menu.getItem(0);

        if (isFavorite) {
            menuItem.setIcon(ContextCompat.getDrawable(DetailMovieActivity.this, R.drawable.ic_favorite));
        } else {
            menuItem.setIcon(ContextCompat.getDrawable(DetailMovieActivity.this, R.drawable.ic_favorite_border));
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_add_favorite) {
            favoriteMovie();
        }
        return super.onOptionsItemSelected(item);
    }

    public void favoriteMovie() {
        uri = Uri.parse(MOVIE_URI + "/" + movie.getMovieID());
        if (uri != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                movieFromDB = MappingHelper.mapCursorObjMovie(cursor);
            } else {
                movieFromDB = null;
            }
        }

        if (movieFromDB != null) {
            uri = Uri.parse(MOVIE_URI + "/" + movie.getMovieID());
            int result = getContentResolver().delete(uri, null, null);

            if (result > 0) {
                menuItem.setIcon(ContextCompat.getDrawable(DetailMovieActivity.this, R.drawable.ic_favorite_border));
                Toast.makeText(DetailMovieActivity.this, getResources().getString(R.string.removefav), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(DetailMovieActivity.this, getResources().getString(R.string.favoriteempty), Toast.LENGTH_SHORT).show();
            }

        } else {
            ContentValues args = new ContentValues();
            args.put(ID_MOVIE, movie.getMovieID());
            args.put(MOVIE_TITLE, movie.getTitleMovie());
            args.put(MOVIE_OVERVIEW, movie.getOverviewMovie());
            args.put(MOVIE_RELEASE_DATE, movie.getDatereleaseMovie());
            args.put(MOVIE_GENRE, movie.getGenreMovie());
            args.put(MOVIE_POPULARITY, movie.getPopularityMovie());
            args.put(MOVIE_POSTER_PATH, movie.getPosterMovie());
            args.put(MOVIE_BACKDROP_PATH, movie.getBackdropMovie());
            args.put(MOVIE_RATING_STAR, movie.getRatingstar());

            Uri resultUri = getContentResolver().insert(MOVIE_URI, args);
            long result = Integer.valueOf(resultUri.getLastPathSegment());

            if (result > 0) {
                menuItem.setIcon(ContextCompat.getDrawable(DetailMovieActivity.this, R.drawable.ic_favorite));
                Toast.makeText(DetailMovieActivity.this, getResources().getString(R.string.favoriteadd), Toast.LENGTH_SHORT).show();
                movie.setMovieID((int) result);
            } else {
                Toast.makeText(DetailMovieActivity.this, getResources().getString(R.string.favoriteempty), Toast.LENGTH_SHORT).show();
            }
        }
    }


}
