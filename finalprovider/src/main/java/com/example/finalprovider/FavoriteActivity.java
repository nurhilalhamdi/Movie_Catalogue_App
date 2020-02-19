package com.example.finalprovider;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity implements LoadFavoriteCallback{

    private static final String EXTRA_PERSON = "EXTRA_PERSON";
    private FavoriteAdapter favoriteAdapter;
    private ProgressBar progressBar;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        recyclerView = findViewById(R.id.rv_movies);
        progressBar =  findViewById(R.id.progressBar_Movie);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        favoriteAdapter = new FavoriteAdapter(this);
        recyclerView.setAdapter(favoriteAdapter);

        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());


        FavoriteActivity.DataObserver observer = new FavoriteActivity.DataObserver(handler,this);
        getContentResolver().registerContentObserver(DatabaseContract.FavoriteColumns.CONTENT_URI,true,observer);


        if (savedInstanceState == null){
            new FavoriteActivity.LoadAsync(this,this).execute();
        }else {
            ArrayList<MovieItem> list = savedInstanceState.getParcelableArrayList(EXTRA_PERSON);
            if (list != null){
                favoriteAdapter.setListfavorite(list);
            }
        }

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_PERSON,favoriteAdapter.getListMoviefavorite());
    }

    public void preExecute(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void postExecute(ArrayList<MovieItem> movieItems) {
        progressBar.setVisibility(View.INVISIBLE);
        if (movieItems.size() > 0){
            favoriteAdapter.setListfavorite(movieItems);
        }else {
            favoriteAdapter.setListfavorite(new ArrayList<MovieItem>());
            showSnackbarMessage(R.string.snackbar_message);
        }
    }

    private static class LoadAsync extends AsyncTask<Void,Void,ArrayList<MovieItem>> {
        private final WeakReference<Context> weakFavcontext;
        private final WeakReference<LoadFavoriteCallback> weakCallback;

        private LoadAsync(Context context, LoadFavoriteCallback callback) {
            weakFavcontext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected ArrayList<MovieItem> doInBackground(Void... voids) {
            Context context =weakFavcontext.get();
            Cursor cursor = context.getContentResolver().query(DatabaseContract.FavoriteColumns.CONTENT_URI
            ,null
            ,null
            ,null
            ,null);
            return MappingHelper.mapCursorToArrayList(cursor);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<MovieItem> movieItems) {
            super.onPostExecute(movieItems);
            weakCallback.get().postExecute(movieItems);
        }
    }

    private void showSnackbarMessage(int message) {
        Snackbar.make(recyclerView, message, Snackbar.LENGTH_SHORT).show();

    }

    public static class DataObserver extends ContentObserver{
        final Context context;

        DataObserver(Handler handler, Context context){
            super(handler);
            this.context = context;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            new FavoriteActivity.LoadAsync(context,(LoadFavoriteCallback)context).execute();
        }
    }
}

interface LoadFavoriteCallback {
    void preExecute();
    void postExecute(ArrayList<MovieItem> movieItems);
}
