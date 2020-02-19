package com.example.submission4.ui.Tvshow;

import android.os.Parcel;
import android.os.Parcelable;

public class TvshowItem implements Parcelable {
    private String title,overview,release_date;
    private String poster_path,fav;
    private int id;

    public String getFav() {
        return fav;
    }

    public void setFav(String fav) {
        this.fav = fav;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TvshowItem(Parcel in) {
        title = in.readString();
        overview = in.readString();
        release_date = in.readString();
        poster_path = in.readString();
        id = in.readInt();
        fav = in.readString();
    }

    public static final Creator<TvshowItem> CREATOR = new Creator<TvshowItem>() {
        @Override
        public TvshowItem createFromParcel(Parcel in) {
            return new TvshowItem(in);
        }

        @Override
        public TvshowItem[] newArray(int size) {
            return new TvshowItem[size];
        }
    };

    public TvshowItem() {

    }
    public TvshowItem(int id, String title, String overview, String poster, String fav , String release) {
        this.title = title;
        this.overview = overview;
        this.poster_path = poster;
        this.id = id;
        this.fav = fav;
        this.release_date = release;
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
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(overview);
        dest.writeString(release_date);
        dest.writeString(poster_path);
        dest.writeInt(id);
        dest.writeString(fav);
    }
}

