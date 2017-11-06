package com.helio.silentsecret.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerTabStrip;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.helio.silentsecret.EncryptionDecryption.CryptLib;
import com.helio.silentsecret.R;
import com.helio.silentsecret.Services.BoundService;
import com.helio.silentsecret.WebserviceDTO.ClearMySecretDTO;
import com.helio.silentsecret.WebserviceDTO.ClearMySecretDataDTO;
import com.helio.silentsecret.WebserviceDTO.ClearMysecretObjectDTO;
import com.helio.silentsecret.WebserviceDTO.FindbyNameDTO;
import com.helio.silentsecret.WebserviceDTO.GetMyrecievedDTO;
import com.helio.silentsecret.WebserviceDTO.GetmyRecievedObjectDTO;
import com.helio.silentsecret.adapters.ViewPagerMineAdapter;
import com.helio.silentsecret.callbacks.DeleteCallback;
import com.helio.silentsecret.callbacks.UpdateCallback;
import com.helio.silentsecret.connection.ConnectionDetector;
import com.helio.silentsecret.connection.IfriendRequest;
import com.helio.silentsecret.dialogs.AccessDialog;
import com.helio.silentsecret.dialogs.ProgressDialog;
import com.helio.silentsecret.dialogs.RateDialog;
import com.helio.silentsecret.dialogs.WebViewDialog;
import com.helio.silentsecret.fragments.MineSecretsFragment;
import com.helio.silentsecret.fragments.MySecretSearchFragment;
import com.helio.silentsecret.loaders.MineLoader;
import com.helio.silentsecret.models.Secret;
import com.helio.silentsecret.utils.AppSession;
import com.helio.silentsecret.utils.Constants;
import com.helio.silentsecret.utils.KeyboardUtil;
import com.helio.silentsecret.utils.ToastUtil;
import com.helio.silentsecret.views.OwnPager;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MySecretsActivity extends BaseActivity implements View.OnClickListener {

    TextView chat_count = null;
    private View mNormalLayout;
    private View mDeleteLayout;
    public static Activity my_secret_activity = null;
    TextView runAnime = null ;
    ImageView search = null , settings = null ;
    ImageView petimage = null;

    private int width = 0, height;
    private OwnPager minePager;
    private PagerTabStrip tabStrip;
    private ViewPagerMineAdapter adapter;

    private UpdateCallback mineSecretsCallback;
    private UpdateCallback hugsCallback;
    private UpdateCallback heartsCallback;
    private UpdateCallback me2sCallback;

    Context ct = null;
    private boolean isDeleteMode = false;
    public static boolean mIsSelectedAll = false;

    private List<String> deleteArray = new ArrayList<>();

    private DeleteCallback updateCallbacks;
    LinearLayout hug_heart_layout = null;
    public static RelativeLayout webview_layout = null;
    LinearLayout feed_view_back = null , search_page = null;
    static WebView mWebView = null;
    RelativeLayout search_layout = null ;
    ImageView my_heart = null, my_hug = null, my_me2 = null;
    TextView search_secret = null , search_back = null;
    public void attachCallback(DeleteCallback item) {
        updateCallbacks = item;
    }
EditText edt_search = null;
ImageView my_secret = null;
    String search_string = "";
TextView fragment_header = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_secrets);

        runAnime = new TextView(this);
        ct = this;
        mWebView = (WebView) findViewById(R.id.feed_view_vb);
        webview_layout = (RelativeLayout) findViewById(R.id.webview_layout);
        search = (ImageView) findViewById(R.id.search);
        my_secret = (ImageView) findViewById(R.id.my_secret);
        settings = (ImageView) findViewById(R.id.settings);
        search_secret = (TextView) findViewById(R.id.search_secret);
        fragment_header = (TextView) findViewById(R.id.fragment_header);
        search_back = (TextView) findViewById(R.id.search_back);
        chat_count = (TextView) findViewById(R.id.chat_count);
        petimage = (ImageView) findViewById(R.id.petimage);
        edt_search = (EditText) findViewById(R.id.edt_search);

        hug_heart_layout = (LinearLayout) findViewById(R.id.hug_heart_layout);
        search_page = (LinearLayout) findViewById(R.id.search_page);
        search_layout = (RelativeLayout) findViewById(R.id.search_layout);
        my_heart = (ImageView) findViewById(R.id.my_heart);
        my_hug = (ImageView) findViewById(R.id.my_hug);
        my_me2 = (ImageView) findViewById(R.id.my_me2);
        my_heart.setOnClickListener(this);
        my_hug.setOnClickListener(this);
        my_me2.setOnClickListener(this);

        chatcount = 0;



        edt_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH)
                {
                    ValidateSearch();

                    return true;
                }
                return false;
            }
        });

        search_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_page.setVisibility(View.GONE);
            }
        });

        ((TextView) findViewById(R.id.stats_username)).setText(AppSession.getValue(ct, Constants.USER_NAME));

        try {
            String result = AppSession.getValue(ct, Constants.USER_VERIFIED);
            if (result != null && result.equalsIgnoreCase("true")) {
                findViewById(R.id.stats_verify).setVisibility(View.VISIBLE);
                findViewById(R.id.stats_circle).setVisibility(View.VISIBLE);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_layout.setVisibility(View.VISIBLE);
            }
        });


     TextView   pGif = (TextView) findViewById(R.id.chat_friend);
        pGif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ConnectionDetector.isNetworkAvailable(ct)) {
                    String verify = AppSession.getValue(ct, Constants.USER_VERIFIED);
                    if (verify != null && verify.equalsIgnoreCase("false")) {
                        new ToastUtil(ct, ct.getString(R.string.sorry_not_verified));


                    } else {
                        startActivity(new Intent(ct,NotificationActivity.class));
                    }
                } else {
                    new ToastUtil(ct, Constants.NETWORK_FAILER);
                }


            }
        });




        my_secret.setOnClickListener(this);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ct,SettingsActivity.class));
            }
        });

        search_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        startTracking(getString(R.string.analytics_MYSecrets));

        edt_search.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s.length() > 0) {
                    search_secret.setText("Search");
                } else
                    search_secret.setText("Cancel");

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

        try {
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            width = displayMetrics.widthPixels;
            height = displayMetrics.heightPixels;

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

        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(Constants.ACCESS_CLEAR_SECRET))
            isDeleteMode = true;
        else
            isDeleteMode = false;


        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(Constants.SELECT_ALL))
            mIsSelectedAll = true;
        else
            mIsSelectedAll = false;

        initViews();

        new GetMyRecieved().execute();

        search_secret.setOnClickListener(this);
       // draw_secret_cat();

        if(MineSecretsFragment.envelop_secre_id!= null && !MineSecretsFragment.envelop_secre_id.equalsIgnoreCase(""))
            MineSecret();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        chat_count.removeCallbacks(refreshchatcount);
        Intent intent = new Intent(this, BoundService.class);
        stopService(intent);
    }

    public static void initWebView(String url) {

        mWebView.getSettings().setJavaScriptEnabled(true);


        mWebView.loadUrl(url);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if (url.contains("http://www.doowapp.me/play.php?id")) {
                    mWebView.loadUrl(url);
                    return false;
                } else
                    return true;
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
    }

    @Override
    protected void onResume() {
        super.onResume();

       String pet_name = AppSession.getValue(ct, Constants.USER_PET_NAME);
        if (pet_name != null && !pet_name.equalsIgnoreCase("") && !pet_name.equalsIgnoreCase("null"))
        {
            int[] petimagearray = {R.drawable.monkey_face, R.drawable.panda_face,
                    R.drawable.horse_face, R.drawable.rabbit_face,
                    R.drawable.donkey_face, R.drawable.sheep_face,
                    R.drawable.deer_face, R.drawable.tiger_face,
                    R.drawable.parrot_face, R.drawable.meerkat_face,
                    R.drawable.mermain_face, R.drawable.cat_face,
                    R.drawable.dog_face, R.drawable.unicorn_face,
                    R.drawable.dragon_face, R.drawable.dynasore_face,
                    R.drawable.starfish_face};


            for (int i = 0; i < MainActivity.petnamearray.length; i++)
            {
                if (pet_name.equalsIgnoreCase(MainActivity.petnamearray[i]))
                {
                    petimage.setImageResource(petimagearray[i]);
                    break;
                }
            }
        }
        if (getIntent().getExtras() == null)
        {
            load(Constants.STATE_MINE_SECRETS, true);
        }

        chat_count.postDelayed(refreshchatcount, 1000);
        Intent intent = new Intent(this, BoundService.class);
        startService(intent);
    }

    public void setUpMine(UpdateCallback data) {
        this.mineSecretsCallback = data;
    }

    public void setUpHugs(UpdateCallback data) {
        this.hugsCallback = data;
    }

    public void setUpHearts(UpdateCallback data) {
        this.heartsCallback = data;
    }

    public void setUpMe2s(UpdateCallback data) {
        this.me2sCallback = data;
    }


    private void initViews() {
        findViewById(R.id.mine_progress_bg_iv).setOnClickListener(this);

        mNormalLayout = findViewById(R.id.mine_normal_layout);
        mDeleteLayout = findViewById(R.id.mine_delete_layout);

        if (isDeleteMode) {
            mNormalLayout.setVisibility(View.GONE);
            mDeleteLayout.setVisibility(View.VISIBLE);

            findViewById(R.id.mine_delete_home).setOnClickListener(this);
            findViewById(R.id.mine_delete_select_all).setOnClickListener(this);
            findViewById(R.id.mine_delete_done).setOnClickListener(this);
        } else {
            mNormalLayout.setVisibility(View.VISIBLE);
            mDeleteLayout.setVisibility(View.GONE);

            findViewById(R.id.mine_home).setOnClickListener(this);
            findViewById(R.id.mine_plus_toogle).setOnClickListener(this);
            findViewById(R.id.mine_get_support).setOnClickListener(this);
        }

        adapter = new ViewPagerMineAdapter(getSupportFragmentManager(), isDeleteMode);
        minePager = (OwnPager) findViewById(R.id.mine_pager);

        tabStrip = (PagerTabStrip) findViewById(R.id.mine_pager_title_strip);
        tabStrip.setTabIndicatorColor(getResources().getColor(R.color.upper_line));

        minePager.setOffscreenPageLimit(4);
        minePager.setAdapter(adapter);
        minePager.setCurrentItem(0);

        if (getIntent().getExtras() == null) {
            loadAccessData();
        } else if (getIntent().getExtras().containsKey(Constants.PUSH_ACTION)) {
            replaceDialog();
        } else if (getIntent().getExtras().containsKey(Constants.ACCESS_CLEAR_SECRET) || getIntent().getExtras().containsKey(Constants.FROM_NOTIFICATIONS_STATE)) {
            loadAccessData();
        }
    }


    @Override
    public void onBackPressed() {
        Fragment frag = getSupportFragmentManager().findFragmentById(R.id.support_frame);
        if (frag instanceof WebViewDialog || frag instanceof RateDialog) {
//            stay still
        } else {
            runBack();
        }
    }

    public void runBack() {

        if (getIntent().getExtras() != null && !getIntent().getExtras().containsKey(Constants.FROM_NOTIFICATIONS_STATE)) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {


            case R.id.my_heart:
                HeartMineSecret();
                break;
            case R.id.my_hug:
                HugMineSecret();
                break;
            case R.id.my_me2:
                Me2MineSecret();
                break;

            case R.id.my_secret:
                MineSecret();
                break;
            case R.id.search_secret:
                ValidateSearch();
                break;
            case R.id.mine_home:
                onBackPressed();
                break;
            case R.id.mine_plus_toogle:
                runSecretCreate();
                break;
            case R.id.mine_get_support:
                runSupport();
                break;
            case R.id.mine_delete_home:
                onBackPressed();
                break;
            case R.id.mine_delete_select_all:
                if (mIsSelectedAll)
                {
                    mIsSelectedAll = false;

                   // updateCallbacks.onUnSelectAll();
                    Intent intent = new Intent(ct, MySecretsActivity.class);
                    intent.putExtra(Constants.ACCESS_CLEAR_SECRET, true);
                    startActivity(intent);
                    finish();

                } else
                {
                    mIsSelectedAll = true;
                  //  updateCallbacks.onSelectAll();
                    Intent intent = new Intent(ct, MySecretsActivity.class);
                    intent.putExtra(Constants.ACCESS_CLEAR_SECRET, true);
                    intent.putExtra(Constants.SELECT_ALL, true);
                    startActivity(intent);
                    finish();

                }
                break;
            case R.id.mine_delete_done:
                deleteOwnData();
                break;
        }
    }



    private void MineSecret()
    {
        fragment_header.setText("My Secrets");
        search_page.setVisibility(View.VISIBLE);
        findViewById(R.id.mine_frame).setVisibility(View.VISIBLE);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mine_frame,  getItem(Constants.STATE_MINE_SECRETS));
        transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss();

    }

    private void Me2MineSecret()
    {
        fragment_header.setText("Me2 Secrets");
        search_page.setVisibility(View.VISIBLE);
        findViewById(R.id.mine_frame).setVisibility(View.VISIBLE);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mine_frame,  getItem(Constants.STATE_ME2S_SECRETS));
        transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss();

    }

    private void HeartMineSecret()
    {
        fragment_header.setText("Heart Secrets");
        search_page.setVisibility(View.VISIBLE);
        findViewById(R.id.mine_frame).setVisibility(View.VISIBLE);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mine_frame,  getItem(Constants.STATE_HEART_SECRETS));
        transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss();

    }

    private void HugMineSecret()
    {
        fragment_header.setText("Hug Secrets");
        search_page.setVisibility(View.VISIBLE);
        findViewById(R.id.mine_frame).setVisibility(View.VISIBLE);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mine_frame,  getItem(Constants.STATE_HUGS_SECRETS));
        transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss();

    }


    private void ValidateSearch()
    {
        search_string = "";
        search_string = edt_search.getText().toString().trim();
        if(search_string!= null && !search_string.equalsIgnoreCase(""))
        {
            edt_search.setText("");
            search_layout.setVisibility(View.GONE);
            search_page.setVisibility(View.VISIBLE);

            fragment_header.setText("Search Secret");
            replaceSearch();
            KeyboardUtil.hideKeyBoard(edt_search, ct);

        }else
            search_layout.setVisibility(View.GONE);

    }
public  String searchText = "";

    private void replaceSearch()
    {

        try {

            String search_text = "";

            try {
                search_string = search_string.toLowerCase();
                String[] secr_text = search_string.split(" ");

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

            findViewById(R.id.mine_frame).setVisibility(View.VISIBLE);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.mine_frame, new MySecretSearchFragment());
            transaction.addToBackStack(null);
            transaction.commitAllowingStateLoss();
        } catch (Exception e) {
        }
    }

    private void runSupport() {
       // startActivity(new Intent(this, NotificationActivity.class));

        startActivity(new Intent(ct,ChatFriends.class));
    }

    private void runSecretCreate()
    {
        if (!MainActivity.is_flag) {
             my_secret_activity = this;
            startActivity(new Intent(this, CreateSecretActivity.class));
        }
        else
            Toast.makeText(ct, "You are not permitted to share a secret.", Toast.LENGTH_SHORT).show();
    }

    public void showProgress() {
        ProgressDialog.showDialog(findViewById(R.id.mine_progress_bg_iv), findViewById(R.id.mine_progress_pb));
    }

    public void stopProgress() {
        ProgressDialog.hideDialog(findViewById(R.id.mine_progress_bg_iv), findViewById(R.id.mine_progress_pb));
    }

    private void load(final int state, boolean showProgress) {
        new MineLoader(this, new UpdateCallback() {
            @Override
            public void onUpdate(List<Secret> list) {

                switch (state) {
                    case Constants.STATE_MINE_SECRETS:
                        if (mineSecretsCallback != null)
                            mineSecretsCallback.onUpdate(list);
                        break;
                    case Constants.STATE_HEART_SECRETS:
                        if (heartsCallback != null)
                            heartsCallback.onUpdate(list);
                        break;
                    case Constants.STATE_HUGS_SECRETS:
                        if (hugsCallback != null)
                            hugsCallback.onUpdate(list);
                        break;
                    case Constants.STATE_ME2S_SECRETS:
                        if (me2sCallback != null)
                            me2sCallback.onUpdate(list);
                        break;

                }
            }
        }).execute(state, showProgress);
    }

    public void openEnvelope(Secret item) {
        replaceWeb(Constants.BACKEND_URL);
    }

    private void replaceWeb(String url) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        try {
            transaction.replace(R.id.mine_frame, WebViewDialog.instance(url, Constants.BACKEND_WEB_VIEW));
        } catch (IllegalStateException e) {
        }
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void replaceDialog() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        try {
            transaction.replace(R.id.mine_frame, AccessDialog.instance(Constants.ACCESS_MY_SECRETS_BACKEND));
        } catch (IllegalStateException e) {
        }
        transaction.addToBackStack(null);
        transaction.commit();
    }


    /*public void replaceDialogIfriend(String frag)
    {
        findViewById(R.id.ifriend_access).setVisibility(View.VISIBLE);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        try {
            transaction.replace(R.id.ifriend_access, AccessDialog.instance(frag));
            transaction.addToBackStack(null);
            transaction.commitAllowingStateLoss();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }
    public void runIFriend() {
        startActivity(new Intent(MySecretsActivity.this, ChatFriends.class));
    }*/
    private void replaceRate() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        try {
            transaction.replace(R.id.mine_frame, RateDialog.instance(Constants.BACKEND_WEB_VIEW));
        } catch (IllegalStateException e) {
        }
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void loadAccessData() {
        load(Constants.STATE_MINE_SECRETS, true);
        load(Constants.STATE_HUGS_SECRETS, false);
        load(Constants.STATE_HEART_SECRETS, false);
        load(Constants.STATE_ME2S_SECRETS, false);
    }

    public void webViewClosed() {
        replaceRate();
    }

    public void makeRate(final int rate) {
        try {
            /*ParseQuery query = ParseQuery.getQuery(Constants.SUPPORT_ORGANISATIONS_CLASS);
            query.whereEqualTo(Constants.SUPPORT_ORGANISATIONS_NAME, getString(R.string.app_name));
            query.getFirstInBackground(new GetCallback() {
                @Override
                public void done(ParseObject object, ParseException e) {
                }

                @Override
                public void done(Object o, Throwable throwable) {
                    if (o != null) {
                        ObjectMaker.formRate((ParseObject) o, rate, ParseUser.getCurrentUser()).saveInBackground();
                    } else {
                        ObjectMaker.formRate(null, rate, ParseUser.getCurrentUser()).saveInBackground();
                    }
                }
            });*/
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void deleteOwnData() {
        if (deleteArray.isEmpty()) {
            new ToastUtil(this, "Please select secrets for delete");
            return;
        }

        if (!ConnectionDetector.isNetworkAvailable(this))
            return;


        new DeleteMysecret().execute();
       /* new DeleteDataLoader(this, new DeleteDataCallback() {
            @Override
            public void onDone(boolean result) {

                updateCallbacks.onDelete(deleteArray);
                deleteArray.clear();

                Intent intent = new Intent(ct, MySecretsActivity.class);

                intent.putExtra(Constants.ACCESS_CLEAR_SECRET, true);
                startActivity(intent);
                finish();


            }
        }
        ).execute(deleteArray);*/
    }

    public void addDeleteItem(String item) {
        if (!deleteArray.contains(item))
            deleteArray.add(item);
    }

    public void removeDeleteItem(String item) {
        if (deleteArray.contains(item))
            deleteArray.remove(item);
    }

    


    private class GetMyRecieved extends android.os.AsyncTask<String, String, Bitmap> {

        android.app.ProgressDialog pDialog;
        String data = "0";
        Bitmap bitmap;
        JSONObject jsonObject = null;
        String response = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }


        protected Bitmap doInBackground(String... args) {
            try {


                IfriendRequest http = new IfriendRequest(ct);
                FindbyNameDTO findbyNameDTO = new FindbyNameDTO(MainActivity.enc_username);
                GetMyrecievedDTO findNameDTO = new GetMyrecievedDTO(findbyNameDTO);
                GetmyRecievedObjectDTO findbynameObjectDTO = new GetmyRecievedObjectDTO(findNameDTO);
                jsonObject = http.GetMyRecieved(findbynameObjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {
            try {
                if (jsonObject != null) {
                    String hugs = "", hearts = "", me2s = "";

                    if (jsonObject.has("hugs_received"))
                        hugs = jsonObject.getString("hugs_received");

                    if (jsonObject.has("hearts_received"))
                        hearts = jsonObject.getString("hearts_received");

                    if (jsonObject.has("me2s_received"))
                        me2s = jsonObject.getString("me2s_received");


                    if (hugs.length() > 3) {
                        hugs = hugs.charAt(0) + Constants.POINT + hugs.charAt(1) + Constants.THOUTHAND;
                    }

                    if (hearts.length() > 3) {
                        hearts = hearts.charAt(0) + Constants.POINT + hearts.charAt(1) + Constants.THOUTHAND;
                    }

                    if (me2s.length() > 3) {
                        me2s = me2s.charAt(0) + Constants.POINT + me2s.charAt(1) + Constants.THOUTHAND;
                    }

                    ((TextView) findViewById(R.id.stats_received_hearts)).setText(hearts);
                    ((TextView) findViewById(R.id.stats_received_hugs)).setText(hugs);
                    ((TextView) findViewById(R.id.stats_received_me2s)).setText(me2s);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    private class DeleteMysecret extends android.os.AsyncTask<String, String, Bitmap> {

        String data = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgress();

        }

        protected Bitmap doInBackground(String... args) {
            try {
                IfriendRequest http = new IfriendRequest(ct);
                ClearMySecretDataDTO clearMySecretDataDTO = new ClearMySecretDataDTO(MainActivity.enc_username, deleteArray);
                ClearMySecretDTO clearMySecretDTO = new ClearMySecretDTO( clearMySecretDataDTO);
                ClearMysecretObjectDTO clearMysecretObjectDTO = new ClearMysecretObjectDTO(clearMySecretDTO);

                data = http.ClearmySecret(clearMysecretObjectDTO);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {

            stopProgress();
            try
            {

                updateCallbacks.onDelete(deleteArray);
                deleteArray.clear();

                Intent intent = new Intent(ct, MySecretsActivity.class);
                intent.putExtra(Constants.ACCESS_CLEAR_SECRET, true);
                startActivity(intent);
                finish();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        MineSecretsFragment.envelop_secre_id = "";
        findViewById(R.id.ifriend_access).setVisibility(View.GONE);
    }



    public static int chatcount = 0;


    Runnable refreshchatcount = new Runnable() {
        @Override
        public void run() {
            try {


                chat_count.removeCallbacks(refreshchatcount);
                chat_count.postDelayed(refreshchatcount, 3000);
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


    public Fragment getItem(int num) {
        switch (num) {
            case Constants.STATE_MINE_SECRETS:
                return MineSecretsFragment.instance(Constants.STATE_MINE_SECRETS, false);
            case Constants.STATE_HEART_SECRETS:
                return MineSecretsFragment.instance(Constants.STATE_HEART_SECRETS, false);
            case Constants.STATE_HUGS_SECRETS:
                return MineSecretsFragment.instance(Constants.STATE_HUGS_SECRETS, false);
            case Constants.STATE_ME2S_SECRETS:
                return MineSecretsFragment.instance(Constants.STATE_ME2S_SECRETS, false);
        }
        return null;
    }
}
