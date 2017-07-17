package com.helio.silentsecret.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerTabStrip;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.helio.silentsecret.R;
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
import com.helio.silentsecret.loaders.MineLoader;
import com.helio.silentsecret.models.Secret;
import com.helio.silentsecret.utils.AppSession;
import com.helio.silentsecret.utils.Constants;
import com.helio.silentsecret.utils.ToastUtil;
import com.helio.silentsecret.views.OwnPager;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MySecretsActivity extends BaseActivity implements View.OnClickListener {

    private View mNormalLayout;
    private View mDeleteLayout;

    TextView runAnime = null;
    ImageView img1, img2, img3, img4, img5, img6, img7, img8;
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

    public static RelativeLayout webview_layout = null;
    LinearLayout feed_view_back = null;
    static WebView mWebView = null;

    public void attachCallback(DeleteCallback item) {
        updateCallbacks = item;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_secrets);

        runAnime = new TextView(this);
        ct = this;
        mWebView = (WebView) findViewById(R.id.feed_view_vb);
        webview_layout = (RelativeLayout) findViewById(R.id.webview_layout);

        // ParseUser user = ParseUser.getCurrentUser();
        ((TextView) findViewById(R.id.stats_username)).setText(AppSession.getValue(ct, Constants.USER_NAME));

        try {
            String result = AppSession.getValue(ct, Constants.USER_VERIFIED);
            if (result != null && result.equalsIgnoreCase("true"))
                findViewById(R.id.stats_verify).setVisibility(View.VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }


        // if( MainActivity.isIAMVerified)

       /* new VerifyReviewLoader(new VerifyCallback() {
            @Override
            public void onUpdate(boolean result) {
                if (result) {
                    findViewById(R.id.stats_verify).setVisibility(View.VISIBLE);
                }
            }
        }).execute(ParseUser.getCurrentUser().getObjectId());*/

        startTracking(getString(R.string.analytics_MYSecrets));

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
       /* new MineSocialCount(new MineCountCallback() {
            @Override
            public void onCount(String hugs, String hearts, String me2s) {

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
        }).execute();*/


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
        if (getIntent().getExtras() == null) {
            load(Constants.STATE_MINE_SECRETS, true);
        }
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

    private void runSupport() {
        startActivity(new Intent(this, SupportActivity.class));
    }

    private void runSecretCreate() {
        if (!MainActivity.is_flag)
            startActivity(new Intent(this, CreateSecretActivity.class));
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
        } catch (Exception e) {
            e.printStackTrace();
        }


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
    }
}
