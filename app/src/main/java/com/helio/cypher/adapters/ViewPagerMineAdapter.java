package com.helio.cypher.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.helio.cypher.fragments.MineSecretsFragment;
import com.helio.cypher.utils.Constants;

public class ViewPagerMineAdapter extends FragmentStatePagerAdapter {

    private String tabTitles[] = {"My Secrets", "Hugs", "Hearts", "Me2s"};

    private static final int COUNT_NORMAL = 4;
    private static final int COUNT_CLEAR = 1;
    private int mCount = COUNT_NORMAL;
    private boolean mode = false;

    public ViewPagerMineAdapter(FragmentManager fm, boolean mode) {
        super(fm);
        this.mode = mode;
        this.mCount = mode ? COUNT_CLEAR : COUNT_NORMAL;
    }

    public Fragment getItem(int num) {
        switch (num) {
            case Constants.STATE_MINE_SECRETS:
                return MineSecretsFragment.instance(Constants.STATE_MINE_SECRETS, mode);
            case Constants.STATE_HEART_SECRETS:
                return MineSecretsFragment.instance(Constants.STATE_HEART_SECRETS, false);
            case Constants.STATE_HUGS_SECRETS:
                return MineSecretsFragment.instance(Constants.STATE_HUGS_SECRETS, false);
            case Constants.STATE_ME2S_SECRETS:
                return MineSecretsFragment.instance(Constants.STATE_ME2S_SECRETS, false);
        }
        return null;
    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}