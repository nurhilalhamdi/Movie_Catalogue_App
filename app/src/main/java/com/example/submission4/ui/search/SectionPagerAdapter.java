package com.example.submission4.ui.search;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class SectionPagerAdapter extends FragmentPagerAdapter {
    private final Context mcontext;
    public SectionPagerAdapter(Context context , FragmentManager fragmentManager){
        super(fragmentManager,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mcontext = context;
    }

    private final int[] TAB_TITLES = new int[]{

    };
    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0 :
                fragment = new SearchMovieFragment();
                break;

            case 1 :
                fragment = new SearchTvFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
