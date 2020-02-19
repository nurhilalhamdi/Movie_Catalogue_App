package com.example.finalprovider;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import static android.provider.BaseColumns._ID;
import static com.example.finalprovider.DatabaseContract.FavoriteColumns.CONTENT_URI;
import static com.example.finalprovider.DatabaseContract.FavoriteColumns.DATE;
import static com.example.finalprovider.DatabaseContract.FavoriteColumns.FAV;
import static com.example.finalprovider.DatabaseContract.FavoriteColumns.OVERVIEW;
import static com.example.finalprovider.DatabaseContract.FavoriteColumns.POSTER;
import static com.example.finalprovider.DatabaseContract.FavoriteColumns.TITLE;

public class DetailFavoriteActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_PERSON = "extra_person";
    TextView title,overview,release;
    ImageView imgposter;
    MovieItem movieItem;
    Button buttonadd;
    Uri uri;
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

        uri = Uri.parse(CONTENT_URI + "/" + movieItem.getId());
        if (uri != null){
            Cursor cursor = getContentResolver().query(uri
            ,null
            ,null
            ,null
            ,null);

            if (cursor != null){
                movieItem = MappingHelper.mapcursorToObject(cursor);
            }
        }

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
            long result = getContentResolver().delete(uri,null,null);
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
