package com.helio.silentsecret.activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.helio.silentsecret.EncryptionDecryption.CryptLib;
import com.helio.silentsecret.R;
import com.helio.silentsecret.StaticObjectDTO.SchoolDTO;
import com.helio.silentsecret.WebserviceDTO.IfriendaDenyConditionDataDTO;
import com.helio.silentsecret.WebserviceDTO.SecretDTO;
import com.helio.silentsecret.WebserviceDTO.SecretDataDTO;
import com.helio.silentsecret.WebserviceDTO.SecretObjectDTO;
import com.helio.silentsecret.WebserviceDTO.SetDateOfBirthDTO;
import com.helio.silentsecret.WebserviceDTO.SetDateOfBitrhObjectDTO;
import com.helio.silentsecret.WebserviceDTO.SetDatetDataDTO;
import com.helio.silentsecret.callbacks.GPSCallback;
import com.helio.silentsecret.callbacks.KeywordCheckerCallback;
import com.helio.silentsecret.callbacks.RiskCheckerCallback;
import com.helio.silentsecret.checkers.CategoryHelper;
import com.helio.silentsecret.checkers.KeywordsChecker;
import com.helio.silentsecret.checkers.RiskChecker;
import com.helio.silentsecret.connection.ConnectionDetector;
import com.helio.silentsecret.connection.IfriendRequest;
import com.helio.silentsecret.creator.ObjectMaker;
import com.helio.silentsecret.dialogs.ProgressDialog;
import com.helio.silentsecret.fragments.CreateSecretDatePickerFragment;
import com.helio.silentsecret.location.GPSCoordinateReceiver;
import com.helio.silentsecret.location.GeoHelper;
import com.helio.silentsecret.models.DisplaySizes;
import com.helio.silentsecret.models.IfriendListDTO;
import com.helio.silentsecret.models.IfriendRequestDTO;
import com.helio.silentsecret.models.IfriendRequestObjectDTO;
import com.helio.silentsecret.models.SecretPushNotificayionDTO;
import com.helio.silentsecret.utils.AppSession;
import com.helio.silentsecret.utils.CommonFunction;
import com.helio.silentsecret.utils.Constants;
import com.helio.silentsecret.utils.ImageLoaderUtil;
import com.helio.silentsecret.utils.KeyboardUtil;
import com.helio.silentsecret.utils.Preference;
import com.helio.silentsecret.utils.ToastUtil;
import com.helio.silentsecret.utils.Utils;
import com.nineoldandroids.animation.Animator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;


public class CreateSecretActivity extends BaseActivity implements View.OnClickListener, GPSCallback {

    /*======= Flicker ============*/


    List<DisplaySizes> PhotoModels = new ArrayList<>();




    String search_string = "", local_string = "";






    private TextView topMessage;
    private TextView hintMessage;
    private TextView bottomCount;
    private EditText secretText;
    private ViewFlipper viewPager;

    private TextView mYears;
    private TextView mMonth;
    private TextView mDays;
    private TextView done_dob;

    List<ImageView> bannerimage_list = new ArrayList<>();
    private RelativeLayout dob_layout;


    List<String> school_users = null;
    String school_id = "";
    String school_code = "", Age = "";
    String flicker_image = "";
    private int itemPosition;
    private String itemValue;
    StringBuilder s;
    CharSequence mText = "";

    private String currentBackground;
    private int imagePosition = 0;
    private ImageLoaderUtil mImageLoader;

    private static final int SWIPE_MIN_DISTANCE = 10;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 20;



    boolean show_swipe = false;
    private String address;

    private List<String> keyWords;


    private String currentMood = null;
    TextView loading = null;
    private GPSCoordinateReceiver GPS;
    private int MIN_HEIGHT_KEYBOARD = 300;

    private StringBuilder mBuilder;
    private TextView inputMessageWorry;

    private ArrayList<String> imageList;


    private int mFont = 1;
    private boolean fontChange = false;
    SecretDataDTO secretDataDTO = null;
    int secrets_count = 0;

    private List<String> friendArrayList = null;
    private List<String> duplicateArrayList = null;
    public static ListView friendListView;
    private String requestuser = "";
    private static Activity mActivity;
    private boolean TAGFRIEND = false;
    private List<String> sendfriendList = null;

    private String one[] = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen", "twenty"};
    private String word = "";

    int page = 1;


    Context ct = null;

    boolean is_processing = false;

    private GestureDetector gestureDetector = new GestureDetector(new SwipeGestureDetector());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_secret);

        startTracking(getString(R.string.create_android));
        mActivity = this;
        ct = this;
        secrets_count = 0;
        updateImageList();
        findViewById(R.id.create_progress_bg_iv).setOnClickListener(this);

        friendListView = (ListView) findViewById(R.id.friendsList);


        CACHE_PATH = ct.getCacheDir().getAbsolutePath();
        loading = new TextView(this);
        currentBackground = imageList.get(imagePosition);
        mImageLoader = new ImageLoaderUtil(this);
        mBuilder = new StringBuilder();


        try {
            new GetFriendlist().execute();
            initViews();
            if (MainActivity.stataicObjectDTO != null) {
                if (MainActivity.stataicObjectDTO.getKeywords() != null) {
                    keyWords = new ArrayList<>();
                    keyWords.addAll(Arrays.asList(MainActivity.stataicObjectDTO.getKeywords().split(", ")));
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


        try {

            checkGPS();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initViews() {

        try {


            topMessage = (TextView) findViewById(R.id.create_top_message);
            inputMessageWorry = (TextView) findViewById(R.id.create_dont_message);
            bottomCount = (TextView) findViewById(R.id.create_text_count);

            secretText = (EditText) findViewById(R.id.create_text_data);
            viewPager = (ViewFlipper) findViewById(R.id.viewpager);

            secretText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
            hintMessage = (TextView) findViewById(R.id.create_hint);

            secretText.setCursorVisible(false);
            secretText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    secretText.requestFocus();
                    KeyboardUtil.showKeyBoard(secretText, CreateSecretActivity.this);
                    secretText.setCursorVisible(true);
                }
            });
            findViewById(R.id.create_home).setOnClickListener(this);
            findViewById(R.id.create_get_support).setOnClickListener(this);
            //  topMessage.setOnClickListener(this);
            bottomCount.setOnClickListener(this);

            findViewById(R.id.create_swipe_root).setOnClickListener(this);


            findViewById(R.id.create_scared).setOnClickListener(this);
            //findViewById(R.id.create_surprise).setOnClickListener(this);
            findViewById(R.id.create_sad).setOnClickListener(this);
            //findViewById(R.id.create_disgust).setOnClickListener(this);
            findViewById(R.id.create_happy).setOnClickListener(this);
            //findViewById(R.id.create_comtempt).setOnClickListener(this);
            findViewById(R.id.create_angry).setOnClickListener(this);
            findViewById(R.id.create_ashamed).setOnClickListener(this);
            findViewById(R.id.create_anxious).setOnClickListener(this);
            findViewById(R.id.create_fml).setOnClickListener(this);
            findViewById(R.id.create_lonely).setOnClickListener(this);
            findViewById(R.id.create_lol).setOnClickListener(this);
            findViewById(R.id.create_love).setOnClickListener(this);
            findViewById(R.id.create_greatful).setOnClickListener(this);
            findViewById(R.id.create_frustrated).setOnClickListener(this);

            changeFont(1);


            viewPager.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(final View view, final MotionEvent event) {
                    gestureDetector.onTouchEvent(event);
                    return true;
                }
            });
            secretText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(final CharSequence text, int start, int before, int count) {

                    try {
                        if (text.toString() != null && !text.toString().equalsIgnoreCase("")) {


                            search_string = secretText.getText().toString();


                            if (search_string.length() > 2 && !is_processing)
                            {
                                String banned_word = CommonFunction.checkBannedword(search_string);
                                if (banned_word != null && !banned_word.equalsIgnoreCase(""))
                                {
                                    Toast.makeText(ct, "\"" + banned_word + "\" is an inappropriate word remove it.", Toast.LENGTH_SHORT).show();
                                    search_string = search_string.trim();
                                    search_string = "";
                                    secretText.removeCallbacks(make_new_req);
                                    secretText.postDelayed(make_new_req, 1500);
                                } else
                                {
                                    search_string = search_string.trim();
                                    secretText.removeCallbacks(make_new_req);
                                    secretText.postDelayed(make_new_req, 1500);
                                }

                            }
                            else
                            {
                                if(is_processing == true)
                                {
                                    secretText.postDelayed(new Runnable()
                                    {
                                        @Override
                                        public void run() {
                                            is_processing = false;
                                        }
                                    }, 3000);
                                }
                            }


                            if (text.toString().contains("@")) {
                                try {
                                    if (duplicateArrayList != null && duplicateArrayList.size() > 0) {

                                        List<String> temprorylist = new ArrayList<String>();

                                        for (int k = 0; k < duplicateArrayList.size(); k++) {
                                            if (!text.toString().contains(duplicateArrayList.get(k))) {
                                                temprorylist.add(duplicateArrayList.get(k));
                                            }
                                        }

                                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mActivity,
                                                R.layout.friend_listitem, temprorylist);


                                        friendListView.setAdapter(adapter);

                                        friendListView.setVisibility(View.VISIBLE);

                                        friendListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                                itemPosition = position;

                                                itemValue = (String) friendListView.getItemAtPosition(position);

                                                s = new StringBuilder(text);
                                                s.append(itemValue);
                                                mText = text + itemValue;


                                                mText = mText.toString().replace("@", "");
                                                secretText.setText(mText);

                                                secretText.setSelection(secretText.getText().length());
                                                secretText.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        friendListView.setVisibility(View.GONE);

                                                    }
                                                }, 200);

                                            }
                                        });
                                    }
                                } catch (Exception e1) {
                                    e1.printStackTrace();
                                }
                            } else
                                friendListView.setVisibility(View.GONE);


                        } else {
                            friendListView.setVisibility(View.GONE);

                        }
                    } catch (IndexOutOfBoundsException e) {
                    }
                    updateCounter(text.length());

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            hintMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    YoYo.with(Techniques.FadeOut).withListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            hintMessage.setVisibility(View.GONE);
                            inputMessageWorry.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    }).duration(500).playOn(hintMessage);

                    secretText.requestFocus();
                    KeyboardUtil.showKeyBoard(secretText, CreateSecretActivity.this);
                    secretText.setCursorVisible(true);
                }
            });

            final View activityRootView = findViewById(R.id.create_root);
            activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    int heightDiff = activityRootView.getRootView().getHeight() - activityRootView.getHeight();
                    if (heightDiff > MIN_HEIGHT_KEYBOARD) {
                        hideMoodHome();
                    } else {
                        showMoodHome();
                    }
                }
            });

            findViewById(R.id.create_font).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (fontChange) {
                        changeFont(1);
                        fontChange = false;
                    } else {
                        changeFont(2);
                        fontChange = true;
                    }
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Runnable make_new_req = new Runnable() {
        @Override
        public void run() {
            try {


                if(search_string!= null && !search_string.equalsIgnoreCase("")) {
                    is_processing = true;
                    page = 1;
                    search_string = search_string.replace(" ", "%20");
                    new SearchAsync().execute();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    };

    private class GetFriendlist extends AsyncTask<String, String, Bitmap> {

        android.app.ProgressDialog pDialog;
        String data = "0";
        Bitmap bitmap;

        List<IfriendListDTO> userList = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            showProgress();
        }

        protected Bitmap doInBackground(String... args) {
            try {

                if (userList != null)
                    userList = null;

                if (friendArrayList != null)
                    friendArrayList = null;

                if (duplicateArrayList != null)
                    duplicateArrayList = null;

                friendArrayList = new ArrayList<String>();
                duplicateArrayList = new ArrayList<String>();


                IfriendRequest http = new IfriendRequest(mActivity);

                IfriendaDenyConditionDataDTO getAllfriedDTO = new IfriendaDenyConditionDataDTO(MainActivity.enc_username);

                IfriendRequestDTO ifriendRequestDTO = new IfriendRequestDTO(getAllfriedDTO, Constants.ENCRYPT_IFRIEND_CHAT_TABLE, Constants.ENCRYPT_FIND_METHOD);
                IfriendRequestObjectDTO ifriendRequestObjectDTO = new IfriendRequestObjectDTO(ifriendRequestDTO);

                userList = http.GetIfriendlist(ifriendRequestObjectDTO);


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {
            try {
                stopProgress();
                if (userList != null) {
                    if (userList != null && userList.size() > 0) {
                        if (friendArrayList != null && friendArrayList.size() > 0) {
                            friendArrayList.clear();
                        }

                        for (int i = 0; i < userList.size(); i++) {


                            requestuser = userList.get(i).getFriend();

                            int temp = i + 1;
                            friendArrayList.add(requestuser);
                            duplicateArrayList.add("iFriend" + temp);
                        }

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            String DOB = AppSession.getValue(ct, Constants.USER_DATE_OF_BIRTH);
            if (DOB == null || DOB.equalsIgnoreCase("")) {
                loadData();
                AlertDialog.Builder updateAlert = new AlertDialog.Builder(ct);
                updateAlert.setIcon(R.drawable.ic_launcher);
                updateAlert.setTitle("Cypher");
                updateAlert.setMessage("Please select your date of birth for an improve experience");
                updateAlert.setPositiveButton(
                        "Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                showDatePickerDialog();
                                dob_layout.setVisibility(View.VISIBLE);
                            }
                        });
                AlertDialog alertUp = updateAlert.create();
                alertUp.setCanceledOnTouchOutside(false);
                alertUp.show();
            }

        }
    }


    public void updateImageList() {
        try {


            imageList = new ArrayList<>();


            int count = 93;
            try {
                String[] list = getAssets().list("backs");

                if (count > list.length)
                    count = list.length;

                for (int i = 13; i < count; i++) {
                    for (String item : list) {
                        if (i == 13) {
                            if (item.equals(new StringBuilder().append("bg").append(0).append(".jpg").toString())) {
                                imageList.add(item);
                                break;
                            }
                        } else {
                            if (item.equals(new StringBuilder().append("bg").append(i).append(".jpg").toString())) {
                                imageList.add(item);
                                break;
                            }
                        }
                    }
                }

                // Free items
                for (String item : Constants.FREE_BG) {
                    if (!imageList.contains(item))
                        imageList.add(item);
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

/*    private void loadSchools() {
        new SchoolLoader(new SchoolCallback() {
            @Override
            public void onUpdate(List<School> data) {
                schools = new ArrayList<>();
                if (data != null)
                    schools.addAll(data);
            }
        }).execute();
    }*/




   /* private void loadRiskWords() {
        new RiskLoader(new HelperRiskCallback() {
            @Override
            public void onUpdate(List<RiskState> data) {
                riskWords = new ArrayList<>();
                if (data != null)
                    riskWords.addAll(data);
            }
        }).execute();
    }*/

    /*  private void loadKeywords() {
          new KeywordsLoader(new HelperArrayCallback() {
              @Override
              public void onUpdate(List<String> data) {
                  keyWords = new ArrayList<>();
                  if (data != null)
                      keyWords.addAll(data);
              }
          }).execute();
      }*/
    private void checkGPS() {
        GPS = new GPSCoordinateReceiver(this, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            // new CreateDisclaimer(this);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void updateCounter(int size) {

        try {
            if (bottomCount != null) {
                mBuilder.setLength(0);
                mBuilder.append(size);
                mBuilder.append(getString(R.string._200));
                bottomCount.setText(mBuilder);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onReceive(Location location) {
        try {
            if (location != null) {
                Preference.saveUserLat(String.valueOf(location.getLatitude()));
                Preference.saveUserLon(String.valueOf(location.getLongitude()));
                address = GeoHelper.returnAddressString(this);

                if (address == null) {
                    GPS.removeCallback();
                    return;
                }
                AppSession.save(ct, Constants.USER_LOCATION, address);
                // ParseUser.getCurrentUser().put(Constants.USER_LOCATION, address);
                // ParseUser.getCurrentUser().saveInBackground();
            }

            GPS.removeCallback();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }






    private void changeFont(int font) {
        mFont = font;

    }

    @Override
    protected void onPause() {
        super.onPause();
        KeyboardUtil.hideKeyBoard(secretText, this);
        loading.removeCallbacks(loader);

        if (topMessage != null) {
            topMessage.setVisibility(View.GONE);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.create_get_support:
                runSupport();
                break;
            case R.id.create_home:
                finish();
                break;

            case R.id.create_scared:
                runSecretPostWithFlagCheck(getString(R.string.scared));
                break;
            case R.id.create_lol:
                runSecretPostWithFlagCheck(getString(R.string.lol));
                break;
            case R.id.create_sad:
                runSecretPostWithFlagCheck(getString(R.string.sad));
                break;
            case R.id.create_fml:
                runSecretPostWithFlagCheck(getString(R.string.fml));
                break;
            case R.id.create_happy:
                runSecretPostWithFlagCheck(getString(R.string.happy_));
                break;
            case R.id.create_lonely:
                runSecretPostWithFlagCheck(getString(R.string.lonely));
                break;
            case R.id.create_angry:
                runSecretPostWithFlagCheck(getString(R.string.angry));
                break;
            case R.id.create_ashamed:
                runSecretPostWithFlagCheck(getString(R.string.ashamed));
                break;
            case R.id.create_love:
                runSecretPostWithFlagCheck(getString(R.string.love));
                break;
            case R.id.create_greatful:
                runSecretPostWithFlagCheck(getString(R.string.grateful));
                break;
            case R.id.create_frustrated:
                runSecretPostWithFlagCheck(getString(R.string.frustrated));
                break;
            case R.id.create_anxious:
                runSecretPostWithFlagCheck(getString(R.string.anxious));
                break;
            case R.id.stats_birthday:
                showDatePickerDialog();
                break;


        }
    }


    private void runSupport() {
        startActivity(new Intent(this, SupportActivity.class));
    }


    public void runSecretPostWithFlagCheck(final String mood) {
        if (!ConnectionDetector.isNetworkAvailable(this))
            return;


        showProgress();

        startCreation(mood);

    }

    @Override
    protected void onDestroy() {


        super.onDestroy();
    }

    String text = "";

    private void startCreation(final String mood) {
        currentMood = mood;

        try {

            text = secretText.getText().toString().trim();

            if (text.isEmpty()) {
                stopProgress();
                Toast.makeText(ct, "Please enter your post.", Toast.LENGTH_SHORT).show();
                return;
            } else if (flicker_image == null || flicker_image.equalsIgnoreCase("")) {
                stopProgress();
                Toast.makeText(ct, "Please select post image.", Toast.LENGTH_SHORT).show();
                return;
            }


            String banned_word = CommonFunction.checkBannedword(search_string);
            if (banned_word != null && !banned_word.equalsIgnoreCase("")) {
                Toast.makeText(ct, "\"" + banned_word + "\" is an inappropriate word remove it.", Toast.LENGTH_SHORT).show();
                stopProgress();
                return;
            }
            text = checkForSchool(secretText.getText().toString());
            sendfriendList = new ArrayList<String>();
            for (int i = 1; i <= friendArrayList.size(); i++) {
                if (text.contains("iFriend" + i)) {
                    text = text.replace("iFriend" + i, "iFriend" + one[i - 1]);
                    sendfriendList.add(friendArrayList.get(i - 1));

                    TAGFRIEND = true;
                }
            }
            String[] data = text.split(" ");
            String phone = null;
            for (String item : data) {
                if (item.length() > 5) {
                    phone = item;
                    item = item.replaceAll("[^0-9]", ObjectMaker.EMPTY);
                    if (CommentSecretActivity.isNumeric(item) || item.matches("[0-9]+") || item.matches("(\\\\d-)?(\\\\d{3}-)?\\\\d{3}-\\\\d{4}")) {
                        continueCreation(phone, null, text);
                        return;
                    }
                }
            }

            new RiskChecker(new RiskCheckerCallback() {
                @Override
                public void onDone(boolean result, String riskWord, String state) {
                    if (result) {
                        continueCreation(null, null, text);
                    } else {
                        continueCreation(riskWord, state, text);
                    }
                }
            }).execute(text);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void continueCreation(String riskWord, String state, final String text) {
        if (riskWord == null) {
            new KeywordsChecker(new KeywordCheckerCallback() {
                @Override
                public void onDone(boolean result, String riskword) {
                    if (result) {
                        fillDataAndCreate(true, riskword, null, text);
                    } else {
                        fillDataAndCreate(false, riskword, null, text);
                    }
                }
            }).execute(keyWords, text);
        } else {
            fillDataAndCreate(false, riskWord, state, text);
        }
    }

    private void fillDataAndCreate(final boolean result, final String riskWord, final String state, final String text) {
       /* final String userLocation;

        if (address != null) {
            userLocation = address;
        } else {
            userLocation = GeoHelper.returnAddressString(this);
        }*/




        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... voids) {
                if (!result) {
                    if (localResolve(text))
                        return 7;
                }
                return 0;
            }

            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);


                try {
                    if (secretDataDTO != null)
                        secretDataDTO = null;

                    long mseconds = System.currentTimeMillis();

                    String user_id = CryptLib.encrypt(AppSession.getValue(ct, Constants.USER_NAME)) + mseconds;

                    String secrets = AppSession.getValue(ct, Constants.USER_SECRETS);
                    try {
                        if (secrets != null && !secrets.equalsIgnoreCase("")) {
                            secrets_count = Integer.parseInt(secrets);
                            secrets_count = secrets_count + 1;
                        } else {
                            secrets_count = 0;
                            AppSession.save(ct, Constants.USER_SECRETS, "" + secrets_count);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    String addrs = "";
                    if (address != null && !address.equalsIgnoreCase(""))
                        addrs = CryptLib.encrypt(address);

                    String search_text = "";

                    try {
                        String srtext = text;
                        srtext = srtext.toLowerCase();
                        String[] secr_text = srtext.split(" ");

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

                    secretDataDTO = new SecretDataDTO(user_id, CryptLib.encrypt(AppSession.getValue(ct, Constants.USER_NAME)), AppSession.getValue(ct, Constants.USER_GENDER), AppSession.getValue(ct, Constants.USER_AGE), addrs, CategoryHelper.returnCategory(currentMood), sendfriendList, "" + integer,
                            currentMood, mFont, currentBackground, CryptLib.encrypt(text), state, secrets_count, riskWord, school_users,
                            school_id, school_code, search_text, flicker_image, "");
                    new CreatSecret().execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }.execute();


    }


    private class CreatSecret extends AsyncTask<String, String, Bitmap> {

        android.app.ProgressDialog pDialog;
        String status = "";
        Bitmap bitmap;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgress();
        }

        protected Bitmap doInBackground(String... args) {
            try {


                IfriendRequest http = new IfriendRequest(ct);

                SecretDTO secretDTO = new SecretDTO(secretDataDTO, Constants.ENCRYPT_SECRET_TABLE, Constants.ENCRYPT_ADD_METHOD);
                SecretObjectDTO loginbjectDTO = new SecretObjectDTO(secretDTO);
                status = http.AddSecret(loginbjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {

            stopProgress();
            try {
                if (status != null && status.equalsIgnoreCase("success")) {
                    load(0, false);
                    finishCreating();
                } else {
                    Toast.makeText(ct, status, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }


    private void finishCreating() {
        try {
            stopProgress();
            MainActivity.is_from_secret_post = true;
            try {
                if (MySecretsActivity.my_secret_activity != null) {
                    MySecretsActivity.my_secret_activity.finish();
                    MySecretsActivity.my_secret_activity = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String checkForSchool(String text) {
        if (MainActivity.stataicObjectDTO != null) {
            if (MainActivity.stataicObjectDTO.getSchoolDTOs() == null)
                return text;
        } else
            return text;


        try {

            if (text.contains("$$=")) {
                for (SchoolDTO item : MainActivity.stataicObjectDTO.getSchoolDTOs()) {

                    if (item.getCode() != null && !item.getCode().equalsIgnoreCase("")) {
                        Pattern pattern = Pattern.compile(Pattern.quote(item.getCode()), Pattern.CASE_INSENSITIVE);
                        Matcher match = pattern.matcher(text);
                        if (match.find()) {
                            if (item.getUsers() != null && item.getUsers().contains(MainActivity.enc_username)) {
                                String find = match.group();
                                text = text.replace(find, "");
                            } else {
                                if (item.getUsers() != null)
                                    school_users = item.getUsers();
                                else
                                    school_users = new ArrayList<>();

                                school_users.add(MainActivity.enc_username);
                                school_id = item.getSchool_id();
                                school_code = item.getCode();

                                String find = match.group();
                                text = text.replace(find, "");
                            }
                            return text;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return text;
    }

/*
    private void addSchool(final SchoolDTO item) {

        try {
            ParseUser user = ParseUser.getCurrentUser();
            List<ParseObject> schools = new ArrayList<>();

            String id = Preference.getUserName();

            for (String userItem : item.getUsers()) {
                if (userItem.equals(id)) {
                    return;
                }
            }

            schools.add(item.getSchool_id());
            if (user.get(Constants.USER_SCHOOLS) == null) {
                user.put(Constants.USER_SCHOOLS, schools);
            } else {
                List<ParseObject> userSchools = (ArrayList<ParseObject>) user.get(Constants.USER_SCHOOLS);
                userSchools.add(item.getObject());
                user.put(Constants.USER_SCHOOLS, userSchools);
            }

            user.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    saveUserSchool(item);
                    try {
                        ParseUser user = ParseUser.getCurrentUser();
                        user.put(Constants.USER_INVITES, user.getInt(Constants.USER_INVITES) + (Constants.INVITE_THRESHOLD * 3));
                        user.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                //PushBroadcastReceiver.generateActionPush(CreateSecretActivity.this, getString(R.string.school_code_unlock_message), null, null, null);
                            }
                        });
                    } catch (NullPointerException nullPointer) {

                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveUserSchool(School item) {
        try {
            ParseObject object = item.getObject();
            List<String> users = new ArrayList<>();
            users.add(Preference.getUserName());
            object.addAll(Constants.SCHOOL_USERS, users);
            object.saveInBackground();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/

    public void showProgress() {
        ProgressDialog.showDialog(findViewById(R.id.create_progress_bg_iv), findViewById(R.id.create_progress_pb));
    }

    public void stopProgress() {
        ProgressDialog.hideDialog(findViewById(R.id.create_progress_bg_iv), findViewById(R.id.create_progress_pb));
    }

    private void showMoodHome() {
        findViewById(R.id.mood_layout).setVisibility(View.VISIBLE);
        findViewById(R.id.bottom_create_layout).setVisibility(View.VISIBLE);
    }

    private void hideMoodHome() {
        findViewById(R.id.mood_layout).setVisibility(View.GONE);
        findViewById(R.id.bottom_create_layout).setVisibility(View.GONE);
    }

    Runnable loader = new Runnable() {
        @Override
        public void run() {
            try {
                if (topMessage != null && inputMessageWorry != null) {
                    YoYo.with(Techniques.FadeOutUp).playOn(topMessage);


                    YoYo.with(Techniques.FadeOut).withListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            topMessage.setVisibility(View.GONE);

                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    }).duration(500).playOn(topMessage);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };

    SecretPushNotificayionDTO secretPushNotificayionDTO = null;

    private void load(final int state, boolean showProgress) {
       /* new CreateMineLoader(this, new UpdateCallback() {
            @Override
            public void onUpdate(List<Secret> list) {


            }
        }).mysecretexecute(state);*/

    }


    public Bitmap getCroppedBitmap(Bitmap bitmap) {
        Bitmap output = null;
        try {


            output = Bitmap.createBitmap(bitmap.getWidth(),
                    bitmap.getWidth(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);

            final int color = 0xff424242;
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getWidth());

            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
            canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getWidth() / 2,
                    bitmap.getWidth() / 2, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);
            //Bitmap _bmp = Bitmap.createScaledBitmap(output, 60, 60, false);
            //return _bmp;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return output;
    }


    public void showDatePickerDialog() {
        CreateSecretDatePickerFragment newFragment = new CreateSecretDatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "DATE_PICKER");
    }


    String dateofbirth = "";

    public void saveAndLoadDate(final Date date) {
        try {

            Calendar thatDay = Calendar.getInstance();
            thatDay.setTime(date);
            Calendar today = Calendar.getInstance();

            long difference = Utils.DiffBetTwoDate(today, thatDay);

            long days = difference / (24 * 60 * 60 * 1000);

            int years = (int) days / 365;
            if (years > 10) {
                Age = "" + years;
                dateofbirth = (String) android.text.format.DateFormat.format("yyyy/MM/dd", date);
                populateDate(date);
            } else
                new ToastUtil(ct, "You are too young to use this app.");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void populateDate(Date date) {
        if (date == null) return;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        mYears.setText(String.valueOf(calendar.get(Calendar.YEAR)));
        mMonth.setText(String.valueOf(calendar.get(Calendar.MONTH) + 1));
        mDays.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
    }

    private class SetDOB extends AsyncTask<String, String, Bitmap> {

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
                SetDatetDataDTO findbyNameDTO = new SetDatetDataDTO(MainActivity.enc_username, dateofbirth, Age);
                SetDateOfBirthDTO findNameDTO = new SetDateOfBirthDTO(findbyNameDTO);
                http.SetDAte(new SetDateOfBitrhObjectDTO(findNameDTO));

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            AppSession.save(ct, Constants.USER_DATE_OF_BIRTH, dateofbirth);
            dob_layout.setVisibility(View.GONE);
        }
    }


    private class SearchAsync extends AsyncTask<String, String, Bitmap> {


        List<DisplaySizes> Models = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgress();

        }


        protected Bitmap doInBackground(String... args) {
            try {


                Models = search();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);


            try {
                local_string = search_string;


                if (Models != null && Models.size() > 0)
                {
                    stopProgress();
                    Timer timerObj1 = new Timer();
                    TimerTask timerTaskObj1 = new TimerTask() {
                        public void run() {
                            try {
                                is_processing = false;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    };
                    timerObj1.schedule(timerTaskObj1, 1000);


                    viewPager.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            is_processing = false;
                        }
                    }, 1000);

                    if (page == 1)
                    {
                        image_counter = 0;
                        viewPager.postDelayed(new Runnable()
                        {
                            @Override
                            public void run() {


                               try
                               {
                                   PhotoModels.clear();
                                   PhotoModels.addAll(Models);
                                   if (show_swipe == false)
                                   {
                                       showViewPager();
                                   }

                                   flicker_image = PhotoModels.get(image_counter).getImageUrl();

                                   if (bannerimage_list.size() > 0)
                                   {
                                       viewPager.setDisplayedChild(0);
                                   }

                                   new LoadImages().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                               }
                               catch (Exception e)
                               {
                                   e.printStackTrace();
                               }

                            }
                        }, 1000);


                    } else
                    {
                        PhotoModels.addAll(Models);
                        new LoadImages().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    }

                } else
                {
                    if (page == 1) {
                        search_string = CommonFunction.GetdefualtWord();
                        new SearchAsync().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


/*
    public void searchAsync(final int page) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    PhotoModels = search(page);
                    showViewPager();
                } catch (IOException | JSONException e) {

                }
            }
        }).start();
    }

*/

   // private static final String API_KEY = "n88pxentayj653upyb8476z8";
    private static final String API_KEY = "js54xr27j2aa57r8snrwv7vs";
    private static final String COLUMN_PHOTO = "images";
    private static final long CACHE_SIZE_IN_MB = 10 * 1024 * 1024;
    private static String CACHE_PATH = "";
    private List<DisplaySizes> search() throws IOException, JSONException
    {
        List<DisplaySizes> displaySizes = new ArrayList<>();
        try
        {
            String url = "https://api.gettyimages.com/v3/search/images?fields=comp&phrase="+search_string + "&page_size=30&page="+page;
            // String url = "https://api.gettyimages.com/v3/search/images?fields=thumb&phrase="+search_string;
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("api-key", API_KEY)
                    .build();
            Response response = getClient().newCall(request).execute();
            String json = response.body().string();
            JSONArray jsonArray = new JSONObject(json).getJSONArray(COLUMN_PHOTO);

            for (int i = 0; i < jsonArray.length(); i++)
            {
                try
                {
                    JSONObject UserInfoobj = new JSONObject(jsonArray.getString(i));
                    DisplaySizes displaySizes1 = new DisplaySizes();

                    JSONArray jsonArray1 = UserInfoobj.getJSONArray("display_sizes");


                    JSONObject UserInfoobj1 = new JSONObject(jsonArray1.getString(0));
                    if (UserInfoobj1.has("uri"))
                        displaySizes1.setUri(UserInfoobj1.getString("uri"));

                    if(displaySizes1!= null && !displaySizes1.equals(""))
                        displaySizes.add(displaySizes1);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }



        return displaySizes;

    }
    private OkHttpClient getClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .cache(new Cache(new File(CACHE_PATH), CACHE_SIZE_IN_MB))
                .build();
    }



    private void loadData() {
        try {
            mYears = (TextView) findViewById(R.id.stats_years);
            mMonth = (TextView) findViewById(R.id.stats_month);
            mDays = (TextView) findViewById(R.id.stats_days);
            done_dob = (TextView) findViewById(R.id.done_dob);
            dob_layout = (RelativeLayout) findViewById(R.id.dob_layout);

            dob_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            findViewById(R.id.stats_birthday).setOnClickListener(this);


            done_dob.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dateofbirth != null && !dateofbirth.equalsIgnoreCase("")) {
                        new SetDOB().execute();
                    } else
                        new ToastUtil(ct, "Please select your date of birth.");
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    int image_counter = 0;

   /* public void Drawbanner()
    {

        image_counter = 0;
        for (int i = 0; i < PhotoModels.size(); i++) {

            RelativeLayout.LayoutParams rlp1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            final ImageView bannerimage = new ImageView(ct);
            bannerimage.setLayoutParams(rlp1);
            bannerimage.setScaleType(ImageView.ScaleType.FIT_XY);


*//*            Glide.with(this)
                    .load(PhotoModels.get(i).getImageUrl(ImageSize.MEDIUM))
                    .placeholder(R.drawable.loading_gif)
                    .into(bannerimage);*//*

           *//* Glide.with(this)
                    .load(PhotoModels.get(i).getImageUrl(ImageSize.MEDIUM))
                    .asBitmap()
                    .placeholder(R.drawable.loading_gif)
                    .into(new SimpleTarget<Bitmap>(100, 100)
                    {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            Drawable drawable = new BitmapDrawable(resource);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                bannerimage.setBackground(drawable);
                            }
                        }
                    });*//*


            bannerimage_list.add(bannerimage);

            viewPager.addView(bannerimage);

          *//*  try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
*//*

        }

        new LoadImages().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

    }

    private void update_banner() {
        image_counter = 0;
        for (int i = 0; i < PhotoModels.size(); i++) {
            try {
                if (bannerimage_list.size() > i) {
                    *//*Glide.with(this)
                            .load(PhotoModels.get(i).getImageUrl(ImageSize.MEDIUM))
                            .placeholder(R.drawable.loading_gif)
                            .into(bannerimage_list.get(i));


                    Thread.sleep(100);*//*
                } else {
                    RelativeLayout.LayoutParams rlp1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                    final ImageView bannerimage = new ImageView(ct);
                    bannerimage.setLayoutParams(rlp1);
                    bannerimage.setScaleType(ImageView.ScaleType.FIT_XY);


                  *//*  Glide.with(this)
                            .load(PhotoModels.get(i).getImageUrl(ImageSize.MEDIUM))
                            .placeholder(R.drawable.loading_gif)
                            .into(bannerimage);
*//*

                    bannerimage_list.add(bannerimage);

                    viewPager.addView(bannerimage);
*//*
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*//*
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        new LoadImages().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        viewPager.setDisplayedChild(0);
    }

    private void update_banner_nextpage() {
        image_counter = 0;
        for (int i = 0; i < PhotoModels.size(); i++) {
            try {
                if (bannerimage_list.size() > i) {
                   *//* Glide.with(this)
                            .load(PhotoModels.get(i).getImageUrl(ImageSize.MEDIUM))
                            .placeholder(R.drawable.loading_gif)
                            .into(bannerimage_list.get(i));

                    Thread.sleep(100);*//*
                } else {
                    RelativeLayout.LayoutParams rlp1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                    final ImageView bannerimage = new ImageView(ct);
                    bannerimage.setLayoutParams(rlp1);
                    bannerimage.setScaleType(ImageView.ScaleType.FIT_XY);

                  *//*  Glide.with(this)
                            .load(PhotoModels.get(i).getImageUrl(ImageSize.MEDIUM))
                            .placeholder(R.drawable.loading_gif)
                            .into(bannerimage);

*//*
                    bannerimage_list.add(bannerimage);

                    viewPager.addView(bannerimage);

                   *//* try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*//*
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        new LoadImages().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
*/

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        // TODO Auto-generated method stub
//        return gestureDetector.onTouchEvent(event);
//    }

    private void showViewPager() {
        try {

            try {
                show_swipe = true;
                Timer timerObj1 = new Timer();
                TimerTask timerTaskObj1 = new TimerTask() {
                    public void run() {
                        try {

                            topMessage.setVisibility(View.GONE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                };
                timerObj1.schedule(timerTaskObj1, 1500);
                topMessage.setVisibility(View.VISIBLE);

                topMessage.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        topMessage.setVisibility(View.GONE);
                    }
                }, 1500);




            } catch (Exception e) {
                e.printStackTrace();
            }



            for (int i = 0; i < PhotoModels.size(); i++)
            {
                try
                {

                    RelativeLayout.LayoutParams rlp1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                    ImageView bannerimage = new ImageView(ct);
                    bannerimage.setLayoutParams(rlp1);
                    bannerimage.setScaleType(ImageView.ScaleType.FIT_XY);
                    bannerimage_list.add(bannerimage);
                    viewPager.addView(bannerimage);
                    Thread.sleep(100);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            viewPager.setDisplayedChild(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    class SwipeGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return super.onSingleTapConfirmed(e);
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return super.onDown(e);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                // right to left swipe
                if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {

                    int index = viewPager.getDisplayedChild();
                    if (index >= PhotoModels.size() - 1)
                    {

                        page++;
                        new SearchAsync().execute();
                        /*
                        int pagesize = PhotoModels.size() / 14;
                        if (pagesize == page) {
                            page++;
                            new SearchAsync().execute();
                        }*/
                    } else {
                        flicker_image = PhotoModels.get(index + 1).getImageUrl();
                                //PhotoModels.get(index + 1).getImageUrl(ImageSize.MEDIUM);
                        viewPager.setInAnimation(inFromRightAnimation());
                        viewPager.setOutAnimation(outToLeftAnimation());
                        viewPager.showNext();

                    }
                    return true;
                } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {

                    int index = viewPager.getDisplayedChild();
                    if (index != 0)
                    {
                       // flicker_image = PhotoModels.get(index - 1).getImageUrl(ImageSize.MEDIUM);
                        flicker_image = PhotoModels.get(index - 1).getImageUrl();
                        viewPager.setInAnimation(inFromLeftAnimation());
                        viewPager.setOutAnimation(outToRightAnimation());

                        viewPager.showPrevious();
                    }
                    return true;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return false;
        }
    }



    private Animation inFromRightAnimation() {

        Animation inFromRight = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, +1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        inFromRight.setDuration(600);

        inFromRight.setInterpolator(new AccelerateInterpolator());
        return inFromRight;
    }

    private Animation outToLeftAnimation() {
        Animation outtoLeft = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, -1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        outtoLeft.setDuration(600);
        outtoLeft.setInterpolator(new AccelerateInterpolator());
        return outtoLeft;
    }


    private Animation inFromLeftAnimation() {
        Animation inFromLeft = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, -1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        inFromLeft.setDuration(600);
        inFromLeft.setInterpolator(new AccelerateInterpolator());
        return inFromLeft;
    }

    private Animation outToRightAnimation() {
        Animation outtoRight = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, +1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        outtoRight.setDuration(600);
        outtoRight.setInterpolator(new AccelerateInterpolator());
        return outtoRight;
    }


    private class LoadImages extends AsyncTask<String, String, Bitmap> {

        android.app.ProgressDialog pDialog;
        String data = "0";

        Bitmap bmp = null;
        InputStream in = null;
        int responseCode = -1;
        List<IfriendListDTO> userList = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


            if (bannerimage_list.size() > image_counter)
            {
                bannerimage_list.get(image_counter).setImageResource(R.drawable.loading_gif);
            } else {
                RelativeLayout.LayoutParams rlp1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                final ImageView bannerimage = new ImageView(ct);
                bannerimage.setLayoutParams(rlp1);
                bannerimage.setScaleType(ImageView.ScaleType.FIT_XY);


                bannerimage_list.add(bannerimage);

                bannerimage_list.get(image_counter).setImageResource(R.drawable.loading_gif);
                viewPager.addView(bannerimage);

            }

        }

        protected Bitmap doInBackground(String... args) {
            try {
            //    bmp =   loadBitmap(PhotoModels.get(image_counter).getDisplay_sizes().get(0).getImageUrl());


                Thread.sleep(20);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image)
        {

            try
            {
                Glide.with(ct)
                        .load(PhotoModels.get(image_counter).getImageUrl())
                        .placeholder(R.drawable.loading_gif)
                        .into(bannerimage_list.get(image_counter));

                if (image_counter < PhotoModels.size()) {
                    image_counter++;
                    new LoadImages().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
