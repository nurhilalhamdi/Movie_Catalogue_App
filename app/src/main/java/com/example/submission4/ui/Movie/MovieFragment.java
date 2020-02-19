package com.example.submission4.ui.Movie;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.submission4.R;

import java.util.ArrayList;

public class MovieFragment extends Fragment implements View.OnClickListener {

    private View view;
    private ProgressBar progressBar;
    private RecyclerViewMovieAdapter recyclerViewMovieAdapter;
    private MovieViewModel viewModelMovie;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_movie, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.rv_movies);
        progressBar = view.findViewById(R.id.progressBar_Movie);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerViewMovieAdapter = new RecyclerViewMovieAdapter();
        recyclerViewMovieAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(recyclerViewMovieAdapter);


        showloading(true);

        recyclerViewMovieAdapter.setOnitemClickCallback(new RecyclerViewMovieAdapter.OnitemClickCallback() {
            @Override
            public void onitemclicked(MovieItem movieItem) {
                showSelectedMovie(movieItem);
            }
        });

        viewModelMovie = new ViewModelProvider(this,new ViewModelProvider.NewInstanceFactory()).get(MovieViewModel.class);
        viewModelMovie.getMovie().observe(getActivity(), new Observer<ArrayList<MovieItem>>() {
            @Override
            public void onChanged(ArrayList<MovieItem> movieItems) {
                if (movieItems != null){
                    recyclerViewMovieAdapter.setData(movieItems);
                    showloading(false);
                }
            }
        });
        viewModelMovie.setMovie();
        return view;
    }


    private void showloading(Boolean state){
        if (state){
            progressBar.setVisibility(View.VISIBLE);

        }else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void showSelectedMovie (MovieItem item){
        Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
        intent.putExtra(MovieDetailActivity.EXTRA_PERSON,item);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {

    }
}