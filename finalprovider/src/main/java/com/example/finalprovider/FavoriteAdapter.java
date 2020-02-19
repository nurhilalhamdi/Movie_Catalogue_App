package com.example.finalprovider;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    private ArrayList<MovieItem> listmoviefavorite = new ArrayList<>();
    private final Activity activity;

    public FavoriteAdapter(Activity activity){
        this.activity = activity;
    }

    public ArrayList<MovieItem> getListMoviefavorite(){
        return listmoviefavorite;
    }

    public void setListfavorite(ArrayList<MovieItem> listfavorite){
        if (listfavorite.size()>0){
            this.listmoviefavorite.clear();
        }
        this.listmoviefavorite.addAll(listfavorite);

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoriteAdapter.FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_movie,parent,false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.FavoriteViewHolder holder, int position) {
        holder.bind(listmoviefavorite.get(position));
        holder.itemView.setOnClickListener(new CustomOnclickListener(position, new CustomOnclickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent moveWithDataIntent = new Intent(view.getContext(), DetailFavoriteActivity.class);
                moveWithDataIntent.putExtra(DetailFavoriteActivity.EXTRA_PERSON, listmoviefavorite.get(position));
                view.getContext().startActivity(moveWithDataIntent);
            }
        }));
    }

    @Override
    public int getItemCount() {
        return listmoviefavorite.size();
    }

    public class FavoriteViewHolder extends RecyclerView.ViewHolder {
        TextView title,overview,release;
        ImageView poster;
        public FavoriteViewHolder(@NonNull View itemView) {
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
}
