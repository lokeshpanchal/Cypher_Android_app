package com.helio.silentsecret.activities;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.gson.Gson;
import com.helio.silentsecret.EncryptionDecryption.CryptLib;
import com.helio.silentsecret.R;
import com.helio.silentsecret.Services.NotificationService;
import com.helio.silentsecret.Services.SinchService;
import com.helio.silentsecret.WebserviceDTO.FindbyNameDTO;
import com.helio.silentsecret.adapters.CounsellingChatDetailsAdapter;
import com.helio.silentsecret.appCounsellingDTO.AppointmentAutoCancelDTO;
import com.helio.silentsecret.appCounsellingDTO.AppointmentCompleteDTO;
import com.helio.silentsecret.appCounsellingDTO.ChatCounsellingMessageDTO;
import com.helio.silentsecret.appCounsellingDTO.ChatMessageReqDTO;
import com.helio.silentsecret.appCounsellingDTO.CommonRequestTypeDTO;
import com.helio.silentsecret.appCounsellingDTO.FinalObjectDTO;
import com.helio.silentsecret.appCounsellingDTO.GetChatObjectDTO;
import com.helio.silentsecret.appCounsellingDTO.InsertRatingDTO;
import com.helio.silentsecret.appCounsellingDTO.RattingQuestionDTO;
import com.helio.silentsecret.appCounsellingDTO.SendAttandanceDataDTO;
import com.helio.silentsecret.appCounsellingDTO.SendCounsellorRatingDTO;
import com.helio.silentsecret.appCounsellingDTO.SendMessageDTO;
import com.helio.silentsecret.appCounsellingDTO.SendMessageDataDTO;
import com.helio.silentsecret.appCounsellingDTO.SendMessageObjectDTO;
import com.helio.silentsecret.appCounsellingDTO.UploadMoodgraphDataDTO;
import com.helio.silentsecret.application.SilentSecretApplication;
import com.helio.silentsecret.connection.ConnectionDetector;
import com.helio.silentsecret.connection.IfriendRequest;
import com.helio.silentsecret.fragments.GlimpseFragment;
import com.helio.silentsecret.models.ChatDetailBean;
import com.helio.silentsecret.models.GetCurrentDateObjectDTO;
import com.helio.silentsecret.models.GetCurrentDateTimeDTO;
import com.helio.silentsecret.models.GetImpactRatingDTO;
import com.helio.silentsecret.models.GetImpactRatingObjectDTO;
import com.helio.silentsecret.models.InserCallSessionDTO;
import com.helio.silentsecret.models.SendImpactRatingDTO;
import com.helio.silentsecret.models.SendImpactRatingObjectDTO;
import com.helio.silentsecret.models.SentImpactratinganswerObjectDTO;
import com.helio.silentsecret.pushnotification.GcmIntentService;
import com.helio.silentsecret.utils.AppSession;
import com.helio.silentsecret.utils.Constants;
import com.helio.silentsecret.utils.KeyboardUtil;
import com.helio.silentsecret.utils.ToastUtil;
import com.helio.silentsecret.utils.Utils;
import com.sinch.android.rtc.SinchError;
import com.sinch.android.rtc.calling.Call;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class LiveCounselling extends SinchBaseActivity implements SinchService.StartFailedListener {
    TextView cancel_button = null;
    TextView timer = null, sharemoodontimer = null, impact_timer = null;
    ImageView chatmoodshare = null, send_btn = null;
    RelativeLayout timer_layout = null, chatscreen = null;
    RelativeLayout progress_bar = null;

    SendCounsellorRatingDTO sendCounsellorRatingDTO = null;
    public static Bitmap bitmapmoodgraph = null;
    public static int total_question_count = 0;
    public static boolean mood_from_chat = false;

    private ListView mChatList = null;
    public static boolean submited_impact = false;
    public static boolean chat_running = false;
    boolean show_impact_reminder = false;
    List<String> chat_id = new ArrayList<>();
    static List<RattingQuestionDTO> rattingQuestionDTOs = null;
    int total_question = 0, current_question = 0;
    int width, height;
    boolean[] submit_array = null;
    TextView submitimpact = null;
    LinearLayout impactquestion = null, impactnotaatall = null, impactonlyoccasional = null, impactsometimes = null, impactofter = null, impactmostoff = null;


    /*   public static long remain_minute = 100,remain_second = 60;*/

    List<SendImpactRatingDTO> sendImpactRatingDTOs = null;
    static SendAttandanceDataDTO sendAttandanceDataDTO = null;
    public static String bookingtime = "";

    Date currentdatetime = null;
    int reversecounter = 0;
    boolean first_time = true;

    public static boolean is_show_rating = false;

    boolean is_from_mood = false;
    boolean is_running = false;

    InserCallSessionDTO inserCallSessionDTO = null;

    static String counsellerid = "", counsellor_booking_id = "", counsellor_booking_name = "";

    static String user_came_at = "";


    static String Counsellertime;
    public static String appointmentid = "";
    Context ct = null;
    static boolean is_taing_insert = false;
    static boolean is_procesing = false;
    boolean is_counselor_Came = false;
    static boolean is_User_Came = false;
    private boolean is_new = false;
    RelativeLayout ratingscreen = null, impactratinglayout = null;
    private EditText msg_et = null;
    private int list_size;


    public static boolean is_from_fivr_min_booking = false;

    public int timediff = -1, seconcount = -1;
    public static boolean is_show_start = false;
    public static String chat_mode = "";
    AppointmentAutoCancelDTO appointmentAutoCancelDTO = null;
    AppointmentCompleteDTO appointmentCompleteDTO = null;

    TextView ratingok = null;
    TextView rating_question = null, show_counsel_timer = null, callbutton = null;

    private CounsellingChatDetailsAdapter mChatDetailsAdapter;
    private ArrayList<ChatDetailBean> mChatArrayList = new ArrayList<ChatDetailBean>();

    private String message = "";
    private String messageFetch = "";
    private String currentSender = "";
    private String receiverFriend = "";
    public static String chat_username = "";
    RatingBar rate_counselling = null;
    public static String date;
    //Date last_updateDateTime = null;
    String ratingquestion[] = {"Do you feel accepted and comfortable during this session?",
            "How was the connection between you and your Counsellor in this session? "};
    Integer rating[] = new Integer[3];
    //int rating_counter = 0;
    public static String counsellor_firstname = "";
    public static Activity livecounsel = null;

    String colors[] = {"#fa8a40", "#61afa8"};
    int user_rating = 0;
    public static String systemDate = "";
    String Tag = "CounChat";
    static TextView attandencemessage = null;
    TextView ten_min_remain = null, book_appoint_remingder = null;

    private String dateFormat = "EEE, d MMM yyyy hh:mm aa";
    //  public static DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy hh:mm aa");
    public static DateFormat df = new SimpleDateFormat("hh:mm aa");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_counselling);
        ct = this;
        livecounsel = this;
        is_taing_insert = false;
        is_procesing = false;
        is_User_Came = false;
        is_from_fivr_min_booking = false;
        chat_running = true;
        sendAttandanceDataDTO = null;
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;

        cancel_button = (TextView) findViewById(R.id.cancel_book);
        submitimpact = (TextView) findViewById(R.id.submitimpact);
        impact_timer = (TextView) findViewById(R.id.impact_timer);
        send_btn = (ImageView) findViewById(R.id.send_btn);
        show_counsel_timer = (TextView) findViewById(R.id.show_counsel_timer);
        msg_et = (EditText) findViewById(R.id.msg_et);
        timer = (TextView) findViewById(R.id.timer);
        attandencemessage = (TextView) findViewById(R.id.attandencemessage);
        ten_min_remain = (TextView) findViewById(R.id.ten_min_remain);
        book_appoint_remingder = (TextView) findViewById(R.id.book_appoint_remingder);
        sharemoodontimer = (TextView) findViewById(R.id.sharemoodontimer);
        chatmoodshare = (ImageView) findViewById(R.id.chatmoodshare);
        timer_layout = (RelativeLayout) findViewById(R.id.timer_layout);
        chatscreen = (RelativeLayout) findViewById(R.id.chatscreen);
        ratingscreen = (RelativeLayout) findViewById(R.id.ratingscreen);
        impactratinglayout = (RelativeLayout) findViewById(R.id.impactratinglayout);
        mChatList = (ListView) findViewById(R.id.chat_list);
        rate_counselling = (RatingBar) findViewById(R.id.rate_counselling);
        ratingok = (TextView) findViewById(R.id.ratingok);
        callbutton = (TextView) findViewById(R.id.callbutton);
        rating_question = (TextView) findViewById(R.id.rating_question);
        progress_bar = (RelativeLayout) findViewById(R.id.progress_bar);


        rate_counselling.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                user_rating = (int) rating;
            }
        });


        book_appoint_remingder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                is_from_fivr_min_booking = true;

                Intent intent = new Intent(LiveCounselling.this, CounsellerBooking.class);
                startActivity(intent);
                finish();
            }
        });

        submitimpact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean is_allSubmit = true;
                for (int i = 0; i < sendImpactRatingDTOs.size(); i++) {
                    if (submit_array[i] == false) {
                        is_allSubmit = false;
                        break;
                    }

                }

                if (is_allSubmit) {
                    if (ConnectionDetector.isNetworkAvailable(ct)) {
                        // ThankYouPopup();


                        new SendImpactRating().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        ImpactRatingdone();
                        submited_impact = true;
                        AppSession.save(ct, Constants.IMPACT_REMINDER, "false");

                    } else {
                        new ToastUtil(ct, "Please check your internet connection.");
                    }

                } else
                    Toast.makeText(ct, "Please answer all questions.", Toast.LENGTH_SHORT).show();
            }
        });

        callbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makecall();
                Attandance();
                UserAttandace();
            }
        });


        startService(new Intent(getBaseContext(), NotificationService.class));

        callbutton.postDelayed(new Runnable() {
            @Override
            public void run() {
                loginClicked();
            }
        }, 1000);

        is_from_mood = true;

        new GetCurrentDateTime().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        //Getcurrenttime();

        timediff = -1;
        seconcount = -1;


        ratingok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if (user_rating > 0) {

                        if (sendCounsellorRatingDTO != null)
                            sendCounsellorRatingDTO = null;

                        sendCounsellorRatingDTO = new SendCounsellorRatingDTO("Done", MainActivity.enc_username, rattingQuestionDTOs.get(current_question).getAppointmentid(), rattingQuestionDTOs.get(current_question).getQuestionid()
                                , "Counselor"
                                , user_rating, "");

                        ratingscreen.setVisibility(View.GONE);


                        new SendRating().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);




                    } else {
                        Toast.makeText(ct, "Ratings can't be blank", Toast.LENGTH_SHORT).show();
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        ratingscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = msg_et.getText().toString().trim();
           /*     if (!is_click)
                {*/

                if (message != null && !message.equalsIgnoreCase("")) {
                    try {

                        long mseconds = System.currentTimeMillis();
                        String unique_id = MainActivity.enc_username + mseconds;

                      /*  if (sendMessageObjectDTO == null)*/
                        SendMessageDataDTO sendMessageDataDTO = new SendMessageDataDTO(MainActivity.enc_username, counsellerid, CryptLib.encrypt(message), appointmentid, unique_id);
                       /* else {
                            sendMessageObjectDTO.getRequestData().getData().setMessage(CryptLib.encrypt(message));
                            sendMessageObjectDTO.getRequestData().getData().setUnique_id(unique_id);
                        }*/

                        msg_et.setText("");

                        try {

                            SendMessageObjectDTO sendMessageObjectDTO = new SendMessageObjectDTO(new SendMessageDTO(sendMessageDataDTO));

                            sendMessageObjectDTOs.add(sendMessageObjectDTO);

                            Date d = new Date();
                            systemDate = df.format(d);
                            ChatDetailBean me = new ChatDetailBean();
                            is_User_Came = true;
                            me.setYourMsg(message);
                            me.setSentTime(systemDate);
                            me.setMine(true);
                            chat_id.add(unique_id);
                            mChatArrayList.add(me);
                            if (mChatDetailsAdapter == null) {
                                mChatDetailsAdapter = new CounsellingChatDetailsAdapter((Activity) ct, mChatArrayList);
                                mChatList.setAdapter(mChatDetailsAdapter);

                            } else {
                                mChatDetailsAdapter.notifyDataSetChanged();
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        if (user_came_at == null || user_came_at.equalsIgnoreCase("")) {
                            attandencemessage.setVisibility(View.GONE);
                            UserAttandace();
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    //  }


                }
            }
        });

        chatmoodshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mood_from_chat = true;
                sharemoodgraph();
                is_from_mood = true;

            }
        });

        sharemoodontimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mood_from_chat = false;
                sharemoodgraph();
                is_from_mood = true;

            }
        });

        mChatList.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        mChatList.setStackFromBottom(true);
        startTracking(getString(R.string.analytics_LiveCounselling));


        if (is_show_rating) {

            new CheckPendingRating().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        } else {
            if (!is_show_start)
            {

                if (submited_impact == false)
                    new GetImpactRating().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                cancel_button.postDelayed(settimer, 1000);

            } else {

                if (submited_impact == false)
                    new GetImpactRating().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                else
                {
                    try{
                        show_counsel_timer.setVisibility(View.VISIBLE);
                        if (chat_mode != null && !chat_mode.equalsIgnoreCase("Chat") && (chat_mode.equalsIgnoreCase("Audio") || chat_mode.equalsIgnoreCase("Video"))) {

                            if (chat_mode.equalsIgnoreCase("Video"))
                                callbutton.setBackgroundResource(R.drawable.video_icon);
                            callbutton.setVisibility(View.VISIBLE);

                        }

                        timer_layout.setVisibility(View.GONE);
                        chatscreen.setVisibility(View.VISIBLE);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }





                if (ConnectionDetector.isNetworkAvailable(this)) {

                    // fetchOnlyAboutMe();
                    new GetAllmessage().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    rating_question.postDelayed(checlcounsellingtime, 1000);
                }
            }
        }

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            chat_running = false;
            if (cancel_button != null) {
                cancel_button.removeCallbacks(settimer);
            }
            if (rating_question != null) {
                rating_question.removeCallbacks(checlcounsellingtime);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();

        is_running = false;
        chat_running = false;
        try {
            if (cancel_button != null) {
                cancel_button.removeCallbacks(settimer);
            }

            if (rating_question != null) {
                rating_question.removeCallbacks(checlcounsellingtime);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        is_running = true;
        chat_running = true;
        try {
            if (bitmapmoodgraph != null) {
                timediff = -1;

                new GetCurrentDateTimeonmoodshare().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                //uploadmoodgraph();
                new UploadMoodgraph().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                if (mood_from_chat) {
                    if (checlcounsellingtime != null) {
                        rating_question.postDelayed(checlcounsellingtime, 1000);
                    }
                } else {
                    if (cancel_button != null) {
                        cancel_button.postDelayed(settimer, 1000);
                    }
                }

            }

            reversecounter = 15;
            if (is_from_mood)
                is_from_mood = false;
            else {
                Intent intent = new Intent(LiveCounselling.this, CounsellerBooking.class);
                startActivity(intent);
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (MainActivity.stataicObjectDTO != null) {
                if (MainActivity.stataicObjectDTO.getiFriendSettingDTO().getSession_duration() != null && !MainActivity.stataicObjectDTO.getiFriendSettingDTO().getSession_duration().equalsIgnoreCase("")) {
                    GlimpseFragment.sesstion_time = Integer.parseInt(MainActivity.stataicObjectDTO.getiFriendSettingDTO().getSession_duration());
                }
            } else
                GlimpseFragment.sesstion_time = 45;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (GcmIntentService.remove_noti_id != 0)
            cancelNotification(this, GcmIntentService.remove_noti_id);
    }


    Runnable settimer = new Runnable() {
        @Override
        public void run() {

            try {


                try {

                    rating_question.removeCallbacks(checlcounsellingtime);
                } catch (Exception e) {

                }

                cancel_button.removeCallbacks(settimer);

                if (currentdatetime != null) {
                    if (timediff < 0)
                        setdate_time();

                    if (seconcount > 0) {

                        seconcount--;
                        cancel_button.postDelayed(settimer, 1000);
                        timer_layout.setVisibility(View.VISIBLE);
                    } else {
                        if (timediff > 0) {
                            timediff--;
                            seconcount = 59;
                            // setdate_time();
                            cancel_button.postDelayed(settimer, 1000);
                            timer_layout.setVisibility(View.VISIBLE);
                        } else {
                            seconcount = 0;
                        }

                    }

                    //   Gettimediff(CounsellerBooking.counseltme_for_timer);
                    //setdate_time();
                    if (seconcount < 10) {
                        timer.setText("0" + timediff + ":0" + seconcount);
                        impact_timer.setText("Counselling will start in " + "0" + timediff + ":0" + seconcount);
                    } else {
                        timer.setText("0" + timediff + ":" + seconcount);
                        impact_timer.setText("Counselling will start in " + "0" + timediff + ":" + seconcount);
                    }

                    if (seconcount == 0 && timediff == 0) {
                        seconcount = 59;
                        timer_layout.setVisibility(View.GONE);
                        chatscreen.setVisibility(View.VISIBLE);
                        show_counsel_timer.setVisibility(View.VISIBLE);

                        if (chat_mode != null && !chat_mode.equalsIgnoreCase("Chat") && (chat_mode.equalsIgnoreCase("Audio") || chat_mode.equalsIgnoreCase("Video"))) {

                            if (chat_mode.equalsIgnoreCase("Video"))
                                callbutton.setBackgroundResource(R.drawable.video_icon);
                            callbutton.setVisibility(View.VISIBLE);
                        }

                        rating_question.postDelayed(checlcounsellingtime, 1000);
                        // timer.setText("Start Counselling");

                        try {
                            cancel_button.removeCallbacks(settimer);
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }

                } else {
                    cancel_button.postDelayed(settimer, 1000);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };


    private void setdate_time() {

        try {
            int intHour, intMinute, second, Mins;

            String Hour, Minute, Second;
            intHour = currentdatetime.getHours();
            intMinute = currentdatetime.getMinutes();
            second = currentdatetime.getSeconds();

            seconcount = 59 - second;

            if (intHour < 10) {
                Hour = "0" + intHour;
            } else {
                Hour = "" + intHour;
            }

            if (intMinute < 10) {
                Minute = "0" + intMinute;
            } else {
                Minute = "" + intMinute;
            }

           /* if (second < 10) {
                Second = "0" + second;
            } else {
                Second = "" + second;
            }*/

            String Mytime = Hour + ":" + Minute;

           /* if (bookingtime.length() <= 5)
            {
                bookingtime = bookingtime + ":00";
            }*/

            SimpleDateFormat format = new SimpleDateFormat("HH:mm");

            Date Date2 = format.parse(Mytime);

            Date Date1 = format.parse(bookingtime);

            if (Date2.getTime() >= Date1.getTime()) {
                long differenceInMins = Math.abs(Date2.getTime() - Date1.getTime()) / 1000;
                timediff = (int) differenceInMins / 60;


            } else {
                long differenceInMins = Math.abs(Date1.getTime() - Date2.getTime()) / 1000;
                timediff = (int) differenceInMins / 60;
                timediff = timediff - 1;

            }


        } catch (Exception e) {
            e.printStackTrace();
        }



        /*Calendar cal = Calendar.getInstance();
        int intsecond = cal.get(Calendar.SECOND);
        seconcount = 59 - intsecond;
*/
    }


    private class GetAppointment extends android.os.AsyncTask<String, String, Bitmap> {

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
                IfriendRequest http = new IfriendRequest(ct);
                FindbyNameDTO findbyNameDTO = new FindbyNameDTO(MainActivity.enc_username);
                CommonRequestTypeDTO checkUserSessionDTO = new CommonRequestTypeDTO(findbyNameDTO, "getLastAppointment");
                FinalObjectDTO findbynameObjectDTO = new FinalObjectDTO(checkUserSessionDTO);
                response = http.Getappointment(findbynameObjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {
            try {

                String status = "";
                Object object = new JSONTokener(response).nextValue();
                if (object instanceof JSONObject) {
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.has("status"))
                        status = jsonObject.getString("status");
                    if (status != null && status.equalsIgnoreCase("true")) {
                        JSONArray json_array = null;
                        if (jsonObject.has("data"))
                            json_array = jsonObject.getJSONArray("data");

                        if (json_array != null && json_array.length() > 0) {

                            JSONObject UserInfoobj = new JSONObject(json_array.getString(0));

                            if (UserInfoobj.has("mode")) {


                                if (UserInfoobj.has("apntmnt_id"))
                                    appointmentid = UserInfoobj.getString("apntmnt_id");

                                String moodGraph = "";

                                if (UserInfoobj.has("mood_graph"))
                                    moodGraph = UserInfoobj.getString("mood_graph");

                                if (UserInfoobj.has("clcnslrun01"))
                                    counsellerid = UserInfoobj.getString("clcnslrun01");

                                if (UserInfoobj.has("usr_come_at"))
                                    user_came_at = UserInfoobj.getString("usr_come_at");


                                if (UserInfoobj.has("apntmnt_date"))
                                    Counsellertime = UserInfoobj.getString("apntmnt_date");

                                if (UserInfoobj.has("counc_unq_id"))
                                    counsellor_booking_id = UserInfoobj.getString("counc_unq_id");

                                if (UserInfoobj.has("counsellor_firstname"))
                                    counsellor_firstname = UserInfoobj.getString("counsellor_firstname");


                                counsellor_booking_name = counsellerid;

                                //  counsellerid = booking_object.getString("Counsellorid");
                              /*  Counsellertime = booking_object.getString("appointmentdate");

                                String appint_array[] = Counsellertime.split(" ");
                                if (appint_array != null && appint_array.length > 0) {
                                    Counsellertime = appint_array[1];
                                }*/

                                if (moodGraph == null || moodGraph.equalsIgnoreCase("") || moodGraph.equalsIgnoreCase("false")) {
                                    sharemoodontimer.setVisibility(View.VISIBLE);
                                    chatmoodshare.setVisibility(View.VISIBLE);
                                } else {
                                    sharemoodontimer.setVisibility(View.GONE);
                                    chatmoodshare.setVisibility(View.GONE);
                                }

                                new GetAllmessage().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                                //  fetchOnlyAboutMe();

                            }

                        }

                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

/*
    private void getAppintment()
    {
        try {

            //  progress_bar.setVisibility(View.VISIBLE);

            ParseQuery qrcode = ParseQuery.getQuery("Appointment");
            qrcode.whereEqualTo("Userid", ParseUser.getCurrentUser().getObjectId());
            qrcode.whereEqualTo("Status", "Accepted");
            // qrcode.whereEqualTo("Status", "Pending");

            qrcode.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    try {


                        if (objects != null && objects.size() > 0)
                        {

                            booking_object = objects.get(objects.size() - 1);


                            appointmentid = booking_object.getString("appointment_id");
                            final String moodGraph = booking_object.getString("MoodGraph");

                            counsellerid = booking_object.getString("Counsellorid");
                            Counsellertime = booking_object.getString("appointmentdate");

                            String appint_array[] = Counsellertime.split(" ");
                            if (appint_array != null && appint_array.length > 0) {
                                Counsellertime = appint_array[1];
                            }

                            if (moodGraph == null || moodGraph.equalsIgnoreCase("") || moodGraph.equalsIgnoreCase("false"))
                            {
                                sharemoodontimer.setVisibility(View.VISIBLE);
                                chatmoodshare.setVisibility(View.VISIBLE);
                            } else {
                                sharemoodontimer.setVisibility(View.GONE);
                                chatmoodshare.setVisibility(View.GONE);
                            }



                            fetchOnlyAboutMe();


                        }

                    } catch (Exception e1) {
                        e1.printStackTrace();

                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/


    private void sharemoodgraph() {

        //show_bookdate.setText(Dayname + " " + daymont + " " + MonthName + ", " + YearName + " " + Hour + ":" + Minute + " " + Ampmtext);
        try {


            AlertDialog.Builder updateAlert = new AlertDialog.Builder(ct);
            updateAlert.setIcon(R.drawable.ic_launcher);
            updateAlert.setTitle("Cypher");
            updateAlert.setMessage("Do you want to share your mood graph?");
            updateAlert.setPositiveButton(
                    "Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            MoodActivity.is_from_auto = true;

                            Intent moodact = new Intent(ct, MoodActivity.class);
                            startActivity(moodact);


                        }
                    });

            updateAlert.setNegativeButton(
                    "Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();

                        }
                    });

            AlertDialog alertUp = updateAlert.create();
            alertUp.setCanceledOnTouchOutside(false);
            alertUp.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


   /* private void uploadmoodgraph() {

        try {

            ByteArrayOutputStream stream = new ByteArrayOutputStream();

            bitmapmoodgraph.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] image = stream.toByteArray();
            final ParseFile file = new ParseFile(appointmentid + ".png", image);
            progress_bar.setVisibility(View.VISIBLE);
            file.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    bitmapmoodgraph = null;
                    if (e == null) {
                        booking_object.put("MoodGraph", "true");
                        booking_object.put("moodGraphImage", file);
                        ParseACL user_session = new ParseACL();
                        user_session.setPublicWriteAccess(true);
                        user_session.setPublicReadAccess(true);
                        booking_object.setACL(user_session);


                        booking_object.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {

                                progress_bar.setVisibility(View.GONE);
                                chatmoodshare.setVisibility(View.GONE);
                                sharemoodontimer.setVisibility(View.GONE);

                            }
                        });
                    } else {
                        progress_bar.setVisibility(View.GONE);
                    }
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }


    }
*/

    private void fetchOnlyAboutMe()

    {

       /* if (is_fetch_done)
        {

            if (appointmentid != null && !appointmentid.equalsIgnoreCase(""))
            {

                is_fetch_done = false;
                ParseQuery<ParseObject> me = ParseQuery.getQuery("CounsellorChat");
                me.whereEqualTo("appointmentid", appointmentid);
                me.setLimit(1000);
                if (last_updateDateTime != null)
                    me.whereGreaterThan("createdAt", last_updateDateTime);
                me.addAscendingOrder("createdAt");
                me.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {

                        progress_bar.setVisibility(View.GONE);
                        if (e == null) {

                            if (objects != null && objects.size() > 0) {

                                for (int i = 0; i < objects.size(); i++) {
                                    ParseObject currentFriendObject = objects.get(i);
                                    currentSender = currentFriendObject.getString("sender");
                                    receiverFriend = currentFriendObject.getString("receiver");
                                    messageFetch = currentFriendObject.getString("message");
                                    date = currentFriendObject.getCreatedAt();

                                    if (i == objects.size() - 1)
                                        last_updateDateTime = currentFriendObject.getCreatedAt();
                                    ;

                                    systemDate = date.toString();
                                    systemDate = df.format(date);

                                    ChatDetailBean me = new ChatDetailBean();
                                    if (currentSender.equalsIgnoreCase(ParseUser.getCurrentUser().getObjectId())) {
                                        me.setMine(true);
                                        is_User_Came = true;

                                    } else {


                                        is_counselor_Came = true;
                                        me.setMine(false);

                                    }


                                    me.setYourMsg(messageFetch);
                                    me.setSentTime(systemDate);



                                    if (!messageFetch.equalsIgnoreCase(currentSender) && !messageFetch.equalsIgnoreCase(receiverFriend) && !messageFetch.equalsIgnoreCase("ImpactRatingDone"))
                                        mChatArrayList.add(me);


                                    //}

                                }

                                if (!is_User_Came) {
                                    attandencemessage.setVisibility(View.VISIBLE);
                                } else {
                                    attandencemessage.setVisibility(View.GONE);
                                }

                                if (mChatArrayList.size() > 0) {
                                    if (mChatDetailsAdapter == null) {
                                        mChatDetailsAdapter = new ChatDetailsAdapter((Activity) ct, mChatArrayList);
                                        mChatList.setAdapter(mChatDetailsAdapter);

                                    } else {
                                        mChatDetailsAdapter.notifyDataSetChanged();
                                    }

                                }


                            } else {
                                if (last_updateDateTime == null) {
                                    attandencemessage.setVisibility(View.VISIBLE);
                                }

                                reversecounter = 0;
                            }


                            sharemoodontimer.postDelayed(new Runnable() {

                                @Override
                                public void run() {

                                    if (ConnectionDetector.isNetworkAvailable(ct)) {
                                        fetchOnlyAboutMe();
                                    }
                                }
                            }, 1000);


                        } else {
                            System.out.println("Warning in chatfetching----> " + e.getMessage());
                        }
                        is_fetch_done = true;
                    }
                });
            } else
                progress_bar.setVisibility(View.GONE);
        }
*/

    }


    private int Gettimediff() {
        int Mins = 0;
        try {

            if (currentdatetime != null) {
                if (reversecounter != 0)
                    reversecounter--;

                int remaining = GlimpseFragment.sesstion_time - timediff - 1;

                if (remaining >= 0) {
                    //setdate_time();


                    String Remain_time = "";
                    if (remaining < 10) {
                        Remain_time = "0" + remaining;

                        if (remaining > 8)
                            ten_min_remain.setVisibility(View.VISIBLE);
                        else
                            ten_min_remain.setVisibility(View.GONE);

                        if (remaining < 5 && MainActivity.session_left > 0) {

                            String next_appoint = AppSession.getValue(ct, Constants.NEXT_APPOINTMENT_BOOKED);
                            if (next_appoint == null || next_appoint.equalsIgnoreCase("") || next_appoint.equalsIgnoreCase("false"))
                                book_appoint_remingder.setVisibility(View.VISIBLE);
                        }

                        show_counsel_timer.setBackgroundColor(Color.parseColor("#ff5d5d"));
                    } else
                        Remain_time = "" + remaining;

                    // remain_minute = remaining;
                    // remain_second = seconcount;

                    if (remaining < GlimpseFragment.sesstion_time) {
                        if (seconcount > 9)
                            show_counsel_timer.setText("" + Remain_time + ":" + seconcount);
                        else {
                            show_counsel_timer.setText("" + Remain_time + ":0" + seconcount);

                        }
                    }


                    if (seconcount < 10) {
                        impact_timer.setText("Counselling remaining time " +  Remain_time + ":0" + seconcount);
                    } else {
                        impact_timer.setText("Counselling remaining time " +  Remain_time + ":" + seconcount);
                    }



                    if (seconcount <= 0) {
                        timediff++;
                        seconcount = 60;
                    }

/*
                   */
                    seconcount--;

                } else {
                    show_counsel_timer.setText("00:00");
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return timediff;
    }


    Runnable checlcounsellingtime = new Runnable() {
        @Override
        public void run() {

            try {
               // impactratinglayout.setVisibility(View.GONE);
                cancel_button.removeCallbacks(settimer);
            } catch (Exception e) {

            }
            is_show_start = true;


            try {

                rating_question.removeCallbacks(checlcounsellingtime);
                if (timediff >= 0) {
                    if (reversecounter == 0) {
                        int timedeff = Gettimediff();

                        if (timedeff >= GlimpseFragment.sesstion_time) {
                            timer_layout.setVisibility(View.GONE);
                            callbutton.setVisibility(View.GONE);
                            chatscreen.setVisibility(View.GONE);

                            AppSession.save(ct, Constants.NEXT_APPOINTMENT_BOOKED, "false");
                            try {

                                MainActivity.is_booking = false;

                                if (is_User_Came == false && is_counselor_Came == false)
                                {
                                    appointmentAutoCancelDTO = new AppointmentAutoCancelDTO(appointmentid, MainActivity.enc_username);
                                    new CancelAppointmentWithoutSession().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                                }

                                else if(is_User_Came == false)
                                {
                                    appointmentAutoCancelDTO = new AppointmentAutoCancelDTO(appointmentid, MainActivity.enc_username);
                                    new CancelAppointmentWithoutSession().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                                }
                                else if (is_User_Came == true && is_counselor_Came == false)
                                {
                                    appointmentAutoCancelDTO = new AppointmentAutoCancelDTO(appointmentid, MainActivity.enc_username);
                                    new DeniedAppinment().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                                }
                                else
                                {
                                    appointmentCompleteDTO = new AppointmentCompleteDTO(appointmentid, MainActivity.enc_username);
                                    new CompleteAppointment().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                                }


                                if (timedeff > GlimpseFragment.sesstion_time + 1) {
                                    if (rattingQuestionDTOs == null || rattingQuestionDTOs.size() == 0)
                                        new CheckPendingRating().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                                    else
                                        ShowRating();
                                } else {
                                    try {
                                        KeyboardUtil.hideKeyBoard(msg_et, LiveCounselling.this);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    AlertDialog.Builder motivateDialog = new AlertDialog.Builder(ct);
                                    motivateDialog.setMessage("Your " + "Counselling" + " time is over");
                                    motivateDialog.setTitle("Cypher");
                                    motivateDialog.setIcon(R.drawable.ic_launcher);
                                    motivateDialog.setPositiveButton(
                                            "Ok",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.dismiss();

                                                    show_counsel_timer.setText("");
                                                    show_counsel_timer.setVisibility(View.GONE);
                                                    if (rattingQuestionDTOs == null || rattingQuestionDTOs.size() == 0)
                                                        new CheckPendingRating().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                                                    else
                                                        ShowRating();

                                                    //ratingscreen.setVisibility(View.VISIBLE);
                                                }
                                            });

                                    AlertDialog alertmotivate = motivateDialog.create();
                                    alertmotivate.setCanceledOnTouchOutside(false);
                                    alertmotivate.show();
                                }

                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }
                        } else if (is_counselor_Came && is_User_Came) {
                            if (!is_taing_insert) {
                                is_taing_insert = true;
                                new InsertRating().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                            }

                            rating_question.postDelayed(checlcounsellingtime, 1000);
                        } else {
                            if (is_counselor_Came == false && timedeff >= 5 && reversecounter <= 0) {
                                try {

                                    try {
                                        if (NotificationService.runningcall != null) {
                                            NotificationService.runningcall.hangup();

                                        }
                                    } catch (Exception e) {

                                    }
                                    appointmentAutoCancelDTO = new AppointmentAutoCancelDTO(appointmentid, MainActivity.enc_username);
                                    new DeniedAppinment().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


                                } catch (Exception e1) {
                                    e1.printStackTrace();
                                }

                            } else {
                                rating_question.postDelayed(checlcounsellingtime, 1000);
                            }

                        }

                    } else
                        rating_question.postDelayed(checlcounsellingtime, 1000);

                } else {
                    //   Getcurrenttime();
                    new GetCurrentDateTimeonmoodshare().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    rating_question.postDelayed(checlcounsellingtime, 1000);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };

    public static void UserAttandace() {
        try {

            String appint_array[] = Counsellertime.split(" ");
            String date = "";
            if (appint_array != null && appint_array.length > 0) {
                date = appint_array[0];
            }

            Calendar cal = Calendar.getInstance();
            int systemhour = 0, systemminute = 0, ampm;
            systemhour = cal.get(Calendar.HOUR);
            systemminute = cal.get(Calendar.MINUTE);
            ampm = cal.get(Calendar.AM_PM);

            String mytime = "";

            if (ampm == 0) {
                if (systemhour > 9)
                    mytime = mytime + systemhour;
                else
                    mytime = "0" + systemhour;

                if (systemminute > 9)
                    mytime = mytime + ":" + systemminute;
                else
                    mytime = mytime + ":0" + systemminute;
            } else {
                if (systemhour == 12) {
                    mytime = mytime + systemhour;


                    if (systemminute > 9)
                        mytime = mytime + ":" + systemminute;
                    else
                        mytime = mytime + ":0" + systemminute;
                } else {
                    systemhour = systemhour + 12;

                    mytime = mytime + systemhour;

                    if (systemminute > 9)
                        mytime = mytime + ":" + systemminute;
                    else
                        mytime = mytime + ":0" + systemminute;
                }
            }

            date = date + " " + mytime + ":00";

            user_came_at = getAttandaceLiveTime(date);

            sendAttandanceDataDTO = new SendAttandanceDataDTO(user_came_at, appointmentid);
            new SendAttandance().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static class InsertRating extends android.os.AsyncTask<String, String, Bitmap> {

        android.app.ProgressDialog pDialog;
        String data = "0";
        Bitmap bitmap;
        String imageurl = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected Bitmap doInBackground(String... args) {
            try {
                InsertRatingDTO insertRatingDTO = null;
                if (MainActivity.session_left == 0)
                    insertRatingDTO = new InsertRatingDTO(appointmentid, counsellerid, MainActivity.enc_username, "User", true);
                else
                    insertRatingDTO = new InsertRatingDTO(appointmentid, counsellerid, MainActivity.enc_username, "User", false);

                IfriendRequest http = new IfriendRequest(livecounsel);
                CommonRequestTypeDTO commonRequestDTO = new CommonRequestTypeDTO(insertRatingDTO, "setRattingQuestions");
                FinalObjectDTO sendChatObjectDTO = new FinalObjectDTO(commonRequestDTO);
                rattingQuestionDTOs = http.InsertRatingQuestion(sendChatObjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {
            try {
                if (rattingQuestionDTOs != null && rattingQuestionDTOs.size() > 0) {

                    is_taing_insert = true;
                } else
                    is_taing_insert = false;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }



/*


    private class GetPendingRating extends android.os.AsyncTask<String, String, Bitmap> {

        android.app.ProgressDialog pDialog;
        String data = "0";
        Bitmap bitmap;
        String imageurl = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressbar.setVisibility(View.VISIBLE);
        }

        protected Bitmap doInBackground(String... args) {
            try {
                String counsellor_id = AppSession.getValue(ct, CommonString.USER_NAME);
                GetRatingonlyDTO getRatingonlyDTO = new GetRatingonlyDTO( counsellor_id);
                CommonRequestDTO commonRequestDTO = new CommonRequestDTO("getCounsellorQuestions",getRatingonlyDTO);
                HttpRequest http = new HttpRequest(ct);
                FinalObjectDTO sendChatObjectDTO = new FinalObjectDTO(commonRequestDTO);
                rattingQuestionDTOs = http.GetRatingQuestion(sendChatObjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {
            try {
                if (rattingQuestionDTOs != null && rattingQuestionDTOs.size() > 0) {
                    progressbar.setVisibility(View.GONE);
                    total_question = rattingQuestionDTOs.size();
                    current_question = 0;
                    ShowRating();
                } else {
                    new GetAppointment().execute();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
*/


/*
    private void ShowRating()
    {
        try {

            callbutton.setVisibility(View.GONE);
            ParseQuery<ParseObject> qrcode = ParseQuery.getQuery("CounsellorRatting");
            qrcode.whereEqualTo("status", "Pending");
            qrcode.whereEqualTo("userid", ParseUser.getCurrentUser().getObjectId());

            qrcode.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    progress_bar.setVisibility(View.GONE);

                    is_show_rating = false;

                    if (objects != null && objects.size() > 0)
                    {
                        try {
                            Rating_boject = objects.get(0);
                            ratingscreen.setVisibility(View.VISIBLE);
                            String question = Rating_boject.getString("questiontext");
                            show_impact_reminder = Rating_boject.getBoolean("impact_reminder");
                            rating_question.setText(question);
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }

                    } else
                    {
                        if(show_impact_reminder)
                        {
                            AlertDialog.Builder updateAlert = new AlertDialog.Builder(LiveCounselling.this);
                            updateAlert.setIcon(R.drawable.ic_launcher);
                            updateAlert.setTitle("Cypher");
                            updateAlert.setMessage("Please join the counselling session 5 minutes before session time, to answer some questions.");
                            updateAlert.setPositiveButton(
                                    "Ok",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                            if (MainActivity.session_left > 0) {
                                                MainActivity.is_booking = false;
                                                Intent booking = new Intent(ct, CounsellerBooking.class);
                                                startActivity(booking);
                                                finish();
                                            } else {
                                                MainActivity.is_booking = false;
                                                Intent intent = new Intent(ct, CameraTestActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        }
                                    });


                            AlertDialog alertUp = updateAlert.create();
                            alertUp.setCanceledOnTouchOutside(false);
                            alertUp.show();
                        }
                        else
                        {
                            if (MainActivity.session_left > 0) {
                                MainActivity.is_booking = false;
                                Intent booking = new Intent(ct, CounsellerBooking.class);
                                startActivity(booking);
                                finish();
                            } else {
                                MainActivity.is_booking = false;
                                Intent intent = new Intent(ct, CameraTestActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }



                    }
                }
            });

            timer_layout.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
*/


    /*private Date Getcurrenttime() {


        final ParseUser token = ParseUser.getCurrentUser();
        token.put("deviceType", "android");

        ParseACL acl = new ParseACL();
        acl.setPublicReadAccess(true);
        acl.setPublicWriteAccess(true);
        token.setACL(acl);


        token.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

                currentdatetime = token.getUpdatedAt();
                setdate_time();


            }


        });
        return null;
    }*/

    private class GetCurrentDateTimeonmoodshare extends android.os.AsyncTask<String, String, Bitmap> {

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


                IfriendRequest http = new IfriendRequest(ct);

                GetCurrentDateTimeDTO allIfriendDTO = new GetCurrentDateTimeDTO("currentTime");

                GetCurrentDateObjectDTO getCurrentDateObjectDTO = new GetCurrentDateObjectDTO(allIfriendDTO);


                response = http.GetDateTime(getCurrentDateObjectDTO);


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {
            if (response != null && !response.equalsIgnoreCase("")) {
                //currentdatetime = token.getUpdatedAt();

                response = getLocalTime(response);

                SimpleDateFormat dateFormat = new SimpleDateFormat(
                        "MM/dd/yyyy HH:mm:ss");
                try {
                    currentdatetime = dateFormat.parse(response);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                setdate_time();
            }

        }
    }


    private class GetCurrentDateTime extends android.os.AsyncTask<String, String, Bitmap> {

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


                IfriendRequest http = new IfriendRequest(ct);

                GetCurrentDateTimeDTO allIfriendDTO = new GetCurrentDateTimeDTO("currentTime");

                GetCurrentDateObjectDTO getCurrentDateObjectDTO = new GetCurrentDateObjectDTO(allIfriendDTO);


                response = http.GetDateTime(getCurrentDateObjectDTO);


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {
            if (response != null && !response.equalsIgnoreCase("")) {
                //currentdatetime = token.getUpdatedAt();

                response = getLocalTime(response);

                SimpleDateFormat dateFormat = new SimpleDateFormat(
                        "MM/dd/yyyy HH:mm:ss");
                try {
                    currentdatetime = dateFormat.parse(response);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                setdate_time();
            }
            new GetAppointment().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    }

    private String getLocalTime(String datetime) {
        String result = "";
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "MM/dd/yyyy HH:mm:ss");
            Date myDate = null;
            try {
                myDate = dateFormat.parse(datetime);

            } catch (Exception e) {
                e.printStackTrace();
            }
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
            SimpleDateFormat destFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            TimeZone london = TimeZone.getTimeZone("Europe/London");
            long now = resultdate.getTime();
            Date resultdate1 = new Date(miliSeconds - london.getOffset(now));
            result = destFormat.format(resultdate1);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return result;
    }


    @Override
    public void onStartFailed(SinchError error) {

    }

    @Override
    public void onStarted() {

    }


    @Override
    protected void onServiceConnected() {

        getSinchServiceInterface().setStartListener(this);
    }

    private void loginClicked() {
        try {
            String userName = MainActivity.enc_username;

            if (userName.isEmpty()) {
                Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
                return;
            }

            //  getSinchServiceInterface().startClient(userName);

            if (!getSinchServiceInterface().isStarted()) {
                getSinchServiceInterface().startClient(userName);

            } else {
                getSinchServiceInterface().stopClient();
                getSinchServiceInterface().startClient(userName);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void callButtonClicked() {
        String userName = counsellerid;

        if (chat_mode != null && chat_mode.equalsIgnoreCase("Audio")) {
            Call call = getSinchServiceInterface().callUser(userName);
            String callId = call.getCallId();
            Intent callScreen = null;
            callScreen = new Intent(this, AudioCallScreenActivity.class);
            callScreen.putExtra(SinchService.CALL_ID, callId);
            startActivity(callScreen);
        } else {
            Call call = getSinchServiceInterface().callUserVideo(userName);
            String callId = call.getCallId();
            Intent callScreen = null;
            callScreen = new Intent(this, CallScreenActivity.class);
            callScreen.putExtra(SinchService.CALL_ID, callId);
            startActivity(callScreen);

        }


        String userid = MainActivity.enc_username;

        inserCallSessionDTO = new InserCallSessionDTO(appointmentid, counsellerid, userid, "calling",
                "User", "");
        new InsertSession().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private void makecall() {

        if (getSinchServiceInterface().isStarted()) {
            callButtonClicked();

        } else {
            String userName = MainActivity.enc_username;

            if (userName.isEmpty()) {
                Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
                return;
            }

            if (!getSinchServiceInterface().isStarted()) {
                getSinchServiceInterface().startClient(userName);

            }
        }
    }

    private class InsertSession extends android.os.AsyncTask<String, String, Bitmap> {

        android.app.ProgressDialog pDialog;
        String data = "0";
        Bitmap bitmap;
        String imageurl = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //  progress_bar.setVisibility(View.VISIBLE);
        }

        protected Bitmap doInBackground(String... args) {
            try {


                IfriendRequest http = new IfriendRequest(ct);
                CommonRequestTypeDTO commonRequestDTO = new CommonRequestTypeDTO(inserCallSessionDTO, "Callsession");
                FinalObjectDTO insertCallSessionObjectDTO = new FinalObjectDTO(commonRequestDTO);
                http.InsertSessopn(insertCallSessionObjectDTO);


               /* InsertCallSessionObjectDTO insertCallSessionObjectDTO = new InsertCallSessionObjectDTO(inserCallSessionDTO);
                data = http.InsertSessopn(insertCallSessionObjectDTO);*/

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {
            //progress_bar.setVisibility(View.GONE);
        }
    }


    public void ImpactRatingdone() {

        try {
            long mseconds = System.currentTimeMillis();
            String unique_id = MainActivity.enc_username + mseconds;
            SendMessageDataDTO sendMessageDataDTO = new SendMessageDataDTO(MainActivity.enc_username, counsellerid, CryptLib.encrypt("ImpactRatingDone"), appointmentid, unique_id);


            SendMessageObjectDTO sendMessageObjectDTO = new SendMessageObjectDTO(new SendMessageDTO(sendMessageDataDTO));

            sendMessageObjectDTOs.add(sendMessageObjectDTO);

            new Submit_impact_async().execute();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void Attandance() {
        try {

            long mseconds = System.currentTimeMillis();
            String unique_id = MainActivity.enc_username + mseconds;


            SendMessageDataDTO sendMessageDataDTO = new SendMessageDataDTO(MainActivity.enc_username, counsellerid, MainActivity.enc_username, appointmentid, unique_id);

            SendMessageObjectDTO sendMessageObjectDTO = new SendMessageObjectDTO(new SendMessageDTO(sendMessageDataDTO));

            sendMessageObjectDTOs.add(sendMessageObjectDTO);
            attandencemessage.setVisibility(View.GONE);
            is_User_Came = true;
            new InsertRating().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

            if (user_came_at == null || user_came_at.equalsIgnoreCase(""))
                UserAttandace();


            //   Getratingquestion();

           /* ParseObject chatFriend = new ParseObject("CounsellorChat");
            chatFriend.put("sender", ParseUser.getCurrentUser().getObjectId());
            chatFriend.put("receiver", counsellerid);
            chatFriend.put("message", ParseUser.getCurrentUser().getObjectId());
            chatFriend.put("read", "false");
            chatFriend.put("appointmentid", appointmentid);

            ParseACL aclchatFriend = new ParseACL();
            aclchatFriend.setPublicWriteAccess(true);
            aclchatFriend.setPublicReadAccess(true);
            chatFriend.setACL(aclchatFriend);

            chatFriend.saveInBackground(new SaveCallback() {

                @Override
                public void done(ParseException e) {

                    Getratingquestion();
                }
            });*/
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    static private Calendar current;
    static private long miliSeconds;
    static private Date resultdate;

    static private String getGMTTime(String datetime) {
        String result = "";
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "MM/dd/yyyy HH:mm:ss");
            Date myDate = null;
            try {
                myDate = dateFormat.parse(datetime);

            } catch (Exception e) {
                e.printStackTrace();
            }
            current = Calendar.getInstance();
            current.setTime(myDate);
            miliSeconds = current.getTimeInMillis();
            TimeZone tzCurrent = current.getTimeZone();
            int offset = tzCurrent.getRawOffset();
            if (tzCurrent.inDaylightTime(new Date())) {
                offset = offset + tzCurrent.getDSTSavings();
            }
            miliSeconds = miliSeconds - offset;
            resultdate = new Date(miliSeconds);
            SimpleDateFormat destFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            TimeZone london = TimeZone.getTimeZone("Europe/London");
            long now = resultdate.getTime();
            Date resultdate1 = new Date(miliSeconds + london.getOffset(now));
            result = destFormat.format(resultdate1);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return result;
    }

    static private String getAttandaceLiveTime(String datetime) {
        String result = "";
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "MM/dd/yyyy HH:mm:ss");
            Date myDate = null;
            try {
                myDate = dateFormat.parse(datetime);

            } catch (Exception e) {
                e.printStackTrace();
            }
            current = Calendar.getInstance();
            current.setTime(myDate);
            miliSeconds = current.getTimeInMillis();
            TimeZone tzCurrent = current.getTimeZone();
            int offset = tzCurrent.getRawOffset();
            if (tzCurrent.inDaylightTime(new Date())) {
                offset = offset + tzCurrent.getDSTSavings();
            }
            miliSeconds = miliSeconds - offset;
            resultdate = new Date(miliSeconds);
            SimpleDateFormat destFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            TimeZone london = TimeZone.getTimeZone("Europe/London");
            long now = resultdate.getTime();
            Date resultdate1 = new Date(miliSeconds + london.getOffset(now));
            result = destFormat.format(resultdate1);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return result;
    }

    protected void startTracking(String path) {
        Tracker t = ((SilentSecretApplication) getApplication()).getTracker(
                SilentSecretApplication.TrackerName.APP_TRACKER);
        t.setScreenName(path);
        t.send(new HitBuilders.AppViewBuilder().build());
    }


    List<CheckBox> NotatAllList = null;
    List<CheckBox> OccasionallList = null;
    List<CheckBox> SometimeList = null;
    List<CheckBox> OfterList = null;
    List<CheckBox> MostofAllList = null;


    private void initview() {

        if (NotatAllList != null)
            NotatAllList.clear();
        else
            NotatAllList = new ArrayList<>();

        if (OccasionallList != null)
            OccasionallList.clear();
        else
            OccasionallList = new ArrayList<>();

        if (SometimeList != null)
            SometimeList.clear();
        else
            SometimeList = new ArrayList<>();

        if (OfterList != null)
            OfterList.clear();
        else
            OfterList = new ArrayList<>();

        if (MostofAllList != null)
            MostofAllList.clear();
        else
            MostofAllList = new ArrayList<>();


        if (submit_array != null)
            submit_array = null;


/*
        String[] overviewQuestiopns = {"1. I’ve felt edgy or nervous", "2. I haven’t felt like talking to anyone",
                "3. I’ve felt able to cope when things go wrong", "4. I’ve thought of hurting myself",
                "5. There’s been someone I felt able to ask for help ", "6. My thoughts and feelings distressed me",
                "7. My problems have felt too much for me", "8. It’s been hard to go to sleep or stay asleep",
                "9. I’ve felt unhappy", "10. I’ve done all the things I wanted to"};*/

        submit_array = new boolean[sendImpactRatingDTOs.size()];

        try {

            if (impactquestion != null)
                impactquestion = null;

            impactquestion = (LinearLayout) findViewById(R.id.impactquestion);

            if (impactnotaatall != null)
                impactnotaatall = null;

            impactnotaatall = (LinearLayout) findViewById(R.id.impactnotaatall);

            if (impactonlyoccasional != null)
                impactonlyoccasional = null;

            impactonlyoccasional = (LinearLayout) findViewById(R.id.impactonlyoccasional);

            if (impactsometimes != null)
                impactsometimes = null;
            impactsometimes = (LinearLayout) findViewById(R.id.impactsometimes);


            if (impactofter != null)
                impactofter = null;
            impactofter = (LinearLayout) findViewById(R.id.impactofter);

            if (impactmostoff != null)
                impactmostoff = null;
            impactmostoff = (LinearLayout) findViewById(R.id.impactmostoff);

            for (int i = 0; i < sendImpactRatingDTOs.size(); i++) {

                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(width / 2, width / 8);
                LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, width / 8);
                llp.setMargins(width / 25, 0, width / 25, 0);
                TextView impactquestiontext = new TextView(ct);
                impactquestiontext.setLayoutParams(llp);
                impactquestiontext.setText((i + 1) + ". " + CryptLib.decrypt(sendImpactRatingDTOs.get(i).getQuestion()));
                impactquestiontext.setGravity(Gravity.LEFT | Gravity.CENTER);
                impactquestiontext.setTextColor(Color.parseColor("#222222"));
                TextView impactquestiontextline = new TextView(ct);
                lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, width / 200);
                impactquestiontextline.setLayoutParams(lp);
                impactquestiontextline.setBackgroundColor(Color.parseColor("#d3d3d3"));
                impactquestion.addView(impactquestiontext);
                impactquestion.addView(impactquestiontextline);
                llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, width / 8);
                CheckBox impactnotatalltext = new CheckBox(ct);
                llp.gravity = Gravity.CENTER;
                impactnotatalltext.setLayoutParams(llp);
                impactnotatalltext.setText("0");
                impactnotatalltext.setId(i + 1);
                impactnotatalltext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int index = v.getId() - 1;
                        if (NotatAllList.get(index).isSelected()) {
                            NotatAllList.get(index).setChecked(false);
                            submit_array[index] = false;
                        } else {
                            NotatAllList.get(index).setChecked(true);
                            submit_array[index] = true;
                            sendImpactRatingDTOs.get(index).setRating("0");
                        }
                        OccasionallList.get(index).setChecked(false);
                        SometimeList.get(index).setChecked(false);
                        OfterList.get(index).setChecked(false);
                        MostofAllList.get(index).setChecked(false);
                    }
                });

                NotatAllList.add(impactnotatalltext);

                TextView basic_impactnotatalltextline = new TextView(ct);
                lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, width / 200);
                basic_impactnotatalltextline.setLayoutParams(lp);
                basic_impactnotatalltextline.setBackgroundColor(Color.parseColor("#d3d3d3"));
                impactnotaatall.addView(impactnotatalltext);
                impactnotaatall.addView(basic_impactnotatalltextline);


                /*lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, width / 8);
                ImageView impactonlyoccasionaltext = new ImageView(ct);
                lp.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
                impactonlyoccasionaltext.setLayoutParams(lp);
                impactonlyoccasionaltext.setPadding(width / 25, width / 25, width / 25, width / 25);
                impactonlyoccasionaltext.setImageResource(R.drawable.check_sel);
*/

                llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, width / 8);
                CheckBox impactonlyoccasionaltext = new CheckBox(ct);
                llp.gravity = Gravity.CENTER;
                impactonlyoccasionaltext.setLayoutParams(llp);

                impactonlyoccasionaltext.setText("1");
                impactonlyoccasionaltext.setId(i + 2);
                impactonlyoccasionaltext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int index = v.getId() - 2;

                        if (OccasionallList.get(index).isSelected()) {
                            OccasionallList.get(index).setChecked(false);
                            submit_array[index] = false;
                        } else {
                            OccasionallList.get(index).setChecked(true);
                            submit_array[index] = true;
                            sendImpactRatingDTOs.get(index).setRating("1");
                        }

                        NotatAllList.get(index).setChecked(false);
                        SometimeList.get(index).setChecked(false);
                        OfterList.get(index).setChecked(false);
                        MostofAllList.get(index).setChecked(false);
                    }
                });
                OccasionallList.add(impactonlyoccasionaltext);

                TextView impactonlyoccasionaltextline = new TextView(ct);
                lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, width / 200);


                impactonlyoccasionaltextline.setLayoutParams(lp);
                impactonlyoccasionaltextline.setBackgroundColor(Color.parseColor("#d3d3d3"));
                impactonlyoccasional.addView(impactonlyoccasionaltext);
                impactonlyoccasional.addView(impactonlyoccasionaltextline);


                llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, width / 8);
                CheckBox impactsometimestext = new CheckBox(ct);
                llp.gravity = Gravity.CENTER;
                impactsometimestext.setLayoutParams(llp);

                impactsometimestext.setText("2");
                impactsometimestext.setId(i + 3);
                impactsometimestext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int index = v.getId() - 3;

                        if (SometimeList.get(index).isSelected()) {
                            SometimeList.get(index).setChecked(false);
                            submit_array[index] = false;
                        } else {
                            SometimeList.get(index).setChecked(true);
                            submit_array[index] = true;
                            sendImpactRatingDTOs.get(index).setRating("2");
                        }

                        NotatAllList.get(index).setChecked(false);
                        OccasionallList.get(index).setChecked(false);
                        OfterList.get(index).setChecked(false);
                        MostofAllList.get(index).setChecked(false);
                    }
                });

                SometimeList.add(impactsometimestext);

                TextView impactsometimestexttline = new TextView(ct);
                lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, width / 200);


                impactsometimestexttline.setLayoutParams(lp);
                impactsometimestexttline.setBackgroundColor(Color.parseColor("#d3d3d3"));
                impactsometimes.addView(impactsometimestext);
                impactsometimes.addView(impactsometimestexttline);


                llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, width / 8);
                CheckBox impactoftertext = new CheckBox(ct);
                llp.gravity = Gravity.CENTER;
                impactoftertext.setLayoutParams(llp);

                impactoftertext.setText("3");
                impactoftertext.setId(i + 4);
                impactoftertext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int index = v.getId() - 4;

                        if (OfterList.get(index).isSelected()) {
                            OfterList.get(index).setChecked(false);
                            submit_array[index] = false;
                        } else {
                            OfterList.get(index).setChecked(true);
                            submit_array[index] = true;
                            sendImpactRatingDTOs.get(index).setRating("3");
                        }

                        NotatAllList.get(index).setChecked(false);
                        OccasionallList.get(index).setChecked(false);
                        SometimeList.get(index).setChecked(false);
                        MostofAllList.get(index).setChecked(false);
                    }
                });

                OfterList.add(impactoftertext);

                TextView impactoftertexttline = new TextView(ct);
                lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, width / 200);


                impactoftertexttline.setLayoutParams(lp);
                impactoftertexttline.setBackgroundColor(Color.parseColor("#d3d3d3"));
                impactofter.addView(impactoftertext);
                impactofter.addView(impactoftertexttline);


                llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, width / 8);
                CheckBox impactmostofftext = new CheckBox(ct);
                llp.gravity = Gravity.CENTER;
                impactmostofftext.setLayoutParams(llp);

                impactmostofftext.setText("4");
                impactmostofftext.setId(i + 4);
                impactmostofftext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int index = v.getId() - 4;

                        if (MostofAllList.get(index).isSelected()) {
                            MostofAllList.get(index).setChecked(false);
                            submit_array[index] = false;
                        } else {
                            MostofAllList.get(index).setChecked(true);
                            submit_array[index] = true;
                            sendImpactRatingDTOs.get(index).setRating("4");
                        }

                        NotatAllList.get(index).setChecked(false);
                        OccasionallList.get(index).setChecked(false);
                        SometimeList.get(index).setChecked(false);
                        OfterList.get(index).setChecked(false);
                    }
                });

                MostofAllList.add(impactmostofftext);

                TextView impactmostofftexttline = new TextView(ct);
                lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, width / 200);


                impactmostofftexttline.setLayoutParams(lp);
                impactmostofftexttline.setBackgroundColor(Color.parseColor("#d3d3d3"));
                impactmostoff.addView(impactmostofftext);
                impactmostoff.addView(impactmostofftexttline);


            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void ThankYouPopup() {
        AlertDialog.Builder updateAlert = new AlertDialog.Builder(LiveCounselling.this);
        updateAlert.setIcon(R.drawable.ic_launcher);
        updateAlert.setTitle("Cypher");
        updateAlert.setMessage("Thanks for your answer");
        updateAlert.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                    }
                });


        AlertDialog alertUp = updateAlert.create();
        alertUp.setCanceledOnTouchOutside(false);
        alertUp.show();
    }


    private class GetImpactRating extends android.os.AsyncTask<String, String, Bitmap> {

        android.app.ProgressDialog pDialog;
        String data = "0";
        Bitmap bitmap;

        String response = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

//progress_bar.setVisibility(View.VISIBLE);
        }

        protected Bitmap doInBackground(String... args) {
            try {


                if (sendImpactRatingDTOs != null)
                    sendImpactRatingDTOs = null;

                IfriendRequest http = new IfriendRequest(ct);

                GetImpactRatingDTO allIfriendDTO = new GetImpactRatingDTO("getImpactQuestions");

                GetImpactRatingObjectDTO getCurrentDateObjectDTO = new GetImpactRatingObjectDTO(allIfriendDTO);


                sendImpactRatingDTOs = http.GetImpactRatingquestion(getCurrentDateObjectDTO);


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {

     //       progress_bar.setVisibility(View.GONE);

            try {
                if (sendImpactRatingDTOs != null && sendImpactRatingDTOs.size() > 0) {
                    for (int i = 0; i < sendImpactRatingDTOs.size(); i++) {
                        sendImpactRatingDTOs.get(i).setAppointmentid(appointmentid);
                        sendImpactRatingDTOs.get(i).setUserid(MainActivity.enc_username);
                        sendImpactRatingDTOs.get(i).setCounsellorid(counsellerid);
                    }
                    impactratinglayout.setVisibility(View.VISIBLE);
                    initview();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            try{
                show_counsel_timer.setVisibility(View.VISIBLE);
                if (chat_mode != null && !chat_mode.equalsIgnoreCase("Chat") && (chat_mode.equalsIgnoreCase("Audio") || chat_mode.equalsIgnoreCase("Video"))) {

                    if (chat_mode.equalsIgnoreCase("Video"))
                        callbutton.setBackgroundResource(R.drawable.video_icon);
                    callbutton.setVisibility(View.VISIBLE);

                }

                timer_layout.setVisibility(View.GONE);
                chatscreen.setVisibility(View.VISIBLE);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }


    private class SendImpactRating extends android.os.AsyncTask<String, String, Bitmap> {

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


                IfriendRequest http = new IfriendRequest(ct);

                SendImpactRatingObjectDTO sendImpactRatingObjectDTO = new SendImpactRatingObjectDTO(sendImpactRatingDTOs);


                SentImpactratinganswerObjectDTO sentImpactratinganswerObjectDTO = new SentImpactratinganswerObjectDTO(sendImpactRatingObjectDTO);

                http.SendRatingquestion(sentImpactratinganswerObjectDTO);


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
          //  impactratinglayout.setVisibility(View.GONE);
            progress_bar.setVisibility(View.GONE);
            restartActivity(LiveCounselling.this);
        }
    }

    public static void restartActivity(Activity act){

        Intent intent=new Intent();
        intent.setClass(act, act.getClass());
        act.startActivity(intent);
        act.finish();

    }

    static List<SendMessageObjectDTO> sendMessageObjectDTOs = new ArrayList<>();
    /*     SendMessageDataDTO sendMessageDataDTO = null;
         SendMessageObjectDTO sendMessageObjectDTO = null;*/
    String last_msg_datetime = "";
    long old_milisecond = 0;
    DateFormat datetimeformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    //  boolean is_click = false;
    GetChatObjectDTO finalObjectDTO = null;


    private class GetAllmessage extends android.os.AsyncTask<String, String, Bitmap> {

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


                IfriendRequest httpRequest = new IfriendRequest(ct);
                try {
                    if (sendMessageObjectDTOs.size() > 0) {

                        String SenmessagejsonData = new Gson().toJson(sendMessageObjectDTOs.get(0));
                        sendMessageObjectDTOs.remove(0);
                        httpRequest.Sendmessage(SenmessagejsonData);


                    }
                } catch (Exception e) {
                    //is_click = false;
                    return null;
                }


                if (finalObjectDTO == null) {
                    ChatCounsellingMessageDTO chatMessageDTO = new ChatCounsellingMessageDTO(appointmentid, last_msg_datetime, MainActivity.enc_username);
                    ChatMessageReqDTO commonRequestDTO = new ChatMessageReqDTO("getMessage", chatMessageDTO);
                    finalObjectDTO = new GetChatObjectDTO(commonRequestDTO);
                }

                String jsonData = new Gson().toJson(finalObjectDTO);

                try {
                    response = httpRequest.Getchatmessag(jsonData);


                } catch (Exception e) {

                    return null;
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {


            Date curretime = null;

            boolean is_new_message = false;
            String status = "";
            try {


                Object object = new JSONTokener(response).nextValue();
                if (object instanceof JSONObject) {
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.has("status"))
                        status = jsonObject.getString("status");

                    if (status != null && status.equalsIgnoreCase("true")) {
                        JSONArray json_array = null;
                        try {
                            if (jsonObject.has("data"))
                                json_array = jsonObject.getJSONArray("data");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        if (json_array != null && json_array.length() > 0) {
                            for (int i = 0; i < json_array.length(); i++) {

                                JSONObject UserInfoobj = new JSONObject(json_array.getString(i));
                                String chatid = "";
                                boolean seen = false;

                                if (UserInfoobj.has("sender"))
                                    currentSender = UserInfoobj.getString("sender");
                                if (UserInfoobj.has("receiver"))
                                    receiverFriend = UserInfoobj.getString("receiver");
                                if (UserInfoobj.has("message"))
                                    messageFetch = UserInfoobj.getString("message");

                                if (UserInfoobj.has("read"))
                                    seen = UserInfoobj.getBoolean("read");


                                if (UserInfoobj.has("unique_id"))
                                    chatid = UserInfoobj.getString("unique_id");


                                if (UserInfoobj.has("created_at")) {
                                    try {
                                        Calendar current;
                                        long miliSeconds;
                                        Date resultdate;
                                        current = Calendar.getInstance();


                                        TimeZone tzCurrent = current.getTimeZone();
                                        int offset = tzCurrent.getRawOffset();
                                        if (tzCurrent.inDaylightTime(new Date())) {
                                            offset = offset + tzCurrent.getDSTSavings();
                                        }
                                        Calendar current1 = Calendar.getInstance();

                                        current1.setTime(Utils.StringTodate(UserInfoobj.getString("created_at")));
                                        miliSeconds = current1.getTimeInMillis();
                                        miliSeconds = miliSeconds + offset;
                                        resultdate = new Date(miliSeconds);
                                        systemDate = resultdate.toString();
                                        systemDate = df.format(resultdate);

                                        if (UserInfoobj.has("updated_at"))
                                            curretime = Utils.StringTodate(UserInfoobj.getString("updated_at"));


                                        long now = curretime.getTime();
                                        if (old_milisecond == 0) {
                                            old_milisecond = now;
                                            last_msg_datetime = datetimeformat.format(curretime);
                                            finalObjectDTO.getRequestData().getData().setLastUpdatedDate(last_msg_datetime);

                                        } else if (now > old_milisecond) {
                                            old_milisecond = now;
                                            last_msg_datetime = datetimeformat.format(curretime);
                                            finalObjectDTO.getRequestData().getData().setLastUpdatedDate(last_msg_datetime);

                                        }


                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }

                                String decrypt_message = CryptLib.decrypt(messageFetch);

                                if(decrypt_message!= null && decrypt_message.equalsIgnoreCase("ImpactRatingDone"))
                                    continue;

                                ChatDetailBean me = new ChatDetailBean();
                                if (currentSender.equalsIgnoreCase(MainActivity.enc_username)) {
                                    me.setMine(true);
                                    is_User_Came = true;
                                    attandencemessage.setVisibility(View.GONE);

                                } else {


                                    is_counselor_Came = true;
                                    me.setMine(false);

                                }

                                me.setYourMsg(decrypt_message);
                                me.setSentTime(systemDate);
                                me.setSeen(seen);

                                   /* if (messageFetch.equalsIgnoreCase("ImpactRatingDone"))
                                        is_userimapctratind_done = true;*/


                                if (!messageFetch.equalsIgnoreCase(currentSender) && !messageFetch.equalsIgnoreCase(receiverFriend) && !messageFetch.equalsIgnoreCase("ImpactRatingDone")) {

                                    try {
                                        if (chat_id.size() == 0) {
                                            is_new_message = true;
                                            chat_id.add(chatid);
                                            mChatArrayList.add(me);
                                        } else if (!chat_id.contains(chatid)) {
                                            is_new_message = true;
                                            chat_id.add(chatid);
                                            mChatArrayList.add(me);
                                        } else {
                                            int index = chat_id.indexOf(chatid);
                                            if (!mChatArrayList.get(index).isSeen() && seen) {
                                                is_new_message = true;
                                                mChatArrayList.get(index).setSeen(seen);
                                            }

                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }


                                }

                            }


                            if (mChatArrayList.size() > 0 && is_new_message) {
                                if (mChatDetailsAdapter == null) {
                                    mChatDetailsAdapter = new CounsellingChatDetailsAdapter((Activity) ct, mChatArrayList);
                                    mChatList.setAdapter(mChatDetailsAdapter);

                                } else {
                                    mChatDetailsAdapter.notifyDataSetChanged();
                                }

                            }
                        }

                        reversecounter = 0;

                        if (!is_User_Came) {
                            attandencemessage.setVisibility(View.VISIBLE);
                        } else {
                            attandencemessage.setVisibility(View.GONE);
                        }

                        mChatList.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (is_running)
                                    new GetAllmessage().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                            }
                        }, 100);
                    }
                } else {
                    if (is_running)
                        new GetAllmessage().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }


            } catch (Exception e) {
                e.printStackTrace();
                if (is_running)
                    new GetAllmessage().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }


        }
    }


    private static class SendAttandance extends android.os.AsyncTask<String, String, Bitmap> {

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


                IfriendRequest http = new IfriendRequest(livecounsel);

                CommonRequestTypeDTO sendImpactRatingObjectDTO = new CommonRequestTypeDTO(sendAttandanceDataDTO, "makeAttendance");


                FinalObjectDTO sentImpactratinganswerObjectDTO = new FinalObjectDTO(sendImpactRatingObjectDTO);

                http.SendAttanduser(sentImpactratinganswerObjectDTO);


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


    }


    private class SendRating extends android.os.AsyncTask<String, String, Bitmap> {

        android.app.ProgressDialog pDialog;
        String data = "0";
        Bitmap bitmap;
        String imageurl = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress_bar.setVisibility(View.VISIBLE);
        }

        protected Bitmap doInBackground(String... args) {
            try {

                IfriendRequest http = new IfriendRequest(ct);
                CommonRequestTypeDTO commonRequestDTO = new CommonRequestTypeDTO(sendCounsellorRatingDTO, "setRattingFeedback");
                FinalObjectDTO userTokenObjectDTO = new FinalObjectDTO(commonRequestDTO);
                http.sendrating(userTokenObjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {

            progress_bar.setVisibility(View.GONE);
            current_question++;
            ShowRating();
        }
    }

    private class CheckPendingRating extends android.os.AsyncTask<String, String, Bitmap> {

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
                IfriendRequest http = new IfriendRequest(ct);
                FindbyNameDTO findbyNameDTO = new FindbyNameDTO(MainActivity.enc_username);
                CommonRequestTypeDTO checkUserSessionDTO = new CommonRequestTypeDTO(findbyNameDTO, "checkUserRatting");
                FinalObjectDTO findbynameObjectDTO = new FinalObjectDTO(checkUserSessionDTO);
                rattingQuestionDTOs = http.GetRatingQuestion(findbynameObjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {
            ShowRating();

        }

    }


    private void ShowRating() {
        try {
            callbutton.setVisibility(View.GONE);
            book_appoint_remingder.setVisibility(View.GONE);
            show_counsel_timer.setVisibility(View.GONE);
            user_rating = 0;

            total_question = rattingQuestionDTOs.size();

            if (current_question < total_question) {
                ratingscreen.setVisibility(View.VISIBLE);
                rate_counselling.setRating(0);
                String question = rattingQuestionDTOs.get(current_question).getQuestiontext();
                rating_question.setText(CryptLib.decrypt(question));

            } else {


                    if (MainActivity.session_left > 0) {
                        MainActivity.is_booking = false;
                        Intent booking = new Intent(ct, CounsellerBooking.class);
                        startActivity(booking);
                        finish();
                    } else {
                        MainActivity.is_booking = false;
                        Intent intent = new Intent(ct, CameraTestActivity.class);
                        startActivity(intent);
                        finish();
                    }


               /* String impact_reminder = AppSession.getValue(ct, Constants.IMPACT_REMINDER);

                if (impact_reminder == null || impact_reminder.equalsIgnoreCase("") || impact_reminder.equalsIgnoreCase("true")) {
                    AlertDialog.Builder updateAlert = new AlertDialog.Builder(LiveCounselling.this);
                    updateAlert.setIcon(R.drawable.ic_launcher);
                    updateAlert.setTitle("Cypher");
                    updateAlert.setMessage("Please join the counselling session 5 minutes before session time, to answer some questions.");
                    updateAlert.setPositiveButton(
                            "Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();

                                    AppSession.save(ct, Constants.IMPACT_REMINDER, "false");

                                    if (MainActivity.session_left > 0) {
                                        MainActivity.is_booking = false;
                                        Intent booking = new Intent(ct, CounsellerBooking.class);
                                        startActivity(booking);
                                        finish();
                                    } else {
                                        MainActivity.is_booking = false;
                                        Intent intent = new Intent(ct, CameraTestActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });


                    AlertDialog alertUp = updateAlert.create();
                    alertUp.setCanceledOnTouchOutside(false);
                    alertUp.show();
                } else*/
            }


            timer_layout.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private class CompleteAppointment extends android.os.AsyncTask<String, String, Bitmap> {

        android.app.ProgressDialog pDialog;
        String data = "0";
        Bitmap bitmap;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progress_bar.setVisibility(View.VISIBLE);
        }

        protected Bitmap doInBackground(String... args) {
            try {


                IfriendRequest http = new IfriendRequest(ct);
                CommonRequestTypeDTO appointmentCancelObjectDTO = new CommonRequestTypeDTO(appointmentCompleteDTO, "completeAppointment");
                FinalObjectDTO finalObjectDTO = new FinalObjectDTO(appointmentCancelObjectDTO);
                http.AcceptAppointment(finalObjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {
            try {
                progress_bar.setVisibility(View.GONE);


            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    private class CancelAppointmentWithoutSession extends android.os.AsyncTask<String, String, Bitmap> {

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


                IfriendRequest http = new IfriendRequest(ct);
                CommonRequestTypeDTO appointmentCancelObjectDTO = new CommonRequestTypeDTO(appointmentAutoCancelDTO, "cancelAppointmentWithoutSession");
                FinalObjectDTO finalObjectDTO = new FinalObjectDTO(appointmentCancelObjectDTO);
                http.AcceptAppointment(finalObjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {
            try {

                MainActivity.session_left = MainActivity.session_left + 1;
                MainActivity.is_booking = false;
                AppSession.save(ct, Constants.USER_SESSION_LEFT, "" + MainActivity.session_left);
                progress_bar.setVisibility(View.GONE);

                AlertDialog.Builder motivateDialog = new AlertDialog.Builder(ct);
                motivateDialog.setMessage("Counsellor is not available. Please reschedule the appointment.");
                motivateDialog.setTitle("Cypher");
                motivateDialog.setIcon(R.drawable.ic_launcher);
                motivateDialog.setPositiveButton(
                        "Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();


                                MainActivity.is_booking = false;
                                Intent booking = new Intent(ct, CounsellerBooking.class);
                                startActivity(booking);
                                finish();
                            }
                        });


                AlertDialog alertmotivate = motivateDialog.create();
                alertmotivate.setCanceledOnTouchOutside(false);
                alertmotivate.show();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    private class DeniedAppinment extends android.os.AsyncTask<String, String, Bitmap> {

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


                IfriendRequest http = new IfriendRequest(ct);
                CommonRequestTypeDTO appointmentCancelObjectDTO = new CommonRequestTypeDTO(appointmentAutoCancelDTO, "cancelAppointment");
                FinalObjectDTO finalObjectDTO = new FinalObjectDTO(appointmentCancelObjectDTO);
                http.AcceptAppointment(finalObjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {
            try {

                MainActivity.session_left = MainActivity.session_left + 1;
                MainActivity.is_booking = false;
                AppSession.save(ct, Constants.USER_SESSION_LEFT, "" + MainActivity.session_left);
                progress_bar.setVisibility(View.GONE);

                AlertDialog.Builder motivateDialog = new AlertDialog.Builder(ct);
                motivateDialog.setMessage("Counsellor is not available. Please reschedule the appointment.");
                motivateDialog.setTitle("Cypher");
                motivateDialog.setIcon(R.drawable.ic_launcher);
                motivateDialog.setPositiveButton(
                        "Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();


                                MainActivity.is_booking = false;
                                Intent booking = new Intent(ct, CounsellerBooking.class);
                                startActivity(booking);
                                finish();
                            }
                        });


                AlertDialog alertmotivate = motivateDialog.create();
                alertmotivate.setCanceledOnTouchOutside(false);
                alertmotivate.show();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

 /*   public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        byte[] byteFormat = stream.toByteArray();
        String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);

        return imgString;
    }*/

    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }


    private class UploadMoodgraph extends android.os.AsyncTask<String, String, Bitmap> {

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


                IfriendRequest http = new IfriendRequest(ct);

                UploadMoodgraphDataDTO allIfriendDTO = new UploadMoodgraphDataDTO(BitMapToString(bitmapmoodgraph), appointmentid);

                CommonRequestTypeDTO getCurrentDateObjectDTO = new CommonRequestTypeDTO(allIfriendDTO, "uploadImage");

                FinalObjectDTO finalObjectDTO = new FinalObjectDTO(getCurrentDateObjectDTO);

                http.SendmodGraph(finalObjectDTO);


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {

            chatmoodshare.setVisibility(View.GONE);
            sharemoodontimer.setVisibility(View.GONE);
        }
    }

    public static void cancelNotification(Context ctx, int notifyId) {
        try {
            String ns = Context.NOTIFICATION_SERVICE;
            NotificationManager nMgr = (NotificationManager) ctx.getSystemService(ns);
            nMgr.cancel(notifyId);
        } catch (Exception e) {

        }
        GcmIntentService.remove_noti_id = 0;
    }

    private class Submit_impact_async extends android.os.AsyncTask<String, String, Bitmap> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }


        protected Bitmap doInBackground(String... args) {
            try {
                IfriendRequest httpRequest = new IfriendRequest(ct);
                try {
                    if (sendMessageObjectDTOs.size() > 0) {

                        String SenmessagejsonData = new Gson().toJson(sendMessageObjectDTOs.get(0));
                        sendMessageObjectDTOs.remove(0);
                        httpRequest.Sendmessage(SenmessagejsonData);


                    }
                } catch (Exception e) {
                    //is_click = false;
                    return null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {


        }
    }

/*

    private class Uploadmoodgraph extends android.os.AsyncTask<String, String, Bitmap> {

        android.app.ProgressDialog pDialog;
        String data = "0";
        Bitmap bitmap;
        String response = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //progress_bar.setVisibility(View.VISIBLE);
        }

        protected Bitmap doInBackground(String... args) {
            try {


                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmapmoodgraph.compress(Bitmap.CompressFormat.JPEG, 75, bos);
                byte[] data = bos.toByteArray();
                HttpClient httpClient = new DefaultHttpClient();
              */
/*  HttpPost postRequest = new HttpPost(
                        "http://10.0.2.2/cfc/iphoneWebservice.cfc?returnformat=json&amp;method=testUpload");*//*


                HttpPost postRequest = new HttpPost(
                        "https://dev1.eu-gb.mybluemix.net/upload");


                ByteArrayBody bab = new ByteArrayBody(data, "forest.jpg");
                // File file= new File("/mnt/sdcard/forest.png");
                // FileBody bin = new FileBody(file);
                MultipartEntity reqEntity = new MultipartEntity(
                        HttpMultipartMode.BROWSER_COMPATIBLE);
                reqEntity.addPart("uploaded", bab);
                reqEntity.addPart("photoCaption", new StringBody("sfsdfsdf"));
                postRequest.setEntity(reqEntity);
                HttpResponse response = httpClient.execute(postRequest);
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        response.getEntity().getContent(), "UTF-8"));
                String sResponse;
                StringBuilder s = new StringBuilder();

                while ((sResponse = reader.readLine()) != null) {
                    s = s.append(sResponse);
                }
                System.out.println("Response: " + s);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image)
        {


        }
    }
*/

}
