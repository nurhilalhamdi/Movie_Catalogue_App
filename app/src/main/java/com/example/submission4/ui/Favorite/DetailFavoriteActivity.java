package com.example.submission4.ui.Favorite;

import android.content.ContentValues;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.submission4.R;
import com.example.submission4.ui.DB.FavoriteHelper;
import com.example.submission4.ui.Movie.MovieItem;

import static android.provider.BaseColumns._ID;
import static com.example.submission4.ui.DB.DatabaseContract.FavoriteColumns.DATE;
import static com.example.submission4.ui.DB.DatabaseContract.FavoriteColumns.FAV;
import static com.example.submission4.ui.DB.DatabaseContract.FavoriteColumns.OVERVIEW;
import static com.example.submission4.ui.DB.DatabaseContract.FavoriteColumns.POSTER;
import static com.example.submission4.ui.DB.DatabaseContract.FavoriteColumns.TITLE;

public class DetailFavoriteActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_PERSON = "extra_person";
    TextView title,overview,release;
    ImageView imgposter;
    MovieItem movieItem;
    Button buttonadd;
    int positiion;
    private FavoriteHelper favoriteHelper;

    public static final String EXTRA_POSITION = "extra_position";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_favorite);

        title = findViewById(R.id.titleMovieDetail);
        overview = findViewById(R.id.overviewMoviedetail);
        imgposter = findViewById(R.id.imageMovieDetail);
        release = findViewById(R.id.textReleaseMovieDetail);
        movieItem = getIntent().getParcelableExtra(EXTRA_PERSON);

        if (movieItem !=null){
            Glide.with(imgposter)
                    .load("https://image.tmdb.org/t/p/w780"+movieItem.getPoster_path())
                    .into(imgposter);
            title.setText(movieItem.getTitle());
            overview.setText(movieItem.getOverview());
            release.setText(movieItem.getRelease_date());
        }

        favoriteHelper = FavoriteHelper.getInstance(getApplicationContext());
        favoriteHelper.open();
        favoriteHelper.queryByAll();
        buttonadd = findViewById(R.id.buttondel);
        buttonadd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttondel){
            movieItem = getIntent().getParcelableExtra(EXTRA_PERSON);
            int id = movieItem.getId();
            String titlem = movieItem.getTitle();
            String overview = movieItem.getOverview();
            String release = movieItem.getRelease_date();
            String poster = movieItem.getPoster_path();

            ContentValues contentValues = new ContentValues();
            Log.d("_hsg",poster);
            contentValues.put(_ID,id);
            contentValues.put(TITLE,titlem);
            contentValues.put(OVERVIEW,overview);
            contentValues.put(DATE,release);
            contentValues.put(POSTER,poster);
            contentValues.put(FAV,"favorite");
            long result = favoriteHelper.deleteById(String.valueOf(movieItem.getId()));
            Log.d("_DATA",contentValues.get(_ID).toString());

            if (result > 0 ){

                Toast.makeText(DetailFavoriteActivity.this,R.string.success_delete,Toast.LENGTH_LONG).show();
            }else if(result < 1){
                Toast.makeText(DetailFavoriteActivity.this,R.string.hasbeen_delete,Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(DetailFavoriteActivity.this,R.string.fail_delete,Toast.LENGTH_LONG).show();
            }
        }
    }
}
