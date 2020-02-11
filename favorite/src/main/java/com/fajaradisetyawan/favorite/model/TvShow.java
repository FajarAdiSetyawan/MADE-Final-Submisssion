package com.fajaradisetyawan.favorite.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONObject;

public class TvShow implements Parcelable {
    private int tvShowID;
    private String titleTvShow;
    private String relaseDateTvShow;
    private String overviewTvShow;
    private String backdropTvShow;
    private String genreTvShow;
    private String posterTvShow;
    private String popularTvShow;
    private double rateingTvShow;

    public TvShow(JSONObject object){
        try {
            this.tvShowID = object.getInt("id");
            this.titleTvShow = object.getString("original_name");
            this.overviewTvShow = object.getString("overview");
            this.relaseDateTvShow = object.getString("first_air_date");
            this.rateingTvShow = object.getDouble("vote_average");
            this.posterTvShow = object.getString("poster_path" );
            this.backdropTvShow = object.getString("backdrop_path");
            this.popularTvShow = object.getString("popularity");
        } catch (Exception e){
            e.printStackTrace();
            Log.d("Error Data", e.getMessage());
        }
    }

    public TvShow() {

    }


    protected TvShow(Parcel in) {
        tvShowID = in.readInt();
        titleTvShow = in.readString();
        relaseDateTvShow = in.readString();
        overviewTvShow = in.readString();
        backdropTvShow = in.readString();
        genreTvShow = in.readString();
        posterTvShow = in.readString();
        popularTvShow = in.readString();
        rateingTvShow = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(tvShowID);
        dest.writeString(titleTvShow);
        dest.writeString(relaseDateTvShow);
        dest.writeString(overviewTvShow);
        dest.writeString(backdropTvShow);
        dest.writeString(genreTvShow);
        dest.writeString(posterTvShow);
        dest.writeString(popularTvShow);
        dest.writeDouble(rateingTvShow);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TvShow> CREATOR = new Creator<TvShow>() {
        @Override
        public TvShow createFromParcel(Parcel in) {
            return new TvShow(in);
        }

        @Override
        public TvShow[] newArray(int size) {
            return new TvShow[size];
        }
    };

    public int getTvShowID() {
        return tvShowID;
    }

    public void setTvShowID(int tvShowID) {
        this.tvShowID = tvShowID;
    }

    public String getTitleTvShow() {
        return titleTvShow;
    }

    public void setTitleTvShow(String titleTvShow) {
        this.titleTvShow = titleTvShow;
    }

    public String getRelaseDateTvShow() {
        return relaseDateTvShow;
    }

    public void setRelaseDateTvShow(String relaseDateTvShow) {
        this.relaseDateTvShow = relaseDateTvShow;
    }

    public String getOverviewTvShow() {
        return overviewTvShow;
    }

    public void setOverviewTvShow(String overviewTvShow) {
        this.overviewTvShow = overviewTvShow;
    }

    public String getBackdropTvShow() {
        return backdropTvShow;
    }

    public void setBackdropTvShow(String backdropTvShow) {
        this.backdropTvShow = backdropTvShow;
    }

    public String getGenreTvShow() {
        return genreTvShow;
    }

    public void setGenreTvShow(String genreTvShow) {
        this.genreTvShow = genreTvShow;
    }

    public String getPosterTvShow() {
        return posterTvShow;
    }

    public void setPosterTvShow(String posterTvShow) {
        this.posterTvShow = posterTvShow;
    }

    public String getPopularTvShow() {
        return popularTvShow;
    }

    public void setPopularTvShow(String popularTvShow) {
        this.popularTvShow = popularTvShow;
    }

    public double getRateingTvShow() {
        return rateingTvShow;
    }

    public void setRateingTvShow(double rateingTvShow) {
        this.rateingTvShow = rateingTvShow;
    }


}
