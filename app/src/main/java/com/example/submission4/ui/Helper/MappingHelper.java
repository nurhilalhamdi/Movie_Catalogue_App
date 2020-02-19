package com.example.submission4.ui.Helper;

import android.database.Cursor;

import com.example.submission4.ui.DB.DatabaseContract;
import com.example.submission4.ui.Movie.MovieItem;

import java.util.ArrayList;

public class MappingHelper {

    public static ArrayList<MovieItem> mapCursorToArrayList(Cursor movieCursor){
        ArrayList<MovieItem> movieItemslist = new ArrayList<>();
        while (movieCursor.moveToNext()){
            int id =  movieCursor.getInt(movieCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns._ID));
            String title = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.TITLE));
            String overview = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.OVERVIEW));
            String date = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.DATE));
            String poster = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.POSTER));
            String fav = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.FAV));
            movieItemslist.add(new MovieItem(id,title,overview,date,poster,fav));
        }

        return movieItemslist;
    }
}
