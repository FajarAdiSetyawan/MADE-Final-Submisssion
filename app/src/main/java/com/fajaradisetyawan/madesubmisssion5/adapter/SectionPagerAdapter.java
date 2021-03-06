package com.fajaradisetyawan.madesubmisssion5.adapter;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.fajaradisetyawan.madesubmisssion5.R;
import com.fajaradisetyawan.madesubmisssion5.activity.fragment.MovieFragment;
import com.fajaradisetyawan.madesubmisssion5.activity.fragment.TvShowFragment;

public class SectionPagerAdapter extends FragmentPagerAdapter {

    private final Context mContext;

    public SectionPagerAdapter(Context context, FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mContext = context;
    }

    @StringRes
    private final int[] TAB_TITLES = new int[]{
            R.string.title_text_movie,
            R.string.title_text_tv_show,
    };

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new MovieFragment();
                break;
            case 1:
                fragment = new TvShowFragment();
                break;
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 2;
    }
}
