package com.helio.cypher.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.helio.cypher.fragments.FilterFragment;
import com.helio.cypher.fragments.GlimpseFragment;
import com.helio.cypher.fragments.HappyFragment;
import com.helio.cypher.fragments.TrendingFragment;

public class ViewPagerMainAdapter extends FragmentStatePagerAdapter {

    private static final int COUNT = 4;
    private String tabTitles[] = {"Trending", "Glimpse", "Happy", ""};

    public ViewPagerMainAdapter(FragmentManager fm) {
        super(fm);
    }

    public Fragment getItem(int num) {

        Fragment target = null;

        switch (num) {
            case 0:
                target = new TrendingFragment();

                break;
            case 1:
                target = new GlimpseFragment();
                break;
            case 2:
                target = new HappyFragment();
                break;
            case 3:
                target = new FilterFragment();
                break;
        }

        return target;
    }

    @Override
    public int getCount() {
        return COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    public void setTabTitle(String title) {
        tabTitles[3] = title;
        notifyDataSetChanged();
    }
}