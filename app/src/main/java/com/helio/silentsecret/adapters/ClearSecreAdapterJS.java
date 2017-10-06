package com.helio.silentsecret.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.helio.silentsecret.R;
import com.helio.silentsecret.WebserviceDTO.FindNameDTO;
import com.helio.silentsecret.WebserviceDTO.FindbyNameDTO;
import com.helio.silentsecret.WebserviceDTO.FindbynameObjectDTO;
import com.helio.silentsecret.WebserviceDTO.SetFlagSecretDTO;
import com.helio.silentsecret.WebserviceDTO.SetFlagUnFlagSecretDataDTO;
import com.helio.silentsecret.WebserviceDTO.SetSecretNotificationReadDataDTO;
import com.helio.silentsecret.WebserviceDTO.SetflagUnflagObjectDTO;
import com.helio.silentsecret.activities.ClearMySecretsActivity;
import com.helio.silentsecret.activities.CommentSecretActivity;
import com.helio.silentsecret.activities.MainActivity;
import com.helio.silentsecret.activities.MoodActivity;
import com.helio.silentsecret.activities.SupportActivity;
import com.helio.silentsecret.appCounsellingDTO.CommonRequestTypeDTO;
import com.helio.silentsecret.appCounsellingDTO.FinalObjectDTO;
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
import com.helio.silentsecret.utils.Utils;
import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;
import com.nineoldandroids.animation.Animator;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ClearSecreAdapterJS extends BaseAdapter {

    private LayoutInflater inflater;
    private List<Secret> mDataList;
    private Context mContext;
    private ImageLoaderUtil mLoader;
    List<FlickerImagload> flickerImagloads = new ArrayList<>();
    private boolean showEnvelope;
    private boolean isDeleteMode = false;
    private Typeface mSecond;
    private boolean showVerify = true;
    private boolean canShare = false;
    private boolean runShare = false;
    String newText = "";
    private Secret item;
    private Secret secret;
    private String wordArray[] = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen", "twenty"};
    SetSecretNotificationReadDataDTO setSecretNotificationReadDataDTO = null;
    SetFlagUnFlagSecretDataDTO setFlagUnFlagSecretDataDTO = null;
    private SocialOperations mOperations;

    public ClearSecreAdapterJS(LayoutInflater inflater, List<Secret> list, Context context, boolean onDeleteMode) {
        this.inflater = inflater;
        mDataList = list;
        mContext = context;
        mLoader = new ImageLoaderUtil(context);
        isDeleteMode = onDeleteMode;
        this.mSecond = Typeface.createFromAsset(mContext.getAssets(), Constants.FONT);
        this.mOperations = new SocialOperations(context);
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
            holder.hugs = (TextView) convertView.findViewById(R.id.feed_hugs_count);
            holder.hearts = (TextView) convertView.findViewById(R.id.feed_hearts_count);
            holder.iFriend_icon = (TextView) convertView.findViewById(R.id.iFriend_icon);
            holder.me2s = (TextView) convertView.findViewById(R.id.feed_me2s_count);
            holder.hug = (ImageView) convertView.findViewById(R.id.feed_hug);
            holder.heart = (ImageView) convertView.findViewById(R.id.feed_heart);
            holder.me2 = (ImageView) convertView.findViewById(R.id.feed_me2);
            holder.verify = convertView.findViewById(R.id.feed_verify);
            holder.envelope = (ImageView) convertView.findViewById(R.id.feed_envelope);
            holder.delete = (CheckBox) convertView.findViewById(R.id.feed_delete_checkbox);

            holder.flag = (ImageView) convertView.findViewById(R.id.feed_flag);
            holder.translate = convertView.findViewById(R.id.feed_translate);
            holder.share = convertView.findViewById(R.id.feed_share);

            holder.mainPanel = convertView.findViewById(R.id.feed_panel);
            holder.flag.setTag(0);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.iFriend_icon.setVisibility(View.GONE);
    item = mDataList.get(position);

        /*if (getItem(position).getFont() != 2) {
            if (Utils.checkBackgroundImage(getItem(position).getBgImageName())) {
                holder.text.setTextAppearance(mContext, R.style.ShadowText);
            } else {
                holder.text.setTextAppearance(mContext, R.style.NormalText);
            }
        } else {
            holder.text.setTextAppearance(mContext, R.style.NormalText);

            holder.text.setTypeface(mSecond);
        }*/


        /*if (getItem(position).getFont() != 2)
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
        } else
        {
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

        if (item.getDoowapplink() != null && !item.getDoowapplink().equalsIgnoreCase(""))
        {
            try
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


                    } else {
                        holder.text.setText(item.getText() != null ? item.getText() : "");

                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }


                String lyrics = newText;

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




        }


        //holder.text.setText(item.getText() != null ? item.getText() : "");

        holder.hugs.setText(String.valueOf(item.getHugs()));
        holder.hearts.setText(String.valueOf(item.getHearts()));
        holder.me2s.setText(String.valueOf(item.getMe2s()));

        holder.flag.setTag(0);
        holder.flag.setVisibility(View.INVISIBLE);
        holder.translate.setVisibility(View.INVISIBLE);
        holder.share.setVisibility(View.INVISIBLE);

        holder.mainPanel.setVisibility(View.INVISIBLE);
        holder.mainPanel.setBackgroundColor(mContext.getResources().getColor(android.R.color.transparent));

      /*  if (item.getBgImageName() != null)
            mLoader.loadSimpleDraw(item.getBgImageName(), holder.root);*/

        if (item.getHugUsers().contains(MainActivity.enc_username)) {
            holder.hug.setImageResource(R.drawable.ic_hug);
        } else {
            holder.hug.setImageResource(R.drawable.ic_not_hug);
        }

        if (item.getHeartUsers().contains(MainActivity.enc_username)) {
            holder.heart.setImageResource(R.drawable.ic_hearted);
        } else {
            holder.heart.setImageResource(R.drawable.ic_heart);
        }

        if (item.getMe2Users().contains(MainActivity.enc_username)) {
            holder.me2.setImageResource(R.drawable.ic_me);
        } else {
            holder.me2.setImageResource(R.drawable.ic_me_off);
        }

        if (item.getFlagUsers() != null)
            if (item.getFlagUsers().contains(Preference.getUserName())) {
                holder.flag.setImageResource(R.drawable.ic_flagged);
            } else {
                holder.flag.setImageResource(R.drawable.ic_flag);
        }


        if (showEnvelope) {
            if (item.isHasNotification()) {
                holder.envelope.setVisibility(View.VISIBLE);
                if (item.isReadNotification()) {
                    holder.envelope.setImageResource(R.drawable.ic_envelope_open);
                } else {
                    holder.envelope.setImageResource(R.drawable.ic_envelope);
                }

                holder.envelope.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!ConnectionDetector.isNetworkAvailable(mContext))
                            return;

                        if (!item.isReadNotification())
                        {
                            item.setReadNotification(true);
                            holder.envelope.setImageResource(R.drawable.ic_envelope_open);

                            setSecretNotificationReadDataDTO = new SetSecretNotificationReadDataDTO(item.getObjectId());
                            new SetNotificationRead().execute();
                        }

                        ((ClearMySecretsActivity) mContext).openEnvelope(item);
                    }
                });
            } else {
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


                }
            });
        } else {
            holder.verify.setVisibility(View.GONE);
        }



        try
        {
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
                        /*Drawable drawable = new BitmapDrawable(flickerImagloads.get(i).getFliker_image_bitmap());

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            holder.root.setBackground(drawable);
                        }*/
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
        }
        catch (Exception e)
        {

        }

        holder.support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, SupportActivity.class));
            }
        });

        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean check = true;

                try {
                    check = checkForClose(position);
                } catch (Exception e) {

                }

                if ((Integer)holder.flag.getTag() == 0) {
                    makeFlagAnimOn(holder, check);
                } else {
                    makeFlagAnimOff(holder, check);
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

        holder.translate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean check = true;

                try {
                    check = checkForClose(position);
                } catch (Exception e) {

                }
                makeFlagAnimOff(holder, check);
                translateText(item.getText(), holder);
            }
        });

        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runShare = true;
                boolean check = true;

                try {
                    check = checkForClose(position);
                } catch (Exception e) {

                }
                makeFlagAnimOff(holder, check);
            }
        });

        if (isDeleteMode) {
            holder.delete.setVisibility(View.VISIBLE);

            holder.delete.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    mDataList.get(position).setIsDeleted(isChecked);

                    if (isChecked) {
                        ((ClearMySecretsActivity) mContext).addDeleteItem(mDataList.get(position).getObjectId());
                    } else {
                        ((ClearMySecretsActivity) mContext).removeDeleteItem(mDataList.get(position).getObjectId());
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

    // social operations start

/*    public ArrayList<String> flag(final Secret item, final FeedAdapter.ViewHolder holder)
    {
        ParseObject biteObject = new ParseObject(Constants.SECRET_CLASS);
        biteObject.setObjectId(item.getObjectId());
        biteObject.put(Constants.SECRET_FLAGS, item.getFlags() + 1);
        ArrayList<String> flagUsers = item.getFlagUsers();
        flagUsers.add(Preference.getUserName());
        biteObject.put(Constants.SECRET_FLAG_USERS, flagUsers);
        ParseACL acl = new ParseACL();
        acl.setPublicReadAccess(true);
        acl.setPublicWriteAccess(true);
        biteObject.setACL(acl);

//        ((MainActivity) mContext).showProgress();
        biteObject.saveInBackground(new SaveCallback() {

            @Override
            public void done(ParseException e) {
                ((MainActivity) mContext).stopProgress();
                if (e == null) {
                    holder.flag.setImageResource(R.drawable.ic_flagged);
                    item.setFlags(item.getFlags() + 1);
                }
            }
        });

        return flagUsers;
    }*/

/*
    public ArrayList<String> unFlag(final Secret item, final FeedAdapter.ViewHolder holder) {
        final ParseObject biteObject = new ParseObject(Constants.SECRET_CLASS);
        biteObject.setObjectId(item.getObjectId());
        biteObject.put(Constants.SECRET_FLAGS, item.getFlags() - 1);
        final ArrayList<String> flagUsers = item.getFlagUsers();
        flagUsers.remove(Preference.getUserName());
        biteObject.put(Constants.SECRET_FLAG_USERS, flagUsers);
        ParseACL acl = new ParseACL();
        acl.setPublicReadAccess(true);
        acl.setPublicWriteAccess(true);
        biteObject.setACL(acl);

//        ((MainActivity) mContext).showProgress();
        biteObject.saveInBackground(new SaveCallback() {

            @Override
            public void done(ParseException e) {
                ((MainActivity) mContext).stopProgress();
                if (e == null) {
                    holder.flag.setImageResource(R.drawable.ic_flag);
                }
            }
        });

        return flagUsers;
    }
*/

    // social operations end

    private void translateText(final String text, final ViewHolder holder) {

        if (!ConnectionDetector.isNetworkAvailable(mContext))
            return;

        Translate.setClientId("helio");
        Translate.setClientSecret("f9jlDTAX+SVmvNC8hpfoovCcdloI2soQVdI+Dv/zfMQ=");

        new AsyncTask<String, String, String>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
//                ((MainActivity) mContext).showProgress();
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
//                ((MainActivity) mContext).stopProgress();
                holder.text.setText(s);
            }
        }.execute(text);

    }

/*
    private void makeEnvelopeOpened(final Secret item, final ViewHolder holder)
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
    }
*/

    private void runComments(Secret item) {
        Intent comments = new Intent(mContext, CommentSecretActivity.class);

        comments.putExtra(Constants.MY_STATE_KEY, Constants.MY_STATE_VALUE);

        Constants.secretComment = item;
        mContext.startActivity(comments);
    }

    public void selectAll() {
        try
        {
            for (Secret item : mDataList) {
                item.setIsDeleted(true);
                ((ClearMySecretsActivity) mContext).addDeleteItem(item.getObjectId());
            }

            notifyDataSetChanged();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void unSelectAll() {

        try
        {
            for (Secret item : mDataList) {
                item.setIsDeleted(false);
                ((ClearMySecretsActivity) mContext).removeDeleteItem(item.getObjectId());
            }

            notifyDataSetChanged();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void deleteOwn(List<String> items) {

        try
        {
            List<Secret> temp = new ArrayList<>();
            for (Secret item : mDataList) {
                if (!items.contains(item.getObjectId())) {
                    temp.add(item);
                }
            }

            mDataList.clear();
            mDataList.addAll(temp);

            notifyDataSetChanged();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }



    }


    @Override
    public void notifyDataSetChanged() {


        super.notifyDataSetChanged();
    }

    static class ViewHolder extends FeedAdapter.ViewHolder {
        ImageView root;
        ImageView envelope;
        TextView text;
        TextView support;
        TextView hugs;
        TextView hearts;
        TextView iFriend_icon;
        TextView me2s;
        View verify;
        ImageView hug;
        ImageView heart;
        ImageView me2;
        CheckBox delete;
        public View share;

        public View mainPanel;
        public ImageView flag;
        public View translate;
    }

    private void makeFlagAnimOn(final ViewHolder holder, final boolean show) {
        YoYo.with(Techniques.FadeInDown).withListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                try {
                    if (show) {
                        holder.mainPanel.setBackgroundColor(mContext.getResources().getColor(R.color.mutual_back));
                        holder.flag.setVisibility(View.VISIBLE);
                       // holder.translate.setVisibility(View.VISIBLE);
                        holder.mainPanel.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                }

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

//                FeedTutorial.clearFlagSupportFromView(holder);
                if (canShare) {
                    holder.share.setVisibility(View.INVISIBLE);
                    if (runShare) {
                        runShare = false;
                        ((MainActivity) mContext).showProgress();
                        holder.verify.setVisibility(View.INVISIBLE);
                        Utils.shareDataOfSecret(holder.blurView, mContext, 3f, new MoodActivity.ShareCallback() {
                            @Override
                            public void onShare() {
                                holder.verify.setVisibility(View.VISIBLE);
                                ((MainActivity) mContext).stopProgress();
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


    private class SetNotificationRead extends AsyncTask<String, String, Bitmap> {

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


    private class CheckSecretFlagverified extends AsyncTask<String, String, Bitmap> {

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

                JSONObject mJsonObjectSub = new JSONObject();
                JSONObject requestdata = new JSONObject();
                JSONObject main_object = new JSONObject();
                mJsonObjectSub.put("clun01", secret.getCreatedByUser());
                requestdata.put("apikey", "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R");
                requestdata.put("data", mJsonObjectSub);
                requestdata.put("requestType", "checkUserVerifyAndroid");

                main_object.put("requestData", requestdata);
                try {
                    response = http.Getsecretflagverified(main_object.toString());

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

    FindbyNameDTO findsecretuserDTO = null;

    private class CheckFlagverified extends AsyncTask<String, String, Bitmap> {

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


    private class SetFlagUnflagSecret extends AsyncTask<String, String, Bitmap> {

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

}