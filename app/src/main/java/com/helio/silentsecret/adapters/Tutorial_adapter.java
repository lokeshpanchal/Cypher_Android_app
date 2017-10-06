package com.helio.silentsecret.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.helio.silentsecret.R;

import java.util.List;

public class Tutorial_adapter extends PagerAdapter {
    private Context mContext;
    LayoutInflater mLayoutInflater;
    private List<String> mResources;





int tutorails[] = {R.layout.post_tutorial,R.layout.home_tutorial,R.layout.notification_tutorial,R.layout.ifriend_tutor,R.layout.like_me_hug_tutor,R.layout.getsupport_tutorial
,R.layout.share_mood_tutor,R.layout.alma_tutorial,R.layout.glimpse_tutor,R.layout.seeall_tutor,R.layout.long_tab_tutor,R.layout.happy_tutor,R.layout.trending_tutor};
    public Tutorial_adapter(Context mContext, List<String> mResources) {
        this.mContext = mContext;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mResources = mResources;

    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        View itemView  = mLayoutInflater.inflate(tutorails[position], container, false);


        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return mResources.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


}