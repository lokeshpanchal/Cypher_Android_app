package com.helio.silentsecret.controller;

import android.content.Context;

import com.helio.silentsecret.activities.MainActivity;
import com.helio.silentsecret.connection.ConnectionDetector;
import com.helio.silentsecret.utils.ToastUtil;


public class Controller {

    public static final int MOOD = 0;
    public static final int ROOM = 1;
    public static final int ACCESS_MY_SECRET = 2;
    public static final int COUNSELL = 3;

    public static void runGlimpse(int action, Context context, boolean fromGlimpse) {
        String title = null;

        switch (action) {
            case MOOD:
                ((MainActivity) context).moodAccess();
                break;
            case ACCESS_MY_SECRET:
                try
                {
                    ((MainActivity) context).AccessMysecret();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                break;
            case ROOM:
                try
                {
                    ((MainActivity) context).RoomAccess();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                    break;
            case COUNSELL:

                if (ConnectionDetector.isNetworkAvailable(context))
                {
                    ((MainActivity) context).QRAccess();
                } else {
                    new ToastUtil(context, "Please check your internet connection.");
                }

                  break;

        }

        if (title != null) {

        }
    }



}
