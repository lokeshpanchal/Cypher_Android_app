package com.helio.silentsecret.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.helio.silentsecret.R;

public class ViewPagerPerformanceAdapter extends PagerAdapter {

    private String titleText = null;
    private LayoutInflater inflater;
    private Context mContext;

    public ViewPagerPerformanceAdapter(String text, Context context) {
        this.inflater = LayoutInflater.from(context);
        this.titleText = text;
        this.mContext = context;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = null;

        if (position == 0) {
            view = inflater.inflate(R.layout.mutual_performance_item, container, false);
            ((TextView) view.findViewById(R.id.mutual_title_text)).setText(titleText);
        } else if (position == 1) {
            view = new View(mContext);
        }

        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((View) object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}