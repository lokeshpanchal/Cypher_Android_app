package com.helio.cypher.activities;

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
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.text.SpannableString;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.helio.cypher.BuildConfig;
import com.helio.cypher.EncryptionDecryption.CryptLib;
import com.helio.cypher.R;
import com.helio.cypher.Services.BoundService;
import com.helio.cypher.Services.InactiveNotification;
import com.helio.cypher.StaticObjectDTO.StataicObjectDTO;
import com.helio.cypher.WebserviceDTO.DeviceTokenDTO;
import com.helio.cypher.WebserviceDTO.DeviceTokenObjectDTO;
import com.helio.cypher.WebserviceDTO.DevicetokendataDTO;
import com.helio.cypher.WebserviceDTO.FindNameDTO;
import com.helio.cypher.WebserviceDTO.FindbyCreateSecUserDTO;
import com.helio.cypher.WebserviceDTO.FindbyNameDTO;
import com.helio.cypher.WebserviceDTO.FindbynameObjectDTO;
import com.helio.cypher.WebserviceDTO.GetLastPostdateDTO;
import com.helio.cypher.WebserviceDTO.GetLastPostdateObjectDTO;
import com.helio.cypher.WebserviceDTO.GetPetConditionDTO;
import com.helio.cypher.WebserviceDTO.GetPetInfoDTO;
import com.helio.cypher.WebserviceDTO.GetPetInfoObjectDTO;
import com.helio.cypher.WebserviceDTO.IfriendSecretpushNotiDataDTO;
import com.helio.cypher.WebserviceDTO.PetAvtarInfoDTO;
import com.helio.cypher.WebserviceDTO.ScratchCouponDTO;
import com.helio.cypher.WebserviceDTO.ScratchCouponObjectDTO;
import com.helio.cypher.WebserviceDTO.ScratchCoupondataDTO;
import com.helio.cypher.WebserviceDTO.SetPetConditionDTO;
import com.helio.cypher.WebserviceDTO.SetPetInfoDTO;
import com.helio.cypher.WebserviceDTO.SetPetInfoObjectDTO;
import com.helio.cypher.WebserviceDTO.SetVerifyFlagConditionDTO;
import com.helio.cypher.WebserviceDTO.SetVerifyFlagDTO;
import com.helio.cypher.WebserviceDTO.SetVerifyFlagObjectDTO;
import com.helio.cypher.WebserviceDTO.StaticDataDTO;
import com.helio.cypher.WebserviceDTO.StaticObjectDTO;
import com.helio.cypher.adapters.PendingIFriendsAdapter;
import com.helio.cypher.adapters.ViewPagerMainAdapter;
import com.helio.cypher.appCounsellingDTO.CheckUserSessionDTO;
import com.helio.cypher.appCounsellingDTO.CheckUserSessionDataDTO;
import com.helio.cypher.appCounsellingDTO.CheckUserSessionObjectDTO;
import com.helio.cypher.appCounsellingDTO.CommonRequestTypeDTO;
import com.helio.cypher.appCounsellingDTO.FinalObjectDTO;
import com.helio.cypher.application.SilentSecretApplication;
import com.helio.cypher.callbacks.FilterUpdateCallback;
import com.helio.cypher.callbacks.SearchUpdateCallback;
import com.helio.cypher.callbacks.UpdateCallback;
import com.helio.cypher.connection.ConnectionDetector;
import com.helio.cypher.connection.IfriendRequest;
import com.helio.cypher.controller.Controller;
import com.helio.cypher.dialogs.AccessDialog;
import com.helio.cypher.dialogs.CustomDialog;
import com.helio.cypher.dialogs.Disclaimer;
import com.helio.cypher.dialogs.ProgressDialog;
import com.helio.cypher.dialogs.ScratchTextView;
import com.helio.cypher.fragments.FeeListFragment;
import com.helio.cypher.fragments.FeedViewFragment;
import com.helio.cypher.fragments.MineSecretsFragment;
import com.helio.cypher.fragments.SearchFragment;
import com.helio.cypher.location.GeoHelper;
import com.helio.cypher.models.AllIfriendDTO;
import com.helio.cypher.models.AllIfriendobjectDTO;
import com.helio.cypher.models.IfriendSecretPushDTO;
import com.helio.cypher.models.IfriendsecrtpushobjectDTO;
import com.helio.cypher.pushnotification.GCMPushNotifHandler;
import com.helio.cypher.pushnotification.GcmIntentService;
import com.helio.cypher.tutorial.MainViewTutorial;
import com.helio.cypher.utils.AppSession;
import com.helio.cypher.utils.CommonFunction;
import com.helio.cypher.utils.Constants;
import com.helio.cypher.utils.KeyboardUtil;
import com.helio.cypher.utils.Preference;
import com.helio.cypher.utils.TimeUtil;
import com.helio.cypher.utils.ToastUtil;
import com.helio.cypher.views.OwnPager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.nineoldandroids.animation.Animator;

import com.plattysoft.leonids.ParticleSystem;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.jsoup.Jsoup;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class MainActivity extends BaseActivity implements
        View.OnClickListener, ViewPager.OnPageChangeListener {

    LinearLayout whole_layout = null;

    public static StataicObjectDTO stataicObjectDTO = null;

    static MainActivity mainActivity = null;
    DevicetokendataDTO devicetokendataDTO = null;
    public static PetAvtarInfoDTO petAvtarInfoDTO = null;

    private int doowappCount;
    private int doowappMaxCount;
    public static int scratch_count = 10;
    public static int comments_count = 0;
    boolean is_done_in_oncreate = false;
    AllIfriendDTO allIfriendDTO = null;
    public static boolean is_flag = false;
    public static String pet_name = "";
    public static boolean is_From_glimpse = false;
    public static boolean is_From_feeling_post = false;

    public static boolean is_from_doowapp = false;
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
    public static String[] petnamearray = {"Monkey", "Panda", "Horse", "Rabbit", "Donkey", "Sheep", "Deer", "Tiger", "Parrot", "Meerkat"};
    public static boolean is_booking = false;
    private String datepen1 = "";
    private String datepen = "";
    private Date date;
    public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
    private ImageView mPostEmotion;
    private String get_date = "";
    public static String AgencyId = "";
    static TextView runAnime = null;
    ImageView img1, img2, img3, img4, img5, img6, img7, img8;
    private int width = 0, height;
    private SlidingMenu menu;
    private View mSecrets;
    private View mLatestFiltering;
    private View mLight;
    private View mTense;
    private View mDeep;
    private View mSeeAll;
    private View mSettings;
    private View mHowToStaySafe;
    private View mResetTutorial;
    private View mClearMySecret;
    static private View mUnverifyMe;
    private View mRateSchool;
    private View mReferFriends;
    private View mStats;
    private View left_chats;
    public static int session_left = 0;
    ImageView search_button = null, notification_button = null, home_button = null;
    private View left_book_appointment;
    public static String friend_table_name = "IFriend_Temp";
    //private IabHelper mHelper;
    public static ArrayList<String> users = null;

    public static ArrayList<String> scratchscretid = null;

    public static boolean is_running = false;
    // private View mGetComments;
    private View mNotifications;

    public interface OnReplace {
        void runMood();
    }

    public OnReplace mMoodRunner;

    private static final int POS = 1;
    private static final int NEG = 0;

    public static int[] trendingData = new int[3];

    private static OwnPager mMainPager;
    private ViewPagerMainAdapter adapter;

    private UpdateCallback mTrendingUpdate;
    private UpdateCallback mHappyUpdate;
    private static FilterUpdateCallback mLatestUpdate;
    private SearchUpdateCallback mSearchCallback;

    private int filterState;

    private EditText mSearchEdit;
    private ImageView mSearchToggle;

    private boolean isKeyBoard = false;

    private ImageView menuButton;
    private Handler mActivityHandler;
    private Runnable mActivityRunnable;

    public static String is_from_push_noti = "";


    private static Handler mBannerHandler;
    private static Runnable mBannerRunnable;
    private int settingsState = Constants.SETTINGS_NONE;
    private static final String PURCHASE_ITEM = "com.helio.silentsecret.get_unlimited_comments";
    private static final int REQUEST_CODE = 0;

    //  private static AtomicBoolean isRunning = new AtomicBoolean();

    private MainViewTutorial mMainTutorial;

    public String searchText = null;

    private String currentTracking = null;

    public boolean mainVerified = false;
    public boolean superVerified = false;
    public boolean showUnverify = false;

/*    private static final int TRENDING_STATE = 0;
    private static final int GLIMPSE_STATE = 1;
    private static final int HAPPY_STATE = 2;
    private static final int LATEST_STATE = 3;*/

    private PagerTabStrip tabStrip;

    // public static LinearLayout caution_view = null;

    private String deviceTOken = "";
    GCMPushNotifHandler mGcmPushNotifHandler;
    private boolean FROMNOTIF = false, UNFRIEND = false, FINDSECRET = false, APPCOUNSELLING = false, SECRET_SHARING = false, ENTERTAINMENT_GLIMPSE = false, TRENDING_PAGE = false;
    private boolean CHAT = false, LATEST_FILTERING = false, RATE_SCHOOL_COLLEGE = false, STATS_PAGE = false;
    private boolean USERNAME = false, MY_SECRET_PAGE = false, GET_SUPPORT_PAGE = false;
    private boolean SECRET = false, MOOD_PAGE = false, LIFESTYLE_GLIMPSE = false, NEWS_GLIMPSE = false, FITNESS_TRACKING = false;
    public static Context ct;
    public static boolean is_chat = false;
    TextView loaddata = null;
    private boolean isNoti_Click = false;
    TextView pGif = null;
    private String senderW = "";
    private String targetW = "";
    TextView chat_count = null;
    String imei_numer = "";
    public static ArrayList<String> userListNamesOnlyWaiting = new ArrayList<>();
    public static CustomDialog cd;
    public static RelativeLayout webview_layout = null;
    LinearLayout feed_view_back = null, pet_main_layout;
    static WebView mWebView = null;
    private static View view = null;
    private View mCouponCode;
    RelativeLayout pet_layout = null, petlayout;

    TextView skip_pet, done_pet = null, cancel_search = null, search_ok = null;

    public static int oxygen = 10, food = 10, water = 10;
    public static ImageView petimage = null;
    String petname = "";
    public static TextView oxygenlevel ;
    TextView show_safeguard , disclaimer_ok ,safeguard_ok;
    public static ProgressBar water_progress, food_progress;

    public static String[] colorsking = {"#aa222222", "#99222222", "#88222222", "#77222222", "#66222222", "#55222222", "#33222222", "#22222222", "#11222222", "#00222222"};
    public static RelativeLayout scratch_layout = null;

    public static RelativeLayout scratchtextview = null;
    RelativeLayout search_layout = null , disclaimer_layout = null, safeguard_layout = null;

    public static String enc_username = "";
    boolean is_from_notification = false;
    static TextView cancelscratch = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ct = this;

        try {
            enc_username = CryptLib.encrypt(AppSession.getValue(ct, Constants.USER_NAME));
        } catch (Exception e) {

        }
        try {


            mainActivity = this;
            is_from_commNotif = false;
            is_booking = false;
            c = Calendar.getInstance();


            users = new ArrayList<>();
            whole_layout = (LinearLayout) findViewById(R.id.whole_layout);
            mWebView = (WebView) findViewById(R.id.feed_view_vb);
            webview_layout = (RelativeLayout) findViewById(R.id.webview_layout);
            pet_main_layout = (LinearLayout) findViewById(R.id.pet_main_layout);
            pet_layout = (RelativeLayout) findViewById(R.id.pet_layout);
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
            show_safeguard = (TextView) findViewById(R.id.show_safeguard);
            cancelscratch = (TextView) findViewById(R.id.cancelscratch);
            scratch_layout = (RelativeLayout) findViewById(R.id.scratch_layout);
            scratchtextview = (RelativeLayout) findViewById(R.id.scratchtextview);
            search_layout = (RelativeLayout) findViewById(R.id.search_layout);
            search_button = (ImageView) findViewById(R.id.search_button);
            notification_button = (ImageView) findViewById(R.id.notification_button);
            home_button = (ImageView) findViewById(R.id.home_button);
            cancel_search = (TextView) findViewById(R.id.cancel_search);
            is_done_in_oncreate = true;

            showProgress();

            show_safeguard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    runPolicy();
                    safeguard_layout.setVisibility(View.GONE);
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
            new ChecUserSessionfirsttime().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

            new CheckFlagverified().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


           String returing =  AppSession.getValue(ct, Constants.USER_RETURNING);
            if(returing == null || returing.equalsIgnoreCase(""))
            {
                AppSession.save(ct, Constants.USER_RETURNING,"1");
            }else if(returing.equalsIgnoreCase("1"))
            {
                new UpdateReturnuing().execute();
                AppSession.save(ct, Constants.USER_RETURNING,"2");
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

                if (getIntent().hasExtra("MY_SECRET_PAGE"))
                {
                    MY_SECRET_PAGE = getIntent().getBooleanExtra("MY_SECRET_PAGE", false);
                    if (MY_SECRET_PAGE)
                    {

                        MineSecretsFragment.envelop_secre_id = AppSession.getValue(ct, Constants.COMMENT_SECRET_ID);

                        showPinDialog();
                        is_from_notification = true;
                    }
                }
                else if (getIntent().hasExtra("COMMENT"))
                {
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
            search_ok = (TextView) findViewById(R.id.search_ok);

            scratch_layout.setVisibility(View.GONE);
            AgencyId = "";
            if (scratchscretid != null)
                scratchscretid = null;


            scratchscretid = new ArrayList<>();

            notification_button.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (isKeyBoard)
                        KeyboardUtil.hideKeyBoard(mSearchEdit, MainActivity.this);


                    if (ConnectionDetector.isNetworkAvailable(ct))
                    {
                        findViewById(R.id.search_frame).setVisibility(View.GONE);
                        showNotificationAccessDialog();
                    } else {
                        new ToastUtil(ct, "Please check your internet connection.");
                    }


                }
            });

            home_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    findViewById(R.id.search_frame).setVisibility(View.GONE);
                    mMainPager.setCurrentItem(2);
                }
            });

            search_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (ConnectionDetector.isNetworkAvailable(ct))
                    {

                        mSearchEdit.setText("");
                        search_layout.setVisibility(View.VISIBLE);
                    } else {
                        new ToastUtil(ct, "Please check your internet connection.");
                    }


                }
            });

            cancel_search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        KeyboardUtil.hideKeyBoard(mSearchEdit, MainActivity.this);
                        search_layout.setVisibility(View.GONE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });


            search_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            search_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String searcstring = mSearchEdit.getText().toString();
                    if (!searcstring.isEmpty()) {
                        search_layout.setVisibility(View.GONE);
                        KeyboardUtil.hideKeyBoard(mSearchEdit, MainActivity.this);
                        makeSearch(mSearchEdit.getText().toString());
                        mSearchEdit.setText("");
                        startTracking(getString(R.string.analytics_SearchSecrets));
                    } else {
                        Toast.makeText(ct, "Please type text to search.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            petlayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, StatsActivity.class));
                    //  showStatsDialog();
                }
            });

            webview_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            feed_view_back = (LinearLayout) findViewById(R.id.feed_view_back);
            feed_view_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    webview_layout.setVisibility(View.GONE);

                    if (Build.VERSION.SDK_INT < 18) {
                        mWebView.clearView();
                    } else {
                        initWebView("about:blank");
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {

            init();

            loaddata = new TextView(this);
            runAnime = new TextView(this);

            cancelscratch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // coupon_image.setVisibility(View.VISIBLE);
                    scratch_layout.setVisibility(View.GONE);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }


        scratch_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


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

                    SpannableString text = new SpannableString("Feed your pet by giving out hugs, hearts, me2s and comments on posts");

                    text.setSpan(new ForegroundColorSpan(Color.parseColor("#333333")), 0, text.length(), 0);
                    text.setSpan(new ForegroundColorSpan(Color.BLACK), 26, 46, 0);
                    text.setSpan(new StyleSpan(Typeface.BOLD), 26, 46, 0);
                    text.setSpan(new ForegroundColorSpan(Color.BLACK), 51, 59, 0);
                    text.setSpan(new StyleSpan(Typeface.BOLD), 51, 59, 0);

                    AlertDialog.Builder updateAlert = new AlertDialog.Builder(ct);
                    updateAlert.setIcon(R.drawable.ic_launcher);
                    updateAlert.setTitle("Cypher");
                    updateAlert.setMessage(text);
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
        mPostEmotion = (ImageView) findViewById(R.id.post_emotion);

        mPostEmotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postEmotion();
            }
        });

        // whole_layout.setVisibility(View.GONE);

        /*final String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAg6Zy+nsCSVRkMixvAIfuDEVi169MzpgCq9Z3rsUVEIGujohY9aBB9lJodAb10fiOLl0XGsC8DHrH3lJfQxPr1xVSYSxoDsNy7rZk/PF/VGnC6KyydUTeBEZgncVX36SrMrSxHQAuy5EcgVpTXT4mvOodTRIYIYo0o31uLynjS1Jabn+JOzvWoEcWhRrlbDIUKqgXGLt3YsrkWCzR+r0noB1aNsnmv73cttLlP0UwSv/B0CWoFOJwuLsryo66S2IEXBPURcTF/lOVYae/1jCgD8CcIInQ9TpYWy46F2kVK9wyk0Ywnv9EpVF21FICmLpLZDBxlBJ5PjSycTR2CH5v3QIDAQAB";


        try {
            mHelper = new IabHelper(this, base64EncodedPublicKey);

            mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
                public void onIabSetupFinished(IabResult result) {
                    if (result.isFailure()) {
                        //Toast.makeText(MainActivity.this, getString(R.string.google_play_error), Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            });
        } catch (Exception e) {
        }*/

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



        loaddata.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {

                    chat_count = (TextView) findViewById(R.id.chat_count);

                    if (MainActivity.is_from_login)
                    {
                        if (ConnectionDetector.isNetworkAvailable(ct))
                        {
                            mGcmPushNotifHandler = new GCMPushNotifHandler((Activity) ct);
                            mGcmPushNotifHandler.register();

                            /*mGcmPushNotifHandler = new GCMPushNotifHandler((Activity) ct);
                            deviceTOken = mGcmPushNotifHandler.getRegistrationId();

                            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                            imei_numer = telephonyManager.getDeviceId();
                            deviceToken();*/
                        } else {
                            new ToastUtil(ct, "Please check your internet connection.");
                        }
                    }
                    pGif = (TextView) findViewById(R.id.chat_friend);
                    pGif.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (ConnectionDetector.isNetworkAvailable(ct))
                            {
                                String verify = AppSession.getValue(ct, Constants.USER_VERIFIED);
                                if (verify != null && verify.equalsIgnoreCase("false")) {
                                    new ToastUtil(ct, ct.getString(R.string.sorry_not_verified));
                                    isIAMVerified = false;

                                } else {
                                    replaceDialog(Constants.ACCESS_IFRIEND);
                                    isIAMVerified = true;

                                }
                            } else {
                                new ToastUtil(ct, "Please check your internet connection.");
                            }





                        }
                    });


                    whole_layout.setVisibility(View.VISIBLE);
                    chatcount = 0;

                    chat_count.postDelayed(refreshchatcount, 1000);

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

                            if (getIntent().hasExtra("SECRET_SHARING"))
                            {
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

                            if (getIntent().hasExtra("STATS_PAGE")) {
                                STATS_PAGE = getIntent().getBooleanExtra("STATS_PAGE", false);
                                if (STATS_PAGE) {
                                    showStatsDialog();
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


                            try {
                                if (getIntent().hasExtra("LIFESTYLE_GLIMPSE")) {
                                    LIFESTYLE_GLIMPSE = getIntent().getBooleanExtra("LIFESTYLE_GLIMPSE", false);
                                    if (LIFESTYLE_GLIMPSE) {
                                        is_from_notification = true;
                                        is_from_push_noti = Constants.LIFESTYLE_PUSH;
                                        // Controller.runGlimpse(Controller.LIFE, MainActivity.this, true);

                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            try {
                                if (getIntent().hasExtra("NEWS_GLIMPSE")) {
                                    NEWS_GLIMPSE = getIntent().getBooleanExtra("NEWS_GLIMPSE", false);
                                    if (NEWS_GLIMPSE) {

                                        is_from_notification = true;
                                        is_from_push_noti = Constants.NEWS_PUSH;
                                        //Controller.runGlimpse(Controller.NEWS, MainActivity.this, true);
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            try {
                                if (getIntent().hasExtra("ENTERTAINMENT_GLIMPSE")) {
                                    ENTERTAINMENT_GLIMPSE = getIntent().getBooleanExtra("ENTERTAINMENT_GLIMPSE", false);
                                    if (ENTERTAINMENT_GLIMPSE) {
                                        is_from_notification = true;
                                        is_from_push_noti = Constants.ENTERTAINMENT_PUSH;
                                        //Controller.runGlimpse(Controller.ENTER, MainActivity.this, true);

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

                            if (getIntent().hasExtra(GcmIntentService.mUsernameSecret)) {
                                USERNAME = getIntent().getBooleanExtra(GcmIntentService.mUsernameSecret, false);
                                if (USERNAME) {
                                    is_from_notification = true;
                                    String mUser = GcmIntentService.mUsernameSecret;
                                    acceptNotification(mUser);
                                }
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else
                        is_from_notification = false;

                    if (is_from_notification == false) {
                        pet_name = AppSession.getValue(ct, Constants.USER_PET_NAME);
                        if (pet_name != null && !pet_name.equalsIgnoreCase("") && !pet_name.equalsIgnoreCase("null"))
                            new GetPetInfo().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        else {
                            pet_layout.setVisibility(View.VISIBLE);
                            pet_name = "";
                            drawpetlist();
                        }
                    }

                    get_date = myPrefs.getString("Date", "Nothing");

                    if (runAnime != null) {
                        runAnime.postDelayed(new Runnable() {
                            @Override
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
                        }, 6000);
                    }
                    SetUserInactiveAlarm();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 500);

        //   startservice();

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


    public static void initWebView(final String url) {

        try {

            mWebView.getSettings().setJavaScriptEnabled(true);
            mWebView.loadUrl(url);
            mWebView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {


                    //  if (url.contains("www.doowapp.me/"))
                    if (url.contains("http://www.doowapp.me/play.php?id")) {
                        mWebView.loadUrl(url);
                        return false;
                    } else
                        return true;


                   /* view.loadUrl(url);
                    return true;*/

                }

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {

                }

                @Override
                public void onPageFinished(WebView view, final String url) {

                }

                @Override
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

                    super.onReceivedError(view, errorCode, description, failingUrl);

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    /*public static void initWebView(final String url) {

       // mWebView.getSettings().setJavaScriptEnabled(true);


*//*
        mWebView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return (event.getAction() == MotionEvent.ACTION_MOVE);
            }
        });*//*

        mWebView.loadUrl(url);

mWebView.postDelayed(new Runnable() {
    @Override
    public void run() {
        mWebView.loadUrl(url);
    }
},1000);


        mWebView.setWebViewClient(new WebViewClient()
        {
           *//* @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {


                if (url.contains("www.doowapp.me/")) {
                    mWebView.loadUrl(url);
                    return false;
                } else
                    return true;
            }


            public boolean shuldOverrideKeyEvent(WebView view, KeyEvent event) {

                return true;
            }
*//*

           *//* @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

            }

            @Override
            public void onPageFinished(WebView view, final String url) {

            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);

            }*//*
        });
    }

*/
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
        buildervalid.setIcon(R.drawable.chat_friend);
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


/*    public void setupTrendingCallback(UpdateCallback data) {
        this.mTrendingUpdate = data;
    }*/

 /*   public void setupHappyCallback(UpdateCallback data) {
        this.mHappyUpdate = data;
    }*/

/*

    public void setupSearchCallback(SearchUpdateCallback data) {
        this.mSearchCallback = data;
    }*/

    public void setupFilterCallback(FilterUpdateCallback data) {
        this.mLatestUpdate = data;
    }

    private void init() {

        try {

            findViewById(R.id.main_progress_bg_iv).setOnClickListener(this);
            mMainTutorial = new MainViewTutorial(this, findViewById(R.id.tutorial_root));
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

            menu = new SlidingMenu(this);
            menu.setMode(SlidingMenu.LEFT);
            menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
            menu.setShadowWidthRes(R.dimen.shadow_width);
            menu.setShadowDrawable(R.drawable.shadow);
            menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
            menu.setFadeDegree(0.8f);
            menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
            menu.setMenu(R.layout.leftmenu);
            menu.setSlidingEnabled(true);

            mSearchEdit = (EditText) findViewById(R.id.search_input);


            mSearchEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {

                @Override
                public boolean onEditorAction(TextView v, int actionId,
                                              KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        String searcstring = mSearchEdit.getText().toString();
                        if (!searcstring.isEmpty()) {
                            search_layout.setVisibility(View.GONE);
                            KeyboardUtil.hideKeyBoard(mSearchEdit, MainActivity.this);
                            makeSearch(mSearchEdit.getText().toString());
                            mSearchEdit.setText("");
                            startTracking(getString(R.string.analytics_SearchSecrets));
                        } else {
                            Toast.makeText(ct, "Please type text to search.", Toast.LENGTH_SHORT).show();
                        }


                        return true;
                    }
                    return false;
                }
            });
            mSearchEdit.clearFocus();
            mSearchToggle = (ImageView) findViewById(R.id.search_toggle);
            // mSearchToggle.setTag(0);

            menuButton = (ImageView) findViewById(R.id.menu_toggle);
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
                    mMainPager.setCurrentItem(3);
                }
            } else {
                if (is_chat) {
                    is_from_notification = true;
                    mMainPager.setCurrentItem(1);
                } else {
                    mMainPager.setCurrentItem(2);
                }
            }
            is_chat = false;

            tabStrip = (PagerTabStrip) findViewById(R.id.pager_title_strip);
            tabStrip.setTabIndicatorColor(getResources().getColor(R.color.upper_line));

            View leftView = menu.getMenu();
            initLeftMenu(leftView);
            loadDefault(true);

          /*  final View activityRootView = findViewById(R.id.app_root);
            activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    int heightDiff = activityRootView.getRootView().getHeight() - activityRootView.getHeight();
                    if (heightDiff > 200) {
                        mSearchToggle.setImageResource(R.drawable.ic_down);
                        mSearchToggle.setTag(1);
                        mSearchEdit.setCursorVisible(true);
                        isKeyBoard = true;
                    } else {
                        mSearchToggle.setImageResource(R.drawable.ic_plus);
                        mSearchToggle.setTag(0);
                        mSearchEdit.setCursorVisible(false);
                        isKeyBoard = false;
                    }
                }
            });*/

            findViewById(R.id.menu_toggle).setOnClickListener(this);
            findViewById(R.id.search_toggle).setOnClickListener(this);
            findViewById(R.id.hug_anim_root).setOnClickListener(this);

            menu.setOnClosedListener(new SlidingMenu.OnClosedListener() {
                @Override
                public void onClosed() {
                    switch (settingsState) {
                        case Constants.SETTINGS_SECRETS:
                            showPinDialog();
                            break;
                        case Constants.SETTINGS_POLICY:
                            runPolicy();
                            break;
                        case Constants.SETTINGS_CLEAR:
                            clearDataDialog();
                            break;
                        case Constants.SETTINGS_NOTIFICATIONS:

                            if (ConnectionDetector.isNetworkAvailable(ct))
                            {
                                showNotificationAccessDialog();
                            } else {
                                new ToastUtil(ct, "Please check your internet connection.");
                            }

                            break;
                        case Constants.SETTINGS_UNVERIFY:
                            showUnverifyDialog();
                            break;
                        case Constants.SETTINGS_RATE_SCHOOL:
                            startRateSchool();
                            break;
                        case Constants.SETTINGS_REFER_FRIEND:
                            startReferFriend();
                            break;
                        case Constants.SETTINGS_STATS:
                            showStatsDialog();
                            break;
                        default:
                            //none
                            break;
                    }

                    settingsState = Constants.SETTINGS_NONE;
                }
            });

            menu.setOnOpenedListener(new SlidingMenu.OnOpenedListener() {
                @Override
                public void onOpened() {
                    mMainTutorial.showMineTutorial(menu.getMenu());
                    // mMainTutorial.showInviteTutorial(menu.getMenu());
                    mMainTutorial.showAppTutorial(menu.getMenu());
                    mMainTutorial.showIFriendsTutorial(menu.getMenu());
                    mMainTutorial.showPetTutorial(menu.getMenu());
                   /* if (mGetComments.getVisibility() == View.VISIBLE)
                        mMainTutorial.showPurchaseTutorial(menu.getMenu());
       */
                }
            });

            mMainTutorial.checkForThePerformance();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void loadDefault(boolean b) {
        // if (ConnectionDetector.isNetworkAvailable(MainActivity.ct)) {
        // fillTrendingData();
        //loadFeed(Constants.STATE_HAPPINESS, b);
        setFilter(Preference.getFilter());


        //}
    }

    private void startReferFriend() {
        startActivity(new Intent(this, ReferFriendActivity.class));
    }

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
        /*new SchoolLoader(new SchoolCallback() {
            @Override
            public void onUpdate(List<School> data) {



            }
        }).execute();*/
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


    private void makeSearch(String text) {
        try {


            if (text.isEmpty())
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

            //Fragment frag = getSupportFragmentManager().findFragmentById(R.id.search_frame);
            // if (!(frag instanceof SearchFragment)) {

            String search_text = "";

            try {
                text = text.toLowerCase();
                String[] secr_text = text.split(" ");

                if (secr_text != null && secr_text.length > 0) {
                    for (int i = 0; i < secr_text.length; i++) {
                        String withplus = CryptLib.encrypt(secr_text[i]);
                        withplus = withplus.replace("+", "");

                        search_text = search_text + " " + withplus;
                        search_text = search_text.trim();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            searchText = search_text;

            replaceSearch();

            // loadSearch(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
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


   /* public void loadSearch(final int skip) {
        new SearchLoader(this, new SearchUpdateCallback() {
            @Override
            public void onUpdate(List<Secret> list, int skip) {
                // if (list != null && list.size() > 0)
                {
                    mSearchCallback.onUpdate(list, skip);
                }
            }
        }).execute(searchText, skip);
    }*/


/*
    public void loadSearchsecretid(final int skip) {
        new SearchLoader(this, new SearchUpdateCallback() {
            @Override
            public void onUpdate(List<Secret> list, int skip) {
                // if (list != null && list.size() > 0)
                {
                    mSearchCallback.onUpdate(list, skip);
                }
            }
        }).executesecretid(searchText, skip);
    }
*/

 /*   @Override
    protected void onPause() {
        super.onPause();
        Intent intent = new Intent(this, BoundService.class);
        stopService(intent);
    }*/

    @Override
    protected void onResume() {
        super.onResume();

        is_running = true;
        Intent intent = new Intent(this, BoundService.class);
        startService(intent);


        pet_name = AppSession.getValue(ct, Constants.USER_PET_NAME);
        if (pet_name != null && !pet_name.equalsIgnoreCase("") && !pet_name.equalsIgnoreCase("null"))
            new GetPetInfo().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


       /* try
        {
            if (!is_done_in_oncreate)
            {
                String secret_id = AppSession.getValue(ct, Constants.COMMENT_SECRET_ID);

                if (secret_id != null && !secret_id.equalsIgnoreCase("")) {
                    is_from_commNotif = true;
                    SearchFragment.secret_id = secret_id;
                    makeSearchwithid(SearchFragment.secret_id);
                    AppSession.save(ct, Constants.COMMENT_SECRET_ID, "");
                }
            } else
                is_done_in_oncreate = false;
        } catch (Exception e) {

        }*/

        /*pet_layout.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {





                    try
                    {
                        if (!is_done_in_oncreate)
                        {
                            String secret_id = AppSession.getValue(ct, Constants.COMMENT_SECRET_ID);

                            if (secret_id != null && !secret_id.equalsIgnoreCase("")) {
                                is_from_commNotif = true;
                                SearchFragment.secret_id = secret_id;
                                makeSearchwithid(SearchFragment.secret_id);
                                AppSession.save(ct, Constants.COMMENT_SECRET_ID, "");
                            }
                        } else
                            is_done_in_oncreate = false;
                    } catch (Exception e) {

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },200);*/


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

                if (menuButton != null) {
                    menuButton.setOnClickListener(MainActivity.this);
                    menuButton.setImageResource(R.drawable.ic_menu_icon_b);
                    menuButton.setTag(0);
                }
                if (isKeyBoard && mSearchEdit != null)
                    KeyboardUtil.hideKeyBoard(mSearchEdit, MainActivity.this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    };

    @Override
    public void onBackPressed()
    {
        try
        {
            FragmentManager manager = getSupportFragmentManager();
            Fragment fragGlimpse = manager.findFragmentById(R.id.glimpse_container);
            Fragment fragHappy = manager.findFragmentById(R.id.happy_glimpse_container);

            if (fragGlimpse instanceof FeedViewFragment || fragGlimpse instanceof FeeListFragment
                    || fragHappy instanceof FeedViewFragment || fragHappy instanceof FeeListFragment) {
                return;
            }

            Fragment frag = manager.findFragmentById(R.id.search_frame);
            if (frag instanceof SearchFragment && (Integer) menuButton.getTag() == 1) {
                return;
            }
            showSearchBar();
        } catch (Exception e) {
            e.printStackTrace();
        }

        runAnime = null;
        super.onBackPressed();
    }

    private void initLeftMenu(View leftView) {

        mSecrets = leftView.findViewById(R.id.left_my_secrets);
        mSecrets.setOnClickListener(mMySecretClick);
        mCouponCode = leftView.findViewById(R.id.coupon_code);

        mCouponCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isKeyBoard)
                    KeyboardUtil.hideKeyBoard(mSearchEdit, MainActivity.this);

                Intent i = new Intent(ct, CouponCodeActivity.class);
                startActivity(i);

            }
        });

        left_chats = leftView.findViewById(R.id.left_chats);
        left_chats.setOnClickListener(left_chatsClick);
        left_book_appointment = leftView.findViewById(R.id.left_book_apointment);


        mLatestFiltering = leftView.findViewById(R.id.left_latest_filtering);
        mLatestFiltering.setTag(NEG);

        mLight = leftView.findViewById(R.id.left_light);
        mTense = leftView.findViewById(R.id.left_tense);
        mDeep = leftView.findViewById(R.id.left_deep);
        mSeeAll = leftView.findViewById(R.id.left_see_all);

        mLatestFiltering.setOnClickListener(mFilterClick);
        left_book_appointment.setOnClickListener(left_book_apointmentclick);

        mLight.setOnClickListener(mFilterClick);
        mTense.setOnClickListener(mFilterClick);
        mDeep.setOnClickListener(mFilterClick);
        mSeeAll.setOnClickListener(mFilterClick);

        mRateSchool = leftView.findViewById(R.id.left_rate_school);
        mRateSchool.setOnClickListener(mRateSchoolClick);
        mReferFriends = leftView.findViewById(R.id.left_refer_friend);
        mReferFriends.setOnClickListener(mReferFriendClick);


        mSettings = leftView.findViewById(R.id.left_settings);
        mSettings.setTag(NEG);
        mHowToStaySafe = leftView.findViewById(R.id.left_how_to_stay_safe);
        mClearMySecret = leftView.findViewById(R.id.left_clear_my_secrets);
        mUnverifyMe = leftView.findViewById(R.id.left_unverify_me);
        mStats = leftView.findViewById(R.id.left_stats);

        mResetTutorial = leftView.findViewById(R.id.left_reset_tutorial);
        //mGetComments = leftView.findViewById(R.id.left_get_comments);
        mNotifications = leftView.findViewById(R.id.left_notifications);

        //mGetComments.setOnClickListener(this);
        mNotifications.setOnClickListener(mNotificationsClick);
        mSettings.setOnClickListener(mSettingsClick);
        mHowToStaySafe.setOnClickListener(mSettingsClick);
        mClearMySecret.setOnClickListener(mSettingsClick);
        mUnverifyMe.setOnClickListener(mSettingsClick);
        mResetTutorial.setOnClickListener(mSettingsClick);
        mStats.setOnClickListener(mSettingsClick);

        try {
            fixPurchaseViewState();

            fixRateYourSchool(leftView);
        } catch (NullPointerException e) {
        }
    }

    private void fixRateYourSchool(View view) {
        if (Integer.parseInt(Preference.getUserAge()) > 15 && mRateSchool != null) {
            ((TextView) view.findViewById(R.id.left_rate_school_text)).setText(getString(R.string.rate_your_college));
        }
    }

    private boolean getAge() {
        return Integer.parseInt(Preference.getUserAge()) > 15;
    }

    /*private void resolveVerify() throws NullPointerException {
        if (ParseUser.getCurrentUser() == null)
            return;

        new VerifyReviewLoader(new VerifyCallback() {
            @Override
            public void onUpdate(boolean result) {
                showUnverify = result;
                if (mUnverifyMe != null && !showUnverify) {
                    mUnverifyMe.setVisibility(View.GONE);
                    isIAMVerified = false;
                }
            }
        }).execute(ParseUser.getCurrentUser().getObjectId());
    }*/

    private void fixPurchaseViewState() {

    }

    View.OnClickListener mMySecretClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isKeyBoard)
                KeyboardUtil.hideKeyBoard(mSearchEdit, MainActivity.this);
            settingsState = Constants.SETTINGS_SECRETS;
            menu.toggle();
        }
    };

    View.OnClickListener left_book_apointmentclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                if (isKeyBoard)
                    KeyboardUtil.hideKeyBoard(mSearchEdit, MainActivity.this);


                if (ConnectionDetector.isNetworkAvailable(ct)) {
                    QRAccess();
                } else {
                    new ToastUtil(ct, "Please check your internet connection.");
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
            menu.toggle();
        }
    };

    View.OnClickListener left_chatsClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isKeyBoard)
                KeyboardUtil.hideKeyBoard(mSearchEdit, MainActivity.this);

            if (ConnectionDetector.isNetworkAvailable(ct))
            {

                String verify = AppSession.getValue(ct, Constants.USER_VERIFIED);
                if (verify != null && verify.equalsIgnoreCase("false")) {
                    new ToastUtil(ct, ct.getString(R.string.sorry_not_verified));
                    isIAMVerified = false;

                } else {
                    replaceDialog(Constants.ACCESS_IFRIEND);
                    isIAMVerified = true;

                }
            } else {
                new ToastUtil(ct, "Please check your internet connection.");
            }

            // new CheckVerifiedforIfriend().execute();

            menu.toggle();
        }
    };

    View.OnClickListener mReferFriendClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isKeyBoard)
                KeyboardUtil.hideKeyBoard(mSearchEdit, MainActivity.this);
            settingsState = Constants.SETTINGS_REFER_FRIEND;
            menu.toggle();
        }
    };

    View.OnClickListener mRateSchoolClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isKeyBoard)
                KeyboardUtil.hideKeyBoard(mSearchEdit, MainActivity.this);

            if (ConnectionDetector.isNetworkAvailable(ct))
            {

                settingsState = Constants.SETTINGS_RATE_SCHOOL;
            } else {
                new ToastUtil(ct, "Please check your internet connection.");
            }


            menu.toggle();
        }
    };

    View.OnClickListener mNotificationsClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isKeyBoard)
                KeyboardUtil.hideKeyBoard(mSearchEdit, MainActivity.this);

            if (ConnectionDetector.isNetworkAvailable(ct))
            {

                settingsState = Constants.SETTINGS_NOTIFICATIONS;
            } else {
                new ToastUtil(ct, "Please check your internet connection.");
            }

            menu.toggle();
        }
    };

    View.OnClickListener mFilterClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            switch (v.getId()) {
                case R.id.left_latest_filtering:
                    makeHeader(v.getId());
                    switchSelectorFilter();
                    break;
                case R.id.left_light:
                    menu.toggle();
                    setFilter(Constants.STATE_LIGHT);
                    mLatestUpdate.onUpdate();
                    mMainPager.setCurrentItem(3);
                    break;
                case R.id.left_tense:
                    menu.toggle();
                    setFilter(Constants.STATE_TENSE);
                    mLatestUpdate.onUpdate();
                    mMainPager.setCurrentItem(3);
                    break;
                case R.id.left_deep:
                    menu.toggle();
                    setFilter(Constants.STATE_DEEP);
                    mLatestUpdate.onUpdate();
                    mMainPager.setCurrentItem(3);
                    break;
                case R.id.left_see_all:
                    menu.toggle();
                    setFilter(Constants.STATE_SEE_ALL);
                    mLatestUpdate.onUpdate();
                    mMainPager.setCurrentItem(3);
                    break;
            }
        }
    };

    private void setFilter(int data) {


        switch (data) {
            case Constants.STATE_LIGHT:
                try {
                    adapter.setTabTitle(getString(R.string.light));
                    Preference.saveFilter(Constants.STATE_LIGHT);
                    filterState = Constants.STATE_LIGHT;

                    //loadFeed(filterState, false);
                    makeFilter(Constants.STATE_LIGHT);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case Constants.STATE_TENSE:
                try {
                    adapter.setTabTitle(getString(R.string.tense));

                    filterState = Constants.STATE_TENSE;
                    Preference.saveFilter(Constants.STATE_TENSE);
                    //loadFeed(filterState, false);
                    makeFilter(Constants.STATE_TENSE);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case Constants.STATE_DEEP:

                try {
                    adapter.setTabTitle(getString(R.string.deep));

                    filterState = Constants.STATE_DEEP;
                    Preference.saveFilter(Constants.STATE_DEEP);
                    //loadFeed(filterState, false);
                    makeFilter(Constants.STATE_DEEP);
                } catch (Exception e) {
                    e.printStackTrace();
                }


                break;
            case Constants.STATE_SEE_ALL:
                try {
                    adapter.setTabTitle(getString(R.string.see_all));

                    filterState = Constants.STATE_SEE_ALL;
                    Preference.saveFilter(Constants.STATE_SEE_ALL);
                    //loadFeed(filterState, false);
                    makeFilter(Constants.STATE_SEE_ALL);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
        }

    }

    View.OnClickListener mSettingsClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            startTracking(getString(R.string.analytics_Settings));

            switch (v.getId()) {
                case R.id.left_settings:
                    switchSelectorSettings();
                    break;
                case R.id.left_how_to_stay_safe:
                    settingsState = Constants.SETTINGS_POLICY;
                    startTracking(getString(R.string.analytics_How_toSafe));
                    menu.toggle();
                    break;
                case R.id.left_reset_tutorial:
                    Preference.clearMenuTutorial();
                    mMainTutorial.checkForThePerformance();
                    startTracking(getString(R.string.analytics_Resettutorial));
                    menu.toggle();
                    break;
                case R.id.left_clear_my_secrets:
                    settingsState = Constants.SETTINGS_CLEAR;
                    startTracking(getString(R.string.analytics_ClearMySecrets));
                    menu.toggle();
                    break;
                case R.id.left_unverify_me:
                    settingsState = Constants.SETTINGS_UNVERIFY;
                    menu.toggle();
                    startTracking(getString(R.string.analytics_UnverifyMe));
                    break;
                case R.id.left_stats:
                    settingsState = Constants.SETTINGS_STATS;
                    menu.toggle();
                    break;
            }
        }
    };

    private void switchSelectorFilter() {
        if ((Integer) mLatestFiltering.getTag() == NEG) {
            try {
                mLight.setVisibility(View.VISIBLE);
                mTense.setVisibility(View.VISIBLE);
                mDeep.setVisibility(View.VISIBLE);
                mSeeAll.setVisibility(View.VISIBLE);

                YoYo.with(Techniques.BounceInDown).duration(500).playOn(mLight);
                YoYo.with(Techniques.BounceInDown).duration(500).playOn(mTense);
                YoYo.with(Techniques.BounceInDown).duration(500).playOn(mDeep);
                YoYo.with(Techniques.BounceInDown).duration(500).playOn(mSeeAll);

                mLatestFiltering.setTag(POS);
                mLatestFiltering.setBackgroundColor(getResources().getColor(R.color.glimpse_gray));
            } catch (Exception e) {
                e.printStackTrace();
            }


        } else {
            YoYo.with(Techniques.FadeOutUp).withListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {

                    try {
                        mLight.setVisibility(View.GONE);
                        mTense.setVisibility(View.GONE);
                        mDeep.setVisibility(View.GONE);
                        mSeeAll.setVisibility(View.GONE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            }).duration(300).playOn(mLight);

            YoYo.with(Techniques.FadeOutUp).duration(300).playOn(mTense);
            YoYo.with(Techniques.FadeOutUp).duration(300).playOn(mDeep);
            YoYo.with(Techniques.FadeOutUp).duration(300).playOn(mSeeAll);

            mLatestFiltering.setTag(NEG);
            mLatestFiltering.setBackgroundColor(getResources().getColor(R.color.glimpse_gray));
        }
    }

    private void switchSelectorSettings() {
        if ((Integer) mSettings.getTag() == NEG) {
            mHowToStaySafe.setVisibility(View.VISIBLE);
            mResetTutorial.setVisibility(View.VISIBLE);
            mClearMySecret.setVisibility(View.VISIBLE);

            if (showUnverify) {
                mUnverifyMe.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.BounceInDown).duration(300).playOn(mUnverifyMe);
            }

            YoYo.with(Techniques.BounceInDown).duration(300).playOn(mHowToStaySafe);
            YoYo.with(Techniques.BounceInDown).duration(300).playOn(mResetTutorial);
            YoYo.with(Techniques.BounceInDown).duration(300).playOn(mClearMySecret);

            mSettings.setTag(POS);
            mSettings.setBackgroundColor(getResources().getColor(R.color.glimpse_gray));
        } else {
            YoYo.with(Techniques.FadeOutUp).withListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    try {
                        mHowToStaySafe.setVisibility(View.GONE);
                        mResetTutorial.setVisibility(View.GONE);
                        mClearMySecret.setVisibility(View.GONE);
                        mUnverifyMe.setVisibility(View.GONE);
                    } catch (Exception e) {
                        e.printStackTrace();

                    }

                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            }).duration(300).playOn(mHowToStaySafe);

            YoYo.with(Techniques.FadeOutUp).duration(300).playOn(mResetTutorial);
            YoYo.with(Techniques.FadeOutUp).duration(300).playOn(mClearMySecret);
            if (showUnverify)
                YoYo.with(Techniques.FadeOutUp).duration(300).playOn(mUnverifyMe);
            mSettings.setTag(NEG);
            mSettings.setBackgroundColor(getResources().getColor(R.color.glimpse_gray));
        }
    }

    private void makeHeader(int id) {
        try {
            switch (id) {
                case R.id.left_latest_filtering:
                    mLatestFiltering.setBackgroundColor(getResources().getColor(R.color.glimpse_gray));
                    break;
                case R.id.left_my_secrets:
                    break;
                case R.id.left_settings:
                    mSettings.setBackgroundColor(getResources().getColor(R.color.glimpse_gray));
                    break;
                default:
                    mLatestFiltering.setBackgroundColor(getResources().getColor(R.color.glimpse_gray));
                    mSecrets.setBackgroundColor(getResources().getColor(R.color.glimpse_gray));
                    mSettings.setBackgroundColor(getResources().getColor(R.color.glimpse_gray));
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void makeFilter(int id) {

        try {
            switch (id) {
                case Constants.STATE_LIGHT:

                    mLight.setBackgroundColor(getResources().getColor(R.color.grey_transparent));
                    mTense.setBackgroundColor(getResources().getColor(R.color.glimpse_gray));
                    mDeep.setBackgroundColor(getResources().getColor(R.color.glimpse_gray));
                    mSeeAll.setBackgroundColor(getResources().getColor(R.color.glimpse_gray));

                    currentTracking = Constants.TRACK_LIGHT;
                    break;
                case Constants.STATE_TENSE:
                    mLight.setBackgroundColor(getResources().getColor(R.color.glimpse_gray));
                    mTense.setBackgroundColor(getResources().getColor(R.color.grey_transparent));
                    mDeep.setBackgroundColor(getResources().getColor(R.color.glimpse_gray));
                    mSeeAll.setBackgroundColor(getResources().getColor(R.color.glimpse_gray));

                    currentTracking = Constants.TRACK_TENSE;
                    break;
                case Constants.STATE_DEEP:
                    mLight.setBackgroundColor(getResources().getColor(R.color.glimpse_gray));
                    mTense.setBackgroundColor(getResources().getColor(R.color.glimpse_gray));
                    mDeep.setBackgroundColor(getResources().getColor(R.color.grey_transparent));
                    mSeeAll.setBackgroundColor(getResources().getColor(R.color.glimpse_gray));

                    currentTracking = Constants.TRACK_DEEP;
                    break;
                case Constants.STATE_SEE_ALL:
                    mLight.setBackgroundColor(getResources().getColor(R.color.glimpse_gray));
                    mTense.setBackgroundColor(getResources().getColor(R.color.glimpse_gray));
                    mDeep.setBackgroundColor(getResources().getColor(R.color.glimpse_gray));
                    mSeeAll.setBackgroundColor(getResources().getColor(R.color.grey_transparent));

                    currentTracking = Constants.TRACK_ALL;
                    break;
            }

            // if (mMainPager.getCurrentItem() == HAPPY_STATE)
            startTracking(currentTracking);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /*IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener
            = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {

            if (purchase == null || result == null) {
                return;
            }

            if (result.isFailure()) {
                return;
            }

            if (purchase.getSku().equals(PURCHASE_ITEM) && result.isSuccess()) {
                mHelper.consumeAsync(purchase, new IabHelper.OnConsumeFinishedListener() {
                    @Override
                    public void onConsumeFinished(Purchase purchase, IabResult result) {
                        makeUserSuper();
                    }
                });
            }
        }
    };*/

  /*  private void makeUserSuper() {
        ParseUser.getCurrentUser().put(Constants.USER_IS_SUPER_VERIFIED, true);
        ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                fixPurchaseViewState();
            }
        });
    }*/


    @Override
    public void onDestroy() {
        //isRunning.set(false);
        super.onDestroy();
        try {
            /*if (mHelper != null) mHelper.dispose();
            mHelper = null;*/

            chat_count.removeCallbacks(refreshchatcount);

        } catch (Exception e) {
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            //if (mHelper == null) return;
            //if (!mHelper.handleActivityResult(requestCode, resultCode, data))
            //  {
            if (is_From_glimpse) {
                is_From_glimpse = false;
                super.onActivityResult(requestCode, resultCode, data);
            }
            // }
        } catch (Exception e) {

        }

        switch (resultCode) {
            case Constants.SETTINGS_RATE_SCHOOL:

                break;
            case Constants.SECRET_POST:
                try {
                    mMainPager.setCurrentItem(3);
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
                            mMainPager.setCurrentItem(3);
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
        if (isKeyBoard)
            KeyboardUtil.hideKeyBoard(mSearchEdit, MainActivity.this);

        switch (v.getId()) {
            case R.id.menu_toggle:
                menu.toggle();
                break;
            case R.id.left_notifications:
                if (ConnectionDetector.isNetworkAvailable(ct))

                    menu.toggle();
                else {
                    new ToastUtil(ct, "Please check your internet connection.");
                }

                break;
            case R.id.search_toggle:
                if (ConnectionDetector.isNetworkAvailable(ct))
                checkpost();
                else {
                    new ToastUtil(ct, "Please check your internet connection.");
                }
                /*if ((Integer) mSearchToggle.getTag() == 0) {
                    checkpost();
                    // startActivityForResult(new Intent(MainActivity.this, CreateSecretActivity.class), 0);
                } else {
                    KeyboardUtil.hideKeyBoard(mSearchEdit, this);
                    mSearchToggle.setTag(0);
                }*/
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

    private void showUnverifyDialog() {

        replaceDialog(Constants.ACCESS_VERIFY);
    }

    private void showNotificationAccessDialog() {
        replaceDialog(Constants.ACCESS_NOTIFICATIONS);
    }

    public void hideSearchBar() {
        try {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
            if (mSearchEdit != null)
                mSearchEdit.setEnabled(false);
        } catch (NullPointerException e) {

        }
    }

    public void showSearchBar() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        if (mSearchEdit != null)
            mSearchEdit.setEnabled(true);
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


   /* public void loadFeed(final int state, boolean progress)
    {
        switch (state)
        {
            case Constants.STATE_HAPPINESS:
                if (mHappyUpdate != null)
                    mHappyUpdate.onUpdate(null);
                break;
            case Constants.STATE_TRENDING:
                if (mTrendingUpdate != null)
                    mTrendingUpdate.onUpdate(null);
                break;
            case Constants.STATE_SEE_ALL:
                if (mLatestUpdate != null) {
                    mLatestUpdate.onUpdate(null, filterState);
                }
                break;
            case Constants.STATE_TENSE:
                if (mLatestUpdate != null) {
                    mLatestUpdate.onUpdate(null, filterState);
                }
                break;
            case Constants.STATE_DEEP:
                if (mLatestUpdate != null) {
                    mLatestUpdate.onUpdate(null, filterState);
                }
                break;
            case Constants.STATE_LIGHT:
                if (mLatestUpdate != null) {
                    mLatestUpdate.onUpdate(null, filterState);
                }
                break;
        }
    }
*/
/*    public void loadFeed(final int state, boolean progress)
    {
        new FeedLoader(this, new UpdateCallback()
        {
            @Override
            public void onUpdate(List<Secret> list)
            {
                {
                    switch (state)
                    {
                        case Constants.STATE_HAPPINESS:
                            if (mHappyUpdate != null)
                                mHappyUpdate.onUpdate(list);
                            break;
                        case Constants.STATE_TRENDING:
                            if (mTrendingUpdate != null)
                                mTrendingUpdate.onUpdate(list);
                            break;
                        case Constants.STATE_SEE_ALL:
                            if (mLatestUpdate != null) {
                                mLatestUpdate.onUpdate(list, filterState);
                            }
                            break;
                        case Constants.STATE_TENSE:
                            if (mLatestUpdate != null) {
                                mLatestUpdate.onUpdate(list, filterState);
                            }
                            break;
                        case Constants.STATE_DEEP:
                            if (mLatestUpdate != null) {
                                mLatestUpdate.onUpdate(list, filterState);
                            }
                            break;
                        case Constants.STATE_LIGHT:
                            if (mLatestUpdate != null) {
                                mLatestUpdate.onUpdate(list, filterState);
                            }
                            break;
                    }
                }

            }
        }).execute(state, progress);
    }*/

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


            mActivityHandler.postDelayed(mActivityRunnable, 5000);

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

 /*   private void fillTrendingData() {
        new TrendingLoader(new TrendingCallback() {
            @Override
            public void onDone() {
                //loadFeed(Constants.STATE_TRENDING, false);
            }
        }).execute();
    }*/

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0) {
            menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        } else {
            menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        }

        if (mMainTutorial != null) {
            mMainTutorial.updateTopTutorials(position);
        }

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

    public void replaceFragmentInside(Fragment frag, boolean flag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (flag) {
            transaction.add(R.id.glimpse_container, frag);
        } else {
            transaction.add(R.id.happy_glimpse_container, frag);
        }
        transaction.commitAllowingStateLoss();
    }

    public void runMood() {
       /* ObjectMaker.formTrack(ParseUser.getCurrentUser(),
                getString(R.string.mood_track), null).saveInBackground();*/
        if (mMoodRunner != null)
            mMoodRunner.runMood();
    }


    public void moodAccess() {
        replaceDialog(Constants.ACCESS_MOOD);
    }

    public void QRAccess() {

       /* if (is_booking || session_left > 0) {
            replaceDialog(Constants.ACCESS_BOOK_APPONTMENT);

        } else */
        //{
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

        /*try {
            ParseQuery<ParseObject> qrcode = ParseQuery.getQuery("UserSession");
            qrcode.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
            int qr_count = Preference.getQrcode();
            if (qr_count > 0)
                qrcode.whereEqualTo("qid", qr_count);

            qrcode.whereEqualTo("status", "Active");

            qrcode.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    try {
                        if (objects != null && objects.size() > 0) {
                            ParseObject session_obj = objects.get(objects.size() - 1);
                            String sessleft = session_obj.getString("sessionleft");
                            if (sessleft != null && !sessleft.equalsIgnoreCase("")) {
                                session_left = Integer.parseInt(sessleft);
                            }

                            if (session_left > 0 || is_booking) {
                                AgencyId = session_obj.getString("agencyId");
                                replaceDialog(Constants.ACCESS_BOOK_APPONTMENT);
                            } else {
                                session_obj.put("status", "Expire");
                                ParseACL user_session = new ParseACL();
                                user_session.setPublicWriteAccess(true);
                                user_session.setPublicReadAccess(true);
                                session_obj.setACL(user_session);
                                session_obj.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        ShowRating();

                                    }
                                });

                            }
                        } else {
                            if (is_booking) {
                                replaceDialog(Constants.ACCESS_BOOK_APPONTMENT);
                            } else
                                ShowRating();

                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }*/


    }


    public void runHighLights() {
        if (mLatestUpdate != null)
            mLatestUpdate.onHighLightAccess();
    }


    public void runYearComments() {
        if (mLatestUpdate != null)
            mLatestUpdate.onCommentsAccess();
    }

    /*private void getfriendname()
    {
        try {
            if (ConnectionDetector.isNetworkAvailable(this))
            {
                ParseQuery myQuery1 = new ParseQuery("IFriendRequest");
                myQuery1.whereEqualTo("sender", ParseUser.getCurrentUser().getUsername());

                ParseQuery myQuery2 = new ParseQuery("IFriendRequest");
                myQuery2.whereEqualTo("target", ParseUser.getCurrentUser().getUsername());

                List<ParseQuery<ParseObject>> queries = new ArrayList<ParseQuery<ParseObject>>();
                queries.add(myQuery1);
                queries.add(myQuery2);

                ParseQuery<ParseObject> mainQuery = ParseQuery.or(queries);
                mainQuery.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {


                        if (e == null)
                        {
                            if (objects != null && objects.size() > 0)
                            {
                                for (int i = 0; i < objects.size(); i++)
                                {
                                    ParseObject waiting = objects.get(i);
                                    senderW = waiting.getString("sender");
                                    userListNamesOnlyWaiting.add(senderW);
                                    targetW = waiting.getString("target");
                                    userListNamesOnlyWaiting.add(targetW);

                                }
                            }
                        } else {

                            if (e.getCode() == error_code) {
                                LoginActivity.CurrentUsername = ParseUser.getCurrentUser().getUsername();

                                if (LoginActivity.CurrentUsername != null && !LoginActivity.CurrentUsername.equalsIgnoreCase("")) {

                                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();


                                }

                            }

                            System.out.println("Warning fetching ----> " + e.getMessage());
                        }


                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }*/

    public static int chatcount = 0;


    Runnable refreshchatcount = new Runnable() {
        @Override
        public void run() {
            try {


                chat_count.removeCallbacks(refreshchatcount);
                chat_count.postDelayed(refreshchatcount, 3000);
                //getAllUnreadCount();
                if (chatcount > 0) {
                    chat_count.setText("" + chatcount);
                    chat_count.setVisibility(View.VISIBLE);
                } else
                    chat_count.setVisibility(View.GONE);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };

    @Override
    protected void onStart() {
        super.onStart();


    }


/*    private void getVersionName()
    {

        new FetchAppVersionFromGooglePlayStore().execute();


    }*/

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
/*

    private void heartAnimation() {

        try {
            img1 = (ImageView) findViewById(R.id.heart_animation1);
            TranslateAnimation mAnimation1 = new TranslateAnimation(0, 0, height, -100);
            mAnimation1.setDuration(5000);
            mAnimation1.setFillAfter(true);
            mAnimation1.setRepeatCount(0);
            img1.setAnimation(mAnimation1);
            img1.setVisibility(View.VISIBLE);

            img2 = (ImageView) findViewById(R.id.heart_animation2);
            TranslateAnimation mAnimation2 = new TranslateAnimation(0, 0, height, -100);
            mAnimation2.setDuration(15000);
            mAnimation2.setFillAfter(true);
            mAnimation2.setRepeatCount(0);
            img2.setAnimation(mAnimation2);
            img2.setVisibility(View.VISIBLE);

            img3 = (ImageView) findViewById(R.id.heart_animation3);
            TranslateAnimation mAnimation3 = new TranslateAnimation(0, 0, height, -100);
            mAnimation3.setDuration(12000);
            mAnimation3.setFillAfter(true);
            mAnimation3.setRepeatCount(0);
            img3.setAnimation(mAnimation3);
            img3.setVisibility(View.VISIBLE);

            img4 = (ImageView) findViewById(R.id.heart_animation4);
            TranslateAnimation mAnimation4 = new TranslateAnimation(0, 0, height, -100);
            mAnimation4.setDuration(20000);
            mAnimation4.setFillAfter(true);
            mAnimation4.setRepeatCount(0);
            img4.setAnimation(mAnimation4);
            img4.setVisibility(View.VISIBLE);

            img5 = (ImageView) findViewById(R.id.heart_animation5);
            TranslateAnimation mAnimation5 = new TranslateAnimation(0, 0, height, -100);
            mAnimation5.setDuration(25000);
            mAnimation5.setFillAfter(true);
            mAnimation5.setRepeatCount(0);
            img5.setAnimation(mAnimation5);
            img5.setVisibility(View.VISIBLE);

            img6 = (ImageView) findViewById(R.id.heart_animation6);
            TranslateAnimation mAnimation6 = new TranslateAnimation(0, 0, height, -100);
            mAnimation6.setDuration(25000);
            mAnimation6.setFillAfter(true);
            mAnimation6.setRepeatCount(0);
            img6.setAnimation(mAnimation6);
            img6.setVisibility(View.VISIBLE);

            img7 = (ImageView) findViewById(R.id.heart_animation7);
            TranslateAnimation mAnimation7 = new TranslateAnimation(0, 0, height, -100);
            mAnimation7.setDuration(13000);
            mAnimation7.setFillAfter(true);
            mAnimation7.setRepeatCount(0);
            img7.setAnimation(mAnimation7);
            img7.setVisibility(View.VISIBLE);

            img8 = (ImageView) findViewById(R.id.heart_animation8);
            TranslateAnimation mAnimation8 = new TranslateAnimation(0, 0, height, -100);
            mAnimation8.setDuration(30000);
            mAnimation8.setFillAfter(true);
            mAnimation8.setRepeatCount(0);
            img8.setAnimation(mAnimation8);
            img8.setVisibility(View.VISIBLE);

            if (runAnime != null) {
                runAnime.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            heartAnimation();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }, 300000);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

*/


    private int show(String time) {
        int day = 0;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");


            Date Date1 = format.parse(CommonFunction.getdate() + " " + CommonFunction.gettime());
            Date Date2 = format.parse(time);
            long mills = Date1.getTime() - Date2.getTime();
            long Day1 = mills / (1000 * 60 * 60);

            day = (int) Day1 / 24;
            //  long Mins = mills / (1000 * 60  );


            if (day < 0)
                day = 0;


            //  diff =""+day;


        } catch (Exception e) {
            e.printStackTrace();
        }
        return day;
    }

/*
    private void getAppintment() {
        try {



            ParseQuery qrcode1 = ParseQuery.getQuery("Appointment");
            qrcode1.whereEqualTo("Userid", ParseUser.getCurrentUser().getObjectId());
            qrcode1.whereEqualTo("Status", "Pending");

            ParseQuery qrcode = ParseQuery.getQuery("Appointment");
            qrcode.whereEqualTo("Userid", ParseUser.getCurrentUser().getObjectId());
            qrcode.whereEqualTo("Status", "Accepted");
            List<ParseQuery<ParseObject>> queries = new ArrayList<ParseQuery<ParseObject>>();

            queries.add(qrcode);
            queries.add(qrcode1);

            ParseQuery<ParseObject> mainQuery = ParseQuery.or(queries);


            mainQuery.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    try {

                        if (e == null) {
                            if (objects != null && objects.size() > 0) {
                                is_booking = true;

                            }

                        } else {
                            if (e.getCode() == error_code) {
                                LoginActivity.CurrentUsername = ParseUser.getCurrentUser().getUsername();

                                if (LoginActivity.CurrentUsername != null && !LoginActivity.CurrentUsername.equalsIgnoreCase("")) {

                                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();


                                }

                            }
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

    /*private void getMyVerification() {

        new FlaggedReviewLoader(new VerifyCallback() {
            @Override
            public void onUpdate(boolean result) {
                if (!result) {
                    new VerifyReviewLoader(new VerifyCallback() {
                        @Override
                        public void onUpdate(boolean result) {

                            if (result) {
                                isIAMVerified = true;
                            } else {
                                isIAMVerified = false;
                            }
                        }
                    }).execute(ParseUser.getCurrentUser().getObjectId());
                }
            }
        }).execute();
    }*/


/*
    private void onLineUser() {


        ParseQuery onlinequery = new ParseQuery("OnlineUser");
        onlinequery.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
        onlinequery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if (e == null) {
                    try {
                        if (objects != null && objects.size() > 0) {


                            ParseObject online = objects.get(objects.size() - 1);
                            online.put("keep_alive", "true");
                            online.put("username", ParseUser.getCurrentUser().getUsername());

                            ParseACL acl = new ParseACL();
                            acl.setPublicReadAccess(true);
                            acl.setPublicWriteAccess(true);
                            online.setACL(acl);

                            online.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {

                                }
                            });


                        } else {
                            ParseObject online = new ParseObject("OnlineUser");
                            online.put("keep_alive", "true");
                            online.put("username", ParseUser.getCurrentUser().getUsername());

                            ParseACL acl = new ParseACL();
                            acl.setPublicReadAccess(true);
                            acl.setPublicWriteAccess(true);
                            online.setACL(acl);

                            online.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {

                                }
                            });
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }


                } else {
                    e.printStackTrace();
                }

            }

        });

*/
/*
        if (runAnime != null) {
            try {
                runAnime.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            onLineUser();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }, 240000);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }*//*


    }
*/

/*
    private void deviceToken() {
        if (deviceTOken != null && !deviceTOken.equalsIgnoreCase("")) {
            ParseQuery<ParseObject> devicetoken1 = ParseQuery.getQuery("UserToken");
            devicetoken1.whereEqualTo("imei", imei_numer);

            ParseQuery<ParseObject> devicetoken2 = ParseQuery.getQuery("UserToken");
            devicetoken2.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());

            List<ParseQuery<ParseObject>> queries = new ArrayList<ParseQuery<ParseObject>>();
            queries.add(devicetoken1);
            queries.add(devicetoken2);

            ParseQuery<ParseObject> mainQuery = ParseQuery.or(queries);

            mainQuery.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {

                    if (e == null) {
                        try {
                            if (objects != null && objects.size() > 0) {
                                final ParseObject devicetokenalready = objects.get(objects.size() - 1);
                                devicetokenalready.put("devicetoken", deviceTOken);
                                devicetokenalready.put("deviceType", "android");
                                devicetokenalready.put("imei", imei_numer);
                                devicetokenalready.put("username", ParseUser.getCurrentUser().getUsername());
                                ParseACL acl = new ParseACL();
                                acl.setPublicReadAccess(true);
                                acl.setPublicWriteAccess(true);
                                devicetokenalready.setACL(acl);

                                devicetokenalready.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {

                                    }
                                });
                            } else {
                                final ParseObject devicetokennew = new ParseObject("UserToken");
                                devicetokennew.put("devicetoken", deviceTOken);
                                devicetokennew.put("deviceType", "android");
                                devicetokennew.put("imei", imei_numer);
                                devicetokennew.put("username", ParseUser.getCurrentUser().getUsername());

                                ParseACL acl = new ParseACL();
                                acl.setPublicReadAccess(true);
                                acl.setPublicWriteAccess(true);
                                devicetokennew.setACL(acl);

                                devicetokennew.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {


                                    }
                                });
                            }
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }

                    } else {
                        e.printStackTrace();
                    }
                }

            });
        }
    }

*/

    String deduction_date_string = "";


    int select_index = -1;

    private void drawpetlist() {

        pet_main_layout.removeAllViews();
        int[] petimagearray = {R.drawable.monkey_face, R.drawable.panda_face,
                R.drawable.horse_face, R.drawable.rabbit_face,
                R.drawable.donkey_face, R.drawable.sheep_face,
                R.drawable.deer_face, R.drawable.tiger_face,
                R.drawable.parrot_face, R.drawable.meerkat_face};

        final List<LinearLayout> petrowlayoutlist = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
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

/*

    private void Getsessionleft() {
        ParseQuery<ParseObject> qrcode = ParseQuery.getQuery("UserSession");
        qrcode.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
        qrcode.whereEqualTo("status", "Active");
        final int qr_count = Preference.getQrcode();

        if (qr_count > 0)
            qrcode.whereEqualTo("qid", qr_count);


        qrcode.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                try {
                    if (objects != null && objects.size() > 0) {
                        ParseObject session_obj = objects.get(objects.size() - 1);
                        String sessleft = session_obj.getString("sessionleft");
                        if (sessleft != null && !sessleft.equalsIgnoreCase("")) {
                            session_left = Integer.parseInt(sessleft);

                            AgencyId = session_obj.getString("agencyId");

                            if (qr_count >= 0) {
                                int qrcode = session_obj.getInt("qid");
                                if (qrcode > 0) {
                                    Preference.setQrcode(qrcode);
                                }
                            }
                        }
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

            }
        });
    }
*/

/*
    private static void getme2count() {

        try {
            if (ParseUser.getCurrentUser().get(Constants.USER_HELP_DATE) == null) {
                Calendar nextday = Calendar.getInstance();
                nextday.add(Calendar.DAY_OF_MONTH, -1);
                Date date = nextday.getTime();
                ParseQuery<ParseObject> me2quesry = ParseQuery.getQuery(Constants.ME2_CLASS);
                me2quesry.include("receivingUser");
                me2quesry.whereEqualTo("user", ParseUser.getCurrentUser());
                me2quesry.whereGreaterThanOrEqualTo("createdAt", date);


                me2quesry.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {

                        if (e == null) {

                            try {
                                if (objects != null && objects.size() > 0) {
                                    for (int i = 0; i < objects.size(); i++) {
                                        ParseObject objCount = objects.get(i);

                                        ParseUser objUser = objCount.getParseUser("receivingUser");
                                        String strUserName = objUser.getUsername();

                                        if (users != null && !users.contains(strUserName)) {
                                            users.add(strUserName);
                                        }
                                    }

                                    getheartcount();
                                } else {
                                    getheartcount();
                                }
                            } catch (Exception e2) {
                                e2.printStackTrace();
                            }

                        } else {
                            getheartcount();
                        }
                    }

                    ;
                });
            } else {

                Calendar nextday = Calendar.getInstance();
                nextday.setTime(ParseUser.getCurrentUser().getDate(Constants.USER_HELP_DATE));
                nextday.add(Calendar.DAY_OF_MONTH, 1);
                Date date = nextday.getTime();

                ParseQuery<ParseObject> me2quesry = ParseQuery.getQuery(Constants.ME2_CLASS);
                me2quesry.include("receivingUser");
                me2quesry.whereEqualTo("user", ParseUser.getCurrentUser());
                me2quesry.whereGreaterThanOrEqualTo("createdAt", date);


                me2quesry.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {

                        if (e == null) {

                            try {
                                if (objects != null && objects.size() > 0) {
                                    for (int i = 0; i < objects.size(); i++) {
                                        ParseObject objCount = objects.get(i);
                                        ParseUser objUser = objCount.getParseUser("receivingUser");
                                        String strUserName = objUser.getUsername();

                                        if (users != null && !users.contains(strUserName)) {
                                            users.add(strUserName);
                                        }
                                    }
                                    getheartcount();
                                } else {
                                    getheartcount();
                                }
                            } catch (Exception e2) {
                                e2.printStackTrace();
                            }

                        } else {
                            getheartcount();
                        }
                    }

                    ;
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private static void getheartcount() {

        try {
            if (ParseUser.getCurrentUser().get(Constants.USER_HELP_DATE) == null) {
                Calendar nextday = Calendar.getInstance();
                nextday.add(Calendar.DAY_OF_MONTH, -1);
                Date date = nextday.getTime();
                ParseQuery<ParseObject> me2quesry = ParseQuery.getQuery(Constants.HEART_CLASS);
                me2quesry.include("receivingUser");
                me2quesry.whereEqualTo("user", ParseUser.getCurrentUser());
                me2quesry.whereGreaterThanOrEqualTo("createdAt", date);


                me2quesry.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {

                        if (e == null) {

                            try {
                                if (objects != null && objects.size() > 0) {
                                    for (int i = 0; i < objects.size(); i++) {
                                        ParseObject objCount = objects.get(i);

                                        // Date createddate = objCount.getCreatedAt();
                                        ParseUser objUser = objCount.getParseUser("receivingUser");
                                        String strUserName = objUser.getUsername();

                                        if (users != null && !users.contains(strUserName)) {
                                            users.add(strUserName);
                                        }
                                    }
                                    gethugcount();
                                } else {
                                    gethugcount();
                                }
                            } catch (Exception e2) {
                                e2.printStackTrace();
                            }

                        } else {
                            gethugcount();
                        }
                    }

                    ;
                });
            } else {

                Calendar nextday = Calendar.getInstance();
                nextday.setTime(ParseUser.getCurrentUser().getDate(Constants.USER_HELP_DATE));
                nextday.add(Calendar.DAY_OF_MONTH, 1);
                Date date = nextday.getTime();

                ParseQuery<ParseObject> me2quesry = ParseQuery.getQuery(Constants.HEART_CLASS);
                me2quesry.include("receivingUser");
                me2quesry.whereEqualTo("user", ParseUser.getCurrentUser());
                me2quesry.whereGreaterThanOrEqualTo("updatedAt", date);


                me2quesry.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {

                        if (e == null) {

                            try {
                                if (objects != null && objects.size() > 0) {
                                    for (int i = 0; i < objects.size(); i++) {
                                        ParseObject objCount = objects.get(i);
                                        ParseUser objUser = objCount.getParseUser("receivingUser");
                                        String strUserName = objUser.getUsername();

                                        if (users != null && !users.contains(strUserName)) {
                                            users.add(strUserName);
                                        }
                                    }
                                    gethugcount();
                                } else {
                                    gethugcount();
                                }
                            } catch (Exception e2) {
                                e2.printStackTrace();
                            }

                        } else {
                            gethugcount();
                        }
                    }

                    ;
                });

            }
        } catch (Exception e) {


        }


    }


    private static void gethugcount() {
        try {
            if (ParseUser.getCurrentUser().get(Constants.USER_HELP_DATE) == null) {
                Calendar nextday = Calendar.getInstance();
                nextday.add(Calendar.DAY_OF_MONTH, -1);
                Date date = nextday.getTime();
                ParseQuery<ParseObject> me2quesry = ParseQuery.getQuery(Constants.HUG_CLASS);
                me2quesry.include("receivingUser");
                me2quesry.whereEqualTo("user", ParseUser.getCurrentUser());
                me2quesry.whereGreaterThanOrEqualTo("createdAt", date);

                me2quesry.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {

                        if (e == null) {

                            try {
                                if (objects != null && objects.size() > 0) {
                                    for (int i = 0; i < objects.size(); i++) {
                                        ParseObject objCount = objects.get(i);


                                        ParseUser objUser = objCount.getParseUser("receivingUser");
                                        String strUserName = objUser.getUsername();

                                        if (users != null && !users.contains(strUserName)) {
                                            users.add(strUserName);
                                        }
                                    }
                                    getCommentcountcount();
                                } else {
                                    getCommentcountcount();
                                }
                            } catch (Exception e2) {
                                e2.printStackTrace();
                            }


                        } else {
                            getCommentcountcount();
                        }
                    }

                    ;
                });
            } else {

                Calendar nextday = Calendar.getInstance();
                nextday.setTime(ParseUser.getCurrentUser().getDate(Constants.USER_HELP_DATE));
                nextday.add(Calendar.DAY_OF_MONTH, 1);
                Date date = nextday.getTime();

                ParseQuery<ParseObject> me2quesry = ParseQuery.getQuery(Constants.HUG_CLASS);
                me2quesry.include("receivingUser");
                me2quesry.whereEqualTo("user", ParseUser.getCurrentUser());
                me2quesry.whereGreaterThanOrEqualTo("createdAt", date);

                me2quesry.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {

                        if (e == null) {

                            try {
                                if (objects != null && objects.size() > 0) {
                                    for (int i = 0; i < objects.size(); i++) {
                                        ParseObject objCount = objects.get(i);
                                        ParseUser objUser = objCount.getParseUser("receivingUser");
                                        String strUserName = objUser.getUsername();
                                        if (users != null && !users.contains(strUserName)) {
                                            users.add(strUserName);
                                        }
                                    }
                                    getCommentcountcount();
                                } else {
                                    getCommentcountcount();
                                }
                            } catch (Exception e2) {
                                e2.printStackTrace();
                            }

                        } else {
                            getCommentcountcount();
                        }
                    }

                    ;
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private static void getCommentcountcount() {

        try {
            if (ParseUser.getCurrentUser().get(Constants.USER_HELP_DATE) == null) {
                Calendar nextday = Calendar.getInstance();
                nextday.add(Calendar.DAY_OF_MONTH, -1);
                Date date = nextday.getTime();
                ParseQuery<ParseObject> me2quesry = ParseQuery.getQuery(Constants.COMMENTS_CLASS);
                me2quesry.include("secret");
                me2quesry.whereEqualTo("user", ParseUser.getCurrentUser());
                me2quesry.whereGreaterThanOrEqualTo("createdAt", date);

                me2quesry.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {

                        if (e == null) {

                            try {
                                if (objects != null && objects.size() > 0) {
                                    for (int i = 0; i < objects.size(); i++) {
                                        ParseObject objCount = objects.get(i);


                                        // NSString *strUserName = objSecret[@"createdByUser"];

                                        ParseObject objUser = objCount.getParseObject("secret");
                                        String strUserName = objUser.getString("createdByUser");


                                        if (users != null && !users.contains(strUserName)) {
                                            users.add(strUserName);
                                        }
                                    }

                                }
                            } catch (Exception e2) {
                                e2.printStackTrace();
                            }


                        }
                    }

                    ;
                });
            } else {

                Calendar nextday = Calendar.getInstance();
                nextday.setTime(ParseUser.getCurrentUser().getDate(Constants.USER_HELP_DATE));
                nextday.add(Calendar.DAY_OF_MONTH, 1);
                Date date = nextday.getTime();

                ParseQuery<ParseObject> me2quesry = ParseQuery.getQuery(Constants.COMMENTS_CLASS);
                me2quesry.include("receivingUser");
                me2quesry.whereEqualTo("user", ParseUser.getCurrentUser());
                me2quesry.whereGreaterThanOrEqualTo("createdAt", date);

                me2quesry.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {

                        if (e == null) {

                            try {
                                if (objects != null && objects.size() > 0) {
                                    for (int i = 0; i < objects.size(); i++) {
                                        ParseObject objCount = objects.get(i);
                                        ParseUser objUser = objCount.getParseUser("receivingUser");
                                        String strUserName = objUser.getUsername();
                                        if (users != null && !users.contains(strUserName)) {
                                            users.add(strUserName);
                                        }
                                    }

                                }
                            } catch (Exception e2) {
                                e2.printStackTrace();
                            }

                        }
                    }

                    ;
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
*/


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

                StaticDataDTO staticDataDTO = new StaticDataDTO();
                StaticObjectDTO staticObjectDTO = new StaticObjectDTO(staticDataDTO);
                stataicObjectDTO = http.GetstaticData(staticObjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {

            try {
                if(stataicObjectDTO!= null && stataicObjectDTO.getiFriendSettingDTO()!= null)
                {
                    if (!Preference.getUserDisclaimer())
                        disclaimer_layout.setVisibility(View.VISIBLE);
                    else
                    {

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
            } catch (Exception e)
            {
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
            petlayout.setVisibility(View.VISIBLE);

            String hhug_food = MainActivity.petAvtarInfoDTO.getHug_oxygen();
            if (hhug_food != null && !hhug_food.equalsIgnoreCase("") && !hhug_food.equalsIgnoreCase("null")) {
                oxygen = Integer.parseInt(hhug_food);
            }
            String hheart_food = MainActivity.petAvtarInfoDTO.getHeart_food();
            if (hheart_food != null && !hheart_food.equalsIgnoreCase("") && !hheart_food.equalsIgnoreCase("null")) {
                food = Integer.parseInt(hheart_food);
            }
            String hme_water = MainActivity.petAvtarInfoDTO.getM2_water();
            if (hme_water != null && !hme_water.equalsIgnoreCase("") && !hme_water.equalsIgnoreCase("null")) {
                water = Integer.parseInt(hme_water);
            }


            water_progress.setProgress(water);
            food_progress.setProgress(food);


            int[] petimagearray = {R.drawable.monkey_face, R.drawable.panda_face,
                    R.drawable.horse_face, R.drawable.rabbit_face,
                    R.drawable.donkey_face, R.drawable.sheep_face,
                    R.drawable.deer_face, R.drawable.tiger_face,
                    R.drawable.parrot_face, R.drawable.meerkat_face};


            for (int i = 0; i < petnamearray.length; i++) {
                if (pet_name.equalsIgnoreCase(petnamearray[i])) {
                    petimage.setImageResource(petimagearray[i]);
                    break;
                }
            }


            // }

            int oxyzen = (int) oxygen / 10;
            oxygenlevel.setBackgroundColor(Color.parseColor(colorsking[oxyzen - 1]));

        } catch (Exception e1) {
            e1.printStackTrace();
        }

    }

    static SetVerifyFlagConditionDTO setVerifyFlagConditionDTO = null;

    static ScratchCoupondataDTO scratchCoupondataDTO = null;

    public static void showscratchview(final String objectid) {
        try {

            int comments_scratch = 0;

            if (comments_count > 0)
                comments_scratch = comments_count ;


            try {
                scratch_count = Integer.parseInt(MainActivity.petAvtarInfoDTO.getScratch_count());
            } catch (Exception e) {
                e.printStackTrace();
            }


            if (scratch_count < 5 + comments_scratch) {

                //  coupon_image = coupon_imag;
                String[] mehuglike = {"oxygen", "food", "water"};

                scratch_layout.setVisibility(View.VISIBLE);
                scratchtextview.removeAllViews();
                final ScratchTextView scratchText = new ScratchTextView(ct);

                final int state = PendingIFriendsAdapter.randInt(0, 3) % 3;

                scratchText.setText("You get 10% " + mehuglike[state] + " in bonus");
                scratchText.setGravity(Gravity.CENTER);
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, width1 / 8);
                scratchText.setLayoutParams(lp);
                scratchtextview.addView(scratchText);
                scratchText.setTextSize(16);
                cancelscratch.setBackgroundColor(Color.parseColor("#fe8940"));
                cancelscratch.setText("Cancel");

                scratchText.setRevealListener(new ScratchTextView.IRevealListener() {
                    @Override
                    public void onRevealed(ScratchTextView tv) {
                        scratch_count++;
                        if (scratchscretid != null)
                            scratchscretid.add(objectid);
                        //secret.setShowCoupon(2);
                        switch (state) {
                            case Constants.INCREASEOXYGEN:
                                try {
                                    scratch_layout.setVisibility(View.GONE);

                                    int hug_oxygen = 10;
                                    if (MainActivity.petAvtarInfoDTO != null) {
                                        try {
                                            String hhug_food = MainActivity.petAvtarInfoDTO.getHug_oxygen();
                                            if (hhug_food != null && !hhug_food.equalsIgnoreCase("") && !hhug_food.equalsIgnoreCase("null")) {
                                                hug_oxygen = Integer.parseInt(hhug_food);
                                                if (hug_oxygen <= 90)
                                                    hug_oxygen = hug_oxygen + 10;


                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        MainActivity.petAvtarInfoDTO.setHug_oxygen("" + hug_oxygen);
                                        CommonFunction.oxygen_maintain();

                                        String total_count = MainActivity.petAvtarInfoDTO.getTotal_scratch_count();
                                        int total_scrcount = 0;
                                        try {
                                            if (total_count != null && !total_count.equalsIgnoreCase("")) {
                                                total_scrcount = Integer.parseInt(total_count);
                                                total_scrcount = total_scrcount + 1;
                                            }

                                            MainActivity.petAvtarInfoDTO.setTotal_scratch_count("" + total_scrcount);
                                        } catch (Exception e) {
                                            MainActivity.petAvtarInfoDTO.setTotal_scratch_count("1");
                                            total_scrcount = 1;
                                            e.printStackTrace();
                                        }


                                        String scrat_count = MainActivity.petAvtarInfoDTO.getScratch_count();
                                        int scr_scrcount = 0;
                                        try {
                                            if (scrat_count != null && !scrat_count.equalsIgnoreCase("")) {
                                                scr_scrcount = Integer.parseInt(scrat_count);
                                                scr_scrcount = scr_scrcount + 1;
                                            }

                                            MainActivity.petAvtarInfoDTO.setScratch_count("" + scr_scrcount);
                                        } catch (Exception e) {
                                            scr_scrcount = 1;
                                            MainActivity.petAvtarInfoDTO.setScratch_count("1");
                                            e.printStackTrace();
                                        }

                                        scratchCoupondataDTO = new ScratchCoupondataDTO(enc_username, "hug_oxygen", "" + hug_oxygen, "" + total_scrcount, "" + scr_scrcount);
                                        new UpdateAvtarPercentage().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                                    }

                                    new ParticleSystem(((Activity) ct), 1000, R.drawable.star_pink, 1500)
                                            .setSpeedRange(0.1f, 0.25f)
                                            .setRotationSpeedRange(90, 180)
                                            .setInitialRotationRange(0, 360)
                                            .oneShot(scratchText, 300);

                                    scratchText.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            new ParticleSystem(((Activity) ct), 100, R.drawable.star_white, 1500)
                                                    .setSpeedRange(0.1f, 0.25f)
                                                    .setRotationSpeedRange(90, 180)
                                                    .setInitialRotationRange(0, 360)
                                                    .oneShot(scratchText, 100);

                                        }
                                    }, 150);

                                    scratchText.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            new ParticleSystem(((Activity) ct), 100, R.drawable.staricon, 1500)
                                                    .setSpeedRange(0.1f, 0.25f)
                                                    .setRotationSpeedRange(90, 180)
                                                    .setInitialRotationRange(0, 360)
                                                    .oneShot(scratchText, 100);
                                        }
                                    }, 300);


                                    // cancelscratch.setBackgroundColor(Color.parseColor("#45b39c"));
                                    //cancelscratch.setText("Done");

                                    //FeedAdapter.mDataList.get(position).setShowCoupon(2);

                                    Tracker t = ((SilentSecretApplication) ct).getTracker(
                                            SilentSecretApplication.TrackerName.APP_TRACKER);
                                    t.setScreenName(MainActivity.ct.getString(R.string.analytics_Scratch));
                                    t.send(new HitBuilders.AppViewBuilder().build());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                break;
                            case Constants.INCREASEFOOD:
                                try {
                                    scratch_layout.setVisibility(View.GONE);
                                    new ParticleSystem(((Activity) ct), 100, R.drawable.star_pink, 1500)
                                            .setSpeedRange(0.1f, 0.25f)
                                            .setRotationSpeedRange(90, 180)
                                            .setInitialRotationRange(0, 360)
                                            .oneShot(scratchText, 100);


                                    scratchText.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            new ParticleSystem(((Activity) ct), 100, R.drawable.star_white, 1500)
                                                    .setSpeedRange(0.1f, 0.25f)
                                                    .setRotationSpeedRange(90, 180)
                                                    .setInitialRotationRange(0, 360)
                                                    .oneShot(scratchText, 100);

                                        }
                                    }, 150);

                                    scratchText.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            new ParticleSystem(((Activity) ct), 100, R.drawable.staricon, 1500)
                                                    .setSpeedRange(0.1f, 0.25f)
                                                    .setRotationSpeedRange(90, 180)
                                                    .setInitialRotationRange(0, 360)
                                                    .oneShot(scratchText, 100);
                                        }
                                    }, 300);


                                    int heart_food = 10;
                                    if (MainActivity.petAvtarInfoDTO != null) {
                                        try {
                                            String hheart_food = MainActivity.petAvtarInfoDTO.getHeart_food();
                                            if (hheart_food != null && !hheart_food.equalsIgnoreCase("") && !hheart_food.equalsIgnoreCase("null")) {
                                                heart_food = Integer.parseInt(hheart_food);
                                                if (heart_food <= 90)
                                                    heart_food = heart_food + 10;
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        MainActivity.petAvtarInfoDTO.setHeart_food("" + heart_food);
                                        CommonFunction.food_maintain();

                                        String total_count = MainActivity.petAvtarInfoDTO.getTotal_scratch_count();
                                        int total_scrcount = 0;
                                        try {
                                            if (total_count != null && !total_count.equalsIgnoreCase("")) {
                                                total_scrcount = Integer.parseInt(total_count);
                                                total_scrcount = total_scrcount + 1;
                                            }

                                            MainActivity.petAvtarInfoDTO.setTotal_scratch_count("" + total_scrcount);
                                        } catch (Exception e) {
                                            total_scrcount = 1;
                                            MainActivity.petAvtarInfoDTO.setTotal_scratch_count("1");
                                            e.printStackTrace();
                                        }


                                        String scrat_count = MainActivity.petAvtarInfoDTO.getScratch_count();
                                        int scr_scrcount = 0;
                                        try {
                                            if (scrat_count != null && !scrat_count.equalsIgnoreCase("")) {
                                                scr_scrcount = Integer.parseInt(scrat_count);
                                                scr_scrcount = scr_scrcount + 1;
                                            }

                                            MainActivity.petAvtarInfoDTO.setScratch_count("" + scr_scrcount);
                                        } catch (Exception e) {
                                            MainActivity.petAvtarInfoDTO.setScratch_count("1");
                                            scr_scrcount = 1;
                                            e.printStackTrace();
                                        }


                                        scratchCoupondataDTO = new ScratchCoupondataDTO(enc_username, "heart_food", "" + heart_food, "" + total_scrcount, "" + scr_scrcount);
                                        new UpdateAvtarPercentage().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                break;
                            case Constants.INCREASEWATER:
                                try {
                                    scratch_layout.setVisibility(View.GONE);
                                    new ParticleSystem(((Activity) ct), 100, R.drawable.star_pink, 1500)
                                            .setSpeedRange(0.1f, 0.25f)
                                            .setRotationSpeedRange(90, 180)
                                            .setInitialRotationRange(0, 360)
                                            .oneShot(scratchText, 100);
                                    scratchText.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            new ParticleSystem(((Activity) ct), 100, R.drawable.star_white, 1500)
                                                    .setSpeedRange(0.1f, 0.25f)
                                                    .setRotationSpeedRange(90, 180)
                                                    .setInitialRotationRange(0, 360)
                                                    .oneShot(scratchText, 100);
                                        }
                                    }, 150);

                                    scratchText.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            new ParticleSystem(((Activity) ct), 100, R.drawable.staricon, 1500)
                                                    .setSpeedRange(0.1f, 0.25f)
                                                    .setRotationSpeedRange(90, 180)
                                                    .setInitialRotationRange(0, 360)
                                                    .oneShot(scratchText, 100);
                                        }
                                    }, 300);


                                    int water = 10;
                                    if (MainActivity.petAvtarInfoDTO != null) {
                                        try {
                                            String me2_water = MainActivity.petAvtarInfoDTO.getM2_water();
                                            if (me2_water != null && !me2_water.equalsIgnoreCase("") && !me2_water.equalsIgnoreCase("null")) {
                                                water = Integer.parseInt(me2_water);
                                                if (water <= 90)
                                                    water = water + 10;


                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                        MainActivity.petAvtarInfoDTO.setM2_water("" + water);
                                        CommonFunction.water_maintain();


                                        String total_count = MainActivity.petAvtarInfoDTO.getTotal_scratch_count();
                                        int total_scrcount = 0;
                                        try {
                                            if (total_count != null && !total_count.equalsIgnoreCase("")) {
                                                total_scrcount = Integer.parseInt(total_count);
                                                total_scrcount = total_scrcount + 1;
                                            }

                                            MainActivity.petAvtarInfoDTO.setTotal_scratch_count("" + total_scrcount);
                                        } catch (Exception e) {
                                            MainActivity.petAvtarInfoDTO.setTotal_scratch_count("1");
                                            e.printStackTrace();
                                            total_scrcount = 1;
                                        }

                                        String scrat_count = MainActivity.petAvtarInfoDTO.getScratch_count();
                                        int scr_scrcount = 0;
                                        try {
                                            if (scrat_count != null && !scrat_count.equalsIgnoreCase("")) {
                                                scr_scrcount = Integer.parseInt(scrat_count);
                                                scr_scrcount = scr_scrcount + 1;
                                            }

                                            MainActivity.petAvtarInfoDTO.setScratch_count("" + scr_scrcount);
                                        } catch (Exception e) {
                                            MainActivity.petAvtarInfoDTO.setScratch_count("1");
                                            scr_scrcount = 1;
                                            e.printStackTrace();
                                        }

                                        scratchCoupondataDTO = new ScratchCoupondataDTO(enc_username, "m2_water", "" + water, "" + total_scrcount, "" + scr_scrcount);
                                        new UpdateAvtarPercentage().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                break;
                        }


                        try {
                            if (scratch_count == 1) {
                                AlertDialog.Builder updateAlert = new AlertDialog.Builder(ct);
                                updateAlert.setIcon(R.drawable.ic_launcher);
                                updateAlert.setTitle("Cypher");
                                updateAlert.setMessage("Leave comments on posts to increase pet water");
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


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        // Toast.makeText(ct, "Revealed!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onRevealPercentChangedListener(ScratchTextView stv, float percent) {
                        if (is_scratch_anim_done) {
                            is_scratch_anim_done = false;
                            scratchText.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    is_scratch_anim_done = true;
                                    new ParticleSystem(((Activity) ct), 50, R.drawable.staricon2, 500)
                                            .setSpeedRange(0.1f, 0.25f)
                                            .setRotationSpeedRange(90, 180)
                                            .setInitialRotationRange(0, 360)
                                            .oneShot(scratchText, 20);
                                }
                            }, 10);
                        }
                    }
                });

            } else {
                Toast.makeText(ct, "Sorry!, Your today's maximum scratch limit exceeded", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    static boolean is_scratch_anim_done = true;

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
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.helio.cypher&hl=en")));
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





/*    private void Getflagunflag()
    {
        ParseQuery<ParseObject> verifiedtable = ParseQuery.getQuery("Flag");
        verifiedtable.whereEqualTo("userId", ParseUser.getCurrentUser().getObjectId());
        verifiedtable.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if (objects != null && objects.size() > 0) {

                    ParseObject currentFriendObject = objects.get(0);
                    boolean isverifiedornot = currentFriendObject.getBoolean("canPost");


                    if (isverifiedornot) {
                        is_flag = false;
                        ParseUser.getCurrentUser().put(Constants.USER_FLAGGED, "no");


                    } else {
                        is_flag = true;
                        ParseUser.getCurrentUser().put(Constants.USER_FLAGGED, "yes");

                    }

                    ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {

                        }
                    });
                }

            }
        });


    }*/


    private void checkpost() {
        if (!is_flag)
            startActivityForResult(new Intent(MainActivity.this, CreateSecretActivity.class), 0);
        else
            Toast.makeText(ct, "You are not permitted to share a secret.", Toast.LENGTH_SHORT).show();
/*
        ParseQuery<ParseObject> verifiedtable = ParseQuery.getQuery("Flag");
        verifiedtable.whereEqualTo("userId", ParseUser.getCurrentUser().getObjectId());
        verifiedtable.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if (objects != null && objects.size() > 0) {

                    ParseObject currentFriendObject = objects.get(0);
                    boolean isverifiedornot = currentFriendObject.getBoolean("canPost");

                    if (isverifiedornot) {
                        is_flag = false;
                        ParseUser.getCurrentUser().put(Constants.USER_FLAGGED, "no");
                        startActivityForResult(new Intent(MainActivity.this, CreateSecretActivity.class), 0);

                    } else {
                        is_flag = true;
                        ParseUser.getCurrentUser().put(Constants.USER_FLAGGED, "yes");
                        Toast.makeText(ct, "You are not permitted to share a secret.", Toast.LENGTH_SHORT).show();
                    }


                    ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {

                        }
                    });
                } else {
                    startActivityForResult(new Intent(MainActivity.this, CreateSecretActivity.class), 0);
                }

            }
        });*/


    }


    class FetchAppVersionFromGooglePlayStore extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... urls) {
            try {
                return
                        Jsoup.connect("https://play.google.com/store/apps/details?id=" + "com.helio.cypher" + "&hl=en")
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


    private class GetPetInfo extends AsyncTask<String, String, Bitmap> {

        android.app.ProgressDialog pDialog;
        String status = "";
        Bitmap bitmap;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected Bitmap doInBackground(String... args) {
            try {

                if (petAvtarInfoDTO != null)
                    petAvtarInfoDTO = null;

                IfriendRequest http = new IfriendRequest(ct);

                GetPetConditionDTO getPetConditionDTO = new GetPetConditionDTO(enc_username);
                GetPetInfoDTO getPetInfoDTO = new GetPetInfoDTO(Constants.ENCRYPT_MYVIRTUALSTATUS_TABLE, Constants.ENCRYPT_FIND_METHOD, getPetConditionDTO);

                GetPetInfoObjectDTO getPetInfoObjectDTO = new GetPetInfoObjectDTO(getPetInfoDTO);

                petAvtarInfoDTO = http.GetPetInfo(getPetInfoObjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {
            try {
                if (petAvtarInfoDTO != null) {
                    ShowPetStatus();
                }
            } catch (Exception e) {
                e.printStackTrace();
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


    private static class UpdateAvtarPercentage extends AsyncTask<String, String, Bitmap> {

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
                ScratchCouponDTO scratchCouponDTO = new ScratchCouponDTO(scratchCoupondataDTO);
                ScratchCouponObjectDTO setPetInfoObjectDTO = new ScratchCouponObjectDTO(scratchCouponDTO);
                http.UpdateScratch(setPetInfoObjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {

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
            try {
                mUnverifyMe.setVisibility(View.GONE);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

/*

    private static class GetStaticData extends AsyncTask<String, String, Bitmap> {

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

*/

/*
    private class CheckVerifiedforIfriend extends android.os.AsyncTask<String, String, Bitmap> {

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
                FindbyNameDTO findbyNameDTO = new FindbyNameDTO(enc_username);
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

                String isverifiedornot = AppSession.getValue(ct, Constants.USER_VERIFIED);

                if (isverifiedornot != null && isverifiedornot.equalsIgnoreCase("true")) {
                    isIAMVerified = true;
                    replaceDialog(Constants.ACCESS_IFRIEND);
                } else {

                    isIAMVerified = false;
                    new ToastUtil(ct, ct.getString(R.string.sorry_not_verified));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

*/

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

                String verify = AppSession.getValue(ct, Constants.USER_VERIFIED);
                if (verify != null && verify.equalsIgnoreCase("false"))
                    mUnverifyMe.setVisibility(View.GONE);
                else
                    mUnverifyMe.setVisibility(View.VISIBLE);


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
                                    Intent intent = new Intent(ct, CreateSecretActivity.class);
                                    startActivity(intent);
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

    /*private void deviceToken() {


        if (deviceTOken != null && !deviceTOken.equalsIgnoreCase("")) {
            try {
                String version = BuildConfig.VERSION_NAME;
                String versionRelease = Build.VERSION.RELEASE;
                String device_details = versionRelease;
                String username = CryptLib.encrypt(AppSession.getValue(ct, Constants.USER_NAME));
                devicetokendataDTO = new DevicetokendataDTO(username, deviceTOken, version, imei_numer, device_details);
                new UpdateDeviceToken().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }*/

   /* private class UpdateDeviceToken extends AsyncTask<String, String, Bitmap> {

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
                DeviceTokenDTO deviceTokenDTO = new DeviceTokenDTO(Constants.ENCRYPT_USER_TABLE, Constants.ENCRYPT_FIND_METHOD, devicetokendataDTO);
                DeviceTokenObjectDTO loginbjectDTO = new DeviceTokenObjectDTO(deviceTokenDTO);

                status = http.UpdateToken(loginbjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {


        }
    }
*/

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
                CommonRequestTypeDTO findNameDTO = new CommonRequestTypeDTO(findbyNameDTO,"updateDisclaimer");
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



    private class UpdateReturnuing extends android.os.AsyncTask<String, String, Bitmap>
    {

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
                CommonRequestTypeDTO findNameDTO = new CommonRequestTypeDTO(findbyNameDTO,"updateReturning");
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



}

