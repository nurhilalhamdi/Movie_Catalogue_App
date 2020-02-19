package com.example.submission4.ui.Tvshow;

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

import static android.provider.BaseColumns._ID;
import static com.example.submission4.ui.DB.DatabaseContract.FavoriteColumns.DATE;
import static com.example.submission4.ui.DB.DatabaseContract.FavoriteColumns.FAV;
import static com.example.submission4.ui.DB.DatabaseContract.FavoriteColumns.OVERVIEW;
import static com.example.submission4.ui.DB.DatabaseContract.FavoriteColumns.POSTER;
import static com.example.submission4.ui.DB.DatabaseContract.FavoriteColumns.TITLE;

public class TvshowDetailActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_PERSON = "extra_person";
    TextView title,overview,release;
    ImageView imgposter;
    private FavoriteHelper favoritetvHelper;
    Button buttonadd;
    TvshowItem tvshowItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvshow_detail);


        title = findViewById(R.id.titleMovieDetail);
        overview = findViewById(R.id.overviewMoviedetail);
        imgposter = findViewById(R.id.imageTvshowDetail);
        release = findViewById(R.id.textReleaseMovieDetail);
        TvshowItem tvshowItem = getIntent().getParcelableExtra(EXTRA_PERSON);

        Glide.with(imgposter)
                .load("https://image.tmdb.org/t/p/w780"+tvshowItem.getPoster_path())
                .into(imgposter);
        title.setText(tvshowItem.getTitle());
        overview.setText(tvshowItem.getOverview());
        release.setText(tvshowItem.getRelease_date());

        favoritetvHelper = FavoriteHelper.getInstance(getApplicationContext());
        favoritetvHelper.open();
        favoritetvHelper.queryByAll();
        buttonadd = findViewById(R.id.buttonadd);
        buttonadd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonadd){
            tvshowItem = getIntent().getParcelableExtra(EXTRA_PERSON);
            int id = tvshowItem.getId();
            String titlem = tvshowItem.getTitle();
            String overview = tvshowItem.getOverview();
            String release = tvshowItem.getRelease_date();
            String poster = tvshowItem.getPoster_path();

            ContentValues contentValuesTv = new ContentValues();
            Log.d("_hsg",poster);
            contentValuesTv.put(_ID,id);
            contentValuesTv.put(TITLE,titlem);
            contentValuesTv.put(OVERVIEW,overview);
            contentValuesTv.put(DATE,release);
            contentValuesTv.put(POSTER,poster);
            contentValuesTv.put(FAV,"fav");
            long result = favoritetvHelper.insert(contentValuesTv);
            Log.d("_DATA",contentValuesTv.get(_ID).toString());

            if (result > 0) {
                Toast.makeText(this, R.string.success_insert, Toast.LENGTH_LONG).show();
            }else if(result < 0){
                Toast.makeText(this, R.string.hasbeen_insert_Tv, Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this, R.string.failde_insert, Toast.LENGTH_LONG).show();
            }
        }
    }
}
