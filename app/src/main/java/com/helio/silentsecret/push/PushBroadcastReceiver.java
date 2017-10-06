package com.helio.silentsecret.push;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;

import com.helio.silentsecret.R;
import com.helio.silentsecret.activities.ActionSecretActivity;
import com.helio.silentsecret.activities.CommentSecretActivity;
import com.helio.silentsecret.activities.MainActivity;
import com.helio.silentsecret.activities.MySecretsActivity;
import com.helio.silentsecret.utils.Constants;
import com.helio.silentsecret.utils.Preference;

import org.json.JSONException;
import org.json.JSONObject;

public class PushBroadcastReceiver extends BroadcastReceiver {

    private Context mContext;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (mContext == null)
            mContext = context;



        JSONObject data = null;
        String alert = mContext.getString(R.string.app_name);
        String secretId = null;
        String action = null;
        String commentId = null;

        try {
            data = new JSONObject(intent.getExtras().getString(
                    Constants.PUSH_DATA));
            if (data.has(Constants.PUSH_ALERT))
                alert = data.optString(Constants.PUSH_ALERT);
            if (data.has(Constants.PUSH_SECRET_ID))
                secretId = data.optString(Constants.PUSH_SECRET_ID);
            if (data.has(Constants.PUSH_ACTION))
                action = data.optString(Constants.PUSH_ACTION);
            if (data.has(Constants.PUSH_REPLY_COMMENT_ID))
                commentId = data.optString(Constants.PUSH_REPLY_COMMENT_ID);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        if (data != null && data.has(Constants.PUSH_ACTION)) {
            generateActionPush(mContext, alert, secretId, action, commentId);
        } else {
            generateActionPush(mContext, alert, null, null, null);
        }
    }

    public static void generateActionPush(Context context, String message, String secretId, String action, String commentId) {

        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        Resources res = context.getResources();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                context);

        Intent notificationIntent = null;

        if (action != null)
        {

            if (action.equals(Constants.ACTION_CAUTION))
            {
                Preference.saveCaution(true);
                LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(Constants.ACTION_CAUTION));
                return;
            } else if (action.equals(Constants.PUSH_ACTION_SOCIAL))
            {
                notificationIntent = new Intent(context, ActionSecretActivity.class);
                notificationIntent.putExtra(Constants.PUSH_SECRET_ID, secretId);
                notificationIntent.putExtra(Constants.ACCESS_SECRET_SHOW_DIALOG, true);
            } else if (action.equals(Constants.PUSH_ACTION_COMMENT))
            {
                notificationIntent = new Intent(context, CommentSecretActivity.class);
                notificationIntent.putExtra(Constants.PUSH_SECRET_ID, secretId);
            } else if (action.equals(Constants.PUSH_ACTION_COMMENT_REPLY))
            {
                notificationIntent = new Intent(context, CommentSecretActivity.class);
                notificationIntent.putExtra(Constants.PUSH_SECRET_ID, secretId);
                notificationIntent.putExtra(Constants.PUSH_REPLY_COMMENT_ID, commentId);
            } else if (action.equals(Constants.PUSH_BACKEND))
            {
                notificationIntent = new Intent(context, MySecretsActivity.class);
                notificationIntent.putExtra(Constants.PUSH_ACTION, Constants.PUSH_BACKEND);
              //  generateNotificationClass();
            }

            if (notificationIntent != null)
                notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            else {
                notificationIntent = new Intent(context, MainActivity.class);
                notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            }
        } else
        {
            // notificationIntent = null;
            notificationIntent = new Intent(context, MainActivity.class);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
/*
        if (ParseUser.getCurrentUser() == null)
        {
            notificationIntent = new Intent(context, LoginActivity.class);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }*/

        if (notificationIntent != null)
        {
            builder.setContentIntent(PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT));
        }

        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        bigTextStyle.setBigContentTitle(res.getString(R.string.app_name));
        bigTextStyle.bigText(message);

        builder.setSmallIcon(R.drawable.ic_launcher)
                .setAutoCancel(true).setContentTitle(res.getString(R.string.app_name))
                .setContentText(message).setStyle(bigTextStyle).setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
        Notification n = builder.build();
        n.flags |= Notification.FLAG_AUTO_CANCEL;
        n.vibrate = new long[]{50, 50, 50};
        notificationManager.notify(0, n);
    }


}
