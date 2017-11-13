package com.helio.silentsecret.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.helio.silentsecret.R;
import com.helio.silentsecret.models.DailyEmotionList;
import com.helio.silentsecret.utils.Constants;
import com.helio.silentsecret.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;

public class DailyEmotionAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<DailyEmotionList> mDataList;
    private Context mContext;

    int emogies_icon_array[] = {R.drawable.ic_scared, R.drawable.create_fml, R.drawable.ic_sad, R.drawable.create_lol,
            R.drawable.create_lonely, R.drawable.ic_happy, R.drawable.create_greatful, R.drawable.create_frustated,
            R.drawable.ic_love, R.drawable.ic_angry, R.drawable.ic_ashamed, R.drawable.create_anxious};

    List<String> emogies_name = new ArrayList<>();

    public DailyEmotionAdapter(LayoutInflater inflater, List<DailyEmotionList> list, Context context) {
        this.inflater = inflater;
        mDataList = list;
        mContext = context;

        emogies_name.clear();

        for (int i = 0; i < Constants.emotion_name_array.length; i++) {
            emogies_name.add(Constants.emotion_name_array[i]);
        }
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public DailyEmotionList getItem(int position) {
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
            convertView = inflater.inflate(R.layout.daily_emotion_item, parent, false);
            holder.emoji_text = (TextView) convertView.findViewById(R.id.emoji_text);
            holder.date_time = (TextView) convertView.findViewById(R.id.date_time);
            holder.emoji_icon = (ImageView) convertView.findViewById(R.id.emoji_icon);
            holder.root_view = (LinearLayout) convertView.findViewById(R.id.root_view);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final DailyEmotionList item = mDataList.get(position);

        int index = emogies_name.indexOf(item.getEmoji_name());
        holder.emoji_icon.setImageResource(emogies_icon_array[index]);
        holder.emoji_text.setText("feeling "+item.getEmoji_name());

        holder.date_time.setText(TimeUtil.timeCalculateForDailyEmotion(item.getCreated_at().getTime()));

        holder.root_view.setBackgroundColor(Color.parseColor(Constants.emotion_color_array[position%5]));
        // YoYo.with(Techniques.BounceIn).duration(500).playOn(convertView);


        return convertView;
    }


    class ViewHolder {
        TextView emoji_text;
        TextView date_time;
        ImageView emoji_icon;
        LinearLayout root_view;
    }


}