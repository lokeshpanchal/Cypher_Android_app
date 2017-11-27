package com.helio.silentsecret.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.helio.silentsecret.EncryptionDecryption.CryptLib;
import com.helio.silentsecret.R;
import com.helio.silentsecret.connection.IfriendRequest;
import com.helio.silentsecret.models.MeetingDetail;
import com.helio.silentsecret.utils.AppSession;
import com.helio.silentsecret.utils.CommonFunction;
import com.helio.silentsecret.utils.Constants;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

import static com.helio.silentsecret.connection.IfriendRequest.toStringArray;
import static com.helio.silentsecret.utils.CommonFunction.ChangeDateFormat;

public class Mediator_meeting extends AppCompatActivity {

    TextView back_iv = null, prev_back = null, view_back = null , edit_meeting = null;

    TextView meeting_title = null, meeting_location = null, meeting_date_time = null;
    RelativeLayout prev_meet_layout = null;
    LinearLayout pre_meeting = null, new_meeting = null, view_meeting = null, prev_main_layout = null, bullet_view_main_layout = null;
    RelativeLayout progress_bar = null;
    TextView new_meeting_textview = null;
    Context ct = this;

    public static boolean is_from_create_meeting = false;
    int emogies_icon_array[] = {R.drawable.ic_scared, R.drawable.create_fml, R.drawable.ic_sad, R.drawable.create_lol,
            R.drawable.create_lonely, R.drawable.ic_happy, R.drawable.create_greatful, R.drawable.create_frustated,
            R.drawable.ic_love, R.drawable.ic_angry, R.drawable.ic_ashamed, R.drawable.create_anxious};


    List<String> emotion_name_list = new ArrayList<>();
    RelativeLayout view_meet_layout = null;
    boolean is_from_view = false;
    TextView emoji_text = null;
    List<MeetingDetail> meetingDetail = new ArrayList<>();
    public  static MeetingDetail meetingDetailObject = null;
    public static List<String> pre_meeting_title = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mediator_meeting);

        back_iv = (TextView) findViewById(R.id.back_iv);
        meeting_title = (TextView) findViewById(R.id.meeting_title);
        meeting_location = (TextView) findViewById(R.id.meeting_location);
        meeting_date_time = (TextView) findViewById(R.id.meeting_date_time);
        view_back = (TextView) findViewById(R.id.view_back);
        prev_back = (TextView) findViewById(R.id.prev_back);
        emoji_text = (TextView) findViewById(R.id.emoji_text);
        edit_meeting = (TextView) findViewById(R.id.edit_meeting);
        new_meeting_textview = (TextView) findViewById(R.id.new_meeting_textview);
        prev_meet_layout = (RelativeLayout) findViewById(R.id.prev_meet_layout);
        view_meet_layout = (RelativeLayout) findViewById(R.id.view_meet_layout);
        progress_bar = (RelativeLayout) findViewById(R.id.progress_bar);
        pre_meeting = (LinearLayout) findViewById(R.id.pre_meeting);
        new_meeting = (LinearLayout) findViewById(R.id.new_meeting);
        view_meeting = (LinearLayout) findViewById(R.id.view_meeting);
        bullet_view_main_layout = (LinearLayout) findViewById(R.id.bullet_view_main_layout);
        prev_main_layout = (LinearLayout) findViewById(R.id.prev_main_layout);

        for (int i = 0; i < Constants.emotion_name_array.length; i++) {
            emotion_name_list.add(Constants.emotion_name_array[i]);
        }
        pre_meeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prev_meet_layout.setVisibility(View.VISIBLE);
                if (meetingDetail.size() > 0)
                    showPrevMeeting();
                else {
                    prev_main_layout.removeAllViews();

                    TextView textView = new TextView(ct);

                    int width = CommonFunction.getScreenWidth();
                    int height = CommonFunction.getScreenHeight();
                    LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(width, width / 2 + width / 4);
                    textView.setLayoutParams(llp);
                    textView.setGravity(Gravity.BOTTOM | Gravity.CENTER);
                    textView.setText("No previous meeting.");
                    textView.setTextSize(18);
                    textView.setTextColor(Color.parseColor("#000000"));
                    // textView.setBackgroundColor(Color.parseColor("#ffffff"));
                    prev_main_layout.addView(textView);
                }
            }
        });

        prev_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prev_meet_layout.setVisibility(View.GONE);
            }
        });
        edit_meeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ct, EditMeeting.class);
                startActivity(intent);
            }
        });

        view_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view_meet_layout.setVisibility(View.GONE);
            }
        });

        new_meeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SetPrev_meet_title();
                Intent intent = new Intent(ct, PrepareMeeting.class);
                startActivity(intent);

            }
        });

        view_meeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (meetingDetail != null && meetingDetail.size() > 0) {
                    prev_meet_layout.setVisibility(View.VISIBLE);
                    // view_meet_layout.setVisibility(View.VISIBLE);
                    showCurrentMeeting();
                }
            }
        });
        back_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        is_from_create_meeting = false;
    }


    private void showCurrentMeeting() {
        prev_main_layout.removeAllViews();
        int width = CommonFunction.getScreenWidth();
        for (int i = 0; i < meetingDetail.size(); i++)
        {


            String currentdate_time = CommonFunction.getCurrentdateTime();

            int date_diff = CommonFunction.Getdatediff(meetingDetail.get(i).getClmtngtime01(), currentdate_time);

            if (date_diff >= 0)
            {


                LinearLayout main_linear = new LinearLayout(ct);
                LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.WRAP_CONTENT);
                //llp.setMargins(0,width/60,0,width/60);
                main_linear.setOrientation(LinearLayout.HORIZONTAL);
                main_linear.setLayoutParams(llp);
                main_linear.setId(i + 1);
                main_linear.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        int index = v.getId() - 1;
                        view_meet_layout.setVisibility(View.VISIBLE);
                        meetingDetailObject = meetingDetail.get(index);
                        ViewmeetingDetail(meetingDetailObject);
                    }
                });

                llp = new LinearLayout.LayoutParams(width, width / 50);
                TextView margin2 = new TextView(ct);
                margin2.setLayoutParams(llp);

                prev_main_layout.addView(margin2);


                llp = new LinearLayout.LayoutParams(width / 5, width / 4 + width / 30);
                LinearLayout dates_leanir = new LinearLayout(ct);
                dates_leanir.setLayoutParams(llp);
                dates_leanir.setOrientation(LinearLayout.VERTICAL);
                dates_leanir.setBackgroundResource(R.drawable.white_left_corner);


                llp = new LinearLayout.LayoutParams(width / 5, width / 12);
                TextView margin1 = new TextView(ct);
                margin1.setLayoutParams(llp);
                dates_leanir.addView(margin1);

                llp = new LinearLayout.LayoutParams(width / 5, LinearLayout.LayoutParams.WRAP_CONTENT);
                TextView dates = new TextView(ct);
                dates.setLayoutParams(llp);

                String dd_mm_yy = CommonFunction.ChangeDateFormat(meetingDetail.get(i).getClmtngtime01());
                dates.setText(CommonFunction.GetDDMM(dd_mm_yy));
                dates.setTextSize(20);
                dates.setTextColor(Color.parseColor("#3daaa4"));
                dates.setGravity(Gravity.CENTER);

                llp = new LinearLayout.LayoutParams(width / 5, LinearLayout.LayoutParams.WRAP_CONTENT);
                TextView last_edited = new TextView(ct);
                last_edited.setLayoutParams(llp);

                last_edited.setText("");
                last_edited.setTextSize(12);
                last_edited.setGravity(Gravity.CENTER);
                last_edited.setTextColor(Color.parseColor("#3daaa4"));

                dates_leanir.addView(dates);
                dates_leanir.addView(last_edited);

                llp = new LinearLayout.LayoutParams(width / 2 + width / 4, width / 4 + width / 30);
                TextView meeting_title = new TextView(ct);
                meeting_title.setLayoutParams(llp);

                meeting_title.setText(meetingDetail.get(i).getClmtngtitle01());
                meeting_title.setTextSize(15);
                meeting_title.setTextColor(Color.parseColor("#ffffff"));
                meeting_title.setGravity(Gravity.CENTER);
                meeting_title.setBackgroundResource(R.drawable.glimpse_button);

                main_linear.addView(dates_leanir);
                main_linear.addView(meeting_title);

                prev_main_layout.addView(main_linear);
                llp = new LinearLayout.LayoutParams(width, width / 50);
                TextView margin = new TextView(ct);
                margin.setLayoutParams(llp);

                prev_main_layout.addView(margin);
            }
        }
    }

    private void showPrevMeeting() {
        prev_main_layout.removeAllViews();
        int width = CommonFunction.getScreenWidth();
        for (int i = 0; i < meetingDetail.size(); i++) {


            String currentdate_time = CommonFunction.getCurrentdateTime();

            int date_diff = CommonFunction.Getdatediff(meetingDetail.get(i).getClmtngtime01(), currentdate_time);

            if (date_diff < 0) {


                LinearLayout main_linear = new LinearLayout(ct);
                LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.WRAP_CONTENT);
                //llp.setMargins(0,width/60,0,width/60);
                main_linear.setOrientation(LinearLayout.HORIZONTAL);
                main_linear.setLayoutParams(llp);
                main_linear.setId(i + 1);
                main_linear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int index = v.getId() - 1;
                        view_meet_layout.setVisibility(View.VISIBLE);
                        ViewmeetingDetail(meetingDetail.get(index));
                    }
                });

                llp = new LinearLayout.LayoutParams(width, width / 50);
                TextView margin2 = new TextView(ct);
                margin2.setLayoutParams(llp);

                prev_main_layout.addView(margin2);


                llp = new LinearLayout.LayoutParams(width / 5, width / 4 + width / 30);
                LinearLayout dates_leanir = new LinearLayout(ct);
                dates_leanir.setLayoutParams(llp);
                dates_leanir.setOrientation(LinearLayout.VERTICAL);
                dates_leanir.setBackgroundResource(R.drawable.white_left_corner);


                llp = new LinearLayout.LayoutParams(width / 5, width / 12);
                TextView margin1 = new TextView(ct);
                margin1.setLayoutParams(llp);
                dates_leanir.addView(margin1);

                llp = new LinearLayout.LayoutParams(width / 5, LinearLayout.LayoutParams.WRAP_CONTENT);
                TextView dates = new TextView(ct);
                dates.setLayoutParams(llp);

                String dd_mm_yy = CommonFunction.ChangeDateFormat(meetingDetail.get(i).getClmtngtime01());
                dates.setText(CommonFunction.GetDDMM(dd_mm_yy));
                dates.setTextSize(20);
                dates.setTextColor(Color.parseColor("#3daaa4"));
                dates.setGravity(Gravity.CENTER);

                llp = new LinearLayout.LayoutParams(width / 5, LinearLayout.LayoutParams.WRAP_CONTENT);
                TextView last_edited = new TextView(ct);
                last_edited.setLayoutParams(llp);

                last_edited.setText("");
                last_edited.setTextSize(12);
                last_edited.setGravity(Gravity.CENTER);
                last_edited.setTextColor(Color.parseColor("#3daaa4"));

                dates_leanir.addView(dates);
                dates_leanir.addView(last_edited);

                llp = new LinearLayout.LayoutParams(width / 2 + width / 4, width / 4 + width / 30);
                TextView meeting_title = new TextView(ct);
                meeting_title.setLayoutParams(llp);

                meeting_title.setText(meetingDetail.get(i).getClmtngtitle01());
                meeting_title.setTextSize(15);
                meeting_title.setTextColor(Color.parseColor("#ffffff"));
                meeting_title.setGravity(Gravity.CENTER);
                meeting_title.setBackgroundResource(R.drawable.glimpse_button);

                main_linear.addView(dates_leanir);
                main_linear.addView(meeting_title);

                prev_main_layout.addView(main_linear);
                llp = new LinearLayout.LayoutParams(width, width / 50);
                TextView margin = new TextView(ct);
                margin.setLayoutParams(llp);

                prev_main_layout.addView(margin);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        new GetPrevMeeting().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private class GetPrevMeeting extends AsyncTask<String, String, Bitmap> {

        android.app.ProgressDialog pDialog;

        String data = "0";
        Bitmap bitmap;


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
                String mediat0r_name = AppSession.getValue(ct, Constants.MEDIATOR_SUPPORT_WORKER_NAME);
                mJsonObjectSub.put("clmediatorun01", mediat0r_name);
                mJsonObjectSub.put("clun01", MainActivity.enc_username);

                requestdata.put("apikey", "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R");
                requestdata.put("data", mJsonObjectSub);
                requestdata.put("requestType", "getUserMeeting");

                main_object.put("requestData", requestdata);
                try {
                    response = httpRequest.UpdateUserFirstName(main_object.toString());
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
                    String status = "", agency_unq_id = "", created_by_user_id = "", created_by_user_un = "";
                    if (response != null) {
                        try {
                            Object object = new JSONTokener(response).nextValue();
                            if (object instanceof JSONObject) {
                                JSONObject jsonObject = new JSONObject(response);

                                if (jsonObject.has("status"))
                                    status = jsonObject.getString("status");

                                if (status != null && status.equalsIgnoreCase("true")) {
                                    meetingDetail.clear();
                                    String userinfo = "";

                                    if (jsonObject.has("data"))
                                        userinfo = jsonObject.getString("data");

                                    JSONArray json_array = null;
                                    if (jsonObject.has("data"))
                                        json_array = jsonObject.getJSONArray("data");

                                    for (int i = 0; i < json_array.length(); i++) {

                                        String lat = "";
                                        String lang = "";
                                        String clmtngtime01 = "";
                                        String clmtngtitle01 = "";
                                        String clmediatorun01 = "";
                                        String address = "";
                                        String meeting_unq_id = "";
                                        List<String> clmtngnotemood01 = null;
                                        List<String> clmtngnote01 = null;

                                        JSONObject UserInfoobj = new JSONObject(json_array.getString(i));


                                        if (UserInfoobj.has("lat"))
                                            lat = UserInfoobj.getString("lat");
                                        if (UserInfoobj.has("long"))
                                            lang = UserInfoobj.getString("long");
                                        if (UserInfoobj.has("clmtngtime01"))
                                            clmtngtime01 = UserInfoobj.getString("clmtngtime01");


                                        if (UserInfoobj.has("clmtngtitle01")) {
                                            clmtngtitle01 = UserInfoobj.getString("clmtngtitle01");

                                            clmtngtitle01 = CryptLib.decrypt(clmtngtitle01);
                                        }
                                        if (UserInfoobj.has("clmediatorun01"))
                                            clmediatorun01 = UserInfoobj.getString("clmediatorun01");
                                        if (UserInfoobj.has("address")) {
                                            address = UserInfoobj.getString("address");
                                            address = CryptLib.decrypt(address);
                                        }

                                        if (UserInfoobj.has("meeting_unq_id"))
                                            meeting_unq_id = UserInfoobj.getString("meeting_unq_id");

                                        JSONArray keys_array = null;
                                        if (UserInfoobj.has("clmtngnotemood01"))
                                            keys_array = UserInfoobj.getJSONArray("clmtngnotemood01");

                                        String flausers1[] = toStringArray(keys_array);

                                        ArrayList<String> flagusers = new ArrayList<String>();
                                        for (int k = 0; k < flausers1.length; k++) {
                                            flagusers.add(flausers1[k]);
                                        }


                                        JSONArray keys_array1 = null;
                                        if (UserInfoobj.has("clmtngnote01"))
                                            keys_array1 = UserInfoobj.getJSONArray("clmtngnote01");

                                        String flausers2[] = toStringArray(keys_array1);
                                        ArrayList<String> flagusers1 = new ArrayList<String>();
                                        for (int k = 0; k < flausers2.length; k++) {
                                            flagusers1.add(CryptLib.decrypt(flausers2[k]));

                                        }

                                        clmtngtime01 = CommonFunction.getLocalTime(clmtngtime01);


                                        meetingDetail.add(new MeetingDetail(lat, lang, clmtngtime01, clmtngtitle01, clmediatorun01, address
                                                , meeting_unq_id, flagusers, flagusers1));
                                    }
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }


                if (meetingDetail != null && meetingDetail.size() > 0)
                {
                    CheckMeetings();
                    if (is_from_create_meeting)
                    {
                        is_from_create_meeting = false;
                        prev_meet_layout.setVisibility(View.VISIBLE);
                        showCurrentMeeting();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

    private void CheckMeetings() {

        for (int i = 0; i < meetingDetail.size(); i++) {

            String currentdate_time = CommonFunction.getDateToString(MainActivity.currentdatetime);
            int date_diff = CommonFunction.Getdatediff(meetingDetail.get(i).getClmtngtime01(), currentdate_time);

            if (date_diff >= 0) {
                view_meeting.setVisibility(View.VISIBLE);
                break;
            }
        }
    }

    @Override
    protected void onStop() {
        view_meet_layout.setVisibility(View.GONE);
        super.onStop();
    }

    private void Viewmeeting()
    {

        for (int i = 0; i < meetingDetail.size(); i++) {
            String currentdate_time = CommonFunction.getDateToString(MainActivity.currentdatetime);

            int date_diff = CommonFunction.Getdatediff(meetingDetail.get(i).getClmtngtime01(), currentdate_time);

            if (date_diff >= 0) {

                ViewmeetingDetail(meetingDetail.get(i));
                break;
            }
        }
    }

    private void SetPrev_meet_title() {
        pre_meeting_title.clear();
        for (int i = 0; i < meetingDetail.size(); i++) {
            String currentdate_time = CommonFunction.getDateToString(MainActivity.currentdatetime);

            int date_diff = CommonFunction.Getdatediff(meetingDetail.get(i).getClmtngtime01(), currentdate_time);
            if (date_diff < 0) {

                pre_meeting_title.add(meetingDetail.get(i).getClmtngtitle01());
            }
        }
    }

    private void ViewmeetingDetail(final MeetingDetail meetingDetail)
    {
        try {
            bullet_view_main_layout.removeAllViews();
            int width = CommonFunction.getScreenWidth();


            meeting_title.setText(meetingDetail.getClmtngtitle01());
            meeting_location.setText(meetingDetail.getAddress());

            meeting_date_time.setText(ChangeDateFormat(meetingDetail.getClmtngtime01()));


            for (int j = 0; j < meetingDetail.getClmtngnote01().size(); j++)
            {
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                RelativeLayout mainlayout = new RelativeLayout(this);
                mainlayout.setLayoutParams(lp);

                mainlayout.setPadding(width / 100, width / 50, width / 100, 0);

                lp = new RelativeLayout.LayoutParams(width / 25, width / 25);
                lp.setMargins(width / 100, width / 50, width / 100, 0);
                TextView dot = new TextView(this);
                dot.setLayoutParams(lp);


                dot.setBackgroundResource(R.drawable.dot);


                dot.setId(j + 1);
                mainlayout.addView(dot);


                lp = new RelativeLayout.LayoutParams(width - width / 3, RelativeLayout.LayoutParams.WRAP_CONTENT);
                TextView topic_desc = new TextView(this);
                lp.addRule(RelativeLayout.RIGHT_OF, dot.getId());
                lp.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
                lp.setMargins(width / 20, 0, width / 200, 0);
                topic_desc.setText(meetingDetail.getClmtngnote01().get(j));
                topic_desc.setLayoutParams(lp);
                topic_desc.setGravity(Gravity.LEFT);
                topic_desc.setTextColor(Color.parseColor("#000000"));
                topic_desc.setTextSize(20);
                topic_desc.setPadding(width / 100, width / 50, width / 100, 0);
                topic_desc.setId(j + 2);
                topic_desc.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/ubuntu.ttf"));

                lp = new RelativeLayout.LayoutParams(width / 12, width / 12);
                lp.addRule(RelativeLayout.RIGHT_OF, topic_desc.getId());
                lp.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
                TextView emotion_icon = new TextView(ct);
                emotion_icon.setLayoutParams(lp);
                int index = emotion_name_list.indexOf(meetingDetail.getClmtngnotemood01().get(j));
                emotion_icon.setBackgroundResource(emogies_icon_array[index]);
                emotion_icon.setId(j + 3);
                emotion_icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int index = v.getId() - 3;
                        countr = 0;
                        is_from_appear = true;

                        int index1 = emotion_name_list.indexOf(meetingDetail.getClmtngnotemood01().get(index));
                        emoji_text.setText(emotion_name_list.get(index1));
                        emoji_text.postDelayed(hide_shoe_ext, 100);

                    }
                });

                mainlayout.addView(topic_desc);
                mainlayout.addView(emotion_icon);

                bullet_view_main_layout.addView(mainlayout);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    int countr = 0;
    double alpha_value = 0.02;
    boolean is_from_appear = false;
    Runnable hide_shoe_ext = new Runnable() {
        @Override
        public void run() {
            emoji_text.removeCallbacks(hide_shoe_ext);

            if (countr < 80 && is_from_appear) {
                double curr_val = alpha_value * countr;
                float currv = (float) curr_val;
                emoji_text.setAlpha(currv);
                emoji_text.postDelayed(hide_shoe_ext, 10);
                emoji_text.setVisibility(View.VISIBLE);
                countr++;
            } else if (countr > 0) {
                is_from_appear = false;
                double curr_val = alpha_value * countr;
                float currv = (float) curr_val;
                emoji_text.setAlpha(currv);
                emoji_text.postDelayed(hide_shoe_ext, 10);
                countr--;
            } else {
                emoji_text.setVisibility(View.GONE);
            }

        }
    };
}
