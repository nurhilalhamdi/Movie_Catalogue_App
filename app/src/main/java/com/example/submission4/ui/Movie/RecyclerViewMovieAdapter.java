package com.example.submission4.ui.Movie;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.submission4.R;

import java.util.ArrayList;

public class RecyclerViewMovieAdapter  extends RecyclerView.Adapter<RecyclerViewMovieAdapter.ViewHolder> {

    private ArrayList<MovieItem> mdata = new ArrayList<>();


    public void setData(ArrayList<MovieItem> items){
        mdata.clear();
        mdata.addAll(items);
        notifyDataSetChanged();
    }

//    public void addItem(MovieItem movieItem) {
//        this.mdata.add(movieItem);
//        notifyItemInserted(mdata.size() - 1);
//    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_movie,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {
        viewHolder.bind(mdata.get(position));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onitemClickCallback.onitemclicked(mdata.get(viewHolder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    private OnitemClickCallback onitemClickCallback;

    public void setOnitemClickCallback(OnitemClickCallback onitemClickCallback){
        this.onitemClickCallback = onitemClickCallback;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title,overview,release;
        ImageView poster;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.text_titlemovie);
            overview = itemView.findViewById(R.id.text_descriptionmovie);
            release = itemView.findViewById(R.id.text_release);
            poster = itemView.findViewById(R.id.imagephotomovie);
        }

        void bind(MovieItem movieItem) {
            title.setText(movieItem.getTitle());
            overview.setText(movieItem.getOverview());
            release.setText(movieItem.getRelease_date());
            Glide.with(poster)
                    .load("https://image.tmdb.org/t/p/w780"+movieItem.getPoster_path())
                    .apply(new RequestOptions()).override(360,360)
                    .into(poster);
        }

    }

    public interface OnitemClickCallback{
        void onitemclicked(MovieItem movieItem);
    }
}

