package com.example.submission4.ui.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.submission4.BuildConfig;
import com.example.submission4.ui.Movie.MovieItem;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SearchMovieModel extends ViewModel {
    private String apikey = BuildConfig.API_KEY;
    private MutableLiveData<ArrayList<MovieItem>> listMutableLiveData = new MutableLiveData<>();
    private AsyncHttpClient client = new AsyncHttpClient();
    private final ArrayList<MovieItem> list = new ArrayList<>();

    public void searchMovie(String query){
            String url = "https://api.themoviedb.org/3/search/movie?api_key="+apikey+"&language=en-US&query="+query;
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject response = new JSONObject(result);
                    JSONArray jsonArray = response.getJSONArray("results");

                    for (int i = 0; i <jsonArray.length() ; i++) {
                        MovieItem movieItem = new MovieItem();
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        movieItem.setId(jsonObject.getInt("id"));
                        movieItem.setTitle(jsonObject.getString("title"));
                        movieItem.setOverview(jsonObject.getString("overview"));
                        movieItem.setRelease_date(jsonObject.getString("release_date"));
                        movieItem.setPoster_path(jsonObject.getString("poster_path"));
                        list.add(movieItem);
                    }
                    listMutableLiveData.postValue(list);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }
        public LiveData<ArrayList<MovieItem>> getSearchMovie(){
        return listMutableLiveData;
        }
}
