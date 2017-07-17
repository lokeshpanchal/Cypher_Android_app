package com.helio.silentsecret.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.helio.silentsecret.R;
import com.helio.silentsecret.creator.ObjectMaker;
import com.helio.silentsecret.models.Feed;
import com.helio.silentsecret.utils.Constants;
import com.helio.silentsecret.utils.ImageLoaderUtil;

import java.util.List;

public class WebFeedAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<Feed> mDataList;
    private Context mContext;
    private ImageLoaderUtil mLoader;

    public WebFeedAdapter(LayoutInflater inflater, List<Feed> list, Context context) {
        this.inflater = inflater;
        mDataList = list;
        mContext = context;
        mLoader = new ImageLoaderUtil(context);
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Feed getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.feed_web_item, parent, false);

            holder.root = (ImageView) convertView.findViewById(R.id.feed_web_item_bg);
            holder.text = (TextView) convertView.findViewById(R.id.feed_web_item_title);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Feed item = mDataList.get(position);

        holder.text.setText(item.getTitle() != null ? item.getTitle() : "");

        if (item.getThumbnail().equals(ObjectMaker.EMPTY)) {
            switch (item.getSource()) {
                case Constants.YOUTUBE:
                    mLoader.loadDrawable(R.drawable.ic_youtube_bg, holder.root);
                    break;
                case Constants.VINE:
                    mLoader.loadDrawable(R.drawable.ic_vine_bg, holder.root);
                    break;
                case Constants.WEB:
                    mLoader.loadDrawable(R.drawable.ic_web_bg, holder.root);
                    break;
            }
        } else {
            mLoader.loadImageAlphaCache(item.getThumbnail(), holder.root);
            holder.root.setAlpha(0.6f);
        }

        return convertView;
    }

    class ViewHolder {
        ImageView root;
        TextView text;
    }
}