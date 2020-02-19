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
import com.example.submission4.ui.Movie.MovieDetailActivity;
import com.example.submission4.ui.Movie.MovieItem;
import com.example.submission4.ui.Movie.RecyclerViewMovieAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchMovieFragment extends Fragment {

    SearchMovieModel searchMovieModel;
    RecyclerView recyclerView;
    RecyclerViewMovieAdapter recyclerViewMovieAdapter;
    View view;
    ProgressBar progressBar;
    TextView textView ;
    public SearchMovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        view =  inflater.inflate(R.layout.fragment_search_movie, container, false);
        recyclerView = view.findViewById(R.id.rv_movies);
        progressBar = view.findViewById(R.id.progressBar_Movie);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        textView = view.findViewById(R.id.searchmovie);
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
                    recyclerViewMovieAdapter = new RecyclerViewMovieAdapter();
                    recyclerViewMovieAdapter.notifyDataSetChanged();
                    recyclerView.setAdapter(recyclerViewMovieAdapter);
                    loading(true);
                    searchMovieModel = new ViewModelProvider(SearchMovieFragment.this,new ViewModelProvider.NewInstanceFactory()).get(SearchMovieModel.class);
                    searchMovieModel.getSearchMovie().observe(getActivity(),new Observer<ArrayList<MovieItem>>() {
                        @Override
                        public void onChanged(ArrayList<MovieItem> movieItems) {
                            if (movieItems != null){
                                recyclerViewMovieAdapter.setData(movieItems);
                                loading(false);

                            }
                        }
                    });
                    searchMovieModel.searchMovie(query);

                    recyclerViewMovieAdapter.setOnitemClickCallback(new RecyclerViewMovieAdapter.OnitemClickCallback() {
                        @Override
                        public void onitemclicked(MovieItem movieItem) {
                            showSelectedMovie(movieItem);
                        }
                    });
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
        }
    }

    private void loading(Boolean state){
        if (state){
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

    private void showSelectedMovie (MovieItem item){
        Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
        intent.putExtra(MovieDetailActivity.EXTRA_PERSON,item);
        startActivity(intent);
    }

}
