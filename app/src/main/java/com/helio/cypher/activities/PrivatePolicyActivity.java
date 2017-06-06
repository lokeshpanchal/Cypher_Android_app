package com.helio.cypher.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.helio.cypher.R;
import com.helio.cypher.WebserviceDTO.FindbyNameDTO;
import com.helio.cypher.WebserviceDTO.UpdateSafeguardDataDTO;
import com.helio.cypher.appCounsellingDTO.CommonRequestTypeDTO;
import com.helio.cypher.appCounsellingDTO.FinalObjectDTO;
import com.helio.cypher.connection.IfriendRequest;
import com.helio.cypher.utils.AppSession;
import com.helio.cypher.utils.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class PrivatePolicyActivity extends BaseActivity {

    WebView webview = null;
    String safeguard_date = "";
    Context ct ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policy_updated);
        webview = (WebView) findViewById(R.id.webview);
        ct = this;

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            safeguard_date = df.format(c.getTime());
        AppSession.save(ct, Constants.USER_SAFE_GUARD, safeguard_date);

        new UpdateDisclaimer().execute();

     //   initWebView("https://cypher-admin.eu-gb.mybluemix.net/privacypolicy.html"); dev server

        initWebView("https://cypheradmin.eu-gb.mybluemix.net/privacypolicy.html");
        findViewById(R.id.policy_home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.policy_get_support).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PrivatePolicyActivity.this, SupportActivity.class));
            }
        });
    }

   /* private String readData() {
        String text = "privacy.txt";
        byte[] buffer = null;
        InputStream is;
        try {
            is = getAssets().open(text);
            int size = is.available();
            buffer = new byte[size];
            is.read(buffer);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(buffer);
    }*/

    public void initWebView(String url) {

        webview.getSettings().setJavaScriptEnabled(true);


        webview.loadUrl(url);

        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                webview.loadUrl(url);
                return false;

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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (webview.canGoBack()) {
                        webview.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
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
                UpdateSafeguardDataDTO findbyNameDTO = new UpdateSafeguardDataDTO(MainActivity.enc_username,safeguard_date);
                CommonRequestTypeDTO findNameDTO = new CommonRequestTypeDTO(findbyNameDTO,"updateSafegaurd");
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
