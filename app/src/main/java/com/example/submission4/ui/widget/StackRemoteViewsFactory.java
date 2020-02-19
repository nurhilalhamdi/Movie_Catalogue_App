package com.example.submission4.ui.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.example.submission4.R;
import com.example.submission4.ui.Movie.MovieItem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.example.submission4.ui.DB.DatabaseContract.FavoriteColumns.CONTENT_URI;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private final List<Bitmap> bitmaps = new ArrayList<>();
    private final Context mcontext;
    private Cursor cursor;

    public StackRemoteViewsFactory(Context context) {
        this.mcontext = context;
    }

    @Override
    public void onCreate() {

        cursor = mcontext.getContentResolver().
                query(CONTENT_URI,
                        null,
                        null,
                        null,
                        null);
    }

    @Override
    public void onDataSetChanged() {
//        if (cursor != null) {
//            cursor.close();
//        }
            final long identityToken = Binder.clearCallingIdentity();
            Binder.restoreCallingIdentity(identityToken);

        }

    @Override
    public void onDestroy() {

    }

    private MovieItem getitem(int position){
        if (!cursor.moveToPosition(position)){
            try {
                throw new IllegalAccessException("Position Valid");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return new MovieItem(cursor);
    }


    @Override
    public int getCount() {
//        Log.d("_ss", String.valueOf(cursor.getCount()));
//        Log.d("_ss", String.valueOf(cursor.getString(1)));
        return cursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(mcontext.getPackageName(), R.layout.widgetitem);
        MovieItem movieItem = getitem(position);
                Bitmap bitmap;
                try {
//                    Log.d("_GGG", getPhoto);
                    bitmap = Glide.with(mcontext).asBitmap().load("https://image.tmdb.org/t/p/w780" + movieItem.getPoster_path())
                            .submit()
                            .get();

                    remoteViews.setImageViewBitmap(R.id.imageView, bitmap);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                Bundle bundle = new Bundle();
                bundle.putInt(FavoriteBannerWidget.EXTRA_ITEM, position);
                Intent fillintent = new Intent();
                fillintent.putExtras(bundle);
                remoteViews.setOnClickFillInIntent(R.id.imageView, fillintent);
//            }
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
