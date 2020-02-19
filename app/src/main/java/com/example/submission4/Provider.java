package com.example.submission4;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.submission4.ui.DB.FavoriteHelper;

import static com.example.submission4.ui.DB.DatabaseContract.AUTHORITY;
import static com.example.submission4.ui.DB.DatabaseContract.FavoriteColumns.CONTENT_URI;
import static com.example.submission4.ui.DB.DatabaseContract.FavoriteColumns.TABLE_NAME;

public class Provider extends ContentProvider {

    private static final int NOTE = 1;
    private static final int NOTE_ID = 2;
    private FavoriteHelper favoriteHelper;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {

        sUriMatcher.addURI(AUTHORITY, TABLE_NAME, NOTE);
        sUriMatcher.addURI(AUTHORITY, TABLE_NAME + "/#", NOTE_ID);
    }
    @Override
    public boolean onCreate() {
        favoriteHelper = FavoriteHelper.getInstance(getContext());
        favoriteHelper.open();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case NOTE:
                cursor = favoriteHelper.queryByAll();
                break;
            case NOTE_ID:
                cursor = favoriteHelper.queryById(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long added;
        switch (sUriMatcher.match(uri)) {
            case NOTE:
                added = favoriteHelper.insert(values);
                break;
            default:
                added = 0;
                break;
        }

        getContext().getContentResolver().notifyChange(CONTENT_URI, null);

        return Uri.parse(CONTENT_URI + "/" + added);

    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int deleted;
        switch (sUriMatcher.match(uri)) {
            case NOTE_ID:
                deleted = favoriteHelper.deleteById(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }

        getContext().getContentResolver().notifyChange(CONTENT_URI, null);

        return deleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
