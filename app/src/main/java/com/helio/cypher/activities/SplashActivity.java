package com.helio.cypher.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.telephony.TelephonyManager;
import android.widget.ImageView;

import com.helio.cypher.BuildConfig;
import com.helio.cypher.EncryptionDecryption.CryptLib;
import com.helio.cypher.R;
import com.helio.cypher.WebserviceDTO.DeviceTokenDTO;
import com.helio.cypher.WebserviceDTO.DeviceTokenObjectDTO;
import com.helio.cypher.WebserviceDTO.DevicetokendataDTO;
import com.helio.cypher.connection.ConnectionDetector;
import com.helio.cypher.connection.IfriendRequest;
import com.helio.cypher.pushnotification.GCMPushNotifHandler;
import com.helio.cypher.utils.AppSession;
import com.helio.cypher.utils.Constants;
import com.helio.cypher.utils.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class SplashActivity extends FragmentActivity {

    ImageView splashIcon = null;


    GCMPushNotifHandler mGcmPushNotifHandler;
   // private String deviceTOken = "";
 //   DevicetokendataDTO devicetokendataDTO = null;
  //  String imei_numer = "";
    Activity ct = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ct = this;
        try
        {
            splashIcon = (ImageView) findViewById(R.id.splash_icon);
            splashIcon.postDelayed(new Runnable() {
                @Override
                public void run()
                {

                    Constants.primary_server = true;

                    AppSession.save(ct, Constants.COMMENT_SECRET_ID,"");
                    String userna = AppSession.getValue(ct, Constants.USER_NAME);
                    if (userna == null || userna.equalsIgnoreCase(""))
                    {
                        LoginActivity.CurrentUsername = AppSession.getValue(ct, Constants.USER_NAME_PARSE);
                    }

                    if (ConnectionDetector.isNetworkAvailable(ct))
                    {

                        if (userna == null || userna.equalsIgnoreCase(""))
                        {
                            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else
                        {

                           /* MainActivity.is_from_login = false;
                            if (ConnectionDetector.isNetworkAvailable(ct))
                            {

                                deviceTOken = mGcmPushNotifHandler.getRegistrationId();

                            } else {
                                new ToastUtil(ct, "Please check your internet connection.");
                            }

                            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                            imei_numer = telephonyManager.getDeviceId();

                            try {
                                if (deviceTOken == null || deviceTOken.equalsIgnoreCase(""))
                                {
                                    deviceTOken = mGcmPushNotifHandler.getregid();
                                }
                            } catch (Exception e3) {
                                e3.printStackTrace();
                            }*/
                            MainActivity.is_from_login = false;
                            mGcmPushNotifHandler = new GCMPushNotifHandler(ct);
                            mGcmPushNotifHandler.register();

                            Getpetname();
                        }

                    } else
                    {
                        if (userna == null || userna.equalsIgnoreCase(""))
                        {

                            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else
                        {
                            MainActivity.is_from_login = false;
                            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                }
            },500);



        } catch (Exception e)
        {

            e.printStackTrace();
        }

    }


 /*   private void deviceToken()
    {
        if (deviceTOken != null && !deviceTOken.equalsIgnoreCase("")) {
            try {
                String version = BuildConfig.VERSION_NAME;
                String versionRelease = Build.VERSION.RELEASE;
                String device_details = versionRelease;
                String username = CryptLib.encrypt(AppSession.getValue(ct, Constants.USER_NAME));
                devicetokendataDTO = new DevicetokendataDTO(username, deviceTOken, version, imei_numer, device_details);
                new UpdateDeviceToken().execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Getpetname();

        }
    }
*/

    private void Getpetname()
    {
        String pet_name = AppSession.getValue(ct, Constants.USER_PET_NAME);
        if (pet_name != null && !pet_name.equalsIgnoreCase("") && !pet_name.equalsIgnoreCase("null")) {
            try
            {
                Intent intent = new Intent(SplashActivity.this, PetAvtarActivity.class);
                startActivity(intent);
                finish();

            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } else
        {
            try {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

    }


    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


/*
    private class UpdateDeviceToken extends AsyncTask<String, String, Bitmap> {

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

            Getpetname();

        }
    }
*/


}


