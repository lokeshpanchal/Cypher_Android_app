package com.helio.silentsecret.adapters;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.helio.silentsecret.R;
import com.helio.silentsecret.utils.Constants;

import java.util.HashMap;

public class SchoolRateAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context mContext;
    private HashMap<String, Float> rateMap;

    public SchoolRateAdapter(LayoutInflater inflater, Context context) {
        this.inflater = inflater;
        mContext = context;
        rateMap = new HashMap<>();
    }

    @Override
    public int getCount() {
        return Constants.rateList.length;
    }

    @Override
    public String getItem(int position) {
        return Constants.rateList[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
//        if (convertView == null) {
        holder = new ViewHolder();
        convertView = inflater.inflate(R.layout.rate_school_item, parent, false);

        holder.title = (TextView) convertView.findViewById(R.id.rate_school_tv);
        holder.rate = (RatingBar) convertView.findViewById(R.id.rate_school_rate_bar);
        holder.padding = convertView.findViewById(R.id.rate_item_padding_view);

        convertView.setTag(holder);
//        }
//        else {
//            holder = (ViewHolder) convertView.getTag();
//        }

        holder.title.setText(getItem(position));

        holder.rate.setRating(rateMap.containsKey(Constants.rateKeySet[position]) ? rateMap.get(Constants.rateKeySet[position]) : 0f);

        LayerDrawable stars = (LayerDrawable) holder.rate.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(mContext.getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(1).setColorFilter(mContext.getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(0).setColorFilter(mContext.getResources().getColor(R.color.grey), PorterDuff.Mode.SRC_ATOP);

        holder.rate.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                rateMap.put(Constants.rateKeySet[position], rating);
            }
        });

        if (position == Constants.rateList.length - 1) {
            holder.padding.setVisibility(View.VISIBLE);
        } else {
            holder.padding.setVisibility(View.GONE);
        }

        return convertView;
    }

    public HashMap<String, Float> retrieveRateMap() {
        return rateMap;
    }

    public void clearMap() {
        rateMap.clear();
        notifyDataSetChanged();
    }

    class ViewHolder {
        RatingBar rate;
        TextView title;
        View padding;
    }
}