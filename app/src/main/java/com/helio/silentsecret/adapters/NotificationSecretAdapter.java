package com.helio.silentsecret.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.helio.silentsecret.R;
import com.helio.silentsecret.WebserviceDTO.FindNameDTO;
import com.helio.silentsecret.WebserviceDTO.FindbyNameDTO;
import com.helio.silentsecret.WebserviceDTO.FindbynameObjectDTO;
import com.helio.silentsecret.activities.ActionSecretActivity;
import com.helio.silentsecret.activities.CommentSecretActivity;
import com.helio.silentsecret.activities.MainActivity;
import com.helio.silentsecret.activities.SupportActivity;
import com.helio.silentsecret.connection.ConnectionDetector;
import com.helio.silentsecret.connection.IfriendRequest;
import com.helio.silentsecret.models.FlickerImagload;
import com.helio.silentsecret.models.Secret;
import com.helio.silentsecret.social.SocialOperations;
import com.helio.silentsecret.utils.AppSession;
import com.helio.silentsecret.utils.Constants;
import com.helio.silentsecret.utils.ImageLoaderUtil;
import com.helio.silentsecret.utils.Preference;
import com.helio.silentsecret.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ABC on 10/4/2016.
 */
public class NotificationSecretAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<Secret> mDataList;
    private Context mContext;
    private ImageLoaderUtil mLoader;
    private Typeface mSecond;

    List<FlickerImagload> flickerImagloads = new ArrayList<>();
    String newText = "";
    private String wordArray[] = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen", "twenty"};


    private Secret item;

    private Secret secret;

    private SocialOperations mOperations;

    public NotificationSecretAdapter(LayoutInflater inflater, List<Secret> list, Context context) {
        this.inflater = inflater;
        mDataList = list;
        mContext = context;
        mLoader = new ImageLoaderUtil(context);

        this.mSecond = Typeface.createFromAsset(mContext.getAssets(), Constants.FONT);
        this.mOperations = new SocialOperations(context);
    }


    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Secret getItem(int position) {
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
            convertView = inflater.inflate(R.layout.newfeed_item, parent, false);

            holder.root = (ImageView) convertView.findViewById(R.id.feed_root);
            holder.text = (TextView) convertView.findViewById(R.id.feed_text);
            holder.support = (TextView) convertView.findViewById(R.id.feed_get_support);
            holder.hugs = (TextView) convertView.findViewById(R.id.feed_hugs_count);
            holder.hearts = (TextView) convertView.findViewById(R.id.feed_hearts_count);
            holder.me2s = (TextView) convertView.findViewById(R.id.feed_me2s_count);
            holder.iFriend_icon = (TextView) convertView.findViewById(R.id.iFriend_icon);
            holder.hug = (ImageView) convertView.findViewById(R.id.feed_hug);
            holder.heart = (ImageView) convertView.findViewById(R.id.feed_heart);
            holder.me2 = (ImageView) convertView.findViewById(R.id.feed_me2);
            holder.verify = convertView.findViewById(R.id.feed_verify);


            holder.mainPanel = convertView.findViewById(R.id.feed_panel);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        item = mDataList.get(position);
        holder.iFriend_icon.setVisibility(View.GONE);

        holder.text.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/ubuntu.ttf"));



        try
        {


                try
                {
                    if (item.getText()!= null && item.getText().contains("iFriend"))

                    {

                        newText = item.getText();

                        for (int j = 1; j <= 20; j++)
                        {

                            if (item.getText().contains("iFriend"+wordArray[j - 1]))
                            {
                                newText = newText.replace("iFriend" + wordArray[j - 1], "iFriend" + j);

                            }
                        }

                        holder.text.setText(newText);

                    } else {
                        holder.text.setText(item.getText() != null ? item.getText() : "");

                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }




           /* if (item.getDoowapplink() != null && !item.getDoowapplink().equalsIgnoreCase(""))
            {

                SpannableString content = new SpannableString(item.getDoowapplyrics());
                content.setSpan(new UnderlineSpan(), 0, item.getDoowapplyrics().length(), 0);
                holder.text.setText(content);
                holder.mDooWappFeed.setVisibility(View.VISIBLE);
            } else
            {
                holder.text.setText(item.getText() != null ? item.getText() : "");
                holder.mDooWappFeed.setVisibility(View.GONE);
            }*/
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }



        //holder.hugs.setText(String.valueOf(item.getHugs()));

        try
        {
            int m2count = item.getHugs();
            if(m2count<=0)
                holder.hugs.setText("0");
            else
                holder.hugs.setText(""+m2count);


        }
        catch (Exception e)
        {

            holder.hugs.setText("0");
            e.printStackTrace();
        }



      //  holder.hearts.setText(String.valueOf(item.getHearts()));


        try
        {
            int m2count = item.getHearts();
            if(m2count<=0)
                holder.hearts.setText("0");
            else
                holder.hearts.setText(""+m2count);


        }
        catch (Exception e)
        {

            holder.hearts.setText("0");
            e.printStackTrace();
        }


        try
        {
            int m2count = item.getMe2s();
            if(m2count<=0)
                holder.me2s.setText("0");
            else
                holder.me2s.setText(""+m2count);


        }
        catch (Exception e)
        {

            holder.me2s.setText("0");
            e.printStackTrace();
        }

       // holder.me2s.setText(String.valueOf(item.getMe2s()));


        holder.mainPanel.setVisibility(View.INVISIBLE);
        holder.mainPanel.setBackgroundColor(mContext.getResources().getColor(android.R.color.transparent));

       /* if (item.getBgImageName() != null)
            mLoader.loadSimpleDraw(item.getBgImageName(), holder.root);
*/


        final String img_url = item.getFlicker_image();
        if (img_url != null && !img_url.isEmpty())
        {
            boolean is_download = false;
            if (flickerImagloads.size() > 0)
            {
                for (int i = 0; i < flickerImagloads.size(); i++)
                {
                    if (img_url.equalsIgnoreCase(flickerImagloads.get(i).getFliker_image_url()))
                    {
                        is_download = true;
                        holder.root.setImageBitmap(flickerImagloads.get(i).getFliker_image_bitmap());

                        break;
                    }

                }
            }

            if (!is_download)
            {
                Glide.with(mContext)
                        .load(item.getFlicker_image()).asBitmap()
                        .placeholder(R.drawable.bg0)
                        .into(new SimpleTarget<Bitmap>(200, 200)
                        {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation)
                            {
                                Drawable drawable = new BitmapDrawable(resource);
                                flickerImagloads.add(new FlickerImagload(img_url, resource));
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                                {
                                    holder.root.setBackground(drawable);
                                }
                            }
                        });
            }



        } else if (item.getBgImageName() != null)
        {
            try
            {
                String url = mLoader.getBackground(item.getBgImageName());
                holder.root.setScaleType(ImageView.ScaleType.FIT_XY);
                Glide.with(mContext)
                        .load(url)
                        .into(holder.root);


            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }


        if (item.getHugUsers().contains(Preference.getUserName())) {
            holder.hug.setImageResource(R.drawable.ic_hug);
        } else {
            holder.hug.setImageResource(R.drawable.ic_not_hug);
        }

        if (item.getHeartUsers().contains(Preference.getUserName())) {
            holder.heart.setImageResource(R.drawable.ic_hearted);
        } else {
            holder.heart.setImageResource(R.drawable.ic_heart);
        }

        if (item.getMe2Users().contains(Preference.getUserName())) {
            holder.me2.setImageResource(R.drawable.ic_me);
        } else {
            holder.me2.setImageResource(R.drawable.ic_me_off);
        }


        holder.verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                item = mDataList.get(position);
               // ((MoodActivity) mContext).showProgress();
                if (!ConnectionDetector.isNetworkAvailable(mContext))
                    return;


                secret = getItem(position);
                new CheckFlagverified().execute();

            }
        });


        holder.support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mContext.startActivity(new Intent(mContext, SupportActivity.class));
            }
        });






        return convertView;
    }


    private void runComments(Secret item) {
        Intent comments = new Intent(mContext, CommentSecretActivity.class);

        comments.putExtra(Constants.MY_STATE_KEY, Constants.MY_STATE_VALUE);

        Constants.secretComment = item;
        mContext.startActivity(comments);
    }

    static class ViewHolder extends FeedAdapter.ViewHolder {
        ImageView root;
        TextView text;
        TextView support;
        TextView hugs;
        TextView hearts;
        TextView me2s;
        TextView iFriend_icon;
        View verify;
        ImageView hug;
        ImageView heart;
        ImageView me2;

        public View mainPanel;

    }

    FindbyNameDTO findsecretuserDTO = null;

    private class CheckFlagverified extends android.os.AsyncTask<String, String, Bitmap> {

        ProgressDialog pDialog;
        String data = "0";
        Bitmap bitmap;

        String response = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ((ActionSecretActivity) mContext).showprogress();

        }


        protected Bitmap doInBackground(String... args) {
            try {


                IfriendRequest http = new IfriendRequest(mContext);
                FindbyNameDTO findbyNameDTO = new FindbyNameDTO(MainActivity.enc_username);
                FindNameDTO findNameDTO = new FindNameDTO(Constants.ENCRYPT_USER_TABLE, Constants.ENCRYPT_FIND_METHOD, findbyNameDTO);
                FindbynameObjectDTO findbynameObjectDTO = new FindbynameObjectDTO(findNameDTO);
                http.Getflagverified(findbynameObjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {
            try {


                ((ActionSecretActivity) mContext).hideprogress();
                String result = AppSession.getValue(mContext, Constants.USER_FLAG);
                if (result != null && !result.equalsIgnoreCase("true"))
                {
                    String isverifiedornot = AppSession.getValue(mContext, Constants.USER_VERIFIED);

                    if (isverifiedornot != null && isverifiedornot.equalsIgnoreCase("true")) {

                        runComments(secret);


                    } else {
                        MainActivity.isIAMVerified = false;
                        new ToastUtil(mContext, mContext.getString(R.string.sorry_not_verified));
                    }


                } else {
                    try {

                        Toast.makeText(mContext, "You are not permitted to comment.", Toast.LENGTH_SHORT).show();
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

}
