package com.example.submission4.ui.DB;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static final String AUTHORITY = "com.example.submission4";
    public static final String SCHEME = "content";

    public static final class FavoriteColumns implements BaseColumns{
        public static final String TABLE_NAME = "favorite";
        public static final String TITLE = "title";
        public static final String OVERVIEW = "overview";
        public static final String DATE = "release_date";
        public static final String POSTER = "poster";
        public static final String FAV = "fav";

        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build();

        public static String getColumnString(Cursor cursor,String columname){
            return cursor.getString(cursor.getColumnIndex(columname));
        }
    }
}
