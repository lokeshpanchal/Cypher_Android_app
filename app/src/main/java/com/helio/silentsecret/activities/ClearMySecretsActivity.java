package com.helio.silentsecret.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.helio.silentsecret.R;
import com.helio.silentsecret.WebserviceDTO.ClearMySecretDTO;
import com.helio.silentsecret.WebserviceDTO.ClearMySecretDataDTO;
import com.helio.silentsecret.WebserviceDTO.ClearMysecretObjectDTO;
import com.helio.silentsecret.callbacks.DeleteCallback;
import com.helio.silentsecret.connection.ConnectionDetector;
import com.helio.silentsecret.connection.IfriendRequest;
import com.helio.silentsecret.dialogs.AccessDialog;
import com.helio.silentsecret.dialogs.ProgressDialog;
import com.helio.silentsecret.dialogs.RateDialog;
import com.helio.silentsecret.dialogs.WebViewDialog;
import com.helio.silentsecret.fragments.ClearSecretsFragment;
import com.helio.silentsecret.fragments.MineSecretsFragment;
import com.helio.silentsecret.models.Secret;
import com.helio.silentsecret.utils.Constants;
import com.helio.silentsecret.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;


public class ClearMySecretsActivity extends BaseActivity implements View.OnClickListener {


    private int width = 0, height;


    Context ct = null;

    public static boolean mIsSelectedAll = false;

    private List<String> deleteArray = new ArrayList<>();

    private DeleteCallback updateCallbacks;
    LinearLayout hug_heart_layout = null;
    public static RelativeLayout webview_layout = null;
    LinearLayout feed_view_back = null;
    static WebView mWebView = null;

    TextView search_secret = null, search_back = null;

boolean isDeleteMode = true;
    ImageView my_secret = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear_my_secrets);

        ct = this;
        mWebView = (WebView) findViewById(R.id.feed_view_vb);
        webview_layout = (RelativeLayout) findViewById(R.id.webview_layout);
        search_back = (TextView) findViewById(R.id.search_back);
        search_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        //my_secret.setOnClickListener(this);


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

        isDeleteMode = true;




        initViews();


        //search_secret.setOnClickListener(this);


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


    }


    private void initViews() {
        findViewById(R.id.mine_progress_bg_iv).setOnClickListener(this);


        findViewById(R.id.mine_delete_home).setOnClickListener(this);
        findViewById(R.id.mine_delete_select_all).setOnClickListener(this);
        findViewById(R.id.mine_delete_done).setOnClickListener(this);
        MineSecret();
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


            case R.id.mine_delete_home:
                finish();
                break;
            case R.id.mine_delete_select_all:
                if (mIsSelectedAll) {
                    mIsSelectedAll = false;

                    // updateCallbacks.onUnSelectAll();
                    Intent intent = new Intent(ct, ClearMySecretsActivity.class);
                    intent.putExtra(Constants.ACCESS_CLEAR_SECRET, true);
                    startActivity(intent);
                    finish();

                } else {
                    mIsSelectedAll = true;
                    //  updateCallbacks.onSelectAll();
                    Intent intent = new Intent(ct, ClearMySecretsActivity.class);
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


    private void MineSecret() {


        findViewById(R.id.mine_frame).setVisibility(View.VISIBLE);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mine_frame, getItem());
        transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss();

    }


    public void showProgress() {
        ProgressDialog.showDialog(findViewById(R.id.mine_progress_bg_iv), findViewById(R.id.mine_progress_pb));
    }

    public void stopProgress() {
        ProgressDialog.hideDialog(findViewById(R.id.mine_progress_bg_iv), findViewById(R.id.mine_progress_pb));
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


    public void webViewClosed() {
        replaceRate();
    }


    public void deleteOwnData() {
        if (deleteArray.isEmpty()) {
            new ToastUtil(this, "Please select secrets for delete");
            return;
        }

        if (!ConnectionDetector.isNetworkAvailable(this))
            return;


        new DeleteMysecret().execute();

    }

    public void addDeleteItem(String item) {
        if (!deleteArray.contains(item))
            deleteArray.add(item);
    }

    public void removeDeleteItem(String item) {
        if (deleteArray.contains(item))
            deleteArray.remove(item);
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
                ClearMySecretDTO clearMySecretDTO = new ClearMySecretDTO(clearMySecretDataDTO);
                ClearMysecretObjectDTO clearMysecretObjectDTO = new ClearMysecretObjectDTO(clearMySecretDTO);

                data = http.ClearmySecret(clearMysecretObjectDTO);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {

            stopProgress();
            try {

               /* updateCallbacks.onDelete(deleteArray);
                deleteArray.clear();*/

                Intent intent = new Intent(ct, ClearMySecretsActivity.class);
                intent.putExtra(Constants.ACCESS_CLEAR_SECRET, true);
                startActivity(intent);
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        MineSecretsFragment.envelop_secre_id = "";

    }


    public Fragment getItem() {
        return ClearSecretsFragment.instance(Constants.STATE_MINE_SECRETS, true);
    }
}
