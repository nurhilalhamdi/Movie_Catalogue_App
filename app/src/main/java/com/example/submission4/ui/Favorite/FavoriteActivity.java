package com.example.submission4.ui.Favorite;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.submission4.R;
import com.example.submission4.ui.DB.FavoriteHelper;
import com.example.submission4.ui.Helper.MappingHelper;
import com.example.submission4.ui.Movie.MovieItem;
import com.google.android.material.snackbar.Snackbar;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity implements LoadFavoriteCallback{

    private static final String EXTRA_PERSON = "EXTRA_PERSON";
    private FavoriteAdapter favoriteAdapter;
    private ProgressBar progressBar;
RecyclerView recyclerView;
private FavoriteHelper favoriteHelper;

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

        favoriteHelper = FavoriteHelper.getInstance(getApplicationContext());
        favoriteHelper.open();

        if (savedInstanceState == null){
            new LoadAsync(favoriteHelper,this).execute();
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
        private final WeakReference<FavoriteHelper> weakFavHelper;
        private final WeakReference<LoadFavoriteCallback> weakCallback;

        private LoadAsync(FavoriteHelper favHelper, LoadFavoriteCallback callback) {
            weakFavHelper = new WeakReference<>(favHelper);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected ArrayList<MovieItem> doInBackground(Void... voids) {
            Cursor cursor =weakFavHelper.get().queryByAll();
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
}

interface LoadFavoriteCallback {
    void preExecute();
    void postExecute(ArrayList<MovieItem> movieItems);
}
