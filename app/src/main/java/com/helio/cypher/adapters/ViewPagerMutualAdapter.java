package com.helio.cypher.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.helio.cypher.R;

public class ViewPagerMutualAdapter extends PagerAdapter {

    private String titleText = null;
    private FeedAdapter adapter = null;
    private LayoutInflater inflater;

    public ViewPagerMutualAdapter(FeedAdapter adapter, Context context, String titleText) {
        this.adapter = adapter;
        this.inflater = LayoutInflater.from(context);
        this.titleText = titleText;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = null;
        if (position == (getCount() - 1)) {
            view = inflater.inflate(R.layout.mutual_last_item, container, false);
            ((TextView) view.findViewById(R.id.mutual_last_text)).setText(titleText);
        } else if ((position < getCount() - 1) && (position >= 0)) {
            view = adapter.getView(position, null, null);
        }

        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return adapter.getCount();
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