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
import com.fajaradisetyawan.madesubmisssion5.database.MappingHelper;
import com.fajaradisetyawan.madesubmisssion5.model.TvShow;
import com.fajaradisetyawan.madesubmisssion5.viewmodel.detail.DetailViewModelTvShow;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

import static com.fajaradisetyawan.madesubmisssion5.database.DatabaseContract.TVShowColumns.ID_TV;
import static com.fajaradisetyawan.madesubmisssion5.database.DatabaseContract.TVShowColumns.TVSHOW_BACKDROP_PATH;
import static com.fajaradisetyawan.madesubmisssion5.database.DatabaseContract.TVShowColumns.TVSHOW_GENRE;
import static com.fajaradisetyawan.madesubmisssion5.database.DatabaseContract.TVShowColumns.TVSHOW_OVERVIEW;
import static com.fajaradisetyawan.madesubmisssion5.database.DatabaseContract.TVShowColumns.TVSHOW_POPULARITY;
import static com.fajaradisetyawan.madesubmisssion5.database.DatabaseContract.TVShowColumns.TVSHOW_POSTER_PATH;
import static com.fajaradisetyawan.madesubmisssion5.database.DatabaseContract.TVShowColumns.TVSHOW_RATING_STAR;
import static com.fajaradisetyawan.madesubmisssion5.database.DatabaseContract.TVShowColumns.TVSHOW_RELEASE_DATE;
import static com.fajaradisetyawan.madesubmisssion5.database.DatabaseContract.TVShowColumns.TVSHOW_TITLE;
import static com.fajaradisetyawan.madesubmisssion5.database.DatabaseContract.TVShowColumns.TVSHOW_URI;

public class DetailTvShowActivity extends AppCompatActivity {

    private TextView tvGenreTvShow, tvRelaseTvShow, tvSinopsisTvShow, tvPopluar;
    private RatingBar ratingBarTvShow;
    private ImageView backDropTvShow, posterTvShow;
    public static String TVSHOW_DETAIL = "tvshow_detail";
    private TvShow tvShow, tvShowDB;
    private boolean isFavorite;
    CoordinatorLayout coordinatorLayout;
    private MenuItem menuItem;
    private Uri uri;
    Toolbar toolbar;
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
        coordinatorLayout = findViewById(R.id.detailTvShow);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        tvShow = getIntent().getParcelableExtra(TVSHOW_DETAIL);

        DetailViewModelTvShow viewModel = ViewModelProviders.of(this).get(DetailViewModelTvShow.class);

        if (getIntent().hasExtra(TVSHOW_DETAIL)) {
            tvShow = getIntent().getParcelableExtra(TVSHOW_DETAIL);

            tvShowDB = null;
            uri = Uri.parse(TVSHOW_URI + "/" + tvShow.getTvShowID());
            if (uri != null) {
                Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                if (cursor != null) {
                    tvShowDB = MappingHelper.mapCursorObjTvShow(cursor);
                }
            }

            if (tvShowDB != null) {
                Log.d("DetailTvshow", "tvshow exist");
                tvShow.setTvShowID(tvShowDB.getTvShowID());
                isFavorite = true;
            } else {
                Log.d("DetailTvshow", "tvshow null");
                isFavorite = false;
            }

            viewModel.setTvShow(tvShow);
            viewModel.getTvShow().observe(this, observerTvshow);
            setTitle(tvShow.getTitleTvShow());

        }
    }

    private Observer<TvShow> observerTvshow = new Observer<TvShow>() {
        @Override
        public void onChanged(@Nullable TvShow tvShow) {
            getGenreTvShow(tvShow.getTvShowID());

            toolbar.setTitle(tvShow.getTitleTvShow());
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_favorite, menu);
        menuItem = menu.getItem(0);

        if (isFavorite) {
            menuItem.setIcon(ContextCompat.getDrawable(DetailTvShowActivity.this, R.drawable.ic_favorite));
        } else {
            menuItem.setIcon(ContextCompat.getDrawable(DetailTvShowActivity.this, R.drawable.ic_favorite_border));
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_add_favorite) {
            favoriteTvShow();
        }
        return super.onOptionsItemSelected(item);
    }

    public void favoriteTvShow() {
        uri = Uri.parse(TVSHOW_URI + "/" + tvShow.getTvShowID());
        if (uri != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                tvShowDB = MappingHelper.mapCursorObjTvShow(cursor);
            } else {
                tvShowDB = null;
            }
        }

        if (tvShowDB != null) {
            uri = Uri.parse(TVSHOW_URI + "/" + tvShow.getTvShowID());
            int result = getContentResolver().delete(uri, null, null);

            if (result > 0) {
                menuItem.setIcon(ContextCompat.getDrawable(DetailTvShowActivity.this, R.drawable.ic_favorite_border));
                Toast.makeText(DetailTvShowActivity.this, getResources().getString(R.string.removefav), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(DetailTvShowActivity.this, getResources().getString(R.string.favoriteempty), Toast.LENGTH_SHORT).show();
            }

        } else {
            ContentValues contentValues = new ContentValues();
            contentValues.put(ID_TV, tvShow.getTvShowID());
            contentValues.put(TVSHOW_TITLE, tvShow.getTvShowID());
            contentValues.put(TVSHOW_OVERVIEW, tvShow.getOverviewTvShow());
            contentValues.put(TVSHOW_RELEASE_DATE, tvShow.getRelaseDateTvShow());
            contentValues.put(TVSHOW_RATING_STAR, tvShow.getRateingTvShow());
            contentValues.put(TVSHOW_GENRE, tvShow.getGenreTvShow());
            contentValues.put(TVSHOW_POPULARITY, tvShow.getPopularTvShow());
            contentValues.put(TVSHOW_POSTER_PATH, tvShow.getPosterTvShow());
            contentValues.put(TVSHOW_BACKDROP_PATH, tvShow.getBackdropTvShow());

            Uri resultUri = getContentResolver().insert(TVSHOW_URI, contentValues);
            long result = Integer.valueOf(resultUri.getLastPathSegment());

            if (result > 0) {
                menuItem.setIcon(ContextCompat.getDrawable(DetailTvShowActivity.this, R.drawable.ic_favorite));
                Toast.makeText(DetailTvShowActivity.this, getResources().getString(R.string.favoriteadd), Toast.LENGTH_SHORT).show();
                tvShow.setTvShowID((int) result);
            } else {
                Toast.makeText(DetailTvShowActivity.this, getResources().getString(R.string.favoriteempty), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
