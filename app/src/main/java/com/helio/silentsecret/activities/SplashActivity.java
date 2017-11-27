package com.helio.silentsecret.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.helio.silentsecret.R;
import com.helio.silentsecret.connection.ConnectionDetector;
import com.helio.silentsecret.connection.IfriendRequest;
import com.helio.silentsecret.pushnotification.GCMPushNotifHandler;
import com.helio.silentsecret.utils.AppSession;
import com.helio.silentsecret.utils.Constants;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.Timer;
import java.util.TimerTask;


public class SplashActivity extends FragmentActivity {

    GCMPushNotifHandler mGcmPushNotifHandler;
    Activity ct = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ct = this;


        Timer timerObj = new Timer();
        TimerTask timerTaskObj = new TimerTask()
        {
            @Override
            public void run()
            {
                new GetPetInfo().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        };

        timerObj.schedule(timerTaskObj, 1000);




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

               /* if (MainActivity.stataicObjectDTO != null)
                    MainActivity.stataicObjectDTO = null;

                IfriendRequest http = new IfriendRequest(ct);
                StaticDataDTO staticDataDTO = new StaticDataDTO();
                StaticObjectDTO staticObjectDTO = new StaticObjectDTO(staticDataDTO);
                MainActivity.stataicObjectDTO = http.GetstaticData(staticObjectDTO);*/

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {
            try {

                Constants.primary_server = true;

                AppSession.save(ct, Constants.COMMENT_SECRET_ID, "");
                String userna = AppSession.getValue(ct, Constants.USER_NAME);


                if (ConnectionDetector.isNetworkAvailable(ct))
                {

                    if (userna == null || userna.equalsIgnoreCase(""))
                    {


                        userna = AppSession.getValue(ct, Constants.GUEST_USER_NAME);
                        if (userna == null || userna.equalsIgnoreCase(""))
                        {
                            new Getdefulatuser_name().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);



                        } else {
                            MainActivity.is_from_login = false;
                            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }


                    } else {

                        MainActivity.is_from_login = false;
                        mGcmPushNotifHandler = new GCMPushNotifHandler(ct);
                        mGcmPushNotifHandler.register();
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                } else {
                    if (userna == null || userna.equalsIgnoreCase(""))
                    {
                        userna = AppSession.getValue(ct, Constants.GUEST_USER_NAME);
                        if (userna == null || userna.equalsIgnoreCase(""))
                            new Getdefulatuser_name().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        else {
                            MainActivity.is_from_login = false;
                            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        MainActivity.is_from_login = false;
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }


    private class Getdefulatuser_name extends AsyncTask<String, String, Bitmap> {

        android.app.ProgressDialog pDialog;
        String response = "";
        Bitmap bitmap;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected Bitmap doInBackground(String... args) {
            try {


                IfriendRequest http = new IfriendRequest(ct);


                String userna = AppSession.getValue(ct, Constants.USER_NAME);
                if (userna == null || userna.equalsIgnoreCase(""))
                {
                    JSONObject requestdata = new JSONObject();
                    JSONObject main_object = new JSONObject();
                    requestdata.put("apikey", "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R");
                    requestdata.put("requestType", "createGuestUser");
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

                String status = "", guest_clun01 = "";
                Object object = new JSONTokener(response).nextValue();
                if (object instanceof JSONObject) {
                    JSONObject jsonObject = new JSONObject(response);


                    if (jsonObject.has("status"))
                        status = jsonObject.getString("status");

                    if (status != null && status.equalsIgnoreCase("true")) {
                        String userinfo = "";
                        if (jsonObject.has("data"))
                            userinfo = jsonObject.getString("data");

                        JSONObject UserInfoobj = new JSONObject(userinfo);

                        if (UserInfoobj.has("guest_clun01"))
                            guest_clun01 = UserInfoobj.getString("guest_clun01");


                        AppSession.save(ct, Constants.GUEST_USER_NAME, guest_clun01);
                        AppSession.save(ct, Constants.USER_AGE, "13");


                        MainActivity.is_from_login = false;
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                    } else {
                        if (jsonObject.has("msg"))
                            status = jsonObject.getString("msg");
                    }


                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }



}


