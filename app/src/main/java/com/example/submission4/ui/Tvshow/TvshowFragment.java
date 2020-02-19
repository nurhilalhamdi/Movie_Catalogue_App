package com.example.submission4.ui.Tvshow;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.submission4.R;

import java.util.ArrayList;

public class TvshowFragment extends Fragment {

    private View v;
    private ProgressBar progressBar;
    private RecyclerViewTvshowAdapter recyclerViewTvshowAdapter;
    private TvshowViewModel viewModelTvshow;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_tvshow, container, false);
        RecyclerView recyclerView = v.findViewById(R.id.rv_tvshow);
        progressBar = v.findViewById(R.id.progressBar_Tvshow);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerViewTvshowAdapter = new RecyclerViewTvshowAdapter();
        recyclerViewTvshowAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(recyclerViewTvshowAdapter);

        showloading(true);

        recyclerViewTvshowAdapter.setOnitemClickCallback(new RecyclerViewTvshowAdapter.OnitemClickCallback() {
            @Override
            public void onitemclicked(TvshowItem tvshowItem) {
                showSelectedTvshows(tvshowItem);
            }
        });

        viewModelTvshow = new ViewModelProvider(this,new ViewModelProvider.NewInstanceFactory()).get(TvshowViewModel.class);
        viewModelTvshow.getTvshow().observe(getActivity(), new Observer<ArrayList<TvshowItem>>() {
            @Override
            public void onChanged(ArrayList<TvshowItem> tvshowItems) {
                if (tvshowItems != null){
                    recyclerViewTvshowAdapter.setData(tvshowItems);
                    showloading(false);
                }
            }
        });
        viewModelTvshow.setTvshow();
        return v;
    }


    private void showloading(Boolean state){
        if (state){
            progressBar.setVisibility(View.VISIBLE);

        }else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void showSelectedTvshows (TvshowItem item){
        Intent intent = new Intent(getActivity(), TvshowDetailActivity.class);
        intent.putExtra(TvshowDetailActivity.EXTRA_PERSON,item);
        startActivity(intent);
    }

}
