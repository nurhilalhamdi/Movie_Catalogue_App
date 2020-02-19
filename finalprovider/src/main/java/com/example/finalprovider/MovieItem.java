package com.example.finalprovider;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieItem implements Parcelable {
    private String title,overview,release_date;
    private String poster_path;

    public String getFavorite() {
        return favorite;
    }

    public void setFavorite(String favorite) {
        this.favorite = favorite;
    }

    private String favorite;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MovieItem(int id, String title, String overview, String date, String poster, String favorite) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.release_date = date;
        this.poster_path = poster;
        this.favorite = favorite;
    }

    public MovieItem() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getPoster_path() {
        return ((poster_path));
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }


    public MovieItem(Parcel in) {
        favorite = in.readString();
        id = in.readInt();
        title = in.readString();
        overview = in.readString();
        release_date = in.readString();
        poster_path = in.readString();

    }

    public static final Creator<MovieItem> CREATOR = new Creator<MovieItem>() {
        @Override
        public MovieItem createFromParcel(Parcel in) {
            return new MovieItem(in);
        }

        @Override
        public MovieItem[] newArray(int size) {
            return new MovieItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(favorite);
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(overview);
        dest.writeString(release_date);
        dest.writeString(poster_path);

    }

}