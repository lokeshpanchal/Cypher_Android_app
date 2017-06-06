package com.helio.cypher.Services;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;

import com.helio.cypher.R;
import com.helio.cypher.activities.MainActivity;
import com.helio.cypher.utils.AppSession;
import com.helio.cypher.utils.CommonFunction;
import com.helio.cypher.utils.Constants;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by abc on 20/11/2015.
 */
public class InactiveNotification extends BroadcastReceiver {

    Context ct = null;
    SimpleDateFormat mmddff = new SimpleDateFormat("MM/dd/yyyy");
    @Override
    public void onReceive(Context context, Intent intent)
    {

                ct = context;
        try
        {
            startservice();

            Calendar c = Calendar.getInstance();
            String todaysdate = mmddff.format(c.getTime());
            String lastdate =  AppSession.getValue(ct, Constants.USERLASTATTAND);
            if(CommonFunction.Getdiffbettwodate(todaysdate,lastdate) >= 14)
            {

                Intent i = new Intent(ct, MainActivity.class);

                int notifId = (int) (Math.random() * 1000) + 1000;
                PendingIntent contentIntent = PendingIntent.getActivity(ct, notifId, i,
                        PendingIntent.FLAG_UPDATE_CURRENT);

                NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(ct)
                        .setSmallIcon(R.drawable.ic_launcher).setTicker("Cypher").setContentTitle("Cypher")
                        .setContentText("How have you been?").setAutoCancel(true).setSound(Settings.
                                System.DEFAULT_NOTIFICATION_URI)
                        .setLights(Color.BLUE, 3000, 3000).setContentIntent(contentIntent);

                NotificationManager notifManager = (NotificationManager) ct
                        .getSystemService(Context.NOTIFICATION_SERVICE);

                notifManager.notify(notifId, notifBuilder.build());

            }



        }
        catch (Exception e)
        {
            e.printStackTrace();
        }



    }




    public void startservice()
    {
        try
        {
            PendingIntent pendingIntent = null;
            Intent alarmIntent = new Intent(ct, InactiveNotification.class);
            pendingIntent = PendingIntent.getBroadcast(ct, 0, alarmIntent, 0);

            AlarmManager manager = (AlarmManager) ct.getSystemService(Context.ALARM_SERVICE);
            manager.cancel(pendingIntent);

            Calendar calendar = Calendar.getInstance();

            calendar.setTimeInMillis(System.currentTimeMillis());

            calendar.add(Calendar.DAY_OF_MONTH, 14);

            manager.set(AlarmManager.RTC_WAKEUP,  calendar.getTimeInMillis(), pendingIntent);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

}