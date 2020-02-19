package com.example.submission4;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.submission4.ui.DB.FavoriteHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private FavoriteHelper favoriteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_movie, R.id.navigation_tvshow, R.id.navigation_favorite,R.id.navigation_search,R.id.navigation_alarm)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        favoriteHelper = FavoriteHelper.getInstance(getApplicationContext());
        favoriteHelper.open();
    }

        public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_change,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == R.id.action_change_settings){
            Intent languageintent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(languageintent);
        }
        return super.onOptionsItemSelected(item);
    }

}
