package com.example.submission4.ui.Tvshow;

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

public class RecyclerViewTvshowAdapter extends RecyclerView.Adapter<RecyclerViewTvshowAdapter.ViewHolder> {

    private ArrayList<TvshowItem> mdata = new ArrayList<>();

    public void setData(ArrayList<TvshowItem> items){
        mdata.clear();
        mdata.addAll(items);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_tvshow,parent,false);
        RecyclerViewTvshowAdapter.ViewHolder viewHolder = new RecyclerViewTvshowAdapter.ViewHolder(view);
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


    private RecyclerViewTvshowAdapter.OnitemClickCallback onitemClickCallback;

    public void setOnitemClickCallback(RecyclerViewTvshowAdapter.OnitemClickCallback onitemClickCallback){
        this.onitemClickCallback = onitemClickCallback;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,overview,release;
        ImageView poster;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.text_titletvshow);
            overview = itemView.findViewById(R.id.text_descriptiontvshow);
            release = itemView.findViewById(R.id.text_releasetvshow);
            poster = itemView.findViewById(R.id.imagephototvshow);
        }
        void bind(TvshowItem tvshowItem){
            title.setText(tvshowItem.getTitle());
            overview.setText(tvshowItem.getOverview());
            release.setText(tvshowItem.getRelease_date());
            Glide.with(poster)
                    .load("https://image.tmdb.org/t/p/w780"+tvshowItem.getPoster_path())
                    .apply(new RequestOptions()).override(360,360)
                    .into(poster);
        }
    }

    public interface OnitemClickCallback{
        void onitemclicked(TvshowItem tvshowItem);
    }

}
