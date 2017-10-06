package com.helio.silentsecret.activities;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SlidingDrawer;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.helio.silentsecret.BuildConfig;
import com.helio.silentsecret.EncryptionDecryption.CryptLib;
import com.helio.silentsecret.R;
import com.helio.silentsecret.Services.BoundService;
import com.helio.silentsecret.Services.InactiveNotification;
import com.helio.silentsecret.StaticObjectDTO.StataicObjectDTO;
import com.helio.silentsecret.WebserviceDTO.FindNameDTO;
import com.helio.silentsecret.WebserviceDTO.FindbyCreateSecUserDTO;
import com.helio.silentsecret.WebserviceDTO.FindbyNameDTO;
import com.helio.silentsecret.WebserviceDTO.FindbynameObjectDTO;
import com.helio.silentsecret.WebserviceDTO.GetLastPostdateDTO;
import com.helio.silentsecret.WebserviceDTO.GetLastPostdateObjectDTO;
import com.helio.silentsecret.WebserviceDTO.GetPetConditionDTO;
import com.helio.silentsecret.WebserviceDTO.IfriendSecretpushNotiDataDTO;
import com.helio.silentsecret.WebserviceDTO.PetAvtarInfoDTO;
import com.helio.silentsecret.WebserviceDTO.SetPetConditionDTO;
import com.helio.silentsecret.WebserviceDTO.SetPetInfoDTO;
import com.helio.silentsecret.WebserviceDTO.SetPetInfoObjectDTO;
import com.helio.silentsecret.WebserviceDTO.SetVerifyFlagConditionDTO;
import com.helio.silentsecret.WebserviceDTO.SetVerifyFlagDTO;
import com.helio.silentsecret.WebserviceDTO.SetVerifyFlagObjectDTO;
import com.helio.silentsecret.WebserviceDTO.StaticDataDTO;
import com.helio.silentsecret.WebserviceDTO.StaticObjectDTO;
import com.helio.silentsecret.adapters.Tutorial_adapter;
import com.helio.silentsecret.adapters.ViewPagerMainAdapter;
import com.helio.silentsecret.appCounsellingDTO.CheckUserSessionDTO;
import com.helio.silentsecret.appCounsellingDTO.CheckUserSessionDataDTO;
import com.helio.silentsecret.appCounsellingDTO.CheckUserSessionObjectDTO;
import com.helio.silentsecret.appCounsellingDTO.CommonRequestTypeDTO;
import com.helio.silentsecret.appCounsellingDTO.FinalObjectDTO;
import com.helio.silentsecret.callbacks.FilterUpdateCallback;
import com.helio.silentsecret.connection.ConnectionDetector;
import com.helio.silentsecret.connection.IfriendRequest;
import com.helio.silentsecret.controller.Controller;
import com.helio.silentsecret.dialogs.AccessDialog;
import com.helio.silentsecret.dialogs.CustomDialog;
import com.helio.silentsecret.dialogs.ProgressDialog;
import com.helio.silentsecret.fragments.MineSecretsFragment;
import com.helio.silentsecret.fragments.SearchFragment;
import com.helio.silentsecret.models.AllIfriendDTO;
import com.helio.silentsecret.models.AllIfriendobjectDTO;
import com.helio.silentsecret.models.IfriendSecretPushDTO;
import com.helio.silentsecret.models.IfriendsecrtpushobjectDTO;
import com.helio.silentsecret.pushnotification.GCMPushNotifHandler;
import com.helio.silentsecret.pushnotification.GcmIntentService;
import com.helio.silentsecret.utils.AppSession;
import com.helio.silentsecret.utils.Constants;
import com.helio.silentsecret.utils.KeyboardUtil;
import com.helio.silentsecret.utils.Preference;
import com.helio.silentsecret.utils.TimeUtil;
import com.helio.silentsecret.utils.ToastUtil;
import com.helio.silentsecret.views.OwnPager;
import com.nineoldandroids.animation.Animator;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.jsoup.Jsoup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends BaseActivity implements
        View.OnClickListener, ViewPager.OnPageChangeListener
{

    private double latitude;
    private double longitude;
    Location location;

    RelativeLayout landing_page = null, ruby_code_layout = null;
    ImageView skip = null;

    TextView special_code = null, submit_button = null , privacy_policy = null;

    String code = "";
    EditText edt_search = null;
    SlidingDrawer simpleSlidingDrawer;







    LinearLayout whole_layout = null;

    public static StataicObjectDTO stataicObjectDTO = null;

    static MainActivity mainActivity = null;

    public static PetAvtarInfoDTO petAvtarInfoDTO = null;


    public static int scratch_count = 10;
    public static int comments_count = 0;
    boolean is_done_in_oncreate = false;
    AllIfriendDTO allIfriendDTO = null;
    public static boolean is_flag = false;
    public static String pet_name = "";
    public static boolean is_From_feeling_post = false;

    public static boolean is_from_secret_post = false;

    public static boolean is_from_login = true;

    public static boolean is_Refresh_Latest = false;

    public static boolean is_from_commNotif = false;
    public static String getVer = "";
    int versionCode = BuildConfig.VERSION_CODE;
    String versionName = BuildConfig.VERSION_NAME;
    public static boolean isIAMVerified = false;
    SimpleDateFormat df = new SimpleDateFormat("dd");
    SimpleDateFormat mmddff = new SimpleDateFormat("MM/dd/yyyy");
    private static int width1 = 0, height1;
    String formattedDate = " ";
    Calendar c = null;
    SharedPreferences myPrefs;


    public static String[] petnamearray = {"Monkey", "Panda", "Horse", "Rabbit", "Donkey", "Sheep", "Deer", "Tiger", "Parrot", "Meerkat", "Mermaid",
            "Cat", "Dog", "Unicorn", "Dragon", "Dinosaur", "Starfish"};
    public static boolean is_booking = false;

   // public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");

    private String get_date = "";
    public static String AgencyId = "";
    static TextView runAnime = null, settings = null ;

    private int width = 0, height;


    public static int session_left = 0;
    ImageView search_button = null, notification_button = null, home_button = null;

    public static ArrayList<String> users = null;

    public static ArrayList<String> scratchscretid = null;
TextView deep_filter = null , seeall_filter ,tense_filter ,light_filter ;
    public static boolean is_running = false;


    public interface OnReplace {
        void runMood();
        void runRoom();
    }

    public OnReplace mMoodRunner;


    private static OwnPager mMainPager;
    private ViewPagerMainAdapter adapter;


    private static FilterUpdateCallback mLatestUpdate;




    //private EditText mSearchEdit;


    private boolean isKeyBoard = false;

    private Handler mActivityHandler;
    private Runnable mActivityRunnable;

    public static String is_from_push_noti = "";


    private static Handler mBannerHandler;
    private static Runnable mBannerRunnable;

    LinearLayout viewPagerCountDots = null;

    Tutorial_adapter mAdapter = null;
    ViewPager tutorial_viewpager = null;

    public String searchText = null;




    private PagerTabStrip tabStrip;


    GCMPushNotifHandler mGcmPushNotifHandler;
    private boolean FROMNOTIF = false, UNFRIEND = false, APPCOUNSELLING = false, SECRET_SHARING = false, ENTERTAINMENT_GLIMPSE = false, TRENDING_PAGE = false;
    private boolean CHAT = false, LATEST_FILTERING = false, RATE_SCHOOL_COLLEGE = false, STATS_PAGE = false;
    private boolean USERNAME = false, MY_SECRET_PAGE = false, GET_SUPPORT_PAGE = false;
    private boolean SECRET = false, MOOD_PAGE = false, LIFESTYLE_GLIMPSE = false, NEWS_GLIMPSE = false, FITNESS_TRACKING = false;
    public static Context ct;
    public static boolean is_chat = false;
    TextView loaddata = null;



    public static ArrayList<String> userListNamesOnlyWaiting = new ArrayList<>();
    public static CustomDialog cd;

    LinearLayout pet_main_layout = null ,filter_layout = null;

    private static View view = null;

    RelativeLayout pet_layout = null, petlayout;

ImageView exit_tutorials = null , hand_icon = null;
    TextView skip_pet, done_pet = null,  search_ok = null;

    public static int oxygen = 10, food = 10, water = 10;
    public static ImageView petimage = null;
    String petname = "";
    public static TextView oxygenlevel;
    TextView show_safeguard, disclaimer_ok, safeguard_ok;
    public static ProgressBar water_progress, food_progress;

       //public static RelativeLayout scratch_layout = null;
    //public static int[] colorsking = {R.drawable.circle_shape, R.drawable.circle_shape1, R.drawable.circle_shape2, R.drawable.circle_shape3, R.drawable.circle_shape4, R.drawable.circle_shape5, R.drawable.circle_shape6, R.drawable.circle_shape7, R.drawable.circle_shape8, R.drawable.circle_shape9};

    RelativeLayout viewPagerIndicator = null, disclaimer_layout = null, safeguard_layout = null;

    public static String enc_username = "";
    boolean is_from_notification = false, is_from_tutorials = false;



    TextView trending_tab_selected = null, happy_tab_selected = null,
            glimplse_tab_selected = null, seeall_tab_selected = null;

    TextView trending_tab = null, happy_tab = null,
            glimplse_tab = null, seeall_tab = null , chat_boat = null;

    LinearLayout tab_layout = null;
RelativeLayout tutor_layout = null , location_detecter = null;
    TextView shaaremood = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ct = this;
        landing_page = (RelativeLayout) findViewById(R.id.landing_page);
        location_detecter = (RelativeLayout) findViewById(R.id.location_detecter);
        try {
            String userna = AppSession.getValue(ct, Constants.USER_NAME);
            if (userna == null || userna.equalsIgnoreCase(""))
            {
                enc_username = AppSession.getValue(ct,Constants.GUEST_USER_NAME);
                String done_landing = AppSession.getValue(ct,Constants.DONE_LANDING_SCREEN);
                if (done_landing == null || done_landing.equalsIgnoreCase(""))
                {
                    landing_page.setVisibility(View.VISIBLE);
                }
            }
            else
            enc_username = CryptLib.encrypt(AppSession.getValue(ct, Constants.USER_NAME));
        } catch (Exception e)
        {

        }

        try {




            try {
                latitude = Double.parseDouble(Preference.getUserLat());
                longitude = Double.parseDouble(Preference.getUserLon());

            } catch (Exception e) {
                e.printStackTrace();
            }


            if (longitude == 0.0 && latitude == 0.0)
            {
                location_detecter.setVisibility(View.VISIBLE);
                LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                if (location == null)
                    location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);


                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 10, new android.location.LocationListener()
                {
                    @Override
                    public void onLocationChanged(Location location)
                    {
                        longitude = location.getLongitude();
                        latitude = location.getLatitude();
                        Preference.saveUserLat("" + latitude);
                        Preference.saveUserLon("" + longitude);

                        location_detecter.setVisibility(View.GONE);
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
            }



            location_detecter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        latitude = Double.parseDouble(Preference.getUserLat());
                        longitude = Double.parseDouble(Preference.getUserLon());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    if (longitude == 0.0 && latitude == 0.0)
                    {
                        location_detecter.setVisibility(View.VISIBLE);
                        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                        location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                        if (location == null)
                            location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                        if (location == null)
                        {


                                try {
                                    AlertDialog.Builder dialog2 = new AlertDialog.Builder(ct);
                                    dialog2.setMessage(getResources().getString(R.string.gps_guest_enabled));
                                    dialog2.setPositiveButton(getResources().getString(R.string.open_location_settings), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                            // TODO Auto-generated method stub
                                            Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                            startActivity(myIntent);
                                            //get gps
                                        }
                                    });

                                    dialog2.setNegativeButton(getString(R.string.Cancel), new DialogInterface.OnClickListener() {

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



                        }
                        else
                        {


                                longitude = location.getLongitude();
                                latitude = location.getLatitude();
                                Preference.saveUserLat("" + latitude);
                                Preference.saveUserLon("" + longitude);

                            location_detecter.setVisibility(View.GONE);

                        }

                        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 10, new android.location.LocationListener()
                        {
                            @Override
                            public void onLocationChanged(Location location)
                            {
                                longitude = location.getLongitude();
                                latitude = location.getLatitude();
                                Preference.saveUserLat("" + latitude);
                                Preference.saveUserLon("" + longitude);

                                location_detecter.setVisibility(View.GONE);
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
                    }
                    else
                        location_detecter.setVisibility(View.GONE);
                }
            });

            simpleSlidingDrawer =(SlidingDrawer) findViewById(R.id.slidingDrawer); // initiate the SlidingDrawer
            simpleSlidingDrawer.open();

            simpleSlidingDrawer.setOnDrawerCloseListener(new SlidingDrawer.OnDrawerCloseListener()
            {
                @Override
                public void onDrawerClosed()
                {
                    landing_page.setVisibility(View.GONE);

                    AppSession.save(ct,Constants.DONE_LANDING_SCREEN,"done");

                    code = AppSession.getValue(ct, Constants.USER_CODE);
                    if (code == null || code.equalsIgnoreCase(""))
                    {
                        try {
                            AlertDialog.Builder dialog2 = new AlertDialog.Builder(ct);
                            dialog2.setMessage(getResources().getString(R.string.have_a_code));
                            dialog2.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface paramDialogInterface, int paramInt)
                                {
                                    paramDialogInterface.dismiss();
                                    ruby_code_layout.setVisibility(View.VISIBLE);
                                }
                            });

                            dialog2.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                    // TODO Auto-generated method stub
                                    paramDialogInterface.dismiss();

                                }
                            });

                            AlertDialog gps = dialog2.create();
                            gps.setCanceledOnTouchOutside(false);
                            gps.show();
                        } catch (Exception e) {


                            e.printStackTrace();
                        }
                    }



                }
            });

            ruby_code_layout = (RelativeLayout) findViewById(R.id.ruby_code_layout);
            skip = (ImageView) findViewById(R.id.skip);
            special_code = (TextView) findViewById(R.id.special_code);
            privacy_policy = (TextView) findViewById(R.id.privacy_policy);
            submit_button = (TextView) findViewById(R.id.submit_button);
            chat_boat = (TextView) findViewById(R.id.chat_boat);
            edt_search = (EditText) findViewById(R.id.edt_search);


            chat_boat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder updateAlert = new AlertDialog.Builder(ct);
                    updateAlert.setIcon(R.drawable.ic_launcher);
                    updateAlert.setTitle("Cypher");
                    updateAlert.setMessage("Chatbot coming soon");
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
            });


            privacy_policy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    runPolicy();
                }
            });

            special_code.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ruby_code_layout.setVisibility(View.VISIBLE);

                    edt_search.setFocusable(true);
                    edt_search.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(edt_search, InputMethodManager.SHOW_IMPLICIT);
                }
            });

            edt_search.addTextChangedListener(new TextWatcher() {

                public void afterTextChanged(Editable s) {
                }

                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                }

                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {
                    if (s.length() > 0) {
                        submit_button.setText("Submit");
                    } else
                        submit_button.setText("Cancel");

                }
            });

            submit_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    code = edt_search.getText().toString().trim();
                    if (code != null && !code.equalsIgnoreCase("")) {
                        new CHeckRubyCode().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    } else
                        ruby_code_layout.setVisibility(View.GONE);

                    KeyboardUtil.hideKeyBoard(edt_search, ct);

                }
            });

        } catch (Exception e) {

            e.printStackTrace();
        }

        try {
            mainActivity = this;
            is_from_commNotif = false;
            is_booking = false;
            c = Calendar.getInstance();


            users = new ArrayList<>();
            whole_layout = (LinearLayout) findViewById(R.id.whole_layout);
            tab_layout = (LinearLayout) findViewById(R.id.tab_layout);
           // mWebView = (WebView) findViewById(R.id.feed_view_vb);

            pet_main_layout = (LinearLayout) findViewById(R.id.pet_main_layout);
            filter_layout = (LinearLayout) findViewById(R.id.filter_layout);
            viewPagerCountDots = (LinearLayout) findViewById(R.id.viewPagerCountDots);
            pet_layout = (RelativeLayout) findViewById(R.id.pet_layout);
            tutor_layout = (RelativeLayout) findViewById(R.id.tutor_layout);
            disclaimer_layout = (RelativeLayout) findViewById(R.id.disclaimer_layout);
            safeguard_layout = (RelativeLayout) findViewById(R.id.safeguard_layout);
            skip_pet = (TextView) findViewById(R.id.skip_pet);
            done_pet = (TextView) findViewById(R.id.done_pet);





            view = findViewById(R.id.banner_text);
            petimage = (ImageView) findViewById(R.id.petimage);
            petlayout = (RelativeLayout) findViewById(R.id.petlayout);
            water_progress = (ProgressBar) findViewById(R.id.water_progress);
            food_progress = (ProgressBar) findViewById(R.id.food_progress);
            oxygenlevel = (TextView) findViewById(R.id.oxygenlevel);
            disclaimer_ok = (TextView) findViewById(R.id.disclaimer_ok);
            safeguard_ok = (TextView) findViewById(R.id.safeguard_ok);
            settings = (TextView) findViewById(R.id.settings);
            show_safeguard = (TextView) findViewById(R.id.show_safeguard);
            shaaremood = (TextView) findViewById(R.id.shaaremood);


            viewPagerIndicator = (RelativeLayout) findViewById(R.id.viewPagerIndicator);

            search_button = (ImageView) findViewById(R.id.search_button);
            exit_tutorials = (ImageView) findViewById(R.id.exit_tutorials);
            hand_icon = (ImageView) findViewById(R.id.hand_icon);
            notification_button = (ImageView) findViewById(R.id.notification_button);
            home_button = (ImageView) findViewById(R.id.home_button);

            tutorial_viewpager = (ViewPager) findViewById(R.id.tutorial_viewpager);

            trending_tab_selected = (TextView) findViewById(R.id.trending_tab_selected);
            happy_tab_selected = (TextView) findViewById(R.id.happy_tab_selected);
            glimplse_tab_selected = (TextView) findViewById(R.id.glimpse_tab_selected);
            seeall_tab_selected = (TextView) findViewById(R.id.seeall_tab_selected);

            deep_filter = (TextView) findViewById(R.id.deep_filter);
            light_filter = (TextView) findViewById(R.id.light_filter);
            tense_filter = (TextView) findViewById(R.id.tense_filter);
            seeall_filter = (TextView) findViewById(R.id.see_all_filter);

            trending_tab = (TextView) findViewById(R.id.trending_tab);
            happy_tab = (TextView) findViewById(R.id.happy_tab);
            glimplse_tab = (TextView) findViewById(R.id.glimpse_tab);
            seeall_tab = (TextView) findViewById(R.id.seeall_tab);

            int state = Preference.getFilter();

            if (state == Constants.STATE_SEE_ALL)
            {
                seeall_tab.setBackgroundResource(R.drawable.seeall_icon);
            } else if (state == Constants.STATE_LIGHT)
            {
                seeall_tab.setBackgroundResource(R.drawable.light_icon);

            } else if (state == Constants.STATE_DEEP)
            {
                seeall_tab.setBackgroundResource(R.drawable.deep_icon);
            } else if (state == Constants.STATE_TENSE)
            {
                seeall_tab.setBackgroundResource(R.drawable.tense_icon);
            }


            petlayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String userna = AppSession.getValue(ct, Constants.USER_NAME);
                    if (userna == null || userna.equalsIgnoreCase(""))
                    {
                        Intent intent = new Intent(MainActivity.this, SignUpDialogActivity.class);
                        startActivity(intent);
                    }
                    else
                    {
                        AccessMysecret();
                    }
                }
            });
            deep_filter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    filter_layout.setVisibility(View.GONE);
                    Preference.saveFilter(Constants.STATE_DEEP);
                    seeall_tab.setBackgroundResource(R.drawable.deep_icon);
                    MainActivity.is_Refresh_Latest = true;
                    mMainPager.setCurrentItem(2);
                    mLatestUpdate.onUpdate();

                }
            });
            light_filter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    filter_layout.setVisibility(View.GONE);
                    Preference.saveFilter(Constants.STATE_LIGHT);
                    seeall_tab.setBackgroundResource(R.drawable.light_icon);
                    MainActivity.is_Refresh_Latest = true;
                    mMainPager.setCurrentItem(2);
                    mLatestUpdate.onUpdate();
                }
            });
            tense_filter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    filter_layout.setVisibility(View.GONE);
                    Preference.saveFilter(Constants.STATE_TENSE);
                    seeall_tab.setBackgroundResource(R.drawable.tense_icon);
                    MainActivity.is_Refresh_Latest = true;
                    mMainPager.setCurrentItem(2);
                    mLatestUpdate.onUpdate();
                }
            });
            seeall_filter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    filter_layout.setVisibility(View.GONE);
                    Preference.saveFilter(Constants.STATE_SEE_ALL);
                    seeall_tab.setBackgroundResource(R.drawable.seeall_icon);
                    MainActivity.is_Refresh_Latest = true;
                    mMainPager.setCurrentItem(2);
                    mLatestUpdate.onUpdate();
                }
            });

            tab_layout.setOnClickListener(this);

            final String tutor = AppSession.getValue(ct,Constants.CYPHER_TUTORIALS );

            if(tutor == null || tutor.equalsIgnoreCase(""))
            {
                tutor_layout.setVisibility(View.VISIBLE);
                is_from_tutorials = true;
            }

           // tutor_layout.setVisibility(View.VISIBLE);

            exit_tutorials.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tutor_layout.setVisibility(View.GONE);
                    is_from_tutorials = false;
                    AppSession.save(ct,Constants.CYPHER_TUTORIALS ,"true");
                }
            });



            hand_icon.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent)
                {
                    if (motionEvent.getAction() == MotionEvent.ACTION_UP)
                    {
                        tutorial_viewpager.setVisibility(View.VISIBLE);
                        viewPagerIndicator.setVisibility(View.VISIBLE);
                        hand_icon.setVisibility(View.GONE);
                    }
                    return true;
                }
            });

            showTutorials();
            trending_tab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mMainPager.setCurrentItem(0);

                }
            });

            happy_tab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mMainPager.setCurrentItem(1);

                }
            });


            glimplse_tab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {


                        mMainPager.setCurrentItem(3);

                }
            });


            seeall_tab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  //  mMainPager.setCurrentItem(2);
                    filter_layout.setVisibility(View.VISIBLE);

                }
            });

            seeall_tab.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    filter_layout.setVisibility(View.VISIBLE);
                    return true;
                }
            });


            is_done_in_oncreate = true;

            showProgress();


            show_safeguard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    runPolicy();
                    safeguard_layout.setVisibility(View.GONE);
                }
            });


            shaaremood.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String userna = AppSession.getValue(ct, Constants.USER_NAME);
                    if (userna == null || userna.equalsIgnoreCase(""))
                    {
                        Intent intent = new Intent(MainActivity.this, SignUpDialogActivity.class);
                        startActivity(intent);
                    }
                    else {
                        postEmotion();
                    }
                }
            });
            disclaimer_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            safeguard_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            String userna = AppSession.getValue(ct, Constants.USER_NAME);
            if (userna != null && !userna.equalsIgnoreCase(""))
            {
                new ChecUserSessionfirsttime().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                new CheckFlagverified().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

            }

            String returing = AppSession.getValue(ct, Constants.USER_RETURNING);
            if (returing == null || returing.equalsIgnoreCase("")) {
                AppSession.save(ct, Constants.USER_RETURNING, "1");
            } else if (returing.equalsIgnoreCase("1")) {
                new UpdateReturnuing().execute();
                AppSession.save(ct, Constants.USER_RETURNING, "2");
            }

            safeguard_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    runPolicy();
                    safeguard_layout.setVisibility(View.GONE);
                }
            });

            disclaimer_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Preference.saveUserDisclaimer(true);
                    disclaimer_layout.setVisibility(View.GONE);
                    new UpdateDisclaimer().execute();
                }
            });

            try {

                if (getIntent().hasExtra("MY_SECRET_PAGE")) {
                    MY_SECRET_PAGE = getIntent().getBooleanExtra("MY_SECRET_PAGE", false);
                    if (MY_SECRET_PAGE) {

                        MineSecretsFragment.envelop_secre_id = AppSession.getValue(ct, Constants.COMMENT_SECRET_ID);

                        showPinDialog();
                        is_from_notification = true;
                    }
                } else if (getIntent().hasExtra("COMMENT")) {
                    {
                        SearchFragment.secret_id = AppSession.getValue(ct, Constants.COMMENT_SECRET_ID);

                        if (SearchFragment.secret_id != null && !SearchFragment.secret_id.equalsIgnoreCase("")) {
                            is_from_notification = true;
                            is_from_commNotif = true;
                            is_done_in_oncreate = true;
                            AppSession.save(ct, Constants.COMMENT_SECRET_ID, "");
                            makeSearchwithid(SearchFragment.secret_id);

                        } else {
                            SearchFragment.secret_id = GcmIntentService.mSecretid;
                            if (SearchFragment.secret_id != null && !SearchFragment.secret_id.equalsIgnoreCase("")) {
                                is_from_notification = true;
                                is_from_commNotif = true;
                                is_done_in_oncreate = true;
                                AppSession.save(ct, Constants.COMMENT_SECRET_ID, "");
                                makeSearchwithid(SearchFragment.secret_id);

                            }
                        }
                    }
                } else if (getIntent().hasExtra(GcmIntentService.mSecretid)) {

                    SECRET = getIntent().getBooleanExtra(GcmIntentService.mSecretid, false);
                    if (SECRET) {
                        SearchFragment.secret_id = AppSession.getValue(ct, Constants.COMMENT_SECRET_ID);
                        if (SearchFragment.secret_id != null && !SearchFragment.secret_id.equalsIgnoreCase("")) {
                            is_from_notification = true;
                            is_from_commNotif = true;
                            is_done_in_oncreate = true;
                            AppSession.save(ct, Constants.COMMENT_SECRET_ID, "");
                            makeSearchwithid(SearchFragment.secret_id);

                        }
                    }
                } else if (getIntent().hasExtra("FINDSECRET")) {
                    SearchFragment.secret_id = getIntent().getStringExtra("SECRETID");
                    is_from_notification = true;
                    is_from_commNotif = true;
                    is_done_in_oncreate = true;
                    AppSession.save(ct, Constants.COMMENT_SECRET_ID, "");
                    makeSearchwithid(SearchFragment.secret_id);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


            // getAppintment();
           // search_ok = (TextView) findViewById(R.id.search_ok);


            AgencyId = "";
            if (scratchscretid != null)
                scratchscretid = null;


            scratchscretid = new ArrayList<>();

            notification_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  /*  if (isKeyBoard)
                        KeyboardUtil.hideKeyBoard(mSearchEdit, MainActivity.this);

*/
                    if (ConnectionDetector.isNetworkAvailable(ct))
                    {

                        String userna = AppSession.getValue(ct, Constants.USER_NAME);
                        if (userna == null || userna.equalsIgnoreCase(""))
                        {
                            Intent intent = new Intent(MainActivity.this, SignUpDialogActivity.class);
                            startActivity(intent);
                        }
                        else {

                            findViewById(R.id.search_frame).setVisibility(View.GONE);
                            showNotificationAccessDialog();
                        }

                    } else {
                        new ToastUtil(ct, "Please check your internet connection.");
                    }


                }
            });

            home_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    findViewById(R.id.search_frame).setVisibility(View.GONE);
                    mMainPager.setCurrentItem(1);
                }
            });



        } catch (Exception e) {
            e.printStackTrace();
        }
        try {

            init();

            loaddata = new TextView(this);
            runAnime = new TextView(this);



        } catch (Exception e) {
            e.printStackTrace();
        }




        skip_pet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pet_layout.setVisibility(View.GONE);
            }
        });
        done_pet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (select_index >= 0)
                {

                    new SetPetInfo().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    pet_layout.setVisibility(View.GONE);




                } else {
                    Toast.makeText(ct, "Please select Your pet", Toast.LENGTH_SHORT).show();
                }

            }
        });

        pet_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



        try {
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            width = displayMetrics.widthPixels;
            height = displayMetrics.heightPixels;
            width1 = width;
            height1 = height;
        } catch (Exception e) {
            if (width <= 0) {
                Display display = getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);
                width = size.x;
                height = size.y;
            }
            e.printStackTrace();
        }


        Timer timerObj = new Timer();
        TimerTask timerTaskObj = new TimerTask()
        {
            public void run() {
                try {
                    if (stataicObjectDTO == null)
                        new GetStaticData().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


                    if (MainActivity.is_from_login) {
                        if (ConnectionDetector.isNetworkAvailable(ct)) {
                            mGcmPushNotifHandler = new GCMPushNotifHandler((Activity) ct);
                            mGcmPushNotifHandler.register();

                        } else {
                            new ToastUtil(ct, "Please check your internet connection.");
                        }
                    }

                    myPrefs = PreferenceManager.getDefaultSharedPreferences(ct);

                    formattedDate = df.format(c.getTime());


                    if (getIntent() != null) {


                        try {

                            if (getIntent().hasExtra("FROMNOTIF")) {
                                FROMNOTIF = getIntent().getBooleanExtra("FROMNOTIF", false);
                                if (FROMNOTIF) {
                                    is_from_notification = true;
                                    replaceDialog(Constants.ACCESS_IFRIEND);
                                    ChatFriends.is_frompush = true;
                                }
                            }


                            if (getIntent().hasExtra("UNFRIEND")) {
                                UNFRIEND = getIntent().getBooleanExtra("UNFRIEND", false);
                                if (UNFRIEND) {
                                    is_from_notification = true;
                                    replaceDialog(Constants.ACCESS_IFRIEND);
                                    ChatFriends.is_frompush = false;
                                }
                            }


                            if (getIntent().hasExtra("APPCOUNSELLING")) {
                                APPCOUNSELLING = getIntent().getBooleanExtra("APPCOUNSELLING", false);
                                if (APPCOUNSELLING) {
                                    is_from_notification = true;
                                    QRAccess();
                                }
                            }

                            if (getIntent().hasExtra("SECRET_SHARING")) {
                                SECRET_SHARING = getIntent().getBooleanExtra("SECRET_SHARING", false);
                                if (SECRET_SHARING) {
                                    is_from_notification = true;
                                    startActivityForResult(new Intent(MainActivity.this, CreateSecretActivity.class), 0);
                                }
                            }

                            if (getIntent().hasExtra("RATE_SCHOOL_COLLEGE")) {
                                RATE_SCHOOL_COLLEGE = getIntent().getBooleanExtra("RATE_SCHOOL_COLLEGE", false);
                                if (RATE_SCHOOL_COLLEGE) {
                                    startRateSchool();
                                    is_from_notification = true;
                                }
                            }



                            try {
                                if (getIntent().hasExtra("MOOD_PAGE")) {
                                    MOOD_PAGE = getIntent().getBooleanExtra("MOOD_PAGE", false);
                                    if (MOOD_PAGE) {
                                        is_from_notification = true;
                                        Controller.runGlimpse(Controller.MOOD, MainActivity.this, true);
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }





                            if (getIntent().hasExtra("GET_SUPPORT_PAGE")) {
                                GET_SUPPORT_PAGE = getIntent().getBooleanExtra("GET_SUPPORT_PAGE", false);
                                if (GET_SUPPORT_PAGE) {
                                    is_from_notification = true;
                                    startActivity(new Intent(MainActivity.this, SupportActivity.class));
                                }
                            }


                            if (getIntent().hasExtra("CHAT")) {
                                CHAT = getIntent().getBooleanExtra("CHAT", false);
                                if (CHAT) {
                                    is_from_notification = true;
                                    replaceDialog(Constants.ACCESS_IFRIEND);
                                }
                            }




                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else
                        is_from_notification = false;


                    get_date = myPrefs.getString("Date", "Nothing");

                    String userna = AppSession.getValue(ct, Constants.USER_NAME);
                    if (userna != null && !userna.equalsIgnoreCase(""))
                    {

                        Timer timerObj1 = new Timer();
                        TimerTask timerTaskObj1 = new TimerTask() {
                            public void run() {
                                try {
                                    if (ConnectionDetector.isNetworkAvailable(ct)) {
                                        new Getallfriend().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                                    }
                                    new GetLastPostDate().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        };
                        timerObj1.schedule(timerTaskObj1,500);



                    }
                    SetUserInactiveAlarm();


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        timerObj.schedule(timerTaskObj, 2500);

        if (getIntent().hasExtra(GcmIntentService.mUsernameSecret))
        {
            USERNAME = getIntent().getBooleanExtra(GcmIntentService.mUsernameSecret, false);
            if (USERNAME) {
                is_from_notification = true;
                String mUser = GcmIntentService.mUsernameSecret;
                acceptNotification(mUser);
            }
        }


        if (is_from_notification == false)
        {
            String userna = AppSession.getValue(ct, Constants.USER_NAME);
            if (userna != null && !userna.equalsIgnoreCase(""))
            {
                pet_name = AppSession.getValue(ct, Constants.USER_PET_NAME);
                if (pet_name == null || pet_name.equalsIgnoreCase("")  || pet_name.equalsIgnoreCase("null")) {
                    //new GetPetInfo().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    pet_layout.setVisibility(View.VISIBLE);
                    pet_name = "";
                    drawpetlist();
                }

            }
        }


    }

    public void SetUserInactiveAlarm() {
        try {


            Calendar c = Calendar.getInstance();
            String todaysdate = mmddff.format(c.getTime());
            AppSession.save(ct, Constants.USERLASTATTAND, todaysdate);
            Intent alarmIntent = new Intent(ct, InactiveNotification.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(ct, 0, alarmIntent, 0);
            try {
                AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.add(Calendar.DAY_OF_MONTH, 14);
                manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

            } catch (Exception e) {
                e.printStackTrace();
            }


        } catch (Exception e)

        {
            e.printStackTrace();
        }
    }



    public static void postEmotion() {
        try {
            if (cd != null)
                cd = null;

            cd = new CustomDialog(mainActivity);
            cd.setCanceledOnTouchOutside(false);
            cd.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    IfriendSecretPushDTO ifriendSecretPushDTO = null;
    static String mFriend = "";

    private void acceptNotification(String mUser) {

        mFriend = mUser;

        AlertDialog.Builder buildervalid = new AlertDialog.Builder(MainActivity.this);
        buildervalid.setIcon(R.drawable.ic_launcher);
        buildervalid.setTitle("iFriend");
        buildervalid.setMessage("Would you like us to notify you when your internet friend shares a secret?");

        buildervalid.setPositiveButton(
                "Yes, Please",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();

                        new IFirendShareSecretNotification().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                    }
                });

        buildervalid.setNegativeButton(
                "No, Thanks",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });


        AlertDialog alert1valid = buildervalid.create();
        alert1valid.setCanceledOnTouchOutside(false);
        alert1valid.show();
    }


    public void runIFriend() {
        startActivity(new Intent(MainActivity.this, ChatFriends.class));
    }




    public void setupFilterCallback(FilterUpdateCallback data) {
        this.mLatestUpdate = data;
    }

    private void init() {

        try {

            findViewById(R.id.main_progress_bg_iv).setOnClickListener(this);

            mActivityHandler = new Handler();
            mActivityRunnable = new Runnable() {
                @Override
                public void run() {
                    YoYo.with(Techniques.FadeOutUp).duration(300).playOn(findViewById(R.id.activity_text));
                }
            };

            mBannerHandler = new Handler();
            mBannerRunnable = new Runnable() {
                @Override
                public void run() {
                    YoYo.with(Techniques.FadeOut).duration(300).playOn(findViewById(R.id.banner_text));
                }
            };


            if (mMainPager != null)
                mMainPager = null;
            mMainPager = (OwnPager) this.findViewById(R.id.pager);

            adapter = new ViewPagerMainAdapter(getSupportFragmentManager());
            mMainPager.setOffscreenPageLimit(3);
            mMainPager.setAdapter(adapter);
            mMainPager.setOnPageChangeListener(this);


            if (getIntent().hasExtra("TRENDING_PAGE")) {
                TRENDING_PAGE = getIntent().getBooleanExtra("TRENDING_PAGE", false);
                if (TRENDING_PAGE) {
                    is_from_notification = true;
                    mMainPager.setCurrentItem(0);
                }
            } else if (getIntent().hasExtra("LATEST_FILTERING")) {
                LATEST_FILTERING = getIntent().getBooleanExtra("LATEST_FILTERING", false);
                if (LATEST_FILTERING) {
                    is_from_notification = true;
                    mMainPager.setCurrentItem(2);
                }
            } else {
                if (is_chat) {
                    is_from_notification = true;
                    mMainPager.setCurrentItem(3);
                } else {
                    mMainPager.setCurrentItem(1);
                }
            }
            is_chat = false;

            tabStrip = (PagerTabStrip) findViewById(R.id.pager_title_strip);
            tabStrip.setTabIndicatorColor(getResources().getColor(R.color.upper_line));

            //initLeftMenu(leftView);
           // loadDefault(true);




            findViewById(R.id.search_toggle).setOnClickListener(this);
            //  findViewById(R.id.hug_anim_root).setOnClickListener(this);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    /*private void startReferFriend() {
        startActivity(new Intent(this, ReferFriendActivity.class));
    }
*/
    private void startRateSchool() {

        try {
            SharedPreferences sharedData = Preference.getSharedPreferences();

            if (sharedData.contains(Constants.RATE_SCHOOL_CLASS)) {
                if (TimeUtil.countSixTime(Constants.RATE_SCHOOL_CLASS, sharedData))
                    startRateSchoolActivity();
                else
                    new ToastUtil(MainActivity.this, getAge() ? getString(R.string.u_can_rate_your_college) : getString(R.string.u_can_rate_school_again_in));
            } else {
                startRateSchoolActivity();
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    private void startRateSchoolActivity() {
        startActivityForResult(new Intent(this, RateSchoolActivity.class), Constants.SETTINGS_RATE_SCHOOL);
    }

    public void unverifyUser() {

        if (setVerifyFlagConditionDTO != null)
            setVerifyFlagConditionDTO = null;
        setVerifyFlagConditionDTO = new SetVerifyFlagConditionDTO(enc_username, "verified", "false");
        new UpdateVeryfiFlag().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        AppSession.save(ct, Constants.USER_VERIFIED, "false");

    }

    private void makeSearchwithid(String id) {
        try {


            if (id.isEmpty())
                return;


            YoYo.with(Techniques.FadeIn).duration(500).withListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    findViewById(R.id.search_frame).setVisibility(View.VISIBLE);
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
            }).playOn(findViewById(R.id.search_frame));


            SearchFragment.secret_id = id;

            Fragment frag = getSupportFragmentManager().findFragmentById(R.id.search_frame);
            if (!(frag instanceof SearchFragment)) {
                replaceSearch();
            }


            //    loadSearchsecretid(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    protected void onResume() {
        super.onResume();

        is_running = true;


        pet_name = AppSession.getValue(ct, Constants.USER_PET_NAME);
        if (pet_name != null && !pet_name.equalsIgnoreCase("") && !pet_name.equalsIgnoreCase("null"))
            ShowPetStatus();

        try {
            if (is_from_secret_post) {
                is_from_secret_post = false;
                mMainPager.setCurrentItem(2);
                is_Refresh_Latest = true;
                if (getIntent() != null)
                    updateTitleBanner(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void replaceSearch() {

        try {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.search_frame, new SearchFragment());
            transaction.addToBackStack(null);
            transaction.commitAllowingStateLoss();
        } catch (Exception e) {
        }
    }

    View.OnClickListener closeSearch = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            try {


                YoYo.with(Techniques.FadeOut).duration(500).withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        findViewById(R.id.search_frame).setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).playOn(findViewById(R.id.search_frame));


               /* if (isKeyBoard && mSearchEdit != null)
                    KeyboardUtil.hideKeyBoard(mSearchEdit, MainActivity.this);*/
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    };

    @Override
    public void onBackPressed() {
        try {

            showSearchBar();
        } catch (Exception e) {
            e.printStackTrace();
        }

        runAnime = null;
        super.onBackPressed();
    }





    private boolean getAge() {
        return Integer.parseInt(Preference.getUserAge()) > 15;
    }




    @Override
    public void onDestroy() {

        super.onDestroy();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {


        switch (resultCode) {
            case Constants.SETTINGS_RATE_SCHOOL:

                break;
            case Constants.SECRET_POST:
                try {
                    mMainPager.setCurrentItem(2);
                    is_Refresh_Latest = true;
                    if (getIntent() != null)
                        updateTitleBanner(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
        }

    }

    public static void updateTitleBanner(int count) {

        try {


            try {
                if (is_From_feeling_post) {
                    view.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            is_From_feeling_post = false;
                            mMainPager.setCurrentItem(2);
                            mLatestUpdate.onUpdate();
                        }
                    }, 1000);


                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            if (mBannerHandler != null)
                mBannerHandler.removeCallbacks(mBannerRunnable);

            String isverifiedornot = AppSession.getValue(ct, Constants.USER_VERIFIED);

            if (isverifiedornot != null && isverifiedornot.equalsIgnoreCase("true")) {
                ((TextView) view).setVisibility(View.GONE);
                return;
            }


            int secretsCount = 0;

            String secrets = AppSession.getValue(ct, Constants.USER_SECRETS);
            try {
                secretsCount = Integer.parseInt(secrets);
                secretsCount = secretsCount + count;
                AppSession.save(ct, Constants.USER_SECRETS, "" + secretsCount);
            } catch (Exception e) {
                e.printStackTrace();
            }

            String hugstring = AppSession.getValue(ct, Constants.USER_HUGS);
            String heartstring = AppSession.getValue(ct, Constants.USER_HEARTS);
            String me2string = AppSession.getValue(ct, Constants.USER_ME2S);

            int hugs = Integer.parseInt(hugstring);
            int hearts = Integer.parseInt(heartstring);
            int me2s = Integer.parseInt(me2string);

            int sumUp = hugs + hearts + me2s;

            if ((sumUp >= 10 && secretsCount == 5) || sumUp == 10 && secretsCount >= 5) {
                generateActionPush(ct, "You are verified! Tap on speech bubble icon to comment", null, null, null);

                if (setVerifyFlagConditionDTO != null)
                    setVerifyFlagConditionDTO = null;
                setVerifyFlagConditionDTO = new SetVerifyFlagConditionDTO(enc_username, "verified", "true");
                new UpdateVeryfiFlag().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                AppSession.save(ct, Constants.USER_VERIFIED, "true");

                return;
            } else if (sumUp > 10 && secretsCount > 5) {
                view.setVisibility(View.GONE);
            } else {
                view.setVisibility(View.VISIBLE);
            }


            YoYo.with(Techniques.FadeIn).playOn(view);

            int secretsLeft = 5 - secretsCount;
            int socialLeft = 10 - sumUp;
            String formattedText = null;

            if (secretsCount >= 5) {
                if (sumUp < 9)
                    formattedText = String.format("Give %1$d hugs, hearts and me2s to get verified for comments", socialLeft);
                else if (sumUp == 9) {
                    formattedText = String.format("Give %1$d hug, heart and me2 to get verified for comments", socialLeft);
                } else
                    isIAMVerified = true;

            } else if (secretsCount == 4) {
                if (sumUp == 9)
                    formattedText = String.format("Share one more secret and give %1$d hugs, hearts and me2s to get verified for comments", socialLeft);
                else if (sumUp < 9)
                    formattedText = String.format("Share one more secret and give %1$d hug, heart and me2 to get verified for comments", socialLeft);
                else
                    formattedText = String.format("Share one more secret to get verified for comments");
            } else {
                if (sumUp < 9)
                    formattedText = String.format("Share %1$d more secrets and give %2$d hugs, hearts and me2s to get verified for comments", secretsLeft, socialLeft);
                else if (sumUp == 9)
                    formattedText = String.format("Share %1$d more secrets and give %2$d hug, heart and me2 to get verified for comments", secretsLeft, socialLeft);
                else
                    formattedText = String.format("Share %d more secrets to get verified for comments", secretsLeft);
            }

            ((TextView) view).setText(formattedText);

            mBannerHandler.postDelayed(mBannerRunnable, 5000);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onClick(View v) {
       /* if (isKeyBoard)
            KeyboardUtil.hideKeyBoard(mSearchEdit, MainActivity.this);
*/
        switch (v.getId()) {

            case R.id.search_toggle:
                if (ConnectionDetector.isNetworkAvailable(ct))
                    checkpost();
                else {
                    new ToastUtil(ct, "Please check your internet connection.");
                }
                break;
        }
    }

    private void showPinDialog() {
        replaceDialog(Constants.ACCESS_MY_SECRETS);
    }

    private void clearDataDialog() {
        replaceDialog(Constants.ACCESS_CLEAR_SECRET);
    }

    private void showStatsDialog() {
        //  replaceDialog(Constants.ACCESS_STATS);
        startActivity(new Intent(MainActivity.this, StatsActivity.class));
    }

   /* private void showUnverifyDialog() {

        replaceDialog(Constants.ACCESS_VERIFY);
    }*/

    private void showNotificationAccessDialog() {
        replaceDialog(Constants.ACCESS_NOTIFICATIONS);
    }

    public void hideSearchBar() {
       /* try {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
            if (mSearchEdit != null)
                mSearchEdit.setEnabled(false);
        } catch (NullPointerException e) {

        }*/
    }

    public void showSearchBar() {
      /* try
       {
           getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
           if (mSearchEdit != null)
               mSearchEdit.setEnabled(true);
       }
       catch ( Exception e)
       {
           e.printStackTrace();

       }*/
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void replaceDialog(String frag) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        try {
            transaction.replace(R.id.main_content, AccessDialog.instance(frag));
            transaction.addToBackStack(null);
            transaction.commitAllowingStateLoss();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }




    public void showProgress() {
        ProgressDialog.showDialog(findViewById(R.id.main_progress_bg_iv), findViewById(R.id.main_progress_pb));
    }

    public void stopProgress() {
        ProgressDialog.hideDialog(findViewById(R.id.main_progress_bg_iv), findViewById(R.id.main_progress_pb));
    }

    public void updateActivity() {

        try {
            if (mActivityHandler != null)
                mActivityHandler.removeCallbacks(mActivityRunnable);
            YoYo.with(Techniques.FadeInDown).duration(300).playOn(findViewById(R.id.activity_text));


            String hugs = AppSession.getValue(ct, Constants.USER_HUGS);
            String hearts = AppSession.getValue(ct, Constants.USER_HEARTS);
            String me2s = AppSession.getValue(ct, Constants.USER_ME2S);


            try {
                int hugcount = 0, heartcount = 0, me2count = 0;

                hugcount = Integer.parseInt(hugs);
                heartcount = Integer.parseInt(hearts);
                me2count = Integer.parseInt(me2s);

                if (hugcount < 0) {
                    hugcount = Math.abs(hugcount);
                }


                if (heartcount < 0) {
                    heartcount = Math.abs(heartcount);
                }

                if (me2count < 0) {
                    me2count = Math.abs(me2count);
                }

                hugs = "" + hugcount;

                hearts = "" + heartcount;

                me2s = "" + me2count;


                ((TextView) findViewById(R.id.activity_text)).setText(getString(R.string.my_action_) + hugs + getString(R.string.hugs_) + hearts + getString(R.string.hearts_point) + me2s + getString(R.string.me2_s));
            } catch (Exception e) {

                ((TextView) findViewById(R.id.activity_text)).setText(getString(R.string.my_action_) + 0 + getString(R.string.hugs_) + 0 + getString(R.string.hearts_point) + 0 + getString(R.string.me2_s));

            }


            if (hugs.length() > 3) {
                hugs = hugs.charAt(0) + Constants.POINT + hugs.charAt(1) + Constants.THOUTHAND;
            }

            if (hearts.length() > 3) {
                hearts = hearts.charAt(0) + Constants.POINT + hearts.charAt(1) + Constants.THOUTHAND;
            }

            if (me2s.length() > 3) {
                me2s = me2s.charAt(0) + Constants.POINT + me2s.charAt(1) + Constants.THOUTHAND;
            }
            try {
                ((TextView) findViewById(R.id.activity_text)).setText(getString(R.string.my_action_) + hugs + getString(R.string.hugs_) + hearts + getString(R.string.hearts_point) + me2s + getString(R.string.me2_s));
            } catch (Exception e) {
                e.printStackTrace();
            }

            //  if (hugs.length() >= 0 && hearts.length() >= 0 && me2s.length() >= 0)


            mActivityHandler.postDelayed(mActivityRunnable, 2000);

            updateTitleBanner(0);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            is_running = false;
            Intent intent = new Intent(this, BoundService.class);
            stopService(intent);

            if (mActivityHandler != null) {
                mActivityHandler.removeCallbacks(mActivityRunnable);
                YoYo.with(Techniques.FadeOutDown).duration(100).playOn(findViewById(R.id.activity_text));
            }


        } catch (Exception e) {

            e.printStackTrace();
        }


    }

    public void runMineSecrets(boolean flag) {
        Intent intent = new Intent(this, MySecretsActivity.class);

        if (!flag)
            intent.putExtra(Constants.ACCESS_CLEAR_SECRET, true);

        startActivity(intent);
    }

    public void runqreader(boolean flag) {
        Intent intent = new Intent(this, CameraTestActivity.class);

        if (!flag)
            intent.putExtra(Constants.ACCESS_QR_READER, true);

        startActivity(intent);
    }


    public void Golivescreen(boolean flag) {
        Intent intent = new Intent(this, LiveCounselling.class);

        if (!flag)
            intent.putExtra(Constants.LIVE_SCREEN, true);

        startActivity(intent);
    }


    public void runbookappointment(boolean flag) {
        Intent intent = new Intent(this, CounsellerBooking.class);

        if (!flag)
            intent.putExtra(Constants.ACCESS_BOOK_APPONTMENT, true);

        startActivity(intent);
    }


    public void showratingpopup(boolean flag) {
        Intent intent = new Intent(this, LiveCounselling.class);
        LiveCounselling.is_show_rating = true;


        if (!flag)
            intent.putExtra(Constants.ACCESS_PENDING_RATING, true);

        startActivity(intent);
    }

    public void runNotifications() {
        startActivity(new Intent(this, NotificationActivity.class));
    }

    public void runPolicy() {
        startActivity(new Intent(this, PrivatePolicyActivity.class));
    }

    public void runStats() {
        startActivity(new Intent(this, StatsActivity.class));
    }



    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        try {

            if (is_from_tutorials)
            {
                for (int i = 0; i < dotsCount; i++)
                {
                    dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));
                }

                dots[position].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));


            } else
            {
                filter_layout.setVisibility(View.GONE);
                if (position == 0)
                {
                    trending_tab_selected.setVisibility(View.VISIBLE);
                    happy_tab_selected.setVisibility(View.INVISIBLE);
                    glimplse_tab_selected.setVisibility(View.INVISIBLE);
                    seeall_tab_selected.setVisibility(View.INVISIBLE);

                } else if (position == 1) {

                    trending_tab_selected.setVisibility(View.INVISIBLE);
                    glimplse_tab_selected.setVisibility(View.INVISIBLE);
                    happy_tab_selected.setVisibility(View.VISIBLE);
                    seeall_tab_selected.setVisibility(View.INVISIBLE);
                } else if (position == 2) {
                    trending_tab_selected.setVisibility(View.INVISIBLE);
                    glimplse_tab_selected.setVisibility(View.INVISIBLE);
                    happy_tab_selected.setVisibility(View.INVISIBLE);
                    seeall_tab_selected.setVisibility(View.VISIBLE);
                } else if (position == 3) {
                    trending_tab_selected.setVisibility(View.INVISIBLE);
                    happy_tab_selected.setVisibility(View.INVISIBLE);
                    glimplse_tab_selected.setVisibility(View.VISIBLE);
                    seeall_tab_selected.setVisibility(View.INVISIBLE);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPageSelected(int position) {



       /* if (position == 1)
        {
            startTracking(getString(R.string.analytics_glimpse));
            ObjectMaker.formTrack(ParseUser.getCurrentUser(),
                    getString(R.string.track_glimpse), null).saveInBackground();
        }*/
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

  /*  public void replaceFragmentInside(Fragment frag, boolean flag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (flag) {
            transaction.add(R.id.glimpse_container, frag);
        } else {
            transaction.add(R.id.happy_glimpse_container, frag);
        }
        transaction.commitAllowingStateLoss();
    }*/

    public void runMood() {
        if (mMoodRunner != null)
            mMoodRunner.runMood();
    }

    public void runRoom() {
        if (mMoodRunner != null)
            mMoodRunner.runRoom();
    }

    public void moodAccess() {
        replaceDialog(Constants.ACCESS_MOOD);
    }


    public void RoomAccess() {
        replaceDialog(Constants.ACCESS_ROOM);
    }

    public void AccessMysecret() {
        replaceDialog(Constants.ACCESS_MY_SECRETS);
    }

    public void QRAccess() {


        String sessions = "";

        sessions = AppSession.getValue(ct, Constants.USER_SESSION_LEFT);
        try {
            session_left = Integer.parseInt(sessions);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (session_left > 0 || MainActivity.is_booking) {

            replaceDialog(Constants.ACCESS_BOOK_APPONTMENT);
        } else {
            new ChecUserSession().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }

    }


    public void runHighLights() {
        if (mLatestUpdate != null)
            mLatestUpdate.onHighLightAccess();
    }


    public void runYearComments() {
        if (mLatestUpdate != null)
            mLatestUpdate.onCommentsAccess();
    }



    @Override
    protected void onStart() {
        super.onStart();


    }


    private void updateApp() {

        try {

            AlertDialog.Builder updateAlert = new AlertDialog.Builder(MainActivity.this);
            updateAlert.setIcon(R.drawable.ic_launcher);
            updateAlert.setTitle("Cypher");
            updateAlert.setMessage("Update Cypher to get new features.");
            updateAlert.setPositiveButton(
                    "Update",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();

                            final String appPackageName = getPackageName();
                            try {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                                SharedPreferences.Editor editor = myPrefs.edit();
                                editor.putString("Date", formattedDate);
                                editor.commit();
                            } catch (Exception e) {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.helio.silentsecret&hl=en")));
                                e.printStackTrace();
                            }

                        }
                    });

            updateAlert.setNegativeButton(
                    "Not Now",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            try {
                                SharedPreferences.Editor editor = myPrefs.edit();
                                editor.putString("Date", formattedDate);
                                editor.commit();
                                Ratingoopup();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
            AlertDialog alertUp = updateAlert.create();
            alertUp.setCanceledOnTouchOutside(false);
            alertUp.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }





    int select_index = -1;

    private void drawpetlist() {

        pet_layout.setVisibility(View.VISIBLE);
        pet_main_layout.removeAllViews();
        int[] petimagearray = {R.drawable.monkey_face, R.drawable.panda_face,
                R.drawable.horse_face, R.drawable.rabbit_face,
                R.drawable.donkey_face, R.drawable.sheep_face,
                R.drawable.deer_face, R.drawable.tiger_face,
                R.drawable.parrot_face, R.drawable.meerkat_face,
                R.drawable.mermain_face, R.drawable.cat_face,
                R.drawable.dog_face, R.drawable.unicorn_face,
                R.drawable.dragon_face, R.drawable.dynasore_face,
                R.drawable.starfish_face};


        final List<LinearLayout> petrowlayoutlist = new ArrayList<>();
        for (int i = 0; i < petimagearray.length; i++) {
            LinearLayout petrowlayout = new LinearLayout(ct);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, width / 6);

            petrowlayout.setLayoutParams(lp);
            petrowlayout.setBackgroundColor(Color.parseColor("#e5e5e5"));
            petrowlayout.setId(i + 1);
            TextView petimage = new TextView(ct);
            lp = new LinearLayout.LayoutParams(width / 5, width / 8);
            lp.gravity = Gravity.CENTER_VERTICAL;
            lp.setMargins(width / 25, 0, 0, 0);
            petimage.setLayoutParams(lp);
            petimage.setBackgroundResource(petimagearray[i]);

            TextView setpetname = new TextView(ct);
            lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, width / 6);
            lp.setMargins(width / 10, 0, 0, 0);
            setpetname.setGravity(Gravity.CENTER_VERTICAL);
            setpetname.setLayoutParams(lp);
            setpetname.setTextColor(Color.parseColor("#222222"));
            setpetname.setTextSize(18);
            setpetname.setText(petnamearray[i]);


            TextView firgy = new TextView(ct);
            lp = new LinearLayout.LayoutParams(width, width / 300);
            firgy.setLayoutParams(lp);
            firgy.setBackgroundColor(Color.parseColor("#555555"));

            petrowlayoutlist.add(petrowlayout);

            petrowlayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    select_index = v.getId() - 1;
                    for (int i = 0; i < petrowlayoutlist.size(); i++) {
                        if (i == select_index)
                            petrowlayoutlist.get(i).setBackgroundColor(Color.parseColor("#36bcbc"));
                        else
                            petrowlayoutlist.get(i).setBackgroundColor(Color.parseColor("#e5e5e5"));

                    }
                }
            });
            petrowlayout.addView(petimage);
            petrowlayout.addView(setpetname);
            pet_main_layout.addView(petrowlayout);
            pet_main_layout.addView(firgy);

        }
    }



    private class Getallfriend extends android.os.AsyncTask<String, String, Bitmap> {

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

                if (allIfriendDTO != null)
                    allIfriendDTO = null;


                if (userListNamesOnlyWaiting != null)
                    userListNamesOnlyWaiting = null;

                userListNamesOnlyWaiting = new ArrayList<>();

                GetPetConditionDTO getAllfriedDTO = new GetPetConditionDTO(MainActivity.enc_username);
                allIfriendDTO = new AllIfriendDTO(getAllfriedDTO);
                AllIfriendobjectDTO allIfriendobjectDTO = new AllIfriendobjectDTO(allIfriendDTO);
                response = http.Getallfriedname(allIfriendobjectDTO);


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {

            try {
                if (stataicObjectDTO != null && stataicObjectDTO.getiFriendSettingDTO() != null) {
                    if (!Preference.getUserDisclaimer())
                        disclaimer_layout.setVisibility(View.VISIBLE);
                    else {

                        String safeguard_date = AppSession.getValue(ct, Constants.USER_SAFE_GUARD);
                        if (safeguard_date != null && !safeguard_date.equalsIgnoreCase("")) {
                            safeguard_date = safeguard_date.substring(0, 10);
                            String last_update_date = stataicObjectDTO.getiFriendSettingDTO().getSafeguard_update_date();
                            int diff = Getdiffbettwodate(last_update_date, safeguard_date);
                            if (diff > 0) {
                                safeguard_layout.setVisibility(View.VISIBLE);
                            }

                        } else {
                            safeguard_layout.setVisibility(View.VISIBLE);
                        }
                    }


                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            new FetchAppVersionFromGooglePlayStore().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        }
    }


    private class IFirendShareSecretNotification extends android.os.AsyncTask<String, String, Bitmap> {

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
                IfriendSecretpushNotiDataDTO ifriendSecretpushNotiDataDTO = new IfriendSecretpushNotiDataDTO(MainActivity.enc_username, mFriend);
                IfriendSecretPushDTO ifriendSecretPushDTO = new IfriendSecretPushDTO(ifriendSecretpushNotiDataDTO);
                IfriendsecrtpushobjectDTO ifriendAcceptObjectDTO = new IfriendsecrtpushobjectDTO(ifriendSecretPushDTO);

                response = http.sendIfriendsecretpush(ifriendAcceptObjectDTO);


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {

        }
    }


    public static int Getdiffbettwodate(String newtime, String oldtime) {
        int day = 0;
        try {
            String outputPattern = "MM/dd/yyyy";
            SimpleDateFormat format = new SimpleDateFormat(outputPattern);


            Date Date1 = format.parse(newtime);
            Date Date2 = format.parse(oldtime);
            long mills = Date1.getTime() - Date2.getTime();
            long Day1 = mills / (1000 * 60 * 60);

            day = (int) Day1 / 24;


            if (day < 0)
                day = 0;


        } catch (Exception e) {
            e.printStackTrace();
        }
        return day;
    }


    private void ShowPetStatus() {

        try {


            int[] petimagearray = {R.drawable.monkey_face, R.drawable.panda_face,
                    R.drawable.horse_face, R.drawable.rabbit_face,
                    R.drawable.donkey_face, R.drawable.sheep_face,
                    R.drawable.deer_face, R.drawable.tiger_face,
                    R.drawable.parrot_face, R.drawable.meerkat_face,
                    R.drawable.mermain_face, R.drawable.cat_face,
                    R.drawable.dog_face, R.drawable.unicorn_face,
                    R.drawable.dragon_face, R.drawable.dynasore_face,
                    R.drawable.starfish_face};


            for (int i = 0; i < petnamearray.length; i++) {
                if (pet_name.equalsIgnoreCase(petnamearray[i])) {
                    petimage.setImageResource(petimagearray[i]);
                    break;
                }
            }


            oxygenlevel.setBackgroundResource(R.drawable.circle_shape9);


        } catch (Exception e1) {
            e1.printStackTrace();
        }

    }

    static SetVerifyFlagConditionDTO setVerifyFlagConditionDTO = null;




    private void Ratingoopup() {
        try {
            boolean showpopup = false;
            Calendar c = Calendar.getInstance();
            String olddate = AppSession.getValue(ct, Constants.RATINGPOPUP);
            final String todaysdate = mmddff.format(c.getTime());
            if (olddate != null && !olddate.equalsIgnoreCase("")) {
                String[] showdate = olddate.split("#");
                if (Getdiffbettwodate(todaysdate, showdate[0]) < 15 && showdate.length < 3) {
                    if (Getdiffbettwodate(todaysdate, showdate[1]) != 0) {
                        String firstpopdate = showdate[0] + "#" + todaysdate;
                        AppSession.save(ct, Constants.RATINGPOPUP, firstpopdate);
                        showpopup = true;
                    }
                }
            } else {
                showpopup = true;
                String firstpopdate = todaysdate + "#" + todaysdate;
                AppSession.save(ct, Constants.RATINGPOPUP, firstpopdate);
            }
            if (showpopup) {
                AlertDialog.Builder updateAlert = new AlertDialog.Builder(MainActivity.this);
                updateAlert.setIcon(R.drawable.ic_launcher);
                updateAlert.setTitle("Cypher");
                updateAlert.setMessage("If you enjoy using Cypher, rate the app on play store, what’s happened?");
                updateAlert.setPositiveButton(
                        "Rate",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();

                                final String appPackageName = getPackageName();
                                try {
                                    String firstpopdate = todaysdate + "#" + todaysdate + "#done";
                                    AppSession.save(ct, Constants.RATINGPOPUP, firstpopdate);

                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                                } catch (Exception e) {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.helio.silentsecret&hl=en")));
                                    e.printStackTrace();
                                }
                            }
                        });

                updateAlert.setNegativeButton(
                        "Not Now",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();

                            }
                        });
                AlertDialog alertUp = updateAlert.create();
                alertUp.setCanceledOnTouchOutside(false);
                alertUp.show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }







    private void checkpost()
    {
        if (!is_flag) {
            String userna = AppSession.getValue(ct, Constants.USER_NAME);
            if (userna == null || userna.equalsIgnoreCase(""))
            {
                Intent intent = new Intent(MainActivity.this, SignUpDialogActivity.class);
                startActivity(intent);
            }
            else {
                startActivityForResult(new Intent(MainActivity.this, CreateSecretActivity.class), 0);
            }

        }
        else
            Toast.makeText(ct, "You are not permitted to share a secret.", Toast.LENGTH_SHORT).show();



    }


    class FetchAppVersionFromGooglePlayStore extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... urls) {
            try {
                return
                        Jsoup.connect("https://play.google.com/store/apps/details?id=" + "com.helio.silentsecret" + "&hl=en")
                                .timeout(10000)
                                .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                                .referrer("http://www.google.com")
                                .get()
                                .select("div[itemprop=softwareVersion]")
                                .first()
                                .ownText();

            } catch (Exception e) {
                return "";
            }
        }

        protected void onPostExecute(String string) {

            try {
                getVer = string;
                Log.d("new Version", getVer);
                if (getVer != null && !getVer.equalsIgnoreCase("")) {

                    if (!getVer.equalsIgnoreCase(versionName)) {
                        if (formattedDate != null) {
                            if (!formattedDate.equals(get_date)) {
                                updateApp();
                            } else
                                Ratingoopup();

                        } else
                            Ratingoopup();
                    } else
                        Ratingoopup();
                } else
                    Ratingoopup();

            } catch (Exception e) {

            }


        }
    }




    private class SetPetInfo extends AsyncTask<String, String, Bitmap> {

        android.app.ProgressDialog pDialog;
        String status = "";
        Bitmap bitmap;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected Bitmap doInBackground(String... args) {
            try {


                IfriendRequest http = new IfriendRequest(ct);

                SetPetConditionDTO setPetConditionDTO = new SetPetConditionDTO(enc_username, petnamearray[select_index]);
                SetPetInfoDTO setPetInfoDTO = new SetPetInfoDTO(setPetConditionDTO);
                SetPetInfoObjectDTO setPetInfoObjectDTO = new SetPetInfoObjectDTO(setPetInfoDTO);
                petAvtarInfoDTO = http.SetPetInfo(setPetInfoObjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {
            try {
                if (petAvtarInfoDTO != null) {
                    pet_name = petnamearray[select_index];
                    AppSession.save(ct, Constants.USER_PET_NAME, petnamearray[select_index]);
                    ShowPetStatus();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }




    private static class UpdateVeryfiFlag extends AsyncTask<String, String, Bitmap> {

        android.app.ProgressDialog pDialog;
        String status = "";
        Bitmap bitmap;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected Bitmap doInBackground(String... args) {
            try {


                IfriendRequest http = new IfriendRequest(ct);

                SetVerifyFlagDTO setVerifyFlagDTO = new SetVerifyFlagDTO(setVerifyFlagConditionDTO);
                SetVerifyFlagObjectDTO setVerifyFlagObjectDTO = new SetVerifyFlagObjectDTO(setVerifyFlagDTO);
                http.UpdateFlagVeryfi(setVerifyFlagObjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {


        }
    }



    private class CheckFlagverified extends android.os.AsyncTask<String, String, Bitmap> {

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

               // String verify = AppSession.getValue(ct, Constants.USER_VERIFIED);


                String result = AppSession.getValue(ct, Constants.USER_FLAG);
                if (result != null && !result.equalsIgnoreCase("true")) {
                    is_flag = false;
                } else
                    is_flag = true;

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    private class GetLastPostDate extends AsyncTask<String, String, Bitmap> {

        android.app.ProgressDialog pDialog;
        String status = "";
        Bitmap bitmap;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected Bitmap doInBackground(String... args) {
            try {


                IfriendRequest http = new IfriendRequest(ct);

                FindbyCreateSecUserDTO setVerifyFlagDTO = new FindbyCreateSecUserDTO(enc_username);
                GetLastPostdateDTO setVerifyFlagObjectDTO = new GetLastPostdateDTO(setVerifyFlagDTO);
                status = http.GetLastPostDate(new GetLastPostdateObjectDTO(setVerifyFlagObjectDTO));

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {
            try {
                int daycount = Integer.parseInt(status);

                if (daycount > 4) {
                    AlertDialog.Builder motivateDialog = new AlertDialog.Builder(ct);
                    motivateDialog.setMessage("Your emotions and mood are very important, you should share some post updates about your day to see how your mood is evolving.");
                    motivateDialog.setTitle("Cypher");
                    motivateDialog.setIcon(R.drawable.ic_launcher);
                    motivateDialog.setPositiveButton(
                            "Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();

                                    String userna = AppSession.getValue(ct, Constants.USER_NAME);
                                    if (userna == null || userna.equalsIgnoreCase(""))
                                    {
                                        Intent intent = new Intent(MainActivity.this, SignUpDialogActivity.class);
                                        startActivity(intent);
                                    }
                                    else {
                                        startActivityForResult(new Intent(MainActivity.this, CreateSecretActivity.class), 0);
                                    }


                                }
                            });
                    motivateDialog.setNegativeButton(
                            "Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();

                                }
                            });

                    AlertDialog alertmotivate = motivateDialog.create();
                    alertmotivate.setCanceledOnTouchOutside(false);
                    alertmotivate.show();
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }


    private class GetAppointment extends android.os.AsyncTask<String, String, Bitmap> {

        android.app.ProgressDialog pDialog;
        String data = "0";
        Bitmap bitmap;
        String response = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgress();
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
                            is_booking = true;
                            stopProgress();
                            replaceDialog(Constants.ACCESS_BOOK_APPONTMENT);
                        } else {
                            new CheckPendingRating().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        }

                    } else {
                        new CheckPendingRating().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    private class ChecUserSession extends android.os.AsyncTask<String, String, Bitmap> {

        android.app.ProgressDialog pDialog;
        String data = "0";
        Bitmap bitmap;
        String response = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgress();
        }


        protected Bitmap doInBackground(String... args) {
            try {
                IfriendRequest http = new IfriendRequest(ct);
                CheckUserSessionDataDTO findbyNameDTO = new CheckUserSessionDataDTO(MainActivity.enc_username);
                CheckUserSessionDTO checkUserSessionDTO = new CheckUserSessionDTO(findbyNameDTO);
                CheckUserSessionObjectDTO findbynameObjectDTO = new CheckUserSessionObjectDTO(checkUserSessionDTO);
                response = http.CheckUserSession(findbynameObjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {
            try {

                String status = "", qid = "", agency_unq_id = "", agent_unq_id = "", Session_left = "";
                Object object = new JSONTokener(response).nextValue();
                if (object instanceof JSONObject) {
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.has("status"))
                        status = jsonObject.getString("status");
                    if (status != null && status.equalsIgnoreCase("true")) {
                        JSONArray json_array = null;
                        if (jsonObject.has("data"))
                            json_array = jsonObject.getJSONArray("data");

                        for (int i = 0; i < json_array.length(); i++) {

                            JSONObject UserInfoobj = new JSONObject(json_array.getString(i));

                            if (UserInfoobj.has("qid"))
                                qid = UserInfoobj.getString("qid");

                            if (UserInfoobj.has("agency_unq_id"))
                                agency_unq_id = UserInfoobj.getString("agency_unq_id");

                            if (UserInfoobj.has("agent_unq_id"))
                                agent_unq_id = UserInfoobj.getString("agent_unq_id");

                            if (UserInfoobj.has("session_left"))
                                Session_left = UserInfoobj.getString("session_left");

                            AppSession.save(ct, Constants.USED_QR_CODE, qid);
                            AppSession.save(ct, Constants.AGENCY_ID, agency_unq_id);
                            AppSession.save(ct, Constants.AGENT_UNIQUE_ID, agent_unq_id);
                            AppSession.save(ct, Constants.USER_SESSION_LEFT, Session_left);

                            session_left = Integer.parseInt(Session_left);


                        }
                        if (session_left > 0) {
                            stopProgress();
                            replaceDialog(Constants.ACCESS_BOOK_APPONTMENT);
                        } else {
                            new GetAppointment().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        }
                    } else {
                        new GetAppointment().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    }
                }

            } catch (Exception e) {
                stopProgress();
                e.printStackTrace();
            }

        }
    }


    private class ChecUserSessionfirsttime extends android.os.AsyncTask<String, String, Bitmap> {

        android.app.ProgressDialog pDialog;
        String data = "0";
        Bitmap bitmap;
        String response = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgress();
        }


        protected Bitmap doInBackground(String... args) {
            try {
                IfriendRequest http = new IfriendRequest(ct);
                CheckUserSessionDataDTO findbyNameDTO = new CheckUserSessionDataDTO(MainActivity.enc_username);
                CheckUserSessionDTO checkUserSessionDTO = new CheckUserSessionDTO(findbyNameDTO);
                CheckUserSessionObjectDTO findbynameObjectDTO = new CheckUserSessionObjectDTO(checkUserSessionDTO);
                response = http.CheckUserSession(findbynameObjectDTO);


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {
            try {
                //
                //  stopProgress();
                String status = "", qid = "", agency_unq_id = "", agent_unq_id = "", Session_left = "";
                Object object = new JSONTokener(response).nextValue();
                if (object instanceof JSONObject) {
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.has("status"))
                        status = jsonObject.getString("status");
                    if (status != null && status.equalsIgnoreCase("true")) {
                        JSONArray json_array = null;
                        if (jsonObject.has("data"))
                            json_array = jsonObject.getJSONArray("data");

                        for (int i = 0; i < json_array.length(); i++) {

                            JSONObject UserInfoobj = new JSONObject(json_array.getString(i));

                            if (UserInfoobj.has("qid"))
                                qid = UserInfoobj.getString("qid");

                            if (UserInfoobj.has("agency_unq_id"))
                                agency_unq_id = UserInfoobj.getString("agency_unq_id");

                            if (UserInfoobj.has("agent_unq_id"))
                                agent_unq_id = UserInfoobj.getString("agent_unq_id");

                            if (UserInfoobj.has("session_left"))
                                Session_left = UserInfoobj.getString("session_left");


                            AppSession.save(ct, Constants.USED_QR_CODE, qid);
                            AppSession.save(ct, Constants.AGENCY_ID, agency_unq_id);
                            AppSession.save(ct, Constants.AGENT_UNIQUE_ID, agent_unq_id);
                            AppSession.save(ct, Constants.USER_SESSION_LEFT, Session_left);

                            session_left = Integer.parseInt(Session_left);


                        }

                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

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
            showProgress();
        }


        protected Bitmap doInBackground(String... args) {
            try {
                IfriendRequest http = new IfriendRequest(ct);
                FindbyNameDTO findbyNameDTO = new FindbyNameDTO(MainActivity.enc_username);
                CommonRequestTypeDTO checkUserSessionDTO = new CommonRequestTypeDTO(findbyNameDTO, "checkUserRatting");
                FinalObjectDTO findbynameObjectDTO = new FinalObjectDTO(checkUserSessionDTO);
                response = http.Getappointment(findbynameObjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {
            try {

                stopProgress();
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
                            replaceDialog(Constants.ACCESS_PENDING_RATING);
                        } else {
                            replaceDialog(Constants.ACCESS_QR_READER);
                        }

                    } else {
                        replaceDialog(Constants.ACCESS_QR_READER);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }


    public static void generateActionPush(Context context, String message, String secretId, String action, String commentId) {

        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        Resources res = context.getResources();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                context);

        Intent notificationIntent = null;

        if (action != null) {

            if (action.equals(Constants.ACTION_CAUTION)) {
                Preference.saveCaution(true);
                LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(Constants.ACTION_CAUTION));
                return;
            } else if (action.equals(Constants.PUSH_ACTION_SOCIAL)) {
                notificationIntent = new Intent(context, ActionSecretActivity.class);
                notificationIntent.putExtra(Constants.PUSH_SECRET_ID, secretId);
                notificationIntent.putExtra(Constants.ACCESS_SECRET_SHOW_DIALOG, true);
            } else if (action.equals(Constants.PUSH_ACTION_COMMENT)) {
                notificationIntent = new Intent(context, CommentSecretActivity.class);
                notificationIntent.putExtra(Constants.PUSH_SECRET_ID, secretId);
            } else if (action.equals(Constants.PUSH_ACTION_COMMENT_REPLY)) {
                notificationIntent = new Intent(context, CommentSecretActivity.class);
                notificationIntent.putExtra(Constants.PUSH_SECRET_ID, secretId);
                notificationIntent.putExtra(Constants.PUSH_REPLY_COMMENT_ID, commentId);
            } else if (action.equals(Constants.PUSH_BACKEND)) {
                notificationIntent = new Intent(context, MySecretsActivity.class);
                notificationIntent.putExtra(Constants.PUSH_ACTION, Constants.PUSH_BACKEND);
                //  generateNotificationClass();
            }

            if (notificationIntent != null)
                notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            else {
                notificationIntent = new Intent(context, MainActivity.class);
                notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            }
        } else {
            // notificationIntent = null;
            notificationIntent = new Intent(context, MainActivity.class);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
/*
        if (ParseUser.getCurrentUser() == null)
        {
            notificationIntent = new Intent(context, LoginActivity.class);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }*/

        if (notificationIntent != null) {
            builder.setContentIntent(PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT));
        }

        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        bigTextStyle.setBigContentTitle(res.getString(R.string.app_name));
        bigTextStyle.bigText(message);

        builder.setSmallIcon(R.drawable.ic_launcher)
                .setAutoCancel(true).setContentTitle(res.getString(R.string.app_name))
                .setContentText(message).setStyle(bigTextStyle).setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
        Notification n = builder.build();
        n.flags |= Notification.FLAG_AUTO_CANCEL;
        n.vibrate = new long[]{50, 50, 50};
        notificationManager.notify(0, n);
    }


    private class UpdateDisclaimer extends android.os.AsyncTask<String, String, Bitmap> {

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
                CommonRequestTypeDTO findNameDTO = new CommonRequestTypeDTO(findbyNameDTO, "updateDisclaimer");
                FinalObjectDTO findbynameObjectDTO = new FinalObjectDTO(findNameDTO);
                http.Getappointment(findbynameObjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {


        }
    }


    private class UpdateReturnuing extends android.os.AsyncTask<String, String, Bitmap> {

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
                CommonRequestTypeDTO findNameDTO = new CommonRequestTypeDTO(findbyNameDTO, "updateReturning");
                FinalObjectDTO findbynameObjectDTO = new FinalObjectDTO(findNameDTO);
                http.Getappointment(findbynameObjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {


        }
    }


    private void showTutorials()
    {
        List<String> tutoriasl = new ArrayList<>();
         String[] petnamearray = {"Monkey", "Monkey", "Monkey", "Monkey", "Monkey", "Monkey", "Monkey", "Monkey", "Monkey", "Meerkat", "Mermaid","Mermaid","Mermaid"};

        for(int i=0; i<13; i++)
        {
            tutoriasl.add(petnamearray[i]);
        }
        mAdapter = new Tutorial_adapter( ct, tutoriasl);

        tutorial_viewpager.setAdapter(mAdapter);
       // tutorial_viewpager.setPageTransformer(true, new ZoomOutSlideTransformer());
        tutorial_viewpager.setCurrentItem(0);
        tutorial_viewpager.setOnPageChangeListener(this);
        setPageViewIndicator();
    }

    private ImageView[] dots;
    private int dotsCount;
    int Tutorials_count = 13;

    private void setPageViewIndicator() {

        Log.d("###setPageViewIndicator", " : called");
        dotsCount = Tutorials_count;
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(ct);
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                   20,
                    20
            );

            params.setMargins(4, 0, 4, 0);

            final int presentPosition = i;
            dots[presentPosition].setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    tutorial_viewpager.setCurrentItem(presentPosition);
                    return true;
                }

            });


            viewPagerCountDots.addView(dots[i], params);
        }

        dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
    }



    private class CHeckRubyCode extends AsyncTask<String, String, Bitmap> {

        android.app.ProgressDialog pDialog;
        String response = "";
        Bitmap bitmap;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           showProgress();
        }

        protected Bitmap doInBackground(String... args) {
            try {


                IfriendRequest http = new IfriendRequest(ct);


                String userna = AppSession.getValue(ct, Constants.USER_NAME);
                if (userna == null || userna.equalsIgnoreCase("")) {
                    JSONObject requestdata = new JSONObject();
                    JSONObject condition = new JSONObject();
                    JSONObject main_object = new JSONObject();
                    requestdata.put("apikey", "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R");
                    condition.put("code", code);
                    requestdata.put("requestType", "checkUsercode");
                    requestdata.put("data", condition);
                    main_object.put("requestData", requestdata);
                    response = http.flagRoomComment(main_object.toString());
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {
            try {
               stopProgress();
                String status = "", guest_clun01 = "";
                Object object = new JSONTokener(response).nextValue();
                if (object instanceof JSONObject) {
                    JSONObject jsonObject = new JSONObject(response);


                    if (jsonObject.has("status"))
                        status = jsonObject.getString("status");

                    if (status != null && status.equalsIgnoreCase("true")) {
                        String codetype = "";
                        if (jsonObject.has("codetype"))
                            codetype = jsonObject.getString("codetype");


                        AppSession.save(ct, Constants.USER_CODE, code);
                        AppSession.save(ct, Constants.USER_CODE_TYPE, codetype);

                        edt_search.setText("");
                        ruby_code_layout.setVisibility(View.GONE);
                        Toast.makeText(ct, "Thanks for submiting the code.", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(ct, "Please enter a valid code.", Toast.LENGTH_SHORT).show();
                    }


                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }


    private class GetStaticData extends AsyncTask<String, String, Bitmap> {

        android.app.ProgressDialog pDialog;
        String status = "";
        Bitmap bitmap;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected Bitmap doInBackground(String... args) {
            try {

                if (stataicObjectDTO != null)
                    stataicObjectDTO = null;

                IfriendRequest http = new IfriendRequest(ct);
                StaticDataDTO staticDataDTO = new StaticDataDTO();
                StaticObjectDTO staticObjectDTO = new StaticObjectDTO(staticDataDTO);
               stataicObjectDTO = http.GetstaticData(staticObjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {





        }
    }

}

