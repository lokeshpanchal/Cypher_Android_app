package com.helio.silentsecret.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.helio.silentsecret.EncryptionDecryption.CryptLib;
import com.helio.silentsecret.R;
import com.helio.silentsecret.adapters.DailyEmotionAdapter;
import com.helio.silentsecret.connection.IfriendRequest;
import com.helio.silentsecret.models.DailyEmotionList;
import com.helio.silentsecret.utils.AppSession;
import com.helio.silentsecret.utils.CommonFunction;
import com.helio.silentsecret.utils.Constants;
import com.helio.silentsecret.utils.Utils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class DailyEmotion extends AppCompatActivity {

    TextView back_iv = null, share_emotion = null, defualt_message = null;
    String todays_emotion = "";
    RelativeLayout progress_bar = null;

    List<DailyEmotionList> dailyEmotionLists = new ArrayList<>();
    Context ct = this;
ListView emotion_list = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_emotion);
        back_iv = (TextView) findViewById(R.id.back_iv);
        defualt_message = (TextView) findViewById(R.id.defualt_message);
        emotion_list = (ListView) findViewById(R.id.emotion_list);
        share_emotion = (TextView) findViewById(R.id.share_emotion);
        progress_bar = (RelativeLayout) findViewById(R.id.progress_bar);

        share_emotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ct, EmotionDialoagActivity.class);
                startActivity(i);

            }
        });

        back_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        new GetEmotion().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

    }


    @Override
    protected void onResume() {
        super.onResume();
        todays_emotion = AppSession.getValue(ct, Constants.TODAYS_EMOTION);
        if (todays_emotion != null && !todays_emotion.equalsIgnoreCase("")) {
            AppSession.save(ct, Constants.TODAYS_EMOTION, "");
            new SetEmotion().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    }

    private class SetEmotion extends android.os.AsyncTask<String, String, Bitmap> {


        String response = "";


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress_bar.setVisibility(View.VISIBLE);

        }

        protected Bitmap doInBackground(String... args) {
            try {


                IfriendRequest httpRequest = new IfriendRequest(ct);
                JSONObject mJsonObjectSub = new JSONObject();
                JSONObject requestdata = new JSONObject();
                JSONObject main_object = new JSONObject();
                String mediator_support_uniq_id = AppSession.getValue(ct, Constants.MEDIATOR_SUPPORT_WORKER_NAME);
                String agency_unq_id = AppSession.getValue(ct, Constants.MEDIATOR_AGENCY_ID);


                mJsonObjectSub.put("emoji", CryptLib.encrypt(todays_emotion));
                mJsonObjectSub.put("clun01", MainActivity.enc_username);
                mJsonObjectSub.put("clmediatorun01", mediator_support_uniq_id);
                mJsonObjectSub.put("agency_unq_id", agency_unq_id);


                requestdata.put("apikey", "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R");
                requestdata.put("data", mJsonObjectSub);
                requestdata.put("requestType", "addDailyEmotions");
                main_object.put("requestData", requestdata);

                try {

                    response = httpRequest.Create_room(main_object.toString());
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
                progress_bar.setVisibility(View.GONE);
                if (response != null) {
                }

                new GetEmotion().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }


    private class GetEmotion extends android.os.AsyncTask<String, String, Bitmap> {


        String response = "";


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dailyEmotionLists.clear();
            progress_bar.setVisibility(View.VISIBLE);

        }

        protected Bitmap doInBackground(String... args) {
            try {


                IfriendRequest httpRequest = new IfriendRequest(ct);
                JSONObject mJsonObjectSub = new JSONObject();
                JSONObject requestdata = new JSONObject();
                JSONObject main_object = new JSONObject();
                mJsonObjectSub.put("clun01", MainActivity.enc_username);

                requestdata.put("apikey", "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R");
                requestdata.put("data", mJsonObjectSub);
                requestdata.put("requestType", "getDailyEmotions");
                main_object.put("requestData", requestdata);

                try {

                    response = httpRequest.Create_room(main_object.toString());
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
                progress_bar.setVisibility(View.GONE);
                if (response != null) {
                    String status = "";
                    if (response != null) {
                        try {
                            Object object = new JSONTokener(response).nextValue();
                            if (object instanceof JSONObject) {
                                JSONObject jsonObject = new JSONObject(response);

                                if (jsonObject.has("status"))
                                    status = jsonObject.getString("status");

                                if (status != null && status.equalsIgnoreCase("true")) {

                                    String userinfo = "";

                                    if (jsonObject.has("data"))
                                        userinfo = jsonObject.getString("data");

                                    JSONArray json_array = null;
                                    if (jsonObject.has("data"))
                                        json_array = jsonObject.getJSONArray("data");

                                    for (int i = 0; i < json_array.length(); i++) {
                                        String emoji = "", created_at = "";
                                        Date Created_at = null;
                                        JSONObject UserInfoobj = new JSONObject(json_array.getString(i));


                                        if (UserInfoobj.has("emoji"))
                                            emoji = UserInfoobj.getString("emoji");
                                        if (UserInfoobj.has("created_at"))
                                            created_at = UserInfoobj.getString("created_at");

                                        if (UserInfoobj.has("created_at"))
                                            created_at = UserInfoobj.getString("created_at");


                                        try {
                                            Created_at = Utils.StringTodate(created_at);
                                            Created_at = getLocalTime(Created_at);
                                            //comment.setCreatedAt(Created_at);

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                        dailyEmotionLists.add(new DailyEmotionList(CryptLib.decrypt(emoji), Created_at));
                                    }
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }

                if (dailyEmotionLists != null && dailyEmotionLists.size()>0)
                {
                    defualt_message.setVisibility(View.GONE);
                    emotion_list.setAdapter(new DailyEmotionAdapter(LayoutInflater.from(ct) , dailyEmotionLists, ct));
                    hide_mood_icon();
                }
                else {
                    defualt_message.setVisibility(View.VISIBLE);
                    share_emotion.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

    private void hide_mood_icon()
    {
        boolean is_visible = false;
       try
       {

           String todays_date = CommonFunction.getDateToString(MainActivity.currentdatetime);
           for(int i=0; i <dailyEmotionLists.size(); i++)
           {
               String emoji_post_date = CommonFunction.getDateToString(dailyEmotionLists.get(i).getCreated_at());
               int date_diff = CommonFunction.Getdatediff(todays_date, emoji_post_date);

               if (date_diff == 0)
               {
                   is_visible = true;

                   break;
               }
           }
       }
       catch (Exception e)
       {
           e.printStackTrace();
       }

       if(is_visible)
           share_emotion.setVisibility(View.GONE);
        else
           share_emotion.setVisibility(View.VISIBLE);
    }







    private Calendar current;
    private long miliSeconds;
    private Date resultdate;

    private Date getLocalTime(Date myDate) {

        Date resultdate1 = null;
        try {

            current = Calendar.getInstance();


            TimeZone tzCurrent = current.getTimeZone();
            int offset = tzCurrent.getRawOffset();
            if (tzCurrent.inDaylightTime(new Date())) {
                offset = offset + tzCurrent.getDSTSavings();
            }

            Calendar current1 = Calendar.getInstance();

            current1.setTime(myDate);
            miliSeconds = current1.getTimeInMillis();
            miliSeconds = miliSeconds + offset;
            resultdate = new Date(miliSeconds);

           /* TimeZone london = TimeZone.getTimeZone("Europe/London");
            long now = resultdate.getTime();
             resultdate1 = new Date(miliSeconds - london.getOffset(now));*/

        } catch (Exception e) {
            e.printStackTrace();
        }


        return resultdate;
    }
}
