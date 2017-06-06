package com.helio.cypher.pushnotification;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.helio.cypher.BuildConfig;
import com.helio.cypher.EncryptionDecryption.CryptLib;
import com.helio.cypher.WebserviceDTO.DeviceTokenDTO;
import com.helio.cypher.WebserviceDTO.DeviceTokenObjectDTO;
import com.helio.cypher.WebserviceDTO.DevicetokendataDTO;
import com.helio.cypher.activities.MainActivity;
import com.helio.cypher.connection.IfriendRequest;
import com.helio.cypher.utils.AppSession;
import com.helio.cypher.utils.Constants;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class GCMPushNotifHandler {

    private static final String TAG = "PushNotifTest";


    SimpleDateFormat df = new SimpleDateFormat("dd");

    DevicetokendataDTO devicetokendataDTO = null;
    Calendar c = Calendar.getInstance();
    SharedPreferences myPrefs;

    // TODO - this must be in a common single place in the app
    // private static final String SERVER_BASE_URL =
    // "https://slbt-staging.appspot.com";

    // private static final String REGISTER_SERVER_URL = SERVER_BASE_URL +
    // "/registerpushnotifid";
    // private static final String UNREGISTER_SERVER_URL = SERVER_BASE_URL +
    // "/unregisterpushnotifid";

    // TODO - for test only
//	private static final String USER_ID = "EM8847022711166435";
//	private static final String SECRET_CODE = "6da4a255d3324757ffc604def841edb06c861001";

    private static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";

    private static final String REG_ID = "reg_id";

    private static final String CLEAR_CACHE = "clear_cache";

    private static final String GAE_SENDER_ID = "457663896982";

    private GoogleCloudMessaging gcm;
    private Activity context;

    public GCMPushNotifHandler(Activity context) {
        this.context = context;
        this.gcm = GoogleCloudMessaging.getInstance(context);
    }

    // TODO - to be invoked when the application launches if it already has
    // UserID and SecretCode,
    // or just after success login, when it gets the User ID and SecreteCode
    public void register() {
        //	deleteRegIdFromSharedPref();

        {
            Log.d(TAG,
                    "Reg ID not found on shard pref - registering in background.");
            registerInBackground();
        } /*else {
            Log.d(TAG, "Registration found on local storage.");
			//showToastMsg("App already registered");
		}*/
    }

    // TODO - to be invoked when user does a logout

    /**
     * Registers the application with GCM servers asynchronously.
     * <p>
     * Stores the registration ID and the app versionCode in the application's
     * shared preferences.
     */
    private void registerInBackground() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                if (gcm == null) {
                    gcm = GoogleCloudMessaging.getInstance(context);
                }

                int retryCount = 0;
                boolean success = false;

                while (!success) {
                    try {
                        String regId = gcm.register(GAE_SENDER_ID);

                        storeregid(regId);

						/*formattedDate = df.format(c.getTime());

						storetodaydate(formattedDate);*/


                        success = true;

                        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                        String imei_numer = telephonyManager.getDeviceId();


                        if (regId != null && !regId.equalsIgnoreCase("")) {
                            try {
                                String version = BuildConfig.VERSION_NAME;
                                String versionRelease = Build.VERSION.RELEASE;
                                String device_details = versionRelease;
                                String username = CryptLib.encrypt(AppSession.getValue(context, Constants.USER_NAME));
                                devicetokendataDTO = new DevicetokendataDTO(username, regId, version, imei_numer, device_details);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    } catch (IOException e) {
                        if (retryCount < ServerUtil.RETRY_COUNT) {
                            Log.w(TAG, "IOException - (" + e.toString()
                                    + ") - Retrying soon...");
                            try {
                                Thread.sleep(ServerUtil.RETRY_INTERVAL);
                            } catch (InterruptedException ex) {
                            }
                        } else {
                            Log.e(TAG, "IOException - All retries failed.");
                            // showToastMsg("ERROR : Could not get Registration ID");
                            break;
                        }
                        retryCount++;
                    }
                }

                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    if (devicetokendataDTO == null) {
                        textView = new TextView(context);
                        textView.postDelayed(device_tokern, 1000);

                    } else
                        new UpdateDeviceToken().execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }.execute(null, null, null);
    }

    TextView textView = null;

    Runnable device_tokern = new Runnable() {
        @Override
        public void run() {
            try {
                textView.removeCallbacks(device_tokern);

                if (devicetokendataDTO == null)
                    textView.postDelayed(device_tokern, 1000);
                else
                    new UpdateDeviceToken().execute();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };


    private class UpdateDeviceToken extends AsyncTask<String, String, Bitmap> {


        String status = "";


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected Bitmap doInBackground(String... args) {
            try {

                IfriendRequest http = new IfriendRequest(context);
                DeviceTokenDTO deviceTokenDTO = new DeviceTokenDTO(Constants.ENCRYPT_USER_TABLE, Constants.ENCRYPT_FIND_METHOD, devicetokendataDTO);
                DeviceTokenObjectDTO loginbjectDTO = new DeviceTokenObjectDTO(deviceTokenDTO);

                status = http.UpdateToken(loginbjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


    }


    /**
     * Gets the current registration ID for application on GCM service, if there
     * is one. If result is empty, the app needs to register.
     *
     * @return registration ID, or null if there is no existing registration ID.
     */

    public String getRegistrationId() {


        //final SharedPreferences prefs = getGcmPreferences();
        String registrationId = getregid();

	/*	if (registrationId == null || registrationId.isEmpty())
			registrationId = getregid();*/

        if (registrationId == null || registrationId.isEmpty()) {
            Log.i(TAG, "Registration not found.");
            return null;
        }

        return registrationId;
    }


    public String getclaeardatastatus() {


        final SharedPreferences prefs = getGcmPreferences();
        String registrationId = prefs.getString(CLEAR_CACHE, "");


        return registrationId;
    }


    /**
     * Stores the registration ID and the app versionCode in the application's
     * {@code SharedPreferences}.
     */
    private void storeRegistrationId(String regId) {
        final SharedPreferences prefs = getGcmPreferences();
        int appVersion = getAppVersion();
        Log.i(TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_REG_ID, regId);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }


    private void storetodaydate(String date) {
        try {
            SharedPreferences.Editor editor = context.getSharedPreferences("Silent", context.MODE_PRIVATE).edit();
            editor.putString(Constants.LAST_REG_DATE, date);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String gettodaysdate() {
        String restoredText = "";

        try {
            SharedPreferences prefs = context.getSharedPreferences("Silent", context.MODE_PRIVATE);
            restoredText = prefs.getString(Constants.LAST_REG_DATE, "");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return restoredText;
    }

    private void storeregid(String regid) {
        try {
            SharedPreferences.Editor editor = context.getSharedPreferences("Cypher", context.MODE_PRIVATE).edit();
            editor.putString(REG_ID, regid);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getregid() {
        String restoredText = "";

        try {
            SharedPreferences prefs = context.getSharedPreferences("Cypher", context.MODE_PRIVATE);
            restoredText = prefs.getString(REG_ID, "");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return restoredText;
    }


    public void setIsdtataclear(String cleardata) {
        try {
            SharedPreferences.Editor editor = context.getSharedPreferences("Silent", context.MODE_PRIVATE).edit();
            editor.putString(CLEAR_CACHE, cleardata);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getisdataclear() {
        String restoredText = "";

        try {
            SharedPreferences prefs = context.getSharedPreferences("Silent", context.MODE_PRIVATE);
            restoredText = prefs.getString(CLEAR_CACHE, "");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return restoredText;
    }


    public void deleteRegIdFromSharedPref() {
        final SharedPreferences prefs = getGcmPreferences();
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(PROPERTY_REG_ID);
        editor.remove(PROPERTY_APP_VERSION);
        editor.commit();
    }

    private int getAppVersion() {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    // /////change
    private SharedPreferences getGcmPreferences() {
        return context.getSharedPreferences(
                MainActivity.class.getSimpleName(),
                Context.MODE_PRIVATE);
    }

    private void showToastMsg(final String msg) {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
            }
        });
    }
}
