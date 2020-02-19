package com.example.submission4.ui.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;




public class DatabaseHelper extends SQLiteOpenHelper {


    public static String DATABASE_NAME = "dbfavorite";
    private static final int DATABASE_VERSION = 1;
    private static final String SQL_CREATE_TABLE_Favorite = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            DatabaseContract.FavoriteColumns.TABLE_NAME,
            DatabaseContract.FavoriteColumns._ID,
            DatabaseContract.FavoriteColumns.TITLE,
            DatabaseContract.FavoriteColumns.OVERVIEW,
            DatabaseContract.FavoriteColumns.DATE,
            DatabaseContract.FavoriteColumns.POSTER,
            DatabaseContract.FavoriteColumns.FAV
    );

    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_Favorite);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.FavoriteColumns.TABLE_NAME);
        onCreate(db);
    }
}
