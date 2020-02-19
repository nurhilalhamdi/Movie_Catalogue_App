package com.example.submission4.ui.Tvshow;

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

public class TvshowViewModel extends ViewModel {
    private String apikey = BuildConfig.API_KEY;

    private MutableLiveData<ArrayList<TvshowItem>> listTvshows = new MutableLiveData<>();

    public void setTvshow(){
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<TvshowItem> listitemtvshow = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/discover/tv?api_key="+apikey + "&language=en-US";
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");
                    for (int i = 0; i <11 ; i++) {
                        JSONObject tvshow = list.getJSONObject(i);
                        TvshowItem tvshowItem = new TvshowItem();
                        tvshowItem.setTitle(tvshow.getString("name"));
                        tvshowItem.setId(tvshow.getInt("id"));
                        tvshowItem.setOverview(tvshow.getString("overview"));
                        tvshowItem.setRelease_date(tvshow.getString("first_air_date"));
                        tvshowItem.setPoster_path(tvshow.getString("poster_path"));
                        listitemtvshow.add(tvshowItem);
                    }
                    listTvshows.postValue(listitemtvshow);
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

    public LiveData<ArrayList<TvshowItem>> getTvshow(){
        return listTvshows;
    }
}
