package com.helio.cypher.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.helio.cypher.R;
import com.helio.cypher.creator.ObjectMaker;
import com.helio.cypher.models.Invite;

import java.util.List;

public class ReferFriendAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<Invite> mDataList;
    private Context mContext;

    public ReferFriendAdapter(LayoutInflater inflater, List<Invite> list, Context context) {
        this.inflater = inflater;
        mDataList = list;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Invite getItem(int position) {
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
            convertView = inflater.inflate(R.layout.refer_item, parent, false);
            holder.text = (TextView) convertView.findViewById(R.id.refer_item_name);
            holder.checkout = (ImageView) convertView.findViewById(R.id.refer_item_accept_iv);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Invite item = mDataList.get(position);

        holder.text.setText(item.getName() != null ? item.getName() : ObjectMaker.EMPTY);

        /*if (item.getInvitee() != null) {
            holder.checkout.setImageResource(R.drawable.ic_acepted);
        } else {
            holder.checkout.setImageResource(R.drawable.ic_not_accepted);
        }*/

        return convertView;
    }

    class ViewHolder {
        TextView text;
        ImageView checkout;
    }
}