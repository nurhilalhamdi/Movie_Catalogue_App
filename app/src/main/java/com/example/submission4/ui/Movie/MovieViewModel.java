package com.example.submission4.ui.Movie;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.submission4.BuildConfig;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MovieViewModel extends ViewModel {
    private String apikey = BuildConfig.API_KEY;

    private MutableLiveData<ArrayList<MovieItem>> listMovies = new MutableLiveData<>();
    public void setMovie(){
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<MovieItem> listitemmovie = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/discover/movie?api_key="+apikey + "&language=en-US";

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i <11 ; i++) {
                        JSONObject movie = list.getJSONObject(i);
                        MovieItem movieItem = new MovieItem();
                        movieItem.setId(movie.getInt("id"));
                        movieItem.setTitle(movie.getString("title"));
                        movieItem.setOverview(movie.getString("overview"));
                        movieItem.setRelease_date(movie.getString("release_date"));
                        movieItem.setPoster_path(movie.getString("poster_path"));
                        listitemmovie.add(movieItem);
                    }
                    listMovies.postValue(listitemmovie);
                } catch (JSONException e) {
                    Log.d("Exception",e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("Exception", error.getMessage());
            }
        });
    }

    public LiveData<ArrayList<MovieItem>> getMovie(){
        return listMovies;
    }
}
