package com.helio.silentsecret.pushnotification;

import android.app.ActivityManager;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.helio.silentsecret.R;
import com.helio.silentsecret.activities.ChatDetailsScreen;
import com.helio.silentsecret.activities.LiveCounselling;
import com.helio.silentsecret.activities.MainActivity;
import com.helio.silentsecret.fragments.SearchFragment;
import com.helio.silentsecret.utils.AppSession;
import com.helio.silentsecret.utils.Constants;
import com.helio.silentsecret.utils.Preference;

import org.json.JSONObject;

import java.util.List;


/**
 * This {@code IntentService} does the actual handling of the GCM message.
 * {@code GcmBroadcastReceiver} (a {@code WakefulBroadcastReceiver}) holds a
 * partial wake lock for this service while the service does its work. When the
 * service is finished, it calls {@code completeWakefulIntent()} to release the
 * wake lock.
 */
public class GcmIntentService extends IntentService {

    public static final String TAG = "PushNotifTest";
    private String senderID;
    private String date;
    private String app_type = "";
    private String type = "";
    private String _badgeCount = "";
    private String alert = "";
    private String source_email = "";
    private String message = "";

    private String lang = "";
    private boolean inForground = false;
    Bundle extras;


    public static String mUsernameSecret = "";
    public static String mSecretid = "";
    boolean dont_show = false;
    SharedPreferences sPref;
    SharedPreferences.Editor editor = null;
    public static String mSecrettext = "";
    String timing = "";
    public static  int remove_noti_id = 0;

    public GcmIntentService() {
        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        extras = intent.getExtras();

        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty())
        {

            if (messageType.equals(GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE)) {
                Log.i(TAG, "**** Push Notification Received from GCM *****");
                Log.i(TAG, "Received: " + extras.toString());


                message = extras.getString("message");
                alert = extras.getString("title");
                type = extras.getString("type");
                app_type = extras.getString("appname");



               /* if(app_type == null || app_type.equalsIgnoreCase("") || (type!= null && type.equalsIgnoreCase("message_seen")))
                    return;*/

                mSecretid = extras.getString("secret_id");
                mSecrettext = extras.getString("secrettext");

                try
                {
                    String data =  extras.getString("data");

                    JSONObject jsonObject = new JSONObject(data);
                    if(jsonObject.has("username"))
                    mUsernameSecret = jsonObject.getString("username");
                    else if(jsonObject.has("clun01"))
                        mUsernameSecret = jsonObject.getString("clun01");

                    if(jsonObject.has("secret_id"))
                        mSecretid = jsonObject.getString("secret_id");

                     if(jsonObject.has("timing"))
                     {
                         timing = jsonObject.getString("timing");
                         AppSession.save(this,Constants.SOMEONELOVE_SECRET_ID,mSecretid);
                         AppSession.save(this,Constants.COMMENT_TIMING,timing);
                     }



                  /*  if(jsonObject.has("secrettext"))
                        mSecrettext = jsonObject.getString("secrettext");*/


                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

               // mUsernameSecret = extras.getString("username");






                AppSession.save(this,Constants.COMMENT_SECRET_ID,mSecretid);


                ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
                List<ActivityManager.RunningAppProcessInfo> tasks = am.getRunningAppProcesses();
                if (tasks != null)
                {
                    for (ActivityManager.RunningAppProcessInfo appProcess : tasks)
                    {
                        String s = appProcess.processName;


                        if (appProcess.importance ==
                                ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
                                && appProcess.processName.contains("com.helio.silentsecret"))
                        {
                            inForground = true;


                        } else {
                            inForground = false;
                        }
                        break;
                    }




                    alert = "Cypher";
                    sendNotification("Cypher", alert, message);

                }
                else
                {
                    Toast.makeText(this,"else me aya",Toast.LENGTH_SHORT).show();
                }
            } else {
                sendNotification("ERROR", "Notification ERROR", "Something is wrong, but I don't know what it is!");
            }
        }


        // Release the wake lock provided by the WakefulBroadcastReceiver.
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    /**
     * Puts a notification as example.
     */
    private void sendNotification(String ticker, String title, String text) {
        int notifId = (int) (Math.random() * 1000) + 1000;
        // Constant.notiID.add(notifId);

        Intent i = null;
        dont_show = false;
        if (type != null && !type.equalsIgnoreCase(""))
        {
            if(mSecretid!= null && !mSecretid.equalsIgnoreCase("") && !type.equalsIgnoreCase("My secrets Page"))
            {
                i = new Intent(this, MainActivity.class);
                i.putExtra("FINDSECRET", true);
                i.putExtra("SECRETID", mSecretid);
            }
           else if (type.equalsIgnoreCase("New Request"))
            {

                i = new Intent(this, MainActivity.class);
                i.putExtra("FROMNOTIF", true);
            } else if (type.equalsIgnoreCase("Reject Request")) {

                i = new Intent(this, MainActivity.class);
                i.putExtra("FROMNOTIF", true);
            } else if (type.equalsIgnoreCase("NearBy")) {

                i = new Intent(this, MainActivity.class);
                i.putExtra("FROMNOTIF", true);
            } else if (type.equalsIgnoreCase("Request Expire")) {

                i = new Intent(this, MainActivity.class);
                i.putExtra("FROMNOTIF", true);
            }
            else if (type.equalsIgnoreCase("Unfriend"))
            {

                i = new Intent(this, MainActivity.class);
                i.putExtra("UNFRIEND", true);
            }
            else if (type.equalsIgnoreCase(Constants.ACTION_CAUTION) )
            {

                if(inForground) {
                    Preference.saveCaution(true);
                    LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(Constants.ACTION_CAUTION));
                    return;
                }
                else
                {
                    i = new Intent(this, MainActivity.class);
                    Preference.saveCaution(true);
                }


            }
            else if (type.equalsIgnoreCase("Latest Filtering Page"))
            {

                i = new Intent(this, MainActivity.class);
                i.putExtra("LATEST_FILTERING", true);
            }

            else if (type.equalsIgnoreCase("Rate SchoolCollege Page"))
            {

                i = new Intent(this, MainActivity.class);
                i.putExtra("RATE_SCHOOL_COLLEGE", true);
            }
            else if (type.equalsIgnoreCase("Stats Page"))
            {

                i = new Intent(this, MainActivity.class);
                i.putExtra("STATS_PAGE", true);
            }
            else if (type.equalsIgnoreCase("My secrets Page"))
            {

                i = new Intent(this, MainActivity.class);
                i.putExtra("MY_SECRET_PAGE", true);
            }


            else if (type.equalsIgnoreCase("Get Support Page"))
            {

                i = new Intent(this, MainActivity.class);
                i.putExtra("GET_SUPPORT_PAGE", true);
            }

            else if(type.equalsIgnoreCase("Appoinment Accepted") ||type.equalsIgnoreCase("Appoinment_reasign") ||type.equalsIgnoreCase("book_first_appointment")  || type.equalsIgnoreCase("AppCounselling Glimpse Page")||  type.equalsIgnoreCase("Ratting") || type.equalsIgnoreCase("Counselling Start") || type.equalsIgnoreCase("Minute") || type.equalsIgnoreCase("24Hour") || type.equalsIgnoreCase("1Hour") || type.equalsIgnoreCase("No Counsellor") || type.equalsIgnoreCase("Reschedule") || type.equalsIgnoreCase("Time Suggestion") || type.equalsIgnoreCase("counselling_chat"))
            {
                if(type.equalsIgnoreCase("counselling_chat") && LiveCounselling.chat_running)
                    dont_show = true;
                else if(type.equalsIgnoreCase("counselling_chat"))
                {
                    if(remove_noti_id !=0)
                        cancelNotification(this,remove_noti_id);

                    remove_noti_id = notifId;
                    i = new Intent(this, MainActivity.class);
                    i.putExtra("APPCOUNSELLING", true);
                }
                else
                {
                    i = new Intent(this, MainActivity.class);
                    i.putExtra("APPCOUNSELLING", true);
                }
            }

            else if (type.equalsIgnoreCase("Approve Request")) {

                if (mUsernameSecret != null && !mUsernameSecret.equalsIgnoreCase(""))
                {

                    i = new Intent(this, MainActivity.class);
                    i.putExtra(mUsernameSecret, true);
                } else {
                    i = new Intent(this, MainActivity.class);
                }
            } else if (type.equalsIgnoreCase("Chat Message") )
            {
                i = new Intent(this, MainActivity.class);
                i.putExtra("CHAT", true);
                if (ChatDetailsScreen.chat_username != null && !ChatDetailsScreen.chat_username.equalsIgnoreCase(""))
                {
                    if (ChatDetailsScreen.chat_username.equalsIgnoreCase(mUsernameSecret))
                        dont_show = true;
                }

            }
            else if (type.equalsIgnoreCase("Secret Sharing Page") )
            {
                i = new Intent(this, MainActivity.class);
                i.putExtra("SECRET_SHARING", true);

            }
            else if (type.equalsIgnoreCase("Entertainment Glimpse Page"))
            {
                i = new Intent(this, MainActivity.class);
                i.putExtra("ENTERTAINMENT_GLIMPSE", true);

            }

            else if (type.equalsIgnoreCase("Trending Page") )
            {
                i = new Intent(this, MainActivity.class);
                i.putExtra("TRENDING_PAGE", true);

            }
            else if (type.equalsIgnoreCase("Mood Page") )
            {
                i = new Intent(this, MainActivity.class);
                i.putExtra("MOOD_PAGE", true);

            }

            else if (type.equalsIgnoreCase("Lifestyle Glimpse Page") )
            {
                i = new Intent(this, MainActivity.class);
                i.putExtra("LIFESTYLE_GLIMPSE", true);

            }

            else if (type.equalsIgnoreCase("News Glimpse Page") )
            {
                i = new Intent(this, MainActivity.class);
                i.putExtra("NEWS_GLIMPSE", true);

            }
            else if (type.equalsIgnoreCase("Entertainment Glimpse Page") )
            {
                i = new Intent(this, MainActivity.class);
                i.putExtra("ENTERTAINMENT_GLIMPSE", true);

            }
            else if (type.equalsIgnoreCase("iFriend Page"))
            {
                i = new Intent(this, MainActivity.class);
                i.putExtra("CHAT", true);
                /*if (ChatDetailsScreen.chat_username != null && !ChatDetailsScreen.chat_username.equalsIgnoreCase("")) {
                    if (ChatDetailsScreen.chat_username.equalsIgnoreCase(mUsernameSecret))
                        dont_show = true;

                }*/

            }
            else if (type.equalsIgnoreCase("Share Secret") || type.equalsIgnoreCase("me2") || type.equalsIgnoreCase("heart")|| type.equalsIgnoreCase("hug") || type.equalsIgnoreCase("comment"))
            {

                if (mSecretid != null && !mSecretid.equalsIgnoreCase(""))
                {
                    i = new Intent(this, MainActivity.class);
                    i.putExtra(mSecretid, true);
                    i.putExtra("COMMENT", true);

                    SearchFragment.secret_id = mSecretid;
                   // SearchFragment.secret_text = mSecrettext;
                } else {
                    i = new Intent(this, MainActivity.class);
                }
            }

           /* else if (type.equalsIgnoreCase("comment"))
            {
                if (mSecretid != null && !mSecretid.equalsIgnoreCase(""))
                {
                    i = new Intent(this, MainActivity.class);
                    i.putExtra(mSecretid, true);

                    SearchFragment.secret_id = mSecretid;
                    SearchFragment.secret_text = mSecrettext;
                } else {
                    i = new Intent(this, MainActivity.class);
                }
            }*/
            else
            {
                i = new Intent(this, MainActivity.class);
            }
        } else
        {
            i = new Intent(this, MainActivity.class);
        }

        if (!dont_show)
        {

            PendingIntent contentIntent = PendingIntent.getActivity(this, notifId, i,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.ic_launcher).setTicker(ticker).setContentTitle(title)
                    .setContentText(text).setAutoCancel(true).setSound(Settings.
                            System.DEFAULT_NOTIFICATION_URI)
                    .setLights(Color.BLUE, 3000, 3000).setContentIntent(contentIntent);

            NotificationManager notifManager = (NotificationManager) this
                    .getSystemService(Context.NOTIFICATION_SERVICE);

            notifManager.notify(notifId, notifBuilder.build());
        } else
            dont_show = false;

    }

    // ////////////////clear all notification from bar
    public static void cancelNotification(Context ctx, int notifyId) {
        try
        {
            String ns = Context.NOTIFICATION_SERVICE;
            NotificationManager nMgr = (NotificationManager) ctx.getSystemService(ns);
            nMgr.cancel(notifyId);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

}
