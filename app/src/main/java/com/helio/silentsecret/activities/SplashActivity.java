package com.helio.silentsecret.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.widget.ImageView;

import com.helio.silentsecret.EncryptionDecryption.CryptLib;
import com.helio.silentsecret.R;
import com.helio.silentsecret.connection.ConnectionDetector;
import com.helio.silentsecret.pushnotification.GCMPushNotifHandler;
import com.helio.silentsecret.utils.AppSession;
import com.helio.silentsecret.utils.Constants;


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

                            String thank_message =  AppSession.getValue(ct, Constants.SHOW_THANK_MESSAGE);

                            if (thank_message == null || thank_message.equalsIgnoreCase(""))
                            {
                                AlertDialog.Builder updateAlert = new AlertDialog.Builder(ct);
                                updateAlert.setIcon(R.drawable.ic_launcher);
                                updateAlert.setTitle("Cypher");
                                updateAlert.setMessage("Thank you for updating your app, Silent Secret has now rebranded as Cypher. Your existing login details works with Cypher app. Welcome to your Cypherian tribe on Cypher.");
                                updateAlert.setPositiveButton(
                                        "Ok",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id)
                                            {
                                                dialog.cancel();
                                                AppSession.save(ct, Constants.SHOW_THANK_MESSAGE,"true");
                                                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        });
                                AlertDialog alertUp = updateAlert.create();
                                alertUp.setCanceledOnTouchOutside(false);
                                alertUp.show();
                            }
                            else
                            {
                                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }


                        } else
                        {

                            MainActivity.is_from_login = false;
                            mGcmPushNotifHandler = new GCMPushNotifHandler(ct);
                            mGcmPushNotifHandler.register();
                            Intent intent = new Intent(SplashActivity.this, PetAvtarActivity.class);
                            startActivity(intent);
                            finish();
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


