package com.helio.silentsecret.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.helio.silentsecret.R;
import com.helio.silentsecret.activities.SupportActivity;
import com.helio.silentsecret.connection.ConnectionDetector;
import com.helio.silentsecret.models.SupportOrganisation;
import com.helio.silentsecret.utils.ImageLoaderUtil;

import java.util.List;

public class SupportAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<SupportOrganisation> mDataList;
    private Context mContext;
    private ImageLoaderUtil mLoader;

    public SupportAdapter(LayoutInflater inflater, List<SupportOrganisation> list, Context context) {
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
    public SupportOrganisation getItem(int position) {
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
            convertView = inflater.inflate(R.layout.support_item, parent, false);

            holder.root = (ImageView) convertView.findViewById(R.id.support_item_image);
            holder.text = (TextView) convertView.findViewById(R.id.support_item_text);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final SupportOrganisation item = mDataList.get(position);

        holder.text.setText(item.getName() != null ? item.getName() : "");

        if (item.getThumb() != null && item.getThumb().contains("http") )
            new ImageLoaderUtil(mContext).loadImageAlphaCache(item.getThumb(), holder.root);
           else if (item.getThumb() != null)
            holder.root.setImageBitmap(StringToBitMap(item.getThumb()));

        YoYo.with(Techniques.BounceIn).duration(600).playOn(convertView);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ConnectionDetector.isNetworkAvailable(mContext))
                    return;

                ((SupportActivity) mContext).runOrganisation(getItem(position));
            }
        });

        return convertView;
    }


    class ViewHolder {
        ImageView root;
        TextView text;
    }


    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

}