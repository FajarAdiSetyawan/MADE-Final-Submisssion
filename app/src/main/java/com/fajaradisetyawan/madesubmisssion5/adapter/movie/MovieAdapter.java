package com.fajaradisetyawan.madesubmisssion5.adapter.movie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fajaradisetyawan.madesubmisssion5.R;
import com.fajaradisetyawan.madesubmisssion5.model.Movie;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>{

    private ArrayList<Movie> listMovie = new ArrayList<>();
    private Context context;
    private OnItemMovieClick onItemMovieClick;

    public MovieAdapter(Context context) {
        this.context = context;
    }

    public void setMovies(ArrayList<Movie> movies) {
        listMovie.clear();
        listMovie.addAll(movies);
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemMovieClick onItemMovieClick) {
        this.onItemMovieClick = onItemMovieClick;
    }


    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie, viewGroup, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieViewHolder holder, final int i) {
        Movie movie = listMovie.get(i);

        holder.tvTitleMovie.setText(movie.getTitleMovie());
        holder.tvRelaseDate.setText(movie.getDatereleaseMovie());
        holder.tvPopular.setText(movie.getPopularityMovie());

        holder.rateMovie.setRating((float)(movie.getRatingstar()/2));

        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w342/"+ movie.getPosterMovie())
                .apply(new RequestOptions().override(350,550))
                .placeholder(R.drawable.loading)
                .into(holder.imgPoster);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemMovieClick.onItemClicked(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitleMovie,tvRelaseDate, tvPopular;
        ImageView imgPoster;
        RatingBar rateMovie;
        MovieViewHolder(@NonNull View itemView) {
            super(itemView);

            imgPoster =itemView.findViewById(R.id.img_movie_poster);
            tvTitleMovie = itemView.findViewById(R.id.tv_title_movie);
            tvRelaseDate = itemView.findViewById(R.id.tv_release_movie);
            tvPopular = itemView.findViewById(R.id.tv_popular_movie);
            rateMovie = itemView.findViewById(R.id.ratingBarMovie);
        }
    }

    public interface OnItemMovieClick {
        void onItemClicked(int position);
    }

}
