package com.fajaradisetyawan.madesubmisssion5.adapter.tvshow;

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
import com.fajaradisetyawan.madesubmisssion5.model.TvShow;

import java.util.ArrayList;

public class TvShowFavAdapter extends RecyclerView.Adapter<TvShowFavAdapter.TvShowFavViewHolder>{
    private ArrayList<TvShow> listTvShow = new ArrayList<>();
    private Context context;
    private OnItemFavTvShowClick onItemFavTvShowClick;

    public TvShowFavAdapter(Context context) {
        this.context = context;
    }

    public void setFavTvShows(ArrayList<TvShow> tvShows) {
        listTvShow.clear();
        listTvShow.addAll(tvShows);
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemFavTvShowClick onItemFavTvShowClick) {
        this.onItemFavTvShowClick = onItemFavTvShowClick;
    }


    @NonNull
    @Override
    public TvShowFavViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_tvshow, viewGroup, false);
        return new TvShowFavAdapter.TvShowFavViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TvShowFavViewHolder holder, final int i) {
        TvShow tvShow = listTvShow.get(i);

        holder.tvTitleTvShow.setText(tvShow.getTitleTvShow());
        holder.tvRelaseTvShow.setText(tvShow.getRelaseDateTvShow());
        holder.tvPopular.setText(tvShow.getPopularTvShow());
        holder.ratingBarTvShow.setRating((float)tvShow.getRateingTvShow());

        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w342/" + tvShow.getPosterTvShow())
                .apply(new RequestOptions().override(350,550))
                .placeholder(R.drawable.loading)
                .into(holder.imgPosterTvShow);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemFavTvShowClick.onItemClicked(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return listTvShow.size();
    }

    class TvShowFavViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitleTvShow, tvRelaseTvShow, tvPopular;
        ImageView imgPosterTvShow;
        RatingBar ratingBarTvShow;

        TvShowFavViewHolder(View itemView) {
            super(itemView);
            tvTitleTvShow = itemView.findViewById(R.id.tv_title_tvshow);
            tvRelaseTvShow = itemView.findViewById(R.id.tv_release_tvshow);
            tvPopular = itemView.findViewById(R.id.tv_popular_tvshow);
            imgPosterTvShow = itemView.findViewById(R.id.img_poster_tvshow);
            ratingBarTvShow= itemView.findViewById(R.id.ratingBarTvShow);
        }
    }

    public interface OnItemFavTvShowClick {
        void onItemClicked(int position);
    }
}
