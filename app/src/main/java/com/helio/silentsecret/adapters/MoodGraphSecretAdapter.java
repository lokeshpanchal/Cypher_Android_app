package com.helio.silentsecret.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.helio.silentsecret.R;
import com.helio.silentsecret.WebserviceDTO.FindNameDTO;
import com.helio.silentsecret.WebserviceDTO.FindbyNameDTO;
import com.helio.silentsecret.WebserviceDTO.FindbynameObjectDTO;
import com.helio.silentsecret.activities.CommentSecretActivity;
import com.helio.silentsecret.activities.MainActivity;
import com.helio.silentsecret.activities.MoodActivity;
import com.helio.silentsecret.activities.SupportActivity;
import com.helio.silentsecret.connection.ConnectionDetector;
import com.helio.silentsecret.connection.IfriendRequest;
import com.helio.silentsecret.models.Secret;
import com.helio.silentsecret.social.SocialOperations;
import com.helio.silentsecret.utils.AppSession;
import com.helio.silentsecret.utils.Constants;
import com.helio.silentsecret.utils.ImageLoaderUtil;
import com.helio.silentsecret.utils.Preference;
import com.helio.silentsecret.utils.ToastUtil;
import com.helio.silentsecret.utils.Utils;


import java.util.List;

/**
 * Created by ABC on 10/4/2016.
 */
public class MoodGraphSecretAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<Secret> mDataList;
    private Context mContext;
    private ImageLoaderUtil mLoader;
    private Typeface mSecond;


    String newText = "";
    private String wordArray[] = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen", "twenty"};


    private Secret item;

    private Secret secret;

    private SocialOperations mOperations;

    public MoodGraphSecretAdapter(LayoutInflater inflater, List<Secret> list, Context context) {
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
            convertView = inflater.inflate(R.layout.feed_item, parent, false);

            holder.root = (ImageView) convertView.findViewById(R.id.feed_root);
            holder.text = (TextView) convertView.findViewById(R.id.feed_text);
            holder.support = (TextView) convertView.findViewById(R.id.feed_get_support);
            holder.hugs = (TextView) convertView.findViewById(R.id.feed_hugs_count);
            holder.hearts = (TextView) convertView.findViewById(R.id.feed_hearts_count);
            holder.me2s = (TextView) convertView.findViewById(R.id.feed_me2s_count);
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


        if (getItem(position).getFont() != 2) {
            if (Utils.checkBackgroundImage(getItem(position).getBgImageName())) {
                holder.text.setTextAppearance(mContext, R.style.ShadowText);
            } else {
                holder.text.setTextAppearance(mContext, R.style.NormalText);
            }
        } else {
            holder.text.setTextAppearance(mContext, R.style.NormalText);

            holder.text.setTypeface(mSecond);
        }


       // holder.text.setText(item.getText() != null ? item.getText() : "");


        try
        {

            if (item.getDoowapplink() != null && !item.getDoowapplink().equalsIgnoreCase(""))
            {
                newText = item.getText();

                try
                {
                    if (item.getText()!= null && item.getText().contains("iFriend"))
                    {



                        for (int j = 1; j <= 20; j++)
                        {

                            if (item.getText().contains("iFriend"+wordArray[j - 1]))
                            {
                                newText = newText.replace("iFriend" + wordArray[j - 1], "iFriend" + j);

                            }
                        }



                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                try
                {

                    String lyrics = item.getText();

                    String splitarra[] = lyrics.split("\"");

                    if(splitarra!= null && splitarra.length>1)
                    {
                        String underlinetext = splitarra[1];

                        SpannableString content = new SpannableString(underlinetext);
                        content.setSpan(new UnderlineSpan(), 0, underlinetext.length(), 0);

                        holder.text.setText(splitarra[0]);
                        holder.text.append(content);
                        if(splitarra.length>2)
                        holder.text.append(splitarra[2]);

                    }
                    else
                    {
                        SpannableString content = new SpannableString(lyrics);
                        content.setSpan(new UnderlineSpan(), 0, lyrics.length(), 0);


                        holder.text.setText(content);

                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            } else
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

        if (item.getBgImageName() != null)
            mLoader.loadSimpleDraw(item.getBgImageName(), holder.root);

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
                ((MoodActivity) mContext).showProgress();
                if (!ConnectionDetector.isNetworkAvailable(mContext))
                    return;


                secret = getItem(position);
                new CheckFlagverified().execute();

                /*new FlaggedReviewLoader(new VerifyCallback() {
                    @Override
                    public void onUpdate(boolean result) {
                        if (!result) {

                            new VerifyReviewLoader(new VerifyCallback() {
                                @Override
                                public void onUpdate(boolean result) {
                                    ((MoodActivity) mContext).stopProgress();

                                    if (result) {
                                        runComments(getItem(position));
                                    } else {
                                        new ToastUtil(mContext, mContext.getString(R.string.sorry_not_verified));
                                    }
                                }
                            }).execute(ParseUser.getCurrentUser().getObjectId());
                        }
                    }
                }).execute();*/
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
            ((MainActivity) mContext).showProgress();

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



                String result = AppSession.getValue(mContext, Constants.USER_FLAG);
                if (result != null && !result.equalsIgnoreCase("true"))
                {
                    String isverifiedornot = AppSession.getValue(mContext, Constants.USER_VERIFIED);

                    if (isverifiedornot != null && isverifiedornot.equalsIgnoreCase("true")) {

                        MainActivity.isIAMVerified = true;

                        if (findsecretuserDTO != null)
                            findsecretuserDTO = null;

                        findsecretuserDTO = new FindbyNameDTO(secret.getCreatedByUser());
                        new CheckSecretFlagverified().execute();

                    } else {
                        ((MainActivity) mContext).stopProgress();
                        MainActivity.isIAMVerified = false;
                        new ToastUtil(mContext, mContext.getString(R.string.sorry_not_verified));
                    }


                } else {
                    try {
                        ((MainActivity) mContext).stopProgress();
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


    private class CheckSecretFlagverified extends android.os.AsyncTask<String, String, Bitmap> {

        ProgressDialog pDialog;
        String data = "0";
        Bitmap bitmap;

        String response = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ((MainActivity) mContext).showProgress();

        }

        protected Bitmap doInBackground(String... args) {
            try {


                IfriendRequest http = new IfriendRequest(mContext);

                FindNameDTO findNameDTO = new FindNameDTO(Constants.ENCRYPT_USER_TABLE, Constants.ENCRYPT_FIND_METHOD, findsecretuserDTO);
                FindbynameObjectDTO findbynameObjectDTO = new FindbynameObjectDTO(findNameDTO);
                response = http.Getsecretflagverified(findbynameObjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {
            try {



                if (response != null && response.equalsIgnoreCase("true"))
                {

                    if (secret.getFlags() >= 3 && secret.getCreatedByUser().equals(Preference.getUserName()))
                    {
                        ((MainActivity) mContext).stopProgress();
                        new ToastUtil(mContext, mContext.getString(R.string.comments_are_disabled));
                        return;
                    }
                    runComments(secret);


                } else {
                    ((MainActivity) mContext).stopProgress();
                    new ToastUtil(mContext, mContext.getString(R.string.sorry_this_user));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

}
