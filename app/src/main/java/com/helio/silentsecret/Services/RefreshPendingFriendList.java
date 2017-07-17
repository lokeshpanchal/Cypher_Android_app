package com.helio.silentsecret.Services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.TextView;

/**
 * Created by ABC on 9/12/2016.
 */
public class RefreshPendingFriendList extends Service {


    static boolean is_refreshcout = false;
    TextView refreshcount = null;
     Context context = null;

    @Override
    public void onCreate() {
        super.onCreate();
        refreshcount = new TextView(this);
        context = this;


    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }




    /*Runnable Refrescount = new Runnable() {
        @Override
        public void run() {
            refreshcount.removeCallbacks(Refrescount);
            refreshcount.postDelayed(Refrescount, 10000);

            if (ConnectionDetector.isNetworkAvailable(context))
            new AsyncCaller().execute();

        }
    };*/




}
