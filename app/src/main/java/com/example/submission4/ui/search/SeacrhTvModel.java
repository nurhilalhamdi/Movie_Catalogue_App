package com.example.submission4.ui.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.submission4.BuildConfig;
import com.example.submission4.ui.Tvshow.TvshowItem;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SeacrhTvModel extends ViewModel {

    private String apikey = BuildConfig.API_KEY;
    private MutableLiveData<ArrayList<TvshowItem>> listMutableLiveData = new MutableLiveData<>();
    private AsyncHttpClient client = new AsyncHttpClient();
    private final ArrayList<TvshowItem> list = new ArrayList<>();

    public void searchTv(String query){
        String url = "https://api.themoviedb.org/3/search/tv?api_key="+apikey+"&language=en-US&query="+query;
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result= new String( responseBody);
                    JSONObject response = new JSONObject(result);
                    JSONArray jsonArray = response.getJSONArray("results");
                    for (int i = 0; i <jsonArray.length() ; i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        TvshowItem tvshowItem = new TvshowItem();
                        tvshowItem.setId(jsonObject.getInt("id"));
                        tvshowItem.setTitle(jsonObject.getString("name"));
                        tvshowItem.setOverview(jsonObject.getString("overview"));
                        tvshowItem.setRelease_date(jsonObject.getString("first_air_date"));
                        tvshowItem.setPoster_path(jsonObject.getString("poster_path"));
                        list.add(tvshowItem);
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

    public LiveData<ArrayList<TvshowItem>> getSearchTv (){
        return listMutableLiveData;
    }
}
