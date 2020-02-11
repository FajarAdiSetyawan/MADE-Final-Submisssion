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

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.TvShowViewHolder>{

    private ArrayList<TvShow> listTvShow = new ArrayList<>();
    private OnItemTvShowClick onItemTvShowClick;

    public TvShowAdapter(Context context) {
    }

    public void setTvShows(ArrayList<TvShow> tvShows) {
        listTvShow.clear();
        listTvShow.addAll(tvShows);
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemTvShowClick onItemShowClick) {
        this.onItemTvShowClick = onItemShowClick;
    }

    @NonNull
    @Override
    public TvShowViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_tvshow, viewGroup, false);
        return new TvShowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TvShowViewHolder holder, final int i) {
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
                onItemTvShowClick.onItemClicked(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return listTvShow.size();
    }

    class TvShowViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitleTvShow, tvRelaseTvShow, tvPopular;
        ImageView imgPosterTvShow;
        RatingBar ratingBarTvShow;
        TvShowViewHolder(View itemView) {
            super(itemView);
            tvTitleTvShow = itemView.findViewById(R.id.tv_title_tvshow);
            tvRelaseTvShow = itemView.findViewById(R.id.tv_release_tvshow);
            tvPopular = itemView.findViewById(R.id.tv_popular_tvshow);
            imgPosterTvShow = itemView.findViewById(R.id.img_poster_tvshow);
            ratingBarTvShow= itemView.findViewById(R.id.ratingBarTvShow);
        }
    }

    public interface OnItemTvShowClick {
        void onItemClicked(int position);
    }

}
