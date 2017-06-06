package com.helio.cypher.adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.nearby.messages.Strategy;
import com.helio.cypher.R;
import com.helio.cypher.WebserviceDTO.FindNameDTO;
import com.helio.cypher.WebserviceDTO.FindbyNameDTO;
import com.helio.cypher.WebserviceDTO.FindbynameObjectDTO;
import com.helio.cypher.WebserviceDTO.GetSecretUserInfoDTO;
import com.helio.cypher.WebserviceDTO.GetSecretUserinfoObjectDTO;
import com.helio.cypher.WebserviceDTO.HideSecretDataDTO;
import com.helio.cypher.WebserviceDTO.SetFlagSecretDTO;
import com.helio.cypher.WebserviceDTO.SetFlagUnFlagSecretDataDTO;
import com.helio.cypher.WebserviceDTO.SetflagUnflagObjectDTO;
import com.helio.cypher.activities.CommentSecretActivity;
import com.helio.cypher.activities.MainActivity;
import com.helio.cypher.activities.MoodActivity;
import com.helio.cypher.activities.SupportActivity;
import com.helio.cypher.appCounsellingDTO.CommonRequestTypeDTO;
import com.helio.cypher.appCounsellingDTO.FinalObjectDTO;
import com.helio.cypher.application.SilentSecretApplication;
import com.helio.cypher.connection.ConnectionDetector;
import com.helio.cypher.connection.IfriendRequest;
import com.helio.cypher.models.IfriendSendRequestObjectDTO;
import com.helio.cypher.models.IfriendSendRequesttDTO;
import com.helio.cypher.models.IfriendSendRequesttDataDTO;
import com.helio.cypher.models.Secret;
import com.helio.cypher.social.SocialOperations;
import com.helio.cypher.tutorial.FeedTutorial;
import com.helio.cypher.utils.AnimationUtils;
import com.helio.cypher.utils.AppSession;
import com.helio.cypher.utils.Constants;
import com.helio.cypher.utils.ImageLoaderUtil;
import com.helio.cypher.utils.Preference;
import com.helio.cypher.utils.ToastUtil;
import com.helio.cypher.utils.Utils;
import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;
import com.nineoldandroids.animation.Animator;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//import com.google.android.gms.location.LocationListener;

public class FeedAdapter extends BaseAdapter {


    private static final int MUTUAL_POSITION = 2;

    private LayoutInflater inflater;
    List<Secret> mDataList;
    private List<Secret> mMe2sList = new ArrayList<>();
    private Context mContext;
    private ImageLoaderUtil mLoader;

    static ViewHolder ifholder = null;
    IfriendSendRequesttDataDTO ifriendSendRequesttDataDTO = null;

    HideSecretDataDTO hideSecretDataDTO =null;
    private boolean canShare = false;
    private boolean runShare = false;
    String newText = "";
    private String wordArray[] = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen", "twenty"};
    private Typeface mSecond = null;

    TextView friendicon;

    private SocialOperations mOperations;

    private ViewPagerMutualAdapter mutualAdapter;
    private FeedAdapter mMutualFeedAdapter;

    private String scrollTitle;
    SetFlagUnFlagSecretDataDTO setFlagUnFlagSecretDataDTO = null;
    private List<String> mViewedSecretsList = new ArrayList<>();

    private double latitude;
    private double longitude;

    private Secret secret;

    LocationManager lm;

    ViewHolder Secretholder;

    Location location;

    private boolean is_click = false;


    public FeedAdapter(LayoutInflater inflater, List<Secret> list, Context context) {
        loadData(inflater, list, context);
    }

    public FeedAdapter(LayoutInflater inflater, List<Secret> list, Context context, boolean canShare) {
        loadData(inflater, list, context);
        this.canShare = canShare;
    }

    public FeedAdapter(LayoutInflater inflater, List<Secret> list, String title, String lastText, Context context) {
        loadData(inflater, list, context);
        this.scrollTitle = title;
        mMutualFeedAdapter = new FeedAdapter(LayoutInflater.from(mContext), mMe2sList, mContext);
        mutualAdapter = new ViewPagerMutualAdapter(mMutualFeedAdapter, mContext, lastText);
    }


    private void loadData(LayoutInflater inflater, List<Secret> list, Context context) {
        try {
            this.inflater = inflater;

            if (list != null && list.size() > 0) {
                for (int k = 0; k < list.size(); k++) {
                    if (list.get(k).getText() == null || list.get(k).getText().equalsIgnoreCase("") || list.get(k).getText().equalsIgnoreCase("null")) {
                        list.remove(k);
                    }
                }
            }


            this.mDataList = list;

            if (mDataList != null && mDataList.size() > 0) {
                for (int k = 0; k < mDataList.size(); k++) {
                    if (mDataList.get(k).getText() == null || mDataList.get(k).getText().equalsIgnoreCase("") || mDataList.get(k).getText().equalsIgnoreCase("null")) {
                        mDataList.remove(k);
                    }
                }
            }

            this.mContext = context;
            this.mLoader = new ImageLoaderUtil(context);
            this.mOperations = new SocialOperations(context);
            this.mViewedSecretsList.clear();
            /*this.mViewedSecretsList.addAll(ParseUser.getCurrentUser().get(Constants.USER_SECRET_VIEWED) != null
                    ? (List<String>) ParseUser.getCurrentUser().get(Constants.USER_SECRET_VIEWED) : new ArrayList<String>());
         */
            this.mSecond = Typeface.createFromAsset(mContext.getAssets(), Constants.FONT);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getCount() {

        try {
            if (mDataList != null && mDataList.size() > 0) {
                for (int k = 0; k < mDataList.size(); k++) {
                    if (mDataList.get(k).getText() == null || mDataList.get(k).getText().equalsIgnoreCase("") || mDataList.get(k).getText().equalsIgnoreCase("null")) {
                        mDataList.remove(k);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mDataList.size();
    }

    @Override
    public Secret getItem(int position) throws IndexOutOfBoundsException {
        try {
            if (mDataList != null && mDataList.size() > 0) {
                for (int k = 0; k < mDataList.size(); k++) {
                    if (mDataList.get(k).getText() == null || mDataList.get(k).getText().equalsIgnoreCase("") || mDataList.get(k).getText().equalsIgnoreCase("null")) {
                        mDataList.remove(k);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /*private void saveInstallation() {
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.put(Constants.USER, ParseUser.getCurrentUser());


        installation.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {

                } else {

                }
            }
        });
    }*/


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
            holder.flag = (ImageView) convertView.findViewById(R.id.feed_flag);
            holder.verify = convertView.findViewById(R.id.feed_verify);

            holder.mIFriend = (TextView) convertView.findViewById(R.id.iFriend_icon);
            holder.foodcoupon = (ImageView) convertView.findViewById(R.id.foodcoupon);

            holder.supportView = convertView.findViewById(R.id.support_tutorial_view);
            holder.flagView = convertView.findViewById(R.id.flag_tutorial_view);
            holder.supportClick = convertView.findViewById(R.id.support_tutorial_ok);
            holder.flagClick = convertView.findViewById(R.id.flag_tutorial_ok);

            holder.verifyView = convertView.findViewById(R.id.verified_tutorial_view);
            holder.verifyClick = convertView.findViewById(R.id.verified_tutorial_ok);
            holder.actionsView = convertView.findViewById(R.id.feed_tutorial_view);
            holder.actionsClick = convertView.findViewById(R.id.feed_tutorial_view);
            holder.actionsText = (TextView) convertView.findViewById(R.id.tutorial_feed_item_text);
            holder.actionsTriangle = convertView.findViewById(R.id.tutorial_feed_triangle);

            holder.mainPanel = convertView.findViewById(R.id.feed_panel);
            holder.translate = convertView.findViewById(R.id.feed_translate);

            holder.hearsSocial = (TextView) convertView.findViewById(R.id.feed_total_hearts);
            holder.hugsSocial = (TextView) convertView.findViewById(R.id.feed_total_hugs);
            holder.me2Social = (TextView) convertView.findViewById(R.id.feed_total_me2);

            holder.blurView = convertView.findViewById(R.id.feed_main);

            holder.blurImage = (ImageView) convertView.findViewById(R.id.feed_blur);

            holder.share = convertView.findViewById(R.id.feed_share);

            holder.feedMutualPager = (ViewPager) convertView.findViewById(R.id.feed_mutual_view_pager);
            holder.feedMutualPerformance = (ViewPager) convertView.findViewById(R.id.feed_mutual_performance);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.feedMutualPager.setVisibility(View.GONE);
        holder.feedMutualPerformance.setVisibility(View.GONE);
        holder.feedMutualPerformance.setAdapter(null);
        holder.feedMutualPager.setAdapter(null);

        secret = getItem(position);

        if (position == MUTUAL_POSITION && mMe2sList.size() > 1 && secret.isMutual()) {
            setupMe2Logic(holder.feedMutualPager, holder.feedMutualPerformance);
            return convertView;
        } else {
            holder.feedMutualPager.setVisibility(View.GONE);
            holder.feedMutualPerformance.setVisibility(View.GONE);
        }


        if (secret.getDoowapplink() != null && !secret.getDoowapplink().equalsIgnoreCase("")) {
            newText = secret.getText();

            try {


                try {
                    if (secret.getText() != null && secret.getText().contains("iFriend"))

                    {


                        for (int j = 1; j <= 20; j++) {

                            if (secret.getText().contains("iFriend" + wordArray[j - 1])) {
                                newText = newText.replace("iFriend" + wordArray[j - 1], "iFriend" + j);

                            }
                        }


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String lyrics = newText;

                String splitarra[] = lyrics.split("\"");

                if (splitarra != null && splitarra.length > 1) {
                    String underlinetext = splitarra[1];

                    SpannableString content = new SpannableString(underlinetext);
                    content.setSpan(new UnderlineSpan(), 0, underlinetext.length(), 0);

                    holder.text.setText(splitarra[0]);
                    holder.text.append(content);

                    if (splitarra.length > 2)
                        holder.text.append(splitarra[2]);


                } else {
                    SpannableString content = new SpannableString(lyrics);
                    content.setSpan(new UnderlineSpan(), 0, lyrics.length(), 0);


                    holder.text.setText(content);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            newText = secret.getText();

            try {
                if (secret.getText() != null && secret.getText().contains("iFriend"))

                {


                    for (int j = 1; j <= 20; j++) {

                        if (secret.getText().contains("iFriend" + wordArray[j - 1])) {
                            newText = newText.replace("iFriend" + wordArray[j - 1], "iFriend" + j);

                        }
                    }


                    holder.text.setText(newText);


                } else {
                    holder.text.setText(secret.getText() != null ? secret.getText() : "");

                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }


        //holder.text.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/domuun.ttf"));



        /*else
        {
            holder.text.setText(secret.getText() != null ? secret.getText() : "");
            holder.mDooWappFeed.setVisibility(View.GONE);
        }*/

/*

        holder.hugs.setText(String.valueOf(secret.getHugs()));
        holder.hearts.setText(String.valueOf(secret.getHearts()));
*/


        /*try {
            if (secret.getUserFilled() != null) {

                String hugs = String.valueOf(secret.getUserFilled().getInt(Constants.USER_HUGS));
                String hearts = String.valueOf(secret.getUserFilled().getInt(Constants.USER_HEARTS));
                String me2s = String.valueOf(secret.getUserFilled().getInt(Constants.USER_ME2S));

                if (hugs.length() > 3) {
                    hugs = hugs.charAt(0) + Constants.POINT + hugs.charAt(1) + Constants.THOUTHAND;
                }

                if (hearts.length() > 3) {
                    hearts = hearts.charAt(0) + Constants.POINT + hearts.charAt(1) + Constants.THOUTHAND;
                }

                if (me2s.length() > 3) {
                    me2s = me2s.charAt(0) + Constants.POINT + me2s.charAt(1) + Constants.THOUTHAND;
                }

                holder.hugsSocial.setText(hugs);
                holder.hearsSocial.setText(hearts);


                try {
                    int m2count = Integer.parseInt(me2s);
                    if (m2count <= 0)
                        holder.me2Social.setText("0");
                    else
                        holder.me2Social.setText(me2s);


                } catch (Exception e) {
                    holder.me2Social.setText("0");
                    e.printStackTrace();
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/


        try {
            int m2count = secret.getHugs();
            if (m2count <= 0)
                holder.hugs.setText("0");
            else
                holder.hugs.setText("" + m2count);


        } catch (Exception e) {

            holder.hugs.setText("0");
            e.printStackTrace();
        }


        try {
            int m2count = secret.getHearts();
            if (m2count <= 0)
                holder.hearts.setText("0");
            else
                holder.hearts.setText("" + m2count);


        } catch (Exception e) {

            holder.hearts.setText("0");
            e.printStackTrace();
        }


        try {
            int m2count = secret.getMe2s();
            if (m2count <= 0)
                holder.me2s.setText("0");
            else
                holder.me2s.setText("" + m2count);


        } catch (Exception e) {

            holder.me2s.setText("0");
            e.printStackTrace();
        }

        int comments_scratch = 0;
        try {
            MainActivity.comments_count  = Integer.parseInt(MainActivity.petAvtarInfoDTO.getComments_count());
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (MainActivity.comments_count > 0)
            comments_scratch = MainActivity.comments_count ;


        int scratch_count = 0;

        try {
            scratch_count = Integer.parseInt(MainActivity.petAvtarInfoDTO.getScratch_count());
        } catch (Exception e) {
            e.printStackTrace();
        }

        String pet_name = AppSession.getValue(mContext, Constants.USER_PET_NAME);
        if (pet_name != null && !pet_name.equalsIgnoreCase("") && !pet_name.equalsIgnoreCase("null")) {

            if (secret.getShowCoupon() == 0 && scratch_count < 5 + comments_scratch) {
                try {
                    holder.foodcoupon.setVisibility(View.VISIBLE);

                    if (MainActivity.scratchscretid != null && MainActivity.scratchscretid.size() > 0) {
                        for (int j = 0; j < MainActivity.scratchscretid.size(); j++) {
                            if (secret.getObjectId().equalsIgnoreCase(MainActivity.scratchscretid.get(j))) {
                                holder.foodcoupon.setVisibility(View.GONE);
                                break;
                            }
                        }
                    }


                } catch (Exception e) {
                    e.printStackTrace();

                }


            } else
                holder.foodcoupon.setVisibility(View.GONE);
        } else
            holder.foodcoupon.setVisibility(View.GONE);

        holder.foodcoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // mDataList.get(position).setShowCoupon(2);

                holder.foodcoupon.setVisibility(View.GONE);

                MainActivity.showscratchview(mDataList.get(position).getObjectId());

            }
        });


        holder.flag.setTag(0);
        holder.flag.setVisibility(View.INVISIBLE);
        holder.mainPanel.setVisibility(View.INVISIBLE);
        holder.mainPanel.setBackgroundColor(mContext.getResources().getColor(android.R.color.transparent));
        holder.translate.setVisibility(View.INVISIBLE);
        holder.share.setVisibility(View.INVISIBLE);

        holder.supportView.setVisibility(View.INVISIBLE);
        holder.flagView.setVisibility(View.INVISIBLE);
        holder.actionsView.setVisibility(View.INVISIBLE);


        if (secret.getBgImageName() != null) {
            mLoader.loadSimpleDraw(secret.getBgImageName(), holder.root);
        }
        try {


            if (secret.getFont() != 2)
            {
                String bgImageName = secret.getBgImageName();
                bgImageName = bgImageName.replaceAll("[^\\d]", "");
                if (bgImageName != null && bgImageName.equalsIgnoreCase("26")) {
                    holder.text.setTextAppearance(mContext, R.style.BlackShadowText);
                } else {
                    if (Utils.checkBackgroundImage(secret.getBgImageName())) {
                        holder.text.setTextAppearance(mContext, R.style.ShadowText);
                    } else {
                        holder.text.setTextAppearance(mContext, R.style.ShadowText);
                    }
                }
            } else {
                String bgImageName = secret.getBgImageName();
                bgImageName = bgImageName.replaceAll("[^\\d]", "");
                if (bgImageName != null && bgImageName.equalsIgnoreCase("26")) {
                    holder.text.setTextAppearance(mContext, R.style.BlackShadowText);
                } else {
                    holder.text.setTextAppearance(mContext, R.style.ShadowText);
                    holder.text.setTypeface(mSecond);
                }
            }

            if (secret.getFlagUsers() != null)
                if (secret.getFlagUsers().contains(MainActivity.enc_username)) {
                    holder.flag.setImageResource(R.drawable.ic_flagged);
                } else {
                    holder.flag.setImageResource(R.drawable.ic_flag);
                }

            if (secret.getHugUsers() != null)
                if (secret.getHugUsers().contains(MainActivity.enc_username)) {
                    holder.hug.setImageResource(R.drawable.ic_hug);
                } else {
                    holder.hug.setImageResource(R.drawable.ic_not_hug);
                }

            if (secret.getHeartUsers() != null)
                if (secret.getHeartUsers().contains(MainActivity.enc_username)) {
                    holder.heart.setImageResource(R.drawable.ic_hearted);
                } else {
                    holder.heart.setImageResource(R.drawable.ic_heart);
                }

            if (secret.getMe2Users() != null)
                if (secret.getMe2Users().contains(MainActivity.enc_username)) {
                    holder.me2.setImageResource(R.drawable.ic_me);
                } else {
                    holder.me2.setImageResource(R.drawable.ic_me_off);
                }

            if (holder.mutualTitle != null) {
                holder.mutualTitle.setText(scrollTitle);
            }

            if (!Preference.getVerifyTutorial())
                FeedTutorial.makeVerifyTutorial(holder);


            if (!secret.getCreatedByUser().equalsIgnoreCase(MainActivity.enc_username))
            {

                holder.mIFriend.setVisibility(View.VISIBLE);

                if (MainActivity.userListNamesOnlyWaiting != null && MainActivity.userListNamesOnlyWaiting.size() > 0)
                {

                    if (MainActivity.userListNamesOnlyWaiting.contains(MainActivity.enc_username) &&
                            MainActivity.userListNamesOnlyWaiting.contains(secret.getCreatedByUser()) || MainActivity.userListNamesOnlyWaiting.contains(secret.getCreatedByUser())) {

                        holder.mIFriend.setVisibility(View.GONE);
                    }
                }
            } else {
                holder.mIFriend.setVisibility(View.GONE);
            }


            /*if (SearchFragment.secret_id != null && !SearchFragment.secret_id.equalsIgnoreCase(""))
                holder.mIFriend.setVisibility(View.GONE);*/

        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.mIFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                friendicon = (TextView) v;
                friendicon.setVisibility(View.GONE);

                try {
                    ((MainActivity) mContext).showProgress();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }



                try {
                    boolean age_verify = false;
                    secret = getItem(position);

                   String age =  AppSession.getValue(mContext, Constants.USER_AGE);
                    int myAge = Integer.parseInt(age);

                    String secretAgesec = secret.getAge();
                    int secretAge = Integer.parseInt(secretAgesec);


                    if (myAge != 0 &&myAge < 18)
                    {

                        if (myAge <= (secretAge + 2) && myAge >= (secretAge - 2))
                        {
                            age_verify = true;
                        }
                    }
                    else if(myAge == 18)
                    {
                        if((secretAge >= 18) && (secretAge <= 21))
                        {
                            age_verify = true;
                        }
                    }
                    else if(myAge == 19)
                    {
                        if((secretAge >= 18) && (secretAge <= 21))
                        {
                            age_verify = true;
                        }
                    }
                    else if(myAge >= 20 && myAge <= 24)
                    {
                        if((secretAge >= 18) && (secretAge <= 25))
                        {
                            age_verify = true;
                        }
                    }
                    else if(myAge >= 25)
                    {
                        if(secretAge >= 25)
                        {
                            age_verify = true;
                        }
                    }


                    if (!ConnectionDetector.isNetworkAvailable(mContext))
                    {
                        friendicon.setVisibility(View.VISIBLE);
                        try {
                            ((MainActivity) mContext).stopProgress();
                        } catch (Exception e2)
                        {
                            e2.printStackTrace();
                        }

                        new ToastUtil(mContext, "Please check your internet connection.");
                    }
                    else if(!age_verify)
                    {
                        is_click = false;
                        new ToastUtil(mContext, "This user is not available for ifriend");
                        ((MainActivity) mContext).stopProgress();
                    }
                    else
                    {
                        if (!is_click)
                        {

                            is_click = true;

                            ifholder = holder;

                            if (MainActivity.userListNamesOnlyWaiting != null && MainActivity.userListNamesOnlyWaiting.size() > 0)
                            {

                                if (MainActivity.userListNamesOnlyWaiting.contains(MainActivity.enc_username) &&
                                        MainActivity.userListNamesOnlyWaiting.contains(secret.getCreatedByUser()) || MainActivity.userListNamesOnlyWaiting.contains(secret.getCreatedByUser()))
                                {
                                    is_click = false;
                                    ifholder.mIFriend.setVisibility(View.GONE);
                                    Alreadyfriend();
                                }
                                else
                                    new CheckVerifiedforIfriend().execute();
                            }
                            else {
                                new CheckVerifiedforIfriend().execute();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


        holder.verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ConnectionDetector.isNetworkAvailable(mContext)) {
                    new ToastUtil(mContext,"Please check your internet connection.");
                    return;
                }
                secret = getItem(position);
                new CheckFlagverified().execute();
               /* try {
                    ((MainActivity) mContext).showProgress();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }*/


                /*    String result =   AppSession.getValue(mContext, Constants.USER_FLAG);
                if (result!= null && !result.equalsIgnoreCase("true"))
                {
                    setverified();

                } else
                {
                    try
                    {
                        ((MainActivity) mContext).stopProgress();
                        Toast.makeText(mContext,"You are not prmited to comment.",Toast.LENGTH_SHORT).show();
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }

                }*/

            }
        });

        holder.hug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if (!ConnectionDetector.isNetworkAvailable(mContext)) {
                        new ToastUtil(mContext,"Please check your internet connection.");
                        return;
                    }
                    secret = getItem(position);
                    if (secret.getHugUsers() != null)
                        if (secret.getHugUsers().contains(MainActivity.enc_username)) {
                            mDataList.get(position).setHugUsers(mOperations.unHug(secret, holder));
                            startTracking(mContext.getString(R.string.analytics_Unhug));
                        } else {
                            mDataList.get(position).setHugUsers(mOperations.hug(secret, holder));
                            startTracking(mContext.getString(R.string.analytics_hug));
                            if (!Preference.getHugTutorial())
                                FeedTutorial.makeActionHugView(holder, mContext);
                            AnimationUtils.playHugAnim(Constants.ANIM_STATE_HUG, ((MainActivity) mContext));
                        }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        holder.me2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if (!ConnectionDetector.isNetworkAvailable(mContext)) {
                        new ToastUtil(mContext,"Please check your internet connection.");
                        return;
                    }

                    secret = getItem(position);
                    if (secret.getMe2Users() != null)
                        if (secret.getMe2Users().contains(MainActivity.enc_username))
                        {
                            mDataList.get(position).setMe2Users(mOperations.unMe2(secret, holder));
                            startTracking(mContext.getString(R.string.analytics_Unme2));
                        } else {
                            startTracking(mContext.getString(R.string.analytics_me2));
                            mDataList.get(position).setMe2Users(mOperations.me2(secret, holder));
                            if (!Preference.getMe2Tutorial())
                                FeedTutorial.makeActionMe2View(holder, mContext);
                            AnimationUtils.playHugAnim(Constants.ANIM_STATE_ME2, ((MainActivity) mContext));
                        }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });

        holder.heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (!ConnectionDetector.isNetworkAvailable(mContext)) {
                        new ToastUtil(mContext,"Please check your internet connection.");
                        return;
                    }
                    secret = getItem(position);
                    if (secret.getHeartUsers() != null)
                        if (secret.getHeartUsers().contains(MainActivity.enc_username)) {
                            mDataList.get(position).setHeartUsers(mOperations.unHeart(secret, holder));
                            startTracking(mContext.getString(R.string.analytics_Unlike));
                        } else {
                            mDataList.get(position).setHeartUsers(mOperations.heart(secret, holder));
                            startTracking(mContext.getString(R.string.analytics_like));
                            if (!Preference.getHeartTutorial())
                                FeedTutorial.makeActionHeartView(holder, mContext);
                            AnimationUtils.playHugAnim(Constants.ANIM_STATE_LOVE, ((MainActivity) mContext));
                        }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean check = true;


                try {
                    if ((Integer) holder.flag.getTag() == 0)
                    {

                        if (findsecretuserDTO != null)
                            findsecretuserDTO = null;

                        if (Secretholder != null)
                            Secretholder = null;

                        Secretholder = holder;

                        Secretholder.hearsSocial.setText("");
                        Secretholder.hugsSocial.setText("");
                        Secretholder.me2Social.setText("");

                        secret = getItem(position);
                        findsecretuserDTO = new FindbyNameDTO(secret.getCreatedByUser());

                        new CheckSecretUserInfo().execute();
                        makeFlagAnimOn(holder, check);
                    } else {
                        makeFlagAnimOff(holder, check);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        holder.flag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ConnectionDetector.isNetworkAvailable(mContext))
                    return;
                try
                {
                    secret = getItem(position);

                    if (!secret.getCreatedByUser().equals(MainActivity.enc_username) && secret.getFlagUsers() != null && secret.getCreatedByUser() != null)
                    {
                        if (secret.getFlagUsers().contains(MainActivity.enc_username))
                        {
                            final ArrayList<String> flagUsers = secret.getFlagUsers();
                            flagUsers.remove(MainActivity.enc_username);
                            mDataList.get(position).setFlagUsers(flagUsers);
                            holder.flag.setImageResource(R.drawable.ic_flag);

                            if(setFlagUnFlagSecretDataDTO!= null)
                                setFlagUnFlagSecretDataDTO = null;

                            setFlagUnFlagSecretDataDTO = new SetFlagUnFlagSecretDataDTO(MainActivity.enc_username,"false",secret.getObjectId());

                        } else
                        {
                            final ArrayList<String> flagUsers = secret.getFlagUsers();
                            flagUsers.add(MainActivity.enc_username);
                            mDataList.get(position).setFlagUsers(flagUsers);
                            holder.flag.setImageResource(R.drawable.ic_flagged);
                            setFlagUnFlagSecretDataDTO = new SetFlagUnFlagSecretDataDTO(MainActivity.enc_username,"true",secret.getObjectId());
                        }

                        new SetFlagUnflagSecret().execute();
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        });

        holder.support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mContext.startActivity(new Intent(mContext, SupportActivity.class));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        holder.translate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSecretDataDTO = new HideSecretDataDTO(MainActivity.enc_username,mDataList.get(position).getObjectId());
               new SetHideSecret().execute();
                mDataList.remove(position);
                notifyDataSetChanged();
                /* boolean check = true;
                secret = getItem(position);
                try {
                    check = checkForClose(position);
                } catch (Exception e) {

                }
                makeFlagAnimOff(holder, check);
                translateText(secret.getText(), holder);*/
            }
        });

        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runShare = true;
                secret = getItem(position);
                boolean check = true;

                try {
                    check = checkForClose(position);
                } catch (Exception e) {

                }
                makeFlagAnimOff(holder, check);
            }
        });


        return convertView;
    }

    private void Alreadyfriend() {

        try {

            AlertDialog.Builder alreadyMax = new AlertDialog.Builder(mContext);
            alreadyMax.setMessage("This user is already in your friend list.");

            alreadyMax.setPositiveButton(
                    "Ok, Fine",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alertMax = alreadyMax.create();
            alertMax.setCanceledOnTouchOutside(false);
            alertMax.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void myMaxAllowed() {

        try {

            AlertDialog.Builder alreadyMax = new AlertDialog.Builder(mContext);
            alreadyMax.setMessage("Maximum iFriends allowed limit reached. Please remove any existing iFriend to add new.");

            alreadyMax.setPositiveButton(
                    "Ok, Fine",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });


            AlertDialog alertMax = alreadyMax.create();
            alertMax.setCanceledOnTouchOutside(false);
            alertMax.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkForClose(int position) throws IndexOutOfBoundsException {

        boolean showPlane = true;

        if (position == 0
                && getItem(position + 1).getCreatedByUser() != null
                && getItem(position).getCreatedByUser() != null
                && getItem(position + 1).getCreatedByUser().
                equals(getItem(position).getCreatedByUser())) {
            showPlane = true;
        } else if (position != 0
                && getItem(position - 1).getCreatedByUser() != null
                && getItem(position).getCreatedByUser() != null
                && getItem(position - 1).getCreatedByUser().
                equals(getItem(position).getCreatedByUser())) {
            showPlane = false;
        }
        return showPlane;
    }

    private void translateText(final String text, final ViewHolder holder) {

        if (!ConnectionDetector.isNetworkAvailable(mContext))
            return;

        Translate.setClientId("helio");
        Translate.setClientSecret("f9jlDTAX+SVmvNC8hpfoovCcdloI2soQVdI+Dv/zfMQ=");

        new AsyncTask<String, String, String>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                try {
                    ((MainActivity) mContext).showProgress();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            protected String doInBackground(String... params) {

                String result = params[0];
                try {
                    result = Translate.execute(result, Language.ENGLISH);

                } catch (Exception e) {
                    e.printStackTrace();
                    return result;
                }
                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    ((MainActivity) mContext).stopProgress();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }

                holder.text.setText(s);
            }
        }.execute(text);

    }

    private void addViewedSecret(String id) {
        if (!mViewedSecretsList.contains(id)) {
            mViewedSecretsList.add(id);
        }

        saveViewed();
    }

    public void saveViewed() {
        if (mViewedSecretsList.size() > 0) {
           /* ParseUser user = ParseUser.getCurrentUser();
            user.put(Constants.USER_SECRET_VIEWED, mViewedSecretsList);
            user.saveInBackground();*/
        }
    }

    private void setupMe2Logic(ViewPager pager, final ViewPager perforce) {
        removeViewedItems();

        if (mMe2sList.isEmpty() || mMe2sList.size() == 1)
            return;

        pager.setVisibility(View.VISIBLE);
        pager.setAdapter(mutualAdapter);
        perforce.setVisibility(View.VISIBLE);
        perforce.setAdapter(new ViewPagerPerformanceAdapter(scrollTitle, mContext));

        perforce.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 1 && perforce != null) {
                    perforce.setVisibility(View.GONE);

                    if (mMe2sList.size() > 0) {
                        addViewedSecret(mMe2sList.get(0).getObjectId());
                    }

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
               /* if (position != mMe2sList.size() - 1 || mMe2sList.get(position).getObject() != null)
                    addViewedSecret(mMe2sList.get(position).getObjectId());*/
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void removeViewedItems() {
        if (mViewedSecretsList.isEmpty())
            return;

        List<Secret> temp = new ArrayList<>();
        for (Secret item : mMe2sList) {
            if (!mViewedSecretsList.contains(item.getObjectId())) {
                temp.add(item);
            }
        }

        mMe2sList.clear();
        mMe2sList.addAll(temp);
        temp.clear();
        mMutualFeedAdapter.notifyDataSetChanged();
        mutualAdapter.notifyDataSetChanged();
    }

    public void updateAttachment(List<Secret> list) {

        if (list != null && list.size() > 0) {
            mMe2sList.clear();
            mMe2sList.addAll(list);
            if (mMe2sList.size() > 0) {
                mMe2sList.add(new Secret());
                Secret item = new Secret();
                item.setIsMutual(true);
                if (mDataList.size() > 2) mDataList.add(2, item);
            }
            notifyAnyway();
        }

    }

    public void notifyAnyway() {
        if (mMutualFeedAdapter != null && mutualAdapter != null) {
            mMutualFeedAdapter.notifyDataSetChanged();
            mutualAdapter.notifyDataSetChanged();
        }
        notifyDataSetChanged();
    }

    private void runComments(Secret item) {
        try {
            Intent comments = new Intent(mContext, CommentSecretActivity.class);

            comments.putExtra(Constants.MAIN_STATE_KEY, Constants.MAIN_STATE_VALUE);

            Constants.secretComment = item;
            mContext.startActivity(comments);
            ((Activity) mContext).overridePendingTransition(R.anim.rotate_out, R.anim.rotate_in);

            ((MainActivity) mContext).stopProgress();
        } catch (Exception e) {
            e.printStackTrace();
            ((MainActivity) mContext).stopProgress();
        }
    }

    private void makeFlagAnimOn(final ViewHolder holder, final boolean show) {
        YoYo.with(Techniques.FadeInDown).withListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                try {
                    if (show)
                        holder.mainPanel.setBackgroundColor(mContext.getResources().getColor(R.color.mutual_back));
                } catch (Exception e) {
                }

                holder.flag.setVisibility(View.VISIBLE);
                holder.translate.setVisibility(View.VISIBLE);
                if (show)
                    holder.mainPanel.setVisibility(View.VISIBLE);

                if (canShare) {
                    holder.share.setVisibility(View.VISIBLE);
                    YoYo.with(Techniques.FadeInDown).duration(300).playOn(holder.share);
                }

                YoYo.with(Techniques.FadeInDown).duration(300).playOn(holder.translate);

                YoYo.with(Techniques.FadeIn).duration(300).playOn(holder.mainPanel);
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).duration(300).playOn(holder.flag);
        holder.flag.setTag(1);
    }

    private void makeFlagAnimOff(final ViewHolder holder, final boolean show) {
        YoYo.with(Techniques.FadeOutUp).withListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (show)
                    YoYo.with(Techniques.FadeOut).duration(300).playOn(holder.mainPanel);
                YoYo.with(Techniques.FadeOutUp).duration(300).playOn(holder.translate);
                if (canShare)
                    YoYo.with(Techniques.FadeOutUp).duration(300).playOn(holder.share);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                holder.flag.setVisibility(View.INVISIBLE);
                holder.translate.setVisibility(View.INVISIBLE);

                if (show) {
                    holder.mainPanel.setBackgroundColor(mContext.getResources().getColor(android.R.color.transparent));
                    holder.mainPanel.setVisibility(View.INVISIBLE);
                }

                FeedTutorial.clearFlagSupportFromView(holder);
                if (canShare) {
                    holder.share.setVisibility(View.INVISIBLE);
                    if (runShare) {
                        runShare = false;
                        try {
                            ((MainActivity) mContext).showProgress();
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }

                        holder.verify.setVisibility(View.INVISIBLE);
                        Utils.shareDataOfSecret(holder.blurView, mContext, 3f, new MoodActivity.ShareCallback() {
                            @Override
                            public void onShare() {
                                holder.verify.setVisibility(View.VISIBLE);
                                try {
                                    ((MainActivity) mContext).stopProgress();
                                } catch (Exception e2) {
                                    e2.printStackTrace();
                                }

                            }
                        });
                    }
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).duration(300).playOn(holder.flag);
        holder.flag.setTag(0);
    }

    public static class ViewHolder {
        public ImageView root;
        public TextView text;
        public TextView support;
        public TextView hugs;
        public TextView hearts;
        public TextView me2s;
        public ImageView flag;
        public ImageView hug;
        public ImageView heart;
        public ImageView me2;
        public View verify;
        public View supportView;
        public View flagView;
        public View supportClick;
        public View flagClick;

        public View verifyClick;
        public View verifyView;
        public ImageView foodcoupon = null;
        public View actionsView;
        public View actionsClick;
        public TextView actionsText;
        public View actionsTriangle;

        public TextView hugsSocial;
        public TextView hearsSocial;
        public TextView me2Social;

        public View translate;
        public View mainPanel;
        public View blurView;
        public ImageView blurImage;

        public View share;
        public ViewPager feedMutualPager;
        public ViewPager feedMutualPerformance;

        public TextView mutualTitle;

        public TextView mIFriend;

    }


    private class IFirendSendRequest extends android.os.AsyncTask<String, String, Bitmap> {

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


                IfriendSendRequesttDTO ifriendSendRequesttDTO = new IfriendSendRequesttDTO(ifriendSendRequesttDataDTO);
                IfriendSendRequestObjectDTO ifriendSendRequestObjectDTO = new IfriendSendRequestObjectDTO(ifriendSendRequesttDTO);


                response = http.SendfriendReuest(ifriendSendRequestObjectDTO);


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {
            try {
                is_click = false;
                ((MainActivity) mContext).stopProgress();
                if (response != null && !response.equalsIgnoreCase("") && response.equalsIgnoreCase("success")) {
                    try {

                        if (MainActivity.userListNamesOnlyWaiting != null)
                            MainActivity.userListNamesOnlyWaiting.add(secret.getCreatedByUser());

                        AlertDialog.Builder dialog1 = new AlertDialog.Builder(mContext);
                        dialog1.setTitle("iFriend");

                        dialog1.setMessage(mContext.getResources().getString(R.string.request_send));
                        dialog1.setPositiveButton(mContext.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                ifholder.mIFriend.setVisibility(View.GONE);
                            }
                        });

                        AlertDialog alert = dialog1.create();
                        alert.setCanceledOnTouchOutside(false);
                        alert.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (response != null && !response.equalsIgnoreCase("") && response.equalsIgnoreCase("Already Friend")) {
                    Alreadyfriend();
                } else {
                    try {
                        friendicon.setVisibility(View.VISIBLE);
                        myMaxAllowed();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    private void sendifrendreq(final ViewHolder holder) {

        if (secret.getCreatedByUser().equalsIgnoreCase(MainActivity.enc_username))
        {
            is_click = false;
            try {
                ((MainActivity) mContext).stopProgress();
            } catch (Exception e2) {
                e2.printStackTrace();
            }

            friendicon.setVisibility(View.GONE);
            try {
                AlertDialog.Builder ownAlert = new AlertDialog.Builder(mContext);
                ownAlert.setMessage("It's Your own Secret.");
                ownAlert.setPositiveButton(
                        "Ok, Fine",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                holder.mIFriend.setVisibility(View.GONE);
                                dialog.cancel();
                            }
                        });


                AlertDialog ownCreate = ownAlert.create();
                ownCreate.setCanceledOnTouchOutside(false);
                ownCreate.show();
            } catch (Exception e) {
                e.printStackTrace();
            }


        } else {

            lm = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
            location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);


            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 10, new android.location.LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    longitude = location.getLongitude();
                    latitude = location.getLatitude();

                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });


            if (location != null)
            {

                longitude = location.getLongitude();
                latitude = location.getLatitude();

            }
            else
            {
                try
                {
                    latitude =  Double.parseDouble(Preference.getUserLat())  ;
                    longitude =   Double.parseDouble( Preference.getUserLon()) ;

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }


            }


            try {


                AlertDialog.Builder request = new AlertDialog.Builder(mContext);
                request.setTitle("iFriend");
                request.setMessage("Would you like to add me as your Internet Friend?");
                request.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        lm = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
                        boolean gps_enabled = false;
                        boolean network_enabled = false;

                        try {
                            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
                        } catch (Exception ex) {
                        }

                        try {
                            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

                        } catch (Exception ex) {
                        }

                        if ( longitude != 0.0 && latitude != 0.0)
                        {
                            gps_enabled = true;
                            network_enabled = true;
                        }

                        if (!gps_enabled && !network_enabled)
                        {
                            is_click = false;
                            try {
                                ((MainActivity) mContext).stopProgress();
                            } catch (Exception e2) {
                                e2.printStackTrace();
                            }


                            friendicon.setVisibility(View.VISIBLE);
                            // notify user
                            try {
                                AlertDialog.Builder dialog2 = new AlertDialog.Builder(mContext);
                                dialog2.setMessage(mContext.getResources().getString(R.string.gps_network_not_enabled));
                                dialog2.setPositiveButton(mContext.getResources().getString(R.string.open_location_settings), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                        // TODO Auto-generated method stub
                                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                        mContext.startActivity(myIntent);
                                        //get gps
                                    }
                                });

                                dialog2.setNegativeButton(mContext.getString(R.string.Cancel), new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                        // TODO Auto-generated method stub


                                    }
                                });

                                AlertDialog gps = dialog2.create();
                                gps.setCanceledOnTouchOutside(false);
                                gps.show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else
                        {

                            if (longitude != 0.0 && latitude != 0.0)
                            {
                                String send_lat = "" + latitude;
                                String send_lang = "" + longitude;


                                ifriendSendRequesttDataDTO = new IfriendSendRequesttDataDTO(MainActivity.enc_username, secret.getCreatedByUser(), send_lat, send_lang);
                                new IFirendSendRequest().execute();

                            } else {
                                try {
                                    ((MainActivity) mContext).stopProgress();
                                } catch (Exception e2) {
                                    e2.printStackTrace();
                                }

                                is_click = false;
                                friendicon.setVisibility(View.VISIBLE);
                                try {


                                    AlertDialog.Builder locationnotfound = new AlertDialog.Builder(mContext);
                                    locationnotfound.setIcon(R.drawable.chat_friend);
                                    locationnotfound.setMessage("Location not found. Please try again to send internet friend request.");
                                    locationnotfound.setPositiveButton(mContext.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                                        }
                                    });

                                    AlertDialog location = locationnotfound.create();
                                    location.setCanceledOnTouchOutside(false);
                                    location.show();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        }

                    }
                });

                request.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        is_click = false;

                        try {
                            ((MainActivity) mContext).stopProgress();
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }

                        friendicon.setVisibility(View.VISIBLE);
                        // do nothing
                    }
                });
                //request.setIcon(R.drawable.chat_friend);

                AlertDialog alertrequest = request.create();
                alertrequest.setCanceledOnTouchOutside(false);
                alertrequest.show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    protected void startTracking(String path) {
        try {
            Tracker t = ((SilentSecretApplication) mContext).getTracker(
                    SilentSecretApplication.TrackerName.APP_TRACKER);
            t.setScreenName(path);
            t.send(new HitBuilders.AppViewBuilder().build());
        } catch (Exception e) {
            e.printStackTrace();
        }

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


    private class CheckVerifiedforIfriend extends android.os.AsyncTask<String, String, Bitmap> {

        ProgressDialog pDialog;
        String data = "0";
        Bitmap bitmap;

        String response = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


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

                ((MainActivity) mContext).stopProgress();

                String isverifiedornot = AppSession.getValue(mContext, Constants.USER_VERIFIED);
                if (isverifiedornot != null && isverifiedornot.equalsIgnoreCase("true"))
                {
                    MainActivity.isIAMVerified = true;

                    if (findsecretuserDTO != null)
                        findsecretuserDTO = null;

                    findsecretuserDTO = new FindbyNameDTO(secret.getCreatedByUser());
                    new CheckSecretIfriendverified().execute();


                } else {
                    is_click = false;
                    new ToastUtil(mContext,
                            mContext.getString(R.string.sorry_not_verified));
                    friendicon.setVisibility(View.VISIBLE);

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    private class CheckSecretIfriendverified extends android.os.AsyncTask<String, String, Bitmap> {

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

                ((MainActivity) mContext).stopProgress();

                if (response != null && response.equalsIgnoreCase("true"))
                    sendifrendreq(ifholder);
                else {
                    is_click = false;
                    new ToastUtil(mContext, mContext.getString(R.string.sorry_this_user));
                }


            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    private class CheckSecretUserInfo extends android.os.AsyncTask<String, String, Bitmap> {

        JSONObject userjsonObject = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ((MainActivity) mContext).showProgress();

        }

        protected Bitmap doInBackground(String... args) {
            try {


                IfriendRequest http = new IfriendRequest(mContext);

                GetSecretUserInfoDTO findNameDTO = new GetSecretUserInfoDTO(findsecretuserDTO);
                GetSecretUserinfoObjectDTO findbynameObjectDTO = new GetSecretUserinfoObjectDTO(findNameDTO);
                userjsonObject = http.GetSecretUserInfo(findbynameObjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {
            try {

                ((MainActivity) mContext).stopProgress();

                if (userjsonObject != null) {
                    Secretholder.hearsSocial.setText(userjsonObject.getString("hearts"));
                    Secretholder.hugsSocial.setText(userjsonObject.getString("hugs"));
                    Secretholder.me2Social.setText(userjsonObject.getString("me2s"));
                }
                //userjsonObject

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }




    private class SetFlagUnflagSecret extends android.os.AsyncTask<String, String, Bitmap> {

        String data = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        protected Bitmap doInBackground(String... args) {
            try {


                IfriendRequest http = new IfriendRequest(mContext);

                SetFlagSecretDTO findNameDTO = new SetFlagSecretDTO(setFlagUnFlagSecretDataDTO);
                SetflagUnflagObjectDTO findbynameObjectDTO = new SetflagUnflagObjectDTO(findNameDTO);
                data = http.SetFlagUnflag(findbynameObjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {


        }
    }



    private class SetHideSecret extends android.os.AsyncTask<String, String, Bitmap> {

        String data = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        protected Bitmap doInBackground(String... args) {
            try {


                IfriendRequest http = new IfriendRequest(mContext);

                CommonRequestTypeDTO findNameDTO = new CommonRequestTypeDTO(hideSecretDataDTO,"updateSecrethide");
                FinalObjectDTO findbynameObjectDTO = new FinalObjectDTO(findNameDTO);
                data = http.Getappointment(findbynameObjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {


        }
    }


}


