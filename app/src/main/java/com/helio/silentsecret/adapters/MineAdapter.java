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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.helio.silentsecret.R;
import com.helio.silentsecret.WebserviceDTO.FindNameDTO;
import com.helio.silentsecret.WebserviceDTO.FindbyNameDTO;
import com.helio.silentsecret.WebserviceDTO.FindbynameObjectDTO;
import com.helio.silentsecret.WebserviceDTO.SetSecretNotificationReadDataDTO;
import com.helio.silentsecret.activities.CommentSecretActivity;
import com.helio.silentsecret.activities.MainActivity;
import com.helio.silentsecret.activities.MySecretsActivity;
import com.helio.silentsecret.activities.SupportActivity;
import com.helio.silentsecret.appCounsellingDTO.CommonRequestTypeDTO;
import com.helio.silentsecret.appCounsellingDTO.FinalObjectDTO;
import com.helio.silentsecret.connection.ConnectionDetector;
import com.helio.silentsecret.connection.IfriendRequest;
import com.helio.silentsecret.models.FlickerImagload;
import com.helio.silentsecret.models.Secret;
import com.helio.silentsecret.utils.AppSession;
import com.helio.silentsecret.utils.Constants;
import com.helio.silentsecret.utils.ImageLoaderUtil;
import com.helio.silentsecret.utils.Preference;
import com.helio.silentsecret.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class MineAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<Secret> mDataList;
    private Context mContext;
    private ImageLoaderUtil mLoader;
    private boolean showEnvelope;
    private boolean isDeleteMode = false;
    private Typeface mSecond;
    private boolean showVerify = true;
    private Secret item;
    private Secret  secret;
    List<FlickerImagload> flickerImagloads = new ArrayList<>();
SetSecretNotificationReadDataDTO setSecretNotificationReadDataDTO = null;
    String newText = "";
    private String wordArray[] = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen", "twenty"};



    public MineAdapter(LayoutInflater inflater, List<Secret> list, Context context, boolean onDeleteMode) {
        this.inflater = inflater;
        mDataList = list;
        mContext = context;
        mLoader = new ImageLoaderUtil(context);
        isDeleteMode = onDeleteMode;
        this.mSecond = Typeface.createFromAsset(mContext.getAssets(), Constants.FONT);
    }

    public void setShowEnvelope(boolean flag) {
        showEnvelope = flag;
    }

    public void setDeleteMode(boolean flag) {
        isDeleteMode = flag;
    }

    public void setVerifyMode(boolean flag) {
        showVerify = flag;
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
            holder.iFriend_icon = (TextView) convertView.findViewById(R.id.iFriend_icon);
            holder.hugs = (TextView) convertView.findViewById(R.id.feed_hugs_count);
            holder.hearts = (TextView) convertView.findViewById(R.id.feed_hearts_count);
            holder.me2s = (TextView) convertView.findViewById(R.id.feed_me2s_count);
            holder.hug = (ImageView) convertView.findViewById(R.id.feed_hug);
            holder.heart = (ImageView) convertView.findViewById(R.id.feed_heart);
            holder.me2 = (ImageView) convertView.findViewById(R.id.feed_me2);
            holder.verify = convertView.findViewById(R.id.feed_verify);
            holder.envelope = (ImageView) convertView.findViewById(R.id.feed_envelope);
            holder.delete = (CheckBox) convertView.findViewById(R.id.feed_delete_checkbox);

            //holder.text.setId(position+1);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.iFriend_icon.setVisibility(View.GONE);
        if(position <mDataList.size())
            item = mDataList.get(position);
        else
        return convertView;
/*

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
*/


      /*  if (getItem(position).getFont() != 2)
        {
            String bgImageName = getItem(position).getBgImageName();
            bgImageName = bgImageName.replaceAll("[^\\d]", "");
            if(bgImageName!= null && bgImageName.equalsIgnoreCase("26"))
            {
                holder.text.setTextAppearance(mContext, R.style.BlackShadowText);
            }
            else {
                if (Utils.checkBackgroundImage(getItem(position).getBgImageName())) {
                    holder.text.setTextAppearance(mContext, R.style.ShadowText);
                } else {
                    holder.text.setTextAppearance(mContext, R.style.ShadowText);
                }
            }
        } else {
            String bgImageName = getItem(position).getBgImageName();
            bgImageName = bgImageName.replaceAll("[^\\d]", "");
            if(bgImageName!= null && bgImageName.equalsIgnoreCase("26"))
            {
                holder.text.setTextAppearance(mContext, R.style.BlackShadowText);
            }
            else {
                holder.text.setTextAppearance(mContext, R.style.ShadowText);
                holder.text.setTypeface(mSecond);
            }
        }
*/



        holder.text.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/ubuntu.ttf"));

           // holder.text.setText(item.getText() != null ? item.getText() : "");

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









       /* holder.hugs.setText(String.valueOf(item.getHugs()));
        holder.hearts.setText(String.valueOf(item.getHearts()));*/


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

      //  holder.me2s.setText(String.valueOf(item.getMe2s()));

        final String img_url = item.getFlicker_image();
        if (img_url != null && !img_url.isEmpty())
        {
            boolean is_download = false;
            if (flickerImagloads.size() > 0)
            {
                for (int i = 0; i < flickerImagloads.size(); i++) {
                    if (img_url.equalsIgnoreCase(flickerImagloads.get(i).getFliker_image_url()))
                    {
                        is_download = true;
                        holder.root.setImageBitmap(flickerImagloads.get(i).getFliker_image_bitmap());
                        break;
                    }

                }
            }

            if (!is_download) {
                Glide.with(mContext).load(item.getFlicker_image()).asBitmap().into(new SimpleTarget<Bitmap>(200, 200)
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

        if (showEnvelope)
        {
            if (item.isHasNotification())
            {
                holder.envelope.setVisibility(View.VISIBLE);
                if (item.isReadNotification())
                {
                    holder.envelope.setImageResource(R.drawable.ic_envelope_open);
                } else {
                    holder.envelope.setImageResource(R.drawable.ic_envelope);
                }

                holder.envelope.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        if (!ConnectionDetector.isNetworkAvailable(mContext))
                            return;

                        item =  mDataList.get(position);

                        if (!item.isReadNotification())
                        {
                            item.setReadNotification(true);
                            holder.envelope.setImageResource(R.drawable.ic_envelope_open);

                            setSecretNotificationReadDataDTO = new SetSecretNotificationReadDataDTO(item.getObjectId());
                          new SetNotificationRead().execute();
                        }

                        ((MySecretsActivity) mContext).openEnvelope(item);
                    }
                });
            } else
            {
                holder.envelope.setVisibility(View.GONE);
            }
        } else {
            holder.envelope.setVisibility(View.GONE);
        }

        if (showVerify) {
            holder.verify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!ConnectionDetector.isNetworkAvailable(mContext))
                        return;

                    secret = getItem(position);
                    new CheckFlagverified().execute();
                    /*new FlaggedReviewLoader(new VerifyCallback() {
                        @Override
                        public void onUpdate(boolean result) {
                            if (!result) {
                                ((MySecretsActivity) mContext).showProgress();
                                new VerifyReviewLoader(new VerifyCallback() {
                                    @Override
                                    public void onUpdate(boolean result) {
                                        ((MySecretsActivity) mContext).stopProgress();

                                        if (result) {
                                            if (getItem(position).getUserFilled() != null) {

                                                if (!getItem(position).getUserFilled().getBoolean(Constants.USER_IS_VERIFIED)
                                                        && !getItem(position).getUserFilled().getBoolean(Constants.USER_IS_SUPER_VERIFIED)) {
                                                    new ToastUtil(mContext, mContext.getString(R.string.sorry_this_user));
                                                } else {
                                                    if (getItem(position).getFlags() >= 3 && getItem(position).getCreatedByUser().equals(Preference.getUserName())) {
                                                        new ToastUtil(mContext, mContext.getString(R.string.comments_are_disabled));
                                                        return;
                                                    }

                                                    runComments(getItem(position));
                                                }
                                            } else {
                                                new ToastUtil(mContext, mContext.getString(R.string.not_exist));
                                            }
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
        } else {
            holder.verify.setVisibility(View.GONE);
        }


        holder.support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, SupportActivity.class));
            }
        });

        if (isDeleteMode) {
            holder.delete.setVisibility(View.VISIBLE);

            holder.delete.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    mDataList.get(position).setIsDeleted(isChecked);

                    if (isChecked) {
                        ((MySecretsActivity) mContext).addDeleteItem(mDataList.get(position).getObjectId());
                    } else {
                        ((MySecretsActivity) mContext).removeDeleteItem(mDataList.get(position).getObjectId());
                    }
                }
            });

            holder.delete.setChecked(mDataList.get(position).isDeleted());

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.delete.performClick();
                }
            });

        } else {
            holder.delete.setVisibility(View.GONE);
        }





        return convertView;
    }

   /* private void makeEnvelopeOpened(final Secret item, final ViewHolder holder)
    {
        final ParseObject biteObject = new ParseObject(Constants.SECRET_CLASS);
        biteObject.setObjectId(item.getObjectId());
        biteObject.put(Constants.SECRET_READ_NOTIFICATION, true);

        ParseACL acl = new ParseACL();
        acl.setPublicReadAccess(true);
        acl.setPublicWriteAccess(true);
        biteObject.setACL(acl);
        biteObject.saveInBackground(new SaveCallback() {

            @Override
            public void done(ParseException e) {
                if (e == null) {
                    item.setReadNotification(true);
                    holder.envelope.setImageResource(R.drawable.ic_envelope_open);
                }
            }
        });
    }*/

    private void runComments(Secret item) {
        Intent comments = new Intent(mContext, CommentSecretActivity.class);

        comments.putExtra(Constants.MY_STATE_KEY, Constants.MY_STATE_VALUE);

        Constants.secretComment = item;
        mContext.startActivity(comments);
    }

    static class ViewHolder {
        ImageView root;
        ImageView envelope;
        TextView text;
        TextView support;
        TextView iFriend_icon;
        TextView hugs;
        TextView hearts;
        TextView me2s;
        View verify;
        ImageView hug;
        ImageView heart;
        ImageView me2;
        CheckBox delete;
        RelativeLayout text_skin;
    }


    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    /*public void selectAll() {
        for (Secret item : mDataList) {
            item.setIsDeleted(true);
            ((MySecretsActivity) mContext).addDeleteItem(item.getObjectId());
        }

        notifyDataSetChanged();
    }

    public void unSelectAll() {
        for (Secret item : mDataList) {
            item.setIsDeleted(false);
            ((MySecretsActivity) mContext).removeDeleteItem(item.getObjectId());
        }

        notifyDataSetChanged();
    }

    public void deleteOwn(List<String> items) {

        List<Secret> temp = new ArrayList<>();

        for (Secret item : mDataList) {
            if (!items.contains(item.getObjectId())) {
                temp.add(item);
            }
        }

        mDataList.clear();
        mDataList.addAll(temp);

        notifyDataSetChanged();
    }*/


    private class SetNotificationRead extends android.os.AsyncTask<String, String, Bitmap> {

        android.app.ProgressDialog pDialog;
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
                  CommonRequestTypeDTO checkUserSessionDTO = new CommonRequestTypeDTO(setSecretNotificationReadDataDTO, "markRedNotification");
                FinalObjectDTO findbynameObjectDTO = new FinalObjectDTO(checkUserSessionDTO);
                response = http.Getappointment(findbynameObjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {

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
            ((MySecretsActivity) mContext).showProgress();

        }

        protected Bitmap doInBackground(String... args) {
            try {


                IfriendRequest http = new IfriendRequest(mContext);


                FindNameDTO findNameDTO = new FindNameDTO(Constants.ENCRYPT_USER_TABLE, Constants.ENCRYPT_FIND_METHOD, findsecretuserDTO);
                FindbynameObjectDTO  findbynameObjectDTO = new FindbynameObjectDTO(findNameDTO);
                response =   http.Getsecretflagverified(findbynameObjectDTO);


               /* JSONObject mJsonObjectSub = new JSONObject();
                JSONObject requestdata = new JSONObject();
                JSONObject main_object = new JSONObject();
                mJsonObjectSub.put("clun01", secret.getCreatedByUser());
                requestdata.put("apikey", "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R");
                requestdata.put("data", mJsonObjectSub);
                requestdata.put("requestType", "checkUserVerifyAndroid");

                main_object.put("requestData", requestdata);*/
                try {
                   // response = http.Getsecretflagverified(main_object.toString());

                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {
            try {

                ((MySecretsActivity) mContext).stopProgress();

                if (response != null && response.equalsIgnoreCase("true"))
                {

                    if (secret.getFlags() >= 3 && secret.getCreatedByUser().equals(Preference.getUserName()))
                    {

                        new ToastUtil(mContext, mContext.getString(R.string.comments_are_disabled));
                        return;
                    }
                    runComments(secret);


                } else {
                    ((MySecretsActivity) mContext).stopProgress();
                    new ToastUtil(mContext, mContext.getString(R.string.sorry_this_user));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

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
            ((MySecretsActivity) mContext).showProgress();

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
                        ((MySecretsActivity) mContext).stopProgress();
                        MainActivity.isIAMVerified = false;
                        new ToastUtil(mContext, mContext.getString(R.string.sorry_not_verified));
                    }


                } else {
                    try {
                        ((MySecretsActivity) mContext).stopProgress();
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