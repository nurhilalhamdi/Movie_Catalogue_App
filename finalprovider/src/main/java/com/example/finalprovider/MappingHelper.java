package com.example.finalprovider;

import android.database.Cursor;

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

    public static MovieItem mapcursorToObject(Cursor cursor){
        cursor.moveToFirst();
        int id =  cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns._ID));
        String title = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.TITLE));
        String overview = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.OVERVIEW));
        String date = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.DATE));
        String poster = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.POSTER));
        String fav = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.FAV));
        return new MovieItem(id,title,overview,date,poster,fav);
    }
}
