package com.helio.silentsecret.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.helio.silentsecret.fragments.IFriendsFragment;
import com.helio.silentsecret.fragments.PendingIFriendsFragment;

/**
 * Created by Maroof Ahmed Siddique on 8/2/2016.
 */
public class PagerAdapterChatFriends extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapterChatFriends(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {


            case 0:
                IFriendsFragment iFriendsFragment = new IFriendsFragment();
                return iFriendsFragment;

            case 1:
                PendingIFriendsFragment pendingIFriendsFragment = new PendingIFriendsFragment();
                return pendingIFriendsFragment;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
