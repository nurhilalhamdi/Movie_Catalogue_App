package com.example.submission4.ui.Movie;

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

import static com.example.submission4.ui.DB.DatabaseContract.FavoriteColumns.DATE;
import static com.example.submission4.ui.DB.DatabaseContract.FavoriteColumns.FAV;
import static com.example.submission4.ui.DB.DatabaseContract.FavoriteColumns.OVERVIEW;
import static com.example.submission4.ui.DB.DatabaseContract.FavoriteColumns.POSTER;
import static com.example.submission4.ui.DB.DatabaseContract.FavoriteColumns.TITLE;
import static com.example.submission4.ui.DB.DatabaseContract.FavoriteColumns._ID;
public class MovieDetailActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_PERSON = "extra_person";
    private TextView title,overview,release;
    private ImageView imgposter;
    private Button btn;
    private MovieItem movieItem;
    private FavoriteHelper favoriteHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        title = findViewById(R.id.titleMovieDetail);
        overview = findViewById(R.id.overviewMoviedetail);
        imgposter = findViewById(R.id.imageMovieDetail);
        release = findViewById(R.id.textReleaseMovieDetail);

        movieItem = getIntent().getParcelableExtra(EXTRA_PERSON);
            Glide.with(imgposter)
                    .load("https://image.tmdb.org/t/p/w780"+movieItem.getPoster_path())
                    .into(imgposter);
            title.setText(movieItem.getTitle());
            overview.setText(movieItem.getOverview());
            release.setText(movieItem.getRelease_date());
            Log.d("_ss","INI JUDUL NYA = "+ title.getText());
            btn = findViewById(R.id.buttonadd);
            favoriteHelper = FavoriteHelper.getInstance(getApplicationContext());
            favoriteHelper.open();
            favoriteHelper.queryByAll();
            btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonadd) {
            movieItem = getIntent().getParcelableExtra(EXTRA_PERSON);
            int id = movieItem.getId();
            String titlem = movieItem.getTitle();
            String overview = movieItem.getOverview();
            String release = movieItem.getRelease_date();
            String poster = movieItem.getPoster_path();


            ContentValues contentValues = new ContentValues();
            Log.d("_hsg", poster);
            contentValues.put(_ID, id);
            contentValues.put(TITLE, titlem);
            contentValues.put(OVERVIEW, overview);
            contentValues.put(DATE, release);
            contentValues.put(POSTER, poster);
            contentValues.put(FAV, "favorite");
            long result = favoriteHelper.insert(contentValues);
            Log.d("_DATA", contentValues.get(_ID).toString());

            if (result > 0) {
                Toast.makeText(MovieDetailActivity.this, R.string.success_insert, Toast.LENGTH_LONG).show();
            }else if(result < 0){
                Toast.makeText(this, R.string.hasbeen_insert_Movie, Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this, R.string.failde_insert, Toast.LENGTH_LONG).show();
            }

        }
    }
}

