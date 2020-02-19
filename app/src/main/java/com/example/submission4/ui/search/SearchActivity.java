package com.example.submission4.ui.search;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.submission4.R;
import com.google.android.material.tabs.TabLayout;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        SectionPagerAdapter sectionPagerAdapter = new SectionPagerAdapter(this,getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionPagerAdapter);
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        getSupportActionBar().setElevation(0);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_movie_white_24dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_tv_white_24dp);

    }
}
