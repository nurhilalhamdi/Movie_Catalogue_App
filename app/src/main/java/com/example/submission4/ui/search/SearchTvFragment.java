package com.example.submission4.ui.search;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.submission4.R;
import com.example.submission4.ui.Tvshow.RecyclerViewTvshowAdapter;
import com.example.submission4.ui.Tvshow.TvshowDetailActivity;
import com.example.submission4.ui.Tvshow.TvshowItem;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchTvFragment extends Fragment {
    SeacrhTvModel seacrhTvModel;
    RecyclerView recyclerView;
    RecyclerViewTvshowAdapter recyclerViewTvshowAdapter;
    ProgressBar progressBar;
    View view;
    TextView textView;

    public SearchTvFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        view =  inflater.inflate(R.layout.fragment_search_tv, container, false);
        recyclerView = view.findViewById(R.id.rv_tvshow);
        progressBar = view.findViewById(R.id.progressBar_Tvshow);
        textView = view.findViewById(R.id.searchtv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        loading(false);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search,menu);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        if (searchManager != null){
            SearchView searchView = (SearchView) (menu.findItem(R.id.search)).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
            searchView.setQueryHint(getResources().getString(R.string.title_search));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    recyclerViewTvshowAdapter = new RecyclerViewTvshowAdapter();
                    recyclerViewTvshowAdapter.notifyDataSetChanged();
                    recyclerView.setAdapter(recyclerViewTvshowAdapter);
                    seacrhTvModel = new ViewModelProvider(SearchTvFragment.this,new ViewModelProvider.NewInstanceFactory()).get(SeacrhTvModel.class);
                    loading(true);
                    seacrhTvModel.getSearchTv().observe(getActivity(), new Observer<ArrayList<TvshowItem>>() {
                        @Override
                        public void onChanged(ArrayList<TvshowItem> tvshowItems) {
                            if (tvshowItems != null){
                                recyclerViewTvshowAdapter.setData(tvshowItems);
                                loading(false);

                            }
                        }
                    });


                    recyclerViewTvshowAdapter.setOnitemClickCallback(new RecyclerViewTvshowAdapter.OnitemClickCallback() {
                        @Override
                        public void onitemclicked(TvshowItem tvshowItem) {
                            showSelectedTvshows(tvshowItem);
                        }
                    });
                    seacrhTvModel.searchTv(query);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });

        }
    }

    public void loading(Boolean state){
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);
        }else {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.GONE);
                }
            },100);
        }
    }

    private void showSelectedTvshows (TvshowItem item){
        Intent intent = new Intent(getActivity(), TvshowDetailActivity.class);
        intent.putExtra(TvshowDetailActivity.EXTRA_PERSON,item);
        startActivity(intent);
    }
}
