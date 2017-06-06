package com.helio.cypher.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.sinch.android.rtc.calling.Call;

/**
 * Created by Maroof Ahmed Siddique on 1/21/2017.
 */
public class NotificationService extends Service {

    public static Call runningcall = null;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("ClearFromRecentService", "Service Started");

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d("ClearFromRecentService", "Service Destroyed");
        runningcall = null;
    }

    public void onTaskRemoved(Intent rootIntent) {
        Log.e("ClearFromRecentService", "END");

        //Code here

        if (runningcall != null)
        {
            runningcall.hangup();

        }
        stopSelf();
    }
}


